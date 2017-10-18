const INTERVAL_TIME = 2000;

function userLoggedOut(data) {
    console.log(data);
    if (data !== "null") {
        const url = JSON.parse(data.responseText);
        console.log(url.content);
        window.location.href = url.content;
    }
}

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

function createCell(parentRow, row, col, content) {
    var newCell = parentRow.insertCell(col);
    console.log(content);
    let prefix;
    if (parentRow.id === "sr"){
        prefix = "s";
    } else{
        prefix = "a";
    }
    newCell.id = prefix + row +","+col;
    switch(content){
        case(null):{
            newCell.className = "sea cell"
            break;
        } case("#"): {
        newCell.className = "myShip cell"
        break;
        } default:{
            console.log("I cant understand what u hit " + content );
        }
    }
    return newCell;
}

function hendelHitResult(hitResJson) {
    let hitRes = JSON.parse(hitResJson);
    let cellId = "a"+hitRes.hitPoint.x + "," + hitRes.hitPoint.y;
    console.log(cellId);
    let cell = document.getElementById(cellId);
    console.log(hitRes);
    switch(hitRes.hitResult){
        case("none"):{
            cell.className = "noHit cell"
            // cell.classList.add("noHit");
            console.log(cell.classList);
            break;
        } case("ship" || "mine"): {
        cell.className = "shipHit cell"
        // cell.classList.add("shipHit");
            break;
        } default:{
            console.log("I cant understand what u hit");
        }
    }
    cell.removeEventListener("click",cellClickEvent);
}

function addOnClickEvents(attackingCell) {
    attackingCell.onclick = cellClickEvent;
}

function cellClickEvent(e){
        e = e || window.event;
        console.log(e.srcElement.id);
        $.ajax({
            type: 'POST',
            url: `http://localhost:8080/PlayerMadeMoveServlet`,
            data: {point:e.srcElement.id},
            success: (data) => {hendelHitResult(data)},
            error: (data) => {console.log(data)}
        })
}

function drawBoards(data) {
    let processedData = JSON.parse(data);
    var shipsTable = document.getElementById("shipsTable")//$("#shipsTable");
    var attackingTable = document.getElementById("attackingTable");
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

$(document).ready(function () {
    $.ajax({
        type: 'POST',
        url: `http://localhost:8080/BoardsInfoServlet`,
        data: {},
        success: (data) => drawBoards(data),
        error: (data) => {console.log(data)}
    })
});