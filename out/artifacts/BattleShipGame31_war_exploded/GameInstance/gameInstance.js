const INTERVAL_TIME = 2000;
let isPageBlocked;
let waitForOtherPlayerIntervalId;
let isOtherPlayerLeftTheGame = false;
let statsAndMovesIntervalId;

function userLoggedOut(data) {
    console.log(data);
    if (data !== "null") {
        const url = JSON.parse(data);
        console.log(url.content);
        window.location.href = url.content;
    }
}

function finishGame() {
    $.ajax({
        type: 'POST',
        url: `http://localhost:8080/FinishGameServlet`,
        data: {},
        success: (data) => {console.log(data)},
        error: (data) => {console.log(data)}
    })
}

function logoutUser(){
    finishGame();
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
            var img = document.createElement('img');
            img.src = "mineImg.jpg";
            img.className = "mineImg";
            newCell.appendChild(img);
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
            case("mine"):{
                if(prefix === "a"){
                    let tmpCellId = "s" + hitRes.hitPoint.x + "," + hitRes.hitPoint.y;
                    let tmpCell = document.getElementById(tmpCellId);
                    if(tmpCell.className = "myShip cell") {
                        tmpCell.className = "shipHit cell";
                    }
                    alert("Oh No, You hit a mine!");
                }
            }
            case("none"): {
                cell.className = "noHit cell";
                console.log(cell.classList);
                break;
            }
            case("ship"): {
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

function isGoodCellForMine(i_row, i_col) {
    return new Promise((resolve,reject)=>{
        $.ajax({
            type: 'POST',
            url: `http://localhost:8080/IsGoodPlaceForMineServlet`,
            data: {row:i_row, col:i_col},
            success: (data) => {
                if (data === "yes"){
                    resolve(true);
                } else{
                    resolve(false);
                }
            },
            error: (data) => {reject(data)}
        })
    })
}

async function addDragEvents(shipCell,i_row,i_col) {
    await isGoodCellForMine(i_row,i_col).then((isGoodCellForMineBool)=>{
        if(isGoodCellForMineBool){
            if(shipCell.children[0] === undefined) {
                shipCell.ondrop = drop;
                shipCell.ondragover = allowDrop;
            }
        } else {
            shipCell.ondrop = null;
            shipCell.ondragover = null;
        }
    }).catch((error)=>console.log(error));
}

function addMines(minesAmount) {
    let minesDiv = $("#mines");
    minesDiv.innerHTML = "";
    for (var i = 0; i < minesAmount; i++ ){
        minesDiv.append('<img id="mineImg'+i+'" src="mineImg.jpg" class="mineImg" draggable="true" ondragstart="drag(event)">');
    }
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
            addDragEvents(shipCell,i,j);
        }
    }
    addMines(processedData.minesAmount);
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

function finishAndGoBackToLobby() {
    finishGame();
    window.location.href = "/Lobby/lobby.html";
}

function getPlayerStats() {
    $.ajax({
        type: 'POST',
        url: `http://localhost:8080/GetStatsServlet`,
        data: {},
        success: (data) => {
            console.log("stats: " + data);
            if (data !== "null") {
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
            } else if(!isOtherPlayerLeftTheGame){
                alert("Your Opponent Left The Game, You Won!!");
                finishAndGoBackToLobby();
                clearInterval(statsAndMovesIntervalId);
                isOtherPlayerLeftTheGame = true;
            }
        },
        error: (data) => {console.log(data)}
    });
}

function initPage() {
    getBoardsInfo();
    getPlayerStats();
    statsAndMovesIntervalId = setInterval( () => {
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
    },500);
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

function initWaitForPlayerInterval() {
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
}

$(document).ready(initWaitForPlayerInterval());

/*drag and drop funcs*/
function allowDrop(ev) {
    ev.preventDefault();
}

function drag(ev) {
    ev.dataTransfer.setData("text", ev.target.id);
}

function doWhenMineIsSat(data) {
    checkWhoIsPlaying();
    alert(data);
}

function drop(ev) {
    ev.preventDefault();
    var data = ev.dataTransfer.getData("text");
    ev.target.appendChild(document.getElementById(data));
    let cell = ev.target;
    let parent = cell.parentNode;
    let newCell = cell.cloneNode(true);
    newCell.childNodes.forEach((child)=>{child.draggable=false});
    parent.replaceChild(newCell,cell);
    $.ajax({
        type: 'POST',
        url: `http://localhost:8080/PlayerSatMineServlet`,
        data: {point:ev.target.id},
        success: (data) => {
            doWhenMineIsSat(data);
        },
        error: (data) => {console.log(data)}
    })
}