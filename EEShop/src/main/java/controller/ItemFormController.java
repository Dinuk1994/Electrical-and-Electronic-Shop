package controller;

import bo.BoFactory;
import bo.custom.ItemBo;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dao.DaoFactory;
import dao.custom.ItemDao;
import dao.util.BoType;
import dao.util.DaoType;
import dao.util.ItemType;
import dto.CustomerDto;
import dto.ItemDto;
import dto.tm.ItemTm;
import entity.Item;
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
import java.util.List;
import java.util.Optional;

public class ItemFormController {

    public AnchorPane pane8;
    @FXML
    private Label lblCode;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private JFXTextField txtItemName;

    @FXML
    private JFXTextField txtItemPrice;

    @FXML
    private JFXComboBox<ItemType> cmbItemCategory;

    @FXML
    private JFXTreeTableView<ItemTm> tblItem;

    @FXML
    private TreeTableColumn<?, ?> colItemCode;

    @FXML
    private TreeTableColumn<?, ?> colItemCategory;

    @FXML
    private TreeTableColumn<?, ?> colItemName;

    @FXML
    private TreeTableColumn<?, ?> colItemPrice;

    @FXML
    private TreeTableColumn<?, ?> colOption;

    ItemBo itemBo= BoFactory.getInstance().getBo(BoType.ITEM);

    public void initialize(){
        generateCode();
        setData();
        loadItemTable();
        setDate();

        tblItem.getSelectionModel().selectedItemProperty().addListener((observable, itemTmTreeItem, newValue) ->setDatas(newValue) );
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

    private void setDatas(TreeItem<ItemTm> newValue) {
        if (newValue!=null){
            lblCode.setText(newValue.getValue().getItemCode());
            cmbItemCategory.setItems(FXCollections.observableArrayList(ItemType.valueOf(newValue.getValue().getItemCategory())));
            txtItemName.setText(newValue.getValue().getItemName());
            txtItemPrice.setText(String.valueOf(newValue.getValue().getItemPrice()));
        }
    }


    private void loadItemTable()  {
        ObservableList<ItemTm> list= FXCollections.observableArrayList();
        try {
            List<ItemDto> dtoList  = itemBo.getAll();
            if (dtoList!=null){
                for (ItemDto dto:dtoList) {
                    JFXButton btn=new JFXButton("Delete");
                    btn.setStyle("-fx-background-color: #EF6262;");
                    ItemTm itemTm=new ItemTm(
                            dto.getItemCode(),
                            dto.getItemCategory(),
                            dto.getItemName(),
                            dto.getItemPrice(),
                            btn
                    );
                    btn.setOnAction(actionEvent -> {

                        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                        confirmationAlert.setHeaderText("Confirm Deletion");
                        confirmationAlert.setContentText("Do you want to delete the item " + itemTm.getItemCode() + "?");

                        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                        confirmationAlert.getButtonTypes().setAll(yesButton, noButton);

                        Optional<ButtonType> result = confirmationAlert.showAndWait();

                        if (result.isPresent() && result.get() == yesButton) {
                            deleteItem(dto.getItemCode());
                        }
                    });
                    list.add(itemTm);
                }
                RecursiveTreeItem<ItemTm> treeItem=new RecursiveTreeItem<>(list, RecursiveTreeObject::getChildren);
                tblItem.setRoot(treeItem);
                tblItem.setShowRoot(false);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void deleteItem(String itemCode) {
        try {
            boolean delete = itemBo.delete(itemCode);
            if (delete){
                new Alert(Alert.AlertType.INFORMATION,"Item Deleted!").show();
                loadItemTable();
            }else{
                new Alert(Alert.AlertType.ERROR,"Something Went Wrong").show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setData() {
        cmbItemCategory.getItems().addAll(ItemType.values());

        colItemCode.setCellValueFactory(new TreeItemPropertyValueFactory<>("itemCode"));
        colItemCategory.setCellValueFactory(new TreeItemPropertyValueFactory<>("itemCategory") );
        colItemName.setCellValueFactory(new TreeItemPropertyValueFactory<>("itemName"));
        colItemPrice.setCellValueFactory(new TreeItemPropertyValueFactory<>("itemPrice"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));
    }

    private void generateCode() {
      lblCode.setText(itemBo.generateId());
    }


    public void dashboardBtnOnAction(javafx.event.ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) pane8.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/UserDashboardForm.fxml"))));
        stage.setTitle("Dashboard Form");
        stage.setResizable(false);
        stage.show();
    }


    public void backBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) pane8.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/UserDashboardForm.fxml"))));
        stage.setTitle("Dashboard Form");
        stage.setResizable(false);
        stage.show();
    }

    public void addItemBtnOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ItemDto itemDto=new ItemDto(lblCode.getText(),cmbItemCategory.getValue().toString(),txtItemName.getText(),Double.parseDouble(txtItemPrice.getText()));
        boolean isSave = itemBo.save(itemDto);
        if (isSave){
            new Alert(Alert.AlertType.INFORMATION,"Item Saved!").show();
            loadItemTable();
        }else {
            new Alert(Alert.AlertType.ERROR,"Something Went Wrong").show();
        }
    }


    public void updateItemBtnOnAction(ActionEvent actionEvent) {
        ItemDto itemDto = new ItemDto(lblCode.getText(),cmbItemCategory.getValue().toString(),txtItemName.getText(),Double.parseDouble(txtItemPrice.getText()));
        try {
            boolean isUpdate= itemBo.update(itemDto);
            if (isUpdate){
                new Alert(Alert.AlertType.INFORMATION,"item Updated!").show();
                loadItemTable();
            }else{
                new Alert(Alert.AlertType.ERROR,"Something Went Wrong").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void newItemBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) pane8.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ItemForm.fxml"))));
        stage.setTitle("Item Form");
        stage.setResizable(false);
        stage.show();
    }
}
