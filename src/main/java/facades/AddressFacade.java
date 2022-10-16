package facades;

import dtos.PersonDTO;
import entities.Address;
import entities.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Date;

public class AddressFacade {
    private static AddressFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private AddressFacade() {
    }

    public static AddressFacade getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new AddressFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    public Address createAddress(String address, int zip, String city) {
        Address a = new Address(address, zip, city);
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            //TODO skal knyttes til en person?
            em.persist(a);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return a;
    }
}
