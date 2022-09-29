import java.util.*;

public class Ship{
  
  private int shipX, shipY, sizeX, sizeY, length;
  private boolean sunk = false;

  public Ship(int length){
    Random r = new Random();
    this.length = length;
    
    //randomly determine orientation and position
    if(r.nextInt(2) == 1){
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
  }
  
  public void sink(){
    sunk = true;
  }
  
  //accessor methods for various vairables
  public int getShipX(){
    return shipX;
  }
  
  public int getShipY(){
    return shipY;
  }
  
  public int getSizeX(){
    return sizeX;
  }
  
  public int getSizeY(){
    return sizeY;
  }
  
  public int length(){
    return length;
  }
  
  public boolean getSunk(){
    return sunk;
  }
}