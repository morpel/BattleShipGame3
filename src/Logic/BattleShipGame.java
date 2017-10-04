
package Logic;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GameType">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="BASIC"/>
 *               &lt;enumeration value="ADVANCE"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="boardSize" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="shipTypes">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="shipType" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="category">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;enumeration value="REGULAR"/>
 *                                   &lt;enumeration value="L_SHAPE"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                             &lt;element name="length" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                             &lt;element name="score" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                           &lt;/sequence>
 *                           &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="mine" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="boards">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="board" maxOccurs="2" minOccurs="2">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="ship" maxOccurs="unbounded">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="shipTypeId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="position">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;attribute name="x" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                                               &lt;attribute name="y" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="direction">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                             &lt;enumeration value="ROW"/>
 *                                             &lt;enumeration value="COLUMN"/>
 *                                             &lt;enumeration value="RIGHT_DOWN"/>
 *                                             &lt;enumeration value="RIGHT_UP"/>
 *                                             &lt;enumeration value="UP_RIGHT"/>
 *                                             &lt;enumeration value="DOWN_RIGHT"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "gameType",
        "boardSize",
        "shipTypes",
        "mine",
        "boards"
})
@XmlRootElement(name = "BattleShipGame")
public class BattleShipGame {

    @XmlElement(name = "GameType", required = true)
    protected String gameType;
    protected int boardSize;
    @XmlElement(required = true)
    protected BattleShipGame.ShipTypes shipTypes;
    protected BattleShipGame.Mine mine;
    @XmlElement(required = true)
    protected BattleShipGame.Boards boards;

    /**
     * Gets the value of the gameType property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getGameType() {
        return gameType;
    }

    /**
     * Sets the value of the gameType property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setGameType(String value) {
        this.gameType = value;
    }

    /**
     * Gets the value of the boardSize property.
     */
    public int getBoardSize() {
        return boardSize;
    }

    /**
     * Sets the value of the boardSize property.
     */
    public void setBoardSize(int value) {
        this.boardSize = value;
    }

    /**
     * Gets the value of the shipTypes property.
     *
     * @return possible object is
     * {@link BattleShipGame.ShipTypes }
     */
    public BattleShipGame.ShipTypes getShipTypes() {
        return shipTypes;
    }

    /**
     * Sets the value of the shipTypes property.
     *
     * @param value allowed object is
     *              {@link BattleShipGame.ShipTypes }
     */
    public void setShipTypes(BattleShipGame.ShipTypes value) {
        this.shipTypes = value;
    }

    /**
     * Gets the value of the mine property.
     *
     * @return possible object is
     * {@link BattleShipGame.Mine }
     */
    public BattleShipGame.Mine getMine() {
        return mine;
    }

    /**
     * Sets the value of the mine property.
     *
     * @param value allowed object is
     *              {@link BattleShipGame.Mine }
     */
    public void setMine(BattleShipGame.Mine value) {
        this.mine = value;
    }

    /**
     * Gets the value of the boards property.
     *
     * @return possible object is
     * {@link BattleShipGame.Boards }
     */
    public BattleShipGame.Boards getBoards() {
        return boards;
    }

