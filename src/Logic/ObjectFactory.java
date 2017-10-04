
package Logic;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the Logic package.
 * <p>An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups.  Factory methods for each of these are
 * provided in this class.
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: Logic
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link BattleShipGame }
     */
    public BattleShipGame createBattleShipGame() {
        return new BattleShipGame();
    }

    /**
     * Create an instance of {@link BattleShipGame.Boards }
     */
    public BattleShipGame.Boards createBattleShipGameBoards() {
        return new BattleShipGame.Boards();
    }

    /**
     * Create an instance of {@link BattleShipGame.Boards.Board }
     */
    public BattleShipGame.Boards.Board createBattleShipGameBoardsBoard() {
        return new BattleShipGame.Boards.Board();
    }

    /**
     * Create an instance of {@link BattleShipGame.Boards.Board.Ship }
     */
    public BattleShipGame.Boards.Board.Ship createBattleShipGameBoardsBoardShip() {
        return new BattleShipGame.Boards.Board.Ship();
    }

    /**
     * Create an instance of {@link BattleShipGame.ShipTypes }
     */
    public BattleShipGame.ShipTypes createBattleShipGameShipTypes() {
        return new BattleShipGame.ShipTypes();
    }

    /**
     * Create an instance of {@link BattleShipGame.Mine }
     */
    public BattleShipGame.Mine createBattleShipGameMine() {
        return new BattleShipGame.Mine();
    }

    /**
     * Create an instance of {@link BattleShipGame.Boards.Board.Ship.Position }
     */
    public BattleShipGame.Boards.Board.Ship.Position createBattleShipGameBoardsBoardShipPosition() {
        return new BattleShipGame.Boards.Board.Ship.Position();
    }

    /**
     * Create an instance of {@link BattleShipGame.ShipTypes.ShipType }
     */
    public BattleShipGame.ShipTypes.ShipType createBattleShipGameShipTypesShipType() {
        return new BattleShipGame.ShipTypes.ShipType();
    }

}
