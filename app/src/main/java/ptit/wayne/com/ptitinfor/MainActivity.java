package ptit.wayne.com.ptitinfor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private View navHeader;
    private static final String TAG_PROFILE = "Your profile";
    private static final String TAG_SEARCH = "Find your mate";
    private static final String TAG_EDIT = "Edit your profile";
    public static String CURRENT_TAG = TAG_PROFILE;
    private String activityTitles[] = {"Your profile", "Find your mate", "Edit your profile"};
    private boolean shouldLoadHomeFragOnBackPress = true;
    public static int navItemIndex = 0;
    private ImageView mImageStudent;
    private TextView mTxtStudentName;
    private TextView mTxtStudentID;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
        getInformation();
        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_PROFILE;
            //loadCurrentFragment();
        }
    }

    public void initView() {
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.nav_view);
        navHeader = navigationView.getHeaderView(0);
        setUpNavigationView();
        db = new Database();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }
        if (shouldLoadHomeFragOnBackPress) {
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_PROFILE;
                //loadCurrentFragment();
                return;
            }
        }
        super.onBackPressed();
    }

    public void getInformation() {
        mImageStudent = navHeader.findViewById(R.id.image_student);
        mTxtStudentName = navHeader.findViewById(R.id.txt_studentName);
        mTxtStudentID = navHeader.findViewById(R.id.txt_studentID);
        Intent getIntent = getIntent();
        final String ID = getIntent.getStringExtra("studentID");
        db.searchStudentByID(ID, new DatabaseCallback() {
            @Override
            public void onSuccess(Student value) {
                mTxtStudentName.setText(value.getStudentName());
                mTxtStudentID.setText(value.getStudentID());
                //mImageStudent.setImageResource(R.drawable.ic_launcher_background);
            }

            @Override
            public void onFail(String message) {
            }
        });
    }

    private void setUpNavigationView() {
        navigationView.setNavigationItemSelectedListener(
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.nav_your_profile:
                            navItemIndex = 0;
                            CURRENT_TAG = TAG_PROFILE;
                            Toast.makeText(MainActivity.this, "1", Toast.LENGTH_SHORT).show();
                            drawer.closeDrawers();
                            break;
                        case R.id.nav_search_student:
                            navItemIndex = 1;
                            CURRENT_TAG = TAG_SEARCH;
                            Toast.makeText(MainActivity.this, "2", Toast.LENGTH_SHORT).show();
                            drawer.closeDrawers();
                            break;
                        case R.id.nav_edit_your_profile:
                            navItemIndex = 2;
                            CURRENT_TAG = TAG_EDIT;
                            Toast.makeText(MainActivity.this, "3", Toast.LENGTH_SHORT).show();
                            drawer.closeDrawers();
                            break;
                        case R.id.nav_exit:
                            finish();
                            break;
                        default:
                            navItemIndex = 0;
                    }
                    if (menuItem.isChecked()) {
                        menuItem.setChecked(false);
                    } else {
                        menuItem.setChecked(true);
                    }
                    menuItem.setChecked(true);
                    //loadCurrentFragment();
                    return true;
                }
            });
        ActionBarDrawerToggle actionBarDrawerToggle =
            new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer,
                R.string.closeDrawer) {
                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                }

                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                }
            };
        drawer.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    /*private Fragment getCurrentFragment() {
        MapFragment mapFragment = new MapFragment();
        BuildFragment buildFragment = new BuildFragment();
        HistoryFragment historyFragment = new HistoryFragment();
        switch (navItemIndex) {
            case 0:
                return mapFragment;
            case 1:
                return buildFragment;
            case 2:
                return historyFragment;
            default:
                return mapFragment;
        }
    }*/
}
