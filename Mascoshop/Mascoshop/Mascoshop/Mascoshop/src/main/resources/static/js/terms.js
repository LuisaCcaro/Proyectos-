//terms y condiciones
const openModal = document.querySelector('.abrir_modal');
const modal = document.querySelector('.modal');
const cierreModal = document.querySelector('.modal_cierre');

openModal.addEventListener('click', (e) => {
    e.preventDefault();
    modal.classList.add('modal_show');
})

cierreModal.addEventListener('click', (e) => {
    e.preventDefault();
    modal.classList.remove('modal_show');
})

//privacidad//
const openModalPrivacidad = document.querySelector('.abrir_modal_privacidad');
const modalPrivacidad  = document.querySelector('.modal_privacidad');
const cierreModalPrivacidad  = document.querySelector('.modal_cierre_privacidad');

openModalPrivacidad.addEventListener('click', (e) => {
    e.preventDefault();
    modalPrivacidad.classList.add('modal_show_privacidad');
});

cierreModalPrivacidad.addEventListener('click', (e) => {
    e.preventDefault();
    modalPrivacidad.classList.remove('modal_show_privacidad');
});