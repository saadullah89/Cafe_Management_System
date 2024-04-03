package com.alabtaal.cafe.controller;

import com.alabtaal.cafe.entity.MenuManagementEntity;
import com.alabtaal.cafe.service.MenuManagementService;
import com.alabtaal.cafe.util.FxUtil;
import com.alabtaal.cafe.util.ImageUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class MenuManagementController implements Initializable {

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextField quantityTextField;
    @FXML
    private TextField searchTextField;
    @FXML
    private ChoiceBox itemTypeChoiceBox;
    @FXML
    private ChoiceBox statusChoiceBox;
    @FXML
    private ImageView imageView;
    @FXML
    private TableView<MenuManagementEntity> menuManagementTable;
    @FXML
    private TableColumn<MenuManagementEntity , String> nameColumn;
    @FXML
    private TableColumn<MenuManagementEntity , String> typeColumn;
    @FXML
    private TableColumn<MenuManagementEntity , String> priceColumn;
    @FXML
    private TableColumn<MenuManagementEntity , String> quantityColumn;
    @FXML
    private TableColumn<MenuManagementEntity , String> statusColumn;
    @FXML
    private TableColumn<MenuManagementEntity , String> dateColumn;
    private final MenuManagementService menuManagementService;

    public MenuManagementController(MenuManagementService menuManagementService) {
        this.menuManagementService = menuManagementService;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        itemTypeChoiceBox.setItems(FXCollections.observableList(getItemTypes()));
        statusChoiceBox.setItems(FXCollections.observableList(getStatus()));
        setUpTable();
        setupListeners();
    }

    private void setupListeners() {
        priceTextField.textProperty().addListener(
                (observable, oldValue, newValue) -> onPriceTextFieldChanged(oldValue, newValue)
        );

        quantityTextField.textProperty().addListener(
                (observable, oldValue, newValue) -> onQuantityTextFieldChanged(oldValue, newValue)
        );

    }
    private void setUpTable(){
        nameColumn.setCellValueFactory(
                (cellData) -> new SimpleStringProperty(cellData.getValue().getName())
        );
        typeColumn.setCellValueFactory(
                (cellData) -> new SimpleStringProperty(cellData.getValue().getItemType())
        );
        priceColumn.setCellValueFactory(
                (cellData) -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPrice()))
        );
        quantityColumn.setCellValueFactory(
                (cellData) -> new SimpleStringProperty(String.valueOf(cellData.getValue().getQuantity()))
        );
        statusColumn.setCellValueFactory(
                (cellData) -> new SimpleStringProperty(cellData.getValue().getStatus())
        );
        dateColumn.setCellValueFactory(
                (cellData) -> new SimpleStringProperty(String.valueOf(cellData.getValue().getAddOrUpdateDate()))
        );
        menuManagementTable.setItems(FXCollections.observableList(findAll()));
        menuManagementTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldSelection, newSelection) -> {
                    if (newSelection != null){
                        nameTextField.setText(newSelection.getName());
                        priceTextField.setText(String.valueOf(newSelection.getPrice()));
                        quantityTextField.setText(String.valueOf(newSelection.getQuantity()));
                        if(newSelection.getImagePath() != null){
                            imageView.setImage(pathToImage(newSelection.getImagePath()));
                        }else{
                            imageView.setImage(null);
                        }
                        if (newSelection.getItemType() != null){
                            itemTypeChoiceBox.setValue(newSelection.getItemType());
                        }else {
                            itemTypeChoiceBox.setValue("");
                        }
                        if (newSelection.getStatus() != null){
                           statusChoiceBox.setValue(newSelection.getStatus());
                        }else {
                            statusChoiceBox.setValue("");
                        }
                    }
                }
        );
    }
    @FXML
    public void onAddButtonPressed() throws IOException {
        final MenuManagementEntity entity = new MenuManagementEntity();
        MenuManagementEntity name = menuManagementService.findByNameIgnoreCase(nameTextField.getText());
        if (nameTextField.getText().isEmpty()) {
            FxUtil.showMessage(Alert.AlertType.ERROR, "Name must be entered");
            return;
        }
            entity.setName(nameTextField.getText());
        if (priceTextField.getText().isEmpty()) {
            FxUtil.showMessage(Alert.AlertType.ERROR, "Price must be entered");
            return;
        }
        entity.setPrice(Double.valueOf(priceTextField.getText()));
        if (quantityTextField.getText().isEmpty()) {
            FxUtil.showMessage(Alert.AlertType.ERROR, "Quantity must be entered");
            return;
        }
        entity.setQuantity(Long.valueOf(quantityTextField.getText()));
        if (itemTypeChoiceBox.getValue() == null) {
            FxUtil.showMessage(Alert.AlertType.ERROR, "Please choose item type");
            return;
        }
        entity.setItemType((String) itemTypeChoiceBox.getValue());
        if (statusChoiceBox.getValue() == null) {
            FxUtil.showMessage(Alert.AlertType.ERROR, "Please choose status");
            return;
        }
        entity.setStatus((String) statusChoiceBox.getValue());
        entity.setAddOrUpdateDate(LocalDate.now());
        if(imageView.getImage() != null){
            entity.setImagePath(getImage(imageToPath()));
        }
        if (name==null){
            menuManagementService.save(entity);
            FxUtil.showMessage(Alert.AlertType.INFORMATION , "Item saved Successfully");
            menuManagementTable.setItems(FXCollections.observableList(findAll()));
            onClearButtonPressed();
        }else{
            FxUtil.showMessage(Alert.AlertType.INFORMATION , "This item name is Already exists");
        }
    }
    private Image pathToImage(String path){
        String ap = "file:///" + path;
        Image image = new Image(ap);
        return image;
    }
    private File imageToPath(){
        if(imageView != null){
            try{
                final  String path = imageView.getImage().getUrl();
                File image = new File(new URL(path).toURI());
                return image;
            }catch(Exception e){
                FxUtil.showMessage(Alert.AlertType.ERROR,e.getMessage());
            }
        }
        return null;
    }
    private String getImage(File imageFile) throws IOException {
        ImageUtil.saveImage(imageFile);
        String projectDirectory = System.getProperty("user.dir"); // Get current working directory
        String imagePath = projectDirectory + File.separator +"src"+ File.separator
                +"main"+ File.separator +"resources"+ File.separator + imageFile.getName();
        return imagePath;
    }
    @FXML
    public void onUpdateButtonPressed() throws IOException {
        final MenuManagementEntity entity = menuManagementService.findByNameIgnoreCase(nameTextField.getText());
        if (nameTextField.getText().isEmpty()) {
            FxUtil.showMessage(Alert.AlertType.ERROR, "Name must be entered");
            return;
        }
        if (priceTextField.getText().isEmpty()) {
            FxUtil.showMessage(Alert.AlertType.ERROR, "Price must be entered");
            return;
        }
        if (quantityTextField.getText().isEmpty()) {
            FxUtil.showMessage(Alert.AlertType.ERROR, "Quantity must be entered");
            return;
        }
        if (itemTypeChoiceBox.getValue() == null) {
            FxUtil.showMessage(Alert.AlertType.ERROR, "Please choose item type");
            return;
        }
        if (statusChoiceBox.getValue() == null) {
            FxUtil.showMessage(Alert.AlertType.ERROR, "Please choose status");
            return;
        }
        if (entity != null) {
            entity.setPrice(Double.parseDouble(priceTextField.getText()));
            entity.setQuantity(Long.parseLong(quantityTextField.getText()));
            entity.setItemType(String.valueOf(itemTypeChoiceBox.getValue()));
            entity.setStatus(String.valueOf(statusChoiceBox.getValue()));
            entity.setAddOrUpdateDate(LocalDate.now());
            if(imageView.getImage() != null){
                entity.setImagePath(getImage(imageToPath()));
            }
            if (FxUtil.confirmation("Are You Really Want to Update This Item").getResult() == ButtonType.OK){
                menuManagementService.update(entity);
                FxUtil.showMessage(Alert.AlertType.INFORMATION,"Item updated successfully");
                menuManagementTable.setItems(FXCollections.observableList(findAll()));
                onClearButtonPressed();
            }

            } else{
            FxUtil.showMessage(Alert.AlertType.ERROR,nameTextField.getText() + " is not found in table");
        }
    }

    @FXML
    public void onDeleteButtonPressed(){
        MenuManagementEntity name = menuManagementService.findByNameIgnoreCase(nameTextField.getText());
        if (nameTextField.getText().isEmpty()) {
            FxUtil.showMessage(Alert.AlertType.ERROR, "Name must be entered");
            return;
        }
                  try{
                      if(name != null){
                          if (FxUtil.confirmation("Are You Really Want to Delete This Item").getResult() == ButtonType.OK){
                              menuManagementService.deleteByName(nameTextField.getText());
                              FxUtil.showMessage(Alert.AlertType.INFORMATION,"Item deleted successfully");
                              menuManagementTable.setItems(FXCollections.observableList(findAll()));
                              onClearButtonPressed();
                          }
                      }else{
                          FxUtil.showMessage(Alert.AlertType.INFORMATION , "This item name is not exists");
                      }
                 }catch (Exception e){
                     FxUtil.showMessage(Alert.AlertType.ERROR,e.getMessage());
                 }
     }
    public void onSearchButtonPressed(){
        MenuManagementEntity entity = menuManagementService.findByNameIgnoreCase(searchTextField.getText());
        if(entity != null){
            nameTextField.setText(entity.getName());
            priceTextField.setText(String.valueOf(entity.getPrice()));
            quantityTextField.setText(String.valueOf(entity.getQuantity()));
            itemTypeChoiceBox.setValue(entity.getItemType());
            statusChoiceBox.setValue(entity.getStatus());
            if(entity.getImagePath() != null){
                imageView.setImage(pathToImage(entity.getImagePath()));
            }
        }else {
            FxUtil.showMessage(Alert.AlertType.ERROR,"Item not found");
        }
    }
    private static List<String> getItemTypes(){
        return List.of("Meals", "Drinks");
    }

    private static  List<String> getStatus(){
        return List.of("Available" , "Not Available");
    }

    private List<MenuManagementEntity> findAll(){
       return menuManagementService.findAll();
    }

    @FXML
    public void onImportImageButtonPressed(){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter
                ("Image files (*.png, *.jpg, *.jpeg)", "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(extFilter);
         File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                Image image = new Image(file.toURI().toString());
                imageView.setImage(image);
            } catch (Exception e) {
                 e.printStackTrace();
            }
        }
    }
    @FXML
    public void onClearButtonPressed(){
        nameTextField.clear();
        priceTextField.clear();
        quantityTextField.clear();
        itemTypeChoiceBox.setValue("");
        statusChoiceBox.setValue("");
        searchTextField.clear();
        imageView.setImage(null);
 }
    @FXML
    public void onPriceTextFieldChanged(String oldValue, String newValue) {
        try{
            Double.parseDouble(newValue);
        }catch(Exception e){
            if(priceTextField.getLength() > 1){
                priceTextField.setText(oldValue);
            }else{
                priceTextField.setText("");
            }
        }
    }

    @FXML
    public void onQuantityTextFieldChanged(String oldValue, String newValue) {
        try{
            Double.parseDouble(newValue);
        }catch(Exception e){
            if(quantityTextField.getLength() > 1){
                quantityTextField.setText(oldValue);
            }else{
                quantityTextField.setText("");
            }
        }
    }

}
