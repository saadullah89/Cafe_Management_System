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
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;
@Controller
public class ChangePasswordController implements Initializable {


    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField mobileNoTextField;

    @FXML
    private PasswordField newPasswordField;

    private final StageManager stageManager;

    private final UserService userService;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    setupListeners();
    getUsername();
    }

    public ChangePasswordController(@Lazy StageManager stageManager, UserService userService) {
        this.stageManager = stageManager;
        this.userService = userService;
    }


    @FXML
    public void onSaveButtonPressed() {
        if (usernameTextField.getText().isEmpty() && mobileNoTextField.getText().isEmpty() && newPasswordField.getText().isEmpty()) {
            FxUtil.showMessage(Alert.AlertType.ERROR, "Username , Mobile No And New Password must be entered");
            usernameTextField.requestFocus();
            return;
        } else if (usernameTextField.getText().isEmpty() && mobileNoTextField.getText().isEmpty()) {
            FxUtil.showMessage(Alert.AlertType.ERROR, "Username And Mobile No must be entered");
            usernameTextField.requestFocus();
            return;
        } else if (mobileNoTextField.getText().isEmpty() && newPasswordField.getText().isEmpty()) {
            FxUtil.showMessage(Alert.AlertType.ERROR, "Mobile No And New Password must be entered");
            mobileNoTextField.requestFocus();
            return;
        } else if (usernameTextField.getText().isEmpty() && newPasswordField.getText().isEmpty()) {
            FxUtil.showMessage(Alert.AlertType.ERROR, "Username And New Password must be entered");
            usernameTextField.requestFocus();
            return;
        } else if (usernameTextField.getText().isEmpty()) {
            FxUtil.showMessage(Alert.AlertType.ERROR, "Username must be entered");
            mobileNoTextField.requestFocus();
            return;
        } else if (mobileNoTextField.getText().isEmpty()) {
            FxUtil.showMessage(Alert.AlertType.ERROR, "Mobile No must be entered");
            mobileNoTextField.requestFocus();
            return;
        } else if (newPasswordField.getText().isEmpty()) {
            FxUtil.showMessage(Alert.AlertType.ERROR, "New Password must be entered");
            mobileNoTextField.requestFocus();
            return;
        }

        UserEntity user = userService.findByMobileNumber(Long.valueOf(mobileNoTextField.getText()));
        if (user != null) {
            if (user.getUsername().equals(usernameTextField.getText())) {
                if (newPasswordField.getText().length() >= 6) {
                    user.setPassword(newPasswordField.getText());
                    userService.save(user);
                    FxUtil.showMessage(Alert.AlertType.INFORMATION, "Your Password has been changed");
                    stageManager.switchScene(FxmlView.LOGIN);
                } else {
                    FxUtil.showMessage(Alert.AlertType.ERROR, "Please Enter Password at least 6 character");
                    newPasswordField.requestFocus();
                    return;
                }
            } else {
                FxUtil.showMessage(Alert.AlertType.ERROR, "Username Or Mobile No Not Found");
                usernameTextField.requestFocus();
                return;
            }

        } else {
            FxUtil.showMessage(Alert.AlertType.ERROR, "Username Or Mobile No Not Found");
            usernameTextField.requestFocus();
            return;
        }


    }
    private void setupListeners() {
        mobileNoTextField.textProperty().addListener(
                (observable, oldValue, newValue) -> onMobileNumberFieldChanged(oldValue, newValue)
        );}

    @FXML
    public void onMobileNumberFieldChanged(String oldValue,String newValue) {
        try{
            Long.parseLong(newValue);
        }catch(Exception e){
            if(mobileNoTextField.getLength() > 1){
                mobileNoTextField.setText(oldValue);
            }else{
                mobileNoTextField.setText("");
            }
        }
    }


    @FXML
    public void onSignUpButtonPressed() {

        stageManager.switchScene(FxmlView.USER);
    }

    @FXML
    public void onLoginButtonPressed() {

        stageManager.switchScene(FxmlView.LOGIN);
    }

    @FXML
    private TextField planeTextField;
@FXML
private CheckBox passwordCheckBox;


    @FXML
    public void onCheckBoxClicked(){
        if(passwordCheckBox.isSelected()){
            planeTextField.setText(newPasswordField.getText());
            newPasswordField.setVisible(false);
        }else{
            newPasswordField.setText(planeTextField.getText());
            newPasswordField.setVisible(true);
        }
    }
    @FXML
    public void onExitButtonPressed() {
        if (FxUtil.confirmation("Are You Sure to Exit The Application").getResult() == ButtonType.OK){
            Platform.exit();
        }
    }

    private void getUsername(){
        usernameTextField.setText(FxUtil.username);
    }


}
