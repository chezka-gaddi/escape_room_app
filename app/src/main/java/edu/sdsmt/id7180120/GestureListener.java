package edu.sdsmt.id7180120;

/**
 * @file
 * @brief Contains the GestureLister handler.
 */

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import static android.support.constraint.Constraints.TAG;


/**
 * The type Gesture listener.
 */
public class GestureListener extends GestureDetector.SimpleOnGestureListener {

    private final SwipeListener swipeListener;

    /**
     * Instantiates a new Gesture listener.
     *
     * @param listener the listener
     */
    public GestureListener(SwipeListener listener) {
        swipeListener = listener;
    }

    /**
     * Called onFling motion, determines and returns direction
     * @param e1 first touch event
     * @param e2 last touch event
     * @param velocityX velocity in the x direction
     * @param velocityY velocity in the y direction
     * @return direction of fling
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        float x1 = e1.getX();
        float y1 = e1.getY();

        float x2 = e2.getX();
        float y2 = e2.getY();
        Log.d(TAG, "Finding a touch");

        Direction direction = getDirection(x1, y1, x2, y2);
        return onSwipe(direction);
    }

    /** Override this method. The Direction enum will tell you how the user swiped. */
    private boolean onSwipe(Direction direction){
        switch (direction) {
            case left:
                swipeListener.onLeft();
                break;

            case right:
                swipeListener.onRight();
                break;

            case up:
                Log.d(TAG, "Swipe Up");
                swipeListener.onUp();
                break;

            case down:
                Log.d(TAG, "Swipe Down");
                swipeListener.onDown();
                break;
        }
        return false;
    }

    /**
     * Given two points in the plane p1=(x1, x2) and p2=(y1, y1), this method returns the direction
     * that an arrow pointing from p1 to p2 would have.
     * @param x1 the x position of the first point
     * @param y1 the y position of the first point
     * @param x2 the x position of the second point
     * @param y2 the y position of the second point
     * @return the direction
     */
    private Direction getDirection(float x1, float y1, float x2, float y2){
        double angle = getAngle(x1, y1, x2, y2);
        return Direction.fromAngle(angle);
    }

    /**
     * Finds the angle between two points in the plane (x1,y1) and (x2, y2). The angle is measured
     * with 0/360 being the X-axis to the right, angles increase counter clockwise.
     * @param x1 the x position of the first point
     * @param y1 the y position of the first point
     * @param x2 the x position of the second point
     * @param y2 the y position of the second point
     * @return the angle between two points
     */
    private double getAngle(float x1, float y1, float x2, float y2) {

        double rad = Math.atan2(y1-y2,x2-x1) + Math.PI;
        return (rad*180/Math.PI + 180)%360;
    }

    /**
     * The enum Direction.
     */
    public enum Direction{
        /**
         * Up direction.
         */
        up,
        /**
         * Down direction.
         */
        down,
        /**
         * Left direction.
         */
        left,
        /**
         * Right direction.
         */
        right;

        /**
         * Returns a direction given an angle.
         * Directions are defined as follows:
         * <p>
         * Up: [45, 135]
         * Right: [0,45] and [315, 360]
         * Down: [225, 315]
         * Left: [135, 225]
         *
         * @param angle an angle from 0 to 360 - e
         * @return the direction of an angle
         */
        static Direction fromAngle(double angle){
            if(inRange(angle, 45, 135)){
                return Direction.up;
            }
            else if(inRange(angle, 0,45) || inRange(angle, 315, 360)){
                return Direction.right;
            }
            else if(inRange(angle, 225, 315)){
                return Direction.down;
            }
            else{
                return Direction.left;
            }

        }

        /**
         * @param angle an angle
         * @param init the initial bound
         * @param end the final bound
         * @return returns true if the given angle is in the interval [init, end).
         */
        private static boolean inRange(double angle, float init, float end){
            return (angle >= init) && (angle < end);
        }
    }

    /**
     * The interface Swipe listener.
     */
    interface SwipeListener {

        /**
         * On left.
         */
        void onLeft();

        /**
         * On right.
         */
        void onRight();

        /**
         * On up.
         */
        void onUp();

        /**
         * On down.
         */
        void onDown();
    }
}
