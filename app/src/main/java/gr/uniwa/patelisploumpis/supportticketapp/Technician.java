package gr.uniwa.patelisploumpis.supportticketapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Technician implements Parcelable {

    private String mName;

    public Technician() {
    }

    public Technician(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected Technician(Parcel in) {
        mName = in.readString();
    }

    public void readFromParcel(Parcel source) {
        mName = source.readString();
    }

    @Override
    public void writeToParcel(Parcel parcelDestination, int i) {
        parcelDestination.writeString(mName);
    }

    public static final Parcelable.Creator<Technician> CREATOR = new Parcelable.Creator<Technician>() {
        @Override
        public Technician createFromParcel(Parcel source) { return new Technician(source); }

        @Override
        public Technician[] newArray(int size) { return new Technician[size]; }
    };

}
