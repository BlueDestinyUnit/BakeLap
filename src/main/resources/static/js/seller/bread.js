const productSection = document.querySelector(".product")
const searchBtn = document.querySelector('.search-btn')

searchBtn.onclick = get_sel_products;

function get_sel_products(){
    const searchTxt = document.querySelector('.search-txt').value;
    console.log(searchTxt)
    const filterParam = `search='${searchTxt}'`;
    get_all_products(filterParam)
}


console.log('하이')

get_all_products()



function get_all_products(filterParam) {
    fetch(`/product?${filterParam}`)
        .then(response => response.json())
        .then(object => {
            console.log(object)
            create_product(object)
        })
        .catch(error => {
            console.error('An error occurred:', error);
            // 또는 사용자에게 메시지 표시
        });
}

function create_product(products) {
    productSection.innerHTML = '';
    for (const product of products) {
        const product_name = product.productVO.product_name;
        const price = product.productVO.price;
        const disPrice =  price * 0.94;
        const image = product.imagesVO[0].image;
        console.log(image);
        productSection.insertAdjacentHTML("beforeend", `
                <ul class="product-list">
                      <li class="baby-product">
                          <a href="#" style="height: 356px;">
                              <ul class="baby-product-wrap">
                                  <li class="image">
                                      <img src="/product/image/${image}" alt="이미지가 존재하지않습니다">
                                  </li>
                                  <li class="description">
                                      <ul class="baby-product badges">
                                          <li class="badge delivery-info">무료배송</li>
                                      </ul>
                                  <li class="name">${product_name}</li>
                                  <li>
                                      <ul class="price-area">
                                          <li class="price-wrap">
                                              <div class="price">
                                                <span class="price-info">
                                                    <span class="discount-percentage">6%</span>
                                                    <del class="base-price">${price}</del>
                                                </span>
                                                  <em class="sale">
                                                      <strong class="price-value">${disPrice}</strong>원
                                                  </em>
                                              </div>
                                              <div class="delivery">
                                                <span class="arrival-info emphasis">
                                                    <em style="color:#212B36">모레(목)</em>
                                                    <em style="color:#212B36">도착 예정</em>
                                                </span>
                                              </div>
                                          </li>
                                      </ul>
                                  </li>
                              </ul>
                          </a>
                      </li>
                </ul>
            `
        )
    }
}
