import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import model.Card;
import model.CardVisual;
import model.Packet;

import java.io.IOException;

public class ClientGUI implements EventHandler {

    VBox gameScene;
   HBox scoreRow;
   HBox playArea;
   HBox bidRow;
   Button makeDraw;
   Button play;
   Button quit;
   Button playerWins;
   Button bankerWins;
   Button tie;
   TextField dollars;
   Packet gameDetails;

    public ClientGUI(VBox gameScene) throws IOException {

        this.gameScene = gameScene;
        gameScene.setPadding(new Insets(10,10,10,10));

        scoreRow =  new HBox();
        createScoreRow();

        playArea = new HBox();
        createPlayArea();

        bidRow = new HBox();
        bidRow.setSpacing(30);
        createControlsArea();

        gameScene.getChildren().addAll(scoreRow,playArea, bidRow);


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
        playerCurrentScore.setBackground(new Background(new BackgroundFill(Color.LIGHTCYAN, CornerRadii.EMPTY, Insets.EMPTY)));
        bankerCurrentScore.setPrefSize(50,50);
        bankerCurrentScore.setBackground(new Background(new BackgroundFill(Color.LIGHTCYAN, CornerRadii.EMPTY, Insets.EMPTY)));

        playerLabel.setBackground(new Background(new BackgroundFill(Color.LIGHTCYAN, CornerRadii.EMPTY, Insets.EMPTY)));
        bankerLabel.setBackground(new Background(new BackgroundFill(Color.LIGHTCYAN, CornerRadii.EMPTY, Insets.EMPTY)));

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
        HBox playerAreaFirst = new HBox();
        playerAreaFirst.setAlignment(Pos.CENTER);
        playerAreaFirst.setSpacing(10);
        HBox playerAreaSecond = new HBox();
        playerAreaSecond.setAlignment(Pos.CENTER);
        playerArea.getChildren().addAll(playerAreaFirst, playerAreaSecond);

        // do draw area
        makeDraw = new Button("DRAW");
        makeDraw.setPrefSize(70,50);
        makeDraw.setOnAction(this);
        drawArea.setAlignment(Pos.CENTER);
        drawArea.getChildren().add(makeDraw);



        // divide bankerArea into column into 2 rows
        HBox bankerAreaFirst = new HBox();
        bankerAreaFirst.setAlignment(Pos.CENTER);
        bankerAreaFirst.setSpacing(10);
        HBox bankerAreaSecond = new HBox();
        bankerAreaSecond.setAlignment(Pos.CENTER);
        bankerArea.getChildren().addAll(bankerAreaFirst,  bankerAreaSecond);


            // TODO: delete later hard code
        Card testCard = new Card("test", 7);
        CardVisual testCardVisual = new CardVisual(testCard);
        CardVisual testCardVisual2 = new CardVisual(testCard);
        CardVisual testCardVisual3 = new CardVisual(testCard);
        CardVisual testCardVisual4 = new CardVisual(testCard);
        CardVisual testCardVisual5 = new CardVisual(testCard);
        CardVisual testCardVisual6 = new CardVisual(testCard);
        playerAreaFirst.getChildren().addAll(testCardVisual.getVisual(), testCardVisual2.getVisual());
        playerAreaSecond.getChildren().add(testCardVisual3.getVisual());
        bankerAreaFirst.getChildren().addAll(testCardVisual4.getVisual(), testCardVisual5.getVisual());
        bankerAreaSecond.getChildren().add(testCardVisual6.getVisual());
        playArea.setBackground(new Background(new BackgroundFill(Color.PALEVIOLETRED, null, null)));


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

    @Override
    public void handle(Event event) {
        if (event.getSource() == makeDraw){

        }
        if(event.getSource() == play){

        }
        if(event.getSource() == quit){

        }
        if(event.getSource() == playerWins){
            bankerWins.setDisable(true);
            tie.setDisable(true);


        }
        if(event.getSource() == bankerWins){

        }
        if(event.getSource() == tie){

        }

    }

}
