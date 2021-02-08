package com.example.user.aina_app.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Field;

public class MovableFloatingActionButton extends FloatingActionButton implements View.OnTouchListener {
    private String TAG = "MovableFloatingActionButton ";
    private final static float CLICK_DRAG_TOLERANCE = 10; // Often, there will be a slight, unintentional, drag when the user taps the FAB, so we need to account for this.
    private float downRawX, downRawY;
    private float dX, dY;

    public MovableFloatingActionButton(Context context) {
        super(context);
    }

    public MovableFloatingActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MovableFloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        //按下
        if (action == MotionEvent.ACTION_DOWN) {
            downRawX = motionEvent.getRawX();
            downRawY = motionEvent.getRawY();
            dX = view.getX() - downRawX;
            dY = view.getY() - downRawY;

            return true; // Consumed
        }
        //移動
        else if (action == MotionEvent.ACTION_MOVE) {

            int viewWidth = view.getWidth();
            int viewHeight = view.getHeight();

            View viewParent = (View) view.getParent();
            int parentWidth = viewParent.getWidth();
            int parentHeight = viewParent.getHeight();

            float newX = motionEvent.getRawX() + dX;
            newX = Math.max(0, newX); // Don't allow the FAB past the left hand side of the parent
            newX = Math.min(parentWidth - viewWidth, newX); // Don't allow the FAB past the right hand side of the parent

            float newY = motionEvent.getRawY() + dY;
            newY = Math.max(0, newY); // Don't allow the FAB past the top of the parent
            newY = Math.min(parentHeight - viewHeight, newY); // Don't allow the FAB past the bottom of the parent

            view.animate()
                    .x(newX)
                    .y(newY)
                    .setDuration(0)
                    .start();

            return true; // Consumed
        }
        //鬆開
        else if (action == MotionEvent.ACTION_UP) {
//            這邊邏輯會造成，拖動 + 點擊，應該是要2選一
//            float upRawX = motionEvent.getRawX();
//            float upRawY = motionEvent.getRawY();
//
//            float upDX = upRawX - downRawX;
//            float upDY = upRawY - downRawY;
//
//            if (Math.abs(upDX) < CLICK_DRAG_TOLERANCE && Math.abs(upDY) < CLICK_DRAG_TOLERANCE) { // A click
//                Log.i(TAG,"852");
//                return performClick();
//            } else { // A drag
//                //  return false; // Consumed
//            }

            //检测移动的距离，如果很微小可以认为是点击事件
            if (Math.abs(motionEvent.getRawX() - downRawX) < 10 && Math.abs(motionEvent.getRawY() - downRawY) < 10) {
                try {
                    Field field = View.class.getDeclaredField("mListenerInfo");
                    //由于是私有属性，所以需要设置它可见
                    field.setAccessible(true);
                    Object object = field.get(view);
                    field = object.getClass().getDeclaredField("mOnClickListener");
                    field.setAccessible(true);
                    object = field.get(object);
                    if (object != null && object instanceof View.OnClickListener) {
                        ((View.OnClickListener) object).onClick(view);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                //Log.i("mandroid.cn", "button已移动");
                return false;
            }
        } else {
            return super.onTouchEvent(motionEvent);
        }

        return true;  // 1.true 表示要阻擋分發到其他事件， false 表示不阻擋分發事件，如果阻擋其他監聽器會失效，
                      // 2.也可以分寫在三個動作裡面， 因為3個事件都各有return，所以這邊return 沒意義了
    }
}
