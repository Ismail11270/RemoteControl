package org.zoobie.remotecontrol.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

import org.zoobie.pomd.remotecontrol.R;

public class TouchpadButton extends AppCompatButton {


    private boolean pressed = false;
    private int background = R.drawable.mouse_buttons_background;
    private int backgroundPressed = R.drawable.mouse_buttons_background_pressed;


    public TouchpadButton(Context context) {
        super(context);
        this.setBackground(getResources().getDrawable(background,null));
    }

    public TouchpadButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setBackground(getResources().getDrawable(background,null));

    }

    public TouchpadButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setBackground(getResources().getDrawable(background,null));
    }
    public void setBackground(int id){
        background = id;
    }
    public void setBackgroundPressed(int id){
        backgroundPressed = id;
    }
    public void toggle(){
        Drawable newBackground;
        if(!pressed){
            newBackground = getResources().getDrawable(backgroundPressed,null);
        } else {
            newBackground = getResources().getDrawable(background,null);
        }
        this.setBackground(newBackground);
    }

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }

    @Override
    public boolean performLongClick() {
        super.performLongClick();
        return true;
    }

    public boolean isButtonPressed(){
        return pressed;
    }

    public void buttonPress(){
        pressed = !pressed;
    }

}
