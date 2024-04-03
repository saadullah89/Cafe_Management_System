package com.alabtaal.cafe.controller;

import com.alabtaal.cafe.config.StageManager;
import com.alabtaal.cafe.entity.UserEntity;
import com.alabtaal.cafe.enums.FxmlView;
import com.alabtaal.cafe.service.UserService;
import com.alabtaal.cafe.util.FxUtil;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class LoginController implements Initializable {

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField planeTextField;

    private final StageManager stageManager;

    private final UserService userService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        planeTextField.setVisible(false);
    }

    public LoginController(@Lazy StageManager stageManager, UserService userService) {
        this.stageManager = stageManager;
        this.userService = userService;
    }

    @FXML
    public void onLoginButtonPressed() {
        if (usernameTextField.getText().isEmpty() && passwordField.getText().isEmpty()) {
            FxUtil.showMessage(Alert.AlertType.ERROR, "Username and Password must be entered");
            usernameTextField.requestFocus();
            return;
        } else if (usernameTextField.getText().isEmpty()) {
            FxUtil.showMessage(Alert.AlertType.ERROR, "Username must be entered");
            usernameTextField.requestFocus();
            return;
        } else if (passwordField.getText().isEmpty()) {
            FxUtil.showMessage(Alert.AlertType.ERROR, "Password must be entered");
            passwordField.requestFocus();
            return;
        }


        UserEntity user = userService.findByUsername(usernameTextField.getText());
        if (user != null) {
            if (usernameTextField.getText().equals(user.getUsername()) && passwordField.getText().equals(user.getPassword())) {
                FxUtil.username = usernameTextField.getText();
                stageManager.switchScene(FxmlView.HOME);
            } else if (!passwordField.getText().equals(user.getPassword())) {
                FxUtil.showMessage(Alert.AlertType.ERROR, "Username or password is incorrect");
                usernameTextField.requestFocus();
            }
        } else {
            FxUtil.showMessage(Alert.AlertType.ERROR, "Username or password is incorrect");
            usernameTextField.requestFocus();
        }
    }
    @FXML
    public void onCheckBoxClicked(){
        planeTextField.setVisible(!planeTextField.isVisible());
        passwordField.setVisible(!planeTextField.isVisible());
    }

    @FXML
    public void onPasswordFieldChanged(){
        planeTextField.setText(passwordField.getText());
    }

    @FXML
    public void onPlanTextFieldChanged(){
        passwordField.setText(planeTextField.getText());
    }



    @FXML
    public void onForgotPasswordButtonPressed() {
        if (usernameTextField.getText().isEmpty()) {
            FxUtil.showMessage(Alert.AlertType.ERROR, "Username must be entered");
            usernameTextField.requestFocus();
            return;
        }

        UserEntity user = userService.findByUsername(usernameTextField.getText());
        if (user != null) {
            FxUtil.username = usernameTextField.getText();
            stageManager.switchScene(FxmlView.CHANGE_PASSWORD);
        } else {
            FxUtil.showMessage(Alert.AlertType.ERROR, "Username not found");
            usernameTextField.requestFocus();
        }

    }

    @FXML
    public void onSignUpButtonPressed() {
        stageManager.switchScene(FxmlView.USER);
    }

    @FXML
    public void onClearButtonPressed() {
        usernameTextField.clear();
        passwordField.clear();
    }

    @FXML
    public void onExitButtonPressed() {
        if (FxUtil.confirmation("Are You Sure to Exit The Application").getResult() == ButtonType.OK){
            Platform.exit();
        }
    }


}