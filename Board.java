public class Board {

  private int[][] Board = new int[10][10];
  private int[][] Guess = new int[10][10];
  private int[][] Ships = new int[10][10];
  private Ship[] Fleet = new Ship[5];

  public Board() {

    // default 2-D array to all false
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        Board[i][j] = 0;
        Guess[i][j] = 0;
        Ships[i][j] = 0;
      }
    }

    // roll all ships
    Fleet[0] = rollShip(new Ship(2));
    Fleet[1] = rollShip(new Ship(3));
    Fleet[2] = rollShip(new Ship(3));
    Fleet[3] = rollShip(new Ship(4));
    Fleet[4] = rollShip(new Ship(5));
  }// Board

  // reroll ships on board
  private Ship rollShip(Ship ship) {
    try {
      int shared = 0;
      boolean overlap = true;

      while (overlap) {
        shared = 0;

        for (int i = 0; i <= ship.getSizeX() - 1; i++) {
          for (int j = 0; j <= ship.getSizeY() - 1; j++) {

            if (Board[ship.getShipX() + i][ship.getShipY() + j] == 1) {
              shared++;
              ship = new Ship(ship.length());
            }

          }
        }

        overlap = shared != 0;
      }

      for (int i = 0; i <= ship.getSizeX() - 1; i++) {
        for (int j = 0; j <= ship.getSizeY() - 1; j++) {
          Board[ship.getShipX() + i][ship.getShipY() + j] = 1;
          Ships[ship.getShipX() + i][ship.getShipY() + j] = 1;
        }
      }

    } catch (Exception IndexOutOfBoundsException) {
      rollShip(ship);
    }

    return ship;
  }// rollShip

  // check if a guess is a hit
  public boolean checkHit(char lett, int num) {
    Board[(int) lett - 97][num - 1] = Board[(int) lett - 97][num - 1] + 2;
    Ships[(int) lett - 97][num - 1] = Ships[(int) lett - 97][num - 1] + 2;
    return Board[(int) lett - 97][num - 1] == 3;
  }// checkHit

  // change guess array to reflect guesses
  public void guess(boolean hit, char lett, int num) {
    if (hit) {
      Guess[(int) lett - 97][num - 1] = 1;
    } else {
      Guess[(int) lett - 97][num - 1] = 2;
    }
    checkSunk((int) lett, num);
  }// guess

  // check for duplicate guesses
  public boolean checkGuess(char lett, int num) {
    return Guess[(int) lett - 97][num - 1] != 0;
  }// checkGuess

  // check if a ship has been sunk
  public void checkSunk(int lett, int num) {
    int hit = 0;

    for (int k = 0; k < Fleet.length; k++) {
      hit = 0;

      for (int i = 0; i <= Fleet[k].getSizeX() - 1; i++) {
        for (int j = 0; j <= Fleet[k].getSizeY() - 1; j++) {

          if (Ships[Fleet[k].getShipX() + i][Fleet[k].getShipY() + j] == 3) {
            hit++;
          }

        }
      }

      if (hit == Fleet[k].length()) {
        System.out.println("You sunk a ship");

        for (int i = 0; i <= Fleet[k].getSizeX() - 1; i++) {
          for (int j = 0; j <= Fleet[k].getSizeY() - 1; j++) {
            Ships[Fleet[k].getShipX() + i][Fleet[k].getShipY() + j] = 4;
          }
        }

      }

    }

  }// checkSunk

  // check to see if this player is out of unsunk ships
  public boolean checkWin() {
    int count = 0;

    for (int i = 0; i < Board.length; i++) {
      for (int j = 0; j < Board[0].length; j++) {

        if (Board[i][j] == 1) {
          count++;
        }

      }
    }
    
    return count == 0;
  }// checkWin

  // print Board for user using symbols
  public void printBoard() {
    char row = 'A';
    System.out.println();
    System.out.println("      Your Board                 Your Guesses    ");
    System.out.println(". 1 2 3 4 5 6 7 8 9 10     . 1 2 3 4 5 6 7 8 9 10");

    for (int i = 0; i < 10; i++) {
      System.out.print(row + " ");

      for (int j = 0; j < 10; j++) {
        int boardPos = Board[i][j];

        switch (boardPos) {
          case 0:
            System.out.print(". ");
            break;

          case 1:
            System.out.print("+ ");
            break;

          case 2:
            System.out.print("O ");
            break;

          case 3:
            System.out.print("X ");
            break;

          default:
            System.out.println("something broke");
            break;
        }// end switch

      }
      System.out.print("     " + row + " ");

      for (int j = 0; j < 10; j++) {
        System.out.print(Guess[i][j] == 1 ? "X " : Guess[i][j] == 2 ? "O " : ". ");
      }

      row++;
      System.out.println();
    }

    System.out.println();
  }// printBoard
}// Board