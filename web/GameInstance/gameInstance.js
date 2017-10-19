const INTERVAL_TIME = 2000;
let isPageBlocked;
let waitForOtherPlayerIntervalId;

function userLoggedOut(data) {
    console.log(data);
    if (data !== "null") {
        const url = JSON.parse(data);
        console.log(url.content);
        window.location.href = url.content;
    }
}

function logoutUser(){
    $.ajax({
        type: 'POST',
        url: `http://localhost:8080/FinishGameServlet`,
        data: {},
        success: (data) => {console.log(data)},
        error: (data) => {console.log(data)}
    });
    $.ajax({
        type: 'POST',
        url: `http://localhost:8080/LogoutServlet`,
        data: {},
        success: (data) => userLoggedOut(data),
        error: (data) => {console.log(data)}
    });
    event.preventDefault();
}

function createCell(parentRow, row, col, content) {
    var newCell = parentRow.insertCell(col);
    let prefix;
    if (parentRow.id === "sr"){
        prefix = "s";
    } else{
        prefix = "a";
    }
    newCell.id = prefix + col +","+row;
    switch(content){
        case(null):{
            newCell.className = "sea cell";
            break;
        } case("#"): {
            newCell.className = "myShip cell";
            break;
        } case("X"):{
            newCell.className = "noHit cell";
            break;
        }case("V"):{
            newCell.className = "shipHit cell";
            break;
        }case("M"):{
            newCell.className = "mine cell";
            break;
        }default:{
            console.log("I cant understand what u hit " + content );
        }
    }
    return newCell;
}

function hendelHitResult(hitResJson, prefix) {
    if(hitResJson !== "null") {
        let hitRes = JSON.parse(hitResJson);
        let cellId = prefix + hitRes.hitPoint.x + "," + hitRes.hitPoint.y;
        console.log(cellId);
        let cell = document.getElementById(cellId);
        console.log(hitRes);
        switch (hitRes.hitResult) {
            case("none"): {
                cell.className = "noHit cell";
                console.log(cell.classList);
                break;
            }
            case("ship" || "mine"): {
                cell.className = "shipHit cell";
                break;
            }
            default: {
                console.log("I cant understand what u hit");
            }
        }
        cell.removeEventListener("click", cellClickEvent,true);
    }
}

function addOnClickEvents(attackingCell) {
    if(attackingCell.classList.contains("sea")) {
        attackingCell.onclick = cellClickEvent;
    }
}

let cellClickEvent = (e)=>{
    e = e || window.event;
    let cellId = e.srcElement.id.split(",")[0] + "," + e.srcElement.id.split(",")[1];
    console.log("!!!!!!!!!!!!!" + cellId);
    let cell = document.getElementById(cellId);
    let new_element = cell.cloneNode(true);
    cell.parentNode.replaceChild(new_element, cell);
    console.log(e.srcElement.id);
    $.ajax({
        type: 'POST',
        url: `http://localhost:8080/PlayerMadeMoveServlet`,
        data: {point:e.srcElement.id},
        success: (data) => {hendelHitResult(data,"a")},
        error: (data) => {console.log(data)}
    });
    checkWhoIsPlaying();
}

function drawBoards(data) {
    let processedData = JSON.parse(data);
    var shipsTable = document.getElementById("shipsTable");
    var attackingTable = document.getElementById("attackingTable");
    shipsTable.innerHTML = "";
    attackingTable.innerHTML = "";
    let boardSize = processedData.boardSize;
    console.log(processedData);
    for (var i=0;i<boardSize;i++){
        var shipsRow = shipsTable.insertRow(i);
        shipsRow.id = "sr";
        var attackingRow = attackingTable.insertRow(i);
        attackingRow.id = "ar"
        for (var j=0;j<boardSize;j++){
            let shipCell = createCell(shipsRow,i,j,processedData.ships[i][j]);
            let attackingCell = createCell(attackingRow,i,j,processedData.attacking[i][j]);
            addOnClickEvents(attackingCell);
        }
    }
}

