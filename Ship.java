import java.util.*;

public class Ship {

  private int shipX, shipY, sizeX, sizeY, length;
  private boolean sunk = false;

  public Ship(int length) {
    Random r = new Random();
    this.length = length;

    // randomly determine orientation and position
    if (r.nextInt(2) == 1) {
      shipX = r.nextInt(10 - length - 2);
      sizeX = length;
      shipY = r.nextInt(9);
      sizeY = 1;

    } else {
      shipX = r.nextInt(9);
      sizeX = 1;
      shipY = r.nextInt(10 - length - 2);
      sizeY = length;

    }
  }// Ship

  // mark a ship as sunk
  public void sink() {
    sunk = true;
  }// sink

  // accessor methods for various vairables
  public int getShipX() {
    return shipX;
  }// getShipX

  public int getShipY() {
    return shipY;
  }// getShipY

  public int getSizeX() {
    return sizeX;
  }// getSizeX

  public int getSizeY() {
    return sizeY;
  }//getSizeY

  public int length() {
    return length;
  }//length

  public boolean getSunk() {
    return sunk;
  }// getSunk
}// Ship