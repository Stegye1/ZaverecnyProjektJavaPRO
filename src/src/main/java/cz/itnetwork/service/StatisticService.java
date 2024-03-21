package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.InvoiceStatisticsDTO;
import cz.itnetwork.dto.PersonStatisticsDTO;

import java.util.List;

public interface StatisticService {

    InvoiceStatisticsDTO getTotalStatistics();

    List<PersonStatisticsDTO> getPersonStatistics();

    List<InvoiceDTO> getPurchases(String identificationNumber);

    List<InvoiceDTO> getSales(String identificationNumber);
}
