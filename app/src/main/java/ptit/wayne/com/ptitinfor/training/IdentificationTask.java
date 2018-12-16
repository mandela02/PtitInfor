package ptit.wayne.com.ptitinfor.training;

import android.content.Context;
import com.microsoft.projectoxford.face.contract.IdentifyResult;
import com.microsoft.projectoxford.face.contract.TrainingStatus;

import java.util.UUID;

import ptit.wayne.com.ptitinfor.Constant;

public class IdentificationTask extends TrainingBasicTask<UUID, String, IdentifyResult[]> {

    private String mPersonGroupId;

    public IdentificationTask(Context context, OnTaskCompleted<IdentifyResult[]> listener, String personGroupId) {
        super(context, listener);
        this.mPersonGroupId = personGroupId;
    }

//    public IdentificationTask(String personGroupId) {
//
//        this.mPersonGroupId = personGroupId;
//    }

    @Override
    protected IdentifyResult[] doInBackground(UUID... params) {
        try {
            publishProgress("Getting person group status...");
            TrainingStatus trainingStatus = faceServiceClient.getLargePersonGroupTrainingStatus(
                    this.mPersonGroupId);     /* personGroupId */
            if (trainingStatus.status != TrainingStatus.Status.Succeeded) {
                publishProgress("Person group training status is " + trainingStatus.status);
            //    mSucceed = false;
                return null;
            }
            publishProgress("Identifying...");
            // Start identification.
            Constant.INDEX = 7;
            return faceServiceClient.identityInLargePersonGroup(
                    this.mPersonGroupId,   /* personGroupId */
                    params,                  /* faceIds */
                    1);  /* maxNumOfCandidatesReturned */
        } catch (Exception e) {
         //   mSucceed = false;
            publishProgress(e.getMessage());
            return null;
        }
    }

}
