package Akari.view;

import Akari.controller.Controller;
import Akari.model.Model;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class TitleView {
  private Controller controller;
  private Model model;

  public TitleView(Model model, Controller controller) {
    this.controller = controller;
    this.model = model;
  }

  public Parent render() {
    VBox title_box = new VBox();
    Button title = new Button("AKARI");
    title.getStyleClass().add("title");
    title_box.getChildren().add(title);
    title_box.setAlignment(Pos.TOP_CENTER);
    title_box.setPadding(new Insets(10, 0, 10, 0));

    return title_box;
  }
}
