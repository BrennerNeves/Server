package application;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import application.ServerSocketAcceptedThread;
import controller.ManageFXMLMainController;
import javafx.application.Platform;
import model.Message;
import model.User;

/**
 * @author Brenner
 */
public class SocketServerThread extends Thread {

	ManageFXMLMainController manageController = JavaFX_Server.getManageController();
	int socketServerPORT;
	int count = 0;
	List<User> userList = new ArrayList<>();
	List<Message> messageList = new ArrayList<>();

	ServerSocket serverSocket;

	@Override
	public void run() {
		try {

			socketServerPORT = Integer.parseInt(manageController.getTextFieldPortServer().getText());
			Socket socket = null;

			serverSocket = new ServerSocket(socketServerPORT);
			Platform.runLater(new Runnable() {

				@Override
				public void run() {

					String text = "Servidor Connectado na Porta: " + serverSocket.getLocalPort();
					manageController.getLblStatus().setText(text);
				}
			});

			while (manageController.getStopServer()) {
				socket = serverSocket.accept();
				count++;

				// Start another thread
				// to prevent blocked by empty dataInputStream
				Thread acceptedThread = new Thread(	new ServerSocketAcceptedThread(socket, count, userList, messageList));
				acceptedThread.setDaemon(true); // terminate the thread when program end
				acceptedThread.start();

			}
		} catch (IOException ex) {
			Logger.getLogger(JavaFX_Server.class.getName()).log(Level.SEVERE, null, ex);

		}

	}

	public void Stop() {
		try {
			new Socket(serverSocket.getInetAddress(), serverSocket.getLocalPort()).close();
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public int getSocketServerPORT() {
		return socketServerPORT;
	}

	public void setSocketServerPORT(int socketServerPORT) {
		this.socketServerPORT = socketServerPORT;
	}

}