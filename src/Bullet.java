import java.awt.Graphics2D;

import javax.swing.ImageIcon;

public class Bullet 
{
    private ImageIcon thefire, gunFire;
    private ImageIcon explode		= new ImageIcon(getClass().getResource("/gfx/other/bulletExplode.gif"));
    
    private int objX =0,objY=0;
    private boolean isBulletSet=false, isHit=false, shot = false;
    private boolean isMachine       = false,
                    isHeavyMachine  = false,
                    isEnFire        = false,
                    isElectric      = false;
	
    private int bulWidth, bulHeight;
	
    private int border       = 0;//Where bullet will go max
    private int bulletSpeed  = 0; //speed of the bullet
    
    private int hitDelay = 0;
    private int electricDelay = 0;
    private int hitPlace = 0;
    private int damage = 0;
    private int imageParseX , imageParseY;
    private int hitDivider=2;
    
    public void setCommon(int x, int y)//Set Common Values
    {
        isBulletSet    = true;
        isHit          = false;
        hitDelay       = 0;
        hitPlace       = (int)((Math.random()*1000))%60+10;
        objX = x;
        objY = y;   
    }
    
    public void setMachineGunFire(int x, int y)
    {
        thefire        = new ImageIcon(getClass().getResource("gfx/other/gun.gif"));
        gunFire        = new ImageIcon(getClass().getResource("gfx/other/smallgunfire.gif"));
        isMachine      = true;
        isElectric     = false;
        shot           = true;
        bulletSpeed    = -15;
        imageParseX    = -8;
        imageParseY	   = -15;
        damage         = 25;
        bulWidth       = 3;
        bulHeight      = 16;
        setCommon(x+27,y-8);
    }
    
    public void setHeavyMachineGunFire(int x, int y)
    {
        thefire        = new ImageIcon(getClass().getResource("gfx/other/heavygun.gif"));
        gunFire        = new ImageIcon(getClass().getResource("gfx/other/biggunfire.gif"));
        imageParseX    = -8;
        imageParseY	   = -29;
        isHeavyMachine = true;
        shot           = true;
        isElectric     = false;
        bulletSpeed    = -15;
        damage         = 50;
        bulWidth       = 13;
        bulHeight      = 15;
        setCommon(x+22,y-10);
    }
    
    public void setElectricGun(int x, int y)
    {
       isElectric = true;
       thefire    = new ImageIcon(getClass().getResource("gfx/other/elect.gif"));
       electricDelay = 0;
       shot  	  = true;
       damage 	  = 40;
       bulWidth   = 150;
       bulHeight  = 700;
       setCommon(x, y-680);
    }
    
    public void setWhiteEnemyFire(int x, int y)
    {
        thefire        = new ImageIcon(getClass().getResource("gfx/other/whiteBeam.gif"));
        isEnFire       = true;
        hitDivider     = -3;
        bulletSpeed    = 13;
        damage         = 30;
        bulWidth       = 5;
        bulHeight      = 20;
        setCommon(x,y);
    }
    
    public void setRedEnemyFire(int x, int y)
    {
        thefire        = new ImageIcon(getClass().getResource("gfx/other/redBeam.gif"));
        isEnFire       = true;
        hitDivider     = -3;
        bulletSpeed    = 10;
        damage         = 50;
        bulWidth       = 7;
        bulHeight      = 28;
        setCommon(x,y);
    }
    
    public void setBoss1Fire(int x, int y)
    {
        thefire        = new ImageIcon(getClass().getResource("gfx/other/bossFire1.gif"));
        isEnFire       = true;
        hitDivider     = -3;
        bulletSpeed    = 10;
        damage         = 70;
        bulWidth       = 12;
        bulHeight      = 23;
        setCommon(x,y);
    }

    public void fire(Graphics2D g2)
    {
        if(isElectric && isBulletSet)
        {
            g2.drawImage(thefire.getImage(),objX,objY,null);
            
            if(electricDelay >= 15)//Delays 10 cycles
                isBulletSet = false;
            
            electricDelay++;
        }
        else if(isBulletSet && !isHit)
        {
            if(shot)
                g2.drawImage(gunFire.getImage(),objX+imageParseX, objY+imageParseY,null);
                  
            shot = false;
            
            g2.drawImage(thefire.getImage(),objX,objY,null);
            objY+=bulletSpeed;        
            
            if(objY <= 0)
                isBulletSet = false;
        }
        
        if(hitDelay<20 && isHit)
        {
            g2.drawImage(explode.getImage(),objX,objY-hitPlace/hitDivider+hitDelay/2,null);
            hitDelay++;
        }
    }
    
    public int[] getCoordinates()
    {   
        int cor[] = new int[4];   
        cor[0]=objX; cor[1]=objX+bulWidth; cor[2]=objY; cor[3]=objY+bulHeight; 
        return cor;
    }
    
    public void hit()
    {
        isHit = true;
        
        if(!isElectric)//if it is electric shot behave different
            isBulletSet = false;
    }
    
    public boolean isSet()//TO PREVENT WRONG COLLUSION DETECTOINS
    {
        return isBulletSet;
    }
    
    public int getDamage()//How much damage does the bullet hit
    {
        return damage;
    }
    
    public boolean isElectric() //This weapon has a pecial type. But this type of
    {							//class handling is not couraged.	
        return isElectric;
    }
}
