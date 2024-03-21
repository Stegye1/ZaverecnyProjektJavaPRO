package cz.itnetwork.controller;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.entity.filter.InvoiceFilter;
import cz.itnetwork.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("")
    public InvoiceDTO addInvoice(@RequestBody InvoiceDTO invoiceDTO) {
        return invoiceService.addInvoice(invoiceDTO);
    }
    //uložení faktury

    @GetMapping({""})
    public List<InvoiceDTO> getInvoices(@RequestParam(value = "minPrice", required = false) Double minPrice,
                                        @RequestParam(value = "maxPrice", required = false) Double maxPrice,
                                        @RequestParam(value = "customerId", required = false) Long customerId,
                                        @RequestParam(value = "supplierId", required = false) Long supplierId,
                                        @RequestParam(value = "product", required = false) String product,
                                        @RequestParam(value = "limit", defaultValue = "20") int limit) {

        InvoiceFilter invoiceFilter = new InvoiceFilter();
        invoiceFilter.setMinPrice(minPrice);
        invoiceFilter.setMaxPrice(maxPrice);
        invoiceFilter.setCustomerId(customerId);
        invoiceFilter.setSupplierId(supplierId);
        invoiceFilter.setProduct(product);
        invoiceFilter.setLimit(limit);

        return invoiceService.getFilteredInvoices(invoiceFilter);
    }       // vypsání seznamu faktur podle hodnot ve filtru

//    @GetMapping({"/invoices"})
//    public List<InvoiceDTO> getInvoices(InvoiceFilter invoiceFilter) {
//        return invoiceService.getFilteredInvoices(invoiceFilter);
//    }           // mělo by zobrazit seznam faktur vyfiltrovaných podle požadavků v parametru, takto to bylo
                // ve filmové databázi, ale nefunguje mi to. Nahoře práce s položkami filtru podle návrhu ChatGPT.

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteInvoice(@PathVariable Long id) {
        invoiceService.removeInvoice(id);
    }       // smazání vybrané faktury

    @GetMapping("/{id}")
    public InvoiceDTO getInvoice(@PathVariable Long id) {
        return invoiceService.getInvoice(id);
    }        // zobrazení detailu zvolené faktury

    @PutMapping("/{id}")
    public InvoiceDTO editInvoice(@PathVariable Long id, @RequestBody InvoiceDTO invoice) {
        return invoiceService.editInvoice(id, invoice);
    }       //úprava faktury
}