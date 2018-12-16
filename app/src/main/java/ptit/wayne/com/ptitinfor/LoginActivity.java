package ptit.wayne.com.ptitinfor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ptit.wayne.com.ptitinfor.model.Account;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    public EditText mEditStudentID;
    public EditText mEditPassword;
    public Button mBtnLogin;
    public CheckBox mCheckShowPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        testDatabase();
        Database db = new Database();
        db.getAll(this);
    }

    public void testDatabase() {
        /*FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Account");
        Account std_1 = new Account("Tri", "1234", "1234567890");
        Account std_2 = new Account("Huong", "1111", "0987654321");
        myRef.child("1234567890").setValue(std_1);
        myRef.child("0987654321").setValue(std_2);*/
    }

    public void initView() {
        mEditStudentID = findViewById(R.id.edit_student_id);
        mEditPassword = findViewById(R.id.edit_password);
        mBtnLogin = findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        mCheckShowPassword = findViewById(R.id.check_show_password);
        mCheckShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mEditPassword.setTransformationMethod(null);
                } else {
                    mEditPassword.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });
    }

    private void login() {
        if (!validate()) {
            onLoginFailed("Không thể đăng nhập");
            return;
        }
        mBtnLogin.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Đang xử lý...");
        progressDialog.show();
        final String sStudentID = mEditStudentID.getText().toString();
        final String sPassword = mEditPassword.getText().toString();
        Database db = new Database();
        db.searchInDatabase(sStudentID, sPassword, new DatabaseCallback() {
            @Override
            public void onSuccess(Account value) {
                onLoginSuccess();
                progressDialog.dismiss();
            }

            @Override
            public void onFail(String message) {
                onLoginFailed(message);
                progressDialog.dismiss();
            }
        });
    }

    public void onLoginSuccess() {
        // TODO: move to next Activity
        Toast.makeText(getBaseContext(), "Login Success", Toast.LENGTH_LONG).show();
        mBtnLogin.setEnabled(true);
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("studentID", mEditStudentID.getText().toString());
        LoginActivity.this.startActivity(intent);
    }

    public void onLoginFailed(String message) {
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
        mBtnLogin.setEnabled(true);
    }

    private boolean validate() {
        boolean valid = true;
        String sStudentID = mEditStudentID.getText().toString();
        String sPassword = mEditPassword.getText().toString();
        if (sStudentID.isEmpty() || sStudentID.length() < 10) {
            mEditStudentID.setError("Mã số sinh viên chưa đúng");
            valid = false;
        } else {
            mEditStudentID.setError(null);
        }
        if (sPassword.isEmpty() || sPassword.length() < 4 || sPassword.length() > 10) {
            mEditPassword.setError("Password gồm 4 đến 10 kí tự");
            valid = false;
        } else {
            mEditPassword.setError(null);
        }
        return valid;
    }
}
