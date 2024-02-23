const idCheckBtn = document.querySelector("button[name='check_id']")
let idVerified = document.getElementById("id_verified");

//////////////////// 아이디 검사
idCheckBtn.onclick = id_check;

// 아이디 - 일치 여부 확인
function id_check() {
    const idInput = document.getElementById("u_id");
    const userId = idInput.value.trim();

    if (userId == "" ){
        let err_txt = document.querySelector(".err_id");
        err_txt.style.color = "red";
        err_txt.textContent = "* 아이디를 입력하세요";
        idInput.focus();
        return false;
    }

    fetch(`/user/find/pwd/check?userId=${userId}`)
    .then(response => response.json())
    .then(object => {
        if (object) {
            alert("아이디가 확인 되었습니다.");
            id_verified();
            form_check();
        } else {
            alert("이용 중인 아이디가 없습니다.");
            idVerified.value = "none";

        }
    })
    .catch();

    function id_verified() {
        idCheckBtn.toggleAttribute("disabled", true);
        idInput.toggleAttribute("readonly", true);

        idInput.style.backgroundColor = 'gray';
        idVerified.value = "verified";

        idCheckBtn.onclick = null;
    }
}



function form_check(){
    const VerifyCodeInput= document.getElementById("verify_code_input");

    if (idVerified.value === "none" || idVerified.value !== "verified"){
        alert("아이디 확인 해주세요!");
        return false;
    }

    if(idCheckBtn == null && !VerifyCodeInput.value.includes("전화번호 인증 완료")){
        alert("전화번호 인증을 해주세요!");
        return false;
    }
}



