function form_check() {
    // form요소 submit 전 유효성 검사
    let pwd = document.getElementById("pwd");
    let repwd = document.getElementById("repwd");
  
    function red_err_txt() {
      err_txt.style.color = "red";
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

  }
