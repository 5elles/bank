package it.academy;

import it.academy.model.dao.impl.CountryDaoImpl;
import it.academy.model.dao.impl.PersonDaoImpl;
import it.academy.model.dao.impl.RegionDaoImpl;
import it.academy.model.entity.Country;
import it.academy.model.entity.Person;
import it.academy.model.entity.Region;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CountryDaoImpl countryDao = new CountryDaoImpl();
        RegionDaoImpl regionDao = new RegionDaoImpl();
        System.out.println(regionDao.deleteById(4));





//        System.out.println(
//                personDaoImpl.delete(Person.builder()
//                        .id(2)
//                        .firstName("Лев")
//                        .middleName("Петрович")
//                        .lastName("Силкин")
//                        .dateOfBirth(LocalDate.ofEpochDay(1975 - 05 - 27))
//                        .citizenIdNumber("150289PB1")
//                        .passportNumber("KH0834615")
//                        .isStaff(true)
//                        .isClient(false)
//                        .build())
//        );



        System.out.println("####################################");
        List<Region> regions = regionDao.getALl();
        regions.forEach(System.out::println);

//        System.out.println(countryDao.getCount());
//        List<Person> persons = personDaoImpl.getALl();
//        persons.forEach(System.out::println);
    }
}