package ptit.wayne.com.ptitinfor;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ptit.wayne.com.ptitinfor.model.Exam;
import ptit.wayne.com.ptitinfor.model.Room;
import ptit.wayne.com.ptitinfor.model.Student;

public class RoomActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "RoomActivity";

    public static Intent getInstance(Context context, Room result) {
        Intent intent = new Intent(context, RoomActivity.class);
        intent.putExtra("Send room", result);
        return intent;
    }

    private Room mResult;
    private List<Student> mStudentList;
    private RecyclerView mRecyclerView;
    private StudentAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private FloatingActionButton mFloatingActionButton;
    private Exam mExam;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        getExam();
        initActionBar();
        addStudent();
        initRecyclerView();
        initView();
    }

    public void getData() {
        Intent intent = getIntent();
        mResult = intent.getParcelableExtra("Send room");
    }

    public void getExam()
    {
        getData();
        mExam = new Exam();
        mExam.setRoom(mResult);
    }

    public void initActionBar() {
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Phòng thi " + mExam.getRoom().getRoom());
        ab.setSubtitle("Giờ bắt đầu: " + mExam.getRoom().getTime());
    }

    public void addStudent()
    {
        //TODO: get Student list from database
        mStudentList = new ArrayList<>();
        mStudentList.add(new Student("Batman", "GOTHAM123","Vắng mặt"));
        mStudentList.add(new Student("Superman", "METRO234","Vắng mặt"));
        mStudentList.add(new Student("Batman", "GOTHAM123","Vắng mặt"));
        mStudentList.add(new Student("Batman", "GOTHAM123","Vắng mặt"));
        mStudentList.add(new Student("Batman", "GOTHAM123","Vắng mặt"));
        mStudentList.add(new Student("Batman", "GOTHAM123","Vắng mặt"));
        mStudentList.add(new Student("Batman", "GOTHAM123","Vắng mặt"));
        mStudentList.add(new Student("Batman", "GOTHAM123","Vắng mặt"));
        mStudentList.add(new Student("Batman", "GOTHAM123","Vắng mặt"));
        mStudentList.add(new Student("Batman", "GOTHAM123","Vắng mặt"));
        mStudentList.add(new Student("Batman", "GOTHAM123","Vắng mặt"));
        mStudentList.add(new Student("Batman", "GOTHAM123","Vắng mặt"));
        mExam.setStudentList(mStudentList);
    }

    public void initRecyclerView()
    {
        mRecyclerView = findViewById(R.id.recycle_student);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new StudentAdapter(mExam.getStudentList(), RoomActivity.this);
        mRecyclerView.setAdapter(mAdapter);

    }

    public void initView()
    {
        mFloatingActionButton = findViewById(R.id.float_button);
        mFloatingActionButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.float_button:
                Intent intent = new Intent(RoomActivity.this, IdentifyActivity.class);
                startActivityForResult(intent, 2);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2)
        {
            mAdapter.notifyDataSetChanged();
        }
    }
}
