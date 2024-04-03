package com.alabtaal.cafe.controller;

import com.alabtaal.cafe.config.SpringFXMLLoader;
import com.alabtaal.cafe.config.StageManager;
import com.alabtaal.cafe.enums.FxmlView;
import com.alabtaal.cafe.util.FxUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class HomeController implements Initializable {

    @FXML
    private Label usernameLabel;
    private final StageManager stageManager;

    private final SpringFXMLLoader fxmlLoader;

    @FXML
    private BorderPane rootBorderPane;


    public HomeController(@Lazy StageManager stageManager, SpringFXMLLoader fxmlLoader) {
        this.stageManager = stageManager;
        this.fxmlLoader = fxmlLoader;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stageManager.getStage().setResizable(false);
        getUsername();
    }
    @FXML
    public void onMenuManagementButtonSelected() throws IOException {
        switchView(FxmlView.MENU_MANAGEMENT);
    }
    @FXML
    public void onHomeButtonPressed() throws IOException {
        switchView(FxmlView.WELCOME);
    }

    @FXML
    public void onItemSaleButtonPressed() throws IOException {
        switchView(FxmlView.ITEM_SALE);
    }

    @FXML
    public void onCustomerButtonPressed() throws IOException {
        switchView(FxmlView.CUSTOMER);
    }
    @FXML
    public void onDashboardButtonPressed() throws IOException {
        switchView(FxmlView.DASHBOARD);
    }

    @FXML
    public void onSignOutButtonSelected(){
        stageManager.switchScene(FxmlView.LOGIN);
    }

    private void switchView(FxmlView fxmlView) throws IOException {
        Parent view = fxmlLoader.load(fxmlView.getFxmlFile());
        stageManager.getStage().setTitle(fxmlView.getTitle());
        rootBorderPane.setCenter(view);
    }

    private void getUsername(){
        usernameLabel.setText(FxUtil.username);
    }

}
