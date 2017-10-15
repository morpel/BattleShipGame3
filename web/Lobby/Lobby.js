const INTERVAL_TIME = 2000;

function renderCurrentGames(games) {
    
}

function renderLoggedinUsers(users) {
    $("#usersList").empty();
    $.each(users,(index,user) => {
        $("#usersList").append('<h3>'+user+'</h3>');
    })
}

function renderGamesAndUsers(data){
    const gamesAndUsersObj = JSON.parse(data);
    console.log(gamesAndUsersObj);
    if (gamesAndUsersObj !== null && gamesAndUsersObj!==undefined){
        renderCurrentGames(gamesAndUsersObj.games);
        renderLoggedinUsers(gamesAndUsersObj.users);
    }
}

function gameLoadError(data) {

}

function gameLoadSuccess(data) {

}
/*
function insertNewGame() {
    var selectedFile = document.getElementById('insertGameBtn').files[0];
    console.log(selectedFile);
    var reader = new FileReader();
    reader.onload = (function(theFile) {
        return function(e) {
            $.ajax({
                url: `http://localhost:8080/AddGameServlet`,
                type: "POST",
                data: { "xmlPath":e},
                success:(data) => gameLoadSuccess(data) ,
                error: (data) => gameLoadError(data)
            })
        }
    });
    reader.readAsText(selectedFile);

}
*/
function userLoggedOut(data) {
    console.log(data);
    const url = JSON.parse(data.responseText);
    console.log(url.content);
    window.location.href = url.content;
}

$(document).ready(
    ()=>{
        setInterval(()=>{
            $.ajax({
                url: `http://localhost:8080/GameServlet`,
                type: "POST",
                data: {},
                success:(data) => renderGamesAndUsers(data) ,
                error: (data) => userLoggedOut(data)
            })
        },INTERVAL_TIME)
    }
);

function logoutUser(){
    $.ajax({
        type: 'POST',
        url: `http://localhost:8080/LogoutServlet`,
        data: {},
        success: (data) => userLoggedOut(data),
        error: (data) => {console.log(data)}
    });
    event.preventDefault();
}

$(document).ready(function () {
    $("#logoutBtn").onclick = function(event){
            $.ajax({
                type: 'POST',
                url: `http://localhost:8080/LogoutServlet`,
                data: {},
                success: function (data) {
                    if (data !== undefined && data !== null) {
                        const url = JSON.parse(data);
                        console.log(url.content);
                        window.location.href = url.content;
                    }
                },
                error: (data) => {console.log(data)}
            });
        event.preventDefault();
    }
});