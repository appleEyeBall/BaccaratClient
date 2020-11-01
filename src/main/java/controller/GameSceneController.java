package controller;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import model.Card;
import model.CardVisual;
import model.Packet;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class GameSceneController extends Thread implements EventHandler {

    VBox gameScene;
    HBox scoreRow;
    HBox playArea;
    HBox bidRow;
    Button makeDraw;
    HBox playerAreaFirst;
    HBox playerAreaSecond;
    HBox bankerAreaFirst;
    HBox bankerAreaSecond;
    Button play;
    Button quit;
    Button playerWins;
    Button bankerWins;
    Button tie;
    TextField dollars;
    Socket socket;
    Packet packet;
    ObjectOutputStream out;
    ObjectInputStream in;
    int countClicks;

    public GameSceneController(VBox gameScene, Socket socket, Packet packet, ObjectOutputStream out) throws IOException {
        this.socket = socket;
        this.gameScene = gameScene;
        this.packet = packet;
        countClicks = 0;
        this.out = out;
        in = new ObjectInputStream(this.socket.getInputStream());

        gameScene.setPadding(new Insets(10,10,10,10));

        scoreRow =  new HBox();
        createScoreRow();

        playArea = new HBox();
        createPlayArea();
        bidRow = new HBox();
        bidRow.setSpacing(30);
        createControlsArea();

        gameScene.getChildren().addAll(scoreRow,playArea, bidRow);
        this.start();
    }


    public void createScoreRow(){

        Label playerCurrentScore = new Label("0");
        playerCurrentScore.setAlignment(Pos.CENTER);
        Label playerLabel = new Label("PLAYER");
        Label baccarat = new Label("BACCARAT");
        Label bankerLabel = new Label("BANKER");
        Label bankerCurrentScore = new Label("0");
        bankerCurrentScore.setAlignment(Pos.CENTER);
        bankerLabel.setAlignment(Pos.CENTER);
        playerLabel.setAlignment(Pos.CENTER);
        baccarat.setAlignment(Pos.CENTER);

        playerCurrentScore.setPrefSize(50,50);
        playerCurrentScore.setBackground(new Background(new BackgroundFill(Color.INDIANRED, CornerRadii.EMPTY, Insets.EMPTY)));
        bankerCurrentScore.setPrefSize(50,50);
        bankerCurrentScore.setBackground(new Background(new BackgroundFill(Color.INDIANRED, CornerRadii.EMPTY, Insets.EMPTY)));

        playerLabel.setBackground(new Background(new BackgroundFill(Color.INDIANRED, CornerRadii.EMPTY, Insets.EMPTY)));
        bankerLabel.setBackground(new Background(new BackgroundFill(Color.INDIANRED, CornerRadii.EMPTY, Insets.EMPTY)));

        playerLabel.setPrefSize(140,50);
        bankerLabel.setPrefSize(140,50);
        baccarat.setPrefSize(120,50);

        scoreRow.setSpacing(600/16);  // might make a UTIL.java later don't worry
        scoreRow.getChildren().addAll(playerCurrentScore,playerLabel, baccarat,bankerLabel, bankerCurrentScore);

    }
    public void createPlayArea(){
        playArea.setMinHeight(370);
        Card sampleCard = new Card("test", 0);
        CardVisual cardVisual = new CardVisual(sampleCard);
        cardVisual.getVisual().setVisible(false);

        // divide the play area into three columns
        VBox playerArea = new VBox();
        playerArea.setMinWidth(600/2.5);
        playerArea.setSpacing(10);
        VBox drawArea = new VBox();
        drawArea.setMinWidth(600/5);
        VBox bankerArea = new VBox();
        bankerArea.setSpacing(10);
        bankerArea.setMinWidth(600/2.5);

        // divide playerArea into column into 2 rows
        playerAreaFirst = new HBox();
        playerAreaFirst.setAlignment(Pos.CENTER);
        playerAreaFirst.setSpacing(10);
        playerAreaSecond = new HBox();
        playerAreaSecond.setAlignment(Pos.CENTER);
        playerArea.getChildren().addAll(playerAreaFirst, playerAreaSecond);

        // do draw area
        makeDraw = new Button("DRAW");
        makeDraw.setPrefSize(70,50);
        makeDraw.setOnAction(this);
        drawArea.setAlignment(Pos.CENTER);
        drawArea.getChildren().add(makeDraw);



        // divide bankerArea into column into 2 rows
        bankerAreaFirst = new HBox();
        bankerAreaFirst.setAlignment(Pos.CENTER);
        bankerAreaFirst.setSpacing(10);
        bankerAreaSecond = new HBox();
        bankerAreaSecond.setAlignment(Pos.CENTER);
        bankerArea.getChildren().addAll(bankerAreaFirst,  bankerAreaSecond);


            // TODO: delete later hard code
//        Card testCard = new Card("test", 7);
//        CardVisual testCardVisual = new CardVisual(testCard);
//        CardVisual testCardVisual2 = new CardVisual(testCard);
//        CardVisual testCardVisual3 = new CardVisual(testCard);
//        CardVisual testCardVisual4 = new CardVisual(testCard);
//        CardVisual testCardVisual5 = new CardVisual(testCard);
//        CardVisual testCardVisual6 = new CardVisual(testCard);
//        playerAreaFirst.getChildren().addAll(testCardVisual.getVisual(), testCardVisual2.getVisual());
//        playerAreaSecond.getChildren().add(testCardVisual3.getVisual());
//        bankerAreaFirst.getChildren().addAll(testCardVisual4.getVisual(), testCardVisual5.getVisual());
//        bankerAreaSecond.getChildren().add(testCardVisual6.getVisual());
        // TODO: end of hard code


        playArea.setBackground(new Background(new BackgroundFill(Color.DARKGREEN, null, null)));
        playArea.getChildren().addAll(playerArea, drawArea, bankerArea);
        playArea.setPadding(new Insets(10,10,0,0));


    }
    public void createControlsArea(){
        VBox bidAmount = new VBox();
        Label bidLabel = new Label();
        bidLabel.setText("Enter Your Bid Amount");
        bidLabel.setAlignment(Pos.CENTER);
        bidLabel.setPrefSize(180,50);
        dollars = new TextField();
        dollars.setPromptText("$");
        dollars.setPrefSize(180,30);
        dollars.setAlignment(Pos.CENTER);
        bidAmount.getChildren().addAll(bidLabel,dollars);

        VBox betChoices = new VBox();
        Label betsLabel = new Label();
        betsLabel.setText("What will you bet on?");
        betsLabel.setPrefSize(180,50);

        playerWins = new Button();
        playerWins.setText("Player");
        playerWins.setAlignment(Pos.CENTER);
        playerWins.setPrefSize(180,30);
        playerWins.setOnAction(this);

        bankerWins = new Button();
        bankerWins.setText("Banker");
        bankerWins.setAlignment(Pos.CENTER);
        bankerWins.setPrefSize(180,30);
        bankerWins.setOnAction(this);

        tie = new Button();
        tie.setText("Tie");
        tie.setAlignment(Pos.CENTER);
        tie.setPrefSize(180,30);
        tie.setOnAction(this);
        betChoices.setSpacing(10);

        betChoices.getChildren().addAll(betsLabel,playerWins,bankerWins,tie);

        VBox controls = new VBox();
        play = new Button();
        play.setText("Play");
        play.setAlignment(Pos.CENTER);
        play.setPrefSize(80,50);
        play.setOnAction(this);

        quit = new Button();
        quit.setText("Quit");
        quit.setAlignment(Pos.CENTER);
        quit.setPrefSize(80,50);
        quit.setOnAction(this);
        controls.setSpacing(30);
        controls.setAlignment(Pos.TOP_RIGHT);

        controls.getChildren().addAll(play,quit);

        bidRow.setPadding(new Insets(20,10,10,10));
        bidRow.getChildren().addAll(bidAmount,betChoices, controls);

    }

    public void displayResults(){
        String result;
        if(packet.getPlayerDetails().getBetChoice().equals(packet.getWinnerMsg())){
            result = "won";
        }
        else{
            result = "lose";
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText("--Game Results--");
        ArrayList<Card> playerHand = packet.getPlayerDetails().getPlayerHand();
        ArrayList<Card> bankerHand = packet.getPlayerDetails().getBankerHand();
        alert.setContentText("Player's hand Total : " + packet.getPlayerDetails().getHandTotal(playerHand));
        alert.setContentText("Banker's hand Total : " + packet.getPlayerDetails().getHandTotal(bankerHand));

        if(packet.getWinnerMsg().equals("Tie")){
            alert.setContentText("It's a Tie !");
        }
        else {
            alert.setContentText(packet.getWinnerMsg() + "wins !");
        }
        alert.setContentText("You bet on " + packet.getPlayerDetails().getBetChoice() + ", you " + result);

        alert.showAndWait();
    }
    @Override
    public void handle(Event event) {
        if (event.getSource() == makeDraw){     // send demo packet to server
            try {
                out.reset();            // reset the ObjectOutputStream
                packet.getPlayerDetails().setBidAmount(50);
                packet.getPlayerDetails().setBetChoice("Player");
                out.writeObject(packet);
                System.out.println("sent packet to server");

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        if(event.getSource() == play){

        }
        if(event.getSource() == playerWins){
            countClicks++;
            if(countClicks % 2!= 0) {
                bankerWins.setDisable(true);
                tie.setDisable(true);
                playerWins.setBackground(new Background(new BackgroundFill(Color.INDIANRED, null, null)));
                packet.getPlayerDetails().setBetChoice(playerWins.getText());
            }
            else{
                bankerWins.setDisable(false);
                tie.setDisable(false);
                playerWins.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, null, null)));
            }

        }
        if(event.getSource() == bankerWins){
            countClicks++;
            if(countClicks % 2!= 0) {
                playerWins.setDisable(true);
                tie.setDisable(true);
                bankerWins.setBackground(new Background(new BackgroundFill(Color.INDIANRED, null, null)));
                packet.getPlayerDetails().setBetChoice(bankerWins.getText());
            }
            else{
                playerWins.setDisable(false);
                tie.setDisable(false);
                bankerWins.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, null, null)));
            }

        }
        if(event.getSource() == tie){
            countClicks++;
            if(countClicks % 2!= 0) {
                bankerWins.setDisable(true);
                playerWins.setDisable(true);
                tie.setBackground(new Background(new BackgroundFill(Color.INDIANRED, null, null)));
                packet.getPlayerDetails().setBetChoice(tie.getText());
            }
            else{
                playerWins.setDisable(false);
                bankerWins.setDisable(false);
                tie.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, null, null)));
            }

        }

    }

    @Override
    public void run() {
        try {
            while (true){
                System.out.println("Waiting on response from server...");
                Packet packetFromServer = (Packet) in.readObject();

                // if packet does not belong to this client, ignore it
                if (!packetFromServer.getIpAddress().toString().equals(socket.getLocalSocketAddress().toString())){
                    System.out.println("Not my packet. ignoring...");
                    continue;
                }
                System.out.println("IT is my packet");
                // assign packet to what we get from server
                packet = packetFromServer;
                updateWithCards(packet);

            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void updateWithCards(Packet packet){
       Platform.runLater(new Runnable() {
           @Override
           public void run() {
               ArrayList<Card> hand = packet.getPlayerDetails().getPlayerHand();
               CardVisual cardVisual = new CardVisual(hand.get(0));

               if (playerAreaFirst.getChildren().size() ==0){
                   System.out.println("size is 0");
                   playerAreaFirst.getChildren().add(cardVisual.getVisual());
                   System.out.println("size changed to "+playerAreaFirst.getChildren().size());
               }
               else if (playerAreaFirst.getChildren().size() ==1){
                   System.out.println("size is 1");
                   playerAreaFirst.getChildren().add(cardVisual.getVisual());
               }
               else{
                   System.out.println("size is 2");
                   playerAreaSecond.getChildren().add(cardVisual.getVisual());
               }

           }
       });

    }
}
