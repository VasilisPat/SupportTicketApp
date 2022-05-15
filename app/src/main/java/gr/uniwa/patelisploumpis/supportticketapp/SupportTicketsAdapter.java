package gr.uniwa.patelisploumpis.supportticketapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SupportTicketsAdapter extends RecyclerView.Adapter<SupportTicketsAdapter.ViewHolder> {

    private final Context context;
    private final LayoutInflater inflater;
    private final List<SupportTicket> mSupportTicketList;
    private final OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        void onItemClick(SupportTicket item);
    }

    public SupportTicketsAdapter(Context context, List<SupportTicket> list, OnItemClickListener listener) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.mSupportTicketList = list;
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public SupportTicketsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_support_ticket, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SupportTicketsAdapter.ViewHolder holder, int position) {
        holder.bind(mSupportTicketList.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return mSupportTicketList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView pdfFileIconImageView;
        private final TextView pdfFileNameTextView, supportTicketClientNameTextView, supportTicketDateTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pdfFileIconImageView = itemView.findViewById(R.id.imageView_pdf_file_icon);
            pdfFileNameTextView = itemView.findViewById(R.id.textView_pdf_file_name);
            supportTicketClientNameTextView = itemView.findViewById(R.id.textView_support_ticket_client_name);
            supportTicketDateTextView = itemView.findViewById(R.id.textView_support_ticket_date);
        }

        public void bind(final SupportTicket item, final OnItemClickListener listener) {
            pdfFileNameTextView.setText("Ticket#" + item.getTicketID());
            supportTicketClientNameTextView.setText(item.getClientName());
            supportTicketDateTextView.setText(item.getLaborDate());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });
        }
    }

}
