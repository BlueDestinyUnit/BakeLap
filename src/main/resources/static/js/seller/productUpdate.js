// const [searchInput, searchBtn] = document.querySelector("section").querySelectorAll('input')
// const postForm = document.querySelector('.postForm')
//
// searchBtn.onclick = search_product;
//
//
// function search_product (){
//     searchValue = searchInput.value;
//     const filterParam = `search=${searchValue}`
//     create_product(filterParam)
// }
//
// function get_all_products(filterParam) {
//     fetch(`/product/updateProduct?${filterParam}`)
//         .then(response => response.json())
//         .then(object => {
//             console.log(object)
//             create_product(object)
//         })
//         .catch(error => {
//             console.error('An error occurred:', error);
//             // 또는 사용자에게 메시지 표시
//         });
// }
//
// function create_product(products) {
//     postForm.innerHTML = '';
//     for (const product of products) {
//         const product_name = product.productVO.product_name;
//         const price = product.productVO.price;
//         const context = product.productVO.context;
//         const nutrition = product.productVO.nutrition
//         const allergy = product.productVO.allergy
//         const category = product.productVO.category
//         const stock = product.productVO.stock
//         const regis_date = product.productVO.regis_date;
//         const image = product.imagesVO[0].image;
//         console.log(image);
//         productSection.insertAdjacentHTML("beforeend", `
//                <form th:action="@{/sale/seller}" method="post" enctype="multipart/form-data">
//                   제품명: ${product_name}<input type="text" name="product_name"> </br>
//                   가격: ${price}<input type="text" name="price"> </br>
//                   내용: ${context}<input type="text" name="context"> </br>
//                   영양정보:${nutrition} <input type="text" name="nutrition"> </br>
//                   알러지 :${allergy} <input type="text" name="allergy"> </br>
//                   카테고리 : ${category} <input type="text" name="category"> </br>
//                   재고 :${stock} <input type="text" name="stock"> </br>
//                   파일: <img src="/product/image/${image}" alt="이미지가 존재하지않습니다"> </br> <input type="file" name="images" multiple height="100px"> </br>
//                   등록날자:${regis_date}<input type="datetime-local" name="regis_date"> </br>
//
//                   <button>확인</button>
//                 </form>
//             `
//         )
//     }
// }
