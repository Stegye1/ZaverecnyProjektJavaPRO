/**
 * Rozhraní definující službu pro práci s fakturami.
 * Předepisuje metody pro přidání, odebrání, editaci a získání faktur,
 * aplikování filtru na seznam faktur.
 */

package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.entity.filter.InvoiceFilter;

import java.util.List;

public interface InvoiceService {
    InvoiceDTO addInvoice(InvoiceDTO invoiceDTO);

   void removeInvoice(long id);

    List<InvoiceDTO> getFilteredInvoices(InvoiceFilter invoicesFilter);

    InvoiceDTO getInvoice(long id);

   InvoiceDTO editInvoice(long id, InvoiceDTO invoice);
}

