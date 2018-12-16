package ptit.wayne.com.ptitinfor.model;

public class Student extends Person{
    private String mStudentName;
    private String mStudentID;
    private String mStudentAvatar;
    private String mStudentStatus;

    public Student(String studentName, String studentID,
                   String studentStatus) {
        mStudentName = studentName;
        mStudentID = studentID;
        mStudentStatus = studentStatus;
    }

    public String getStudentName() {
        return mStudentName;
    }

    public void setStudentName(String studentName) {
        mStudentName = studentName;
    }

    public String getStudentID() {
        return mStudentID;
    }

    public void setStudentID(String studentID) {
        mStudentID = studentID;
    }

    public String getStudentAvatar() {
        return mStudentAvatar;
    }

    public void setStudentAvatar(String studentAvatar) {
        mStudentAvatar = studentAvatar;
    }

    public String getStudentStatus() {
        return mStudentStatus;
    }

    public void setStudentStatus(String studentStatus) {
        mStudentStatus = studentStatus;
    }
}
