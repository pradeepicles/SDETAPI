package pojo.user;

import lombok.Getter;
import lombok.Setter;
import pojo.user.address.Address;
import pojo.user.company.Company;
@Getter
@Setter

public class users {

    private int id;
    private String name;
    private String username;
    private String email;
    private Address address;
    private String phone;
    private String website;
    private Company company;





}
