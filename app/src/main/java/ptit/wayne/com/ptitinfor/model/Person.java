package ptit.wayne.com.ptitinfor.model;

import java.util.List;

public class Person {
    public String mPersonId;
    public List<String> mFaceIdList;
    public List<String> mFaceUriList;

    public String getPersonId() {
        return mPersonId;
    }

    public void setPersonId(String personId) {
        mPersonId = personId;
    }

    public List<String> getFaceIdList() {
        return mFaceIdList;
    }

    public void setFaceIdList(List<String> faceIdList) {
        mFaceIdList = faceIdList;
    }

    public List<String> getFaceUriList() {
        return mFaceUriList;
    }

    public void setFaceUriList(List<String> faceUriList) {
        mFaceUriList = faceUriList;
    }
}
