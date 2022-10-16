package facades;

import dtos.PersonDTO;
import dtos.PersonsDTO;
import entities.Address;
import entities.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

public class PersonFacade implements IPersonFacade {
    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private PersonFacade() {
    }

    public static PersonFacade getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public PersonDTO addPerson(String fName, String lName, String phone, String address, int zip, String city) {
        Address a = new Address(address, zip, city);
        Person p = new Person(fName, lName, phone);
        p.setAddress(a);
        Date date = new Date();
        p.setCreated(date);
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(a);
            em.persist(p);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(p);
    }

    @Override
    public PersonDTO deletePerson(int id) {

        EntityManager em = getEntityManager();
        Person p = em.find(Person.class, id);
        if (p == null)
            throw new EntityNotFoundException("Could not remove Person with id: "+id);
        em.getTransaction().begin();
        //TODO also delete address
        em.remove(p.getAddress());
        em.remove(p);
        em.getTransaction().commit();
        em.close();
        return new PersonDTO(p);
    }

    @Override
    public PersonDTO getPerson(int id) {
        EntityManager em = getEntityManager();
        Person p = em.find(Person.class, id);
        return new PersonDTO(p);
    }

    @Override
    public PersonsDTO getAllPersons() {
        EntityManager em = getEntityManager();
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
        List<PersonDTO> personDTOList = PersonDTO.getPersonDTOList(query.getResultList());
        return new PersonsDTO(personDTOList);
    }

    @Override
    public PersonDTO editPerson(PersonDTO pNew) {
        //TODO, skal tilpasses det med address
        EntityManager em = getEntityManager();
        int id = pNew.getId();
        Person person = em.find(Person.class, id);
        //TODO, måske der skal if-sætninger på der tjekker om Stringene er tomme
        person.setfName(pNew.getFName());
        person.setlName(pNew.getLName());
        person.setPhone(pNew.getPhone());
        Date date = new Date();
        person.setLastEdited(date);
        try {
            em.getTransaction().begin();
            em.merge(person);
            em.getTransaction().commit();
            return new PersonDTO(person);
        } finally {
            em.close();
        }
    }
}
