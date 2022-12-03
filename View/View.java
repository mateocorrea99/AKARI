package Akari.view;

import Akari.controller.Controller;
import Akari.model.Model;
import Akari.model.ModelObserver;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class View implements ModelObserver, FXComponent {
  private Controller controller;
  private Model model;
  private Stage stage;

  public View(Model model, Controller controller, Stage stage) {
    this.controller = controller;
    this.model = model;
    this.stage = stage;
  }

  @Override
  public Parent render() {
    VBox pane = new VBox();

    TitleView titleView = new TitleView(model, controller);
    TopView topView = new TopView(model, controller);
    PuzzleView puzzleView = new PuzzleView(model, controller);
    BottomView bottomView = new BottomView(model, controller, this);

    pane.getChildren().add(titleView.render());
    pane.getChildren().add(topView.render());
    pane.getChildren().add(puzzleView.render());
    pane.getChildren().add(bottomView.render());

    return pane;
  }

  @Override
  public void update(Model model) {
    Scene scene;
    if (model.isSolved()) {
      this.isSolved();
    } else {
      scene = new Scene(render());
      scene.getStylesheets().add("main.css");
      stage.setScene(scene);
      stage.sizeToScene();
    }
  }

  public void isSolved() {
    StackPane pane = new StackPane(render());

    Button close;
    if (model.isSolved()) {
      close = new Button("You solved this puzzle!\nPress this button for a new random puzzle.");
      close.setOnAction(
          (ActionEvent event) -> {
            controller.clickRandPuzzle();
          });
      close.getStyleClass().add("isSolvedWin");
    } else {
      close = new Button("Sorry, there is some mistake!\nPress this button to go back.");
      close.setOnAction(
          (ActionEvent event) -> {
            this.update(model);
          });
      close.getStyleClass().add("isSolvedLose");
    }

    close.setTextAlignment(TextAlignment.CENTER);

    pane.getChildren().add(close);
    Scene scene = new Scene(pane);
    scene.getStylesheets().add("main.css");
    stage.setScene(scene);
    stage.sizeToScene();
  }
}
