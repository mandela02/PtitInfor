package ptit.wayne.com.ptitinfor;

public interface DatabaseCallback {
    void onSuccess(Student value);
    void onFail(String message);
}
