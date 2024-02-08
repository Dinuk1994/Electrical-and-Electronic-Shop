package controller;

import bo.BoFactory;
import bo.custom.CustomerBo;
import bo.custom.ItemBo;
import bo.custom.OrderBo;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dao.DaoFactory;
import dao.custom.OrdersDao;
import dao.util.BoType;
import dao.util.DaoType;
import dao.util.ItemType;
import dto.CustomerDto;
import dto.ItemDto;
import dto.OrderDetailsDto;
import dto.OrdersDto;
import dto.tm.OrderTm;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlaceOrderFormController {

    public JFXComboBox cmbItemCode;
    @FXML
    private AnchorPane pane7;

    @FXML
    private Label lblId;

    @FXML
    private JFXComboBox<?> cmbCustomerID;

    @FXML
    private JFXTextField txtCustomerName;

    @FXML
    private JFXComboBox<ItemType> cmbItemCategory;

    @FXML
    private JFXTextField txtItemName;

    @FXML
    private JFXTextField txtItemQty;

    @FXML
    private JFXTextField txtItemPrice;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private JFXTreeTableView<OrderTm> tblPlaceOrder;

    @FXML
    private TreeTableColumn<?, ?> colOrderID;

    @FXML
    private TreeTableColumn<?, ?> colCustomerName;

    @FXML
    private TreeTableColumn<?, ?> colItemCategory;

    @FXML
    private TreeTableColumn<?, ?> colItemName;

    @FXML
    private TreeTableColumn<?, ?> colItemQty;

    @FXML
    private TreeTableColumn<?, ?> colPrice;

    @FXML
    private TreeTableColumn<?, ?> colDate;

    @FXML
    private TreeTableColumn<?, ?> colOption;

    @FXML
    private Label lblAmount;

    private List<CustomerDto> customers;
    private List<ItemDto> items;

    OrderBo orderBo=BoFactory.getInstance().getBo(BoType.ORDERS);

    ItemBo itemBo=BoFactory.getInstance().getBo(BoType.ITEM);
    CustomerBo customerBo= BoFactory.getInstance().getBo(BoType.CUSTOMER);

    ObservableList<OrderTm> list=FXCollections.observableArrayList();

    public void initialize(){
        cmbItemCategory.getItems().addAll(ItemType.values());
        setOrderId();
        loadCustomerId();
        loadItemCode();
        setData();
        setDate();
        cmbCustomerID.getSelectionModel().selectedItemProperty().addListener((observableValue, o, newValue) -> setCustomerData(newValue));
        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> setItemData(newValue));
    }


    private void setDate() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, actionEvent -> lblDate.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
        ), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        Timeline timeline1 = new Timeline(new KeyFrame(Duration.ZERO, actionEvent -> lblTime.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH-mm-ss")))
        ), new KeyFrame(Duration.seconds(1)));
        timeline1.setCycleCount(Animation.INDEFINITE);
        timeline1.play();
    }

    private void setData() {
        colOrderID.setCellValueFactory(new TreeItemPropertyValueFactory<>("orderId"));
        colCustomerName.setCellValueFactory(new TreeItemPropertyValueFactory<>("customerName"));
        colItemCategory.setCellValueFactory(new TreeItemPropertyValueFactory<>("itemCategory"));
        colItemName.setCellValueFactory(new TreeItemPropertyValueFactory<>("itemName"));
        colItemQty.setCellValueFactory(new TreeItemPropertyValueFactory<>("itemQty"));
        colPrice.setCellValueFactory(new TreeItemPropertyValueFactory<>("itemPrice"));
        colDate.setCellValueFactory(new TreeItemPropertyValueFactory<>("date"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));
    }

    private void setCustomerData(Object newValue) {
        for (CustomerDto dto:customers) {
            if (dto.getCustomerId().equals(newValue)){
                txtCustomerName.setText(dto.getCustomerName());
            }
        }
    }

    private void setItemData(Object newValue) {
        for (ItemDto itemDto:items) {
            if (itemDto.getItemCode().equals(newValue)){
                cmbItemCategory.setItems(FXCollections.observableArrayList(ItemType.valueOf(itemDto.getItemCategory())));
                txtItemName.setText(itemDto.getItemName());
                txtItemPrice.setText(String.valueOf(itemDto.getItemPrice()));
            }
        }
    }




    private void loadCustomerId() {
        try {
            customers=customerBo.allCustomers();
            ObservableList list= FXCollections.observableArrayList();
            for (CustomerDto dto:customers) {
                list.add(dto.getCustomerId());
            }
            cmbCustomerID.setItems(list);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadItemCode() {
        try {
            items=itemBo.getAll();
            ObservableList list=FXCollections.observableArrayList();
            for (ItemDto itemDto:items) {
                list.add( itemDto.getItemCode());
            }
            cmbItemCode.setItems(list);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    private void setOrderId() {
       lblId.setText(orderBo.generateId());
    }


    public void dashboardBtnOnAction(javafx.event.ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) pane7.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/UserDashboardForm.fxml"))));
        stage.setTitle("New Order Form");
        stage.setResizable(false);
        stage.show();
    }

    public void backBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) pane7.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/UserDashboardForm.fxml"))));
        stage.setTitle("New Order Form");
        stage.setResizable(false);
        stage.show();
    }

    public void cmbCustomerID(ActionEvent actionEvent) {
    }

    public void cmbItemCategory(ActionEvent actionEvent) {
    }

    public void placeOrderBtnOnAction(ActionEvent actionEvent) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setHeaderText("Do you want to place the Order");

        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        confirmationAlert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == yesButton) {

            List<OrderDetailsDto> orderList=new ArrayList<>();
            for (OrderTm orderTm:list) {
                String status =calculateStatus(orderTm.getDate());
                OrderDetailsDto orderDetailsDto = new OrderDetailsDto(
                        orderTm.getOrderId(),
                        cmbItemCode.getValue().toString(),
                        txtCustomerName.getText(),
                        cmbItemCategory.getValue().toString(),
                        txtItemName.getText(),
                        Integer.parseInt(txtItemQty.getText()),
                        Double.parseDouble(txtItemPrice.getText()),
                        status
                );
                orderList.add(orderDetailsDto);
            }
            if (!orderList.isEmpty()){
                try {
                    boolean saved= orderBo.saveOrder(new OrdersDto(
                            lblId.getText(),
                            cmbItemCategory.getValue().toString(),
                            cmbCustomerID.getValue().toString(),
                            txtItemName.getText(),
                            Integer.parseInt(txtItemQty.getText()),
                            Double.parseDouble(txtItemPrice.getText()),
                            lblDate.getText(),
                            orderList
                    ));
                    if (saved){
                        new Alert(Alert.AlertType.INFORMATION,"Order Saved!").show();
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        }
     }

    private String calculateStatus(String orderDate) {
        LocalDateTime currentDate = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
        LocalDateTime orderDateTime = LocalDateTime.parse(orderDate + " 00-00-00", dateTimeFormatter);

        long dayDifference = ChronoUnit.DAYS.between(orderDateTime, currentDate);

        if (dayDifference > 10) {
            return "RED ZONE";
        } else if (dayDifference > 5) {
            return "ORANGE ZONE";
        } else if (dayDifference > 0) {
            return "YELLOW ZONE";
        } else {
            return "PENDING";
        }
    }


    public void newOrderBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) pane7.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/placeOrderForm.fxml"))));
        stage.setTitle("New Order Form");
        stage.setResizable(false);
        stage.show();
    }

    public void addToCartBtnOnAction(ActionEvent actionEvent) {
        JFXButton btn=new JFXButton("Delete");
        btn.setStyle("-fx-background-color: #EF6262;");
        Double price=(Double.parseDouble(txtItemPrice.getText())*Integer.parseInt(txtItemQty.getText()));

        OrderTm orderTm =new OrderTm(
                lblId.getText(),
                txtCustomerName.getText(),
                cmbItemCategory.getValue().toString(),
                txtItemName.getText(),
                Integer.parseInt(txtItemQty.getText()),
                Double.parseDouble(price.toString()),
                lblDate.getText(),
                btn
        );
        btn.setOnAction(actionEvent1 -> {
            deleteOrder(orderTm);
        });

        list.add(orderTm);
        lblAmount.setText(calculateAmount());

        RecursiveTreeItem<OrderTm> treeItem = new RecursiveTreeItem<>( list, RecursiveTreeObject::getChildren);
        tblPlaceOrder.setRoot(treeItem);
        tblPlaceOrder.setShowRoot(false);

    }

    private void deleteOrder(OrderTm orderTm) {
        list.remove(orderTm);
        lblAmount.setText(calculateAmount());
    }

    private String calculateAmount() {
        double amount =0;
        for (OrderTm orderTm:list) {
            amount+=orderTm.getItemPrice();
        }
        return String.format("%.2f",amount);
    }
}
