package ptit.wayne.com.ptitinfor;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.microsoft.projectoxford.face.contract.Face;
import com.microsoft.projectoxford.face.contract.FaceRectangle;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ptit.wayne.com.ptitinfor.training.DetectionTask;
import ptit.wayne.com.ptitinfor.training.OnTaskCompleted;

public class IdentifyActivity extends AppCompatActivity implements View.OnClickListener,
    OnTaskCompleted {
    public static final int REQUEST_CAPTURE_IMAGE = 100;
    public static final int PICK_IMAGE = 200;
    public static final int REQUEST_UPDATE_DATABASE = 300;
    public Button mButton;
    public ImageView mImageTake;
    public ImageView mImageSelect;
    public ImageView mImageDisplay;
    public ProgressDialog progressDialog;
    public Uri mImageUri;
    public Bitmap imageBitmap;
    public String mPersonGroupId;
    public boolean detected;
    public RecyclerView mRecyclerView;
    public FaceDetectedAdapter mFaceDetectedAdapter;
    public LinearLayoutManager mLinearLayoutManager;
    public List<String> personGroupIdList;
    public boolean mCheckResult;
    public String userChoseTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identifity);
        initView();
        checkPermission();
    }

    public void initView() {
        mImageSelect = findViewById(R.id.image_select);
        mImageSelect.setOnClickListener(this);
        mImageTake = findViewById(R.id.image_take);
        mImageTake.setOnClickListener(this);
        mImageDisplay = findViewById(R.id.image_display);
        mRecyclerView = findViewById(R.id.list_identified_faces);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mButton = findViewById(R.id.button_identify);
        mButton.setOnClickListener(this);
        mButton.setEnabled( false);
        personGroupIdList = new ArrayList<>();
    }

    public void checkPermission()
    {
        mCheckResult = Utility.checkPermission(IdentifyActivity.this);

    }

    private void openGalleryIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
        }
        startActivityForResult(intent, PICK_IMAGE);
    }

    private void openCameraIntent() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(pictureIntent,
                REQUEST_CAPTURE_IMAGE);
        }
    }

    private void detect(Bitmap bitmap) {
        // Put the image into an input stream for detection.
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(output.toByteArray());
        new DetectionTask(this, this).execute(inputStream);
    }

    private static Bitmap drawFaceRectanglesOnBitmap(
        Bitmap originalBitmap, Face[] faces) {
        Bitmap bitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        if (faces != null) {
            for (Face face : faces) {
                FaceRectangle faceRectangle = face.faceRectangle;
                canvas.drawRect(
                    faceRectangle.left,
                    faceRectangle.top,
                    faceRectangle.left + faceRectangle.width,
                    faceRectangle.top + faceRectangle.height,
                    paint);
            }
        }
        return bitmap;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_identify:
                Intent intent = new Intent();
                setResult(REQUEST_UPDATE_DATABASE, intent);
                finish();
                break;
            case R.id.image_take:
                userChoseTask ="Take Photo";
                if(mCheckResult) openCameraIntent();
                break;
            case R.id.image_select:
                userChoseTask ="Choose from Library";
                if(mCheckResult) openGalleryIntent();
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CAPTURE_IMAGE || requestCode == PICK_IMAGE) {
            //detected = false;
            mImageUri = data.getData();
            imageBitmap = ImageHelper.loadSizeLimitedBitmapFromUri(
                mImageUri, getContentResolver());
            mImageDisplay.setImageBitmap(imageBitmap);
            mButton.setEnabled(true);
            detect(imageBitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoseTask.equals("Take Photo"))
                        openCameraIntent();
                    else if(userChoseTask.equals("Choose from Library"))
                        openGalleryIntent();
                } else {
                    finish();
                }
                break;
        }
    }

    @Override
    public void handle(Object value) throws IOException {
        Face[] detectionResult;
        detectionResult = (Face[]) value;
        mImageDisplay.setImageBitmap(ImageHelper.drawFaceRectanglesOnBitmap(imageBitmap, detectionResult,
            false));
        mFaceDetectedAdapter =
            new FaceDetectedAdapter(detectionResult, imageBitmap, "this", this);
        mRecyclerView.setAdapter(mFaceDetectedAdapter);
    }
}
