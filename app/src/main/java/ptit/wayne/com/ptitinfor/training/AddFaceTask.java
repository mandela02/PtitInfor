package ptit.wayne.com.ptitinfor.training;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.microsoft.projectoxford.face.contract.AddPersistedFaceResult;
import com.microsoft.projectoxford.face.contract.Face;
import com.microsoft.projectoxford.face.contract.FaceRectangle;
import com.microsoft.projectoxford.face.rest.ClientException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ptit.wayne.com.ptitinfor.Constant;

public class AddFaceTask extends TrainingBasicTask<Void, String, Boolean> {

    private List<Face> listFace = new ArrayList<>();
    private String mPersonId;
    private Bitmap mBitmap;
    private Face face;
    private String personGroupId;

    public AddFaceTask(Context context, OnTaskCompleted<Boolean> listener, String mPersonGroupId, String mPersonId, Bitmap bitmap, List<Face> faces) {
        super(context, listener);
        this.personGroupId = mPersonGroupId;
        this.mPersonId = mPersonId;
        this.mBitmap = bitmap;
        this.listFace = faces;

    }

    @Override
    protected Boolean doInBackground(Void... params) {
        // Get an instance of face service client to detect faces in image.
        Log.d("MainActivity", "doInBg");
        Constant.INDEX = 4;
        try {
            publishProgress("Adding face...");
            Log.d("MainActivity", "doInBg1");
            UUID personId = UUID.fromString(mPersonId);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            InputStream imageInputStream = new ByteArrayInputStream(stream.toByteArray());

            for(Face face : listFace) {
                FaceRectangle faceRect = face.faceRectangle;
                // Start the request to add face.

                AddPersistedFaceResult result = faceServiceClient.addPersonFaceInLargePersonGroup(
                        personGroupId, personId, imageInputStream,"User Data", faceRect);
            }

                    //    faceIdList.set(index, result.persistedFaceId);
        //    Log.d("MainActivity", "doInBg true " + result.persistedFaceId.toString());

            return Boolean.valueOf(true);

        } catch (ClientException e1) {
            e1.printStackTrace();
            Log.d("MainActivity", "doInBg3");
            return Boolean.valueOf(false);
        } catch (IOException e2) {
            e2.printStackTrace();
            Log.d("MainActivity", "doInBg4");
            return Boolean.valueOf(false);
        } catch (Exception e) {
            publishProgress(e.getMessage());
            Log.d("MainActivity", "doInBg5");
            return Boolean.valueOf(false);
        }

    }
}