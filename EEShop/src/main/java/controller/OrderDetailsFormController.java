package controller;

import bo.BoFactory;
import bo.custom.CustomerBo;
import bo.custom.OrderDetailsBo;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dao.DaoFactory;
import dao.custom.OrderDetailsDao;
import dao.util.BoType;
import dao.util.DaoType;
import dao.util.ItemType;
import dto.CustomerDto;
import dto.OrderDetailsDto;
import dto.tm.CustomerTm;
import dto.tm.ItemTm;
import dto.tm.OrderDetailsTm;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OrderDetailsFormController {

    public JFXTreeTableView tblOrderDetails;
    public TreeTableColumn colStatus;
    @FXML
    private AnchorPane pane7;

    @FXML
    private JFXComboBox<?> cmbCustomerID;

    @FXML
    private JFXTextField txtCustomerName;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private JFXTreeTableView<OrderDetailsTm> tblPlaceOrder;

    @FXML
    private TreeTableColumn<?, ?> colOrderID;

    @FXML
    private TreeTableColumn<?, ?> colCustomerID;

    @FXML
    private TreeTableColumn<?, ?> colCustomerName;

    @FXML
    private TreeTableColumn<?, ?> colItemCategory;

    @FXML
    private TreeTableColumn<?, ?> colItemName;

    @FXML
    private TreeTableColumn<?, ?> colItemQty;

    @FXML
    private TreeTableColumn<?, ?> colItemPrice;
    
    OrderDetailsBo orderDetailsBo= BoFactory.getInstance().getBo(BoType.ORDER_DETAILS);

    CustomerBo customerBo=BoFactory.getInstance().getBo(BoType.CUSTOMER);

    List<OrderDetailsDto> orderDetailsDtoList;
    List<CustomerDto> customers;


    
    public void initialize(){
        loadOrderDetailsTable();
        setData();
        calculateTime();
        loadCustomerId();

    }

    private void loadCustomerId()  {
        ObservableList list=FXCollections.observableArrayList();
        try {
            customers=customerBo.allCustomers();
            for (CustomerDto customerDto:customers) {
                list.add(customerDto.getCustomerId());
            }
            cmbCustomerID.setItems(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

     }

    private void calculateTime() {
            Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, actionEvent -> lblTime.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")))
            ), new KeyFrame(Duration.seconds(1)));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();

            Timeline timeline1 = new Timeline(new KeyFrame(Duration.ZERO, actionEvent -> lblDate.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
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
        colItemPrice.setCellValueFactory(new TreeItemPropertyValueFactory<>("itemPrice"));
        colStatus.setCellValueFactory(new TreeItemPropertyValueFactory<>("status"));

    }

    private void loadOrderDetailsTable() {
        ObservableList<OrderDetailsTm> orderDetailsDtos= FXCollections.observableArrayList();
        try {
            orderDetailsDtoList=orderDetailsBo.getAll();
            if (orderDetailsDtoList!=null){
                for (OrderDetailsDto dto:orderDetailsDtoList) {
                    OrderDetailsTm orderDetailsTm=new OrderDetailsTm(
                            dto.getOrderId(),
                            dto.getItemCode(),
                            dto.getCustomerName(),
                            dto.getItemCategory(),
                            dto.getItemName(),
                            dto.getItemQty(),
                            dto.getItemPrice(),
                            dto.getStatus()

                    );
                    orderDetailsDtos.add(orderDetailsTm);
                }
                RecursiveTreeItem<OrderDetailsTm> treeItem=new RecursiveTreeItem<>(orderDetailsDtos, RecursiveTreeObject::getChildren);
                tblOrderDetails.setRoot(treeItem);
                tblOrderDetails.setShowRoot(false);

            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    public void dashboardBtnOnAction(javafx.event.ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) pane7.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/UserDashboardForm.fxml"))));
        stage.setTitle("New Order Form");
        stage.setResizable(false);
        stage.show();
    }

    public void backBtnOnAction(javafx.event.ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) pane7.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/UserDashboardForm.fxml"))));
        stage.setTitle("New Order Form");
        stage.setResizable(false);
        stage.show();
    }

    public void cmbCustomerID(javafx.event.ActionEvent actionEvent) {
    }

    public void searchBtnOnAction(javafx.event.ActionEvent actionEvent) {
    }
}
