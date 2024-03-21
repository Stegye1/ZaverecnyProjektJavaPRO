/*
 * Rozhraní pro mapování mezi objekty tříd InvoiceDTO a InvoiceEntity.
 */

package cz.itnetwork.dto.mapper;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.entity.InvoiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


/* knihovna Mapsturct najde všechna rozhraní s anotací @Mapper a podle hlaviček metod automaticky vygeneruje
logiku pro převod tříd.
*/
@Mapper(componentModel = "spring")
public interface InvoiceMapper {
    InvoiceEntity toEntity(InvoiceDTO source);

    InvoiceDTO toDTO(InvoiceEntity source);

    //	updateInvoiceEntity() zkopíruje data z přepravky do existující entity za účelem aktualizace
    //	záznamu v databázi daty z přepravky.
    @Mapping(target="supplier", ignore = true)
    @Mapping(target="customer", ignore = true)
    void updateInvoiceEntity(InvoiceDTO source, @MappingTarget InvoiceEntity target);

}
