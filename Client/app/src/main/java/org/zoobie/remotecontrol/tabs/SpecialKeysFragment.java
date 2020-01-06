package org.zoobie.remotecontrol.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import org.zoobie.pomd.remotecontrol.R;
import org.zoobie.remotecontrol.view.KeyboardButton;

import java.util.ArrayList;
import java.util.HashMap;

public class SpecialKeysFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener {

    private ConstraintLayout keysLayout;
    private ArrayList<KeyboardButton> buttonsList;
    private EditText inputField;
//    private HashMap<String,KeyboardButton> keysMap;
    private String[] keyTags;
    public SpecialKeysFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_keyboard, container, false);

        //Setup code here
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        keysLayout = (ConstraintLayout) view;
        ArrayList<View> views = keysLayout.getTouchables();
        buttonsList = new ArrayList<>();
        for(View v : views){
            if(v instanceof KeyboardButton){
                KeyboardButton b = (KeyboardButton)v;
                b.setOnClickListener(this);
                b.setOnLongClickListener(this);
                buttonsList.add(b);
                System.out.println(b.getTag());
            }
        }
        System.out.println(buttonsList.size() + " VIEWS");
    }


    @Override
    public void onClick(View v) {
//        System.out.println(v.getTag());
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}
