const categorylist=document.querySelectorAll('.category>li');
categorylist.forEach(categoryLi => {
    categoryLi.onclick = () => {
        window.location.href = `/?category=${categoryLi.className}`
    }
} );