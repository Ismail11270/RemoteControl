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

import java.util.ArrayList;

public class SpecialKeysFragment extends Fragment implements View.OnClickListener {

    private ConstraintLayout keysLayout;
    private ArrayList<Button> buttonsList;
    private EditText inputField;


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
            if(v instanceof Button){
                Button b = (Button)v;
                b.setOnClickListener(this);
                buttonsList.add(b);
            }
        }
        System.out.println(buttonsList.size() + " VIEWS");
    }


    @Override
    public void onClick(View v) {
        System.out.println(v.getTag());
    }
}
