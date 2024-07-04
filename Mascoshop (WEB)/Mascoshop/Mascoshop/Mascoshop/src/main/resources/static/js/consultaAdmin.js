document.addEventListener('DOMContentLoaded', function() {
    const tableBody = document.querySelector('#productos-table tbody');

    function loadProductos() {
        fetch('/api/productos')
            .then(response => response.json())
            .then(productos => {
                tableBody.innerHTML = '';

                productos.forEach(producto => {
                    const row = document.createElement('tr');
                    row.innerHTML = `
                        <td>${producto.idProducto}</td>
                        <td>${producto.nombre}</td>
                        <td>${producto.descripcion}</td>
                        <td>${producto.precio}</td>
                        <td>${producto.cantidadDisponible}</td>
                        <td>${producto.animal ? producto.animal.nombre : ''}</td>
                        <td>${producto.marca ? producto.marca.nombre : ''}</td>
                        <td>${producto.categoriaProducto ? producto.categoriaProducto.nombre : ''}</td>
                        <td><img src="img/${producto.imagen}" width="50"></td>
                        <td><button class="delete-button" data-id="${producto.idProducto}">Delete</button></td>
                    `;
                    tableBody.appendChild(row);
                });

                const deleteButtons = document.querySelectorAll('.delete-button');
                deleteButtons.forEach(button => {
                    button.addEventListener('click', function() {
                        const productId = this.getAttribute('data-id');
                        fetch(`/api/productos/eliminar-producto/${productId}`, {
                            method: 'DELETE'
                        })
                        .then(response => {
                            if (response.status === 204) {
                                Swal.fire({
                                    title: 'Éxito',
                                    text: 'Producto eliminado!',
                                    icon: 'success'
                                });
                                loadProductos();
                            } else {
                                console.error('Error eliminando el producto:', response.status);
                            }
                        })
                        .catch(error => {
                            console.error('Error eliminando el producto:', error);
                        });
                    });
                });
            })
            .catch(error => console.error('Error al cargar productos:', error));
    }

    loadProductos();
    setInterval(loadProductos, 5000);
});

document.addEventListener('DOMContentLoaded', function() {
    const categoryIdInput = document.getElementById('categoryIdInput');
    const form = document.getElementById('update-product-form');
    const updateButton = document.getElementById('update-product');

    if (!categoryIdInput) {
        console.error('Element with id "categoryIdInput" not found');
        return;
    }

    categoryIdInput.addEventListener('keydown', function(event) {
        if (event.key === 'Enter') {
            event.preventDefault();
            const categoryId = categoryIdInput.value;

            console.log(`Fetching data for category ID: ${categoryId}`);

            fetch(`/api/productos/${categoryId}`)
                .then(response => {
                    console.log('Response status:', response.status);
                    if (!response.ok) {
                        throw new Error('Network response was not ok ' + response.statusText);
                    }
                    return response.json();
                })
                .then(producto => {
                    console.log('Producto fetched:', producto);

                    if (producto.idProducto) {
                        setValue('#categoria', producto.categoriaProducto.idCategoria);
                        setValue('#animal', producto.animal.idAnimal);
                        setValue('#nombre', producto.nombre);
                        setValue('#marcaId', producto.marca.idMarca);
                        setValue('#descripcion', producto.descripcion);
                        setValue('#precio', producto.precio);
                        setValue('#cantidadDisponible', producto.cantidadDisponible);
                    } else {
                        Swal.fire({
                            title: 'Error',
                            text: 'Producto no encontrado',
                            icon: 'error'
                        });
                    }
                })
                .catch(error => {
                    console.error('Error al cargar el producto:', error);
                    Swal.fire({
                        title: 'Error',
                        text: 'Error al cargar el producto. Verifique los logs del servidor.',
                        icon: 'error'
                    });
                });
        }
    });

    const updateProductButton = document.getElementById('update-product');
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
            Swal.fire({
                title: 'Información',
                text: 'Completar los campos',
                icon: 'info'
            });
            return;
        }

        try {
            const response = await axios.put(`/api/productos/editar-producto/${id}`, formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            });
            if (response.status === 200) {
                Swal.fire({
                    title: 'Éxito',
                    text: 'Producto actualizado correctamente',
                    icon: 'success'
                });
                clearFields();
            } else {
                Swal.fire({
                    title: 'Error',
                    text: 'Producto no actualizado',
                    icon: 'error'
                });
            }
        } catch (error) {
            console.error('Error updating product: ', error.message);
            Swal.fire({
                title: 'Error',
                text: 'Producto no actualizado',
                icon: 'error'
            });
        }
    });

    function setValue(selector, value) {
        const element = form.querySelector(selector);
        if (element) {
            element.value = value;
        } else {
            console.error(`Element with selector "${selector}" not found`);
        }
    }
});

document.addEventListener("DOMContentLoaded", function() {
    fetch('/en/usuarios')
        .then(response => response.json())
        .then(data => {
            const tbody = document.querySelector("#admin-table tbody");
            data.forEach(user => {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>${user.id}</td>
                    <td>${user.nombre}</td>
                    <td>${user.apellido}</td>
                    <td>${user.correo}</td>
                    <td>${user.nombreUsuario}</td>
                    <td>${user.direccion}</td>
                    <td>${user.telefono}</td>
                    <td>${user.rol ? user.rol.nombre : 'Sin rol'}</td>
                `;
                tbody.appendChild(row);
            });
        })
        .catch(error => console.error('Error:', error));
});