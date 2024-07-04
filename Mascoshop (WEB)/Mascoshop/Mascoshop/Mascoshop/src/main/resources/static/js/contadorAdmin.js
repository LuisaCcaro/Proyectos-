document.addEventListener("DOMContentLoaded", function() {
    fetch('/en/usuarios/countClientes')
        .then(response => response.json())
        .then(data => {
            document.getElementById('clientesCount').textContent = data;
        })
        .catch(error => console.error('Error al obtener el conteo de clientes:', error));

    fetch('/api/productos/count')
        .then(response => response.json())
        .then(data => {
            document.getElementById('productosCount').textContent = data;
        })
        .catch(error => console.error('Error al obtener el conteo de productos:', error));
});