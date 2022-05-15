package gr.uniwa.patelisploumpis.supportticketapp;

import android.content.Context;

public class ATaskParams {

    private Context mAppContext;
    private int mTicketID;

    public ATaskParams(Context appContext, int ticketID) {
        this.mAppContext = appContext;
        this.mTicketID = ticketID;
    }

    // Getters
    public Context getAppContext() { return mAppContext; }

    public int getTicketID() { return mTicketID; }

    //Setters
    public void setAppContext(Context mAppContext) { this.mAppContext = mAppContext; }

    public void setTicketID(int mTicketID) { this.mTicketID = mTicketID; }
}
