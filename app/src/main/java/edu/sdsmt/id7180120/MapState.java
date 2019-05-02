package edu.sdsmt.id7180120;

/**
 * @file
 * @brief Contains the class and methods to maintain MapState.
 */

import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * The type Map state.
 */
public class MapState {

    /**
     * The enum States.
     */
    public enum States {
        /**
        * Grey states.
        */
        Grey,
        /**
         * Blue states.
         */
        Blue,
        /**
         * Red states.
         */
        Red,
        /**
         * Green states.
         */
        Green,
        /**
         * Orange states.
         */
        Orange,
        /**
         * Exit states.
         */
        Exit}

    /**
     * The enum Directions.
     */
    public enum Directions {
        /**
        * Up directions.
        */
        Up,
        /**
         * Down directions.
         */
        Down,
        /**
         * Left directions.
         */
        Left,
        /**
         * Right directions.
         */
        Right,
        /**
         * Static directions.
         */
        Static}

    private States state = States.Grey;
    private Directions dir = Directions.Static;
    private final GameView view;
    private Boolean noEntry = false;
    private Boolean hasKey = false;

    /**
     * Instantiates a new Map state.
     *
     * @param v the v
     */
    MapState(GameView v) {
        view = v;
        view.setRoom(state);
    }

    /**
     * Instantiates a new Map state.
     *
     * @param v      the v
     * @param room   the room
     * @param direct the direct
     * @param key    the key
     * @param entry  the entry
     */
    MapState(GameView v, States room, Directions direct, Boolean key, Boolean entry) {
        view = v;
        state = room;
        dir = direct;
        hasKey = key;
        noEntry = entry;
        view.setRoom(state);
    }

    /**
     * On left.
     */
    // REBENITSCH: EXTENSION
    // onLeft, onRight, onUp, onDown, bounceRight, bounceLeft, bounceUp, bounceDown all controls
    // the movement of the player
    public void onLeft() {
        dir = Directions.Left;
        switch (state) {
            case Grey:
                Log.d(TAG, "Found the key!");
                state = States.Blue;
                hasKey = true;
                noEntry = false;
                break;

            case Red:
                state = States.Grey;
                noEntry = false;
                break;

            default:
                noEntry = true;
                break;
        }

       view.animatePlayer(-1, 0);
    }

    /**
     * On right.
     */
    public void onRight() {
        dir = Directions.Right;
        switch (state) {
            case Grey:
                state = States.Red;
                noEntry = false;
                break;

            case Blue:
                state = States.Grey;
                noEntry = false;
                break;

                // REBENITSCH: EXIT
            case Red:
                if (hasKey) {
                    state = States.Exit;
                    noEntry = false;
                    break;
                }

            default:
                noEntry = true;
                break;
        }

        view.animatePlayer(1, 0);
    }

    /**
     * On up.
     */
    public void onUp() {
        dir = Directions.Up;
        switch (state) {
            case Grey:
                state = States.Green;
                noEntry = false;
                break;

            case Orange:
                state = States.Grey;
                noEntry = false;
                break;

            default:
                noEntry = true;
                break;
        }
        view.animatePlayer(0, -1);
    }

    /**
     * On down.
     */
    public void onDown() {
        dir = Directions.Down;
        switch (state) {
            case Grey:
                state = States.Orange;
                noEntry = false;
                break;

            case Green:
                state = States.Grey;
                noEntry = false;
                break;

            default:
                noEntry = true;
                break;
        }
        view.animatePlayer(0, 1);
    }

    /**
     * Sets state.
     */
    public void setState() {
        if (noEntry) {
            switch (dir) {
                case Up:
                    bounceDown();
                    break;

                case Down:
                    bounceUp();
                    break;

                case Left:
                    bounceRight();
                    break;

                case Right:
                    bounceLeft();
                    break;

                    default:
                        break;
            }
        }

        else {
            view.setRoom(state);

            switch (dir) {
                case Up:
                    view.setPlayerPosition(0, 1);
                    break;

                case Down:
                    view.setPlayerPosition(0, -1);
                    break;

                case Left:
                    view.setPlayerPosition(1, 0);
                    break;

                case Right:
                    view.setPlayerPosition(-1, 0);
                    break;

                default:
                    break;
            }
        }
    }

    /**
     * Animate player to bounce right.
     */
    private void bounceRight() { view.animatePlayer(1, 0); }

    /**
     * Animate player to bounce left.
     */
    private void bounceLeft() { view.animatePlayer(-1, 0); }

    /**
     * Animate player to bounce up.
     */
    private void bounceUp() { view.animatePlayer(0, -1); }

    /**
     * Animate player to bounce down.
     */
    private void bounceDown() { view.animatePlayer(0, 1); }

    /**
     * Gets room.
     *
     * @return the room
     */
    public States getRoom() { return this.state; }

    /**
     * Gets dir.
     *
     * @return the dir
     */
    public Directions getDir() { return this.dir; }

    /**
     * Gets has key.
     *
     * @return the has key
     */
    public Boolean getHasKey() { return this.hasKey; }

    /**
     * Gets no entry.
     *
     * @return the no entry
     */
    public Boolean getNoEntry() { return this.noEntry; }
}