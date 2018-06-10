package acheiaqui.com.acheiaqui;

public class Shop
{
    private String name;
    private String info;
    private String address;
    private String reference;

    public Shop(String name, String info, String address, String reference)
    {
        this.name = name;
        this.info = info;
        this.address = address;
        this.reference = reference;

    }

    public String getName() {

        return this.name;
    }

    public String getInfo() {
        return this.info;
    }

    public String getPlace() {
        return this.address;
    }

    public String getReference() {
        return this.reference;
    }
}
