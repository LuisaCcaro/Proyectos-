// productosDOM.js
const productos = [
    {
        id: 1,
        name: "Royal Canin Cachorros",
        price: 5.80,
        image: "../img/RoyalCaninCachorros.jpg",
        category: "secos",
        description: "Comida para cachorros.",
        rating: 4
    },
    {
        id: 2,
        name: "Alimentos húmedos Adulto",
        price: 5.70,
        image: "../img/AlimentoHumedoPerro.png",
        category: "humedos",
        description: "Comida húmeda para perros adultos.",
        rating: 3
    },
    {
        id: 3,
        name: "Snacks Purina",
        price: 3.20,
        image: "../img/snacksPurina.jpg",
        category: "snacks",
        description: "Deliciosos snacks para perros.",
        rating: 5
    },
    {
        id: 4,
        name: "Alimentos especializados",
        price: 5.60,
        image: "../img/AlimentoEspecializadoGatos.jpg",
        category: "medicado",
        description: "Comida especializada para gatos.",
        rating: 4
    },
    {
        id: 5,
        name: "Alimentos secos Pedigree",
        price: 5.80,
        image: "../img/SecoPerro.png",
        category: "secos",
        description: "Comida seca para perros.",
        rating: 4
    },
    {
        id: 6,
        name: "Alimentos secos R. Canin",
        price: 5.70,
        image: "../img/RoyalCaninAdulto.jpg",
        category: "secos",
        description: "Comida seca para perros adultos.",
        rating: 3
    },
    {
        id: 7,
        name: "Snacks Hills",
        price: 3.20,
        image: "../img/snackGatos.webp",
        category: "snacks",
        description: "Snacks para gatos.",
        rating: 5
    },
    {
        id: 8,
        name: "Alimento especializado",
        price: 5.60,
        image: "../img/medicadoGatos.png",
        category: "medicado",
        description: "Comida especializada para gatos.",
        rating: 4
    }
];


document.addEventListener('DOMContentLoaded', () => {
    fetch('/api/productos')
        .then(response => response.json())
        .then(data => {
            generateProducts(data);
        })
        .catch(error => console.error('Error al obtener los productos:', error));

    const filters = document.querySelectorAll('.categoriaFilter');
    filters.forEach(filter => {
        filter.addEventListener('change', () => {
            filterProducts();
        });
    });

    document.addEventListener('click', function (event) {
        if (event.target.closest('.view-product-button')) {
            const productId = event.target.closest('.view-product-button').getAttribute('data-id');
            localStorage.setItem('selectedProductId', productId);
            window.location.href = '/carta';
        }
    });
});

function generateProducts(productos) {
    const container = document.getElementById('container-products');
    if (!container) return;
    container.innerHTML = '';

    productos.forEach(producto => {
        const productCard = `
            <div class="card-product ${producto.categoria}">
                <div class="container-img">
                    <img src="../img/${producto.imagen}" alt="${producto.nombre}">
                </div>
                <div class="content-card-product">
                    <div class="stars">
                        <i class="fa-solid fa-star"></i>
                        <i class="fa-solid fa-star"></i>
                        <i class="fa-solid fa-star"></i>
                        <i class="fa-solid fa-star"></i>
                        <i class="fa-regular fa-star"></i>
                    </div>
                    <h3>${producto.nombre}</h3>
                    <span class="add-cart">
                        <a href="#" class="view-product-button" data-id="${producto.idProducto}"><i class="fa-regular fa-eye"></i></a>
                    </span>
                    <p class="price">$${producto.precio}</p>
                </div>
            </div>
        `;
        container.innerHTML += productCard;
    });

    document.querySelectorAll('.view-product-button').forEach(button => {
        button.addEventListener('click', function() {
            const productId = this.getAttribute('data-id');
            localStorage.setItem('selectedProductId', productId);
        });
    });
}


function filterProducts() {
    const selectedCategories = Array.from(document.querySelectorAll('.categoriaFilter:checked')).map(checkbox => checkbox.getAttribute('category'));
    const allProducts = document.querySelectorAll('.card-product');

    if (selectedCategories.length === 0 || selectedCategories.includes('all')) {
        allProducts.forEach(product => product.style.display = 'block');
    } else {
        allProducts.forEach(product => {
            const productCategory = product.classList.contains('secos') ? 'secos' :
                                    product.classList.contains('humedos') ? 'humedos' :
                                    product.classList.contains('medicado') ? 'medicado' :
                                    product.classList.contains('snacks') ? 'snacks' :
                                    product.classList.contains('organicos') ? 'organicos' : '';
            product.style.display = selectedCategories.includes(productCategory) ? 'block' : 'none';
        });
    }
}
