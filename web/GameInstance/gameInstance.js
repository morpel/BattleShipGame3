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
    newCell.innerHTML = content;
    let prefix;
    if (parentRow.id === "sr"){
        prefix = "s";
    } else{
        prefix = "a";
    }
    newCell.id = prefix + row +","+col;
    newCell.classList.add("cell");
    return newCell;
}

function hendelHitResult(hitResJson) {
    let hitRes = JSON.parse(hitResJson);
    let cellId = "a"+hitRes.hitPoint.x + "," + hitRes.hitPoint.y;
    console.log(cellId);
    let cell = document.getElementById(cellId);
    console.log(hitRes);
    cell.innerHTML = hitRes.sign;
}

function addOnClickEvents(attackingCell) {
    attackingCell.onclick = (e)=>{
            console.log(e.srcElement.id);
            $.ajax({
                type: 'POST',
                url: `http://localhost:8080/PlayerMadeMoveServlet`,
                data: {point:e.srcElement.id},
                success: (data) => {hendelHitResult(data)},
                error: (data) => {console.log(data)}
            })
    }
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
            createCell(shipsRow,i,j,processedData.ships[i][j]);
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