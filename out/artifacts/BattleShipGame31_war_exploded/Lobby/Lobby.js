const INTERVAL_TIME = 2000;
const NumOfProfilePics = 3;
const profilePicsKind = ".svg"
const Colors = getColorScheme();
var currrentGamesDisplayed=0;
let firstGamesRender = true;
let chachedGamesMap;
let currentLoggedInUser;




function removeGame(i_gameName) {
    $.ajax({
        url:'../DeleteGameServlet',
        type:"POST",
        data: {gameName:i_gameName},
        success: (data) => {
            alert(data);
        },
        error: (error) => {console.log(error)},
    })
}

function fireAnimation(selectorId,animationName,callback){

    //in case of fade-out anim
    if(animationName.includes("In")){

        $(`#${selectorId}`).css("visibility","visible");
    }
    if(callback){
        callback();
    }
    $(`#${selectorId}`).addClass(`animated ${animationName}`).one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function(){
        $(`#${selectorId}`).removeClass(`animated ${animationName}`);

        if(animationName.includes("Out")){

            $(`#${selectorId}`).css("visibility","hidden");
        }
        //in case of fade-in anim
        else
        {
            $(`#${selectorId}`).css("visibility","visible");
        }
    });

}
function toggleShowHideNewGameForm(event){
    const formVisibility = $("#addGameForm").css("visibility");
    if(        $("#newGameButton")[0].children[0].innerText === "close")
    {
        $("#gameNameInput").removeClass("gameNameInputLookStretchOut");
        $("#gameNameInput").addClass("gameNameInputLookNoStretchOut")
        $("#newGameButton").css("background-color",Colors.New);
        $("#newGameButton")[0].children[0].innerText = "add";

        fireAnimation("addGameForm","fadeOutDown")

    }
    else
    {
        $("#newGameButton").css("background-color",Colors.Close);
        $("#newGameButton")[0].children[0].innerText = "close";
        $("#gameNameInput").removeClass("gameNameInputLookNoStretchOut");
        $("#gameNameInput").addClass("gameNameInputLookStretchOut");

        fireAnimation("addGameForm","fadeInLeft");

    }
}
 function isPlayerOwnGame(i_game) {
    return i_game.creator === currentLoggedInUser;
}

 function renderCurrentGames(games) {

        $("#gamesList").empty();
        $.each(games, (index,game) => {
                if (isPlayerOwnGame(game)){
                    $("#gamesList").append(
                        '<li class="mdc-grid-tile textMaterial gameItemBackground" id="game'+index+'">' +
                        '<span><button class="deleteGameButton mdc-fab mdc-fab--mini material-icons " data-mdc-auto-init="MDCRipple" id="removeGameNum'+index+'">'+
                        '<span class="mdc-fab__icon">delete_forever</span></button></span>'+
                        '<div id="gameCubeFirstItem'+index+'">Game Name: ' +game.gameName+'</div>' +
                        '<div>Game Creator: ' +game.creator+'</div>' +
                        '<div>Board Size: ' +game.boardSize+'</div>' +
                        '<div>Game Type: ' +game.gameStyle+'</div>' +
                        '<div>Players Connected: ' +game.otherPlayerInGame+'</div>' +
                        '<div>---------------------------------</div>'+
                        '<div id="gameCubeLastItem'+index+'"> <button class="gameItemButtons colorGreen mdc-fab mdc-fab--mini material-icons " data-mdc-auto-init="MDCRipple" id="enterGameNum'+index+'">' +
                        '<span class="mdc-fab__icon">' +'directions_boat'+
                        '</span></button><span class="enterGameButtonLabel">Enter</span></div>' +
                        '</li>'
                    );
                    const removeBtnId = "#removeGameNum"+index;
                    $(removeBtnId).click(function(event) {
                        removeGame(game.gameName);

                    });

                    const enterBtnId = "#enterGameNum"+index;
                    $(enterBtnId).click(function(event) {
                        enterGame(game.gameName);
                    });
                }
                else{
                    $("#gamesList").append(
                        '<li class="mdc-grid-tile textMaterial gameItemBackground" id="game'+index+'">' +
                        '<div id="gameCubeLineSpace">' +
                        '<div id="gameCubeFirstItem'+index+'">Game Name: ' +game.gameName+'</div>' +
                        '<div>Game Creator: ' +game.creator+'</div>' +
                        '<div>Board Size: ' +game.boardSize+'</div>' +
                        '<div>Game Type: ' +game.gameStyle+'</div>' +
                        '<div>Players Connected: ' +game.otherPlayerInGame+'</div>' +
                        '<div>---------------------------------</div>'+
                        '<div id="gameCubeLastItem'+index+'"> <button class="gameItemButtons colorGreen mdc-fab mdc-fab--mini material-icons " data-mdc-auto-init="MDCRipple" id="enterGameNum'+index+'">' +
                        '<span class="mdc-fab__icon">' +'directions_boat'+
                        '</span></button><span class="enterGameButtonLabel">Enter</span></div>' +
                        '</li>'
                    );
                    const enterBtnId = "#enterGameNum"+index;
                    $(enterBtnId).click(function(event) {
                        enterGame(game.gameName);
                    });
                }
            })
}

