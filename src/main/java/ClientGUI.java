import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.IOException;

public class ClientGUI implements EventHandler {

    VBox gameScene;
   HBox scoreRow;
   HBox playArea;
   Button makeDraw;

    public ClientGUI(VBox gameScene) throws IOException {

        this.gameScene = gameScene;
        gameScene.setPadding(new Insets(10,10,10,10));

        scoreRow =  new HBox();
        createScoreRow();

        playArea = new HBox();
        createPlayArea();

        gameScene.getChildren().addAll(scoreRow,playArea);


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

        scoreRow.setSpacing(600/16);  // might make a UTIL.java later dont worry
        scoreRow.getChildren().addAll(playerCurrentScore,playerLabel, baccarat,bankerLabel, bankerCurrentScore);

    }
    public void createPlayArea(){

        Rectangle playerCard_1 = new Rectangle();
        playerCard_1.setHeight(200);
        playerCard_1.setWidth(100);
        playerCard_1.setFill(Color.WHITE);

        Label cardNum_1 = new Label();
        cardNum_1.setText("1");
        cardNum_1.setTextFill(Color.INDIANRED);
        StackPane card_1 = new StackPane();
        card_1.getChildren().addAll(playerCard_1,cardNum_1);
        cardNum_1.setAlignment(Pos.TOP_RIGHT);

        Rectangle playerCard_2 = new Rectangle();

        Label cardNum_2 = new Label();
        cardNum_2.setText("ACE");
        cardNum_2.setTextFill(Color.INDIANRED);
        StackPane card_2 = new StackPane();
        card_1.getChildren().addAll(playerCard_2,cardNum_2);
        cardNum_2.setAlignment(Pos.TOP_RIGHT);

        makeDraw = new Button("DRAW");
        makeDraw.setPrefSize(70,50);
        makeDraw.setAlignment(Pos.CENTER);
        makeDraw.setOnAction(this);


        playArea.getChildren().addAll(card_1,card_2, makeDraw);
        playArea.setSpacing(20);
        playArea.setPadding(new Insets(10,10,0,0));


    }
    public void createBankerSide(){


    }

    @Override
    public void handle(Event event) {
        if (event.getSource() == makeDraw){

        }

    }

}
