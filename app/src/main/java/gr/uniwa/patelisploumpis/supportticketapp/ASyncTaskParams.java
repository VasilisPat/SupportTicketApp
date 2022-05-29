package gr.uniwa.patelisploumpis.supportticketapp;

import android.content.Context;

public class ASyncTaskParams {

    private Context mAppContext;
    private String mTicketID;

    public ASyncTaskParams(Context appContext, String ticketID) {
        this.mAppContext = appContext;
        this.mTicketID = ticketID;
    }

    // Getters
    public Context getAppContext() { return mAppContext; }

    public String getTicketID() { return mTicketID; }

    //Setters
    public void setAppContext(Context mAppContext) { this.mAppContext = mAppContext; }

    public void setTicketID(String mTicketID) { this.mTicketID = mTicketID; }
}
