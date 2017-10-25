const servletUrl = buildUrlWithContextPath("LoginPageServlet")

console.log(servletUrl);

function loginErr(data) {
    $("#errMsg").css({'color':'red'});
    $("#errMsg").val(data.responseText);
    document.getElementById("errMsg").innerText = data.responseText;
    console.log(data.responseText);
}

$(document).ready(function () {
    const checkIfSessionExists = () => {
        $.ajax({
            url: `http://localhost:8080/${servletUrl}`,
            type: "POST",
            data: {},
            success: function (data) {
                console.log(data);
                if (data !== "null") {
                    const url = JSON.parse(data);
                    console.log(url.content);
                    window.location.href = "../"+url.content;
                }
            },
            error: error => console.log(error)
        })
    }

    checkIfSessionExists();
    $("#nickNameForm").submit((event) => {
        const name = $("#nickNameInput").val();
        if (name === "" || name === null || name === undefined)
        {
            alert("please choose a valid nickName!");
        }
        else
        {
            $.ajax({
                type: 'POST',
                url: `http://localhost:8080/${servletUrl}`,
                data: {
                    userName: name
                },
                success: function (data) {
                    if (data !== undefined && data !== null) {
                        const url = JSON.parse(data);
                        console.log(url.content);
                        window.location.href = url.content;

                    }
                },
                error: (data) => {loginErr(data)}
            });
        }
        event.preventDefault();
    });
})