package controller;

import bo.BoFactory;
import bo.custom.CustomerBo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dao.util.BoType;
import db.DBConnection;
import dto.CustomerDto;
import dto.tm.CustomerTm;
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
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class CustomerFormController {


    public JFXTextField txtCustomerName;
    public JFXTextField txtAddress;
    public JFXTextField txtContactNumber;
    public JFXTextField txtEmail;
    public Label lblDate;
    public Label lblTime;
    public TreeTableColumn ColCustomerName;
    public TreeTableColumn colAddress;
    public TreeTableColumn colContactNumber;
    public TreeTableColumn colEmail;
    public JFXTreeTableView<CustomerTm> tblCustomer;
    public TreeTableColumn colCustomerName;

    @FXML
    private AnchorPane pane6;

    @FXML
    private Label lblId;


    @FXML
    private TreeTableColumn<?, ?> colOption;

        CustomerBo customerBo = BoFactory.getInstance().getBo(BoType.CUSTOMER);

    public void initialize() {
        generateId();
        setDate();
        loadCustomerTable();

        colCustomerName.setCellValueFactory(new TreeItemPropertyValueFactory<>("customerName"));
        colAddress.setCellValueFactory(new TreeItemPropertyValueFactory<>("customerAddress"));
        colContactNumber.setCellValueFactory(new TreeItemPropertyValueFactory<>("customerContactNumber"));
        colEmail.setCellValueFactory(new TreeItemPropertyValueFactory<>("customerEmail"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));

        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observableValue,customerTmTreeItem,newValue) -> setData(newValue));
    }

    private void setData(TreeItem<CustomerTm> newValue) {
        if (newValue!=null){
            lblId.setText(newValue.getValue().getCustomerId());
            txtCustomerName.setText(newValue.getValue().getCustomerName());
            txtAddress.setText(newValue.getValue().getCustomerAddress());
            txtContactNumber.setText(String.valueOf(newValue.getValue().getCustomerContactNumber()));
            txtEmail.setText(newValue.getValue().getCustomerEmail());
        }
    }


    private void loadCustomerTable() {
        ObservableList<CustomerTm> customerTms= FXCollections.observableArrayList();
        try {
            List<CustomerDto> dtoList=customerBo.allCustomers();
            if (dtoList!=null){
                for (CustomerDto dto:dtoList) {
                    JFXButton btn=new JFXButton("Delete");
                    btn.setStyle("-fx-background-color: #EF6262;");

                    CustomerTm customerTm = new CustomerTm(
                            dto.getCustomerId(),
                            dto.getCustomerName(),
                            dto.getCustomerAddress(),
                            dto.getCustomerContactNumber(),
                            dto.getCustomerEmail(),
                            btn
                    );
                    btn.setOnAction(actionEvent -> {
                        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                        confirmationAlert.setHeaderText("Confirm Deletion");
                        confirmationAlert.setContentText("Do you want to delete the user " + customerTm.getCustomerId() + "?");

                        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                        confirmationAlert.getButtonTypes().setAll(yesButton, noButton);

                        Optional<ButtonType> result = confirmationAlert.showAndWait();

                        if (result.isPresent() && result.get() == yesButton) {
                            deleteCustomer(customerTm.getCustomerId());
                        }
                    });

                    customerTms.add(customerTm);
                }
                RecursiveTreeItem<CustomerTm> treeItem = new RecursiveTreeItem<>(customerTms, RecursiveTreeObject::getChildren);
                tblCustomer.setRoot(treeItem);
                tblCustomer.setShowRoot(false);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteCustomer(String customerId) {
        try {
            boolean isDelete = customerBo.deleteCustomer(customerId);
            if (isDelete){
                new Alert(Alert.AlertType.INFORMATION,"Customer Deleted").show();
                loadCustomerTable();
            }else {
                new Alert(Alert.AlertType.ERROR,"Something Went Wrong").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


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

    public void dashboardBtnOnAction(javafx.event.ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) pane6.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/UserDashboardForm.fxml"))));
        stage.setTitle("User Dashboard Form");
        stage.setResizable(false);
        stage.show();
    }

    public void backBtnOnAction(javafx.event.ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) pane6.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/UserDashboardForm.fxml"))));
        stage.setTitle("User Dashboard Form");
        stage.setResizable(false);
        stage.show();
    }


    public void generateId() {
       lblId.setText(customerBo.generateID());
    }

    public void addCustomerBtnOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        CustomerDto customerDto = new CustomerDto(lblId.getText(), txtCustomerName.getText(), txtAddress.getText(), Integer.parseInt(txtContactNumber.getText()), txtEmail.getText());
        boolean isSaved = customerBo.saveCustomer(customerDto);
        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Customer Saved!").show();
            loadCustomerTable();
        } else {
            new Alert(Alert.AlertType.ERROR, "Something Went Wrong").show();
        }
    }

    public void updateCustomerBtnOnAction(ActionEvent actionEvent) {
        CustomerDto customerDto=new CustomerDto(lblId.getText(),txtCustomerName.getText(),txtAddress.getText(),Integer.parseInt(txtContactNumber.getText()),txtEmail.getText());
        try {
            boolean isUpdate = customerBo.updateCustomer(customerDto);
            if (isUpdate){
                new Alert(Alert.AlertType.INFORMATION,"Customer Updated!").show();
                loadCustomerTable();
            }else {
                new Alert(Alert.AlertType.ERROR,"Something Went Wrong").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void addNewCustomerBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) pane6.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/NewCustomer.fxml"))));
        stage.setTitle("New Customer Form");
        stage.setResizable(false);
        stage.show();
    }

    public void reportBtnOnAction(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load("src/main/resources/reports/Customer_Reports.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}

