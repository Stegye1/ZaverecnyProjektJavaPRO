package cz.itnetwork.entity;

import cz.itnetwork.constant.Currency;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity(name = "invoice")
@Getter
@Setter
public class InvoiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int number;

    @Column(nullable = false)
    private Date issued;

    @Column(nullable = false)
    private Date dueDate;

    @Column(nullable = false)
    private String product;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private Currency currency;

    @Column(nullable = false)
    private int vat;            //procento DPH

    private String note;

    @ManyToOne
    private PersonEntity supplier;

    @ManyToOne
    private PersonEntity customer;

}
