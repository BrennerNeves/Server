package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.SocketServerThread;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Message;
import model.User;

/**
 * @author Brenner
 */
public class ManageFXMLMainController implements Initializable {

	

	@FXML
	private Button btnStartServer;

	@FXML
	private TextField textFieldPortServer;

	@FXML
	private Label lblStatus;

	@FXML
	private ListView<User> lvConnectedUsers;

	@FXML
	private ListView<Message> lvMessageUsers;

	@FXML
	private Button btnStopServer;

	@FXML
	private Boolean stopServer = true;
	
	ObservableList<User> obsConnectedUsers;
	
	ObservableList<Message> obsMessageUsers;
	

	SocketServerThread socketServerThread;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	//Ação do click do botao StartServer verifica se a porta esta correta e inicia o servidor.
	@FXML
	private void onBtnStartServerClick(ActionEvent event) {
		String portText = textFieldPortServer.getText() == null ? "0" : textFieldPortServer.getText();
		int port = Integer.parseInt(portText );
		
		System.out.println("Porta usada: " + port);
		
		if(port<= 0 || port >= 655535 ) {
            Platform.runLater(new Runnable() {

                @Override
                public void run() {
                	
                	
                	String text = "Favor digitar uma porta válida! " ;
                	getLblStatus().setText(text);
                }
            });
		}else{
			btnStartServer.setDisable(true); 
			btnStopServer.setDisable(false); 
			socketServerThread = new SocketServerThread();
			socketServerThread.setDaemon(true); // terminate the thread when program end
			socketServerThread.start();
		}
	}

	@FXML
	private void onBtnStopServerClick(ActionEvent event) throws IOException {
		setStopServer(false);
		socketServerThread.Stop();
		setStopServer(true);
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
            	
            	
            	String text = "Servidor Desconectado! " ;
            	getLblStatus().setText(text);
                btnStartServer.setDisable(false);
                btnStopServer.setDisable(true); 
                lvConnectedUsers.setItems(null);
                lvMessageUsers.setItems(null);
            }
        });

	}
	
	
	public void loadListUsers(List<User> listUsers) {
		Platform.setImplicitExit(false);
		obsConnectedUsers = FXCollections.observableArrayList(listUsers);		
		lvConnectedUsers.setItems(obsConnectedUsers);
		
	}
	
	
	public void loadListMessages(List<Message> listMessages) {
		Platform.setImplicitExit(false);
		obsMessageUsers = FXCollections.observableArrayList(listMessages);		
		lvMessageUsers.setItems(obsMessageUsers);
		
	}

	public Button getBtnStartServer() {
		return btnStartServer;
	}

	public void setBtnStartServer(Button btnStartServer) {
		this.btnStartServer = btnStartServer;
	}

	public TextField getTextFieldPortServer() {
		return textFieldPortServer;
	}

	public void setTextFieldPortServer(TextField textFieldPortServer) {
		this.textFieldPortServer = textFieldPortServer;
	}

	public Label getLblStatus() {
		return lblStatus;
	}

	public void setLblStatus(Label lblStatus) {
		this.lblStatus = lblStatus;
	}


	public ListView<User> getLvConnectedUsers() {
		return lvConnectedUsers;
	}

	public void setLvConnectedUsers(ListView<User> lvConnectedUsers) {
		this.lvConnectedUsers = lvConnectedUsers;
	}

	public ListView<Message> getLvMessageUsers() {
		return lvMessageUsers;
	}

	public void setLvMessageUsers(ListView<Message> lvMessageUsers) {
		this.lvMessageUsers = lvMessageUsers;
	}

	public Button getBtnStopServer() {
		return btnStopServer;
	}

	public void setBtnStopServer(Button btnStopServer) {
		this.btnStopServer = btnStopServer;
	}

	public Boolean getStopServer() {
		return stopServer;
	}

	public void setStopServer(Boolean stopServer) {
		this.stopServer = stopServer;
	}

}
