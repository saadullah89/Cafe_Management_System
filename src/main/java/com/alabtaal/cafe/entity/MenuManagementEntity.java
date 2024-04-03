package com.alabtaal.cafe.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import javafx.scene.image.Image;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(schema = "cafe" , name = "menu_management", uniqueConstraints = {
        @UniqueConstraint(name = "menu_management_uk", columnNames = "name")
})
public class MenuManagementEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "quantity")
    private Long quantity;
    @NotNull
    @Column(name = "price")
     private Double price;

    @Column(name = "item_type")
    private String itemType;

    @Column(name = "status")
    private String status;

    @Column(name = "image")
    private String imagePath;

    @Column(name = "date")
    private LocalDate addOrUpdateDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "menuManagementItem")
    private List<ItemSaleEntity> itemSales;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "menuManagementItem")
    private List<ItemSaleEntity1> itemSales1;
}
