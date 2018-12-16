package ptit.wayne.com.ptitinfor;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ptit.wayne.com.ptitinfor.model.Room;
import ptit.wayne.com.ptitinfor.model.Time;

import static android.content.ContentValues.TAG;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.ViewHolder> {
    private List<Time> mTime;
    private Context mContext;
    private String mRoom;

    public TimeAdapter(List<Time> time, Context context, String room) {
        mTime = time;
        mContext = context;
        mRoom = room;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_time, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Time item = mTime.get(position);
        holder.bindData(item.getTime());
    }

    @Override
    public int getItemCount() {
        return mTime.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTxtTime;

        public ViewHolder(View itemView) {
            super(itemView);
            mTxtTime = itemView.findViewById(R.id.text_time);
            mTxtTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Time time = mTime.get(getAdapterPosition());
                    Room room = new Room(time.getTime(), time.getDate(), mRoom);
                    Log.d(TAG, "onClick: " + mTime.get(getAdapterPosition()).getTime());
                    ((Activity) mContext).startActivityForResult(RoomActivity.getInstance
                        (mContext, room), 100);
                }
            });
        }

        public void bindData(String item) {
            mTxtTime.setText(item);
        }
    }
}
