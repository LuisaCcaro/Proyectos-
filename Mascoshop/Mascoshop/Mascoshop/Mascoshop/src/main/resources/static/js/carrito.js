document.addEventListener('DOMContentLoaded', () => {
    const cartTableBody = document.getElementById('cart-table-body');
    const cartItemCountElement = document.getElementById('cart-item-count');

    // Obtener productos del localStorage
    let productsInCart = JSON.parse(localStorage.getItem('productsInCart')) || [];

    // Función para actualizar el contador de elementos en el carrito
    const updateCartItemCount = () => {
        const itemCount = productsInCart.reduce((total, product) => total + product.quantity, 0);
        if (cartItemCountElement) {
            cartItemCountElement.textContent = `(${itemCount})`;
        }
    };

    // Función para agregar un producto al carrito
    const addToCart = (product) => {
        const productIndex = productsInCart.findIndex(p => p.name === product.name);

        if (productIndex > -1) {
            productsInCart[productIndex].quantity += 1;
        } else {
            productsInCart.push(product);
        }

        localStorage.setItem('productsInCart', JSON.stringify(productsInCart));
        updateCartItemCount();
        updateCartTable(); // Actualizar la tabla cuando se añaden productos
    };

    // Función para actualizar la tabla de productos en el carrito
    const updateCartTable = () => {
        if (cartTableBody) {
            cartTableBody.innerHTML = ''; // Limpiar la tabla antes de agregar las filas

            productsInCart.forEach((product, index) => {
                if (product.name && product.price && product.image) {
                    const row = document.createElement('tr');
                    row.innerHTML = `
                        <td><img src="${product.image}" alt="${product.name}" style="width: 50px;"></td>
                        <td>${product.name}</td>
                        <td>$${product.price.toFixed(2)}</td>
                        <td>${product.quantity}</td>
                        <td><button class="remove-btn" data-index="${index}">Eliminar</button></td>
                    `;
                    cartTableBody.appendChild(row);
                } else {
                    console.error('Error: Datos del producto incompletos.', product);
                }
            });

            // Añadir eventos a los botones de eliminar después de agregar las filas
            document.querySelectorAll('.remove-btn').forEach(button => {
                button.addEventListener('click', removeProductFromCart);
            });
        }
    };

    // Función para eliminar un producto del carrito
    const removeProductFromCart = (event) => {
        const index = event.target.getAttribute('data-index');
        console.log('Index:', index);

        if (index !== null && productsInCart[index]) {
            // Verificar la cantidad del producto en el carrito
            if (productsInCart[index].quantity > 1) {
                // Si hay más de uno, reducir la cantidad en uno
                productsInCart[index].quantity -= 1;
            } else {
                // Si es el último, eliminar el producto del array
                productsInCart.splice(index, 1);
            }

            localStorage.setItem('productsInCart', JSON.stringify(productsInCart)); // Actualizar el localStorage
            updateCartTable(); // Actualizar la tabla
            updateCartItemCount(); // Actualizar el contador de elementos en el carrito
        } else {
            console.error('Producto no encontrado en el índice especificado:', index);
        }
    };

    // Inicializar la tabla y el contador al cargar la página
    updateCartTable();
    updateCartItemCount();

    // Añadir eventos a los botones de añadir al carrito
    const addToCartButtons = document.querySelectorAll('.add-to-cart-button');
    addToCartButtons.forEach(button => {
        button.addEventListener('click', (e) => {
            e.preventDefault();
            console.log('Button clicked');
            const product = {
                name: button.getAttribute('data-name'),
                price: parseFloat(button.getAttribute('data-price')),
                image: button.getAttribute('data-image'),
                quantity: 1
            };

            addToCart(product);
        });
    });
});
