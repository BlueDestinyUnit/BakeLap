const tbody = document.querySelector('#mainTbody');
const address = document.querySelector('#address').value;
const addressList = document.getElementsByClassName('addressList')
const final = document.querySelector('.final')
const pay = document.querySelector('#pay')
const addrSerch = document.querySelector('#addrSerch')
const postalCode = document.querySelector('#postalCode').value

const csrfTokenElement = document.querySelector('#csrfToken');
const csrfToken = csrfTokenElement.value;

pay.onclick = requestPay;

var addrsum
addr();
function addr(){
    count = 0;
    address.split(',').forEach(addr => {
        addressList[count].value = addr;
        count++;
    })
}
for(let i; i<addressList.length; i++){
    addrsum += addressList[i].value;
}
console.log(addressList[1].value+','+addressList[2].value)
// 쿠키 이름으로 쿠키 값을 가져오는 함수
function getCookie(cookieName) {
    const cookieValue = document.cookie
        .split('; ')
        .find(row => row.startsWith(cookieName))
        .split('=')[1];
    return decodeURIComponent(cookieValue);
}

// 'purchaseReadyJson' 쿠키에서 데이터 가져오기
const purchaseReadyJsonString = getCookie('purchaseReadyJson');
console.log(purchaseReadyJsonString)
// 쿠키에서 데이터를 가져왔을 경우 원하는 작업 수행
const purchaseReadyDatas = JSON.parse(purchaseReadyJsonString);
ready(purchaseReadyDatas);
function ready(purchaseReadyDatas){
    tbody.innerHTML='';
    final.innerHTML='';
    total = purchaseReadyDatas.length;
    originPrice = 0;
    discountPrice = 0;
    totalPrice = 0;
    purchaseReadyDatas.forEach(purchaseReadyData => {
        const purchase_name = purchaseReadyData.product_name.replaceAll('+',' ');
        const count = purchaseReadyData.count;
        const price = purchaseReadyData.price;
        const discount = purchaseReadyData.price * 0.90;
        originPrice += price;
        discountPrice = originPrice * 0.1;
        totalPrice += discount;
        const image = purchaseReadyData.image;
        tbody.insertAdjacentHTML('beforeend',`
        <tr class="cart_list_detail">
            <td style="border:1px solid #772800; border-left:none; border-right:none;"></td>
            <td style="border-right:1px solid #772800;"><img src="/product/image/${image}" alt="이미지가 없습니다" style="width:80px; height:80px;"></td>
            <td style="border-right:1px solid #772800;"><a href="/product/details?product_name=${purchase_name}"><p class="product_name" style="margin-left:45px;">${purchase_name}</p></a>
                <span style="text-decoration: line-through; color: lightgray; margin-left:45px;">${price}</span>
                <span class="price">${discount}</span>
            </td>
            <td class="cart_list_option">
                <p>${purchase_name} :   ${count} 개</p>

            </td>
            <td id="td5" style="border-left:1px solid #772800;"><span class="price">${discount}원</span><br>
            </td>
            <td id="td6" style="border:1px solid #772800; border-right:none; font-weight:bold;">무료</td>
        </tr>`)
    })
    final.insertAdjacentHTML('beforeend',`
        <table>
            <tr>
                <td>총</td>
                <td id="totalCount">${total} 건</td>
            </tr>
            <tr>
                <td>상품금액</td>
                <td>${originPrice}</td>
            </tr>
            <tr>
                <td>할인금액</td>
                <td>-${discountPrice}</td>
            </tr>
            <tr>
                <td>배송비</td>
                <td>0</td>
            </tr>
            <tr>
                <td>전체주문금액</td>
                <td id="tPrice">${totalPrice}</td>
            </tr>
        </table>
        <button id="pay" onclick="requestPay()">결제하기
        `)

}

// 결제 인증
const userCode = "imp48836513";
var IMP = window.IMP;
IMP.init(userCode);


const product_names = document.getElementsByClassName('product_name')
function find_product_names(){
    str_product_name = '';
    Array.from(product_names).forEach(product_name => {
        str_product_name += product_name.innerHTML+',';
    })
    return str_product_name = str_product_name.slice(0, -1)
}
const tPrice = +document.querySelector('#tPrice').innerHTML
const product_name = find_product_names();
const name = document.querySelector('#name').innerHTML;
const tel = document.querySelector('#tel').innerHTML;
const email = document.querySelector('#email').innerHTML;

// 결제 스위치
function requestPay() {
    // 1. 주문번호 생성
    order_number_create()
        .then(data => {
            const orderNumber = data.order_number;
            const payNumber = data.pay_number;

            // 3. 결제
            performPayment(orderNumber, payNumber);
        })
        .catch(error => {
            console.error('Error:', error);
            // 오류 처리 로직을 추가할 수 있습니다.
        });
}

// 결제 진행
function performPayment(orderNumber, payNumber) {
    IMP.request_pay({
        pg: "kakaopay.TC0ONETIME",
        pay_method: "card",
        merchant_uid: orderNumber,
        name: product_name,
        amount: tPrice,
        buyer_email: email,
        buyer_name: name,
        buyer_tel: tel,
        buyer_addr: address,
        buyer_postcode: postalCode
    }, function (rsp) {
        if (rsp.success) {
            // 4. 결제 성공 처리
            handlePaymentSuccess(rsp, orderNumber, payNumber);
        }
    });
}

// 결제 핸들러
function handlePaymentSuccess(rsp, orderNumber, payNumber) {
    // 5. 결제 정보 서버로 전송 및 처리
    sendPaymentDataToServer(rsp, orderNumber, payNumber,purchaseReadyDatas)
        .then(data => {
            // 서버에서의 응답 데이터 처리
            // 이 부분에 서버 응답에 대한 작업을 추가하세요.
        })
        .catch(error => {
            console.error('Error:', error);
            // 오류 처리 로직을 추가할 수 있습니다.
        });
}

// 결제 완료
function sendPaymentDataToServer(rsp, orderNumber, payNumber, purchaseReadyDatas) {
    return fetch('/payResult', {
        method: "post",
        headers: {
            "Content-Type": "application/json",
            "X-CSRF-TOKEN": csrfToken
        },
        body: JSON.stringify({
            imp_uid: rsp.imp_uid,
            merchant_uid: rsp.merchant_uid,
            order_number: orderNumber,
            pay_number: payNumber,
            amount: tPrice,
            purchase_ready_data: purchaseReadyDatas
        })
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        }).then(data => {

            window.location.href = '/'; // 원하는 페이지 URL로 변경
        })
}

// 주문번호 생성
function order_number_create() {
    return fetch('/orderNumber')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        });
}
