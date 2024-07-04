document.addEventListener('DOMContentLoaded', () => {
    const incrementButton = document.getElementById('increment');
    const decrementButton = document.getElementById('decrement');
    const quantityInput = document.getElementById('quantity-input');
    const productPriceElement = document.getElementById('product-price');
    const addToCartButton = document.getElementById('add-to-cart-btn');

    let originalPrice = 0;

    const productId = localStorage.getItem('selectedProductId');
    console.log('Product ID:', productId);

    if (productId) {
        fetch(`http://localhost:9090/api/productos/buscar/${productId}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                return response.json();
            })
            .then(product => {
                originalPrice = product.precio;
                productPriceElement.innerText = `$${originalPrice.toFixed(2)}`;
                document.getElementById('product-image').src = `../img/${product.imagen}`;
                document.getElementById('product-nombre').innerText = product.nombre;
                document.getElementById('product-description').innerText = product.descripcion;
                updatePrice(); // Actualiza el precio inicial
            })
            .catch(error => console.error('Error al obtener los detalles del producto:', error));
    } else {
        console.error('No se ha encontrado el ID del producto.');
    }

    function updatePrice() {
        let currentValue = parseInt(quantityInput.value);
        if (isNaN(currentValue) || currentValue < 1) {
            currentValue = 1;
            quantityInput.value = 1;
        }
        console.log('Current value:', currentValue);
        let totalPrice = originalPrice * currentValue;
        productPriceElement.innerText = `$${totalPrice.toFixed(2)}`;
    }

    incrementButton.addEventListener('click', () => {
        let currentValue = parseInt(quantityInput.value);
        console.log('Increment clicked. Current value:', currentValue);
        if (currentValue < parseInt(quantityInput.max)) {
            quantityInput.value = currentValue + 1;
            updatePrice();
        }
    });

    decrementButton.addEventListener('click', () => {
        let currentValue = parseInt(quantityInput.value);
        console.log('Decrement clicked. Current value:', currentValue);
        if (currentValue > parseInt(quantityInput.min)) {
            quantityInput.value = currentValue - 1;
            updatePrice();
        }
    });

    quantityInput.addEventListener('input', () => {
        updatePrice();
    });

    addToCartButton.addEventListener('click', () => {
        Swal.fire({
            title: 'Confirmar compra',
            text: '¿Estás seguro de que quieres comprar este producto?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Sí, comprar',
            cancelButtonText: 'Cancelar'
        }).then((result) => {
            if (result.isConfirmed) {
                Swal.fire({
                    title: 'Producto vendido!',
                    text: 'El producto ha sido agregado, entrega en 2 días hábiles!',
                    icon: 'success',
                    confirmButtonText: 'Aceptar'
                }).then(() => {
                    window.location.href = '/producto';
                });
            }
        });
    });
});
