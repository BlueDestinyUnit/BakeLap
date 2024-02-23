///// 마이페이지 이메일
const submitBtn = document.querySelector("button[name='submitBtn']");
// 이메일 인증
const EmailSubmitBtn=document.getElementById("email_Authenti_btn");
const EmailCodeConfirmBtn=document.getElementById("email_code_confirm_btn");
const EmailCodeInput=document.getElementById("email_code");

///////////// 마이페이지
submitBtn.onclick = create_email;

//////////// 이메일 인증
EmailSubmitBtn.onclick = email_submit;
EmailCodeConfirmBtn.onclick = check_email_code;

// 나눠져 있는 이메일 하나로 만드는 함수
function create_email() {
    const hiddenEmail = document.querySelector("input[name='email']");
    const emailID = document.querySelector('input[name="email_id"]').value;
    const emailDns = document.querySelector('input[name="email_dns"]').value;
    hiddenEmail.value = emailID + '@' + emailDns;
}

//이메일을 서버로 보내는 함수
    function email_submit() {
        create_email();
        const userEmail = document.querySelector("input[name='email']").value;
        if (userEmail == '@') {
            alert('이메일을 입력해주세요');
            return;
        }
        fetch(`/user/join/email?userEmail=${userEmail}`)
            .then(response => response)
            .then(object => {
                if (object){
                    alert("이메일을 전송 하였습니다");
                }else{
                    alert("오류: 이메일 전송에 실패했습니다")
                }
            })
            .catch();
    }

//이메일 인증 확인을 위해 사용자가 입력한값 서버로 보내는 함수
    function check_email_code() {
        const userEmailKey = EmailCodeInput.value
        if (!userEmailKey) {
            alert('인증번호를 입력해주세요!');
            return;
        }
        fetch(`/user/join/email/verify?userEmailKey=${userEmailKey}`)
            .then(response => response.json())
            .then(object => {
                if (object) {
                    alert("인증에 성공하였습니다");
                    email_verified();
                } else {
                    alert("인증번호가 일치하지 않습니다");
                }
            })
            .catch();
    }

    function email_verified() {
        document.getElementById("email_verified").value = true;
        EmailSubmitBtn.toggleAttribute("disabled", true);
        EmailCodeConfirmBtn.toggleAttribute("disabled", true);
        EmailCodeInput.toggleAttribute("disabled", true);

        EmailCodeInput.style.backgroundColor = 'gray';

        EmailSubmitBtn.onclick = null;
    }

