public class Board {

  private int[][] Board = new int[10][10];
  private int[][] Guess = new int[10][10];
  private int[][] Ships = new int[10][10];
  private Ship[] Fleet = new Ship[5];

  private static final String ANSI_RED = "\u001B[31m";
  private static final String ANSI_RESET = "\u001B[0m";
  private static final String ANSI_GREEN = "\u001B[32m";

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
  public boolean checkHit(int lett, int num) {
    Board[lett][num] += 2;
    Ships[lett][num] += 2;
    return Board[lett][num] == 3;
  }// checkHit

  // change guess array to reflect guesses
  public void guess(boolean hit, int lett, int num) {
    if (hit) {
      Guess[lett][num] = 1;
    } else {
      Guess[lett][num] = 2;
    }
    
  }// guess

  // check for duplicate guesses
  public boolean checkGuess(int lett, int num) {
    return !(Guess[lett][num] == 0);
  }// checkGuess

  // check if a ship has been sunk
  public boolean checkSunk(int lett, int num) {
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
        for (int i = 0; i <= Fleet[k].getSizeX() - 1; i++) {
          for (int j = 0; j <= Fleet[k].getSizeY() - 1; j++) {
            Ships[Fleet[k].getShipX() + i][Fleet[k].getShipY() + j] = 4;
          }
        }
        return true;
      }

    }
    return false;
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
            System.out.print(ANSI_GREEN + "O " + ANSI_RESET);
            break;

          case 3:
            System.out.print(ANSI_RED + "X " + ANSI_RESET);
            break;

          default:
            System.out.println("something broke");
            break;
        }// end switch

      }
      System.out.print("     " + row + " ");

      for (int j = 0; j < 10; j++) {
        System.out.print(Guess[i][j] == 1 ? (ANSI_GREEN + "X " + ANSI_RESET): Guess[i][j] == 2 ? (ANSI_RED + "O " + ANSI_RESET): ". ");
      }

      row++;
      System.out.println();
    }

    System.out.println();
  }// printBoard

  public int getBoardAt(int guessLett, int guessNum){
    return Board[guessLett][guessNum];
  }

}// Board