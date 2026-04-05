package model;

public class Member {
    private String fullName;
    private String dob; // yyyy-mm-dd
    private String anniversary; // yyyy-mm-dd
    private String contactNumber;
    private String address;

    public Member(String fullName, String dob, String anniversary, String contactNumber, String address) {
        this.fullName = fullName;
        this.dob = dob;
        this.anniversary = anniversary;
        this.contactNumber = contactNumber;
        this.address = address;
    }

    public String getFullName() { return fullName; }
    public String getDob() { return dob; }
    public String getAnniversary() { return anniversary; }
    public String getContactNumber() { return contactNumber; }
    public String getAddress() { return address; }
}