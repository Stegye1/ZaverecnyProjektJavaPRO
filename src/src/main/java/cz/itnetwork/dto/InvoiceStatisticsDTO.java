package cz.itnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InvoiceStatisticsDTO {
   private Double currentYearEURSum;
   private Double allTimeEURSum;
   private Double currentYearCZKSum;
   private Double allTimeCZKSum;
   private Long allInvoicesCount;
}
