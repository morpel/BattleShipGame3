package Logic;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.awt.*;
import java.io.*;
import java.nio.file.Path;
import java.util.*;

public class GameLogic {
    private final int PLAYERS_AMOUNT = 2;
    private BattleShipGame BSGameInputs;
    private Player[] players;
    private boolean isGameFinished;
    private boolean isGameLoaded;
    private Path XMLPath;
    private Player currentPlayer;
    private Player attackedPlayer;
    private int totalShipPointsOnBoard;
    private GameStatistics gameStats;
    private int totalMineForPlayer;
    private ArrayList<PlayersBoard> shipBoardsHistory;
    private ArrayList<PlayersBoard> AttackingBoardsHistory;
    private ArrayList<String> PlayerNameHistory;

    public void initGameComponents() {
        players = new Player[PLAYERS_AMOUNT];
        isGameFinished = false;
        isGameLoaded = false;
        for (int i = 0; i < PLAYERS_AMOUNT; i++) {
            players[i] = new Player("Player " + (i + 1), BSGameInputs.boards.board.get(i), BSGameInputs.shipTypes, BSGameInputs.boardSize, BSGameInputs.mine.amount);
        }
        currentPlayer = players[0];
        attackedPlayer = players[1];
        totalMineForPlayer = BSGameInputs.mine.amount;
        totalShipPointsOnBoard = 0;
        calcTotalShipPointsOnBoard();
        gameStats = new GameStatistics();
        shipBoardsHistory = new ArrayList<>();
        AttackingBoardsHistory = new ArrayList<>();
        PlayerNameHistory = new ArrayList<>();
        gameStats.startWatch();
        initHTMLSessions();
    }

    private void initHTMLSessions() {

    }

    public boolean isGameLoaded() {
        return isGameLoaded;
    }

    public Path getXMLPath(){
        return XMLPath;
    }

    public int getTotalMineForPlayer() {
        return totalMineForPlayer;
    }

    public Player[] getPlayers() {
        return players;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public GameStatistics getGameStats() {
        return gameStats;
    }

    public int getBoardSize() {
        return BSGameInputs.boardSize;
    }

    public Player getAttackedPlayer() {
        return attackedPlayer;
    }

    public void setXMLPath(Path pathFromUser) {
        XMLPath = pathFromUser;
    }

    public void setGameFinished(boolean gameFinished) {
        isGameFinished = gameFinished;
    }

    public boolean checkIfPlayerCanAffordMine() {
        return (currentPlayer.getMinesLeft() > 0);
    }

    private void calcTotalShipPointsOnBoard() {
        for (BoardComponent comp : players[0].getMyComponents()) {
            if (comp.getCompKind().equals(Cell.BoardObjects.ship)) {
                totalShipPointsOnBoard += ((Ship) comp).getOnBoardParts();
            }
        }
    }

    public boolean isGameFinished() {
        return isGameFinished;
    }

    public boolean initGameFromXML() throws FileNotFoundException, NotXMLFileException, JAXBException {
        FileInputStream inputStream = null;

        if (XMLPath!=null) {
            inputStream = new FileInputStream(XMLPath.toString());
            BSGameInputs = deserializeFrom(inputStream);
            if (BSGameInputs != null) {
                isGameLoaded = true;
            }
        }

        return isGameLoaded;
    }

    private BattleShipGame deserializeFrom(InputStream inputStream) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
        Unmarshaller u = jc.createUnmarshaller();
        return (BattleShipGame) u.unmarshal(inputStream);
    }

    public PlayersBoard getHistoryBoard(int index, boolean isAttackingBoard){
        if (shipBoardsHistory.size() != 0) {
            if (isAttackingBoard) {
                return AttackingBoardsHistory.get(index);
            } else {
                return shipBoardsHistory.get(index);
            }
        }
        return null;
    }

    public Cell.BoardObjects checkMove(Point userMove) {
        Cell.BoardObjects res = Cell.BoardObjects.none;
        BoardComponent attackedComp = attackedPlayer.checkIfHitMe(userMove);
        gameStats.updateMoveCounter();
        if (attackedComp != null) {
            res = attackedComp.getCompKind();
            attackedComp.action(currentPlayer, attackedPlayer, userMove);
        }
        else{
            currentPlayer.addNewMove(userMove,false,0);
            attackedPlayer.addNewHitInMyBoard(userMove,false);
        }
        maintainHistory();
        return res;
    }

    private void maintainHistory() {
        shipBoardsHistory.add(new PlayersBoard(currentPlayer.getMyShipBoard()));
        AttackingBoardsHistory.add(new PlayersBoard(currentPlayer.getMyAttackingBoard()));
        PlayerNameHistory.add(currentPlayer.getName());
    }