    /**
     * Sets the value of the boards property.
     *
     * @param value allowed object is
     *              {@link BattleShipGame.Boards }
     */
    public void setBoards(BattleShipGame.Boards value) {
        this.boards = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * <p>
     * <p>The following schema fragment specifies the expected content contained within this class.
     * <p>
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="board" maxOccurs="2" minOccurs="2">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="ship" maxOccurs="unbounded">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="shipTypeId" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="position">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;attribute name="x" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
     *                                     &lt;attribute name="y" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                             &lt;element name="direction">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                   &lt;enumeration value="ROW"/>
     *                                   &lt;enumeration value="COLUMN"/>
     *                                   &lt;enumeration value="RIGHT_DOWN"/>
     *                                   &lt;enumeration value="RIGHT_UP"/>
     *                                   &lt;enumeration value="UP_RIGHT"/>
     *                                   &lt;enumeration value="DOWN_RIGHT"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "board"
    })
    public static class Boards {

        @XmlElement(required = true)
        protected List<BattleShipGame.Boards.Board> board;

        /**
         * Gets the value of the board property.
         * <p>
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the board property.
         * <p>
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getBoard().add(newItem);
         * </pre>
         * <p>
         * <p>
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link BattleShipGame.Boards.Board }
         */
        public List<BattleShipGame.Boards.Board> getBoard() {
            if (board == null) {
                board = new ArrayList<BattleShipGame.Boards.Board>();
            }
            return this.board;
        }


        /**
         * <p>Java class for anonymous complex type.
         * <p>
         * <p>The following schema fragment specifies the expected content contained within this class.
         * <p>
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="ship" maxOccurs="unbounded">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="shipTypeId" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="position">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;attribute name="x" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
         *                           &lt;attribute name="y" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                   &lt;element name="direction">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                         &lt;enumeration value="ROW"/>
         *                         &lt;enumeration value="COLUMN"/>
         *                         &lt;enumeration value="RIGHT_DOWN"/>
         *                         &lt;enumeration value="RIGHT_UP"/>
         *                         &lt;enumeration value="UP_RIGHT"/>
         *                         &lt;enumeration value="DOWN_RIGHT"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "ship"
        })
        public static class Board {

            @XmlElement(required = true)
            protected List<BattleShipGame.Boards.Board.Ship> ship;

            /**
             * Gets the value of the ship property.
             * <p>
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the ship property.
             * <p>
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getShip().add(newItem);
             * </pre>
             * <p>
             * <p>
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link BattleShipGame.Boards.Board.Ship }
             */
            public List<BattleShipGame.Boards.Board.Ship> getShip() {
                if (ship == null) {
                    ship = new ArrayList<BattleShipGame.Boards.Board.Ship>();
                }
                return this.ship;
            }


            /**
             * <p>Java class for anonymous complex type.
             * <p>
             * <p>The following schema fragment specifies the expected content contained within this class.
             * <p>
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="shipTypeId" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="position">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;attribute name="x" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
             *                 &lt;attribute name="y" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="direction">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *               &lt;enumeration value="ROW"/>
             *               &lt;enumeration value="COLUMN"/>
             *               &lt;enumeration value="RIGHT_DOWN"/>
             *               &lt;enumeration value="RIGHT_UP"/>
             *               &lt;enumeration value="UP_RIGHT"/>
             *               &lt;enumeration value="DOWN_RIGHT"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "shipTypeId",
                    "position",
                    "direction"
            })
            public static class Ship {

                @XmlElement(required = true)
                protected String shipTypeId;
                @XmlElement(required = true)
                protected BattleShipGame.Boards.Board.Ship.Position position;
                @XmlElement(required = true)
                protected String direction;

                /**
                 * Gets the value of the shipTypeId property.
                 *
                 * @return possible object is
                 * {@link String }
                 */
                public String getShipTypeId() {
                    return shipTypeId;
                }

                /**
                 * Sets the value of the shipTypeId property.
                 *
                 * @param value allowed object is
                 *              {@link String }
                 */
                public void setShipTypeId(String value) {
                    this.shipTypeId = value;
                }

                /**
                 * Gets the value of the position property.
                 *
                 * @return possible object is
                 * {@link BattleShipGame.Boards.Board.Ship.Position }
                 */
                public BattleShipGame.Boards.Board.Ship.Position getPosition() {
                    return position;
                }

                /**
                 * Sets the value of the position property.
                 *
                 * @param value allowed object is
                 *              {@link BattleShipGame.Boards.Board.Ship.Position }
                 */
                public void setPosition(BattleShipGame.Boards.Board.Ship.Position value) {
                    this.position = value;
                }

                /**
                 * Gets the value of the direction property.
                 *
                 * @return possible object is
                 * {@link String }
                 */
                public String getDirection() {
                    return direction;
                }

                /**
                 * Sets the value of the direction property.
                 *
                 * @param value allowed object is
                 *              {@link String }
                 */
                public void setDirection(String value) {
                    this.direction = value;
                }


                /**
                 * <p>Java class for anonymous complex type.
                 * <p>
                 * <p>The following schema fragment specifies the expected content contained within this class.
                 * <p>
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;attribute name="x" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
                 *       &lt;attribute name="y" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "")
                public static class Position {

                    @XmlAttribute(name = "x", required = true)
                    protected int x;
                    @XmlAttribute(name = "y", required = true)
                    protected int y;

                    /**
                     * Gets the value of the x property.
                     */
                    public int getX() {
                        return x;
                    }

                    /**
                     * Sets the value of the x property.
                     */
                    public void setX(int value) {
                        this.x = value;
                    }

                    /**
                     * Gets the value of the y property.
                     */
                    public int getY() {
                        return y;
                    }

                    /**
                     * Sets the value of the y property.
                     */
                    public void setY(int value) {
                        this.y = value;
                    }

                }

            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * <p>
     * <p>The following schema fragment specifies the expected content contained within this class.
     * <p>
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "amount"
    })
    public static class Mine {

        protected int amount;

        /**
         * Gets the value of the amount property.
         */
        public int getAmount() {
            return amount;
        }

        /**
         * Sets the value of the amount property.
         */
        public void setAmount(int value) {
            this.amount = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * <p>
     * <p>The following schema fragment specifies the expected content contained within this class.
     * <p>
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="shipType" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="category">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;enumeration value="REGULAR"/>
     *                         &lt;enumeration value="L_SHAPE"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                   &lt;element name="length" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                   &lt;element name="score" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                 &lt;/sequence>
     *                 &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "shipType"
    })
    public static class ShipTypes {

        @XmlElement(required = true)
        protected List<BattleShipGame.ShipTypes.ShipType> shipType;

        /**
         * Gets the value of the shipType property.
         * <p>
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the shipType property.
         * <p>
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getShipType().add(newItem);
         * </pre>
         * <p>
         * <p>
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link BattleShipGame.ShipTypes.ShipType }
         */
        public List<BattleShipGame.ShipTypes.ShipType> getShipType() {
            if (shipType == null) {
                shipType = new ArrayList<BattleShipGame.ShipTypes.ShipType>();
            }
            return this.shipType;
        }


        /**
         * <p>Java class for anonymous complex type.
         * <p>
         * <p>The following schema fragment specifies the expected content contained within this class.
         * <p>
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="category">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;enumeration value="REGULAR"/>
         *               &lt;enumeration value="L_SHAPE"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *         &lt;element name="length" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *         &lt;element name="score" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *       &lt;/sequence>
         *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "category",
                "amount",
                "length",
                "score"
        })
        public static class ShipType {

            @XmlElement(required = true)
            protected String category;
            protected int amount;
            protected int length;
            protected int score;
            @XmlAttribute(name = "id", required = true)
            protected String id;

            /**
             * Gets the value of the category property.
             *
             * @return possible object is
             * {@link String }
             */
            public String getCategory() {
                return category;
            }

            /**
             * Sets the value of the category property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setCategory(String value) {
                this.category = value;
            }

            /**
             * Gets the value of the amount property.
             */
            public int getAmount() {
                return amount;
            }

            /**
             * Sets the value of the amount property.
             */
            public void setAmount(int value) {
                this.amount = value;
            }

            /**
             * Gets the value of the length property.
             */
            public int getLength() {
                return length;
            }

            /**
             * Sets the value of the length property.
             */
            public void setLength(int value) {
                this.length = value;
            }

            /**
             * Gets the value of the score property.
             */
            public int getScore() {
                return score;
            }

            /**
             * Sets the value of the score property.
             */
            public void setScore(int value) {
                this.score = value;
            }

            /**
             * Gets the value of the id property.
             *
             * @return possible object is
             * {@link String }
             */
            public String getId() {
                return id;
            }

            /**
             * Sets the value of the id property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setId(String value) {
                this.id = value;
            }

        }

    }

}
