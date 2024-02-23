// 전화 번호 형식 변경
function format_tel(){
  const tel = document.querySelector(".tel");
  const reg_tel = /^(\d{2,3})(\d{3,4})(\d{4})$/;

  tel.innerHTML = tel.innerHTML.replace(reg_tel, `$1-$2-$3`);

}

// 주소 형식 변경
function format_addr(){
    const addr = document.querySelector(".addr");
    let [zipCode, add1, add2] = addr.innerHTML.split(",");

    addr.innerHTML = add1 + ", " + add2 + " ("  + zipCode + ")";
}
//탈퇴 확인 체크
const deleteBtn=document.getElementById("delete");
deleteBtn.onclick=deleteBtn_account_check;

function deleteBtn_account_check(){
    if(confirm("정말 회원탈퇴 하시겠습니까?")){
        return true;
    }else{
        return false;
    }
}
format_tel();
format_addr();




