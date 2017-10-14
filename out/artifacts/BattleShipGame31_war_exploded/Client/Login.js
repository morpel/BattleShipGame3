const servletUrl = buildUrlWithContextPath("LoginPageServlet")
console.log(servletUrl);
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
                    window.location.href = url.content;
                }
            },
            error: error => console.log(error)
        })
    }

    checkIfSessionExists();
    $("#nickNameForm").submit((event) => {
        const name = $("#nickNameInput").val();
        $.ajax({
            type: 'POST',
            url: `http://localhost:8080/${servletUrl}`,
            data: {
                userName: name
            },
            success: function (data) {
                // console.log(data.stringify(data));
                if (data !== undefined && data !== null) {
                    const url = JSON.parse(data);
                    console.log(url.content);
                    window.location.href = url.content;

                }
            },
            error: error => console.log(error)
        });
        event.preventDefault();

    });
})