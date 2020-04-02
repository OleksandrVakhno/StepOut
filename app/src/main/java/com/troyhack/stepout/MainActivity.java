package com.troyhack.stepout;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;

    //buttons for homescreen
    private Button arButton;
    private Button chatButton;
    private Button surveyButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        //send to one of three screens
        sendtoScreen();
    }


    //on start, if user is logged in, got to login activity
    @Override
    protected void onStart() {
        super.onStart();
        if (user==null){
            SendToLogin();
        }
    }


    //send to login activity method
    private void SendToLogin(){
        Intent login = new Intent(MainActivity.this, Login.class);
        startActivity(login);
    }




    //DRAWER STUFF
    private void addDrawerItems() {
        String[] osArray = { "Settings", "Log Out" };
        mAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent settings = new Intent(MainActivity.this, Settings.class);
                        startActivity(settings);
                        break;
                    case 1:
                        Toast.makeText(MainActivity.this, "Logging out...", Toast.LENGTH_SHORT).show();
                        //sign out and send back to login screen
                        auth.signOut();
                        SendToLogin();
                        break;

                        default:
                            return;

                }

            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                mDrawerList.bringToFront();
                mDrawerLayout.requestLayout();
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //button selector
    private void sendtoScreen(){
        arButton = (Button) findViewById(R.id.ar);
        chatButton = (Button) findViewById(R.id.chatroom);
        surveyButton = (Button) findViewById(R.id.survey);

        arButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TO DO: go to AR room
                Toast.makeText(MainActivity.this, "loading virtual world", Toast.LENGTH_SHORT).show();
                //go to ar room
                Intent arplayground = new Intent(MainActivity.this, Arplayground.class);
                startActivity(arplayground);
            }
        });

        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TO DO: go to chat room activity
                Intent chatIntent = new Intent(MainActivity.this, ChatActivity.class);
                startActivity(chatIntent);
                Toast.makeText(MainActivity.this, "going to chatroom", Toast.LENGTH_SHORT).show();
            }
        });

        surveyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TO DO : go to survey activity
                Toast.makeText(MainActivity.this, "going to survey", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
