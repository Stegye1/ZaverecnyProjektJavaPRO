/*
 * Implementace rozhraní InvoiceService.
 * Obsahuje metody pro správu faktur a získávání informací o ních.
 */
package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.mapper.InvoiceMapper;
import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.filter.InvoiceFilter;
import cz.itnetwork.entity.repository.InvoiceRepository;
import cz.itnetwork.entity.repository.PersonRepository;
import cz.itnetwork.entity.repository.specification.InvoiceSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceMapper invoiceMapper;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private PersonRepository personRepository;

    @Override
    public InvoiceDTO addInvoice(InvoiceDTO invoiceDTO) {
        InvoiceEntity entity = invoiceMapper.toEntity(invoiceDTO);
        entity = invoiceRepository.save(entity);
        //teď připojíme údaje o odběrateli a dodavateli
        entity.setSupplier(personRepository.getReferenceById(invoiceDTO.getSupplier().getId()));
        entity.setCustomer(personRepository.getReferenceById(invoiceDTO.getCustomer().getId()));
        InvoiceEntity saved = invoiceRepository.save(entity);

        return invoiceMapper.toDTO(saved);
    }    // založení faktury vrací v údajích faktury i údaje odběratele a dodavatele


    @Override
    public void removeInvoice(long id) {
        InvoiceEntity entity = fetchInvoiceById(id);
        invoiceRepository.delete(entity);
    }   // smazání faktury - nevrací nic

    @Override
    public List<InvoiceDTO> getFilteredInvoices(InvoiceFilter invoiceFilter) {
        InvoiceSpecification invoiceSpecification = new InvoiceSpecification(invoiceFilter);

        return invoiceRepository.findAll(invoiceSpecification, PageRequest.of(0, invoiceFilter.getLimit()))
                .stream()
                .map(invoiceMapper::toDTO)
                .collect(Collectors.toList());
    }       //vypíše faktury vyfiltrované podle zadaných kritérií


    @Override
    public InvoiceDTO getInvoice(long id) {
        InvoiceEntity entity = fetchInvoiceById(id);
        return invoiceMapper.toDTO(entity);
    }
    //vyhledání faktury, vrací fakturu i s detaily odběratele a dodavatele

    @Override
    public InvoiceDTO editInvoice(long id, InvoiceDTO data) {
        InvoiceEntity entity = fetchInvoiceById(id); //najde fa v databázi

        data.setId(id);                 //proč bez toho byla v id null??
        invoiceMapper.updateInvoiceEntity(data, entity); //upraví data entity
        entity.setSupplier(personRepository.getReferenceById(data.getSupplier().getId()));
        entity.setCustomer(personRepository.getReferenceById(data.getCustomer().getId()));
        invoiceRepository.save(entity);                      // uloží entitu
        return invoiceMapper.toDTO(entity);                  //a vytvoří z ní DTO, kt. vrátí
    }   // upraví fakturu v databázi podle obdržených dat a vrátí upravenou fa včetně odb a dod


     private InvoiceEntity fetchInvoiceById(long id) {
            return invoiceRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Invoice with id " + id + " wasn't found in the database."));
        }    //privátní metoda k použití v ostatních metodách této třídy, hledá + kontroluje, jestli
            // je vůbec hledaná fa v databázi
}
