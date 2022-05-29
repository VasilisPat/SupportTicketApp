package gr.uniwa.patelisploumpis.supportticketapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

public class SupportTicketsRecylerAdapter extends RecyclerView.Adapter<SupportTicketsRecylerAdapter.ViewHolder> {

    private final Context mContext;
    private final LayoutInflater inflater;
    private final List<SupportTicket> mSupportTicketList;
    private final OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        void onItemClick(SupportTicket item);
    }

    public SupportTicketsRecylerAdapter(Context context, List<SupportTicket> list, OnItemClickListener listener) {
        inflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mSupportTicketList = list;
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public SupportTicketsRecylerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_support_ticket, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SupportTicketsRecylerAdapter.ViewHolder holder, int position) {
        holder.bind(mSupportTicketList.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return mSupportTicketList.size();
    }

    public void updateList(List<SupportTicket> supportTicketList) {
        mSupportTicketList.clear();
        mSupportTicketList.addAll(supportTicketList);
        notifyDataSetChanged();
    }

    private void deleteItem(SupportTicket ticket) {
        mSupportTicketList.remove(ticket);
        AsyncTask aSyncTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseHelper.getInstance(mContext.getApplicationContext()).deleteSupportTicketByID(ticket.getTicketID());
                return null;
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView pdfFileIconImageView, emailSupportTicketImageView, deleteSupportTicketImageView;
        private final TextView pdfFileNameTextView, supportTicketClientNameTextView, supportTicketDateTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pdfFileIconImageView = itemView.findViewById(R.id.imageView_pdf_file_icon);
            pdfFileNameTextView = itemView.findViewById(R.id.textView_pdf_file_name);
            supportTicketClientNameTextView = itemView.findViewById(R.id.textView_support_ticket_client_name);
            supportTicketDateTextView = itemView.findViewById(R.id.textView_support_ticket_date);
            emailSupportTicketImageView = itemView.findViewById(R.id.imageView_email);
            deleteSupportTicketImageView = itemView.findViewById(R.id.imageView_delete);
        }

        public void bind(final SupportTicket supportTicket, final OnItemClickListener listener) {
            pdfFileNameTextView.setText("Ticket#" + supportTicket.getTicketID());
            supportTicketClientNameTextView.setText("Client Name: " + supportTicket.getClientName());
            supportTicketDateTextView.setText("Date: " + supportTicket.getLaborDate());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(supportTicket);
                }
            });

            emailSupportTicketImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { /*new EmailSender()*/ }
            });

            deleteSupportTicketImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteItem(supportTicket);
                    File pdfFile= new File(Environment.getExternalStorageDirectory() + "/SupportTickets", "ticket#" + supportTicket.getTicketID() + ".pdf");
                    if(pdfFile.exists()){
                        if(pdfFile.delete()) {
                            Toast.makeText(mContext, "Deleted Successfully ticket#" + supportTicket.getTicketID() + ".pdf", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(mContext, "Couldn't Delete ticket#" + supportTicket.getTicketID() + ".pdf", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }

}
