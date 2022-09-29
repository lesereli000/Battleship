import java.util.*;

class Main {
  
  private static Board user = new Board();
  private static Board comp = new Board();
  private static Scanner k = new Scanner(System.in);
  private static Random r = new Random();
  
  private static boolean prevHit = false;
  private static int LHlett = 0, LHnum = 0;
  
  private static ArrayList<Integer[]> prevHits = new ArrayList<Integer[]>();
    
  public static void main(String[] args) {
    
    //loop lets user reroll board
    boolean ok = false;
    while(!ok){
      user.printBoard();
      
      String input = "";
      while(!input.equals("N") && !input.equals("n") && !input.equals("Y") && !input.equals("y")){
        System.out.println("Is this Board OK? (Y/N)");
        input = k.nextLine();
        if(input.equals("N") || input.equals("n")){
          user = new Board();
        } else {
          ok = true;
        }
      }
    }

    //loop for game rounds
    while(getWinner() == 0){
      
      compSmartGuess();
      
      boolean badInput = true;
      while(badInput){
        try{
          //break user input into useful values
          System.out.println("What is your guess?(A1 or J10)");
          String guess = k.nextLine();
          guess = guess.toLowerCase();
          
          //bound user input to valid indexes
          if(guess.length() > 1 && guess.length() <= 3){
            if(guess.charAt(0) >= 'a' && guess.charAt(0) <= 'j'){
              if((int)guess.charAt(1) < 58 && (int)guess.charAt(1) > 48){
                if((int)Integer.parseInt(guess.substring(1)) <= 10){
                  char lett = guess.charAt(0);
                  int num = Integer.parseInt(guess.substring(1));
                  
                  if(!user.checkGuess(lett, num)){
                    user.guess(comp.checkHit(lett, num), lett, num);
                    user.printBoard();
                    badInput = false;
                    winner(getWinner());
                  } 
                }
              }
            }
          }
        }catch(NumberFormatException e){
                    
        }
      }
    }
  }
  
  //method for computer to guess somewhat intelligently
  public static void compSmartGuess(){
    try {
    char lett = 'a';
    int lettInd, num, LHtries = 0;
    LHlett = (int)LHlett > 97 ? LHlett - 97 : LHlett;
    
    if(!prevHit){
      lettInd = r.nextInt(10);
      num = r.nextInt(9) + 1;
    } else if (r.nextInt(2) == 1){
      lettInd = LHlett;
      num = LHnum - 2 + r.nextInt(3);
      LHtries = 0;
    } else {
      lettInd = LHlett - 3 + r.nextInt(3);
      num = LHnum;
      LHtries = 0;
    }
    
    for(int i = 0; i < lettInd; i++){
      lett++;
    }
    
    LHlett = (int)LHlett > 96 ? LHlett - 96 : LHlett;
    //prevent duplicate guesses
    while(comp.checkGuess(lett, num)){
      LHlett = (int)LHlett > 96 ? LHlett - 96 : LHlett;
      if(!prevHit){
        lettInd = r.nextInt(10);
        num = r.nextInt(10) + 1;
      } else if (r.nextInt(2) == 1){
        lettInd = LHlett;
        num = LHnum - 1 + r.nextInt(3);
        LHtries++;
      } else {
        lettInd = LHlett - 1 + r.nextInt(3);
        num = LHnum;
        LHtries++;
      }
      
      if(LHtries > 20){
        if(prevHits.size() == 1){
          prevHit = false;
          LHtries = 0;
          prevHits.clear();
        } else {
          prevHits.remove(prevHits.size() - 1);
          LHtries = 0;
        }
      }
      
      lett = 'a';
      for(int i = 0; i < lettInd; i++){
        lett++;
      }
    }
    
    //check if hit or miss then output accordingly
    boolean hit = user.checkHit(lett, num);
    comp.guess(hit, lett, num);
    System.out.println(LHtries);
    System.out.println(prevHit);
    // System.out.println(LHlett);
    // System.out.println(LHnum);
    if(hit){
      LHlett = (int)lett;
      LHnum = num;
      prevHit = true;
      Integer hitLoc[] = {(int)lett, num};
      prevHits.add(hitLoc);
    }
    if(prevHits.size() != 0){
    System.out.println(prevHits.get(prevHits.size() - 1)[0] + " " + prevHits.get(prevHits.size() - 1)[1]);
    }
    if(hit){
      System.out.println("The computer guessed " + lett + num + ". It was a hit!");
    } else {
      System.out.println("The computer guessed " + lett + num + ". It was a miss!");
    }
    winner(getWinner());
    
    LHlett = (int)LHlett > 96 ? LHlett - 96 : LHlett;
    } catch (Exception e){
      compSmartGuess();
    }
  }
  
  //returns winner
  //0: no winner
  //1: user wins
  //2: comp wins
  public static int getWinner(){
    if (comp.checkWin()){
      return 1;
    } else if (user.checkWin()){
      return 2;
    } else {
      return 0;
    }
  }
  
  public static void winner(int winner){
    if (winner == 1){
      System.out.println("Congrats! You Win!");
      System.exit(0);
    } else if (winner == 2){
      System.out.println("The computer won.");
      System.exit(0);
    }
  }
}
//TODO fix comp guess moving 2 away or both directions (prob try / catch issue)
//TODO better comp opponent
//       ArrayList of previous hits in chain to fall back on