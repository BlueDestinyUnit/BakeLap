const categorylist=document.querySelectorAll('.category>li');
categorylist.forEach(categoryLi => {
    categoryLi.onclick = () => {
        window.location.href = `/?category=${categoryLi.className}`
    }
} );

document.addEventListener("DOMContentLoaded", function() {
    // 모바일 화면 크기에서만 실행되도록 체크
    if (window.matchMedia("(max-width: 768px)").matches) {
        mobileEventHandler();
    }
});

function mobileEventHandler() {
    const sideBar = document.querySelector('.side-bar');
    const sideBarIcon = document.querySelector('.side-bar-icon-box > .side-bar-icon-1');
    const story = document.querySelector('.story');
    const menu = document.querySelector('.menu');
    const myPage = document.querySelector('.my-page');

    story.addEventListener('click',function (e) {
        if(sideBar.classList.contains('expanded')){
            e.preventDefault();
        }
    })

    menu.addEventListener('click',function (e) {
        if(sideBar.classList.contains('expanded')){
            e.preventDefault();
        }
    })

    myPage.addEventListener('click',function (e) {
        if(sideBar.classList.contains('expanded')){
            e.preventDefault();
        }
    })

    sideBar.addEventListener('click', function(e) {
        sideBar.classList.toggle('expanded');
        sideBarIcon.classList.toggle('move');
        for (let child of sideBarIcon.children) {
            child.classList.toggle('div-event')
        }
    });

}