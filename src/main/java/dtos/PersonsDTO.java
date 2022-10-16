package dtos;

import dtos.PersonDTO;
import entities.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonsDTO {
    private List<PersonDTO> all = new ArrayList<>();

    public PersonsDTO(List<PersonDTO> personDTOList) {
        all.addAll(personDTOList);
    }

//    public PersonsDTO(List<Person> personEntities) {
//        personEntities.forEach((p) -> {
//            all.add(new PersonDTO(p));
//        });
//    }

    public List<PersonDTO> getAll() {
        return all;
    }
}
