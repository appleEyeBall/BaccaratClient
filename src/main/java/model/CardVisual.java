package model;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CardVisual {
    VBox visual;

    public CardVisual(Card card) {
        this.visual = new VBox();
        this.visual.setMinHeight(130);
        this.visual.setMinWidth(90);
        CornerRadii corner = new CornerRadii(16);

        HBox topRow = new HBox();
        HBox middleRow = new HBox();
        HBox bottomRow = new HBox();

        topRow.getChildren().add(new Label(String.valueOf(card.getValue())));
        topRow.setAlignment(Pos.TOP_LEFT);
        middleRow.getChildren().add(new Label(card.getSuite()));
        middleRow.setAlignment(Pos.CENTER);
        bottomRow.getChildren().add(new Label(String.valueOf(card.getValue())));
        bottomRow.setAlignment(Pos.BOTTOM_RIGHT);

        visual.getChildren().addAll(topRow, middleRow, bottomRow);
        visual.setSpacing(40);

        this.visual.setBackground(new Background(new BackgroundFill(Color.WHITE, corner, Insets.EMPTY)));
        this.visual.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID, corner, BorderWidths.DEFAULT)));
        this.visual.setPadding(new Insets(10,10,10,10));
    }

    public VBox getVisual(){
        return visual;
    }
}
