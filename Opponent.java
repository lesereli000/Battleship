import java.util.ArrayList;
import java.util.Random;

public class Opponent {

    private Random r;
    private int LHlett, LHnum, LHtries, guessLett, guessNum;
    private ArrayList<Integer[]> prevHits;
    private boolean prevHit, hit;
    private Board comp;
    private Board user;

    public Opponent(Board user, Board comp) {
        r = new Random();
        LHlett = 0;
        LHnum = 0;
        guessLett = r.nextInt(10);
        guessNum = r.nextInt(10);
        prevHits = new ArrayList<Integer[]>();
        prevHit = false;
        this.comp = comp;
        this.user = user;
    } // Opponent

    // handle computer guesses
    public void compSmartGuess() {
        try {
            LHtries = 0;
            
            checkDuplicate();

            handleGuess();
            
            guessOutput();

        } catch (Exception e) {
            compSmartGuess();
        }
    } // compSmartGuess

    // determine guesses dased on either a random number, or on previous hits
    private void chooseGuess(){
        LHlett = LHlett > 97 ? LHlett - 97 : LHlett;

        if (!prevHit) {
            guessLett = r.nextInt(10);
            guessNum = r.nextInt(10);
        } else if (r.nextInt(2) == 1) {
            guessLett = LHlett;
            guessNum = LHnum - 1 + r.nextInt(3);
        } else {
            guessLett = LHlett - 1 + r.nextInt(3);
            guessNum = LHnum;
        }

        if(guessLett < 0 || guessNum < 0){
            chooseGuess();
        }
        if(guessLett > 9 || guessNum > 9){
            chooseGuess();
        }

    }// chooseGuess

    // prevent duplicate guesses
    private void checkDuplicate(){
        while (comp.checkGuess(guessLett, guessNum)) {
            LHlett = LHlett > 97 ? LHlett - 97 : LHlett;
            chooseGuess();
            LHtries++;

            if (LHtries > 20) { // 20 is an arbitrary value for the number of times the code will try to guess before saying 'oh I must have gotten all the squares around me'
                if (prevHits.size() == 1) {
                    prevHits.clear();
                    prevHit = false;
                    chooseGuess();
                    break;
                } else {
                    prevHits.remove(prevHits.size() - 1);
                    LHlett = prevHits.get(prevHits.size() - 1)[0];
                    LHnum = prevHits.get(prevHits.size() - 1)[1];
                    LHtries = 0;
                }
            }
        }
    } // checkDuplicate

    // check if hit or miss
    private void handleGuess(){
        hit = user.checkHit(guessLett, guessNum);
        comp.guess(hit, guessLett, guessNum);

        if (hit) {
            LHlett = guessLett;
            LHnum = guessNum;
            prevHit = true;
            Integer[] hitLoc = {guessLett, guessNum};
            prevHits.add(hitLoc);
        }
    } // handleGuess

    // output according to guess
    private void guessOutput(){
        char lett = 'a';
        for(int i = 0; i < guessLett; i++){
            lett++;
        }

        if (hit) {
            System.out.println("The computer guessed " + lett + (guessNum + 1) + ". It was a hit!");
        } else {
            System.out.println("The computer guessed " + lett + (guessNum + 1) + ". It was a miss!");
        }
    } // guessOutput

}// Opponent
