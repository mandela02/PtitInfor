package ptit.wayne.com.ptitinfor;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class Database {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference studentRef = database.getReference("Student");

    public void searchInDatabase(final String studentID, final String studentPassword,
                                 final DatabaseCallback callback) {
        Query query1 = studentRef.orderByChild("studentID").equalTo
            (studentID);
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                getStudentPass(studentPassword, dataSnapshot, callback);
                else
                    callback.onFail("Không tìm thấy mã số sinh viên này");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFail(databaseError.getMessage());
            }
        });
    }

    public void getStudentPass(String studentPassword, DataSnapshot dataSnapshot,
                           DatabaseCallback callback) {
        Map<String, Student> map = new HashMap<String, Student>();
        Iterable<DataSnapshot> children = dataSnapshot.getChildren();
        for (DataSnapshot child : children) {
            Student p = child.getValue(Student.class);
            map.put(child.getKey(), p);
        }
        ArrayList<Student> values = new ArrayList<>(map.values());
        if (!values.isEmpty()) {
            Log.d(TAG, "Name is: " + values.get(0).getStudentID() + "Password is: " + values.get(0)
                .getStudentPassword());
            if (values.get(0).getStudentPassword().equals(studentPassword)) {
                callback.onSuccess(values.get(0));
            } else {
                callback.onFail("Sai mật khẩu");
            }
        }
    }

    public void getAll(final Context c) {
        Query query1 = studentRef.orderByChild("studentID");
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Student> map = new HashMap<String, Student>();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children) {
                    Student p = child.getValue(Student.class);
                    map.put(child.getKey(), p);
                }
                ArrayList<Student> values = new ArrayList<>(map.values());
                Log.d(TAG, "Value is: " + values.size());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void searchStudentByID(String studentID, final DatabaseCallback callback)
    {
        Query query1 = studentRef.orderByChild("studentID").equalTo(studentID);
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                    getStudentByID(dataSnapshot, callback);
                else
                    callback.onFail("Không tìm thấy mã số sinh viên này");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFail(databaseError.getMessage());
            }
        });
    }

    private void getStudentByID(DataSnapshot dataSnapshot, DatabaseCallback callback) {
        Map<String, Student> td = new HashMap<String, Student>();

        Iterable<DataSnapshot> children = dataSnapshot.getChildren();
        for (DataSnapshot child : children) {
            Student p = child.getValue(Student.class);
            td.put(child.getKey(), p);
        }

        ArrayList<Student> values = new ArrayList<Student>(td.values());

        if (!values.isEmpty()) {
            callback.onSuccess(values.get(0));
        } else {
        }
    }
}
