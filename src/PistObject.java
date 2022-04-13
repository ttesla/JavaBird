import java.awt.Graphics2D;
import javax.swing.ImageIcon;

public class PistObject
{
    private ImageIcon obje, krater;
    private int x1, y1, width, height;
    private boolean isSet = false;
    private int yPos = 0;
    private boolean isDead = false;
    private int score  = 0;
    private int objHealth = 0;
    private Pat patla;
    
    public void common(int x, int y, int w, int h)
    {
        x1 = x;
        y1 = y;
        width  = w;
        height = h;
        isSet  = true;
        isDead = false;
        patla  = new Pat(); 
    }
      
    public void generateOil(int x, int y)
    {
        obje   = new ImageIcon(getClass().getResource("gfx/objects/oilholder.gif"));
        krater = new ImageIcon(getClass().getResource("gfx/objects/bkrater.gif"));
        score = 200;
        objHealth = 400;
        common(x,y,75,98); 
    }
    
    public void generateDock(int x, int y)
    {
        obje   = new ImageIcon(getClass().getResource("gfx/objects/dock.gif"));
        krater = new ImageIcon(getClass().getResource("gfx/objects/krater4.gif"));
        score  = 150;
        objHealth = 250;
        common(x,y,53,45); 
    }
    
    public void generatePod(int x, int y)
    {
        obje   = new ImageIcon(getClass().getResource("gfx/objects/pod.gif"));
        krater = new ImageIcon(getClass().getResource("gfx/objects/krater3.gif"));
        score  = 50;
        objHealth = 100;
        common(x,y,30,19); 
    }
    
    public void generateEasyP(int x, int y)
    {
        obje   = new ImageIcon(getClass().getResource("gfx/objects/easyYan.gif"));
        krater = new ImageIcon(getClass().getResource("gfx/objects/krater2.gif"));
        score  = 50;
        objHealth = 100;
        common(x,y,60,51); 
    }
    
    public void generateNormalP(int x, int y)
    {
        obje   = new ImageIcon(getClass().getResource("gfx/objects/normalYan.gif"));
        krater = new ImageIcon(getClass().getResource("gfx/objects/krater2.gif"));
        score  = 100;
        objHealth = 200;
        common(x,y,53,51); 
    }
    
    public void generateRedRight(int x, int y)
    {
        obje   = new ImageIcon(getClass().getResource("gfx/objects/redRight.gif"));
        krater = new ImageIcon(getClass().getResource("gfx/objects/krater2.gif"));
        score  = 50;
        objHealth = 100;
        common(x,y,40,47); 
    }
    
    public void generateBomb(int x, int y)
    {
        obje   = new ImageIcon(getClass().getResource("gfx/objects/bomb.gif"));
        krater = new ImageIcon(getClass().getResource("gfx/objects/krater1.gif"));
        score  = 50;
        objHealth = 75;
        common(x,y,15,15); 
    }
    
    public void generateBox(int x, int y)
    {
        obje   = new ImageIcon(getClass().getResource("gfx/objects/box.gif"));
        krater = new ImageIcon(getClass().getResource("gfx/objects/krater2.gif"));
        score  = 100;
        objHealth = 200;
        common(x,y,50,46); 
    }
    
    public void generateVant(int x, int y)
    {
        obje   = new ImageIcon(getClass().getResource("gfx/objects/vant.gif"));
        krater = new ImageIcon(getClass().getResource("gfx/objects/krater4.gif"));
        score  = 100;
        objHealth = 200;
        common(x,y,70,70); 
    }
    
    public void generateUranyum(int x, int y)
    {
        obje   = new ImageIcon(getClass().getResource("gfx/objects/uranyum.gif"));
        krater = new ImageIcon(getClass().getResource("gfx/objects/krater2.gif"));
        score  = 100;
        objHealth = 200;
        common(x,y,50,50);  
    }
    
    public void generateSunGenerator(int x, int y)
    {
        obje   = new ImageIcon(getClass().getResource("gfx/objects/sun.gif"));
        krater = new ImageIcon(getClass().getResource("gfx/objects/bkrater2.gif"));
        score  = 300;
        objHealth = 550;
        common(x,y,98,100);  
    }
    
    
    public void show(Graphics2D g2)
    {
        if(yPos%2 == 0 ){ y1 ++; yPos = 0;}
	
		yPos++;
               
        if(isSet)
            g2.drawImage(obje.getImage() ,x1,y1,null);
       
		else if(isDead)
        {
            g2.drawImage(krater.getImage() ,x1-10,y1-5,null);
            patla.birkereSetle(100,15,x1+width/2,y1+height/2);
        	patla.patlat(g2);
        }
        
        if(y1>800)
            isSet = false;
    }
    
    public int[] getCoordinates()
    {
        int cor[] = {x1,x1+width,y1,y1+height};
        return cor;
    }
    
    public boolean isSet()
    {
        return isSet;
    }
    
    public void hit(int damage)
    {
        objHealth -= damage;
        
        if(objHealth <= 0)
        {
            isDead = true;
            isSet  = false;
        }
    }
    
    public int getScore()
    {
        //Different objects will return different values
        if(isDead)
            return score;
        else
            return 0;
    }
}
