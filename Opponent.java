import java.util.ArrayList;
import java.util.Random;

public class Opponent {

    private Random r;
    private int LHlett, LHnum;
    private ArrayList<Integer[]> prevHits;
    private boolean prevHit;
    private Board comp;
    private Board user;

    public Opponent(Board user, Board comp) {
        r = new Random();
        LHlett = 0;
        LHnum = 0;
        prevHits = new ArrayList<Integer[]>();
        prevHit = false;
        this.comp = comp;
        this.user = user;
    }

    public void compSmartGuess() {
        try {
            char lett = 'a';
            int lettInd, num, LHtries = 0;
            LHlett = (int) LHlett > 97 ? LHlett - 97 : LHlett;

            if (!prevHit) {
                lettInd = r.nextInt(10);
                num = r.nextInt(9) + 1;
            } else if (r.nextInt(2) == 1) {
                lettInd = LHlett;
                num = LHnum - 2 + r.nextInt(3);
                LHtries = 0;
            } else {
                lettInd = LHlett - 3 + r.nextInt(3);
                num = LHnum;
                LHtries = 0;
            }

            for (int i = 0; i < lettInd; i++) {
                lett++;
            }

            LHlett = (int) LHlett > 96 ? LHlett - 96 : LHlett;
            // prevent duplicate guesses
            while (comp.checkGuess(lett, num)) {
                LHlett = (int) LHlett > 96 ? LHlett - 96 : LHlett;
                if (!prevHit) {
                    lettInd = r.nextInt(10);
                    num = r.nextInt(10) + 1;
                } else if (r.nextInt(2) == 1) {
                    lettInd = LHlett;
                    num = LHnum - 1 + r.nextInt(3);
                    LHtries++;
                } else {
                    lettInd = LHlett - 1 + r.nextInt(3);
                    num = LHnum;
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
                for (int i = 0; i < lettInd; i++) {
                    lett++;
                }
            }

            // check if hit or miss then output accordingly
            boolean hit = user.checkHit(lett, num);
            comp.guess(hit, lett, num);
            System.out.println(LHtries);
            System.out.println(prevHit);
            // System.out.println(LHlett);
            // System.out.println(LHnum);
            if (hit) {
                LHlett = (int) lett;
                LHnum = num;
                prevHit = true;
                Integer hitLoc[] = { (int) lett, num };
                prevHits.add(hitLoc);
            }
            if (prevHits.size() != 0) {
                System.out.println(prevHits.get(prevHits.size() - 1)[0] + " " + prevHits.get(prevHits.size() - 1)[1]);
            }
            if (hit) {
                System.out.println("The computer guessed " + lett + num + ". It was a hit!");
            } else {
                System.out.println("The computer guessed " + lett + num + ". It was a miss!");
            }

            LHlett = (int) LHlett > 96 ? LHlett - 96 : LHlett;
        } catch (Exception e) {
            compSmartGuess();
        }
    }

}
