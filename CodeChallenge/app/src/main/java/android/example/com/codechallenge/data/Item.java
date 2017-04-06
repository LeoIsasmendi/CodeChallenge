package android.example.com.codechallenge.data;


import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {

    private Long userId;
    private String firstName;
    private String lastName;
    private String photo;
    private String thumb;

    public Long getUserId() {
        return userId;
    }

    public Item setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Item setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Item setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPhoto() {
        return photo;
    }

    public Item setPhoto(String photo) {
        this.photo = photo;
        return this;
    }

    public String getThumb() {
        return thumb;
    }

    public Item setThumb(String thumb) {
        this.thumb = thumb;
        return this;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.userId);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.photo);
        dest.writeString(this.thumb);
    }

    public Item() {
    }

    protected Item(Parcel in) {
        this.userId = (Long) in.readValue(Long.class.getClassLoader());
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.photo = in.readString();
        this.thumb = in.readString();
    }

    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}
