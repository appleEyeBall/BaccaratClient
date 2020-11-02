package model;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CardVisual {
    VBox visual;
    String faceCard;

    // if a card is a face card then set its name and return true
    public Boolean isFaceCard(Card card){

        if(card.getValue() == 11){
            faceCard = "QUEEN";
            return true;
        }
        if(card.getValue() == 12){
            faceCard = "KING";
            return true;
        }
        if(card.getValue() == 13){
            faceCard = "JACK";
            return true;
        }
        if(card.getValue() == 1){
            faceCard = "ACE";
            return  true;
        }
        return false;
    }

    // every card is a Vbox with its name and suite displayed  by using labels
    public CardVisual(Card card) {
        this.visual = new VBox();
        this.visual.setMinHeight(150);
        this.visual.setMinWidth(100);
        CornerRadii corner = new CornerRadii(16);

        HBox topRow = new HBox();
        HBox middleRow = new HBox();
        HBox bottomRow = new HBox();
        Label topLabel = new Label();
        Label bottomLabel = new Label();

        if(isFaceCard(card)){   // if face card, change the label
            topLabel.setText(faceCard);
            bottomLabel.setText(faceCard);
        }
        else{
            topLabel.setText(String.valueOf(card.getValue()));
            bottomLabel.setText(String.valueOf(card.getValue()));
        }
        topRow.getChildren().add(topLabel);  // contains the value/ face name
        topRow.setAlignment(Pos.TOP_RIGHT);
        middleRow.getChildren().add(new Label(card.getSuite()));  // contains the suite name
        middleRow.setAlignment(Pos.CENTER);
        bottomRow.getChildren().add(bottomLabel);
        bottomRow.setAlignment(Pos.BOTTOM_LEFT);

        visual.getChildren().addAll(topRow, middleRow, bottomRow);  // add the suite and value to the Vbox
        visual.setSpacing(40);

        this.visual.setBackground(new Background(new BackgroundFill(Color.WHITE, corner, Insets.EMPTY)));
        this.visual.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID, corner, BorderWidths.DEFAULT)));
        this.visual.setPadding(new Insets(10,10,10,10));
    }

    // return the created card visual
    public VBox getVisual(){
        return visual;
    }
}
