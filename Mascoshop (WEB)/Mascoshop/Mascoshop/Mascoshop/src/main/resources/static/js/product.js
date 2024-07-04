document.addEventListener('DOMContentLoaded', () => {
    const addProduct = document.getElementById('save-product');
    const idCategoria = document.getElementById('categoria');
    const idAnimal = document.getElementById('animal');
    const nombreProducto = document.getElementById('nombre');
    const idMarca = document.getElementById('marcaId');
    const descripcionProducto = document.getElementById('descripcion');
    const precioProducto = document.getElementById('precio');
    const cantidadDispo = document.getElementById('cantidadDisponible');
    const imagenProducto = document.getElementById('imagen'); // Campo de la imagen
    const messageElement = document.getElementById('message');

    // addProduct.addEventListener('click', async () => {
    //     try {
    //         const marcaId = parseInt(idMarca.value.trim(), 10);
    //         if (isNaN(marcaId) || marcaId <= 0) {
    //             throw new Error('Debe especificar un ID válido para la marca.');
    //         }

    //         const categoriaId = parseInt(idCategoria.value.trim(), 10);
    //         if (isNaN(categoriaId) || categoriaId <= 0) {
    //             throw new Error('Debe especificar un ID válido para la categoría.');
    //         }

    //         const animalId = parseInt(idAnimal.value.trim(), 10);
    //         if (isNaN(animalId) || animalId <= 0) {
    //             throw new Error('Debe especificar un ID válido para el animal.');
    //         }

    //         const formData = new FormData();
    //         formData.append('nombre', nombreProducto.value.trim());
    //         formData.append('categoriaProducto.idCategoria', categoriaId);
    //         formData.append('animal.idAnimal', animalId);
    //         formData.append('marca.idMarca', marcaId);
    //         formData.append('descripcion', descripcionProducto.value.trim());
    //         formData.append('precio', parseFloat(precioProducto.value));
    //         formData.append('cantidadDisponible', parseInt(cantidadDispo.value, 10));
    //         formData.append('imagen', imagenProducto.files[0]); // Añadir imagen

    //         const response = await axios.post('/api/productos/agregar-producto', formData, {
    //             headers: {
    //                 'Content-Type': 'multipart/form-data'
    //             }
    //         });

    //         messageElement.textContent = 'Producto guardado exitosamente';
    //         console.log('Response:', response.data);

    //     } catch (error) {
    //         console.error('Error guardando el producto:', error);
    //         messageElement.textContent = 'Error guardando el producto: ' + error.message;
    //     }
    // });

    //BORRAR EL PRODUCTO
// const deleteButton = document.getElementById('delete-product');

//     deleteButton.addEventListener('click', () => {
//         const productId = document.getElementById('id-producto').value;

//         axios.delete(`/api/productos/eliminar-producto/${productId}`)
//             .then(response => {
//                 if (response.status === 204) {
//                     console.log('Producto eliminado!');
//                 } else {
//                     console.error('Unexpected response status:', response.status);
//                 }
//             })
//             .catch(error => {
//                 console.error('Error eliminando el producto:', error);
//                 document.getElementById('message').textContent = 'Error eliminando el producto.';
//             });
//     });

    const updateProductButton = document.getElementById('update-product');
    const searchProductButton = document.getElementById('search-product');
    const productId = document.getElementById('id-producto-update');
    const productName = document.getElementById('update-nombre');
    const productCategory = document.getElementById('update-categoria');
    const productAnimal = document.getElementById('update-animal');
    const productBrand = document.getElementById('update-marcaId');
    const productDescription = document.getElementById('update-descripcion');
    const productPrice = document.getElementById('update-precio');
    const productAmount = document.getElementById('update-cantidadDisponible');
    const productImage = document.getElementById('update-imagen');

    const clearFields = () => {
        productId.value = '';
        productName.value = '';
        productCategory.value = '';
        productAnimal.value = '';
        productBrand.value = '';
        productDescription.value = '';
        productPrice.value = '';
        productAmount.value = '';
        productImage.value = '';
    };

    searchProductButton.addEventListener('click', async () => {
        const id = productId.value;

        if (!id) {
            new Noty({
                type: 'info',
                layout: 'topLeft',
                text: 'Please enter a product ID',
                timeout: 3000
            }).show();
            return;
        }

        try {
            const response = await axios.get(`/api/productos/buscar/${id}`);
            if (response.status === 200) {
                const product = response.data;
                productName.value = product.nombre;
                productCategory.value = product.categoriaProducto.idCategoria;
                productAnimal.value = product.animal.idAnimal;
                productBrand.value = product.marca.idMarca;
                productDescription.value = product.descripcion;
                productPrice.value = product.precio;
                productAmount.value = product.cantidadDisponible;
                messageElement.textContent = 'Product found.';
            } else {
                new Noty({
                    type: 'warning',
                    layout: 'topRight',
                    text: 'Product not found',
                    timeout: 3000
                }).show();
            }
        } catch (error) {
            console.error('Error fetching product: ', error);
            if (error.response && error.response.status === 404) {
                new Noty({
                    type: 'warning',
                    layout: 'topRight',
                    text: 'Product not found',
                    timeout: 3000
                }).show();
            } else {
                messageElement.textContent = 'Error fetching product: ' + error.message;
            }
        }
    });

    updateProductButton.addEventListener('click', async () => {
        const id = productId.value;

        const formData = new FormData();
        formData.append('nombre', productName.value);
        formData.append('categoriaProducto.idCategoria', parseInt(productCategory.value));
        formData.append('animal.idAnimal', parseInt(productAnimal.value));
        formData.append('marca.idMarca', parseInt(productBrand.value));
        formData.append('descripcion', productDescription.value);
        formData.append('precio', parseFloat(productPrice.value));
        formData.append('cantidadDisponible', parseInt(productAmount.value));
        if (productImage.files.length > 0) {
            formData.append('imagen', productImage.files[0]);
        }

        if (!id || !formData.get('nombre') || !formData.get('categoriaProducto.idCategoria') || !formData.get('animal.idAnimal') ||
            !formData.get('marca.idMarca') || !formData.get('descripcion') || !formData.get('precio') ||
            !formData.get('cantidadDisponible')) {
            new Noty({
                type: 'info',
                layout: 'topLeft',
                text: 'Please fill in all fields.',
                timeout: 3000
            }).show();
            return;
        }

        try {
            const response = await axios.put(`/api/productos/editar-producto/${id}`, formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            });
            if (response.status === 200) {
                new Noty({
                    type: 'success',
                    layout: 'topRight',
                    text: 'The product updated successfully.',
                    timeout: 3000
                }).show();
                clearFields();
            } else {
                new Noty({
                    type: 'error',
                    layout: 'topRight',
                    text: 'Failed to update the product',
                    timeout: 3000
                }).show();
            }
        } catch (error) {
            console.error('Error updating product: ', error.message);
            new Noty({
                type: 'error',
                layout: 'topRight',
                text: 'Error updating the product',
                timeout: 3000
            }).show();
        }
    });
});