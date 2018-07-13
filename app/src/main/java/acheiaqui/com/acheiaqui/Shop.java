package acheiaqui.com.acheiaqui;

import android.widget.TextView;

public class Shop
{
    private String id;
    private String name;
    private String info;
    private String reference;
    private String food;
    private double latitude;
    private double longitude;

    public Shop(String name, String info, String reference, String food, double latitude, double longitude)
    {
        this.name = name;
        this.info = info;
        this.reference = reference;
        this.food = food;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Shop(){

    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}