package ptit.wayne.com.ptitinfor.training;

import android.content.Context;
import com.microsoft.projectoxford.face.contract.CreatePersonResult;

import ptit.wayne.com.ptitinfor.Constant;

public class AddPersonTask extends TrainingBasicTask<String, String, String> {
    // Indicate the next step is to add face in this person, or finish editing this person.

    private String personId;

    public AddPersonTask(Context context, OnTaskCompleted<String> listener) {
        super(context, listener);
    }


    @Override
    protected String doInBackground(String... params) {
        // Get an instance of face service client.
        try {
            publishProgress("Syncing with server to add person...");
            // Start the request to creating person.
            CreatePersonResult createPersonResult =
                    super.faceServiceClient.createPersonInLargePersonGroup(
                            params[0],
                            "Person name", "User data");
            Constant.INDEX = 2;
            return createPersonResult.personId.toString();
        } catch (Exception e) {
            publishProgress(e.getMessage());
            return null;
        }
    }

}