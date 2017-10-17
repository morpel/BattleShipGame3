const INTERVAL_TIME = 2000;

function removeGame(i_gameName) {
    $.ajax({
        url:'http://localhost:8080/DeleteGameServlet',
        type:"POST",
        data: {gameName:i_gameName},
        success: (data) => {
            alert(data);
        },
        error: (error) => {console.log(error)},
    })
}

 function isPlayerOwnGame(i_gameName) {
    return new Promise((resolve,reject)=>{
        $.ajax({
            url:'http://localhost:8080/IsPlayerOwnGameServlet',
            type:"POST",
            data: {gameName:i_gameName},
            success: (data) => {
                if (data === "yes") {
                    resolve(true);
                } else {
                    resolve(false);
                }
            },
            error: (error) => {reject(error)},
        })
    })
}

async function renderCurrentGames(games) {

    $("#gamesList").empty();
    $.each(games,async (index,game) => {
        console.log(game);
        $("#gamesList").append(
            '<div id="game'+index+'">' +
                '<li>Game Name: ' +game.gameName+'</li>' +
                '<li>Game Creator: ' +game.creator+'</li>' +
                '<li>Board Size: ' +game.boardSize+'</li>' +
                '<li>Game Type: ' +game.gameStyle+'</li>' +
                '<li>Players Connected: ' +game.otherPlayerInGame+'</li>' +
                '<li><button id="enterGameNum'+index+'">Enter</button></li>' +
            '</div>'
        );
        const enterBtnId = "#enterGameNum"+index;
        $(enterBtnId).click(function(event) {
            enterGame(game.gameName);
        });
       await isPlayerOwnGame(game.gameName).then((isPlayerOwnGameBool)=>{
            if (isPlayerOwnGameBool){
                const divId = "#game"+index;
                $(divId).append(
                    '<li><button id="removeGameNum'+index+'">Remove</button></li>'
                );
                const removeBtnId = "#removeGameNum"+index;
                $(removeBtnId).click(function(event) {
                    removeGame(game.gameName);
                });

            };
        }).catch((error)=>console.log(error));
    })
}

function enterGame(i_gameName) {
    console.log("Entering game: "+i_gameName);
    $.ajax({
        url:'http://localhost:8080/EnterGameServlet',
        type:"POST",
        data: {gameName:i_gameName},
        success: (data) =>{
            console.log(data);
            if (data !== "null") {
                const url = JSON.parse(data.responseText);
                console.log(url.content);
                window.location.href = url.content;
            }
    },
        error: error => {
            console.log(error);
            alert(error.responseText);
        }
    })
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
        setTimeout(()=>{
            document.getElementById("XmlErrMsg").innerText = "";
        },5000)
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