/*  _____ _______         _                      _
 * |_   _|__   __|       | |                    | |
 *   | |    | |_ __   ___| |___      _____  _ __| | __  ___ ____
 *   | |    | | '_ \ / _ \ __\ \ /\ / / _ \| '__| |/ / / __|_  /
 *  _| |_   | | | | |  __/ |_ \ V  V / (_) | |  |   < | (__ / /
 * |_____|  |_|_| |_|\___|\__| \_/\_/ \___/|_|  |_|\_(_)___/___|
 *                                _
 *              ___ ___ ___ _____|_|_ _ _____
 *             | . |  _| -_|     | | | |     |  LICENCE
 *             |  _|_| |___|_|_|_|_|___|_|_|_|
 *             |_|
 *
 *   PROGRAMOVÁNÍ  <>  DESIGN  <>  PRÁCE/PODNIKÁNÍ  <>  HW A SW
 *
 * Tento zdrojový kód je součástí výukových seriálů na
 * IT sociální síti WWW.ITNETWORK.CZ
 *
 * Kód spadá pod licenci prémiového obsahu a vznikl díky podpoře
 * našich členů. Je určen pouze pro osobní užití a nesmí být šířen.
 * Více informací na http://www.itnetwork.cz/licence
 */

/*
 * Implementace rozhraní PersonService.
 * Obsahuje metody předepsané rozhraním pro správu osob v systému a získávání informací o ních.
 */
package cz.itnetwork.service;

import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.dto.mapper.PersonMapper;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private PersonRepository personRepository;

    @Override
    public PersonDTO addPerson(PersonDTO personDTO) {
        PersonEntity entity = personMapper.toEntity(personDTO);
        entity = personRepository.save(entity);

        return personMapper.toDTO(entity);
    }

    @Override
    public void removePerson(long id) {
        PersonEntity person = fetchPersonById(id);
        person.setHidden(true);
        personRepository.save(person);
    }       // odstranění osoby - nejde o smazání, osoba zůstává v databázi jako skrytá

    @Override
    public List<PersonDTO> getAll() {
        return personRepository.findByHidden(false)
                .stream()
                .map(i -> personMapper.toDTO(i))
                .sorted(Comparator.comparing(p -> p.getName().toLowerCase()))
                .collect(Collectors.toList());
    }   // metoda vrací všeechny osoby, které nejsou skryté, seřazené podle abecedy

    @Override
    public PersonDTO getPerson(long id) {
        PersonEntity personEntity = fetchPersonById(id);
        return personMapper.toDTO(personEntity);
    }
    //vyhledá osobu podle id v databázi a vrátí DTO

    @Override
    public PersonDTO updatePerson(long id, PersonDTO person) {
        removePerson(id);
        person.setId(null);         //když nenastavíme id na null, přepíše se původní entita
        return addPerson(person);
        //úprava osoby - smaže původní a vytvoří novou!!!
    }


    private PersonEntity fetchPersonById(long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Person with id " + id + " wasn't found in the database."));
    }
    // privátní metoda, která vyhledá v databázi osobu podle id a vrátí entitu
}
