package org.zoobie.remotecontrol.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import org.zoobie.pomd.remotecontrol.R;

public class EntryActivity extends AppCompatActivity {

    private static final int START_REMOTE = 111;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        SharedPreferences sp = getSharedPreferences("org.zoobie.pomd.remotecontrol",MODE_PRIVATE);
        int port = sp.getInt("udp_port",0);
        if(port == 0){
            sp.edit().putInt("upd_port",1711).apply();
        }
        startActivityForResult(new Intent(this, MainActivity.class),START_REMOTE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        System.out.println("HERE");
        finishAndRemoveTask();
        super.onActivityResult(requestCode, resultCode, data);
    }
}
