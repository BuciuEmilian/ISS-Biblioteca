package iss.library.libraryiss1.model;

public class Address implements Identifiable<Integer> {
    private String streetName;
    private int streetNumber;
    private String county;

    public Address() {
    }

    public Address(String streetName, int streetNumber, String county) {
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.county = county;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    private Integer id;
    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
}
