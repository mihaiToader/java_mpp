package Gui;

import Gui.Login.LoginController;
import Services.IServerServices;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import networking.objectprotocol.ChatServerObjectProxy;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Mihai on 03.04.2017.
 */
public class StartClient extends Application {
    public static void main(String args[]){
        launch(args);
    }

    private static int defaultChatPort=55555;
    private static String defaultServer="localhost";

    @Override
    public void start(Stage primaryStage) throws Exception {
        Properties clientProps=new Properties();
        try {
            clientProps.load(StartClient.class.getResourceAsStream("/chatclient.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatclient.properties "+e);
            return;
        }
        String serverIP=clientProps.getProperty("chat.server.host",defaultServer);
        int serverPort=defaultChatPort;
        try{
            serverPort=Integer.parseInt(clientProps.getProperty("chat.server.port"));
        }catch(NumberFormatException ex){
            System.err.println("Wrong port number "+ex.getMessage());
            System.out.println("Using default port: "+defaultChatPort);
        }
        System.out.println("Using server IP "+serverIP);
        System.out.println("Using server port "+serverPort);
        IServerServices server=new ChatServerObjectProxy(serverIP, serverPort);

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
