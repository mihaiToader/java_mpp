package Gui;

import Gui.Login.LoginController;
import Services.IServerServices;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by Mihai on 03.04.2017.
 */
public class StartClient extends Application {
    public static void main(String args[]){
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        ApplicationContext factory = new ClassPathXmlApplicationContext("services2.xml");
        IServerServices server=(IServerServices)factory.getBean("serverServices");

        FXMLLoader loaderMain = new FXMLLoader();
        loaderMain.setLocation(StartClient.class.getResource("/Gui/Login.fxml"));
        Parent parentMain = null;
        try {
            parentMain = loaderMain.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LoginController loginController = loaderMain.getController();

        Scene scene = new Scene(parentMain);
        loginController.setData(server, primaryStage, scene);

        primaryStage.setScene(scene);
        primaryStage.setTitle("^_^ MPP APPLICATION ^_^");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
