const reviewForm = document.querySelector(".review_write");
const reviewUpdateForm = document.querySelector(".review_context_modal")
const context = document.querySelector(".update_textarea")

// 댓글 전체 조회 하기
get_all_reviews();

function get_all_reviews(){
    const product_name = document.querySelector(".product_detail > h1").innerText;
    fetch(`/review?product_name=${product_name}`)
        .then(response => response.json())
        .then(object => {
            create_review(object);
        })
        .catch();
}

// 리뷰 작성
reviewForm.addEventListener("submit", e => {
    const context = document.querySelector("textarea");
    if(context.value.trim() === ""){
        alert("리뷰가 작성 되지 않았습니다.")
        return false;
    }
    if (confirm("등록 하시겠습니까?")){
        alert("리뷰등록이 완료되었습니다.")
        location.reload();
    }
})

// 총 댓글 수
function all_reviews_count() {
    let count = document.querySelector('.review_context').childElementCount;
    const countSpan = document.querySelector('.counts');
    countSpan.innerText = count + '건';
}

// 총 평균 평점 수
function all_scores_avg(scoreArr){
    const angSpan = document.querySelector('.avg');
    const result = scoreArr.reduce((sum, currentValue) => {return sum + currentValue;}, 0);
    const avgResult = (result / scoreArr.length).toFixed(1); // 소수점 첫째 자리까지
    if (isNaN(avgResult) === true){
        angSpan.innerText = 0;
    }
    else {
        angSpan.innerText = avgResult;
    }
}

// 리뷰 수정
function update_review(no){
    const body = document.getElementsByTagName('body')[0];
    const reviewContextModal = document.querySelector(".review_context_modal");
    const inputNo = document.querySelector("input[name=no]");
    if (no == null){
        alert("비정상적인 접근 방식입니다")
        return false;
    }
    reviewContextModal.classList.toggle("update");
    body.classList.add('scrollLock');
    none_btn();
    inputNo.value = no;
    update_text_check();

    // 모달창 닫기 함수
    set_modal();
}

function set_modal(){
    const body = document.getElementsByTagName('body')[0];
    const modalBtn = document.querySelector(".update_delete_btn");
    const reviewContextModal = document.querySelector(".review_context_modal");
    const updateBtns = document.querySelectorAll(".update_btn");
    const deleteBtns = document.querySelectorAll(".delete_btn");

    modalBtn.addEventListener("click", () => {

        reviewContextModal.classList.add("update");

        body.classList.remove('scrollLock');

        updateBtns.forEach(btn => {
            btn.style.display = "inline-block"
        })
        deleteBtns.forEach(btn => {
            btn.style.display = "inline-block"
        })
    })
}

// 버튼 숨기기
function none_btn(){
    const updateBtns = document.querySelectorAll(".update_btn");
    const deleteBtns = document.querySelectorAll(".delete_btn");
    updateBtns.forEach(btn => {
        btn.style.display = "none"
    })
    deleteBtns.forEach(btn => {
        btn.style.display = "none"
    })
}

// 리뷰 수정창 글자 실시간 확인, 제한 길이 체크
function update_text_check(){
    let counterUpdate = document.getElementById("counter_update");

    context.onkeyup = e => {
        let updateContext = e.target.value;
        counterUpdate.innerText = updateContext.length;
        if (updateContext.length > 500){
            alert("최대 500자까지 작성 가능 합니다!")
            counterUpdate.innerText = "500";
            context.value = updateContext.substring(0,501);
        }
    }
}

// 수정 창 submit
reviewUpdateForm.addEventListener("submit", e => {

    if (context.value.trim() === "") {
        alert("수정할 내용이 작성되지 않았습니다!")
        return false;
    }
    if (confirm("수정 하시겠습니까?")){
        window.location.reload();
    }
})

// 리뷰 삭제
function delete_review(no){
    if (!confirm("정말 삭제 하시겠습니까?")){
        return false;
    }
    console.log(no)
    fetch(`/review/delete?no=${no}`)
        .then(response => response)
        .catch();
    location.reload();
}

function create_review(reviews) {
    const reviewSection = document.querySelector(".review_context");
    let scoreArr = [];

    reviewSection.innerHTML = '';

    for (const review of reviews) {
        const no = review.no;
        const userID = review.userID
        const reviewUserID = userID.substring(0,4) + "****";
        const context = review.context;
        const score = review.score;
        scoreArr.push(score);
        let submit_date = review.submit_date;
        submit_date = submit_date.replace("T", " ");

        const buttonObj = userNameCheck(userID, no);
        reviewSection.insertAdjacentHTML("beforeend",
            `<div class="review_context_item">
                    <p class="item_set">
                        <span class="name">${reviewUserID}님</span>
                        <span><i class="fa-solid fa-star" style="color: #c01313;"></i></span>
                        <span>${score}점</span>
                        ${buttonObj}
                    </p>
                    <div class="item_context">
                        <p>${context}</p>
                    </div>
                    <p class="item_date">${submit_date}</p>
                </div>`
        )
        all_reviews_count();
    }
    all_scores_avg(scoreArr);
}

// 로그인 유저(username), 댓글 단 유저(userID) 일치 확인
function userNameCheck(userID, no, context) {
    const usernameSpan = document.querySelector(".review_write > li > span");
    let username = '';

    // 만약 null 이 아니면 =>  로그인 한 사람이 있다.
    if (usernameSpan != null){
        username = usernameSpan.innerText;
    }

    // userID 과 username이 일치한다면?
    if (userID.includes(username) && userID === username){
        return `<button type="button" class="update_btn" onclick="update_review(${no});">
            <i class="fa-solid fa-pen-to-square fa-lg"></i>
        </button>
        <button type="button" class="delete_btn" onclick="delete_review(${no});">
             <i class="fa-solid fa-xmark fa-lg"></i>
        </button>`;
    }
    return '';
}