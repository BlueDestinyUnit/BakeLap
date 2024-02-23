const verifyCodeConFirmBtn=document.getElementById('verify_code_confirm_btn');
const VerifyCodeInput=document.getElementById("verify_code_input");
const verifyCodeGetBtn=document.getElementById('verify_code_get_btn');

verifyCodeGetBtn.onclick=get_verify_code;
verifyCodeConFirmBtn.onclick=check_verify_code;

// 인증번호받기 버튼 눌렀을 때 인증번호 받아오기
function get_verify_code(){
    // 번호 정규식
    let regex_mobile = /^[0-9]{10,11}$/g;

    // 작성되어있는 휴대폰 번호 가져오기
    const phoneNumber = document.getElementById('mobile').value.trim();
    if(phoneNumber == "" || !regex_mobile.test(phoneNumber)){
        alert('올바른 전화번호를 입력해주세요');
        return;
    }
    // 해당 휴대폰 번호를 전달해서 GET 요청
    fetch(`/user/sms/key?phoneNumber=${phoneNumber}`)
        .then(response => response.json())
        .then(object => {
            if(object){
                alert("인증번호 발송 완료");
                console.log('인증번호를 생성 성공!');
            }else{
                alert("오류: 인증번호 발급 실패");
                console.log('인증번호를 생성 실패!');
            } })
        .catch();
}
//인증번호 받아와서 체크
function check_verify_code(){
    const key = VerifyCodeInput.value;
    fetch(`/user/sms/verify?key=${key}`)
        .then(response => response.json())
        .then(object => {
            if(object){
                alert("인증에 성공하였습니다");
                phone_verified(); //인증 성공 시 처리
                console.log('인증 성공!');
            }else{
                alert("인증번호가 일치하지 않습니다");
                console.log('인증 실패!');
            } })
        .catch();
}

function phone_verified(){
    document.getElementById("phone_verified").value=true;
    verifyCodeGetBtn.toggleAttribute("disabled", true);
    verifyCodeConFirmBtn.toggleAttribute("disabled", true);
    VerifyCodeInput.toggleAttribute("disabled", true);

    VerifyCodeInput.style.backgroundColor = 'gray';

    verifyCodeGetBtn.onclick = null;
    verifyCodeConFirmBtn.onclick = null;

    VerifyCodeInput.value = "전화번호 인증 완료";

}