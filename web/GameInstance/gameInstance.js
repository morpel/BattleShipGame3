const INTERVAL_TIME = 2000;
const NumOfProfilePics = 3;
const profilePicsKind = ".svg"
function renderCurrentGames(games) {
    $("#gamesList").empty();
    $.each(games,(index,game) => {
        console.log(game);
        $("#gamesList").append(
            '<div id="game'+index+'">' +
                '<li>Game Name: ' +game.gameName+'</li>' +
                '<li>Game Creator: ' +game.creator+'</li>' +
                '<li>Board Size: ' +game.boardSize+'</li>' +
                '<li>Game Type: ' +game.gameStyle+'</li>' +
                '<li>Players Connected: ' +game.otherPlayerInGame+'</li>' +
                '<li><button id="enterNum'+index+'">Enter</button></li>' +
            '</div>'
        );
        const btnId = "#enterNum"+index;
        $(btnId).click(function(event) {
            enterGame(game.gameName);
        });
    })
}

function enterGame(gameName) {
    console.log(gameName);
}

function randProfilePic(){
    let profilePicPath = "../Client/Resources/animal";
    const randIndex = Math.floor(Math.random()*NumOfProfilePics + 1);
    profilePicPath=profilePicPath.concat(randIndex).concat(profilePicsKind);
    return profilePicPath;
}
function renderLoggedinUsers(users) {
    $("#usersList").empty();
    let profilePicPath = randProfilePic();
    $.each(users,(index,user) => {
        $("#usersList").append(`<li class="mdc-list-item"><img class="mdc-list-item__start-detail profilePic" src=${profilePicPath}> <h2 class="bold">${user.toLocaleUpperCase()}</h2></li>`);
        profilePicPath = randProfilePic();
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

function userLoggedOut(data) {
    console.log(data);
    if (data !== "null") {
        const url = JSON.parse(data.responseText);
        console.log(url.content);
        window.location.href = url.content;
    }
}

function printXmlError(data) {
    console.log(data);
    if (data !== "null" && data!==undefined) {
        const message = JSON.parse(data);
        console.log(message);
        console.log(message.XMLValidityMsg);
        if(message.XMLValidityMsg !== undefined) {
            $("#XmlErrMsg").css({'color':'red'});
            document.getElementById("XmlErrMsg").innerText = message.XMLValidityMsg;
        } else{
            $("#XmlErrMsg").css({'color':'green'});
            document.getElementById("XmlErrMsg").innerText = "Game Loaded Successfully";
        }
    }
}

$(document).ready(
    ()=>{
        setInterval(()=>{
                $.ajax({
                    url:'http://localhost:8080/CheckForXmlErrorsServlet',
                    type:"POST",
                    data: {},
                    success: (data) => printXmlError(data),
                    error: error => console.log(error)
                })
            },INTERVAL_TIME);
        setInterval(()=>{
            $.ajax({
                url: `http://localhost:8080/GameServlet`,
                type: "POST",
                data: {},
                success:(data) => renderGamesAndUsers(data) ,
                error: (data) => userLoggedOut(data)
            })}
        ,INTERVAL_TIME);
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