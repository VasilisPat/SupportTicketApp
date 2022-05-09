package gr.uniwa.patelisploumpis.supportticketapp;

import android.os.Parcel;
import android.os.Parcelable;

public class SupportTicket implements Parcelable {

    private int mlaborHours;
    private String mtechnicianID, mtechnicianName, mclientName, mclientAddress, mclientPhone, mclientEmail, mlaborDate,
            mlaborType, mlaborDescription;

    public SupportTicket() {
    }

    public SupportTicket(int laborHours, String technicianID, String technicianName, String clientName, String clientAddress, String clientPhone, String clientEmail, String laborDate, String laborType, String laborDescription) {
        this.mlaborHours = laborHours;
        this.mtechnicianID = technicianID;
        this.mtechnicianName = technicianName;
        this.mclientName = clientName;
        this.mclientAddress = clientAddress;
        this.mclientPhone = clientPhone;
        this.mclientEmail = clientEmail;
        this.mlaborDate = laborDate;
        this.mlaborType = laborType;
        this.mlaborDescription = laborDescription;
    }

    //Getters
    public int getLaborHours() { return mlaborHours; }

    public String getTechnicianID() { return mtechnicianID; }

    public String getTechnicianName() { return mtechnicianName; }

    public String getClientName() { return mclientName; }

    public String getClientAddress() { return mclientAddress; }

    public String getClientPhone() { return mclientPhone; }

    public String getClientEmail() { return mclientEmail; }

    public String getLaborDate() { return mlaborDate; }

    public String getLaborType() { return mlaborType; }

    public String getLaborDescription() { return mlaborDescription; }

    //Setters

    public void setLaborHours(int laborHours) { this.mlaborHours = laborHours; }

    public void setTechnicianID(String technicianID) { this.mtechnicianID = technicianID; }

    public void setTechnicianName(String technicianName) { this.mtechnicianName = technicianName; }

    public void setClientName(String clientName) { this.mclientName = clientName; }

    public void setClientAddress(String clientAddress) { this.mclientAddress = clientAddress; }

    public void setClientPhone(String clientPhone) { this.mclientPhone = clientPhone; }

    public void setClientEmail(String clientEmail) { this.mclientEmail = clientEmail; }

    public void setLaborDate(String laborDate) { this.mlaborDate = laborDate; }

    public void setLaborType(String laborType) { this.mlaborType = laborType; }

    public void setLaborDescription(String laborDescription) { this.mlaborDescription = laborDescription; }

    @Override
    public int describeContents() {
        return 0;
    }

    protected SupportTicket(Parcel in) {
        this.mlaborHours = in.readInt();
        this.mtechnicianID = in.readString();
        this.mtechnicianName = in.readString();
        this.mclientName = in.readString();
        this.mclientAddress = in.readString();
        this.mclientPhone = in.readString();
        this.mclientEmail = in.readString();
        this.mlaborDate = in.readString();
        this.mlaborType = in.readString();
        this.mlaborDescription = in.readString();
    }

    public void readFromParcel(Parcel source) {
        this.mlaborHours = source.readInt();
        this.mtechnicianID = source.readString();
        this.mtechnicianName = source.readString();
        this.mclientName = source.readString();
        this.mclientAddress = source.readString();
        this.mclientPhone = source.readString();
        this.mclientEmail = source.readString();
        this.mlaborDate = source.readString();
        this.mlaborType = source.readString();
        this.mlaborDescription = source.readString();
    }

    @Override
    public void writeToParcel(Parcel parcelDestination, int i) {
        parcelDestination.writeInt(mlaborHours);
        parcelDestination.writeString(mtechnicianID);
        parcelDestination.writeString(mtechnicianName);
        parcelDestination.writeString(mclientName);
        parcelDestination.writeString(mclientAddress);
        parcelDestination.writeString(mclientPhone);
        parcelDestination.writeString(mclientEmail);
        parcelDestination.writeString(mlaborDate);
        parcelDestination.writeString(mlaborType);
        parcelDestination.writeString(mlaborDescription);
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
