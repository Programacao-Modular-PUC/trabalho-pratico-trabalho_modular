// DARK MODE

const themeToggle = document.getElementById('themeToggle');

themeToggle.addEventListener('click', () => {
    document.body.classList.toggle('dark');
});

// FAQ

const questions = document.querySelectorAll('.faq-question');

questions.forEach(question => {

    question.addEventListener('click', () => {

        const answer = question.nextElementSibling;

        if(answer.style.maxHeight){
            answer.style.maxHeight = null;
        } else {
            answer.style.maxHeight =
            answer.scrollHeight + 'px';
        }

    });

});

// SEARCH

const searchInput =
document.getElementById('searchInput');

if(searchInput){

searchInput.addEventListener('keyup', () => {

    const cards =
    document.querySelectorAll('.card');

    cards.forEach(card => {

        const title =
        card.innerText.toLowerCase();

        const value =
        searchInput.value.toLowerCase();

        card.style.display =
        title.includes(value)
        ? 'block'
        : 'none';

    });

});

}