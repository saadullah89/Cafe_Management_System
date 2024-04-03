package com.alabtaal.cafe.controller;

import com.alabtaal.cafe.config.StageManager;
import com.alabtaal.cafe.entity.*;
import com.alabtaal.cafe.enums.FxmlView;
import com.alabtaal.cafe.repository.ItemSaleRepo;
import com.alabtaal.cafe.service.*;
import com.alabtaal.cafe.util.Bill;
import com.alabtaal.cafe.util.FxUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class ItemSaleController implements Initializable {

    @FXML
    private Label itemName;
    @FXML
    private Label itemPrice;
    @FXML
    private TextField discount;
    @FXML
    private Label totalPrice;
    @FXML
    private Label change;
    @FXML
    private TextField receiveAmount;
    @FXML
    private TextField customerName;
    @FXML
    private Button payButton;
    @FXML
    private ImageView itemImage;
    @FXML
    private ChoiceBox itemChooseChoiceBox;
    @FXML
    private Spinner<Integer> spinner;
    @FXML
    private TableView<ItemSaleEntity> saleTable;
    @FXML
    private TableColumn<ItemSaleEntity,String> nameColumn;
    @FXML
    private TableColumn<ItemSaleEntity,String> quantityColumn;
    @FXML
    private TableColumn<ItemSaleEntity,String> discountColumn;
    @FXML
    private TableColumn<ItemSaleEntity,String> priceColumn;
    @FXML
    private TableColumn<ItemSaleEntity,String> dateColumn;


    private final MenuManagementService menuManagementService;
    private final ItemSaleService saleService;
    private final StageManager stageManager;

    private final CustomerService customerService;

    private final BillService billService;

    private final ItemSale1Service sale1Service;

    public ItemSaleController(MenuManagementService menuManagementService,
                              ItemSaleService saleService, @Lazy StageManager stageManager, CustomerService customerService, BillService billService, ItemSale1Service sale1Service) {
        this.menuManagementService = menuManagementService;
        this.saleService = saleService;
        this.stageManager = stageManager;
        this.customerService = customerService;
        this.billService = billService;
        this.sale1Service = sale1Service;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        itemChooseChoiceBox.setItems(FXCollections.observableList(getItems()));
        spinner();
        setUpTable();
        setupListeners();
        enableOrDisablePayButton();

    }
    private void setupListeners() {
        discount.textProperty().addListener(
                (observable, oldValue, newValue) -> onDiscountChanged(oldValue, newValue)
        );
        
        receiveAmount.textProperty().addListener(
                (observable, oldValue, newValue) -> onReceiveAmountChanged(oldValue, newValue)
        );

    }

    private void onReceiveAmountChanged(String oldValue, String newValue) {
        enableOrDisablePayButton();
        try{
            Long.parseLong(newValue);
        }catch(Exception e){
            if(receiveAmount.getLength() > 1){
                receiveAmount.setText(oldValue);
            }else{
                receiveAmount.setText("");
            }
        }
    }

    private void onDiscountChanged(String oldValue, String newValue) {
        try{
            Long.parseLong(newValue);
        }catch(Exception e){
            if(discount.getLength() > 1){
                discount.setText(oldValue);
            }else{
                discount.setText("");
            }
        }
    }

    @FXML
    public void onChoiceBox(){
        itemName.setText(String.valueOf(itemChooseChoiceBox.getValue()));
        MenuManagementEntity entity = menuManagementService.findByNameIgnoreCase(itemName.getText());
        if(entity != null){
            itemImage.setImage(pathToImage(entity.getImagePath()));
            itemPrice.setText(String.valueOf(entity.getPrice()));
        }
    }
    private Image pathToImage(String path){
        String ap = "file:///" + path;
        Image image = new Image(ap);
        return image;
    }
    private List<MenuManagementEntity> findAll1() {
        return menuManagementService.findAll();
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
        saleTable.setItems(FXCollections.observableList(saleService.findAll()));
        saleTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldSelection, newSelection) -> {
                    if(newSelection != null){
                        itemChooseChoiceBox.setValue(newSelection.getName());
                    }else{itemChooseChoiceBox.setValue("");
                    }
                }
        );
    }
    @FXML
    public void onAddButtonPressed() {
        final ItemSaleEntity saleEntity = new ItemSaleEntity();
        final ItemSaleEntity findByNameEntity = saleService.findByNameIgnoreCase(itemName.getText());
        final MenuManagementEntity menuEntity =
                menuManagementService.findByNameIgnoreCase(String.valueOf(itemChooseChoiceBox.getValue()));
        saleEntity.setMenuManagementItem(menuEntity);
        saleEntity.setName(itemName.getText());
        if(menuEntity != null){
            if (menuEntity.getQuantity() < spinner.getValue()) {
                FxUtil.showMessage(Alert.AlertType.ERROR,
                        spinner.getValue() + " quantity not available only "
                                + menuEntity.getQuantity() + " quantity available");
                return;
            } else {
                saleEntity.setQuantity(Long.parseLong(String.valueOf(spinner.getValue())));
            }
            if(discount.getText().equals("")){
                discount.setText("0");
                saleEntity.setDiscount(Double.parseDouble(discount.getText()));
                double price = Double.parseDouble(itemPrice.getText());
                saleEntity.setPrice(price * spinner.getValue());
            }else{
                boolean discountCondition = Double.parseDouble(discount.getText()) <= 20 && Long.parseLong(discount.getText()) >= 0;
                if (discountCondition){
                    saleEntity.setDiscount(Double.parseDouble(discount.getText()));
                }else {
                    FxUtil.showMessage(Alert.AlertType.INFORMATION , "Please add discount less then 20");
                    return;
                }
                double price = Double.parseDouble(itemPrice.getText());
                double originalPrice = price * spinner.getValue();
                double discountPercentage = Double.parseDouble(discount.getText());
                Double discountedPrice = originalPrice - (originalPrice * (discountPercentage / 100));
                saleEntity.setPrice(discountedPrice);
            }
            saleEntity.setSaleDate(LocalDate.now());
            if (findByNameEntity != null){
                FxUtil.showMessage(Alert.AlertType.ERROR , "This Item Already added");
                return;
            }
            saleService.save(saleEntity);
            clearFields();
            saleTable.setItems(FXCollections.observableList(saleService.findAll()));
            totalPrice.setText(String.valueOf(totalPrice()));
        }else{
            FxUtil.showMessage(Alert.AlertType.INFORMATION, "Please choose item on choice box");
        }
    }
    @FXML
    public void onDeleteButtonPressed(){
        ItemSaleEntity entity = saleService.findByNameIgnoreCase(String.valueOf(itemChooseChoiceBox.getValue()));
        if(entity != null){
            try{
                if (FxUtil.confirmation("Are You Really Want to Delete This Item").getResult() == ButtonType.OK){
                    saleService.deleteByName(entity.getName());
                    saleTable.setItems(FXCollections.observableList(saleService.findAll()));
                    FxUtil.showMessage(Alert.AlertType.INFORMATION,"Delete Item successfully");
                }
            }catch (Exception e){
                 FxUtil.showMessage(Alert.AlertType.ERROR,e.getMessage());
            }
        }
    }
    private long totalPrice() {
        final List<ItemSaleEntity> items = saleService.findAll();
        long total = 0;
        for (ItemSaleEntity item : items) {
            total += item.getPrice();
        }
        return total;
    }
    private List<String> getItems() {
        List<String> itemNames = new ArrayList<>();
        final List<MenuManagementEntity> items = findAll1();
        for (MenuManagementEntity item : items) {
            itemNames.add(item.getName());
        }
        return itemNames;
    }
    private void spinner(){
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1,500);
        valueFactory.setValue(1);
        spinner.setValueFactory(valueFactory);
    }
    @FXML
    public void onBillButtonPressed(){
         stageManager.dialogScene(FxmlView.BILL);
    }

    private List<ItemSaleEntity> findAll() {
        return saleService.findAll();
    }
    
    public void onPayButtonPressed(){
        final CustomerEntity customer = new CustomerEntity();
        customer.setPrice(totalPrice());
        customer.setTotalQuantity(getQuantities());
        customer.setCashier(FxUtil.username);
        customer.setCustomerName(customerName.getText());
        boolean condition1 = Long.parseLong(receiveAmount.getText()) < totalPrice();
        boolean condition2 = Long.parseLong(receiveAmount.getText()) >= totalPrice();
      Long receiveAmountFromUser = Long.parseLong(receiveAmount.getText());
        if (totalPrice()>0){
            if (condition1){
                FxUtil.showMessage(Alert.AlertType.ERROR , "Your Amount is less then total price");
            }else if (condition2){
                customerService.save(customer);
                FxUtil.showMessage(Alert.AlertType.INFORMATION , "Payment Received");
                change.setText(String.valueOf(receiveAmountFromUser-totalPrice()));
                 if(!customerName.getText().equals("")){
                    Bill.customerNameOfBill = customerName.getText();
                } else{
                    Bill.customerNameOfBill  = "";
                }
                Bill.totalPriceOfBill = totalPrice.getText();
                Bill.receiveAmountOfBill = receiveAmount.getText();
                if(!change.equals("")){
                    Bill.changeOfBill = change.getText();
                }
                addSaleItemsInSaleTable();
                addSalesOnBill();
                updateQuantities();
                clearFields();
                saleService.deleteAll();
                saleTable.setItems(FXCollections.observableList(saleService.findAll()));
                stageManager.dialogScene(FxmlView.BILL);
            }
         }else {
            FxUtil.showMessage(Alert.AlertType.ERROR,"Please Add some items");
        }

    }

    private void addSalesOnBill(){
        List<BillEntity> entity = billService.findAll();
        if(entity != null){
            billService.deleteAll();
        }
        List<ItemSaleEntity> saleEntities = findAll();
        for (ItemSaleEntity saleEntity : saleEntities) {
            BillEntity billEntity = new BillEntity();
            billEntity.setName(saleEntity.getName());
            billEntity.setPrice(saleEntity.getPrice());
            billEntity.setQuantity(saleEntity.getQuantity());
            billEntity.setDiscount(saleEntity.getDiscount());
            billEntity.setSaleDate(saleEntity.getSaleDate());
            billService.save(billEntity);
        }
     }

     public void addSaleItemsInSaleTable(){
         List<ItemSaleEntity> saleEntities = findAll();
         for (ItemSaleEntity saleEntity : saleEntities){
             final MenuManagementEntity menuEntity =
                menuManagementService.findByNameIgnoreCase(String.valueOf(itemChooseChoiceBox.getValue()));
             ItemSaleEntity1 saleEntity1 = new ItemSaleEntity1();
             saleEntity1.setPrice(saleEntity.getPrice());
             saleEntity1.setQuantity(saleEntity.getQuantity());
             saleEntity1.setDiscount(saleEntity.getDiscount());
             saleEntity1.setSaleDate(saleEntity.getSaleDate());
             saleEntity1.setMenuManagementItem(menuEntity);
             sale1Service.save(saleEntity1);
         }
     }

    private Long getQuantities() {
        Long totalQuantity = 0L;
        final List<ItemSaleEntity> items = findAll();
        for (ItemSaleEntity item : items) {
            totalQuantity += item.getQuantity();
        }
        return totalQuantity;
    }

    public void updateQuantities(){
        final List<ItemSaleEntity> saleEntities = findAll();
        final List<MenuManagementEntity> menuManagementEntities = findAll1();
        for (ItemSaleEntity saleItem : saleEntities){
            for (MenuManagementEntity menuItem : menuManagementEntities){
                if (saleItem.getName().equals(menuItem.getName())){
                    menuItem.setQuantity(menuItem.getQuantity()-saleItem.getQuantity());
                    menuManagementService.save(menuItem);
                }
            }
        }
    }
   

    public void clearFields(){
        discount.setText("0");
        receiveAmount.clear();
        change.setText("0");
        customerName.setText("");
    }
    private boolean isFormValid(){
        return !receiveAmount.getText().isEmpty();
    }

    private void enableOrDisablePayButton(){
        payButton.setDisable(!isFormValid());
    }
}
