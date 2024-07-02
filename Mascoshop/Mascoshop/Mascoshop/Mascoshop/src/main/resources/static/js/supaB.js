document.addEventListener('DOMContentLoaded', () => {
    console.log('DOM completamente cargado y analizado');
    handleAuthResponse();
    updateUIForLoggedInUser();
});

// Función para manejar el inicio de sesión con Google
function signIn() {
    let oauthEndpoint = "https://accounts.google.com/o/oauth2/v2/auth";

    let form = document.createElement('form');
    form.setAttribute('method', 'GET');
    form.setAttribute('action', oauthEndpoint);

    let params = {
        "client_id": "1024245301453-op5p6ofsnqnsketgf8lldkfl9dtukmcr.apps.googleusercontent.com",
        "redirect_uri": "http://localhost:9090/home",
        "response_type": "token",
        "scope": "https://www.googleapis.com/auth/userinfo.profile",
        "include_granted_scopes": "true",
        "state": "pass-through-value"
    };

    for (let p in params) {
        let input = document.createElement('input');
        input.setAttribute('type', 'hidden');
        input.setAttribute('name', p);
        input.setAttribute('value', params[p]);
        form.appendChild(input);
    }

    document.body.appendChild(form);
    form.submit();
}

// Función para manejar la respuesta de autenticación de Google
function handleAuthResponse() {
    let hash = window.location.hash.substr(1);
    let result = hash.split('&').reduce((res, item) => {
        let parts = item.split('=');
        res[decodeURIComponent(parts[0])] = decodeURIComponent(parts[1]);
        return res;
    }, {});

    console.log('Auth response:', result);

    if (result.access_token && !localStorage.getItem('googleToken')) {
        localStorage.setItem('googleToken', result.access_token);
        updateUIForLoggedInUser();
        Swal.fire({
            title: 'Google',
            text: 'Inicio de sesión exitoso',
            icon: 'success'
        }).then(()=>{
            window.location.href = '/home';
        });
    }
}

// Función para actualizar la interfaz de usuario cuando el usuario ha iniciado sesión
function updateUIForLoggedInUser() {
    let loginButton = document.getElementById('loginButton');
    let logoutButton = document.getElementById('logoutButton');
    let configButton = document.getElementById('configButton');
    let configPerfilButton = document.getElementById('configPerfilButton');

    console.log('Revisando tokens en localStorage:');
    console.log('Google Token:', localStorage.getItem('googleToken'));
    console.log('Supabase Token:', localStorage.getItem('supabaseToken'));

    if (localStorage.getItem('googleToken') || localStorage.getItem('supabaseToken')) {
        console.log('La usuario ha iniciado sesión');
        if (loginButton) loginButton.style.display = 'none';
        if (logoutButton) logoutButton.style.display = 'block';
        if (configButton) configButton.style.display = 'block';
        if (configPerfilButton) configPerfilButton.style.display = 'block';
    } else {
        updateUIForLoggedOutUser();
    }
}

// Función para actualizar la interfaz de usuario cuando el usuario ha cerrado sesión
function updateUIForLoggedOutUser() {
    let loginButton = document.getElementById('loginButton');
    let logoutButton = document.getElementById('logoutButton');
    let configButton = document.getElementById('configButton');
    let configPerfilButton = document.getElementById('configPerfilButton');

    console.log('Usuario desconectado');

    if (loginButton) loginButton.style.display = 'block';
    if (logoutButton) logoutButton.style.display = 'none';
    if (configButton) configButton.style.display = 'none';
    if (configPerfilButton) configPerfilButton.style.display = 'none';
}

// Función para cerrar sesión
function signOut() {
    Swal.fire({
        title: 'Cerrar sesión',
        text: '¿Estás seguro de que quieres cerrar sesión?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sí, cerrar sesión'
    }).then((result) => {
        if (result.isConfirmed) {
            localStorage.removeItem('googleToken');
            localStorage.removeItem('supabaseToken');
            Swal.fire(
                '¡Sesión cerrada!',
                'Tu sesión ha sido cerrada.',
                'success'
            );
        }
    }).then(()=>{
        window.location.reload();
    });
}

document.addEventListener('DOMContentLoaded', (event) => {
    const signOutButton = document.getElementById('signOutButton');
    if (signOutButton) {
        signOutButton.addEventListener('click', signOutAdmin);
    } else {
        console.error('El botón de cerrar sesión no se encontró en el DOM.');
    }
});

function signOutAdmin() {
    localStorage.removeItem("supabaseToken");
    window.location.href("/home");
    
}



// Manejar el evento de submit del formulario de login
document.querySelector('#loginForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const formData = new FormData(e.target);
    const formDataJson = Object.fromEntries(formData.entries());

    try {
        const response = await fetch('/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formDataJson),
        });

        if (!response.ok) {
            const errorData = await response.json();
            Swal.fire({
                title: '¡Ups!',
                text: errorData.message,
                icon: 'error'
            }).then(() => {
                window.location.reload();
            });
        } else {
            const data = await response.json();
            console.log('Mensaje de respuesta:', data.message);
            Swal.fire({
                title: '¡Hola!',
                text: data.message,
                icon: 'success'
            }).then(() => {
                if (data.message === "login exitoso") {
                    localStorage.setItem('supabaseToken', data.token); // Guarda el token de Supabase
                    localStorage.setItem('userRole', data.role); // Guarda el rol del usuario
                    localStorage.setItem('authMethod', 'usuario');
                    window.location.href = '/home';
                } else if (data.message === "Administrador - login exitoso") {
                    window.location.href = '/administrador';
                }
            });
        }
    } catch (error) {
        Swal.fire({
            title: 'Error',
            text: 'Error: ' + error.message,
            icon: 'error'
        });
    }
});

// Manejar el evento de submit del formulario de registro
document.querySelector('#form-register').addEventListener('submit', async (e) => {
    e.preventDefault(); // Evitar que se envíe el formulario de forma predeterminada

    const formData = new FormData(e.target); // Obtener datos del formulario
    const formDataJson = Object.fromEntries(formData.entries()); // Convertir a JSON

    try {
        const response = await fetch('/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formDataJson),
        });

        if (!response.ok) {
            // Si la respuesta no es OK, intenta obtener el mensaje de error
            const errorData = await response.json();
            Swal.fire({
                title: '¡Ups!',
                text: data.message,
                icon: 'error'
            }).then(() =>{
                window.location.reload();
            });
            // throw new Error(errorData.message || 'Error al registrar usuario');
        } else {
            const data = await response.json();
            Swal.fire({
                title: 'Exito!',
                text: "Usuario creado exitosamente",
                icon: 'success'
            }).then(() => {
                window.location.reload();
            });
            // alert(data.message || 'Usuario creado exitosamente');
        }
    } catch (error) {
        alert('Error aqui: ' + error.message);
    }
});
