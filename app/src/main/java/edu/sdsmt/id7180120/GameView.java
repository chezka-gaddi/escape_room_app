package edu.sdsmt.id7180120;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import static android.support.constraint.Constraints.TAG;


public class GameView extends View {

    private static final String SUPER_STATE = "superState";
    private static final String PLAYER_X = "player_x";
    private static final String PLAYER_Y = "player_y";
    private static final String VELOCITY_X = "x_velocity";
    private static final String VELOCITY_Y = "y_velocity";
    private static final String ROOM = "room";
    private static final String DIR = "direction";
    private static final String HAS_KEY = "hasKey";
    private static final String NO_ENTRY = "noEntry";

    private MapState state;
    private boolean end = false;

    // Paint Colors
    private Paint roomPaint;
    private Paint playerPaint;

    private int width = -1;
    private int height = -1;
    private int minDim = -1;

    // Animation properties
    private long lastTime = 0;
    private float x_velocity = 0;
    private float y_velocity = 0;
    private float delta;

    // Player properties
    private float player_x = 0;
    private float player_y = 0;
    private float radius = 0;

    // Game room properties
    private float margin_left_x;
    private float margin_right_x;
    private float margin_top_y;
    private float margin_bottom_y;
    private float boxSize;

    private PlayerStrategy player;

    private final static float SCALE_IN_VIEW = 0.8f;

    private final GestureDetector gestureDetector = new GestureDetector(this.getContext(), new GestureListener(new GestureListener.SwipeListener() {
        @Override
        public void onLeft() {
            state.onLeft();
        }

        @Override
        public void onRight() {
            state.onRight();
        }

        @Override
        public void onUp() {
            state.onUp();
        }

        @Override
        public void onDown() {
            state.onDown();
        }
    }));

    private interface PlayerStrategy {
        void drawPlayer(Canvas canvas);
    }

    public class CirclePlayer implements PlayerStrategy {
        @Override
        public void drawPlayer(Canvas canvas) {
            radius = (width - boxSize) / 4;

            canvas.drawCircle(width/2 + player_x, height/2 + player_y, radius, playerPaint);
        }
    }

    public class SquarePlayer implements PlayerStrategy {
        @Override
        public void drawPlayer(Canvas canvas) {
            radius = (width - boxSize) / 4;

            canvas.drawRect(width/2 + player_x - radius, height/2 + player_y - radius, width/2 + player_x + radius, height/2 + player_y + radius, playerPaint);
        }
    }


    /**
     * Simple constructor to use when creating a view from code.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     */
    public GameView(Context context) {
        super(context);
        init();
    }

    /**
     * Constructor that is called when inflating a view from XML. This is called
     * when a view is being constructed from an XML file, supplying attributes
     * that were specified in the XML file. This version uses a default style of
     * 0, so the only attribute values applied are those in the Context's Theme
     * and the given AttributeSet.
     *
     * <p>
     * The method onFinishInflate() will be called after all children have been
     * added.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     * @param attrs   The attributes of the XML tag that is inflating the view.
     */
    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * Perform inflation from XML and apply a class-specific base style from a
     * theme attribute. This constructor of View allows subclasses to use their
     * own base style when they are inflating. For example, a Button class's
     * constructor would call this version of the super class constructor and
     * supply <code>R.attr.buttonStyle</code> for <var>defStyleAttr</var>; this
     * allows the theme's button style to modify all of the base view attributes
     * (in particular its background) as well as the Button class's attributes.
     *
     * @param context      The Context the view is running in, through which it can
     *                     access the current theme, resources, etc.
     * @param attrs        The attributes of the XML tag that is inflating the view.
     * @param defStyleAttr An attribute in the current theme that contains a
     *                     reference to a style resource that supplies default values for
     *                     the view. Can be 0 to not look for defaults.
     */
    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * Initializes the custom view, GameView.
     */
    private void init() {
        roomPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        state = new MapState(this);
        playerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        playerPaint.setColor(Color.YELLOW);

        lastTime = SystemClock.uptimeMillis();
        player = new SquarePlayer();
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        boxSize = (int)(minDim * SCALE_IN_VIEW);
        long time = SystemClock.uptimeMillis();
        delta = (time - lastTime) * 0.001f;
        lastTime = time;

        player_x += x_velocity * delta;
        player_y += y_velocity * delta;

        if (width == -1) {
            width = getWidth();
            height = getHeight();
            minDim = width < height ? width : height;
        }

        checkBoundaries();

        drawRoom(canvas);
        player.drawPlayer(canvas);
        invalidate();
    }


