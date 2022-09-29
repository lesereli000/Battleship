import java.util.*;

class Main {

  private Board user;
  private Board comp;
  private Scanner k;
  private Opponent opp;

  public void main(String[] args) {

    user = new Board();
    comp = new Board();
    k = new Scanner(System.in);
    opp = new Opponent(user, comp);

    // loop lets user reroll board
    boolean ok = false;
    while (!ok) {
      user.printBoard();

      String input = "";
      while (!input.equals("N") && !input.equals("n") && !input.equals("Y") && !input.equals("y")) {
        System.out.println("Is this Board OK? (Y/N)");
        input = k.nextLine();
        if (input.equals("N") || input.equals("n")) {
          user = new Board();
        } else {
          ok = true;
        }
      }
    }

    // loop for game rounds
    while (getWinner() == 0) {

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
                  char lett = guess.charAt(0);
                  int num = Integer.parseInt(guess.substring(1));

                  if (!user.checkGuess(lett, num)) {
                    user.guess(comp.checkHit(lett, num), lett, num);
                    user.printBoard();
                    badInput = false;
                    winnerOutput(getWinner());
                  }
                }
              }
            }
          }
        } catch (NumberFormatException e) {

        }
      }
    }
  }

  // returns winner
  // 0: no winner
  // 1: user wins
  // 2: comp wins
  public int getWinner() {
    if (comp.checkWin()) {
      return 1;
    } else if (user.checkWin()) {
      return 2;
    } else {
      return 0;
    }
  }

  public void winnerOutput(int winner) {
    if (winner == 1) {
      System.out.println("Congrats! You Win!");
      System.exit(0);
    } else if (winner == 2) {
      System.out.println("The computer won.");
      System.exit(0);
    }
  }
}
// TODO fix comp guess moving 2 away or both directions (prob try / catch issue)
// TODO better comp opponent
// ArrayList of previous hits in chain to fall back on