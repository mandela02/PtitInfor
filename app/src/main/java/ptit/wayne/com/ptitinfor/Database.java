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

import ptit.wayne.com.ptitinfor.model.Account;

import static android.content.ContentValues.TAG;

public class Database {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference studentRef = database.getReference("Account");

    public void searchInDatabase(final String studentID, final String studentPassword,
                                 final DatabaseCallback callback) {
        Query query1 = studentRef.orderByChild("id").equalTo
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
        Map<String, Account> map = new HashMap<String, Account>();
        Iterable<DataSnapshot> children = dataSnapshot.getChildren();
        for (DataSnapshot child : children) {
            Account p = child.getValue(Account.class);
            map.put(child.getKey(), p);
        }
        ArrayList<Account> values = new ArrayList<>(map.values());
        if (!values.isEmpty()) {
            Log.d(TAG, "Name is: " + values.get(0).getID() + "Password is: " + values.get(0)
                .getPassword());
            if (values.get(0).getPassword().equals(studentPassword)) {
                callback.onSuccess(values.get(0));
            } else {
                callback.onFail("Sai mật khẩu");
            }
        }
    }

    public void getAll(final Context c) {
        Query query1 = studentRef.orderByChild("id");
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Account> map = new HashMap<String, Account>();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children) {
                    Account p = child.getValue(Account.class);
                    map.put(child.getKey(), p);
                }
                ArrayList<Account> values = new ArrayList<>(map.values());
                Log.d(TAG, "Value is: " + values.size());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void searchStudentByID(String studentID, final DatabaseCallback callback)
    {
        Query query1 = studentRef.orderByChild("id").equalTo(studentID);
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
        Map<String, Account> td = new HashMap<String, Account>();

        Iterable<DataSnapshot> children = dataSnapshot.getChildren();
        for (DataSnapshot child : children) {
            Account p = child.getValue(Account.class);
            td.put(child.getKey(), p);
        }

        ArrayList<Account> values = new ArrayList<Account>(td.values());

        if (!values.isEmpty()) {
            callback.onSuccess(values.get(0));
        } else {
        }
    }
}
