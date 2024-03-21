package cz.itnetwork.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import cz.itnetwork.constant.Currency;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDTO {

        @JsonProperty("_id")
        private Long id;

        private int number;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private Date issued;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private Date dueDate;
        private String product;
        private double price;
        private Currency currency;
        private int vat;
        private String note;
        private PersonDTO supplier;
        private PersonDTO customer;

}

