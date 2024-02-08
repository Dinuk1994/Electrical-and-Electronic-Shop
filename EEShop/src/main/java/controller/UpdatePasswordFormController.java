package controller;

import bo.BoFactory;
import bo.custom.UpdatePasswordBo;
import bo.custom.UserUpdateBo;
import com.jfoenix.controls.JFXPasswordField;
import dao.util.BoType;
import dto.UserDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class UpdatePasswordFormController {

    public Label lblEmail;
    @FXML
    private AnchorPane pane;

    @FXML
    private JFXPasswordField txtNewPassword;

    @FXML
    private JFXPasswordField txtReEnterPassword;


    private String email;

    UpdatePasswordBo updatePasswordBo= BoFactory.getInstance().getBo(BoType.UPDATE_PASSWORD);
    UserUpdateBo userUpdateBo=BoFactory.getInstance().getBo(BoType.UPDATE);

    public void setUserData(String email){
        this.email=email;
        lblEmail.setText(email);
    }

    public void initialize(){
        setUserData(email);
    }

    @FXML
    void btnConfirmOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String email = lblEmail.getText();
        String password = txtNewPassword.getText();
        String confirmPassword = txtReEnterPassword.getText();

        UserDto userDto = new UserDto();
        userDto.setEmail(email);


        if (updatePasswordBo.isFound(userDto) && userUpdateBo.isValidPassword(password)){
            userDto.setPrimaryPassword(password);
            boolean update = updatePasswordBo.isUpdate(userDto);
            if (update){
                Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION,"Password Updated");
                confirmation.showAndWait().ifPresent(response->{
                    if (response== ButtonType.OK){
                        Stage stage = (Stage) pane.getScene().getWindow();
                        try {
                            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml"))));
                            stage.setTitle("Login Form");
                            stage.setResizable(false);
                            stage.show();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    }
                });

            }else {
                new Alert(Alert.AlertType.ERROR,"Something Went Wrong").show();
            }
        }

    }

}
