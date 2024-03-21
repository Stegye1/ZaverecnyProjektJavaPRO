/**
 * Třída reprezentující filtr pro vyhledávání faktur.
 * Tento filtr umožňuje filtrovat faktury podle různých kritérií, jako je produkt, cena, dodavatel,
 * zákazník a limit počtu výsledků.
 */

package cz.itnetwork.entity.filter;
import lombok.Data;

@Data
public class InvoiceFilter {

    private String product;
    private Double minPrice;
    private Double maxPrice;
    private Long supplierId;
    private Long customerId;
    private Integer limit;
}
