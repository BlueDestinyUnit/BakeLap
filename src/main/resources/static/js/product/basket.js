// basketVO 쿠키 읽어오기

const tbody = document.querySelector('tbody');
const returnShopping = document.querySelector('.cart_bigorderbtn.left');
const allCheck = document.querySelector('.allCheck')
const check = document.getElementsByClassName('check')

// 메인 화면으로 이동
returnShopping.addEventListener('click', () => {
    window.location.href = "/";
});
// 체크된 수 감지
allCheck.addEventListener('click', () => {
    Array.from(check).forEach(checked => {
        checked.checked = allCheck.checked;
    });
});
// 쿠키 가져오기
function getCookie(cookieName) {
    const name = cookieName;
    const decodedCookie = decodeURIComponent(document.cookie);
    const cookieArray = decodedCookie.split(';');
    const basketCookieArray = cookieArray.filter(cookie => cookie.trim().indexOf(name) === 0);
    const basketVOArray = [];
    for (let i = 0; i < basketCookieArray.length; i++) {
        let cookie = basketCookieArray[i];
        const cookieDivd = cookie.split('=');
        const cookieOrginName = cookieDivd[0].trim();
        cookie = cookieDivd[1];
        cookie = cookie.replace(/\+/g, ' '); // replace 메서드의 결과를 다시 할당
        cookie = cookie.replace('}',`,"cookie_name":"${cookieOrginName}"}`);
        basketVOArray.push(cookie); // 가공된 쿠키 값을 배열에 추가
    }

    return basketVOArray;
}

// basketVO 쿠키 읽어오기
const basketVOJsonArray = getCookie("basketVO");

// JSON 문자열을 JavaScript 객체로 파싱
const basketVOArray = basketVOJsonArray.map(jsonString => JSON.parse(jsonString));

function create_cookiedate(){
    tbody.innerHTML='';
    let no =0;
    basketVOArray.forEach(basketVO => {
        // JSON 파싱하여 JavaScript 객체로 변환
        // 키와 값을 추출하여 사용
        const cookieOrginName = basketVO.cookie_name;
        const count = basketVO.count;
        const product_name = basketVO.product_name;
        const price = basketVO.price;
        const discount = price * 0.94;
        const totalPrice = price * count;
        const image = basketVO.image;


        tbody.insertAdjacentHTML('beforeend', `
            <tr class="cart_list_detail"  >
                <td style="border:1px solid #772800; border-left:none; border-right:none;">
                    <input class="check" type="checkbox" value="${cookieOrginName}">
                </td>
                <td style="border-right:1px solid #772800;">
                    <img src="/product/image/${image}" alt="이미지가 출력되지 않았습니다" style="width:80px; height:80px;">
                </td>
                <td style="border-right:1px solid #772800;">
                    <a href="#"><p style="margin-left:45px;">${product_name}</p></a>
                    <span style="text-decoration: line-through; color: lightgray; margin-left:45px;">${price}</span>
                    <sapn class="price">${discount}원</sapn>
                 </td>
                <td class="cart_list_option">
                    <p>품목명 : ${product_name} / ${count}개</p>
                    
                </td>
                <td style="border-left:1px solid #772800;"><span class="price">${totalPrice}</span><br>
                                                
                 </td>
                <td style="border:1px solid #772800; border-right:none; font-weight:bold;">무료</td>
            </tr>
        `)
        ++no
    })
}

function get_all_basketList() {
    fetch(`/mypage/basketList`)
        .then(response => response.json())
        .then(object => {
            console.log(object)
            create_basketList(object)
        })
        .catch(error => {
            console.error('An error occurred:', error);
            // 또는 사용자에게 메시지 표시
        });
}


// 장바구니 목록 생성
function create_basketList(basketVO) {
    tbody.innerHTML='';
    for (const basketVOS of basketVO) {
        // 키와 값을 추출하여 사용
        const no = basketVOS.no;
        const userID = basketVO.userID;
        const count = basketVOS.count;
        const product_name = basketVOS.product_name;
        const price = basketVOS.price;
        const discount = price * 0.90;
        const totalPrice = discount * count;
        const image = basketVOS.image;

        tbody.insertAdjacentHTML('beforeend', `
            <tr class="cart_list_detail">
                <td style="border:1px solid #772800; border-left:none; border-right:none;">
                    <input class="check" type="checkbox" value="${no}">
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
                </td>
                <td class="td5" style="border-left:1px solid #772800;">
                    <span class="price">${totalPrice}</span><br>
                </td>
                <td class="td6" style="border:1px solid #772800; border-right:none; font-weight:bold;">무료</td>
            </tr>
        `)
    }
}

const checkedValues = [];
const deleteBtn = document.querySelector('#deleteBtn');
const csrfTokenElement = document.querySelector('#csrfToken');
const csrfToken = csrfTokenElement.value;


// 선택된 장바구니 확인
function selected_product (){
    Array.from(check).forEach(checked => {
        if (checked.checked === true) {
            checkedValues.push(checked.value);
        }
    })
}

function deleteCookie(checkedValues) {
    for (const cookieName of checkedValues) {
        document.cookie = cookieName + '=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';
    }
}
// 장바구니 목록 삭제
deleteBtn.onclick = () => {
    const auth =document.getElementById('auth').innerText;
    selected_product();
    if(auth === 'false'){
        deleteCookie(checkedValues);
        location.reload();
        return;
    }

    const dataToSend = {
        key1: checkedValues
    };

    console.log(dataToSend)


    fetch('/basketCancle', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json', // 데이터 형식 지정
            'X-CSRF-TOKEN': csrfToken
        },
        body: JSON.stringify(dataToSend) // 데이터를 JSON 형식으로 변환하여 전송
    })
        .then(response => {
            console.log(response);
            if (response.ok) {
                // 성공적으로 처리된 경우 페이지 새로고침
                location.reload();
            }
        })
        .catch(error => {
            console.log(error);
            // 오류 처리
        });
};

const orderBtn = document.querySelector('.cart_bigorderbtn.right')
// 주문으로 이동
orderBtn.onclick = () => {
    const auth =document.getElementById('auth').innerText;
    if(auth === 'false'){
        alert('주문은 로그인 후 이용해주세요')
        return;
    }

    selected_product()
    const dataToSend = {
        key1: checkedValues
    };
    fetch('/purchaseReady2', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json', // 데이터 형식 지정
            'X-CSRF-TOKEN': csrfToken
        },
        body: JSON.stringify(dataToSend)// 데이터를 JSON 형식으로 변환하여 전송
    })
        .then(response => {
            if (response.ok) {
                // 성공적으로 처리된 경우 '/buy2'로 이동
                window.location.href = '/mypage/buy';
            }
        })
        .catch(error => {
            console.log(error);
            // 오류 처리
        });
}