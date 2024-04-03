package com.alabtaal.cafe.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@Table(schema = "cafe" , name = "customer" )
public class CustomerEntity{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "Total_quantities")
    private Long totalQuantity;

    @Column(name = "price")
    private Long price;

    @Column(name = "Date")
    private LocalDate date = LocalDate.now();

    @Column(name = "Cashier_name")
    private String cashier;

    @Column(name = "Customer_name")
    private String customerName;
}
