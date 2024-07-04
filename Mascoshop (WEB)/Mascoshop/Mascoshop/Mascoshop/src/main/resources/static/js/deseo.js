document.addEventListener('DOMContentLoaded', () => {
    const deseoTableBody = document.getElementById('deseo-table-body');
    const deseoItemCountElement = document.getElementById('deseo-item-count');

    // Obtener productos de la lista de deseos del localStorage
    let productsInDeseo = JSON.parse(localStorage.getItem('productsInDeseo')) || [];

    // Función para actualizar la tabla de productos en la lista de deseos
    const updateDeseoTable = () => {
        deseoTableBody.innerHTML = ''; // Limpiar la tabla antes de actualizar
        productsInDeseo.forEach((product, index) => {
            if (product.name && product.price && product.image) {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td><img src="${product.image}" alt="${product.name}" style="width: 50px;"></td>
                    <td>${product.name}</td>
                    <td>$${product.price.toFixed(2)}</td>
                    <td><button class="remove-btn" data-index="${index}">Eliminar</button></td>
                `;
                deseoTableBody.appendChild(row);
            } else {
                console.error('Error: Datos del producto incompletos.', product);
            }
        });
    };

    // Actualizar el contador de elementos en la lista de deseos
    const updateDeseoItemCount = () => {
        const itemCount = productsInDeseo.length;
        if (deseoItemCountElement) {
            deseoItemCountElement.textContent = `(${itemCount})`;
        }
    };

    // Inicializar la tabla y el contador al cargar la página
    updateDeseoTable();
    updateDeseoItemCount();

    // Añadir eventos a los botones de añadir a la lista de deseos
    const addToDeseoButtons = document.querySelectorAll('.add-to-deseo-button');
    addToDeseoButtons.forEach(button => {
        button.addEventListener('click', (e) => {
            e.preventDefault();
            const product = {
                name: button.getAttribute('data-name'),
                price: parseFloat(button.getAttribute('data-price')),
                image: button.getAttribute('data-image')
            };

            const productIndex = productsInDeseo.findIndex(p => p.name === product.name);

            if (productIndex > -1) {
                alert('Este producto ya está en la lista de deseos.');
            } else {
                productsInDeseo.push(product);
            }

            localStorage.setItem('productsInDeseo', JSON.stringify(productsInDeseo));
            updateDeseoItemCount();
            updateDeseoTable(); // Actualizar la tabla cuando se añaden productos
        });
    });

    // Añadir evento para eliminar productos de la lista de deseos
    deseoTableBody.addEventListener('click', (e) => {
        if (e.target.classList.contains('remove-btn')) {
            const index = e.target.getAttribute('data-index');
            productsInDeseo.splice(index, 1);
            localStorage.setItem('productsInDeseo', JSON.stringify(productsInDeseo));
            updateDeseoTable();
            updateDeseoItemCount();
        }
    });
});
