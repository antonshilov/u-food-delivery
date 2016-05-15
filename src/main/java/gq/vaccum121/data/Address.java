package gq.vaccum121.data;

import org.springframework.data.annotation.Id;

/**
 * Created by vaccum121 on 11.04.16.
 */
public class Address {

    @Id
    private String id;

    private String city;
    private String building;
    private String street;

    public Address() {
    }

    public Address(String street, String building) {
        this.city = "Krasnoyarsk";
        this.building = building;
        this.street = street;
    }

    public Address(String city, String building, String street) {
        this.city = city;
        this.building = building;
        this.street = street;
    }

    @Override
    public String toString() {
        return String.format("Customer[id=%s, city='%s', building='%s']",
                id, city, building);
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
