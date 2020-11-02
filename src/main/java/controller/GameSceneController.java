package controller;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import model.Card;
import model.CardVisual;
import model.BaccaratInfo;
import util.Util;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

//This class controls the Client game scene GUI and handles all the socket communication with the server whenever needed

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
    Button playBtn;
    Button quitBtn;
    Button playerWins;
    Button bankerWins;
    Button tie;
    TextField dollars;
    Socket socket;
    BaccaratInfo baccaratInfo;
    ObjectOutputStream out;
    ObjectInputStream in;
    int countClicks;
    int drawBtnClicks;
    int playerCard;
    int bankerCard;
    Label playerCurrentScore;
    Label bankerCurrentScore;


    public GameSceneController(VBox gameScene, Socket socket, BaccaratInfo baccaratInfo, ObjectOutputStream out) throws IOException {
        this.socket = socket;
        this.gameScene = gameScene;
        this.baccaratInfo = baccaratInfo;
        this.out = out;

        gameScene.setPadding(new Insets(10,10,10,10));

        scoreRow =  new HBox();  // this row displays the current scores for each player
        createScoreRow();
        playArea = new HBox();  // this Hbox is the main play area where cards are presented
        createPlayArea();
        bidRow = new HBox();    // this HBox consists of the bidding information and selection made by the client
        bidRow.setSpacing(30);
        createControlsArea();
        initializeGame();

        gameScene.getChildren().addAll(scoreRow,playArea, bidRow);
        this.start();

    }


    public void createScoreRow(){

        playerCurrentScore = new Label("0");    // display the player's current score

        playerCurrentScore.setAlignment(Pos.CENTER);
        Label playerLabel = new Label("PLAYER");
        Label baccarat = new Label("BACCARAT");
        baccarat.setUnderline(true);
        Label bankerLabel = new Label("BANKER");
        bankerCurrentScore = new Label("0");    // display the banker's current score

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

        scoreRow.setSpacing(600/16);
        scoreRow.getChildren().addAll(playerCurrentScore,playerLabel, baccarat,bankerLabel, bankerCurrentScore); // adding all the elements to the scoreRow

    }
    public void createPlayArea(){
        playArea.setMinHeight(380);
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

        playArea.setBackground(new Background(new BackgroundFill(Color.DARKGREEN, null, null)));
        playArea.getChildren().addAll(playerArea, drawArea, bankerArea);
        playArea.setPadding(new Insets(10,10,0,0));

    }

    public void createControlsArea(){
        // create a VBox that sores the bid amount input taken from client
        VBox bidAmount = new VBox();
        Label bidLabel = new Label();
        bidLabel.setText("Enter Your Bid Amount");
        bidLabel.setAlignment(Pos.CENTER);
        bidLabel.setPrefSize(180,50);

        dollars = new TextField();  // contains the amount
        dollars.setPromptText("$");
        dollars.setPrefSize(180,30);
        dollars.setAlignment(Pos.CENTER);
        dollars.setOnKeyReleased(this);
        bidAmount.getChildren().addAll(bidLabel,dollars);

        // create a VBox that shows the client's bet for the game
        VBox betChoices = new VBox();
        Label betsLabel = new Label();
        betsLabel.setText("What will you bet on?");
        betsLabel.setPrefSize(180,50);

        playerWins = new Button();   // bet for player
        playerWins.setText("Player");
        playerWins.setAlignment(Pos.CENTER);
        playerWins.setPrefSize(180,30);
        playerWins.setOnAction(this);

        bankerWins = new Button();   // bet for banker
        bankerWins.setText("Banker");
        bankerWins.setAlignment(Pos.CENTER);
        bankerWins.setPrefSize(180,30);
        bankerWins.setOnAction(this);

        tie = new Button();  // bet for a Tie in the game
        tie.setText("Tie");
        tie.setAlignment(Pos.CENTER);
        tie.setPrefSize(180,30);
        tie.setOnAction(this);
        betChoices.setSpacing(10);

        betChoices.getChildren().addAll(betsLabel,playerWins,bankerWins,tie);

        // create a play button to start the game
        VBox controls = new VBox();
        playBtn = new Button();
        playBtn.setText("Play");
        playBtn.setAlignment(Pos.CENTER);
        playBtn.setPrefSize(80,50);
        playBtn.setOnAction(this);

        //create a quit button to end the game and ends the server connection
        quitBtn = new Button();
        quitBtn.setText("Quit");
        quitBtn.setAlignment(Pos.CENTER);
        quitBtn.setPrefSize(80,50);
        quitBtn.setOnAction(this);
        controls.setSpacing(30);
        controls.setAlignment(Pos.TOP_RIGHT);

        controls.getChildren().addAll(playBtn, quitBtn);

        bidRow.setPadding(new Insets(20,10,10,10));
        bidRow.getChildren().addAll(bidAmount,betChoices, controls);

    }

    // display the results of the game after its over in an alert window
    public void displayResults(){
        String result;
        if(baccaratInfo.getPlayerDetails().getBetChoice().equals(baccaratInfo.getWinnerMsg())){
            result = "won";
        }
        else{
            result = "lose";
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeight(300);
        alert.setWidth(300);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.setTitle("Game Over");
        alert.setHeaderText("--Game Results--");

        //get the player and banker hands after game finishes from the BaccaratInfo
        ArrayList<Card> playerHand = baccaratInfo.getPlayerDetails().getPlayerHand();
        ArrayList<Card> bankerHand = baccaratInfo.getPlayerDetails().getBankerHand();

        // print the content for the result
        if(baccaratInfo.getWinnerMsg().equals("Tie")){
            alert.setContentText("Player's hand Total : " + baccaratInfo.getPlayerDetails().getHandTotal(playerHand)+"\n"+
           "Banker's hand Total : " + baccaratInfo.getPlayerDetails().getHandTotal(bankerHand)+ "\n"+
            "It's a Tie ! "+ "You bet on " + baccaratInfo.getPlayerDetails().getBetChoice() + ", you " + result);
        }
        else {
            alert.setContentText("Player's hand Total : " + baccaratInfo.getPlayerDetails().getHandTotal(playerHand)+"\n"+
            "Banker's hand Total : " + baccaratInfo.getPlayerDetails().getHandTotal(bankerHand)+ "\n"+
             baccaratInfo.getWinnerMsg() + " wins !"+ "You bet on " + baccaratInfo.getPlayerDetails().getBetChoice() + ", you " + result);
        }
        alert.showAndWait();
    }


    @Override
    public void handle(Event event) {

        // event handling for bid amount textfield
        if(event.getSource() == dollars){
            System.out.println("enter dollars");
            baccaratInfo.getPlayerDetails().setBidAmount(Integer.valueOf(dollars.getText()));  // set the amount in the BaccaratInfo
            activateButtons();  // activate the play game buttons
        }

        // event handling for the draw button that reads BaccaratInfo sent from the server and displays the cards
        if (event.getSource() == makeDraw) {     // send demo packet to server
            if (baccaratInfo.getPlayerDetails().getPlayerHand()==null){
                try {

                    baccaratInfo.actionRequest = Util.ACTION_REQUEST_DRAW;
                    out.reset();            // reset the ObjectOutputStream
                    out.writeObject(baccaratInfo);
                    System.out.println("sent packet to server");


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else { // update the current scores for each player
                updateWithCards(baccaratInfo);
                drawBtnClicks++;
                if(drawBtnClicks % 2 != 0){
                    updateCurrentScores(baccaratInfo.getPlayerDetails().getPlayerHand(),playerCurrentScore,playerCard);
                    playerCard++;
                }
                else{
                    updateCurrentScores(baccaratInfo.getPlayerDetails().getBankerHand(),bankerCurrentScore,bankerCard);
                    bankerCard++;

                }
            }

        }

        if(event.getSource() == playBtn){
            // get all game info and send to server
            activateButtons();
            baccaratInfo.actionRequest = Util.ACTION_REQUEST_PLAY;
            try {
                out.reset();
                out.writeObject(baccaratInfo);   // send the BaccratInfo to server that contains Bidding details from the client side
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        if (event.getSource() == quitBtn) {
            try {
                // notify the server of our intention to quit game
                baccaratInfo.setClientPlaying(-1);
                baccaratInfo.actionRequest = Util.ACTION_REQUEST_QUIT;
                out.reset();
                out.writeObject(baccaratInfo);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("sent quit notification to server");
        }

        if(event.getSource() == playerWins){
            countClicks++;
            if(countClicks % 2!= 0) {
                bankerWins.setDisable(true);
                tie.setDisable(true);
                playerWins.setBackground(new Background(new BackgroundFill(Color.INDIANRED, null, null)));
                baccaratInfo.getPlayerDetails().setBetChoice(playerWins.getText());  // set bet choice to player
               activateButtons();

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
                baccaratInfo.getPlayerDetails().setBetChoice(bankerWins.getText());
                activateButtons();
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
                baccaratInfo.getPlayerDetails().setBetChoice(tie.getText());
                activateButtons();
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
            in = new ObjectInputStream(this.socket.getInputStream());  // create input stream for the socket

            while (true){
                System.out.println("Waiting on response from server...");
                BaccaratInfo baccaratInfoFromServer = (BaccaratInfo) in.readObject();

                // if packet does not belong to this client, ignore it
                if (!baccaratInfoFromServer.getIpAddress().toString().equals(socket.getLocalSocketAddress().toString())){
                    System.out.println("Not my packet. ignoring...");
                    continue;
                }
                else if (baccaratInfoFromServer.actionRequest.equals(Util.ACTION_REQUEST_QUIT)){
                    // server has told us we can quit
                    System.out.println("IT is my packet, quitting");
                    // assign packet to what we get from server
                    this.interrupt();   // interrupt the thread that listens for the server response
                    in.close();
                    out.close();
                    socket.close();
                    Platform.exit();
                    System.exit(0);
                }
                else{
                    System.out.println("IT is my packet, updating...");
                    baccaratInfo = baccaratInfoFromServer;
                    updateWithCards(baccaratInfo);

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            drawBtnClicks++;
                            System.out.println("Should update scores...");
                            updateCurrentScores(baccaratInfo.getPlayerDetails().getPlayerHand(),playerCurrentScore,playerCard);
                            playerCard++;

                        }
                    });
                }

            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //update cuurent scores for a given hand
    public void updateCurrentScores(ArrayList<Card> hand, Label scoreLbl,int index){
        ArrayList<Card> tempHand = new ArrayList<>();
        ArrayList<Integer> currScore = new ArrayList<>();

        for(Card card: hand){
            tempHand.add(card);
            currScore.add(baccaratInfo.getPlayerDetails().getHandTotal(tempHand));  // store the scores at each stage in the list
        }
        System.out.println("score: " + currScore.get(0));
        if(index >= hand.size()){
            return;
        }
        scoreLbl.setText(String.valueOf(currScore.get(index)));
    }

    // initialize the entire game scene when the game starts or restarts
    public void initializeGame(){

        // disable the buttons
        playBtn.setDisable(true);
        makeDraw.setDisable(true);
        playerWins.setDisable(false);
        bankerWins.setDisable(false);
        tie.setDisable(false);
        countClicks = 0;
        drawBtnClicks = 0;
        playerCard = 0;
        bankerCard = 0;
        // reset the Baccarat Info members  before starting a new game
        playerAreaFirst.getChildren().clear();
        playerAreaSecond.getChildren().clear();
        bankerAreaFirst.getChildren().clear();
        bankerAreaSecond.getChildren().clear();
        baccaratInfo.getPlayerDetails().setBankerHand(null);
        baccaratInfo.getPlayerDetails().setPlayerHand(null);
        baccaratInfo.getPlayerDetails().setBidAmount(0);
        playerCurrentScore.setText("0");
        bankerCurrentScore.setText("0");
        playerWins.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, null, null)));
        bankerWins.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, null, null)));
        tie.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, null, null)));
        baccaratInfo.getPlayerDetails().setBetChoice(null);


    }

    // activate the play and draw buttons when needed
     public void activateButtons(){

         if(!playBtn.isDisable()){
             makeDraw.setDisable(false);
             playBtn.setDisable(true);
         }
         if(baccaratInfo.getPlayerDetails().getBetChoice() != null && baccaratInfo.getPlayerDetails().getBidAmount() != 0){
             System.out.println("activated");
             playBtn.setDisable(false);
         }

     }
     // extract one card from the player and banker hands one at a time and display them on the screen
    public void updateWithCards(BaccaratInfo baccaratInfo){
       Platform.runLater(new Runnable() {
           @Override
           public void run() {
               System.out.println("Started run later");
               ArrayList<Card> playerHand = baccaratInfo.getPlayerDetails().getPlayerHand();
               ArrayList<Card> bankerHand = baccaratInfo.getPlayerDetails().getBankerHand();

               if (playerHand == null || bankerHand == null){
                   return;
               }
               else if (playerAreaFirst.getChildren().size() == 0){
                   CardVisual cardVisual = new CardVisual(playerHand.get(0));
                   playerAreaFirst.getChildren().add(cardVisual.getVisual());  // create the visuals for the card in a new class
               }
               else if (bankerAreaFirst.getChildren().size() == 0){
                   CardVisual cardVisual = new CardVisual(bankerHand.get(0));
                   bankerAreaFirst.getChildren().add(cardVisual.getVisual());
               }
               else if (playerAreaFirst.getChildren().size() ==1){
                   CardVisual cardVisual = new CardVisual(playerHand.get(1));
                   playerAreaFirst.getChildren().add(cardVisual.getVisual());
               }
               else if (bankerAreaFirst.getChildren().size() ==1){
                   CardVisual cardVisual = new CardVisual(bankerHand.get(1));
                   bankerAreaFirst.getChildren().add(cardVisual.getVisual());
               }
               else if (playerAreaSecond.getChildren().size() == 0 && playerHand.size()>2){
                   CardVisual cardVisual = new CardVisual(playerHand.get(2));
                   playerAreaSecond.getChildren().add(cardVisual.getVisual());
               }
               else if (bankerAreaSecond.getChildren().size() == 0 && bankerHand.size()>2){
                   CardVisual cardVisual = new CardVisual(bankerHand.get(2));
                   bankerAreaSecond.getChildren().add(cardVisual.getVisual());
               }
               // game ends - reset the hands to null
               else{
                   System.out.println("gonna send a game-over request");
                   displayResults();
                   baccaratInfo.getPlayerDetails().setPlayerHand(null);
                   baccaratInfo.getPlayerDetails().setBankerHand(null);
                   try {
                       baccaratInfo.actionRequest = Util.ACTION_REQUEST_GAME_OVER;
                       out.reset();
                       out.writeObject(baccaratInfo);
                       initializeGame();
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }

           }
       });

    }
}