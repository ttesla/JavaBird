/**
 * JAVA BIRD (2005 - 2008)
 * 
 * Last Update: 21.05.2009
 * 
 * It was first shown at 7D5 Demoparty - Turkey
 * 
 * Re-mastered for humanity in 2008
 * 
 * People who Contributed For JAVA BIRD:
 * 
 * PROGRAMMING:
 * - Omer Akyol (tesla/RESIDENT)
 * - Daghan Demirci
 * - Julien Gouesse (Now runs in Linux, thanks!)
 * 
 * GRAPHICS:
 * - Ahmet Metehan Alter (spritus / RESIDENT)
 * - Omer Akyol (Hours of ripping from google and photoshopping)
 * 
 * MUSIC:
 * Talha Karadeniz (flexi/RESIDENT)
 * Omer Akyol (Again hours of ripping music. Long live Gold Wave!)
 */
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

public class SkyMain 
{

	public static void main(String[] args) 
	{
		GraphicsEnvironment ge = GraphicsEnvironment
		.getLocalGraphicsEnvironment();
		DisplayMode newDisplayMode = new DisplayMode(1024, 768, 16, 0);
		GraphicsDevice de = ge.getDefaultScreenDevice();
	
		SkyHawkEngine skyEn = new SkyHawkEngine();

        try // Try fullscreen.
        {
            if(de.isFullScreenSupported()) // Try Fullscreen
            {
                skyEn.setUndecorated(true); // eliminate borders
                de.setFullScreenWindow(skyEn);
                de.setDisplayMode(newDisplayMode);
            }
            else // Windowed
            {
                skyEn.setUndecorated(false); // Borders on
                skyEn.setSize(1024, 768);
            }
        }
        catch(Exception e)
        {
            skyEn.setUndecorated(false); // Borders on
            skyEn.setSize(1024, 768);
        }
		
		try 
		{
			skyEn.initialize();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Something went very wrong!");
		}

		skyEn.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
