package ptit.wayne.com.ptitinfor.model;

import java.util.List;

public class Exam {
    private String mExamID;
    private Room mRoom;
    private List<Student> mStudentList;
    private String mState;
    private String mSupervisorName;

    public String getExamID() {
        return mExamID;
    }

    public void setExamID(String examID) {
        mExamID = examID;
    }

    public Room getRoom() {
        return mRoom;
    }

    public void setRoom(Room room) {
        mRoom = room;
    }

    public List<Student> getStudentList() {
        return mStudentList;
    }

    public void setStudentList(List<Student> studentList) {
        mStudentList = studentList;
    }

    public String getState() {
        return mState;
    }

    public void setState(String state) {
        mState = state;
    }

    public String getSupervisorName() {
        return mSupervisorName;
    }

    public void setSupervisorName(String supervisorName) {
        mSupervisorName = supervisorName;
    }
}
