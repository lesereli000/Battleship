import java.util.*;

class Main {

  public static void main(String[] args) {

    Board user = new Board();
    Board comp = new Board();
    Scanner k = new Scanner(System.in);
    Opponent opp = new Opponent(user, comp);

    // loop lets user reroll board
    boolean okBoard = false;
    while (!okBoard) {
      user.printBoard();
      String input = "";

      while (!input.equals("N") && !input.equals("n") && !input.equals("Y") && !input.equals("y")) {
        System.out.println("Is this Board OK? (Y/N)");
        input = k.nextLine();

        if (input.equals("N") || input.equals("n")) {
          user = new Board();
        } else {
          okBoard = true;
        }

      }
    }

    // loop for game rounds
    while (getWinner(user, comp) == 0) {
      opp.compSmartGuess();
      boolean badInput = true;

      while (badInput) {
        try {
          // break user input into useful values
          System.out.println("What is your guess?(A1 or J10)");
          String guess = k.nextLine();
          guess = guess.toLowerCase();

          // bound user input to valid indexes
          if (guess.length() > 1 && guess.length() <= 3) {
            if (guess.charAt(0) >= 'a' && guess.charAt(0) <= 'j') {
              if ((int) guess.charAt(1) < 58 && (int) guess.charAt(1) > 48) {
                if ((int) Integer.parseInt(guess.substring(1)) <= 10) {

                  // break user input into useful values
                  char lett = guess.charAt(0);
                  int num = Integer.parseInt(guess.substring(1)) - 1;
                  int userLett = (int) lett - 97;

                  // call methods to run the game
                  if (!user.checkGuess(userLett, num)) {
                    user.guess(comp.checkHit(userLett, num), userLett, num);
                    if(comp.checkSunk(userLett, num)){
                      System.out.println("You sunk a ship!");
                    }
                    user.printBoard();
                    badInput = false;
                    winnerOutput(getWinner(user, comp));
                  }

                }
              }
            }
          }

        } catch (NumberFormatException e) {

        }
      }
    }
    k.close();
  }// main

  // returns winner
  // 0: no winner
  // 1: user wins
  // 2: comp wins
  public static int getWinner(Board user, Board comp) {
    if (comp.checkWin()) {
      return 1;
    } else if (user.checkWin()) {
      return 2;
    } else {
      return 0;
    }
  }// getWinner

  public static void winnerOutput(int winner) {
    if (winner == 1) {
      System.out.println("Congrats! You Win!");
      System.exit(0);
    } else if (winner == 2) {
      System.out.println("The computer won.");
      System.exit(0);
    }
  }// winnerOutput

}
// TODO fix bug causing false hits
// TODO fix bug causing failure to display comp guesses