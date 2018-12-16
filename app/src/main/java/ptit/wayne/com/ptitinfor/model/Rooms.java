package ptit.wayne.com.ptitinfor.model;

import java.util.List;

public class Rooms {
    private String mRoom;
    private List<Time> mTime;

    public Rooms(String room, List<Time> time) {
        mRoom = room;
        mTime = time;
    }

    public String getRoom() {
        return mRoom;
    }

    public void setRoom(String room) {
        mRoom = room;
    }

    public List<Time> getTime() {
        return mTime;
    }

    public void setTime(List<Time> time) {
        mTime = time;
    }
}
