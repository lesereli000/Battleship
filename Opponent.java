import java.util.ArrayList;
import java.util.Random;

public class Opponent {

    private Random r;
    private int LHlett, LHnum, LHtries, guessLett, guessNum;
    private ArrayList<Integer[]> prevHits;
    private boolean prevHit, hit;
    private char lett;
    private Board comp;
    private Board user;

    public Opponent(Board user, Board comp) {
        r = new Random();
        LHlett = 0;
        LHnum = 0;
        guessLett = 0;
        guessNum = 0;
        prevHits = new ArrayList<Integer[]>();
        prevHit = false;
        this.comp = comp;
        this.user = user;
    }

    public void compSmartGuess() {
        try {
            lett = 'a';
            LHtries = 0;
            
            chooseGuess();
            
            checkDuplicate();

            handleGuess();
            
            guessOutput();

        } catch (Exception e) {
            compSmartGuess();
        }
    }

    // determine guesses dased on either a random number, or on previous hits
    private void chooseGuess(){
        LHlett = (int) LHlett > 97 ? LHlett - 97 : LHlett;

            if (!prevHit) {
                guessLett = r.nextInt(10);
                guessNum = r.nextInt(9) + 1;
            } else if (r.nextInt(2) == 1) {
                guessLett = LHlett;
                guessNum = LHnum - 2 + r.nextInt(3);
                LHtries = 0;
            } else {
                guessLett = LHlett - 3 + r.nextInt(3);
                guessNum = LHnum;
                LHtries = 0;
            }

            for (int i = 1; i < guessLett; i++) {
                lett++;
            }

            LHlett = (int) LHlett > 97 ? LHlett - 97 : LHlett;
    }

    // prevent duplicate guesses
    private void checkDuplicate(){
        while (comp.checkGuess(lett, guessNum)) {
            LHlett = (int) LHlett > 96 ? LHlett - 96 : LHlett;
            if (!prevHit) {
                guessLett = r.nextInt(10);
                guessNum = r.nextInt(10) + 1;
            } else if (r.nextInt(2) == 1) {
                guessLett = LHlett;
                guessNum = LHnum - 1 + r.nextInt(3);
                LHtries++;
            } else {
                guessLett = LHlett - 1 + r.nextInt(3);
                guessNum = LHnum;
                LHtries++;
            }

            if (LHtries > 20) {
                if (prevHits.size() == 1) {
                    prevHit = false;
                    LHtries = 0;
                    prevHits.clear();
                } else {
                    prevHits.remove(prevHits.size() - 1);
                    LHtries = 0;
                }
            }

            lett = 'a';
            for (int i = 0; i < guessLett; i++) {
                lett++;
            }
        }
    }

    // check if hit or miss
    private void handleGuess(){
        hit = user.checkHit(lett, guessNum);
        comp.guess(hit, lett, guessNum);

        if (hit) {
            LHlett = (int) lett;
            LHnum = guessNum;
            prevHit = true;
            Integer hitLoc[] = { (int) lett, guessNum };
            prevHits.add(hitLoc);
        }
    }

    // output according to guess
    private void guessOutput(){
        if (prevHits.size() != 0) {
            System.out.println(prevHits.get(prevHits.size() - 1)[0] + " " + prevHits.get(prevHits.size() - 1)[1]);
        }
        if (hit) {
            System.out.println("The computer guessed " + lett + guessNum + ". It was a hit!");
        } else {
            System.out.println("The computer guessed " + lett + guessNum + ". It was a miss!");
        }
    }

}
