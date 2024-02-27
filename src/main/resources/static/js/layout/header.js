const searchBtn = document.querySelector('#searchBtn')
searchBtn.onclick = get_sel_products;
function get_sel_products(){
    const searchTxt = document.querySelector('#searchText').value;
    const filterParam = `search=${searchTxt}`;
    window.location.href = `/?${filterParam}`

}