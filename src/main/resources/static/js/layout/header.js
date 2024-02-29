const searchBtn = document.querySelector('#searchBtn')
const searchText = document.getElementById('searchText');


function get_sel_products(){
    const value = searchText.value;
    const filterParam = `search=${value}`;
    window.location.href = `/?${filterParam}`
}
searchText.addEventListener('keyup', function(event) {
    console.log('안녕')
    if (event.key === 'Enter') {
        const value = searchText.value;
        const filterParam = `search=${value}`;
        window.location.href = `/?${filterParam}`
    }
});


document.addEventListener("DOMContentLoaded", function() {
    // 모바일 화면 크기에서만 실행되도록 체크
    if (window.matchMedia("(max-width: 768px)").matches) {
        console.log('안녕')
        searchBtn.addEventListener('click',function (e) {
            if(searchBtn.classList.contains('ready') || searchText.value === ''){
                e.preventDefault();
            }else{
                get_sel_products();
            }
            searchBtn.classList.toggle('ready');
        })
    }else{
        searchBtn.onclick = get_sel_products;
    }


});