package gr.uniwa.patelisploumpis.supportticketapp.Task;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;

import gr.uniwa.patelisploumpis.supportticketapp.DatabaseHelper;
import gr.uniwa.patelisploumpis.supportticketapp.Model.ASyncTaskParams;
import gr.uniwa.patelisploumpis.supportticketapp.Model.SupportTicket;

public class EmailSender extends AsyncTask<ASyncTaskParams, Void, Intent> {

    private Context mContext;
    private final File storageDir = new File(Environment.getExternalStorageDirectory() + "/SupportTickets");

    @Override
    protected Intent doInBackground(ASyncTaskParams... params) {
        mContext = params[0].getAppContext();
        return composeEmail(DatabaseHelper.getInstance(mContext).getTicketByID(params[0].getTicketID()));
    }

    @Override
    protected void onPostExecute(Intent intent) {
        mContext.startActivity(intent);
    }

    private Intent composeEmail(SupportTicket supportTicket) {
        String[] emailRecipients = {supportTicket.getClientEmail(), "support@support.com"};
        String emailSubject = "Support Ticket No." + supportTicket.getTicketID() + " -- " + supportTicket.getClientName();
        File supportTicketFileURI = new File(storageDir.toString(), "ticket#" + supportTicket.getTicketID() + ".pdf");

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, emailRecipients);
        intent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
        intent.putExtra(Intent.EXTRA_STREAM, supportTicketFileURI);

        return intent;
    }

}