package acheiaqui.com.acheiaqui;

public class Shop
{
    private String name;
    private String info;
    private String reference;
    private String foods;
    private double latitude;
    private double longitude;

    public Shop(String name, String info, String reference, String foods, double latitude, double longitude)
    {
        this.name = name;
        this.info = info;
        this.reference = reference;
        this.foods = foods;
        this.latitude = latitude;
        this.longitude = longitude;


    }

    public Shop(){

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

    public String getFoods() {
        return foods;
    }

    public void setFoods(String foods) {
        this.foods = foods;
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