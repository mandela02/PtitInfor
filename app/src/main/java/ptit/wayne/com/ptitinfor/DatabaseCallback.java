package ptit.wayne.com.ptitinfor;

import ptit.wayne.com.ptitinfor.model.Account;

public interface DatabaseCallback {
    void onSuccess(Account value);
    void onFail(String message);
}
