
let idVerified = document.getElementById("id_verified");


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


