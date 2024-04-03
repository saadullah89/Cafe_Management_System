package com.alabtaal.cafe.controller;

import com.alabtaal.cafe.entity.CustomerEntity;
import com.alabtaal.cafe.entity.ItemSaleEntity;
import com.alabtaal.cafe.entity.ItemSaleEntity1;
import com.alabtaal.cafe.service.CustomerService;
import com.alabtaal.cafe.service.ItemSale1Service;
import com.alabtaal.cafe.service.ItemSaleService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import org.hibernate.bytecode.internal.bytebuddy.BytecodeProviderImpl;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
@Controller
public class DashboardController implements Initializable {
    @FXML
    private AreaChart<String,Number> areaChart;
    @FXML
    private BarChart<String,Number> barChart;
    private final CustomerService customerService;
    private final ItemSale1Service itemSale1Service;
    @FXML
    private Label customerNumber;
    @FXML
    private Label totalIncome;
    @FXML
    private Label totalSoldItem;
    @FXML
    private Label todayTotalIncome;

    public DashboardController(CustomerService customerService, ItemSale1Service itemSale1Service) {
        this.customerService = customerService;
        this.itemSale1Service = itemSale1Service;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        areaChart();
        barChart();
        customerNumber();
        totalIncome();
        totalSoldItem();
        todayTotalIncome();
    }
    private void customerNumber(){
        List<CustomerEntity> customers = customerService.findAll();
        customerNumber.setText(String.valueOf(customers.size()));

    }
    private void totalSoldItem(){
        List<ItemSaleEntity1> soldItem = itemSale1Service.findAll();
        totalSoldItem.setText(String.valueOf(soldItem.size()));

    }
    private void todayTotalIncome(){
        LocalDate todayDate=LocalDate.now();
        final List<ItemSaleEntity1> items = itemSale1Service.findAll();
        double total = 0;
        for (ItemSaleEntity1 item : items) {
            LocalDate saleItem=item.getSaleDate();
            if(saleItem.equals(todayDate)){
                total += item.getPrice();
            }
        }
        todayTotalIncome.setText(String.valueOf(total));
    }



    private void totalIncome(){
        final List<ItemSaleEntity1> items = itemSale1Service.findAll();
        long total = 0;
        for (ItemSaleEntity1 item : items) {
            total += item.getPrice();
        }
        totalIncome.setText(String.valueOf(total));
    }



    private void areaChart(){
        List<CustomerEntity> customers = customerService.findAll();
         Map<String, Double> totalPriceMap = new HashMap<>();
        for (CustomerEntity customer : customers) {
            String date = String.valueOf(customer.getDate());
            double price = customer.getPrice();

            if (totalPriceMap.containsKey(date)) {
                totalPriceMap.put(date, totalPriceMap.get(date) + price);
            } else {
                totalPriceMap.put(date, price);
            }
        }
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        for (Map.Entry<String, Double> entry : totalPriceMap.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        areaChart.getData().add(series);
    }
    private void barChart(){
        List<CustomerEntity> customers = customerService.findAll();
        Map<String, Double> totalCustomerMap = new HashMap<>();
        for (CustomerEntity customer : customers) {
            String date = String.valueOf(customer.getDate());

            if (totalCustomerMap.containsKey(date)) {
                totalCustomerMap.put(date, totalCustomerMap.get(date) + 1);
            } else {
                totalCustomerMap.put(date, 1.0);
            }
        }
        XYChart.Series<String, Number> seriesBar = new XYChart.Series<>();
        for (Map.Entry<String, Double> entry : totalCustomerMap.entrySet()) {
            seriesBar.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        barChart.getData().add(seriesBar);
    }
}
