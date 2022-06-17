package gr.uniwa.patelisploumpis.supportticketapp.Model;

import android.content.Context;

public class ASyncTaskParams {

    private Context mAppContext;
    private String mTicketID;

    public ASyncTaskParams(Context appContext, String ticketID) {
        mAppContext = appContext;
        mTicketID = ticketID;
    }

    // Getters
    public Context getAppContext() { return mAppContext; }

    public String getTicketID() { return mTicketID; }

    // Setters
    public void setAppContext(Context mAppContext) { mAppContext = mAppContext; }

    public void setTicketID(String mTicketID) { mTicketID = mTicketID; }
}
