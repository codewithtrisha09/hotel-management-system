package model;
public class Booking {
    private String name;
    private int roomNo;
    private boolean wifi;
    private int days;
    private boolean member;
    private double bill;
    private int adults;
    private int kids;
    private boolean extraMattress;

    public Booking(String name, int roomNo, boolean wifi, int days, boolean member, double bill,
                   int adults, int kids, boolean extraMattress) {
        this.name = name;
        this.roomNo = roomNo;
        this.wifi = wifi;
        this.days = days;
        this.member = member;
        this.bill = bill;
        this.adults = adults;
        this.kids = kids;
        this.extraMattress = extraMattress;
    }

    public String getName() { return name; }
    public int getRoomNo() { return roomNo; }
    public boolean isWifi() { return wifi; }
    public int getDays() { return days; }
    public boolean isMember() { return member; }
    public void setMember(boolean member) { this.member = member; }
    public double getBill() { return bill; }
    public int getAdults() { return adults; }
    public int getKids() { return kids; }
    public boolean isExtraMattress() { return extraMattress; }
}
