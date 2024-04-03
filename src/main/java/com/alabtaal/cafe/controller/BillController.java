package com.alabtaal.cafe.controller;

import com.alabtaal.cafe.entity.BillEntity;
import com.alabtaal.cafe.service.BillService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

import static com.alabtaal.cafe.util.Bill.*;

@Controller
public class BillController implements Initializable {
    @FXML
    private Label customerName;
    @FXML
    private Label totalPrice;
    @FXML
    private Label receiveAmount;
    @FXML
    private Label change;
    @FXML
    private TableView<BillEntity> billTable;
    @FXML
    private TableColumn<BillEntity,String> nameColumn;
    @FXML
    private TableColumn<BillEntity,String> quantityColumn;
    @FXML
    private TableColumn<BillEntity,String> discountColumn;
    @FXML
    private TableColumn<BillEntity,String> priceColumn;
    @FXML
    private TableColumn<BillEntity,String> dateColumn;
    private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addLabelValues();
        setUpTable();
     }
    private void addLabelValues(){
        customerName.setText(customerNameOfBill);
        totalPrice.setText(totalPriceOfBill);
        receiveAmount.setText(receiveAmountOfBill);
        change.setText(changeOfBill);
    }
    private void setUpTable() {
        nameColumn.setCellValueFactory(
                (cellData) -> new SimpleStringProperty(cellData.getValue().getName())
        );
        quantityColumn.setCellValueFactory(
                (cellData) -> new SimpleStringProperty(String.valueOf(cellData.getValue().getQuantity()))
        );
        discountColumn.setCellValueFactory(
                (cellData) -> new SimpleStringProperty(String.valueOf(cellData.getValue().getDiscount()))
        );
        priceColumn.setCellValueFactory(
                (cellData) -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPrice()))
        );
        dateColumn.setCellValueFactory(
                (cellData) -> new SimpleStringProperty(String.valueOf(cellData.getValue().getSaleDate()))
        );
        billTable.setItems(FXCollections.observableList(billService.findAll()));
    }

}