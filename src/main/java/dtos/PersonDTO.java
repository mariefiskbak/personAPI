package dtos;

import entities.Address;
import entities.Person;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link entities.Person} entity
 */
public class PersonDTO implements Serializable {
    private Integer id;
    private final String fName;
    private final String lName;
    private final String phone;
    private Address address;

    public PersonDTO(Person person) {
        this.id = person.getId();
        this.fName = person.getfName();
        this.lName = person.getlName();
        this.phone = person.getPhone();
    }

    public PersonDTO(Integer id, String fName, String lName, String phone) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.phone = phone;
    }

    public static List<PersonDTO> getPersonDTOList(List<Person> personList) {
        List<PersonDTO> personDTOList = new ArrayList<>();
        personList.forEach(person -> personDTOList.add(new PersonDTO(person)));
        return personDTOList;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public String getFName() {
        return fName;
    }

    public String getLName() {
        return lName;
    }

    public String getPhone() {
        return phone;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonDTO entity = (PersonDTO) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.fName, entity.fName) &&
                Objects.equals(this.lName, entity.lName) &&
                Objects.equals(this.phone, entity.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fName, lName, phone);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "fName = " + fName + ", " +
                "lName = " + lName + ", " +
                "phone = " + phone + ")";
    }
}