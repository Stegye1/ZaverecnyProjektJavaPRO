/**
 * Třída InvoiceSpecification představuje specifikaci pro filtrování faktur pomocí kritérií definovaných
 * ve filtru InvoiceFilter.
 */

package cz.itnetwork.entity.repository.specification;

import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.InvoiceEntity_;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.PersonEntity_;
import cz.itnetwork.entity.filter.InvoiceFilter;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor    //při kompilaci třídě automaticky vygeneruje konstruktor se všemi atributy, které jsou označené jako final:
public class InvoiceSpecification implements Specification<InvoiceEntity> {
    private final InvoiceFilter filter;
    /**
     * Metoda toPredicate vytváří predikát pro filtraci faktur na základě kritérií definovaných v objektu InvoiceFilter.
     *
     * @param root = kořenový objekt kritérií/to, co chceme filtrovat
     * @param criteriaQuery objekt dotazu, který lze použít k vytvoření dotazu/nástroj pro tvorbu dotazů pro databázi
     * @param criteriaBuilder objekt pro vytváření různých kritérií
     * @return predikát, který reprezentuje kritéria pro filtrování faktur
     */
    @Override
    public Predicate toPredicate(Root<InvoiceEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getMinPrice() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(InvoiceEntity_.PRICE), filter.getMinPrice()));
        }
        if(filter.getMaxPrice() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(InvoiceEntity_.PRICE), filter.getMaxPrice()));
        }
        if (filter.getCustomerId() != null) {
            Join<InvoiceEntity, PersonEntity> customerJoin = root.join(InvoiceEntity_.CUSTOMER);
            predicates.add(criteriaBuilder.equal(customerJoin.get(PersonEntity_.ID), filter.getCustomerId()));
        }
        if (filter.getSupplierId() != null) {
            Join<InvoiceEntity, PersonEntity> supplierJoin = root.join(InvoiceEntity_.SUPPLIER);
            predicates.add(criteriaBuilder.equal(supplierJoin.get(PersonEntity_.ID), filter.getSupplierId()));
        }
        if(filter.getProduct() != null)  {
             predicates.add(criteriaBuilder.like(root.get(InvoiceEntity_.PRODUCT),"%"+filter.getProduct()+"%"));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}

