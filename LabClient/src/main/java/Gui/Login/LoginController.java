package Gui.Login;

import Gui.MainWindow.MainWindowController;
import Services.LabException;
import model.User;
import Services.IServerServices;
import Services.IServicesClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;

/**
 * Created by Mihai on 21.03.2017.
 */
public class LoginController{
    @FXML
    private TextField textFieldUsername;

    @FXML
    private PasswordField passwordField;

    private Stage primaryStage;

    private IServerServices server;


    private Alert alert;

    private Scene thisScene;

    private MainWindowController mwc;

    public void setData(IServerServices server, Stage primaryStage,Scene thisScene){
        this.server = server;
        this.primaryStage = primaryStage;
        this.thisScene = thisScene;

        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");

        textFieldUsername.setText("Mihai");
        passwordField.setText("parola");
    }

    @FXML
    void doLogin(ActionEvent event) {
        User user = null;
        try {
            user = server.validateUser(textFieldUsername.getText(), passwordField.getText());
            System.out.println("User connected: " + user.getUsername());
            FXMLLoader loaderMain = new FXMLLoader();
            loaderMain.setLocation(LoginController.class.getResource("/Gui/MainWindow.fxml"));
            Parent parentMain = null;
            try {
                parentMain = loaderMain.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mwc = loaderMain.getController();
            mwc.setController(primaryStage,server,user,thisScene);
            server.setClient(mwc);
            Scene scene = new Scene(parentMain);
            primaryStage.setScene(scene);
            primaryStage.setResizable(true);

        } catch (LabException e) {
            alert.setContentText("Wrong username or password!");
            alert.show();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void doSignUp(ActionEvent event) {
        System.out.println("login");
    }



}
