package Akari.view;

import Akari.model.CellType;
import Akari.model.Model;
import Akari.model.Puzzle;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TileView {
  private int r;
  private int c;
  private CellType cellType;
  private Model model;

  public TileView(int r, int c, Puzzle puzzle, Model model) {
    this.r = r;
    this.c = c;
    this.cellType = puzzle.getCellType(r, c);
    this.model = model;
  }

  public Parent render() {
    Button button;
    if (this.cellType == CellType.CORRIDOR) {
      if (model.isLamp(r, c)) {
        Image image = new Image("light-bulb.png", 30, 30, true, true);
        button = new Button();
        button.setPrefSize(50, 50);
        button.setMaxSize(50, 50);

        ImageView imgView = new ImageView();
        imgView.setImage(image);
        imgView.setFitWidth(button.getWidth());
        imgView.setFitHeight(button.getHeight());
        button.setGraphic(imgView);

        button.getStyleClass().add("lamp");
      } else if (model.isLit(r, c)) {
        button = new Button();
        button.getStyleClass().add("lit");
        button.setMinSize(50, 50);
        button.setMaxSize(50, 50);
      } else {
        button = new Button();
        button.getStyleClass().add("corridor");
        button.setMinSize(50, 50);
        button.setMaxSize(50, 50);
      }

      button.setOnAction(
          (ActionEvent event) -> {
            model.clickCell(r, c);
            model.update();
          });

    } else if (this.cellType == CellType.CLUE) {
      button = new Button(String.valueOf(model.getActivePuzzle().getClue(r, c)));
      // STYLING FOR CLUE
      button.getStyleClass().add("clue");
      button.setMinSize(50, 50);
      button.setMaxSize(50, 50);
    } else {
      // WALL
      button = new Button();
      // STYLING FOR WALL
      button.getStyleClass().add("wall");
      button.setMinSize(50, 50);
      button.setMaxSize(50, 50);
    }

    return button;
  }
}
