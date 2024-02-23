const saveBtn =  document.querySelector(".final_btn > button")

saveBtn.addEventListener("click", gap_check );

//  저장 하기 전 유효성 검사
function gap_check(){
    // 요소의 input 값들 공백 제거
    let postalCode = document.getElementById('postalCode').value.trim();
    let addr1 = document.getElementById("addr1").value;
    const add2 = document.getElementById("addr2");
    const email_id = document.getElementById("email_id");
    const email_dns = document.getElementById("email_dns").value.trim();
    const mobile = document.getElementById("mobile");
    const VerifyCodeInput = document.getElementById("verify_code_input");

    // 이메일 정규식
    let regex_email = /^[A-Za-z0-9_\\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/; //(알파벳,숫자)@(알파벳,숫자).(알파벳,숫자)

    // 이메일
    let email = email_id.value.trim() + '@' + email_dns;

    // 에러 메세지
    function red_err_txt() {
        err_txt.style.color = "red";
    }


    // 모두 공백일 경우
    if ( !addr1.trim() && !add2.value.trim() && !email_id.value.trim() && !email_dns && !mobile.value.trim()) {
        alert("수정 사항을 작성 해주세요!")
        return false;
    }
    // 아닐 경우 =>  한 칸이라도 적었다
    else{

        // 상세 주소가 작성 되지 않았다
        if(postalCode && addr1.trim() && !add2.value.trim()){
            var err_txt = document.querySelector(".err_address");
            red_err_txt();
            err_txt.textContent = "상세 주소를 입력 해주세요.";
            add2.focus();
            return false;
        }// 상세 주소만 작성 되었다
        if(!postalCode && add2.value.trim()){
            alert("주소 검색을 해주세요!");
            return false;
        }

        // 이메일을 올바르게 적었다
        if (email_id.value.trim() && email_dns){
            // 이메일이 올바른 형식이 아니다.
            if( !regex_email.test(email) ){
                var err_txt = document.querySelector(".err_email");
                red_err_txt();
                err_txt.textContent = "유효한 이메일 형식이 아닙니다.";
                email_id.focus();
                return false;
            }// 이메일이 올바른 형식이다.
            // save();
        }
        // 둘 중 한 곳만 작성 했다
        else if (email_id.value.trim() || email_dns){
            var err_txt = document.querySelector(".err_email");
            red_err_txt();
            err_txt.textContent = "이메일을 올바르게 입력 해주세요.";
            email_id.focus();
            return false;
        }
        // 전화번호를 적고 인증 하지 않았을 시
        if (mobile.value.trim() && !VerifyCodeInput.value.trim()){
            alert("인증번호를 발송해주세요!")
            return false;
        }
        // 그 외
        save();

    }
    // submit
    function save(){

        if(!confirm("저장 하시겠습니까?")){
            return false;
        }
        return saveBtn.setAttribute("type","submit");
    }
}


