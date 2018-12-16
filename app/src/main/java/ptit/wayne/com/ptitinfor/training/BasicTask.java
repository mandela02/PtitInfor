package ptit.wayne.com.ptitinfor.training;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;

public abstract class BasicTask<I, M, O> extends AsyncTask<I, M, O> {

    /*
    for AsyncTask
    I: Input
    M: Middle Result
    O: Output
     */

    protected OnTaskCompleted<O> listener;
    public BasicTask(Context context, OnTaskCompleted<O> listener) {
        this.listener = listener;
    }

    @Override
    protected void onPostExecute(O o) {
        super.onPostExecute(o);
        try {
            this.listener.handle(o);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
