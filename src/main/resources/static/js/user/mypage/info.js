// 사용자 정보를 가져오는 함수
function getCurrentUser() {
    return fetch("/user/api")
        .then(response => response.json())
        .then(data =>
            btn_change(data.role))
        .catch(error => {
            console.error('An error occurred while fetching user data:', error);
            return null;
        });
}

function btn_change(role){
    if(role === 'SELLER'){
        const btn = document.getElementsByClassName('btn')
        const a = document.querySelectorAll('.final_btn a')
        btn.item(0).innerHTML = '상품등록'
        a[0].setAttribute('href','/seller/regist')
        btn.item(1).innerHTML = '판매추이'
        a[1].setAttribute('href','/seller/stock')
        a[2].remove();
        a[3].remove();
    }
}
getCurrentUser();