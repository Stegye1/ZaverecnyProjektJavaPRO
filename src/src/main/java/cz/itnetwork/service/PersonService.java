/**
 * Rozhraní definující službu pro práci s osobami.
 * Předepisuje metody pro přidání, odebrání, aktualizaci a získání osob,
 * získání seznamu všech osob.
 */

package cz.itnetwork.service;

import cz.itnetwork.dto.PersonDTO;

import java.util.List;

public interface PersonService {
    PersonDTO addPerson(PersonDTO personDTO);

    void removePerson(long id);

    List<PersonDTO> getAll();

    PersonDTO getPerson(long id);

    PersonDTO updatePerson(long id, PersonDTO person);
}
