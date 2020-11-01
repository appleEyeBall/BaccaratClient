package controller;

import javafx.application.Application;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Packet;

import java.io.*;
import java.net.Socket;

public class JavaFXTemplate extends Application implements EventHandler, Serializable {
	Stage primaryStage;
	VBox mainRoot;
	GameSceneController gameSceneController;
	public TextField ipAddress;
	public TextField clientName;
	public TextField portNum;
	public Button connectBtn;
	private Packet packet;
	private Socket socket;

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		launch(args);
//	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
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

	public void showGameScene(ObjectOutputStream out) throws IOException {
		mainRoot = new VBox();
		gameSceneController = new GameSceneController(mainRoot, socket, packet, out);
		primaryStage.setScene(new Scene(mainRoot,600,600));
		primaryStage.show();

	}

	@Override
	public void handle(Event event) {
		if (event.getSource() == connectBtn){
			try {
				// connect to server, then show game scene
				ObjectOutputStream out = connectToServer(Integer.valueOf(portNum.getText()));
				showGameScene(out);
			} catch (IOException | ClassNotFoundException e) {    //
				e.printStackTrace();
			}

		}
	}

	public ObjectOutputStream connectToServer(int portNumber) throws IOException, ClassNotFoundException {

		socket = new Socket(ipAddress.getText(), portNumber);
		ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());

		packet = new model.Packet(socket.getLocalSocketAddress().toString(),portNumber, clientName.getText());
		outStream.writeObject(packet);
		outStream.reset();
		return outStream;
	}
}
