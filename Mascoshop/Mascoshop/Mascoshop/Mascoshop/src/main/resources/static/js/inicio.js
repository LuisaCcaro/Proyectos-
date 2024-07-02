document.addEventListener('DOMContentLoaded', () => {

    // const lenis = new Lenis({
    //     duration: 1.2,
    //     easing: (t) => Math.min(1, 1.001 - Math.pow(2, -10 * t)),
    //     smooth: true,
    // });
    
    // function raf(time) {
    //     lenis.raf(time);
    //     requestAnimationFrame(raf);
    // }
    
    // requestAnimationFrame(raf);

    var typed = new Typed('.cambio', {
        strings: ['Gatos', 'Perros'],
        typeSpeed: 50,
        backSpeed: 30, 
        loop: true
    });


const searchInput = document.getElementById('search');
const suggestionsContainer = document.getElementById('suggestions');

const data = [
    'Comida secos para perros',
    'Comida secos para gatos',
    'Comida humedos para perros',
    'Comida humedos para gatos',
    'Especial para perros',
    'Especial para gatos',
    'Snacks para perros',
    'Snacks para gatos'
];

searchInput.addEventListener('input', () => {
    const inputValue = searchInput.value.toLowerCase();
    suggestionsContainer.innerHTML = '';
    
    if (inputValue) {
        const filteredData = data.filter(item => item.toLowerCase().includes(inputValue));
        
        filteredData.forEach(item => {
            const suggestionDiv = document.createElement('div');
            suggestionDiv.textContent = item;
            suggestionsContainer.appendChild(suggestionDiv);
            
            suggestionDiv.addEventListener('click', () => {
                searchInput.value = item;
                suggestionsContainer.innerHTML = '';
            });
        });
    }
});

var swiper = new Swiper(".mySwiper", {
    slidesPerView: 4,
    spaceBetween: 30,
    // loop: true,
    // loopFillGroupWithBlank: true,
    pagination: {
      el: ".swiper-pagination",
      clickable: true,
    },
    navigation: {
      nextEl: ".swiper-button-next",
      prevEl: ".swiper-button-prev",
    },
  });
});