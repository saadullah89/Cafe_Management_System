package com.alabtaal.cafe.controller;

import com.alabtaal.cafe.entity.CustomerEntity;
import com.alabtaal.cafe.entity.ItemSaleEntity1;
import com.alabtaal.cafe.service.CustomerService;
import com.alabtaal.cafe.service.ItemSale1Service;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class CustomerController implements Initializable {

    @FXML
    private TableView<CustomerEntity> customerTable;

    @FXML
    private TableColumn<CustomerEntity, String> customerIDColumn;

    @FXML
    private TableColumn<CustomerEntity, String> totalPriceColumn;
    @FXML
    private TableColumn<CustomerEntity, String> totalQuantityColumn;

    @FXML
    private TableColumn<CustomerEntity, String> dateColumn;

    @FXML
    private TableColumn<CustomerEntity, String> cashierColumn;

    @FXML
    private TableColumn<CustomerEntity, String> customerNameColumn;

    @FXML
    private TableView<ItemSaleEntity1> saleTable;

    @FXML
    private TableColumn<ItemSaleEntity1, String> saleIdColumn;


    @FXML
    private TableColumn<ItemSaleEntity1, String> saleItemPriceColumn;

    @FXML
    private TableColumn<ItemSaleEntity1, String> saleItemQuantityColumn;

    @FXML
    private TableColumn<ItemSaleEntity1, String> saleItemDiscountColumn;

    @FXML
    private TableColumn<ItemSaleEntity1, String> menuManagementIdColumn;

    @FXML
    private TableColumn<ItemSaleEntity1, String> saleItemSaleDateColumn;
    private final CustomerService customerService;

    private final ItemSale1Service sale1Service;

    public CustomerController(CustomerService customerService, ItemSale1Service sale1Service) {
        this.customerService = customerService;
        this.sale1Service = sale1Service;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupCustomerTable();
        setupSaleTable();
    }

    private List<CustomerEntity> findAll(){
       return customerService.findAll();
    }

    private List<ItemSaleEntity1> findAll1(){
        return sale1Service.findAll();
    }


    public void setupCustomerTable(){
        customerIDColumn.setCellValueFactory(
                (cellData) -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId()))
        );
        totalPriceColumn.setCellValueFactory(
                (cellData) -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPrice()))
        );
        totalQuantityColumn.setCellValueFactory(
                (cellData) -> new SimpleStringProperty(String.valueOf(cellData.getValue().getTotalQuantity()))
        );
        dateColumn.setCellValueFactory(
                (cellData) -> new SimpleStringProperty(String.valueOf(cellData.getValue().getDate()))
        );
        cashierColumn.setCellValueFactory(
                (cellData) -> new SimpleStringProperty(cellData.getValue().getCashier())
        );
        customerNameColumn.setCellValueFactory(
                (cellData) -> new SimpleStringProperty(cellData.getValue().getCustomerName())
        );
        customerTable.setItems(FXCollections.observableList(findAll()));
    }

    private void setupSaleTable(){
        saleIdColumn.setCellValueFactory(
                (cellData) -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId()))
        );
        saleItemPriceColumn.setCellValueFactory(
                (cellData) -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPrice()))
        );
        saleItemQuantityColumn.setCellValueFactory(
                (cellData) -> new SimpleStringProperty(String.valueOf(cellData.getValue().getQuantity()))
        );
        saleItemDiscountColumn.setCellValueFactory(
                (cellData) -> new SimpleStringProperty(String.valueOf(cellData.getValue().getDiscount()))
        );
        saleItemSaleDateColumn.setCellValueFactory(
                (cellData) -> new SimpleStringProperty(String.valueOf(cellData.getValue().getSaleDate()))
        );
        menuManagementIdColumn.setCellValueFactory(
                (cellData) -> new SimpleStringProperty(String.valueOf(cellData.getValue().getMenuManagementItem().getId()))
        );
        saleTable.setItems(FXCollections.observableList(findAll1()));
    }
}
