/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.PersonDTO;
import dtos.RenameMeDTO;
import entities.Person;
import entities.RenameMe;

import javax.persistence.EntityManagerFactory;

import utils.EMF_Creator;

/**
 * @author tha
 */
public class Populator {
    public static void populate() {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        FacadeExample fe = FacadeExample.getFacadeExample(emf);
        PersonFacade pf = PersonFacade.getInstance(emf);
        pf.addPerson("First 1", "Last 1", "12345678", "Firststreet 1", 1000, "City1");
        pf.addPerson("First 2", "Last 2", "88888888", "Secondstreet 2", 2000, "City2");
        pf.addPerson("First 3", "Last 3", "12121212", "Thirdstreet 3", 3000, "City3");

    }

    public static void main(String[] args) {
        populate();
    }
}
