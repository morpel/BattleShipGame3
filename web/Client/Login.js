
function checkNickName() {
    const name = $("#nickNameInput").val();
    console.log(name);
    $.get({
        url:"LoginPageServlet",
        success:(res)=>{console.log(res)},
        error:(error=>console.log(error))
    });

}