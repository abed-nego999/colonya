package sngular.com.atmsmap.presentation.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alberto.hernandez on 14/04/2016.
 */
public class SearchResult implements Parcelable {

    String id, name, address;
    Integer radius;
    Float longitude, latitude;

    public SearchResult(String id, String name, String address, Integer radius, Float longitude, Float latitude) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.radius = radius;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.address);
        dest.writeValue(this.radius);
        dest.writeValue(this.longitude);
        dest.writeValue(this.latitude);
    }

    protected SearchResult(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.address = in.readString();
        this.radius = (Integer) in.readValue(Integer.class.getClassLoader());
        this.longitude = (Float) in.readValue(Float.class.getClassLoader());
        this.latitude = (Float) in.readValue(Float.class.getClassLoader());
    }

    public static final Creator<SearchResult> CREATOR = new Creator<SearchResult>() {
        @Override
        public SearchResult createFromParcel(Parcel source) {
            return new SearchResult(source);
        }

        @Override
        public SearchResult[] newArray(int size) {
            return new SearchResult[size];
        }
    };
}
