package ptit.wayne.com.ptitinfor.model;

public class Time {
    private String mDate;
    private String mTime;

    public Time(String date, String time) {
        mDate = date;
        mTime = time;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }
}
