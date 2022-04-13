
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.Timer;


public class SkyHawkEngine extends JFrame implements ActionListener 
{
    private Timer animationTimer;
    private int widthX, heightY;
    private int animationDelay = 10;
    private int animCount = 0;
    private boolean upPressed, downPressed, rightPressed, leftPressed, shot, 
    				isGameOver = false;
    
    public static boolean Level1Complete = false;
    
    private int fireCount = 0; // Count of bullet fired
    
    private Bullet hawkFire[];
    private Enemy  enemy[];
    private Hawk hawk;
    private PistObject pobject[];
    private int maxObject = 50;
    
    private int colDelay  = 0; //  same thing with shotDelay...
    private int maxStandartBullet = 25, maxEnemyCount = 20;
    private int SCORE = 0;
    
    private AudioClip audio[];
    
    private int weaponType   = 0;
    private int maxShotDelay = 8;// A plane can't shoot like hell :)
    private int shotDelay = 0;  
    
    private Collusion col;
    
    private int currentEnemy = 0;
    private int AIsens = 70;//Sensitivity of AI in shooting 
    
    private int electricDelay = 0;
    private int maxElectricDelay = 75;
    
    private ImageIcon machineGun;
    private ImageIcon heavymachineGun;
    private ImageIcon electricGun;
    private ImageIcon wleft;
    private ImageIcon wright;
    //private ImageIcon spaceGround;
    private ImageIcon pistGround;
    //private ImageIcon star;
    private ImageIcon panel;
    private ImageIcon currentGun;
    
    public static int machineAmmo      = 200; 
    public static int heavymachineAmmo = 75;
    public static int electricAmmo = 5; 
    
    private int ammo;
	private boolean isGameState;
	
	// Intro variables
	private boolean isIntroState; // Switch for intro state
	private Intro intro;
    
    private boolean sc1 = false,//For the scene events
    				sc2 = false,// "   "    "    "  
    				sc3 = false,// "   "    "    " 
    				sc4 = false,// "   "    "    "
    				sc5 = false,// "   "    "    "
    				enemyWave = false,//Enemy wave coming
					//level1 = true,//Level 1 options
    				//level2 = false,//Level 2 options
    				loader1 = true,//Loader of level 1
    				//loader2 = false,//Loader of level 2
    				boss1 = false;//Boss of level 1
    				//boss2 = false;//Boss of level 2
	
	private int gameOverTime = 0;
	private int levelCompleteTime = 0;
	private int comboTime = 0;
    private int comboScore = 0;
    private int scoreDif = 0;
    private boolean isCombo = false;
    private boolean showComment = false;
    private int showCommentTime = 0;
    private int temp = 0;
    
    private BufferStrategy strategy;
	
	// String constants
	private final String text1      = "GAME OVER";
	private final String text2      = "PRESS 'ESC' TO MENU";
	private final String text3      = "MISSION COMPLETE!";
	private final String text4	    = "SORRY! NO MORE LEVELS...";
	private final String text5      = "PRESS 'ESC' TO MENU";
	private final String goodStr    = "GOOD !";
	private final String vgoodStr   = "VERY GOOD !";
	private final String excelStr   = "EXCELLENT !!";
	private final String perfectStr = "PERFECT !!";
	private final String insaneStr  = "INSANE !!!";
	private final String masterStr  = "ARCADE MASTER !!!";
	private final String cheatStr   = "ARE YOU CHEATING ???";
	private final String level1Str  = "LEVEL 1: Enemy Base";
     
