package com.alabtaal.cafe.util;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class FxUtil {
    public static void showMessage(AlertType alertType , String message){
        final Alert alert = new Alert(alertType);
        alert.setTitle(alertType.name());
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static Alert confirmation(String message){
        Alert confirmationDialog = new Alert(AlertType.CONFIRMATION);
        confirmationDialog.setHeaderText(null);
        confirmationDialog.setContentText(message);
        confirmationDialog.showAndWait();
        return confirmationDialog;
    }

    public static String username = null;
    
}
