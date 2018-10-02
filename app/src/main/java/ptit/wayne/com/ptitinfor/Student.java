package ptit.wayne.com.ptitinfor;

public class Student {
    private String mStudentName;
    private String mStudentPassword;
    private String mStudentID;


    public String getStudentName() {
        return mStudentName;
    }

    public void setStudentName(String studentName) {
        mStudentName = studentName;
    }

    public String getStudentPassword() {
        return mStudentPassword;
    }

    public void setStudentPassword(String studentPassword) {
        mStudentPassword = studentPassword;
    }

    public String getStudentID() {
        return mStudentID;
    }

    public void setStudentID(String studentID) {
        mStudentID = studentID;
    }

    public Student() {
    }

    public Student(String studentName, String studentPassword, String studentID) {
        mStudentName = studentName;
        mStudentPassword = studentPassword;
        mStudentID = studentID;
    }
}
