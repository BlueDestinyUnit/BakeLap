const csrfTokenElement = document.querySelector('#csrfToken');
const csrfToken = csrfTokenElement.value;

function count(count){
    let result=document.getElementById('result');
    if(count=="plus"){
        result.textContent=parseInt(result.textContent)+1;

    }
    else if(count=="minus"){
        if(result.textContent>0){
            result.textContent=parseInt(result.textContent)-1;
        }
    }
    const hiddenCount = document.querySelector("input[name='count']");
    hiddenCount.value = result.textContent;
}

function nutritional(){
    let info=document.getElementById("nutritional");
    const arr=info.textContent.split(",");
    console.log(arr);
    info.innerHTML='';
    for(let i=0;i<arr.length;i++){
        info.insertAdjacentHTML("beforeend",`<div>${arr[i]}</div>`);
    }

}
nutritional();