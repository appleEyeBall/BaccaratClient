import javafx.application.Application;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class JavaFXTemplate extends Application implements EventHandler,Serializable {
	Stage primaryStage;
	VBox mainRoot;
	ClientGUI gameSceneController;
	public TextField ipAddress;
	public TextField clientName;
	public TextField portNum;
	public Button connectBtn;
	private Packet packet;

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		launch(args);
//	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

		this.primaryStage = primaryStage;
		VBox parent = new VBox();
		parent.setPadding(new Insets(100,120,0,120));
		clientName =  new TextField();
		clientName.setPromptText("Enter name:");

		ipAddress =  new TextField();
		ipAddress.setPromptText("Enter IP Address:");

		portNum = new TextField();
		portNum.setPromptText("Enter Port Number:");

		connectBtn = new Button("Connect");
		clientName.setAlignment(Pos.CENTER);
		ipAddress.setAlignment(Pos.CENTER);
		portNum.setAlignment(Pos.CENTER);
		connectBtn.setAlignment(Pos.CENTER);
		connectBtn.setPrefWidth(70);
		connectBtn.setPrefHeight(36);

		connectBtn.setOnAction(this);

		parent.getChildren().addAll(clientName,ipAddress,portNum, connectBtn);
		Scene scene = new Scene(parent, 600, 600);
		primaryStage.setTitle("Baccarrat Game");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public void LaunchMainScene(Event event) throws IOException {
		mainRoot = new VBox();
		gameSceneController = new ClientGUI(mainRoot);
		primaryStage.setScene(new Scene(mainRoot,600,600));
		primaryStage.show();

	}

	@Override
	public void handle(Event event) {
		if (event.getSource() == connectBtn){
			try {
				LaunchMainScene(event);
				connectToServer(Integer.valueOf(portNum.getText()));
			} catch (IOException | ClassNotFoundException e) {    //
				e.printStackTrace();
			}

		}
	}

	public void connectToServer(int portNumber) throws IOException, ClassNotFoundException {

		Socket socket = new Socket(ipAddress.getText(), portNumber);
		ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());

		packet = new Packet(ipAddress.getText(),portNumber);

		Packet.PlayerDetails playerDetails= packet.new PlayerDetails();
		playerDetails.setPlayerName(clientName.getText());
		outStream.writeObject(packet);
		Packet clientPacket = (Packet) inStream.readObject();

		System.out.println("Packet received from server");
		System.out.println("Game Result is: " + clientPacket.getWinnerMsg());

		outStream.close();
		socket.close();



	}
}