function enterGame(i_gameName) {
    console.log("Entering game: "+i_gameName);
    $.ajax({
        url:'../EnterGameServlet',
        type:"POST",
        data: {gameName:i_gameName},
        success: (data) =>{
            console.log(data);
            if (data !== "null") {
                const url = JSON.parse(data);
                console.log(url.content);
                window.location.href = "../" + url.content;
            }
    },
        error: error => {
            console.log(error);
            alert(error.responseText);
        }
    })
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
        $("#usersList").append(`<li class="mdc-list-item mdc-list-item-heightFix"><img class="mdc-list-item__start-detail profilePic" src=${profilePicPath}> <h2 class="bold">${user.toLocaleUpperCase()}</h2></li>`);
        profilePicPath = randProfilePic();
    })
}
function renderGamesAndUsers(data){
    const gamesAndUsersObj = JSON.parse(data);
    currentLoggedInUser = gamesAndUsersObj.userName;
    if (gamesAndUsersObj !== null && gamesAndUsersObj!==undefined){
        renderCurrentGames(gamesAndUsersObj.games);
        renderLoggedinUsers(gamesAndUsersObj.users);
    }
}

function userLoggedOut(data) {
    if (data !== "null") {
        const url = JSON.parse(data.responseText);
        window.location.href = "../" + url.content;
    }
}

function printXmlError(data) {
    if (data !== "null" && data!==undefined) {
        const message = JSON.parse(data);
        if(message.XMLValidityMsg !== undefined) {
            alert(message.XMLValidityMsg);
        } else{
            alert("Game Loaded Successfully");
        }
    }
}


function logoutUser(){
    $.ajax({
        type: 'POST',
        url: `../LogoutServlet`,
        data: {},
        success: (data) => userLoggedOut(data),
        error: (data) => {console.log(data)}
    });
    event.preventDefault();
}

$(document).ready(function () {
    $('#gameNameSubmitButton').click(()=>{

        if($("#gameNameInput")[0].value===""){
            alert("Please enter a non-empty game name!");
            event.preventDefault();
        }
    })
    $("#logoutBtn").onclick = function(event){
            $.ajax({
                type: 'POST',
                url: `../LogoutServlet`,
                data: {},
                success: function (data) {
                    if (data !== undefined && data !== null) {
                        const url = JSON.parse(data);
                        window.location.href = "../" + url.content;
                    }
                },
                error: (data) => {console.log(data)}
            });
        event.preventDefault();
    }
        setInterval(()=>{
            $.ajax({
                url:'../CheckForXmlErrorsServlet',
                type:"POST",
                data: {},
                success: (data) => printXmlError(data),
                error: error => console.log(error)
            })
        },INTERVAL_TIME);
        setInterval(()=>{
                $.ajax({
                    url: `../GameServlet`,
                    type: "POST",
                    data: {},
                    success:(data) => renderGamesAndUsers(data) ,
                    error: (data) => userLoggedOut(data)
                })}
            ,INTERVAL_TIME);
});