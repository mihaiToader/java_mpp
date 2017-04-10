package Gui.MainWindow;

import Services.IServicesClient;
import Services.LabException;
import Services.ServicesApplication;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import model.Competition;
import model.Match;
import model.Ticket;
import model.User;
import Services.IServerServices;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import persistance.jdbc.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Mihai on 21.03.2017.
 */
public class MainWindowController implements IServicesClient {
    @FXML
    private TableView<Match> tableMatch;

    @FXML
    private TableColumn<Match, String> columnTableMatchName;

    @FXML
    private TableColumn<Match, String> columnTableMatchAvaibleSeats;

    @FXML
    private Label labelUsername;

    @FXML
    private ComboBox<Competition> comboBoxCompetition;

    @FXML
    private Label labelTotalSeats;

    @FXML
    private Label labelTicketCost;

    @FXML
    private TextField textFieldSearch;


    @FXML
    private TextField textFieldCustomerName;

    @FXML
    private TextField textFieldSeatsNumber;

    private Stage primaryStage;

    private IServerServices server;

    private Alert alert = new Alert(Alert.AlertType.ERROR);

    private User user;

    private Scene loginScene;

    private ObservableList<Competition>  competitionObservableList;

    private ObservableList<Match> matchObservableList;

    private ServicesApplication serv1;

    @FXML
    private ProgressBar loadingbar;

    public void setController(Stage primaryStage, IServerServices server, User user, Scene loginScene){
        this.primaryStage = primaryStage;
        this.server = server;
        this.user = user;
        this.loginScene = loginScene;
        initServer();
        initializeLabelUsername();
        initializeObservableLists();
        initializeTableColumn();
        initializeTable();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                try {
                    server.logOut();
                } catch (LabException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initializeObservableLists(){
        competitionObservableList = FXCollections.observableArrayList(server.getAllCompetition());
        initializeCompetition();
        matchObservableList = FXCollections.observableArrayList();
        refreshComboBox();
        loadCompetitionData();
    }

    private void initServer() {
        Properties serverProps=new Properties();
        try {
            serverProps.load(MainWindowController.class.getResourceAsStream("/bd.config"));
        } catch (IOException e) {
            return;
        }
        DataBaseManager dbm = null;
        try {
            dbm = new DataBaseManager(serverProps);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        RepositoryUser userRepo=new RepositoryUser(dbm);
        RepositoryCompetition repositoryCompetition = new RepositoryCompetition(dbm);
        RepositoryMatch repositoryMatch = new RepositoryMatch(dbm);
        RepositoryTicket repositoryTicket = new RepositoryTicket(dbm);
        serv1=new ServicesApplication(repositoryMatch, repositoryCompetition, repositoryTicket, userRepo);
    }

    private void initializeLabelUsername(){
        labelUsername.setText(user.getUsername());
    }

    public void initializeMatchObservableList(){
        if (comboBoxCompetition.getSelectionModel().getSelectedItem() != null){
            matchObservableList.setAll(server.getAllMatchesFromCompetition(comboBoxCompetition.getSelectionModel().getSelectedItem().getId()));
        }
    }

    private void initializeCompetitionObservableList(){
        competitionObservableList.setAll(serv1.getAllCompetition());
    }

    private void refreshComboBox(){
        comboBoxCompetition.getSelectionModel().selectFirst();
    }

    private void initializeCompetition(){
        comboBoxCompetition.setItems(competitionObservableList);
    }

    private void initializeTable(){
        tableMatch.setItems(matchObservableList);
    }

    private void initializeTableColumn(){
            columnTableMatchName.setCellValueFactory(new PropertyValueFactory<>("name"));
            columnTableMatchAvaibleSeats.setCellValueFactory(x->{

                if (x.getValue().getAvaibleSeats().equals(0)) return new SimpleStringProperty("Sold out!");
                return new SimpleStringProperty(x.getValue().getAvaibleSeats().toString());

            });
    }

    private void loadCompetitionData(){
        Competition c = comboBoxCompetition.getSelectionModel().getSelectedItem();
        if (c != null){
            initializeMatchObservableList();
            labelTicketCost.setText(c.getTicketCost().toString() + "$");
            labelTotalSeats.setText(c.getSeats().toString());
        }
    }

    private void clearTextFields(){
        textFieldCustomerName.clear();
        textFieldSeatsNumber.clear();
    }

    @FXML
    void reloadMatches(ActionEvent event) {
        loadCompetitionData();
    }

    @FXML
    void doLogout(ActionEvent event) {
        /*try {
            server.logOut();
        } catch (LabException e) {
            e.printStackTrace();
        }*/
        primaryStage.setScene(loginScene);
        primaryStage.setResizable(false);
    }

    @FXML
    void doRefresh(ActionEvent event) {
        textFieldSearch.clear();
        initializeMatchObservableList();
    }

    @FXML
    void doSearch(ActionEvent event) {
        matchObservableList.setAll(server.searchMatch(textFieldSearch.getText(),comboBoxCompetition.getSelectionModel().getSelectedItem().getId()));
    }

    @FXML
    void doSell(ActionEvent event) {
        Integer seats = 0;
        if (textFieldCustomerName.getText().equals("")){
            alert.setContentText("Customer Name can't be empty");
            alert.show();
            return;
        }
        try{
            seats = Integer.parseInt(textFieldSeatsNumber.getText());
        }catch (NumberFormatException e){
            alert.setContentText(e.getMessage());
            alert.show();
            return;
        }
        Match m = tableMatch.getSelectionModel().getSelectedItem();
        if (m == null){
            alert.setContentText("Choose a match!");
            alert.show();
        }else{
            if (m.getAvaibleSeats() == 0){
                alert.setContentText("Sold out!");
                alert.show();
            }else if (m.getAvaibleSeats()-seats<0){
                alert.setContentText("Not enough seats!");
                alert.show();
            }else{
                server.addTicket(seats, m.getId(), textFieldCustomerName.getText());
                clearTextFields(); return;
            }
        }
        //initializeMatchObservableList();
    }


}
