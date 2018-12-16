package ptit.wayne.com.ptitinfor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ptit.wayne.com.ptitinfor.model.Account;
import ptit.wayne.com.ptitinfor.model.Rooms;
import ptit.wayne.com.ptitinfor.model.Time;

public class MainActivity extends AppCompatActivity {
    private TextView mTxtStudentName;
    private TextView mTxtStudentID;
    private Database db;
    private SearchView mSearchView;
    private LinearLayoutManager mLinearLayoutManager;
    private RoomAdapter mRoomAdapter;
    private RecyclerView mRecyclerView;
    private List<Rooms> mRoomsList;
    private boolean isSearching = false;
    private String currentDateTimeString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        getInformation();
        addRoom();
        initRecyclerView();
    }

    public void initView() {
        db = new Database();
        currentDateTimeString = DateFormat.getDateInstance().format(new Date());
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Danh sách lớp thi");
        ab.setSubtitle(currentDateTimeString);
        //setTitle("Danh sách lớp thi");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuSearch = menu.findItem(R.id.menu_search);
        mSearchView = (SearchView) menuSearch.getActionView();
        initSearchView();
        return super.onCreateOptionsMenu(menu);
    }

    public void initSearchView() {
        isSearching = !isSearching;

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.equals("")) {
                    int i;
                    List<Rooms> mSearchList = new ArrayList<>();
                    for (i = 0; i < mRoomsList.size(); i++)
                        if (mRoomsList.get(i).getRoom().contains(newText)) {
                            mSearchList.add(mRoomsList.get(i));
                        }
                    mRoomAdapter = new RoomAdapter(mSearchList, MainActivity.this);
                    mRecyclerView.setAdapter(mRoomAdapter);
                }
                return false;
            }
        });
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                isSearching = !isSearching;
                mRoomAdapter = new RoomAdapter(mRoomsList, MainActivity.this);
                mRecyclerView.setAdapter(mRoomAdapter);
                return false;
            }
        });
    }

    public void getInformation() {
        Intent getIntent = getIntent();
        final String ID = getIntent.getStringExtra("studentID");
        db.searchStudentByID(ID, new DatabaseCallback() {
            @Override
            public void onSuccess(Account value) {
              /*  mTxtStudentName.setText(value.getName());
                mTxtStudentID.setText(value.getID());*/
            }

            @Override
            public void onFail(String message) {
            }
        });
    }

    private void addRoom() {
        List<Time> time = new ArrayList<>();
        time.add(new Time(currentDateTimeString , "11:11"));
        time.add(new Time(currentDateTimeString , "00:11"));
        time.add(new Time(currentDateTimeString , "01:11"));
        time.add(new Time(currentDateTimeString , "02:11"));
        time.add(new Time(currentDateTimeString , "03:11"));
        time.add(new Time(currentDateTimeString , "04:11"));
        time.add(new Time(currentDateTimeString , "05:11"));
        mRoomsList = new ArrayList<>();
        mRoomsList.add(new Rooms("101A1", time));
        mRoomsList.add(new Rooms("102A2", time));
        mRoomsList.add(new Rooms("103A3", time));
        mRoomsList.add(new Rooms("104A4", time));
        mRoomsList.add(new Rooms("105A5", time));
    }

    public void initRecyclerView() {
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView = findViewById(R.id.recycler_room);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRoomAdapter = new RoomAdapter(mRoomsList, MainActivity.this);
        mRecyclerView.setAdapter(mRoomAdapter);
    }
}
