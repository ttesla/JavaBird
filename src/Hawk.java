import java.awt.Graphics2D;

import javax.swing.ImageIcon;

public class Hawk 
{    
    private ImageIcon hawk;
    private ImageIcon left;
    private ImageIcon right;
    private ImageIcon normal;
    
    private int objX, objY,boundX, boundY; 
    private boolean isHawkSet = false;
    private boolean isHit     = false;
    private boolean isDead    = false;
    private int hitDelay   = 0;
    public static int hawkHealth = 1000;
    private Pat patla;
    
    public Hawk()
    {
        left    = new ImageIcon(getClass().getResource("gfx/ships/okleft.gif"));
        right   = new ImageIcon(getClass().getResource("gfx/ships/okright.gif"));
        normal  = new ImageIcon(getClass().getResource("gfx/ships/ok.gif"));
    }
    
    public void setHawk(int x, int y, int z, int k)//Keep in screen bounds
    {
    	patla      = new Pat();
        hawk       = normal;
        hitDelay   = 0; 
        hawkHealth = 1000;
        isHawkSet  = true;
        isHit      = false;
        isDead     = false;
        objX       = x;
        objY       = y;
        boundX     = z;
        boundY     = k;
    }
    
    public void showHawk(Graphics2D g2)
    {
        if(isHawkSet && !isDead)
            g2.drawImage(hawk.getImage(),objX,objY,null);
        
        else if(isDead)
        {
        	patla.birkereSetle(300,30,objX+29,objY+38);
        	patla.patlat(g2);
        }
    }
    
    public void hit(int damage)
    {
        isHit      = true;
        hawkHealth -= damage;
        
        if(hawkHealth <= 0)
        {
            isDead    = true;
            isHawkSet = false;
        }
    }
    
    public int[] getCoordinates()
    {
        int cor[] = {objX,objX+58,objY,objY+77};
        return cor;
    }
    
   public void moveLeft()
   {  
       hawk = left;
       if(objX>2)
       objX-=5;
   } 
   
   public void moveRight()
   {
       hawk = right;
       if(objX<boundX-60)
       objX+=5;
   }
   
   public void moveUp()
   {
       hawk = normal;
       if(objY>5)
       objY-=5;
   }
   
   public void moveDown()
   { 
       hawk = normal;
       if(objY<boundY-77)
       objY+=5;
   }

   public void standStill() 
   {
       hawk  = normal;
   }
   
   public boolean isSet()//TO PREVENT WRONG COLLUSION DETECTOINS
   {
       return isHawkSet;
   }
   
   public int getHealth()
   {
       return hawkHealth;
   }
}
