const tbody = document.querySelector('tbody');
let data; // 전역 변수로 data 선언
function orderList() {
    tbody.innerHTML = '';
    data.orderList.forEach(order => {
        const order_number = order.purchaseVO.order_number;
        const pay_number = order.paymentVO.pay_number;
        const image = order.image; // 예시로 이미지 URL을 가져오는 부분, 실제로는 객체의 구조에 맞게 접근
        const product_name = order.purchaseVO.product_name; // 품목명을 가져오는 부분
        const price = order.price; // 가격을 가져오는 부분
        const discount = price*0.9 // 할인 가격을 가져오는 부분
        const count = order.purchaseVO.count; // 개수를 가져오는 부분
        const payPrice = discount * count; // 총 가격을 가져오는 부분
        const address = order.orderVO.address;
        const purchase_date = order.paymentVO.purchase_date.substring(0,10) + ' ' + order.paymentVO.purchase_date.substring( 12)
        console.log(order.paymentVO)

        tbody.insertAdjacentHTML('beforeend', `
            <tr class="cart_list_detail">
                <td style="border:1px solid #772800; border-left:none; border-right:none;">
                   
                </td>
                <td style="border-right:1px solid #772800;">
                    <img src="/product/image/${image}" alt="이미지가 출력되지 않았습니다" style="width:80px; height:80px;">
                </td>
                <td style="border-right:1px solid #772800;">
                    <a href="#">
                        <p style="margin-left:45px;">${product_name}</p>
                    </a>
                    <span style="text-decoration: line-through; color: lightgray; margin-left:45px;">${price}</span>
                    <sapn class="price">${discount}원</sapn>
                </td>
                <td class="cart_list_option">
                    <p>품목명 : ${product_name} / ${count}개</p>
                    <p>배송지 : ${address}</p>
                    <p>구매날짜 : ${purchase_date}</p>
                </td>
                <td class="td5" style="border-left:1px solid #772800;">
                    <span class="price">${payPrice}</span><br>
                </td>
                <td class="td6" style="border:1px solid #772800; border-right:none; font-weight:bold;">${order_number}
                <br> ${pay_number}
                </td>
            </tr>
        `);
    });
}




function getOrderList() {
    fetch(`/orderList?page=${currentPage}`) // 현재 페이지 번호를 서버에 전달
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json(); // JSON 데이터로 변환
        })
        .then(responseData => {
            data = responseData; // 전역 변수에 데이터 저장
            console.log('안녕');
            // 데이터를 사용하는 로직을 여기에 작성
            console.log(data); // 응답 데이터를 콘솔에 출력하거나 다른 작업을 수행할 수 있습니다.
            const totalPage = data.totalPage;
            console.log(totalPage)
            updatePaginationButtons(totalPage);
            orderList(); // 데이터를 받아온 후에 orderList 함수 호출
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

// 페이지 번호 클릭 시 호출되는 함수
function goToPage(pageNumber) {
    currentPage = pageNumber; // 클릭한 페이지 번호로 현재 페이지 업데이트
    getOrderList(); // 새로운 페이지의 데이터 가져오기
}


const pageSize = 3; // 한 페이지에 표시할 항목 수
let currentPage = 1; // 현재 페이지
getOrderList();
function updatePaginationButtons(totalPages) {
    const paginationContainer = document.getElementById("pagination-buttons");
    paginationContainer.innerHTML = "";

    // 이전 페이지 버튼
    const prevButton = document.createElement("button");
    prevButton.innerText = "이전 페이지";
    prevButton.className = "move-button";
    prevButton.addEventListener("click", () => {
        if (currentPage > 1) {
            currentPage--;
            getOrderList(); // 페이지 변경 시 데이터 다시 불러오기
        }
    });
    paginationContainer.appendChild(prevButton);

    // 페이지 번호 버튼들
    for (let i = 1; i <= totalPages; i++) {
        const pageButton = document.createElement("button");
        pageButton.innerText = i;
        pageButton.className = "page-button";
        pageButton.addEventListener("click", () => {
            currentPage = i;
            getOrderList(); // 페이지 변경 시 데이터 다시 불러오기
        });
        paginationContainer.appendChild(pageButton);
    }

    // 다음 페이지 버튼
    const nextButton = document.createElement("button");
    nextButton.innerText = "다음 페이지";
    nextButton.className = "move-button";
    nextButton.addEventListener("click", () => {
        if (currentPage < totalPages) {
            currentPage++;
            getOrderList(); // 페이지 변경 시 데이터 다시 불러오기
        }
    });
    paginationContainer.appendChild(nextButton);
}

// 페이지 번호 버튼들을 가져옴
const pageButtons = document.querySelectorAll(".page-button");

// 각 페이지 번호 버튼에 대해 클릭 이벤트 핸들러 등록
pageButtons.forEach(button => {
    button.addEventListener("click", () => {
        const pageNumber = parseInt(button.innerText); // 클릭한 버튼의 페이지 번호 추출
        console.log(pageNumber)
        goToPage(pageNumber); // 페이지 번호를 goToPage 함수로 전달하여 페이지 변경
    });
});