    public boolean checkIfGameFinished() {
        if (currentPlayer.getStats().getNumHits() == totalShipPointsOnBoard) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkGameInputs() throws InvalidXMLInputsException {
        boolean res;

        res = checkInputBoardSize();
        res = res && checkShipTypesDetail();
        res = res && checkBoardComponentsPositions();
        res = res && checkIfShipsMatchShipTypes();
        isGameLoaded = res;

        return res;
    }

    private boolean checkShipTypesDetail() throws InvalidXMLInputsException{
        for (BattleShipGame.ShipTypes.ShipType shipType : BSGameInputs.shipTypes.shipType){
                if(shipType.length > BSGameInputs.boardSize || shipType.length < 1)
                    throw new InvalidXMLInputsException("Ship size in XML file is invalid");
                else if(shipType.amount < 0)
                    throw new InvalidXMLInputsException("Ship amount in XML file is invalid");
                else if(shipType.score < 0)
                    throw new InvalidXMLInputsException("Ship score in XML file is invalid");
        }

        return true;
    }

    private boolean checkIfShipsMatchShipTypes() throws InvalidXMLInputsException {
        String currID;
        int currAmount, totalShipAmount = 0;
        int shipCounterInCurrType = 0, totalShipsCounter = 0;
        for (BattleShipGame.ShipTypes.ShipType shipType : BSGameInputs.shipTypes.shipType) {
            currID = shipType.id;
            currAmount = shipType.amount;
            totalShipAmount += currAmount;
            for (BattleShipGame.Boards.Board board : BSGameInputs.boards.board) {
                for (BattleShipGame.Boards.Board.Ship ship : board.ship) {
                    if (currID.equals(ship.shipTypeId)) {
                        shipCounterInCurrType++;
                        totalShipsCounter++;
                        if (shipCounterInCurrType > currAmount) {
                            throw new InvalidXMLInputsException("The entered ships do not match the ship types!");
                        }
                    }
                }
                shipCounterInCurrType = 0;
            }
        }
        return totalShipAmount == totalShipsCounter / 2;
    }

    private boolean checkBoardComponentsPositions() throws InvalidXMLInputsException {
        ArrayList<Point> prevShipsPoints = new ArrayList<>();
        ArrayList<Point> currShipPoints = new ArrayList<>();
        boolean isGoodInput = true;

        for (BattleShipGame.Boards.Board board : BSGameInputs.boards.board) {
            for (BattleShipGame.Boards.Board.Ship ship : board.ship) {
                isGoodInput = checkShipPosition(ship, prevShipsPoints);
                if (isGoodInput) {
                    for (Point point : currShipPoints) {
                        prevShipsPoints.add(point);
                    }
                }
            }
            currShipPoints.clear();
            prevShipsPoints.clear();
        }
        return isGoodInput;
    }

    private boolean checkShipPosition(BattleShipGame.Boards.Board.Ship ship, ArrayList<Point> prevShipsPoints) throws InvalidXMLInputsException {
        String direction = ship.direction;
        int shipSize = 0;
        for (BattleShipGame.ShipTypes.ShipType shipType : BSGameInputs.shipTypes.shipType) {
            if (shipType.id.equals(ship.shipTypeId)) {
                shipSize = shipType.length;
                break;
            }
        }
        checkIfShipExceedsBoardSize(ship, direction, shipSize);
        ArrayList<Point> currShipPoints = getAllShipPoints(ship, direction, shipSize);
        return checkIfShipOverlapp(currShipPoints, prevShipsPoints);
    }

    private void checkIfShipExceedsBoardSize
            (BattleShipGame.Boards.Board.Ship ship, String direction, int shipSize) throws InvalidXMLInputsException {
        if (direction.indexOf("RIGHT") >= 0 || direction.equals("ROW"))
            if (ship.position.x + shipSize - 1 > getBoardSize())
                throw new InvalidXMLInputsException("Ships are exceeding the board's size");

        if (direction.indexOf("LEFT") >= 0)
            if (ship.position.x - shipSize < 0)
                throw new InvalidXMLInputsException("Ships are exceeding the board's size");

        if (direction.indexOf("UP") >= 0 || direction.equals("COLUMN"))
            if (ship.position.y + shipSize -1 > getBoardSize())
                throw new InvalidXMLInputsException("Ships are exceeding the board's size");

        if (direction.indexOf("DOWN") >= 0)
            if (ship.position.y - shipSize < 0)
                throw new InvalidXMLInputsException("Ships are exceeding the board's size");
    }

    private boolean checkIfShipOverlapp(ArrayList<Point> currShipPoints, ArrayList<Point> prevShipsPoints) throws InvalidXMLInputsException {
        for (Point currShipPoint : currShipPoints) {
            for (Point prevShipPoint : prevShipsPoints) {
                for (int i = -1; i < 2; i++) {
                    for (int j = -1; j < 2; j++) {
                        if (currShipPoint.x + i == prevShipPoint.x && currShipPoint.y + j == prevShipPoint.y) {
                            throw new InvalidXMLInputsException("Ship are overlapping each other");
                        }
                    }
                }
            }
        }

        return true;
    }

    private ArrayList<Point> getAllShipPoints(BattleShipGame.Boards.Board.Ship ship, String direction, int shipSize) {
        ArrayList<Point> res = new ArrayList<>();
        int row, col;
        if (direction.equals("ROW")) {
            row = 1;
            col = 0;
        } else {
            row = 0;
            col = 1;
        }
        for (int i = 0; i < shipSize; i++) {
            res.add(new Point(ship.position.x + (i * row), ship.position.y + (i * col)));
        }

        return res;
    }

    private boolean checkInputBoardSize() throws InvalidXMLInputsException {
        if (BSGameInputs.boardSize < 5 || BSGameInputs.boardSize > 20)
            throw new InvalidXMLInputsException("Board size is invalid");
        else
            return true;
    }

    public void switchPlayers() {
        Player tempPlayer = currentPlayer;
        currentPlayer = attackedPlayer;
        attackedPlayer = tempPlayer;
    }

    public void addMineToPlayer(Point location) {
        currentPlayer.addMine(location);
        maintainHistory();
    }

    public String getHistoryName(int index) {
        if (PlayerNameHistory.size() != 0){
            return PlayerNameHistory.get(index);
        }

        return null;
    }

    public boolean checkIfPlayerExist(String enterdName) {
//        TODO
        return true;
    }
}