package org.zoobie.remotecontrol.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import org.zoobie.remotecontrol.MainActivity;
import org.zoobie.pomd.remotecontrol.R;

public class EntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        SharedPreferences sp = getSharedPreferences("org.zoobie.pomd.remotecontrol",MODE_PRIVATE);
        String ip = sp.getString("server_ip",null);
        if(ip == null){
            //get ip
            sp.edit().putString("server_ip","157.158.170.23").apply();
            sp.edit().putInt("upd_port",1711).apply();
        }

        startActivity(new Intent(this, MainActivity.class));
    }
}
