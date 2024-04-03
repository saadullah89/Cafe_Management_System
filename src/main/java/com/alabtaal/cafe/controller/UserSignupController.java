package com.alabtaal.cafe.controller;

import com.alabtaal.cafe.config.StageManager;
import com.alabtaal.cafe.entity.UserEntity;
import com.alabtaal.cafe.enums.FxmlView;
import com.alabtaal.cafe.service.UserService;
import com.alabtaal.cafe.util.FxUtil;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class UserSignupController implements Initializable {

    private final UserService userService;

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField fatherNameTextField;
    @FXML
    private TextField mobileNumberTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField passwordShowField;
    @FXML
    private CheckBox passwordCheckBox;
    @FXML
    private Button signupButton;
    private final StageManager stageManager;

    public UserSignupController(UserService userService, @Lazy StageManager stageManager) {
        this.userService = userService;
        this.stageManager = stageManager;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        passwordShowField.setVisible(false);
     setupListeners();
     enableOrDisableSignupButton();
    }

    private void setupListeners() {
        nameTextField.textProperty().addListener(
                (observable, oldValue, newValue) -> onNameTextFieldChanged(oldValue, newValue)
        );
        fatherNameTextField.textProperty().addListener(
                (observable, oldValue, newValue) -> onFatherNameTextFieldChanged(oldValue, newValue)
        );
        mobileNumberTextField.textProperty().addListener(
                (observable, oldValue, newValue) -> onMobileNumberFieldChanged(oldValue, newValue)
        );
        usernameTextField.textProperty().addListener(
                (observable, oldValue, newValue) -> onUsernameFieldChanged(oldValue, newValue)
        );
        passwordField.textProperty().addListener(
                (observable, oldValue, newValue) -> onPasswordFieldChanged(oldValue, newValue)
        );
    }

    @FXML
    public void onNameTextFieldChanged(String oldValue,String newValue) {
        enableOrDisableSignupButton();
    }
    @FXML
    public void onFatherNameTextFieldChanged(String oldValue,String newValue) {
        enableOrDisableSignupButton();
    }

    @FXML
    public void onMobileNumberFieldChanged(String oldValue,String newValue) {
        try{
            Long.parseLong(newValue);
        }catch(Exception e){
            if(mobileNumberTextField.getLength() > 1){
                mobileNumberTextField.setText(oldValue);
            }else{
                mobileNumberTextField.setText("");
            }
        }
        enableOrDisableSignupButton();
    }
    @FXML
    public void onUsernameFieldChanged(String oldValue,String newValue) {
        enableOrDisableSignupButton();
    }
    @FXML
    public void onPasswordFieldChanged(String oldValue,String newValue) {
        enableOrDisableSignupButton();
    }

    @FXML
    public void onPasswordCheckBox(){
        if(passwordCheckBox.isSelected()){
            passwordShowField.setVisible(true);
            passwordShowField.setText(passwordField.getText());
            passwordField.setVisible(false);
        }else{
            passwordField.setText(passwordShowField.getText());
            passwordField.setVisible(true);
        }
    }

    @FXML
    public void onSignupButtonPressed() {
        final UserEntity entity = new UserEntity();
        entity.setName(nameTextField.getText());
        entity.setFatherName(fatherNameTextField.getText());
        UserEntity userEntity = userService.findByMobileNumber(Long.parseLong(mobileNumberTextField.getText()));
        if (userEntity != null) {
                FxUtil.showMessage(AlertType.ERROR, "This Mobile Number already has an account created!");
                mobileNumberTextField.requestFocus();
                return;
        }else{
            entity.setMobileNumber(Long.parseLong(mobileNumberTextField.getText()));
        }
        UserEntity user = userService.findByUsername(usernameTextField.getText());
        if(user != null){
                 FxUtil.showMessage(AlertType.ERROR,"This Username already has an account created!");
                usernameTextField.requestFocus();
                return;
        }else{
            entity.setUsername(usernameTextField.getText());
        }
        if(passwordField.getText().length() < 6){
            FxUtil.showMessage(AlertType.ERROR,"Please Enter Password at least 6 character");
            passwordField.requestFocus();
            return;
        }
        entity.setPassword(passwordField.getText());
        if (FxUtil.confirmation("Are You Sure to Add This User").getResult() ==ButtonType.OK){
            userService.save(entity);
            stageManager.switchScene(FxmlView.LOGIN);
        }
        }
    @FXML
    public void onLoginButtonPressed(){
        stageManager.switchScene(FxmlView.LOGIN);
    }
    @FXML
    public void onExitButtonPressed(){
        if (FxUtil.confirmation("Are You Sure to Exit The Application").getResult() ==ButtonType.OK){
            Platform.exit();
        }
    }

    private boolean isFormValid(){
        return nameTextField.getText().length() >= 3 &&
                fatherNameTextField.getText().length() >= 3 &&
                mobileNumberTextField.getText().length() >= 10 &&
                usernameTextField.getText().length() >= 3 &&
                passwordField.getText().length() >= 3;
    }
    private void enableOrDisableSignupButton(){
           signupButton.setDisable(!isFormValid());
    }
}
