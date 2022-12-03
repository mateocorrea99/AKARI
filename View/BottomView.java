package Akari.view;

import Akari.controller.Controller;
import Akari.model.Model;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class BottomView {
  private Controller controller;
  private Model model;
  private View view;

  public BottomView(Model model, Controller controller, View view) {
    this.controller = controller;
    this.model = model;
    this.view = view;
  }

  public Parent render() {
    String str =
        "Puzzle: "
            + Integer.toString(model.getActivePuzzleIndex() + 1)
            + "/"
            + Integer.toString(model.getPuzzleLibrarySize());
    Button label = new Button(str);

    Button done = new Button("Done!");
    done.setOnAction(
        (ActionEvent event) -> {
          view.isSolved();
        });

    done.getStyleClass().add("other");
    label.getStyleClass().add("other");

    HBox hbox = new HBox(10);
    Button reset = new Button("Reset Puzzle");
    reset.setOnAction(
        (ActionEvent event) -> {
          controller.clickResetPuzzle();
        });

    Button shuffle = new Button("New Puzzle");
    shuffle.setOnAction(
        (ActionEvent event) -> {
          controller.clickRandPuzzle();
        });

    // Keep on coding
    reset.getStyleClass().add("other");
    shuffle.getStyleClass().add("other");
    hbox.getChildren().addAll(shuffle, reset, done, label);
    hbox.setPrefHeight(20);
    hbox.setAlignment(Pos.BOTTOM_CENTER);
    hbox.setPadding(new Insets(10, 5, 10, 5));

    return hbox;
  }
}
