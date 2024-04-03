package com.alabtaal.cafe.enums;

import java.util.ResourceBundle;

public enum FxmlView {

    LOGIN{
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("login.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/login.fxml";
        }
    },
    DASHBOARD{
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("dashboard.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/dashboard.fxml";
        }
    },
    USER{
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("user.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/user.fxml";
        }
    },
    CHANGE_PASSWORD{
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("changePassword.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/changePassword.fxml";
        }
    },
    HOME{
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("home.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/home.fxml";
        }
    },
    WELCOME{
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("welcome.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/welcome.fxml";
        }
    },
    ITEM_SALE{
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("itemSale.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/itemSale.fxml";
        }
    },
    CUSTOMER{
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("customer.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/customer.fxml";
        }
    },
     BILL{
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("bill.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/bill.fxml";
        }
    },
    MENU_MANAGEMENT{
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("menuManagement.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/menuManagement.fxml";
        }
    };

    static String getStringFromResourceBundle(final String key){
        return ResourceBundle.getBundle("Bundle").getString(key);
    }

    public abstract String getTitle();

    public abstract String getFxmlFile();

}
