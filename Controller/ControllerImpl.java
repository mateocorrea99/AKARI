package Akari.controller;

import Akari.model.Model;

import java.util.Random;

public class ControllerImpl implements Controller {
  private Model model;

  public ControllerImpl(Model model) {
    this.model = model;
  }

  @Override
  public void clickNextPuzzle() {
    model.setActivePuzzleIndex(model.getActivePuzzleIndex() + 1);
  }

  @Override
  public void clickPrevPuzzle() {
    model.setActivePuzzleIndex(model.getActivePuzzleIndex() - 1);
  }

  @Override
  public void clickRandPuzzle() {
    Random rand = new Random();
    int rand_num = rand.nextInt(model.getPuzzleLibrarySize());
    while (rand_num == model.getActivePuzzleIndex()) {
      rand_num = rand.nextInt(model.getPuzzleLibrarySize());
    }
    model.setActivePuzzleIndex(rand_num);
  }

  @Override
  public void clickResetPuzzle() {
    model.resetPuzzle();
  }

  @Override
  public void clickCell(int r, int c) {
    model.clickCell(r, c);
  }
}
