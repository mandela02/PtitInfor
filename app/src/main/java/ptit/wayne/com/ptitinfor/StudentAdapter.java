package ptit.wayne.com.ptitinfor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ptit.wayne.com.ptitinfor.model.Student;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder>{
    private List<Student> mStudentList;
    private Context mContext;

    public StudentAdapter(List<Student> studentList, Context context) {
        mStudentList = studentList;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_student, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Student item = mStudentList.get(position);
        holder.bindData(item);
    }

    @Override
    public int getItemCount() {
        return mStudentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mAvatar;
        private TextView mName;
        private TextView mID;
        private TextView mStatus;
        public ViewHolder(View itemView) {
            super(itemView);
            mAvatar = itemView.findViewById(R.id.image_face);
            mName = itemView.findViewById(R.id.text_name);
            mID = itemView.findViewById(R.id.text_studentID);
            mStatus = itemView.findViewById(R.id.text_status);
        }

        public void bindData(Student item) {
            //TODO: use piccaso to update image
            //mAvatar.setImageResource(R.drawable.ic_launcher_background);
            mName.setText(item.getStudentName());
            mID.setText("MSSV: " + item.getStudentID());
            mStatus.setText("Trạng thái: " + item.getStudentStatus());
        }
    }
}
