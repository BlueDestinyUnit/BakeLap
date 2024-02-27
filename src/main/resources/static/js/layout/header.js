const searchBtn = document.querySelector('#searchBtn')
const searchText = document.getElementById('searchText');

searchBtn.onclick = get_sel_products;
function get_sel_products(){
    const value = searchText.value;
    const filterParam = `search=${value}`;
    window.location.href = `/?${filterParam}`
}
searchText.addEventListener('keyup', function(event) {
    if (event.key === 'Enter') {
        const value = searchText.value;
        const filterParam = `search=${value}`;
        window.location.href = `/?${filterParam}`
    }
});