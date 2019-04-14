package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

import controller.ManageFXMLMainController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Brenner
 */
public class JavaFX_Server extends Application {

	TextField textTitle;
	Label labelPort;
	Label labelSys, labelIp;
	TextArea textAreaMsg;
	CheckBox optWelcome;
	static ManageFXMLMainController manageController;

	//Carrega a tela FXMLMainView
	@Override
	public void start(Stage primaryStage) throws IOException {

		FXMLLoader fxmlLoader = new FXMLLoader();
		Pane root = fxmlLoader.load(getClass().getResource("/view/FXMLMainView.fxml").openStream());
		manageController = (ManageFXMLMainController) fxmlLoader.getController();

		Scene scene = new Scene(root, 500, 400);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static ManageFXMLMainController getManageController() {
		return manageController;
	}

	public void setManageController(ManageFXMLMainController manageController) {
		this.manageController = manageController;
	}

}
