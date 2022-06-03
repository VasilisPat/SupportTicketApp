package gr.uniwa.patelisploumpis.supportticketapp;

import android.os.Parcel;
import android.os.Parcelable;

public class SupportTicket implements Parcelable {

    private int mLaborHours;
    private String mTicketID, mTechnicianName, mClientName, mClientAddress, mClientPhone, mClientEmail, mLaborDate,
            mLaborType, mLaborDescription;

    public SupportTicket() {
    }

    public SupportTicket(int laborHours, String ticketID, String technicianName, String clientName, String clientAddress, String clientPhone, String clientEmail, String laborDate, String laborType, String laborDescription) {
        mLaborHours = laborHours;
        mTicketID = ticketID;
        mTechnicianName = technicianName;
        mClientName = clientName;
        mClientAddress = clientAddress;
        mClientPhone = clientPhone;
        mClientEmail = clientEmail;
        mLaborDate = laborDate;
        mLaborType = laborType;
        mLaborDescription = laborDescription;
    }

    //Getters
    public int getLaborHours() { return mLaborHours; }

    public String getTicketID() { return mTicketID; }

    public String getTechnicianName() { return mTechnicianName; }

    public String getClientName() { return mClientName; }

    public String getClientAddress() { return mClientAddress; }

    public String getClientPhone() { return mClientPhone; }

    public String getClientEmail() { return mClientEmail; }

    public String getLaborDate() { return mLaborDate; }

    public String getLaborType() { return mLaborType; }

    public String getLaborDescription() { return mLaborDescription; }

    //Setters
    public void setLaborHours(int laborHours) { mLaborHours = laborHours; }

    public void setTicketID(String ticketID) { mTicketID = ticketID; }

    public void setTechnicianName(String technicianName) { mTechnicianName = technicianName; }

    public void setClientName(String clientName) { mClientName = clientName; }

    public void setClientAddress(String clientAddress) { mClientAddress = clientAddress; }

    public void setClientPhone(String clientPhone) { mClientPhone = clientPhone; }

    public void setClientEmail(String clientEmail) { mClientEmail = clientEmail; }

    public void setLaborDate(String laborDate) { mLaborDate = laborDate; }

    public void setLaborType(String laborType) { mLaborType = laborType; }

    public void setLaborDescription(String laborDescription) { mLaborDescription = laborDescription; }

    @Override
    public int describeContents() {
        return 0;
    }

    protected SupportTicket(Parcel in) {
        mLaborHours = in.readInt();
        mTicketID = in.readString();
        mTechnicianName = in.readString();
        mClientName = in.readString();
        mClientAddress = in.readString();
        mClientPhone = in.readString();
        mClientEmail = in.readString();
        mLaborDate = in.readString();
        mLaborType = in.readString();
        mLaborDescription = in.readString();
    }

    public void readFromParcel(Parcel source) {
        mLaborHours = source.readInt();
        mTicketID = source.readString();
        mTechnicianName = source.readString();
        mClientName = source.readString();
        mClientAddress = source.readString();
        mClientPhone = source.readString();
        mClientEmail = source.readString();
        mLaborDate = source.readString();
        mLaborType = source.readString();
        mLaborDescription = source.readString();
    }

    @Override
    public void writeToParcel(Parcel parcelDestination, int i) {
        parcelDestination.writeInt(mLaborHours);
        parcelDestination.writeString(mTicketID);
        parcelDestination.writeString(mTechnicianName);
        parcelDestination.writeString(mClientName);
        parcelDestination.writeString(mClientAddress);
        parcelDestination.writeString(mClientPhone);
        parcelDestination.writeString(mClientEmail);
        parcelDestination.writeString(mLaborDate);
        parcelDestination.writeString(mLaborType);
        parcelDestination.writeString(mLaborDescription);
    }

    public static final Creator<SupportTicket> CREATOR = new Creator<SupportTicket>() {
        @Override
        public SupportTicket createFromParcel(Parcel source) {
            return new SupportTicket(source);
        }

        @Override
        public SupportTicket[] newArray(int size) {
            return new SupportTicket[size];
        }
    };

}
