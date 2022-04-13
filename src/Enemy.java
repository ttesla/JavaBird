import java.awt.Graphics2D;

import javax.swing.ImageIcon;

public class Enemy
{
    private ImageIcon enemy;
    private ImageIcon exp[];
    private int objX , objY;
    private boolean isEnemySet = false;
    private boolean isHit      = false;
    private boolean isDead     = false;
    private int hitDelay = 0;
    private int enemyHealth = 0;
    private int score = 0;
    private int x2,y2;
    private int fireCount = 0;
    private int type;
    private Bullet enemyFire[];
    private int currentShot = 0;
    private int shotDelay;
    private int expanim=0;
    private Pat patla;
    private boolean shield    = false;
    private boolean ammo      = false;
    private boolean teslaGun  = false;
    private boolean isPowerUp = false;
    private ImageIcon powerUpImage;
    private int powWidth;
    private int powHeight;
    
    public void setCommon(int x,int y,int x2,int y2,int fireCoun)
    {
        //explode animation
    	patla = new Pat();
        exp = new ImageIcon[5];
        exp[0] = new ImageIcon(getClass().getResource("gfx/other/exp/e1.gif"));
        exp[1] = new ImageIcon(getClass().getResource("gfx/other/exp/e2.gif"));
        exp[2] = new ImageIcon(getClass().getResource("gfx/other/exp/e3.gif"));
        exp[3] = new ImageIcon(getClass().getResource("gfx/other/exp/e4.gif"));
        exp[4] = new ImageIcon(getClass().getResource("gfx/other/exp/e5.gif"));
        expanim=0;
        //animation
        
        objX    = x;
        objY    = y;
        this.x2 = x2;
        this.y2 = y2;
        
        shield   = false;
        ammo 	 = false;
        teslaGun = false;
        isPowerUp = false;
        
        setPowerUp();//Set power up thing... 
        
        isEnemySet = true;
        isHit      = false;
        isDead     = false;
        
        hitDelay   = 0;
        shotDelay  = 100;//Very sensitive.. dont come close
        fireCount  = fireCoun;
        
        for(int i=0; i<fireCount; i++)
            enemyFire[i] = new Bullet();
    }
    
    public void setPowerUp()
    {
        int z = ((int)(Math.random()*100))%10;
        
        if(z == 0 || z == 1)
        {
           ammo = true;
           isPowerUp = true;
           powerUpImage = new ImageIcon(getClass().getResource("gfx/other/ammo.gif"));
           powWidth = 55;
           powHeight = 50;
        }
        else if(z == 2)
        {
           teslaGun = true;
           isPowerUp = true;
           powerUpImage = new ImageIcon(getClass().getResource("gfx/other/teslaGun.gif"));
           powWidth = 21;
           powHeight = 50;
        }
        else if(z == 3)
        {
            shield = true;
            powerUpImage = new ImageIcon(getClass().getResource("gfx/other/shield.gif"));
            isPowerUp = true;
            powWidth  = 43;
            powHeight = 50;
        }
    }
    
    public void setEasyEnemy(int x, int y)
    {
        enemy      = new ImageIcon(getClass().getResource("gfx/ships/easy.gif"));
        enemyFire  = new Bullet[10];
        type = 1;//Enemy type
        score      = 50;
        enemyHealth = 50;
        setCommon(x,y,58,77,10);
    }
    
    public void setNormalEnemy(int x, int y)
    {
        enemy        = new ImageIcon(getClass().getResource("gfx/ships/normal.gif"));
        enemyFire    = new Bullet[10];
        type = 2;//Enemy type
        score       = 100; 
        enemyHealth = 150;
        setCommon(x,y,58,77,10);
    }
    
    public void setSpeedyEnemy(int x, int y)
    {
        enemy        = new ImageIcon(getClass().getResource("gfx/ships/red.gif"));
        enemyFire    = new Bullet[20];
        type = 3;//Enemy type
        score      = 100; 
        enemyHealth = 25;
        setCommon(x,y,47,50,20);
    }
    
    public void setHeroicEnemy(int x, int y)
    {
        enemy      = new ImageIcon(getClass().getResource("gfx/ships/heroic.gif"));
        enemyFire  = new Bullet[15];
        type = 4;//Enemy type
        score       = 250; 
        enemyHealth = 500;
        setCommon(x,y,120,150,15);
    }
    
    public void setBOSS1(int x, int y)
    {
        enemy      = new ImageIcon(getClass().getResource("gfx/ships/boss1.gif"));
        enemyFire  = new Bullet[30];
        type = 5; //Enemy type
        score       = 1250; 
        enemyHealth = 1500;
        setCommon(x,y,120,98,30);
    }
    
