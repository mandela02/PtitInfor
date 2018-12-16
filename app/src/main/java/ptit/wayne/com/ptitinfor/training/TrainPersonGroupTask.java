package ptit.wayne.com.ptitinfor.training;

import android.content.Context;

import ptit.wayne.com.ptitinfor.Constant;

public class TrainPersonGroupTask extends TrainingBasicTask<String, String, String> {

    public TrainPersonGroupTask(Context context, OnTaskCompleted<String> listener) {
        super(context, listener);
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            faceServiceClient.trainLargePersonGroup(params[0]);
            Constant.INDEX = 5;
            return params[0];
        } catch (Exception e) {
            publishProgress(e.getMessage());
            return null;
        }
    }

}