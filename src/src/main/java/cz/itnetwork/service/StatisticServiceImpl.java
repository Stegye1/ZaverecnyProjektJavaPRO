package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.InvoiceStatisticsDTO;
import cz.itnetwork.dto.PersonStatisticsDTO;
import cz.itnetwork.dto.mapper.InvoiceMapper;
import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.repository.InvoiceRepository;
import cz.itnetwork.entity.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticServiceImpl implements StatisticService   {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private InvoiceMapper invoiceMapper;

    @Override
    public InvoiceStatisticsDTO getTotalStatistics() {
        List<Object[]> resultEUR = invoiceRepository.statisticsEUR();
        List<Object[]> resultCZK = invoiceRepository.statisticsCZK();
        Long totalNumberOfInvoices = 0L;
        Double totalEURAmountThisYear = 0.0;
        Double totalEURAmount = 0.0;
        Double totalCZKAmountThisYear = 0.0;
        Double totalCZKAmount = 0.0;

        if (resultEUR != null && !resultEUR.isEmpty()) {
            Object[] valuesEUR = resultEUR.get(0);

            totalEURAmountThisYear = (Double) valuesEUR[0];
            totalEURAmount = (Double) valuesEUR[1];
            totalNumberOfInvoices = (Long) valuesEUR[2];
        }
        if (resultCZK != null && !resultCZK.isEmpty()) {
            Object[] valuesCZK = resultCZK.get(0);

            totalCZKAmountThisYear = (Double) valuesCZK[0];
            totalCZKAmount = (Double) valuesCZK[1];
            totalNumberOfInvoices += (Long) valuesCZK[2];

        }
        return new InvoiceStatisticsDTO(totalEURAmountThisYear, totalEURAmount, totalCZKAmountThisYear, totalCZKAmount, totalNumberOfInvoices);
    }
    // metoda vrací součty hodnot faktur v CZK a v EUR celkové a letošní


    public List<PersonStatisticsDTO> getPersonStatistics() {
        List<Object[]> resultEUR = personRepository.statisticsEUR();
        List<Object[]> resultCZK = personRepository.statisticsCZK();
        List<PersonStatisticsDTO> statisticsDTOList = new ArrayList<>();

        for (Object[] valueEUR : resultEUR) {       //předání hodnot pro fa v EUR
            Long companyId = (Long) valueEUR[0];
            String companyName = (String) valueEUR[1];
            Double totalEURAmount = (Double) valueEUR[2];
            Double totalCZKAmount = 0.0;
            for (Object[] valueCZK : resultCZK) {   //doplnění faktur v CZK

                if ((Long) valueCZK[0] == companyId) {
                    totalCZKAmount = (Double) valueCZK[2];

                }
            }
            statisticsDTOList.add(new PersonStatisticsDTO(companyId, companyName, totalEURAmount, totalCZKAmount));
        }
        //kontrola, jestli chybí nějaká firma s pouze CZK fakturami + doplnění:

        for (Object[] valueCZK : resultCZK) {
            boolean missing = true;
            for (PersonStatisticsDTO statistic : statisticsDTOList) {
                if ((valueCZK[0]).equals(statistic.getId())) {
                    missing = false;
                    break;      //ukončí procházení DTO, pokud už je v něm statistika této osoby obsažena
                }
            }
            if (missing) {      //pokud není obsažena, tj. neměla fa v EUR, doplníme údaje o fakturách v CZK
                Long companyId = (Long) valueCZK[0];
                String companyName = (String) valueCZK[1];
                Double totalEURAmount = 0.0;
                Double totalCZKAmount = (Double) valueCZK[2];
                statisticsDTOList.add(new PersonStatisticsDTO(companyId, companyName, totalEURAmount, totalCZKAmount));
            }
        }
        return statisticsDTOList;
    }           // metoda vrací DTO se statistikami firem celkovými a letošními rozdělenými podle měny

    @Override
    public List<InvoiceDTO> getPurchases(String identificationNumber) {
        // hledám víc customerů se stejným IČ! Kvůli možnému přejmenování/jiné změně firmy.
        List<PersonEntity> customers = personRepository.findByIdentificationNumber(identificationNumber);
        List<InvoiceDTO> purchasesDTO = new ArrayList<>();
        for (PersonEntity customer : customers) {
            for (InvoiceEntity invoice : customer.getReceivedInvoices()) {
                purchasesDTO.add(invoiceMapper.toDTO(invoice));
            }
        }
        return purchasesDTO;       // metoda vrací seznam faktur přijatých zadaným odběratelem
                                    //spojí i skryté a nově otevřené firmy se stejným ič a jakoukoliv změnou!
    }

    @Override
    public List<InvoiceDTO> getSales(String identificationNumber) {
        // hledáme víc supplierů se stejným IČ! Kvůli možnému přejmenování/jiné změně firmy.
        // Najdeme všechny entity osoby se zadaným IČ a všechny projdeme
        List<PersonEntity> suppliers = personRepository.findByIdentificationNumber(identificationNumber);
        List<InvoiceDTO> salesDTO = new ArrayList<>();
        for (PersonEntity supplier : suppliers) {
            // Projdeme seznam faktur vystavených danou osobou
            for (InvoiceEntity invoice : supplier.getIssuedInvoices()) {
                // Převedeme entity faktur na DTO a přidáme je do výsledného seznamu
                salesDTO.add(invoiceMapper.toDTO(invoice));
            }
        }
        return salesDTO;
    }
                // metoda vrací seznam faktur vystavených dodavatelem
                // spojí i skryté a nově otevřené firmy se stejným ič a jakoukoliv změnou
}
