package Akari.view;

import Akari.SamplePuzzles;
import Akari.controller.Controller;
import Akari.controller.ControllerImpl;
import Akari.model.*;
import com.comp301.a09akari.model.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppLauncher extends Application {
  @Override
  public void start(Stage stage) {
    // TODO: Create your Model, View, and Controller instances and launch your GUI
    PuzzleLibrary puzzleLibrary = new PuzzleLibraryImpl();
    Puzzle puzzle1 = new PuzzleImpl(SamplePuzzles.PUZZLE_01);
    puzzleLibrary.addPuzzle(puzzle1);
    Puzzle puzzle2 = new PuzzleImpl(SamplePuzzles.PUZZLE_02);
    puzzleLibrary.addPuzzle(puzzle2);
    Puzzle puzzle3 = new PuzzleImpl(SamplePuzzles.PUZZLE_03);
    puzzleLibrary.addPuzzle(puzzle3);
    Puzzle puzzle4 = new PuzzleImpl(SamplePuzzles.PUZZLE_04);
    puzzleLibrary.addPuzzle(puzzle4);
    Puzzle puzzle5 = new PuzzleImpl(SamplePuzzles.PUZZLE_05);
    puzzleLibrary.addPuzzle(puzzle5);

    Model model = new ModelImpl(puzzleLibrary);
    Controller controller = new ControllerImpl(model);
    View view = new View(model, controller, stage);
    model.addObserver(view);

    Scene scene = new Scene(view.render());
    scene.getStylesheets().add("main.css");
    stage.setScene(scene);
    stage.show();
  }
}