    public void initialize() throws Exception 
    {    
        //--- FULL SCREEN --- SCREEN OPTIONS
        //Dimension size = Toolkit.getDefaultToolkit ().getScreenSize ();
        widthX  = 1024;//size.width;
        heightY = 768; //size.height;
        //setSize(widthX, heightY);
        //setUndecorated (true); //Removes screen accessory(title bar, side bars)
        setBackground(Color.BLACK);
        setTitle("JAVA BIRD 2008 - Remastered version");
        
        // GFX FILES
        machineGun		= new ImageIcon(getClass().getResource("/gfx/other/gunType.gif"));
        heavymachineGun = new ImageIcon(getClass().getResource("/gfx/other/heavygunType.gif"));
        electricGun 	= new ImageIcon(getClass().getResource("/gfx/other/electType.gif"));
        wleft			= new ImageIcon(getClass().getResource("/gfx/other/wleft.gif"));
        wright			= new ImageIcon(getClass().getResource("/gfx/other/wright.gif"));
        //spaceGround		= new ImageIcon(getClass().getResource("/gfx/other/spaceBack.gif"));
        pistGround		= new ImageIcon(getClass().getResource("/gfx/other/pistBack.jpg"));
        //star			= new ImageIcon(getClass().getResource("/gfx/other/yildiz.gif"));
        panel			= new ImageIcon(getClass().getResource("/gfx/other/panel.gif"));
        
        //AUDIO FILES
        audio        = new AudioClip[10];
        audio[0]     = Applet.newAudioClip(getClass().getResource("sfx/MG.au"));
        audio[1]     = Applet.newAudioClip(getClass().getResource("sfx/heavyMG.au"));
        //audio[2]     = Applet.newAudioClip(getClass().getResource("sfx/intro.au"));
        audio[3]     = Applet.newAudioClip(getClass().getResource("sfx/powerUp.au"));
        audio[4]     = Applet.newAudioClip(getClass().getResource("sfx/explode.au"));
        audio[5]     = Applet.newAudioClip(getClass().getResource("sfx/worm.au"));
        audio[6]     = Applet.newAudioClip(getClass().getResource("sfx/collide.au"));
        audio[7]     = Applet.newAudioClip(getClass().getResource("sfx/electric.au"));
        audio[8]     = Applet.newAudioClip(getClass().getResource("sfx/change.au"));
        audio[9]     = Applet.newAudioClip(getClass().getResource("sfx/empty.au"));
      
        currentGun = machineGun; //weapon image
        ammo = machineAmmo;		 //weapon ammo
        
        hawk = new Hawk();
        hawk.setHawk(300,500,widthX,heightY);
        
        pobject = new PistObject[maxObject];
        for(int i=0; i<maxObject; i++)
            pobject[i] = new PistObject();
  
        enemy = new Enemy[maxEnemyCount];
        for(int i=0; i < maxEnemyCount; i++)
            enemy[i] = new Enemy();
            
        hawkFire = new Bullet[maxStandartBullet];
        for (int i = 0; i < maxStandartBullet; i++)
            hawkFire[i] = new Bullet();
        
        //Hide Mouse
        BufferedImage cur = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);
        setCursor(getToolkit().createCustomCursor(cur,new Point(0,0),""));
        
		// Screen options
        setResizable(false);
        setVisible(true);
        
        //Buffer strategy
        createBufferStrategy(2); // Double buffer. 
		strategy = getBufferStrategy();
		
		// INTRO //
		intro = new Intro(widthX, heightY);
		isIntroState = true; // Game starts with Intro
		isGameState = false; // It is not game state yet
		
		// Start the animation loop
        startAnimation();

