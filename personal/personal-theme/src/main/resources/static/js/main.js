'use strict';

// navbar variables
const nav = document.querySelector('.mobile-nav');
const navMenuBtn = document.querySelector('.nav-menu-btn');
const navCloseBtn = document.querySelector('.nav-close-btn');

// navToggle function
const navToggleFunc = function () { nav.classList.toggle('active'); };

navMenuBtn.addEventListener('click', navToggleFunc);
navCloseBtn.addEventListener('click', navToggleFunc);

// theme toggle variables
const themeBtn = document.querySelectorAll('.theme-btn');

for (let i = 0; i < themeBtn.length; i++) {
    themeBtn[i].addEventListener('click', function () {
        const themeElement = document.getElementById('theme-wrapper');
        themeElement.classList.toggle('light-theme');
        themeElement.classList.toggle('dark-theme');
        for (let i = 0; i < themeBtn.length; i++) {
            themeBtn[i].classList.toggle('light');
            themeBtn[i].classList.toggle('dark');
        }
    });
}
