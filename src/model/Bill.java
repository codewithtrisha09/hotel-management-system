package model;

public class Bill {
    private String name;
    private int roomNo;
    private int days;
    private int adults;
    private int kids;
    private boolean wifi;
    private boolean mattress;

    private double base;
    private double gst;
    private double discount;
    private double total;

    public Bill(String name, int roomNo, int days, int adults, int kids,
                boolean wifi, boolean mattress,
                double base, double gst, double discount, double total) {

        this.name = name;
        this.roomNo = roomNo;
        this.days = days;
        this.adults = adults;
        this.kids = kids;
        this.wifi = wifi;
        this.mattress = mattress;
        this.base = base;
        this.gst = gst;
        this.discount = discount;
        this.total = total;
    }

    public String getName() { return name; }
    public int getRoomNo() { return roomNo; }
    public int getDays() { return days; }
    public int getAdults() { return adults; }
    public int getKids() { return kids; }
    public boolean isWifi() { return wifi; }
    public boolean isMattress() { return mattress; }

    public double getBase() { return base; }
    public double getGst() { return gst; }
    public double getDiscount() { return discount; }
    public double getTotal() { return total; }
}