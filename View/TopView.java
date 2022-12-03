package Akari.view;

import Akari.controller.Controller;
import Akari.model.Model;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class TopView {
  private Controller controller;
  private Model model;

  public TopView(Model model, Controller controller) {
    this.controller = controller;
    this.model = model;
  }

  public Parent render() {

    Button prev = new Button("Previous Puzzle");

    if (model.getActivePuzzleIndex() != 0) {
      prev.setOnAction(
          (ActionEvent event) -> {
            controller.clickPrevPuzzle();
          });
    }

    Button next = new Button("Next Puzzle");
    if (model.getActivePuzzleIndex() < model.getPuzzleLibrarySize() - 1) {
      next.setOnAction(
          (ActionEvent event) -> {
            controller.clickNextPuzzle();
          });
    }

    // Keep on coding
    prev.getStyleClass().add("other");
    next.getStyleClass().add("other");
    next.setMinWidth(150);
    prev.setMinWidth(150);

    HBox pane = new HBox(10);
    pane.setPadding(new Insets(0, 0, 5, 0));
    pane.setAlignment(Pos.CENTER);
    pane.getChildren().addAll(prev, next);
    return pane;
  }
}
