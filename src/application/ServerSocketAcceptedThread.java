package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import controller.ManageFXMLMainController;
import javafx.application.Platform;
import model.Message;
import model.User;

/**
 * @author Brenner
 */
public class ServerSocketAcceptedThread extends Thread {

	//Recebe a instance da managerController
	ManageFXMLMainController manageController = JavaFX_Server.getManageController();
	
	//Atributos para o socket e pegar o que recebe do servidor e o que envia para o servidor
	Socket socket = null;
	DataInputStream dataInputStream = null;
	DataOutputStream dataOutputStream = null;
	
	//Atributos para auxiliar na hora de carregar os dados para as Listas
	List<User> userList;
	List<Message> messageList;
	int count;
	boolean aux;
	String newMessage;

	
	ServerSocketAcceptedThread(Socket s, int c, List<User> list, List<Message> messages) {
		socket = s;
		count = c;
		aux = true;
		userList = list;
		messageList = messages;
	}

	@Override
	public void run() {
		try {
			;
			Calendar cal = Calendar.getInstance();
			String date = cal.get(cal.DAY_OF_MONTH) + "/" + cal.get(cal.MONTH) + "/" + cal.get(cal.YEAR) + " "
					+ cal.get(cal.HOUR_OF_DAY) + ":" + cal.get(cal.MINUTE) + ":" + cal.get(cal.SECOND);
			dataInputStream = new DataInputStream(socket.getInputStream());
			dataOutputStream = new DataOutputStream(socket.getOutputStream());
			
			//Verifica se o usuario ja esta na lista caso nao estiver é um novo usuario que ira conectar
			for (User user : userList) {
				if (user.getIpAddres().equalsIgnoreCase(socket.getInetAddress().getHostAddress())) {
					aux = false;

				}

			}

			
			//Criar uma mensagem padrao na primeira conexxao
			String messageFromClient = dataInputStream.readUTF();
			if (aux == true) {
				newMessage = date + "-" + messageFromClient + socket.getInetAddress().getHostAddress() + ":" + socket.getPort();
			}
			
			//seta os valores de um novo usuario
			if (aux == true) {
				User user = new User();
				user.setId(count);
				user.setName(messageFromClient);
				user.setIpAddres(socket.getInetAddress().getHostAddress());
				Message message = new Message();
				message.setUser(user);
				message.getMessage().add(newMessage);
				userList.add(user);
				messageList.add(message);
				manageController.loadListUsers(userList);
				manageController.loadListMessages(messageList);
				dataOutputStream.writeUTF("true");
			} else {
				
				//verifica se o usuario esta desconectando para liberar seus dados
				for (Message message : messageList) {
					if (message.getUser().getIpAddres().equalsIgnoreCase(socket.getInetAddress().getHostAddress())) {
						if (messageFromClient.contains("true")) {
							messageList.remove(message);
							userList.remove(message.getUser());
							manageController.loadListUsers(userList);
							manageController.loadListMessages(messageList);
						} else
							message.getMessage().add(date + ": " + messageFromClient);
					}
				}
				manageController.loadListMessages(messageList);
				dataOutputStream.writeUTF("true");
			}
			aux = false;

		} catch (IOException ex) {
			Logger.getLogger(JavaFX_Server.class.getName()).log(Level.SEVERE, null, ex);

		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException ex) {
					Logger.getLogger(JavaFX_Server.class.getName()).log(Level.SEVERE, null, ex);
				}
			}

			if (dataInputStream != null) {
				try {
					dataInputStream.close();
				} catch (IOException ex) {
					Logger.getLogger(JavaFX_Server.class.getName()).log(Level.SEVERE, null, ex);
				}
			}

			if (dataOutputStream != null) {
				try {
					dataOutputStream.close();
				} catch (IOException ex) {
					Logger.getLogger(JavaFX_Server.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
	}

}
