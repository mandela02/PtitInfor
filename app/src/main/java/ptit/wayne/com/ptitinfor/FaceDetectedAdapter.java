package ptit.wayne.com.ptitinfor;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.microsoft.projectoxford.face.contract.Face;
import com.microsoft.projectoxford.face.contract.IdentifyResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ptit.wayne.com.ptitinfor.model.Rooms;

public class FaceDetectedAdapter extends RecyclerView.Adapter<FaceDetectedAdapter.ViewHolder> {

    public List<Face> mFaces;
    public List<IdentifyResult> mIdentifyResults;
    public List<Bitmap> mFaceThumbnails;
    public Context mContext;
    public String mPersonGroupId;


    public FaceDetectedAdapter(Face[] detectionResult, Bitmap imageBitmap, String id, Context context) {
        mContext = context;
        mPersonGroupId = id;
        mFaces = new ArrayList<>();
        mFaceThumbnails = new ArrayList<>();
        mIdentifyResults = new ArrayList<>();
        if (detectionResult != null) {
            mFaces = Arrays.asList(detectionResult);
            for (Face face : mFaces) {
                try {
                    // Crop face thumbnail with five main landmarks drawn from original image.
                    mFaceThumbnails.add(ImageHelper.generateFaceThumbnail(
                        imageBitmap, face.faceRectangle));
                } catch (IOException e) {
                    // Show the exception when generating face thumbnail fails.
                }
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_face_with_description, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bitmap item = mFaceThumbnails.get(position);
        holder.bindData(item);
    }

    @Override
    public int getItemCount() {
        return mFaces.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;
        TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.text_detected_face);
            mImageView = itemView.findViewById(R.id.face_thumbnail);
        }

        public void bindData(Bitmap item) {
            mImageView.setImageBitmap(item);
        }
    }
}
