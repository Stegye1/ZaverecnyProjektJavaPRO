package cz.itnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PersonStatisticsDTO {
    private Long id;
    private String name;
    private Double totalSumEUR;
    private Double totalSumCZK;
}
