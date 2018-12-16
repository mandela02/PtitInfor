package ptit.wayne.com.ptitinfor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ptit.wayne.com.ptitinfor.model.Rooms;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {
    private List<Rooms> mRooms;
    private Context mContext;

    public RoomAdapter(List<Rooms> rooms, Context context) {
        mRooms = rooms;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_room, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Rooms item = mRooms.get(position);
        holder.bindData(item);

    }

    @Override
    public int getItemCount() {
        return mRooms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextRoom;
        public RecyclerView mRecyclerTime;
        public TimeAdapter mTimeAdapter;
        private GridLayoutManager mGridLayoutManager;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextRoom = itemView.findViewById(R.id.text_room);
            mRecyclerTime = itemView.findViewById(R.id.recycler_time);
            mGridLayoutManager = new GridLayoutManager(mContext, 3);
            mRecyclerTime.setLayoutManager(mGridLayoutManager);
        }
        public void bindData(Rooms item) {
            mTimeAdapter = new TimeAdapter(item.getTime(), mContext, item.getRoom());
            mTextRoom.setText(item.getRoom());
            mRecyclerTime.setAdapter(mTimeAdapter);
        }
    }
}
