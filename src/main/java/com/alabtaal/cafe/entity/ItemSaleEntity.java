package com.alabtaal.cafe.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@Table(schema = "cafe" , name = "item_sale", uniqueConstraints = {
        @UniqueConstraint(name = "item_sale_uk", columnNames = "name")
})
public class ItemSaleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "price")
    private Double price;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "sale_date")
    private LocalDate saleDate;

    @ManyToOne
    private MenuManagementEntity menuManagementItem;
}
