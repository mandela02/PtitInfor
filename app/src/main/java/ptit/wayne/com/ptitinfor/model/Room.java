package ptit.wayne.com.ptitinfor.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Room implements Parcelable {
    private String mTime;
    private String mDate;
    private String mRoom;

    public Room(String time, String date, String room) {
        mTime = time;
        mDate = date;
        mRoom = room;
    }

    protected Room(Parcel in) {
        mTime = in.readString();
        mDate = in.readString();
        mRoom = in.readString();
    }

    public static final Creator<Room> CREATOR = new Creator<Room>() {
        @Override
        public Room createFromParcel(Parcel in) {
            return new Room(in);
        }

        @Override
        public Room[] newArray(int size) {
            return new Room[size];
        }
    };

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getRoom() {
        return mRoom;
    }

    public void setRoom(String room) {
        mRoom = room;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTime);
        dest.writeString(mDate);
        dest.writeString(mRoom);
    }
}