    private void drawRoom(Canvas canvas) {
        margin_left_x = (width - boxSize) / 2;
        margin_top_y = (height - boxSize) / 2;
        margin_right_x = margin_left_x + boxSize;
        margin_bottom_y = margin_top_y + boxSize;

        canvas.drawRect(margin_left_x, margin_top_y, margin_right_x, margin_bottom_y, roomPaint);
    }


    public void animatePlayer(float x_vel, float y_vel) {
//        Log.d(TAG, "Animating Player");
        float MOVE_PER_SEC = 100000;
        x_velocity = x_vel * MOVE_PER_SEC * delta;
        y_velocity = y_vel * MOVE_PER_SEC * delta;
    }


    public void setPlayerPosition(int x, int y) {
        player_x = x * (margin_right_x - width/2 - radius);
        player_y = y * (margin_bottom_y - height/2 - radius);
    }


    public void setPlayerShape(String type) {
        switch(type) {
            case "circle":
                player = new CirclePlayer();
                break;

            case "square":
                player = new SquarePlayer();
                break;

            default:
                break;
        }
    }


    private void checkBoundaries() {
//        Log.d(TAG, "Checking Boundaries");
        if (player_y < 20 && player_y > -20 && player_x < 20 && player_x > -20) {
            x_velocity = 0;
            y_velocity = 0;
            player_x = 0;
            player_y = 0;
        }

        // Reached left boundary
        else if (width/2 + player_x - radius - 10 < margin_left_x) {
            state.setState();
        }

        //Reached right boundary
        else if (width/2 + player_x + radius + 10 > margin_right_x) {
            state.setState();
        }

        // Reached bottom boundary
        else if (height/2 + player_y + radius + 10 > margin_bottom_y) {
            state.setState();
        }

        // Reached top boundary
        else if (height/2 + player_y - radius - 10 < margin_top_y) {
            state.setState();
        }
    }


    // REBENITSCH: ROOM
    public void setRoom(MapState.States newState) {
        switch (newState) {
            case Grey:
                roomPaint.setColor(Color.GRAY);
                break;

            case Blue:
                roomPaint.setColor(Color.BLUE);
                break;

            case Red:
                roomPaint.setColor(Color.RED);
                break;

            case Orange:
                roomPaint.setColor(Color.MAGENTA);
                break;

            case Green:
                roomPaint.setColor(Color.GREEN);
                break;

            case Exit:
                Log.d(TAG, "Found the exit");
                roomPaint.setColor(Color.BLACK);
                end = true;
                break;
        }
    }


    public Boolean getEnd() { return end; }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;

            case MotionEvent.ACTION_UP:
                performClick();
                break;

            default:
                break;
        }
        return true;
    }


    @Override
    public boolean performClick() { return super.performClick(); }


    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(SUPER_STATE, super.onSaveInstanceState());
        bundle.putFloat(PLAYER_X, this.player_x);
        bundle.putFloat(PLAYER_Y, this.player_y);
        bundle.putFloat(VELOCITY_X, this.x_velocity);
        bundle.putFloat(VELOCITY_Y, this.y_velocity);

        MapState.States room = this.state.getRoom();
        MapState.Directions dir = this.state.getDir();
        Boolean hasKey = this.state.getHasKey();
        Boolean noEntry = this.state.getNoEntry();

        bundle.putSerializable(ROOM, room);
        bundle.putSerializable(DIR, dir);
        bundle.putBoolean(HAS_KEY, hasKey);
        bundle.putBoolean(NO_ENTRY, noEntry);

        return bundle;
    }


    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            this.player_x = bundle.getFloat(PLAYER_X);
            this.player_y = bundle.getFloat(PLAYER_Y);
            this.x_velocity = bundle.getFloat(VELOCITY_X);
            this.y_velocity = bundle.getFloat(VELOCITY_Y);

            MapState.States room = (MapState.States) bundle.getSerializable(ROOM);
            MapState.Directions dir = (MapState.Directions) bundle.getSerializable(DIR);
            Boolean hasKey = bundle.getBoolean(HAS_KEY);
            Boolean noEntry = bundle.getBoolean(NO_ENTRY);


            this.state = new MapState(this, room, dir, hasKey, noEntry);

            state = bundle.getParcelable(SUPER_STATE);
        }
        super.onRestoreInstanceState(state);
    }

}