function showOrHideScreen(data) {
    if (data !== "true"){
        if(!isPageBlocked) {
            console.log("covering");
            $("#cover").block({
                css: {backgroundColor: '#f80', color: '#fff'},
                message: '<h3> The Other Player Makes A Move</h3>'
            });
            isPageBlocked = true;
        }
    } else{
        if(isPageBlocked) {
            console.log("un-covering");
            $("#cover").unblock();
            isPageBlocked = false;
        }
    }
}

function checkWhoIsPlaying() {
    $.ajax({
        type: 'POST',
        url: `http://localhost:8080/CheckIfIPlayServlet`,
        data: {},
        success: (data) => showOrHideScreen(data),
        error: (data) => {console.log(data)}
    });
}

function getBoardsInfo() {
    $.ajax({
        type: 'POST',
        url: `http://localhost:8080/BoardsInfoServlet`,
        data: {},
        success: (data) => drawBoards(data),
        error: (data) => {console.log(data)}
    });
    checkWhoIsPlaying();
}

function getPlayerStats() {
    $.ajax({
        type: 'POST',
        url: `http://localhost:8080/GetStatsServlet`,
        data: {},
        success: (data) => {
            console.log("stats: " + data);
            let stats = JSON.parse(data);
            console.log(stats);
            $("#playerScoreInput").text(stats.myScore);
            $("#hitsCounterInput").text(stats.myHits);
            $("#missCounterInput").text(stats.myMisses);
            $("#minesLeftInput").text(stats.minesLeft);

            $("#opponentScoreInput").text(stats.opponentScore);
            $("#gameTypeInput").text(stats.gameType);
            $("#avgMoveTimeInput").text(stats.avgMoveTime);
            $("#shipsLeftInput").text(stats.shipsLeftToSink);
        },
        error: (data) => {console.log(data)}
    });
}

function initPage() {
    getBoardsInfo();
    getPlayerStats();
    setInterval( () => {
        $.ajax({
            type: 'POST',
            url: `http://localhost:8080/CheckForNewMovesServlet`,
            data: {},
            success: (data) => {
                hendelHitResult(data, "s")
            },
            error: (data) => {console.log(data)}
        });
        checkWhoIsPlaying();
        getPlayerStats();
    },2000);
}

function checkIfOtherPlayerEntered(data) {
    if (data !== "true"){
        console.log("blocking");
        if(isPageBlocked === false || isPageBlocked === undefined){
            $("#cover").block({
                css: {backgroundColor: '#f78', color: '#fff'},
                message: '<h3> Waiting for other player to enter the game...</h3>'
            });
            isPageBlocked = true;
        }
    } else{
        console.log("unblocking 1");
        if(isPageBlocked === true || isPageBlocked === undefined){
            console.log("unblocking 2");
            $("#cover").unblock();
            isPageBlocked = false;
            clearInterval(waitForOtherPlayerIntervalId);
            initPage();
        }
    }
}

$(document).ready(()=>{
    isPageBlocked = undefined;
    waitForOtherPlayerIntervalId = setInterval(()=>{
        $.ajax({
            type: 'POST',
            url: `http://localhost:8080/WaitForOtherPlayerServlet`,
            data: {},
            success: (data) => {
                console.log(data);
                checkIfOtherPlayerEntered(data);
            },
            error: (data) => {console.log(data)}
        });
    },3000);
});
// $(document).ready(getBoardsInfo);
// $(document).ready(getPlayerStats);
// $(document).ready(
//     setInterval( () => {
//     $.ajax({
//         type: 'POST',
//         url: `http://localhost:8080/CheckForNewMovesServlet`,
//         data: {},
//         success: (data) => {
//             console.log("new hit on me: " + data);
//             hendelHitResult(data, "s")
//         },
//         error: (data) => {console.log(data)}
//     });
//     checkWhoIsPlaying();
//     getPlayerStats();
//     },2000));