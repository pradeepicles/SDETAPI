package pojo.user.address;

import lombok.Getter;
import lombok.Setter;
import pojo.user.address.geo.Geo;
@Getter
@Setter
public class Address {
    private String street;
    private String suite;
    private String city;
    private String zipcode;
    private Geo geo;

}