        addKeyListener(new KeyAdapter() {//KEY listeners
            public void keyPressed(KeyEvent kevent) 
			{
                if (kevent.getKeyCode() == 27)
				{
					if(isIntroState)
					{
						// If ESC is pressed in Intro state, say Good Bye!
						dispose();
						System.exit(0);
					}
                    
					else if(isGameState)
					{
						isIntroState = true;// Switch to Intro state
						isGameState = false;// Disable Game state	
						masterReset(); // Reset game
						intro.start(); // Start intro music
					}
                }

                if (kevent.getKeyCode() == KeyEvent.VK_UP)
                    upPressed = true;

                if (kevent.getKeyCode() == KeyEvent.VK_DOWN)
                    downPressed = true;

                if (kevent.getKeyCode() == KeyEvent.VK_RIGHT)
                    rightPressed = true;

                if (kevent.getKeyCode() == KeyEvent.VK_LEFT)
                    leftPressed = true;

                if (kevent.getKeyCode() == KeyEvent.VK_SPACE)
                    shot = true;
                
                if (kevent.getKeyCode() == KeyEvent.VK_X)
                    changeWeapon();
            }

            public void keyReleased(KeyEvent kevent)
			{
                if (kevent.getKeyCode() == KeyEvent.VK_UP)
                    upPressed = false;

                if (kevent.getKeyCode() == KeyEvent.VK_DOWN)
                    downPressed = false;

                if (kevent.getKeyCode() == KeyEvent.VK_RIGHT)
                    rightPressed = false;

                if (kevent.getKeyCode() == KeyEvent.VK_LEFT)
                    leftPressed = false;
                
                if (kevent.getKeyCode() == KeyEvent.VK_SPACE)
				{
                    shot = false; 
					
					if(isIntroState)
					{
						isIntroState = false; // Skip intro state
						isGameState = true;	  // Start Game state
						
						intro.stop();	 // Stop intro music
						audio[5].loop(); // Loop background music 
					}
				}
            }
        });
    }
	
	
	// MASTER RESET - Allows game replayable before exiting
	private void masterReset()
	{
		animCount = 0;
		upPressed = false;
		downPressed = false;
		rightPressed = false;
		leftPressed = false;
		shot = false; 
		isGameOver = false;
		Level1Complete = false;
    	fireCount = 0; // Count of bullet fired
		maxObject = 50;
		colDelay  = 0; //  same thing with shotDelay...
		maxStandartBullet = 25;
		maxEnemyCount = 20;
		SCORE = 0;
		weaponType = 0;
		maxShotDelay = 8;// A plane can't shoot like hell :)
		shotDelay = 0;  
		currentEnemy = 0;
		AIsens = 70;//Sensitivity of AI in shooting 
		electricDelay = 0;
		maxElectricDelay = 75;
		machineAmmo      = 200; 
		heavymachineAmmo = 75;
		electricAmmo	 = 5; 
		gameOverTime = 0;
		levelCompleteTime = 0;
		comboTime = 0;
		comboScore = 0;
		scoreDif = 0;
		isCombo = false;
		showComment = false;
		showCommentTime = 0;
		temp = 0;

		sc1 = false;//For the scene events
    	sc2 = false;// "   "    "    "  
    	sc3 = false;// "   "    "    " 
    	sc4 = false;// "   "    "    "
    	sc5 = false;// "   "    "    "
    	enemyWave = false;//Enemy wave coming
		loader1   = true;//Loader of level 1
    	boss1     = false;//Boss of level 1
		
		isIntroState = true; // Game starts with Intro
		isGameState = false; // It is not game state yet
					
		currentGun = machineGun; //weapon image
        ammo = machineAmmo;		 //weapon ammo
		
		hawk.setHawk(300,500,widthX,heightY); // Set Hawk
		
		// Re-generate all objects 
        for(int i=0; i<maxObject; i++)
            pobject[i] = new PistObject();
  
		// Re-generate all enemies
        for(int i=0; i < maxEnemyCount; i++)
            enemy[i] = new Enemy();
           
		// Re-generate all bullets
        for (int i = 0; i < maxStandartBullet; i++)
            hawkFire[i] = new Bullet();
		
		// Kill all Auido
		for(int i = 0; i < audio.length; i++)
			if(audio[i] != null)
				audio[i].stop();
	}
	
	
	// All of the Game 
	private void runGame(Graphics2D g2)
	{		
		whatWhen();         //Determines what happens when?
		levelDrawings(g2);  //Draws level specific things
		move();				//Move the plane(hawk)
		hawk.showHawk(g2);  //Show plane(HAWK)
		enemyMove(g2);      //move enemies
		enemyShoot();       //ai shoot
		hawkShoot(g2);      //Shoot
		drawPanel(g2);      //Draws panel
		comboMaker(g2);     //Calculates combos
		loaders(g2);        //Shows level

		if(isGameOver)
			gameOver(g2);
		else if(Level1Complete)
			levelComplete(g2);
		else
			collusionDetector();//Detects any hits or collusions

		animCount++;

		if(animCount >= Integer.MAX_VALUE)//Control integer bounds
			animCount = animCount/2;
	}

    //ALL DRAWINGS DONE HERE
    public void paint(Graphics g1)
	{       
		if(strategy == null) { return; }//null exception vermesin diye
		
		Graphics2D g2 = null;
		
		try
		{
			g2 = (Graphics2D)strategy.getDrawGraphics();
			g2.clearRect(0, 0, widthX, heightY);
		
			// INTRO
			if(isIntroState)
				intro.run(g2);
		
			// GAME
			else if(isGameState)
				runGame(g2);
		}
		finally
		{
			// Should be disposed, looked up from java.sun.com
			g2.dispose(); 
		}
		
		// Blitting - Swap buffers
		strategy.show();
 
    }//paint()
    
  
	// Game over routine
    public void gameOver(Graphics2D g2)
    {
        gameOverTime++;
        g2.setFont(new Font("Monospaced",Font.BOLD,40));
        g2.setColor(new Color((gameOverTime*3)%205+50,0,0));
        
        if(gameOverTime < 500)
            g2.drawString(text1, (widthX - text1.length()*25)/2, heightY/2);
        else if(gameOverTime < 1000)
		{
			String scoreStr = "FINAL SCORE: " + SCORE;
			g2.drawString(scoreStr, (widthX - scoreStr.length()*25)/2, heightY/2);
		}
        else
			g2.drawString(text2, (widthX - text2.length()*25)/2, heightY/2);
    }
    
    // Level complete routine
    public void levelComplete(Graphics2D g2)
    {   
        g2.setFont(new Font("Monospaced",Font.BOLD,40));
        g2.setColor(new Color(0,0,(gameOverTime*3)%205+50));
        
        if(levelCompleteTime < 500)
            g2.drawString(text3, (widthX - text3.length()*25)/2, heightY/2);
        else if(levelCompleteTime < 1000)
        {
			String scoreStr = "FINAL SCORE: " + SCORE;
			g2.drawString(scoreStr, (widthX - scoreStr.length()*25)/2, heightY/2);
		}
        else if(levelCompleteTime < 1500)
            g2.drawString(text4, (widthX - text4.length()*25)/2, heightY/2);
        else
            g2.drawString(text5, (widthX - text5.length()*25)/2, heightY/2);  
    }
   
    
    public void levelDrawings(Graphics2D g2) // Which Level, what to draw?
    {
        //if(level1)
        //{
        drawPist(g2);  //Prints the pist
        wallDraw(g2);  //Draw walls
        pistScenes();  //Pist scenes
        dispObject(g2);//Displays pist objects
        //}
    }
    
    public void drawPanel(Graphics2D g2)
    {
        g2.drawImage(panel.getImage(),0,0,null);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Monospaced",Font.BOLD,15));
        g2.drawString(""+SCORE,10,33);
        g2.drawImage(currentGun.getImage(),13,85,null);
        g2.drawString("x "+ammo,15,175);
        
        int tempHealth = hawk.getHealth(); 
        
        if(tempHealth <= 200 && !isGameOver)
        {
            g2.setFont(new Font("Monospaced",Font.BOLD,25));
            g2.setColor(new Color((animCount*2)%205+50,0,0));
            g2.drawString("DANGER !!",5,225);
        }
        else
            g2.setColor(Color.GREEN); 
        
        for(int i=0; i<hawk.getHealth(); i += 47)
            g2.fillRect(7+i/10,57,5,18);
    }
    
	// The combo calculator
    public void comboMaker(Graphics2D g2)// Get extra score for combo
    {
        scoreDif = SCORE - scoreDif;
        
        if(scoreDif > 0)
        { 
            isCombo = true;
            comboTime = 0;
            comboScore += scoreDif;
        }
        
        if(isCombo)
        {
            comboTime ++;
            
            if(!showComment)
                temp = comboScore/500;
            
            if(temp >= 1)
            {
                g2.setColor(Color.GREEN);
            	g2.setFont(new Font("Monospaced",Font.BOLD,15));
            	g2.drawString(temp + " K", 85 ,30);
            }
            
            if(comboTime > 125)
            {
                if(temp >= 1)
                {
                    SCORE += temp * 1000;
                    showComment = true;
                }
                
                comboScore = 0;
                comboTime  = 0;
                isCombo = false;
            }
        }
        
        if(showComment)
        {
            showCommentTime++;
            
            if(showCommentTime > 300)
            {
                showCommentTime = 0;
                showComment = false;
            }
            
            g2.setFont(new Font("Monospaced",Font.BOLD,30));
            g2.setColor(new Color(0,0,(showCommentTime*10)%205+50));
            
            if(temp < 3 && temp >= 1)
                g2.drawString(goodStr,	  (widthX - goodStr.length()*19)/2,    heightY/2-30);
            else if(temp < 4)
                g2.drawString(vgoodStr,   (widthX - vgoodStr.length()*19)/2,   heightY/2-30);
            else if(temp < 5)
                g2.drawString(excelStr,   (widthX - excelStr.length()*19)/2,   heightY/2-30);
            else if(temp < 6)
                g2.drawString(perfectStr, (widthX - perfectStr.length()*19)/2, heightY/2-30);
            else if(temp < 7)
                g2.drawString(insaneStr,  (widthX - insaneStr.length()*19)/2,  heightY/2-30);
            else if(temp < 9)
                g2.drawString(masterStr,  (widthX - masterStr.length()*19)/2,  heightY/2-30);
            else if(temp >= 9)
                g2.drawString(cheatStr,   (widthX - cheatStr.length()*19)/2,   heightY/2-30);
        }
            
        scoreDif = SCORE;
    }
    
    public void whatWhen()
    {
    	if(animCount > 300)
    		loader1 = false;
    	
    	//if(level1)
        if(animCount == 100) sc1 = true;
        else if(animCount == 1500) enemyWave = true;
        else if(animCount == 2300) sc2  = true;
        else if(animCount == 5300) sc3  = true;
        else if(animCount == 7600) sc4  = true;
        else if(animCount == 10150) sc5 = true; 
        else if(animCount == 12400) sc3 = true;
        else if(animCount == 14700) sc2 = true;
        else if(animCount == 17700) sc4 = true;
        else if(animCount == 20150) sc1 = true;
        else if(animCount == 21000) enemyWave = false;
    	else if(animCount == 22000) boss1 = true;
    }
    
    public void loaders(Graphics2D g2)
    {
    	if(loader1)
    	{
    		g2.setColor(Color.CYAN);    				
    		g2.setFont(new Font("Monospaced",Font.BOLD,20));
    		g2.drawString(level1Str, (widthX - level1Str.length()*15)/2, heightY/2);
    	}
    	/*else if(loader2)
    	{
    		g2.setColor(Color.CYAN);    				
    		g2.setFont(new Font("Monospaced",Font.BOLD,20));
    		g2.drawString("LEVEL 2: Deep Space",widthX/2-100,heightY/2);
    	}*/
    }
    
    public void pistScenes()
    {
    	setEnemies(animCount);//Set enemises
    	
        if(sc1)
        {
            pobject[0].generateOil(widthX-350, -150);
            pobject[1].generateDock(widthX-250,-150);
            pobject[2].generateDock(widthX-250,-110);
            pobject[3].generateDock(widthX-180,-150);
            pobject[4].generateDock(widthX-180,-110);
            for(int i=0; i<5; i++)
                for(int j=0; j<3; j++)
                pobject[5+j+i*3].generatePod(widthX-170-j*40,-180-i*30);
            
            sc1 = false;
        }
        
        if(sc2)
        {
            int i;
            
            for(i=0; i<4; i++)
            {
                pobject[i].generateEasyP(widthX-300,-540-i*70);
                pobject[4+i].generateNormalP(widthX-220,-540-i*70);
            }
            
            pobject[16].generateDock(widthX-320,-470);
            pobject[17].generateDock(widthX-200,-470);
            pobject[18].generateDock(widthX-320,-410);
            pobject[19].generateDock(widthX-200,-410);
            pobject[20].generatePod(widthX-260,-450);
            pobject[21].generatePod(widthX-260,-410);
            pobject[22].generatePod(widthX-230,-450);
            pobject[23].generatePod(widthX-230,-410);
       
            for(i=0; i<4; i++)
            {
                pobject[8+i].generateEasyP(widthX-300,-150-i*70);
                pobject[12+i].generateNormalP(widthX-220,-150-i*70);
            }   
     
            sc2 = false;
        }
        
        if(sc3)
        {
            int i,j ;
        
            for(i=0; i<4; i++)
                pobject[i].generateRedRight(150,-130-i*60);
            
            for(i=0; i<8; i++)
                for(j=0; j<2; j++)
                pobject[4+i*2+j].generateBomb(100+j*20,-100-i*30);
            
            pobject[20].generateSunGenerator(widthX-400,-350);
            
            for(i=0; i<5; i++)
                for(j=0; j<2; j++)
                    pobject[21+i*2+j].generatePod(widthX-250-j*40,-250-i*30);
            
            for(i=0; i<2; i++)
                for(j=0; j<2; j++)
                    pobject[31+j+i*2].generateDock(350+j*60,-200-i*60);
           
            sc3 = false;
        }
        
        if(sc4)
        {
            int i,j;
            
            for(i=0; i<5; i++)
                for(j=0; j<2; j++)
                    pobject[i*2 + j].generatePod(110+j*40,-160-i*30);
            
            pobject[10].generateDock(100,-120);
            pobject[11].generateDock(170,-120);
            
            pobject[12].generateBox(320,-250);
            pobject[13].generateBox(370,-250);
            pobject[14].generateBox(320,-300);
            pobject[15].generateBox(370,-300);
            
            for(i=0; i<2; i++)
                for(j=0; j<3; j++)
                    pobject[16+i*3 + j].generateVant(widthX-320-j*70,-400-i*70);
            
            pobject[22].generateOil(widthX-240,-450);
                    
            sc4 = false;
        }
        
        if(sc5)
        {
            int i,j;
            
            for(i=0; i<2; i++)
                for(j=0; j<4; j++)
                    pobject[i*4+j].generateUranyum(75+j*60,-100-i*60);
            
            pobject[8].generateSunGenerator(320,-150);
            
            pobject[9].generateVant(widthX-310, -200);
            pobject[10].generateVant(widthX-310, -270);
            pobject[11].generateVant(widthX-310, -340);
            pobject[12].generateVant(widthX-170, -200);
            pobject[13].generateVant(widthX-170, -270);
            pobject[14].generateVant(widthX-170, -340);
            
            pobject[15].generateDock(widthX-230, -190);
            pobject[16].generateDock(widthX-230, -260);
            pobject[17].generateDock(widthX-230, -330);
                    
            sc5 = false;
        }
    }
    
    public void dispObject(Graphics2D g2)
    {
        for(int i=0; i<maxObject; i++)
            pobject[i].show(g2);
    }
    
    public void wallDraw(Graphics2D g2)//50 e 150
    {   
        int speed = animCount%150;
        
        for(int i=0; i<heightY/150+3; i++)
        {
            g2.drawImage(wleft.getImage() ,0,-300+i*150+speed*2,null);
            g2.drawImage(wright.getImage(),widthX-50,-300+i*150+speed*2,null);
        }
    }
    
    public void drawPist(Graphics2D g2)
    {
        int speed = animCount%1536;
        
        for(int j=0; j<2; j++)
            g2.drawImage(pistGround.getImage() ,0,-768+j*768+speed/2,null);
    }
       
    public void collusionDetector()
    {
        col = new Collusion();
        colDelay++;
        //Detect if Hawk's fire hit something... 
        for(int i=0; i<maxStandartBullet; i++)
        {
            for(int j=0; j<maxEnemyCount; j++)
            {
                if(enemy[j].isSet())//To get rid of Unnecessary collision calculations
                {
                    if(hawkFire[i].isSet())
                    if(col.isCollide(hawkFire[i].getCoordinates()[0],
                                     hawkFire[i].getCoordinates()[1],
                                     hawkFire[i].getCoordinates()[2],
                                     hawkFire[i].getCoordinates()[3],
                                     enemy[j].getCoordinates()[0],
                                     enemy[j].getCoordinates()[1],
                                     enemy[j].getCoordinates()[2],
                                     enemy[j].getCoordinates()[3]))
                    {
                        hawkFire[i].hit();
                        enemy[j].hit(hawkFire[i].getDamage());
                        SCORE += enemy[j].getScore();
                        if(!enemy[j].isSet())
                            audio[4].play();    
                    }
                    if(col.isCollide(hawk.getCoordinates()[0],
                            hawk.getCoordinates()[1],
                            hawk.getCoordinates()[2],
                            hawk.getCoordinates()[3],
                            enemy[j].getCoordinates()[0],
                            enemy[j].getCoordinates()[1],
                            enemy[j].getCoordinates()[2],
                            enemy[j].getCoordinates()[3]) && colDelay > 20)
                    {
                        colDelay = 0;
                        audio[6].play();  
                        hawk.hit(100);    // same with the thing below
                        enemy[j].hit(100);//If collide with enemy ship get 100 damage
                        SCORE += enemy[j].getScore();
                        
						if(!enemy[j].isSet() || !hawk.isSet())
                        {
                            audio[4].play();
                            
							if(!hawk.isSet() && !isGameOver)
                            {
                                isGameOver = true;
                                audio[5].stop();
                            }
                        } 
                    }
                }
                
                if(enemy[j].isDead() && enemy[j].isPowerUp() && 
                        col.isCollide(hawk.getCoordinates()[0],
                                	  hawk.getCoordinates()[1], 
                                	  hawk.getCoordinates()[2], 
                                	  hawk.getCoordinates()[3], 
                                	  enemy[j].getCoordinates()[0], 
                                	  enemy[j].getCoordinates()[1],
                                	  enemy[j].getCoordinates()[2],
                                	  enemy[j].getCoordinates()[3]))
                {
                    audio[3].play();
                    enemy[j].getPowerUp();
                    
                    if(weaponType == 0)
                        ammo = machineAmmo;
                    else if(weaponType == 1)
                        ammo = heavymachineAmmo;
                    else if(weaponType == 2)
                        ammo = electricAmmo;                  
                }
            }
            
            for(int j=0; j<maxObject; j++)
            {
                if(pobject[j].isSet())//Gereksiz carpismalardan kurtulmak icin.
                {
                    if(hawkFire[i].isSet() && !hawkFire[i].isElectric())
                        if(col.isCollide(hawkFire[i].getCoordinates()[0],
                                         hawkFire[i].getCoordinates()[1],
                                         hawkFire[i].getCoordinates()[2],
                                         hawkFire[i].getCoordinates()[3],
                                         pobject[j].getCoordinates()[0],
                                         pobject[j].getCoordinates()[1],
                                         pobject[j].getCoordinates()[2],
                                         pobject[j].getCoordinates()[3]))
                        {
                            hawkFire[i].hit();
                            pobject[j].hit(hawkFire[i].getDamage());
                            SCORE += pobject[j].getScore();
                            if(!pobject[j].isSet())
                                audio[4].play();    
                        }
                }
            }
        }
        
        for(int i=0; i<maxEnemyCount; i++)//Enemy Fire Hit control
        {
            for(int j=0; j<enemy[i].getFireCount(); j++)
            {
                if(enemy[i].getBullet(j).isSet())
                if(col.isCollide(
                        enemy[i].getBullet(j).getCoordinates()[0],
                        enemy[i].getBullet(j).getCoordinates()[1],
                        enemy[i].getBullet(j).getCoordinates()[2],
                        enemy[i].getBullet(j).getCoordinates()[3],
                        hawk.getCoordinates()[0],
                        hawk.getCoordinates()[1],
                        hawk.getCoordinates()[2],
                        hawk.getCoordinates()[3]))
                {
                    enemy[i].getBullet(j).hit();
                    hawk.hit(enemy[i].getBullet(j).getDamage());
                    
                    if(!hawk.isSet() && !isGameOver)
                    {
                        isGameOver = true;
                        audio[5].stop();
                        audio[4].play();
                    } 
                }
            }
        }
    }
 
    public void setEnemies(int count)
    {
        if(count % 100 == 0 && enemyWave)//1 enemy comes in every 100 cycle
        {
        	if(currentEnemy >= maxEnemyCount)
                currentEnemy = 0;
        	
        	if(enemy[currentEnemy].isSet())
        	{
        		currentEnemy++;
        		return;
        	}
        		
            count = getRandom(0,3);
            
            if(count == 0 || count == 1)
                enemy[currentEnemy].setEasyEnemy(getRandom(60,widthX-120),-75);
            else if(count == 2)
                enemy[currentEnemy].setNormalEnemy(getRandom(60,widthX-120),-75);
            else if(count == 3)
                enemy[currentEnemy].setSpeedyEnemy(getRandom(60,widthX-120),-75);
            
            currentEnemy ++;
        }
        
        if(boss1)
        {
           enemy[0].setBOSS1(widthX-180,-120);
           enemy[1].setBOSS1(55,-120);
           enemy[2].setBOSS1(widthX-180,-450);
           enemy[3].setBOSS1(55,-450);
           AIsens = 500;
           boss1 = false;
        }
    }
    
    public void enemyMove(Graphics2D g2)
    {
        //standart move;
        for(int i = 0; i < maxEnemyCount; i++)
            if(enemy[i].isSet())
                enemy[i].move();// setPosition
        
        for(int i = 0; i < maxEnemyCount; i++)
            enemy[i].showEnemy(g2);
    }
    
    public void enemyShoot()
    {
        col = new Collusion();
        
        for(int i = 0; i < maxEnemyCount; i++)
            if(enemy[i].isSet())
                if(col.readyToShoot(
                   (hawk.getCoordinates()[0]+hawk.getCoordinates()[1])/2,
                   enemy[i].getCoordinates()[0],enemy[i].getCoordinates()[1],AIsens))
                    enemy[i].shoot();
    }
    
    public int getRandom(int lower, int upper)
    {
        return  ((int)(Math.random()*1000))%(upper-lower+1) + lower;
    }
    
    public void move() {//Moves the plane excellent work :)

        if (upPressed)
            hawk.moveUp();

        if (downPressed)
            hawk.moveDown();

        if (rightPressed)
            hawk.moveRight();
           
        if (leftPressed)
            hawk.moveLeft();
        
        if (!(rightPressed || leftPressed || upPressed || downPressed))
            hawk.standStill();
    }

    public void hawkShoot(Graphics2D g2)
    {
        if (shot && !isGameOver && shotDelay > maxShotDelay)
        {
            if(ammo <= 0)
            {
                audio[9].play();
                fireCount--;
            }
            else if(weaponType == 0)
            {
                hawkFire[fireCount].setMachineGunFire(
                        hawk.getCoordinates()[0],
                        hawk.getCoordinates()[2]);
                audio[0].play();
                ammo = --machineAmmo;
            }
            else if(weaponType == 1)
            {
                hawkFire[fireCount].setHeavyMachineGunFire(
				hawk.getCoordinates()[0],
				hawk.getCoordinates()[2]);
                audio[1].play();
                ammo = --heavymachineAmmo; 
            }
            else if(weaponType == 2 && electricDelay >= maxElectricDelay)
            {
                electricDelay  = 0;
                hawkFire[fireCount].setElectricGun(
                            hawk.getCoordinates()[0]-50,
                            hawk.getCoordinates()[2]);
                audio[7].play();
                ammo = --electricAmmo;
            }
            
            fireCount++;
            fireCount = fireCount%maxStandartBullet;
            shotDelay = 0;                
        }

        for (int i = 0; i < maxStandartBullet; i++)
            hawkFire[i].fire(g2); 

        shotDelay++;
        electricDelay++;
    }

    public void changeWeapon()
    {
        if(isGameOver)
            return;
        
        audio[8].play();
        
        weaponType++;
        weaponType = weaponType%3;
        
        if(weaponType == 0)
        {
            currentGun = machineGun;
            ammo = machineAmmo;
        }
        else if(weaponType == 1)
        {
            currentGun = heavymachineGun;
            ammo = heavymachineAmmo;
        }
        else if(weaponType == 2)
        {
            currentGun = electricGun;
            ammo = electricAmmo;
        }
    }
    
    public void startAnimation() {
        if (animationTimer == null) {
            animationTimer = new Timer(animationDelay, this);
            animationTimer.start();
        }
    }

    public void stopAnimation() {
        animationTimer.stop();
    }

    public void actionPerformed(ActionEvent arg0) {
        repaint();
    }
}