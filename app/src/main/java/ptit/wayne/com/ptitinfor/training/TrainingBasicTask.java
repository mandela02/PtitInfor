package ptit.wayne.com.ptitinfor.training;

import android.content.Context;
import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.FaceServiceRestClient;

import ptit.wayne.com.ptitinfor.Constant;

public abstract class TrainingBasicTask<I, M, O> extends BasicTask<I, M, O> {

    /*
    for AsyncTask
    I: Input
    M: Middle Result
    O: Output
     */

    protected FaceServiceClient faceServiceClient;

    public TrainingBasicTask(Context context, OnTaskCompleted<O> listener) {
        super(context,listener);
        this.faceServiceClient = new FaceServiceRestClient(Constant.API_ENDPOINT, Constant.API_KEY);
    }

}
