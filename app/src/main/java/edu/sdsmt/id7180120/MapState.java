package edu.sdsmt.id7180120;

import android.util.Log;

import static android.content.ContentValues.TAG;

public class MapState {

    public enum States {Grey, Blue, Red, Green, Orange, Exit}
    public enum Directions {Up, Down, Left, Right, Static}

    private States state = States.Grey;
    private Directions dir = Directions.Static;
    private final GameView view;
    private Boolean noEntry = false;
    private Boolean hasKey = false;

    MapState(GameView v) {
        view = v;
        view.setRoom(state);
    }

    MapState(GameView v, States room, Directions direct, Boolean key, Boolean entry) {
        view = v;
        state = room;
        dir = direct;
        hasKey = key;
        noEntry = entry;
        view.setRoom(state);
    }

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

    private void bounceRight() { view.animatePlayer(1, 0); }

    private void bounceLeft() { view.animatePlayer(-1, 0); }

    private void bounceUp() { view.animatePlayer(0, -1); }

    private void bounceDown() { view.animatePlayer(0, 1); }

    public States getRoom() { return this.state; }

    public Directions getDir() { return this.dir; }

    public Boolean getHasKey() { return this.hasKey; }

    public Boolean getNoEntry() { return this.noEntry; }
}