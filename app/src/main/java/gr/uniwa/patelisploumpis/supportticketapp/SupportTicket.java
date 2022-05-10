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
        this.mLaborHours = laborHours;
        this.mTicketID = ticketID;
        this.mTechnicianName = technicianName;
        this.mClientName = clientName;
        this.mClientAddress = clientAddress;
        this.mClientPhone = clientPhone;
        this.mClientEmail = clientEmail;
        this.mLaborDate = laborDate;
        this.mLaborType = laborType;
        this.mLaborDescription = laborDescription;
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

    public void setLaborHours(int laborHours) { this.mLaborHours = laborHours; }

    public void setTicketID(String ticketID) { this.mTicketID = ticketID; }

    public void setTechnicianName(String technicianName) { this.mTechnicianName = technicianName; }

    public void setClientName(String clientName) { this.mClientName = clientName; }

    public void setClientAddress(String clientAddress) { this.mClientAddress = clientAddress; }

    public void setClientPhone(String clientPhone) { this.mClientPhone = clientPhone; }

    public void setClientEmail(String clientEmail) { this.mClientEmail = clientEmail; }

    public void setLaborDate(String laborDate) { this.mLaborDate = laborDate; }

    public void setLaborType(String laborType) { this.mLaborType = laborType; }

    public void setLaborDescription(String laborDescription) { this.mLaborDescription = laborDescription; }

    @Override
    public int describeContents() {
        return 0;
    }

    protected SupportTicket(Parcel in) {
        this.mLaborHours = in.readInt();
        this.mTicketID = in.readString();
        this.mTechnicianName = in.readString();
        this.mClientName = in.readString();
        this.mClientAddress = in.readString();
        this.mClientPhone = in.readString();
        this.mClientEmail = in.readString();
        this.mLaborDate = in.readString();
        this.mLaborType = in.readString();
        this.mLaborDescription = in.readString();
    }

    public void readFromParcel(Parcel source) {
        this.mLaborHours = source.readInt();
        this.mTicketID = source.readString();
        this.mTechnicianName = source.readString();
        this.mClientName = source.readString();
        this.mClientAddress = source.readString();
        this.mClientPhone = source.readString();
        this.mClientEmail = source.readString();
        this.mLaborDate = source.readString();
        this.mLaborType = source.readString();
        this.mLaborDescription = source.readString();
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
