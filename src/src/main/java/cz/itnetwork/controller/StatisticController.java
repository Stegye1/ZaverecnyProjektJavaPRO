package cz.itnetwork.controller;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.InvoiceStatisticsDTO;
import cz.itnetwork.dto.PersonStatisticsDTO;
import cz.itnetwork.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StatisticController {

    @Autowired
    private StatisticService statisticService;

    @GetMapping("/invoices/statistics")
    public InvoiceStatisticsDTO getInvoiceStatistics() {
        return statisticService.getTotalStatistics();
    }   // statistiky: součet hodnoty všech faktur za letošek, celkem a počet faktur

    @GetMapping("/persons/statistics")
    public List<PersonStatisticsDTO> getPersonStatistics() {
        return statisticService.getPersonStatistics();
    }   // statistiky: součet hodnoty faktur vydaných jednotlivými firmami za letošek a celkem

    @GetMapping("/identification/{identificationNumber}/purchases")
    public List<InvoiceDTO> getPurchases(@PathVariable String identificationNumber) {
        return statisticService.getPurchases(identificationNumber);
    }       //faktury přijaté odběratelem se zadaným IČ

    @GetMapping("/identification/{identificationNumber}/sales")
    public List<InvoiceDTO> getSales(@PathVariable String identificationNumber) {
        return statisticService.getSales(identificationNumber);
    }       //faktury vystavené dodavatelem se zadaným IČ

}






