package org.zoobie.remotecontrol.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import org.zoobie.pomd.remotecontrol.R;
import org.zoobie.remotecontrol.adapter.ViewPagerAdapter;
import org.zoobie.remotecontrol.core.actions.Actions;
import org.zoobie.remotecontrol.core.connection.ConnectionException;
import org.zoobie.remotecontrol.core.connection.Connector;
import org.zoobie.remotecontrol.core.connection.udp.Server;
import org.zoobie.remotecontrol.view.CustomViewPager;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private View view;

    private GestureDetector mGestureDetector;

    private boolean fullScreen = false;
    private Toolbar toolbar;
    private AudioManager audioManager;
    //Views
    private ViewPager pager;
    private TabLayout tabLayout;
    private ViewPagerAdapter viewPagerAdapter;
    private Connector connector;
    private SharedPreferences connectionSp;
    private Intent connectionIntent;

    //Adapters
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connectionSp = getSharedPreferences("org.zoobie.connectiondata", Context.MODE_PRIVATE);
        connectionIntent = new Intent(this, ConnectionActivity.class);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        setContentView(R.layout.activity_main);


        toolbar = findViewById(R.id.toolBar);
        toolbar.setNavigationIcon(R.drawable.navigation_icon);
        setSupportActionBar(toolbar);

        //todo put tcp server on a seperate thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        setup();
        initConnection();
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), ViewPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,connector);
        pager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(pager);
    }

    private void setup() {
        //Toolbar setup
        toolbar = findViewById(R.id.toolBar);
        toolbar.setNavigationIcon(R.drawable.navigation_icon);
        setSupportActionBar(toolbar);
        //Views
        pager = (CustomViewPager) findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tabs);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.fullscreen) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            toolbar.setVisibility(View.GONE);
            fullScreen = true;
            Toast.makeText(this, "Press back to exit fullscreen mode", Toast.LENGTH_LONG).show();
        } else if(item.getItemId() == R.id.connectionSettings){
            startActivity(connectionIntent);
        } else if(item.getItemId() == R.id.settings){

        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (fullScreen) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            toolbar.setVisibility(View.VISIBLE);
            fullScreen = false;
        } else {
            finish();
        }
    }



    private boolean volumeButtonPressed = false;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN && !volumeButtonPressed) {
            Log.i(TAG, "Volume down...");
            audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
            connector.send(Actions.VOLUME_ACTION, Actions.VOLUME_DOWN_ACTION);
            volumeButtonPressed = true;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP && !volumeButtonPressed) {
            Log.i(TAG, "Volume up...");
            audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
            connector.send(Actions.VOLUME_ACTION, Actions.VOLUME_UP_ACTION);
            volumeButtonPressed = true;
        }
        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        super.onKeyUp(keyCode,event);
        volumeButtonPressed = false;
        return true;
    }


    private void initConnection() {
        String ip = connectionSp.getString("server_ip", null);
        Integer portUdp = connectionSp.getInt("udp_port", -1) == -1 ? null : connectionSp.getInt("udp_port", -1);
        Server server = new Server(ip, portUdp);
        try {
            connector = new Connector(server);
            boolean isConnected = connector.checkUdpConnection() | connector.checkBluetoothConnection();
            if (!isConnected) throw new ConnectionException("Couldn't connect to the server");
            Toast.makeText(this, "Connected to " + connector.getServerName(), Toast.LENGTH_SHORT).show();
        } catch (ConnectionException | ExecutionException | InterruptedException e) {
            Toast.makeText(this, "FAILED TO CONNECT", Toast.LENGTH_SHORT).show();
            startActivity(connectionIntent);
        }
    }
}
