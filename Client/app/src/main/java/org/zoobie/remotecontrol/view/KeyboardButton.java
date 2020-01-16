package org.zoobie.remotecontrol.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import org.zoobie.pomd.remotecontrol.R;

public class KeyboardButton extends AppCompatButton {
    private static int keysCount;
    private static int keysPressed;
    static {
        keysCount = 0;
        keysPressed = 0;
    }

    private boolean pressed = false;
    private int background = R.drawable.mouse_middle_button_background;
    private int backgroundPressed = R.drawable.mouse_buttons_background_pressed_middle;
    private int textColor = R.color.amber_light;
    private int textColorPressed = R.color.red2;
    private boolean isToggleable;

    public KeyboardButton(Context context) {
        super(context);
        this.setBackground(getResources().getDrawable(background,null));
        setTextColor(getResources().getColor(textColor,null));
    }

    public KeyboardButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setBackground(getResources().getDrawable(background,null));
        keysCount++;
        setAttrs(context,attrs,0);
        setTextColor(getResources().getColor(textColor,null));
    }

    public KeyboardButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setBackground(getResources().getDrawable(background,null));
        setAttrs(context,attrs,defStyleAttr);
        setTextColor(getResources().getColor(textColor,null));
    }
    private void setAttrs(Context ctx, AttributeSet attrs, int defStyleAttr){
        TypedArray typedArray = ctx.getTheme().obtainStyledAttributes(attrs, R.styleable.KeyboardButton, defStyleAttr, 0);
        isToggleable = typedArray.getBoolean(R.styleable.KeyboardButton_isToggleable,true);
        typedArray.recycle();
    }

    public void toggle(){
        if(!isToggleable) return;
        Drawable newBackground;
        if(!pressed){
            newBackground = getResources().getDrawable(backgroundPressed,null);
            setTextColor(getResources().getColor(textColorPressed,null));
        } else {
            newBackground = getResources().getDrawable(background,null);
            setTextColor(getResources().getColor(textColor,null));
        }
        this.setBackground(newBackground);
    }

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }

    @Override
    public boolean performLongClick(){
        super.performLongClick();
        return true;
    }

    public boolean isToggleable(){
        System.out.println(isToggleable);
        return isToggleable;
    }
    public boolean isButtonPressed(){
        return pressed;
    }

    public void buttonPress(){
        pressed = !pressed;
    }

}
