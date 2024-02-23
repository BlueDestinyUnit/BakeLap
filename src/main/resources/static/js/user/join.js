const idCheckBtn = document.querySelector("button[name='check_id']")
let idVerified = document.getElementById("id_verified");

//////////////////// 아이디 중복 검사
idCheckBtn.onclick = check_user_id;

function check_user_id(){
    const userIdInput = document.getElementById("u_id");
    const userId = userIdInput.value.trim();

    if (userId == "" ){
        let err_txt = document.querySelector(".err_id");
        err_txt.style.color = "red";
        err_txt.textContent = "* 아이디를 입력하세요";
        userIdInput.focus();
        return false;
    }
    const uid_len = userId.length;
    if (uid_len < 4 || uid_len > 12) {
        let err_txt = document.querySelector(".err_id");
        err_txt.style.color = "red";
        err_txt.textContent = "* 4~12글자까지만 입력 가능합니다.";
        userIdInput.focus();
        return false;
    }
    fetch(`/user/join/id?userId=${userId}`)
        .then(response => response.json())
        .then(object => {
            if (object){
                alert("사용 가능한 아이디 입니다");
                idVerified.value = "verified";
                form_check()
            }else{
                alert("이미 사용 중인 아이디 입니다");
                idVerified.value = "none";
                form_check()
            }
        })
        .catch();
}
function form_check() {
    // form요소 submit 전 유효성 검사
    let u_name = document.getElementById("u_name");
    let pwd = document.getElementById("pwd");
    let repwd = document.getElementById("repwd");
    let agree = document.getElementById("agree");

    function red_err_txt() {
      err_txt.style.color = "red";
    }

    console.log(idVerified.value);

    if (u_name.value == "") {
      var err_txt = document.querySelector(".err_name");
      red_err_txt();
      err_txt.textContent = "* 이름을 입력하세요.";
      u_name.focus();
      return false;
    }

    if (idVerified.value === "none" || idVerified.value !== "verified"){
        alert("아이디 중복 여부를 확인해주세요!");
        return false;
    }

    if (pwd.value == "") {
      var err_txt = document.querySelector(".err_pwd");
      red_err_txt();
      err_txt.textContent = "* 비밀번호를 입력하세요";
      pwd.focus();
      return false;
    }
    var pwd_len = pwd.value.length;
    if (pwd_len < 4 || pwd_len > 12) {
      var err_txt = document.querySelector(".err_pwd");
      red_err_txt();
      err_txt.textContent = "* 4~12글자까지만 입력 가능합니다.";
      pwd.focus();
      return false;
    }
    if (pwd.value != repwd.value) {
      var err_txt = document.querySelector(".err_repwd");
      red_err_txt();
      err_txt.textContent = "* 비밀번호를 확인해주세요.";
      repwd.focus();
      return false;
    }
    if(email_id.value==""&&email_dns.value==""){
        var err_txt = document.querySelector(".err_email");
        red_err_txt();
        err_txt.textContent = "이메일을 입력해주세요.";
        email.focus();
        return false;
    }
    if(email_code.value==""){
        var err_txt = document.querySelector(".err_email_confirm");
        red_err_txt();
        err_txt.textContent = "이메일 인증을 완료해주세요.";
        email_code.focus();
        return false;
    }
    var reg_mobile = /^[0-9]{10,11}$/g;
    if (!reg_mobile.test(mobile.value)) {
      var err_txt = document.querySelector(".err_mobile");
      red_err_txt();
      err_txt.textContent = "전화번호는 숫자만 입력할 수 있습니다.";
      mobile.focus();
      return false;
    }
    if (!agree.checked) {
      alert("약관 동의가 필요합니다.");
      agree.focus();
      return false;
    }

}

function id_search() {
    window.open("search_id.html", "", "width=600, height=250, left=0, top=0");
}

function add_search() {
    window.open("search_addr.html", "", "width=600, height=250, left=0, top=0");
}

