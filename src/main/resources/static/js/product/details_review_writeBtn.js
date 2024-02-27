const reviewBtn = document.querySelector(".review");


// 버튼 클릭 시, 리뷰 작성 메뉴 열고 닫기
reviewBtn.addEventListener('click', () => {
    const sectionWrite = document.querySelector(".review_write");
    sectionWrite.classList.toggle('create');
    text_check();
})

// 글자 실시간 확인, 제한 길이 체크
function text_check(){
    const textarea = document.querySelector("textarea");
    let counter = document.getElementById("counter");

    textarea.onkeyup = e => {
        let context = e.target.value;
        counter.innerText = context.length;
        if (context.length > 500){
            alert("최대 500자까지 작성 가능 합니다!")
            counter.innerText = "500";
            textarea.value = context.substring(0,501);
        }
    }
}