    public void showEnemy(Graphics2D g2)
    {
        for(int i=0; i<fireCount; i++)
            enemyFire[i].fire(g2);
             
        if(isEnemySet && !isDead)
            g2.drawImage(enemy.getImage(),objX,objY,null);
        
        else if(hitDelay<40 && isDead )
        {
            expAnimation(g2,hitDelay);
            hitDelay+=2;
        }
        
        if(isDead)
        {
            if(isPowerUp)
                g2.drawImage(powerUpImage.getImage(), objX + x2 / 2, objY + y2 / 2, null);
                
        	if(type == 5)
        		patla.birkereSetle(200,25,objX+x2/2,objY+y2/2);
        	else
        		patla.birkereSetle(100,15,objX+x2/2,objY+y2/2);
        	
        	patla.patlat(g2);
        }
    }
    
    public void hit(int damage)
    {
        isHit      = true;
        enemyHealth -= damage;
        
        if(enemyHealth <= 0)
        {
            isDead     = true;
            isEnemySet = false;
        }
    }
    
    public void setPosition(int x, int y)
    {
        objX += x;
        objY += y;
    }
    
    public void getPowerUp()
    {
        if(shield)
        {
            Hawk.hawkHealth	+= 500;
            if(Hawk.hawkHealth >= 1000) {Hawk.hawkHealth = 1000;}
        } 
        
        else if(teslaGun)
            SkyHawkEngine.electricAmmo++;
        
        else if(ammo)
        {
            SkyHawkEngine.machineAmmo += 75;
            SkyHawkEngine.heavymachineAmmo += 25;
        }
        
        isPowerUp = false;
    }
    
    public boolean isDead()
    {
        return isDead;
    }

    public boolean isPowerUp()
    {
        return isPowerUp;
    }
    
    public int[] getCoordinates()
    {
        int cor[] = {objX,objX+x2,objY,objY+y2};
        
        if(isDead && isPowerUp)
        {
            int newX = objX + x2 / 2;
            int newY = objY + y2 / 2;
            cor[0] = newX;
            cor[1] = newX + powWidth;
            cor[2] = newY;
            cor[3] = newY + powHeight;
        }

        return cor;
    }
    
    public boolean isSet()//TO PREVENT WRONG COLLUSION DETECTOINS
    {
        return isEnemySet;
    }
    
    public int getScore()
    {
        //Different enemis will return different values
        if(isDead)
            return score;
        else
            return 0;
    }
    
    public void shoot()
    {
        int delay = 0;
        if (type == 1)
            delay = 70;
        else if(type == 2)
            delay = 40;
        else if(type == 3)
            delay = 15;
        else if(type == 5)//Boss
        	delay = 40;
        
        if(!isDead && shotDelay>delay)
        {
        	currentShot++;
            if(currentShot >= fireCount)
                currentShot = 0;
        	
        	if(type == 1 || type == 2)
               enemyFire[currentShot%10].setRedEnemyFire(objX+26,objY+60);
            else if(type == 3)//Red enemy
            {
                int mod = currentShot % 10;
                enemyFire[mod].setWhiteEnemyFire(objX + 42, objY + 16);
                enemyFire[mod + 10].setWhiteEnemyFire(objX + 3, objY + 16);
            }
            else if(type == 5)//Level 1 Boss
            {
            	int mod = currentShot%10;
            	enemyFire[mod].setBoss1Fire(objX+30,objY+90);
            	enemyFire[(mod) + 10].setBoss1Fire(objX+60,objY+90);
            	enemyFire[(mod) + 20].setBoss1Fire(objX+90,objY+90);
            }
        
            shotDelay = 0; 
        }
        
        shotDelay++;
    }
    
    public int getFireCount()
    {
        return fireCount;
    }
    
    public Bullet getBullet(int a)
    {
        return enemyFire[a];
    }
    
    private int boss1Move = 2;
   
    public void move()
    {	
        if(type == 1)
            objY += 2;
        else if(type == 2)
            objY += 1;
        else if(type == 3)
            objY += 2;
        else if(type == 5)//Level 1 BOSS
        {
        	if((objX < 55) || (objX > 844)) { boss1Move = -boss1Move; }
        	
        	if(objY < 100)
        		objY += 2;
        	else
        	    objX += boss1Move;
        }
        
        if(objY>780)  
        	isEnemySet = false;
    }
    
    public void expAnimation(Graphics2D g2, int counter)
    {   
        g2.drawImage(exp[expanim].getImage(),objX+x2/2-40,objY+y2/2-50,null);
        
        if(counter%10 == 0)
        {
            expanim++;
            expanim = expanim%5;
        }
    }
}
