document.getElementById('emailForm').addEventListener('submit', function(event) {
    event.preventDefault();
    const formData = new FormData(this);
    const data = Object.fromEntries(formData.entries());
    data.to = data.to.trim();

    Swal.fire({
        title: 'Enviando...',
        text: 'Por favor, espera mientras enviamos tu correo.',
        allowOutsideClick: false,
        onBeforeOpen: () => {
            Swal.showLoading();
        }
    });

    fetch('/gmail', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (response.ok) { 
            Swal.fire({
                title: '¡Ëxito!',
                text: 'Correo enviado exitosamente',
                icon: 'success'
            });
            this.reset();
        } else {
            return response.text().then(text => { throw new Error(text); });
        }
    })
    .catch(error => {
        console.error('Error en la solicitud Fetch:', error);
        Swal.fire({
            title: '¡Hola!',
            text: 'Hubo un error al enviar el correo: ' + error.message,
            icon: 'success'
        });
    });
});

//productos



