package org.zoobie.remotecontrol.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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

    private EditText inputFieldEt;
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
    private Actions.Keys keys;
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
        inputFieldEt = findViewById(R.id.inputField);

        keys = new Actions.Keys(getResources());
        textInputSetup();
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
        } else if(item.getItemId() == R.id.keyboard){
            if(inputFieldEt.isFocused()){
                hideKeyboard();
            } else {
                Log.d(TAG, "OPENING KEYBOARD...");
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                imm.showSoftInput(inputFieldEt, InputMethodManager.SHOW_IMPLICIT);
                inputFieldEt.requestFocus();
            }
        }
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideKeyboard();
    }

    private void hideKeyboard() {
        inputFieldEt.clearFocus();
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(inputFieldEt.getWindowToken(), 0);
    }

    private void textInputSetup() {
        inputFieldEt.setOnKeyListener((v, keyCode, event) -> {
            Log.i(TAG, keyCode + " key pressed");
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_DEL:
                        connector.send(Actions.KEYBOARD_ACTION, Actions.SPECIAL_KEY_ACTION_CLICK, keys.getActionCodeForKey(R.string.tag_backspace));
                        break;
                    case KeyEvent.KEYCODE_ENTER:
                        connector.send(Actions.KEYBOARD_ACTION, Actions.SPECIAL_KEY_ACTION_CLICK, keys.getActionCodeForKey(R.string.tag_enter));
                        break;
                    case KeyEvent.KEYCODE_BACK:
                        v.clearFocus();
                        break;
                    default:
                        return false;
                }
            } else if (event.getAction() == KeyEvent.ACTION_UP) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_DEL:
                    case KeyEvent.KEYCODE_ENTER:
                    case KeyEvent.KEYCODE_BACK:
                        break;
                    default:
                        return false;
                }
            }
            return true;
        });

        inputFieldEt.addTextChangedListener(new TextWatcher() {
            boolean isChanged = true;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG,"BEFORE TEXT CHANGED");

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i(TAG, s.toString());
                if (before < count && isChanged) {
                    if (s.charAt(before) == '\n')
                        connector.send(Actions.KEYBOARD_ACTION, Actions.SPECIAL_KEY_ACTION_CLICK, keys.getActionCodeForKey(R.string.tag_enter));
                    else
                        connector.send(Actions.KEYBOARD_ACTION, Actions.TEXT_KEY_ACTION_CLICK, (byte) s.charAt(before));

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(""))
                    inputFieldEt.setText("");
            }
        });
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
            boolean isConnected = connector.checkUdpConnection();
            if (!isConnected) throw new ConnectionException("Couldn't connect to the server");
            Toast.makeText(this, "Connected to " + connector.requestServerName(), Toast.LENGTH_SHORT).show();
        } catch (ConnectionException | ExecutionException | InterruptedException e) {
            Toast.makeText(this, "FAILED TO CONNECT", Toast.LENGTH_SHORT).show();
            startActivity(connectionIntent);
        }
    }
}
