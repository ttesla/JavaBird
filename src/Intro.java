import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;


public class Intro
{
    private int animCount = 0;
    private ImageIcon residentLogo, inspire13Logo, backGrd, javaBirdLogo;
    private Pat pat1, pat2;
	private int widthX, heightY;
	private AudioClip intrSfx;
	private Font font1, font2;
	private float residentLogoR, inspire13LogoR; // Image ratios WIDTH / HEIGHT
	private int logoCycle = 0;
	private int gameLogoCycle = 0;
	private String[] text;
	private String[] text2;
	private String[] tempText;
	private String startText;
	private int harmonicVar = 1;
	private int harmonicConst = 1;
    
    public Intro(int widthX, int heightY)
    {
		this.widthX = widthX;
		this.heightY = heightY;
		
		font1 = new Font("Monospaced",Font.BOLD,30);
		font2 = new Font("Monospaced", Font.BOLD, 40);
		
		pat1 = new Pat(800,25,widthX/2-200,320);
		pat2 = new Pat(800,25,widthX/2+100,320);
		residentLogo  = new ImageIcon(getClass().getResource("gfx/other/logo.gif"));
		backGrd       = new ImageIcon(getClass().getResource("gfx/other/spaceBack.gif"));
		inspire13Logo = new ImageIcon(getClass().getResource("gfx/other/inspire13.jpg"));
		javaBirdLogo  = new ImageIcon(getClass().getResource("gfx/other/javabird.jpg"));
		
		residentLogoR  = residentLogo.getIconWidth()  / residentLogo.getIconHeight();
		inspire13LogoR = inspire13Logo.getIconWidth() / inspire13Logo.getIconHeight();
		
		intrSfx  = Applet.newAudioClip(getClass().getResource("sfx/intro.au"));
		
		// TEXTS
		text = new String[6];
		text[0] = "-- CREDITS --";
		text[1] = "Omer Akyol      (Programming)";
		text[2] = "Daghan Demirci  (Programming)";
		text[3] = "Metehan Alter   ( Graphics  )";
		text[4] = "Talha Karadeniz (   Music   )";
		text[5] = "* some graphics are taken from internet";
		
		text2 = new String[5];
		text2[0] = "-- CONTROLS --";
		text2[1] = "Fly with arrow keys       ";
		text2[2] = "'X' to change weapon      ";
		text2[3] = "'SPACE' to fire!          ";
		text2[4] = "'ESC' to give up and quit!";
		
		startText = "HIT 'SPACE' TO START!!!";
		tempText = text2; // Controls will be shown...
		
		intrSfx.loop();
    }

    //TUM CIZIMLER BURADA
    public void run(Graphics2D g2)
    {
		// Draw background
        drawBack(g2);
		
		// Logo part
        logoUp(g2);
		    
		// Explode part and game logo
        if(animCount > 600)
        {
        	pat1.patlat(g2);
         	pat2.patlat(g2);
			gameLogo(g2);
        }
		
		// Scroll part
		if(animCount > 800)
		{
			scrollText(g2);
		}
        
       // g2.setColor(Color.WHITE);
       // g2.drawString("Counter: " + animCount,30,30);
             
		animCount++;
    }
	
	// Stop intro
	public void stop()
	{
		intrSfx.stop();
	}
	
	// Start intro
	public void start()
	{
		intrSfx.loop();
	}
	
    // Show scroll text
    private void scrollText(Graphics2D g2)
    {	
		g2.setFont(font1);
		
		// Adjust color fade in / fade out effect
		if(harmonicVar < 1 || harmonicVar > 250)
		{
			harmonicConst = -harmonicConst; // Go to other side
			
			// Switch texts - Switch only if harmonicVar == 0
			if(harmonicVar == 0)
			{
				if(tempText.equals(text2))
					tempText = text;
				else
					tempText = text2;
			}
		}
		
		g2.setColor(new Color(harmonicVar, harmonicVar, harmonicVar));
		harmonicVar += harmonicConst;
		
		for(int i = 0; i < tempText.length; i++)
		{
			if(i == 0)
				g2.drawString(tempText[i], (widthX - tempText[i].length()*19)/2 , 375 + i*35);
			
			else
				g2.drawString(tempText[i], (widthX - tempText[i].length()*19)/2 , 400 + i*35);
		}
		
		g2.setColor(new Color((150 + animCount*4)%250, 0, 0));
		g2.setFont(font2);
		g2.drawString(startText, (widthX - startText.length()*25)/2, heightY - 100);
    }
	
	// JAVA BIRD logo part
	private void gameLogo(Graphics2D g2)
	{
		int x = (widthX - javaBirdLogo.getIconWidth()) / 2;
		int y = 250;
		int slideX = javaBirdLogo.getIconWidth()/2;		
		
		// Slide effect
		if(gameLogoCycle <= slideX)
		{
			g2.drawImage(javaBirdLogo.getImage(), x, y, null);
			
			g2.setColor(Color.BLACK);
			g2.fillRect(x, y, slideX - gameLogoCycle, javaBirdLogo.getIconHeight());
			g2.fillRect(slideX + x + gameLogoCycle, y, slideX - gameLogoCycle, javaBirdLogo.getIconHeight());
		}
		
		else if(gameLogoCycle < slideX + 100)
		{
			g2.drawImage(javaBirdLogo.getImage(), x, y - (gameLogoCycle - slideX), null);
		}
		
		else
			g2.drawImage(javaBirdLogo.getImage(), x, y - 100, null);
		
		gameLogoCycle++;
	}
    
	// Draw background
    private void drawBack(Graphics2D g2)
    {
        int speed = animCount%768;
        
        for(int j=0; j<2; j++)
			g2.drawImage(backGrd.getImage(), 0, -768 + j*768 + speed, null);
    }
    
	// Logo animation
    private void logoUp(Graphics2D g2)
    {
		ImageIcon tempIcon = null;
		int size = 0;
		int ratio = 0;
		
		// Resident logo COME
    	if(logoCycle < 100)
		{	
			size = logoCycle*5;
			tempIcon = residentLogo;
			ratio = (int)(size / residentLogoR);
		}
		
		// Resident logo HOLD
		else if(logoCycle < 150)
		{
			size  = 500;
			tempIcon = residentLogo;
			ratio = (int)(size / residentLogoR);
		}
		
		// Resident logo GO 
		else if(logoCycle < 250)
		{
			size  = (250 - logoCycle)*5;
			tempIcon = residentLogo;
			ratio = (int)(size / residentLogoR);
		}
		
		// Inspire13 logo COME
		else if(logoCycle < 350)
		{
			size = (logoCycle - 250)*5;
			tempIcon = inspire13Logo;
			ratio = (int)(size / inspire13LogoR);
		}
		
		// Inspire13 logo HOLD
		else if(logoCycle < 400)
		{
			size  = 500;
			tempIcon = inspire13Logo;
			ratio = (int)(size / inspire13LogoR);
		}
		
		else if(logoCycle < 500)
		{
			size  = (500 - logoCycle)*5;
			tempIcon = inspire13Logo;
			ratio = (int)(size / inspire13LogoR);
		}
		
		else 
			return; // May be we can cain some CPU cycles ;)
		
		// Draw logo
		if(tempIcon != null)
		g2.drawImage(tempIcon.getImage(), 
					(widthX - size)/2,	 // X
					250,				 // Y
					size,				 // X size
					ratio,				 // Y size
					null);
			
		logoCycle++;
    }
}