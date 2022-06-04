package gr.uniwa.patelisploumpis.supportticketapp.Adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import gr.uniwa.patelisploumpis.supportticketapp.DatabaseHelper;
import gr.uniwa.patelisploumpis.supportticketapp.Model.Technician;
import gr.uniwa.patelisploumpis.supportticketapp.R;

public class TechniciansRecyclerAdapter extends RecyclerView.Adapter<TechniciansRecyclerAdapter.ViewHolder>{
    private final Context mContext;
    private final LayoutInflater inflater;
    private final List<Technician> mTechnicianList;
    private final TechniciansRecyclerAdapter.OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Technician item);
    }

    public TechniciansRecyclerAdapter(Context context, List<Technician> list, TechniciansRecyclerAdapter.OnItemClickListener listener) {
        inflater = LayoutInflater.from(context);
        mContext = context;
        mTechnicianList = list;
        itemClickListener = listener;
    }

    @NonNull
    @Override
    public TechniciansRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_technician, parent, false);
        TechniciansRecyclerAdapter.ViewHolder holder = new TechniciansRecyclerAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TechniciansRecyclerAdapter.ViewHolder holder, int position) {
        holder.bind(mTechnicianList.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return mTechnicianList.size();
    }

    public void updateList(List<Technician> technicianList) {
        mTechnicianList.clear();
        mTechnicianList.addAll(technicianList);
        notifyDataSetChanged();
    }

    private void deleteItem(Technician technician) {
        mTechnicianList.remove(technician);
        AsyncTask aSyncTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseHelper.getInstance(mContext.getApplicationContext()).deleteTechnician(technician.getName());
                return null;
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView technicianIconImageView, deleteSupportTicketImageView;
        private final TextView technicianTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            technicianIconImageView = itemView.findViewById(R.id.imageView_technician_icon);
            technicianTextView = itemView.findViewById(R.id.textView_technician_name);
            deleteSupportTicketImageView = itemView.findViewById(R.id.imageView_delete);
        }

        public void bind(final Technician technician, final TechniciansRecyclerAdapter.OnItemClickListener listener) {
            technicianTextView.setText(technician.getName());

            deleteSupportTicketImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteItem(technician);
                }
            });
        }
    }
}
