package Akari.view;

import Akari.controller.Controller;
import Akari.model.Model;
import Akari.model.Puzzle;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;

public class PuzzleView {
  private Controller controller;
  private Model model;

  public PuzzleView(Model model, Controller controller) {
    this.controller = controller;
    this.model = model;
  }

  public Parent render() {
    GridPane pane = new GridPane();
    Puzzle puzzle = model.getActivePuzzle();

    for (int i = 0; i < puzzle.getHeight(); i++) {
      for (int j = 0; j < puzzle.getWidth(); j++) {
        TileView button = new TileView(i, j, puzzle, model);
        pane.add(button.render(), j, i);
      }
    }
    pane.setAlignment(Pos.CENTER);
    pane.setPadding(new Insets(0, 10, 0, 10));

    return pane;
  }
}
