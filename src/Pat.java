import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Pat
{
    private int pixel_say = 0;
	private float tur[];
	private float artis[];
	private float eyim[];
	private float eyim2[];
	private float eyim3[];
	private float eyim4[];
	int sayac = 0;
	private int X,Y;
	private int buyukluk = 0;
	private final int PIX_SIZE = 4;


	public Pat(){}//Default constructor

	public Pat(int buy, int p_say, int x, int y)
	{
	    setPatlat(buy, p_say, x, y);
	}

        
	public void setPatlat(int buy, int p_say, int x, int y)
	{
	    sayac = 0;
		X = x;
		Y = y;
		buyukluk  = buy;
		pixel_say = p_say;

		tur   = new float[pixel_say];
		artis = new float[pixel_say];
		eyim  = new float[pixel_say];
		eyim2 = new float[pixel_say];
		eyim3 = new float[pixel_say];
		eyim4 = new float[pixel_say];

		float k = 0.0f;
		for(int i=0; i<pixel_say; i++, k += 0.15f)
		{
			eyim[i] = k;
			eyim2[pixel_say-1-i] = k;
			k = k%4;
		}

		k = 0.0f;
		for(int i=0; i<pixel_say; i++, k += 0.25f)
		{
			eyim3[i] = k;
			eyim4[pixel_say-1-i] = k;
			k = k%4;
		}

		for(int z=0; z<pixel_say; z++)
		{
		    float random = (float)((Math.random()*10)%3);
		    artis[z] = random;
		}
	}

	public void patlat(Graphics2D g2)
	{
		sayac++;

		if(sayac>buyukluk)
			return;

		for(int k=0; k<pixel_say; k++)
		    tur[k] += artis[k];

		//g2.setFont(new Font("Monospaced",Font.BOLD,19));

		for(int i=0; i<pixel_say; i++)
		{
			if(i%2 == 0)
				g2.setColor(Color.RED);
			else if(i%3 == 0)
				g2.setColor(Color.YELLOW);
			else
				g2.setColor(Color.ORANGE);

		   /*
			 g2.drawString(".",(X+tur[i]*eyim [i]),Y+tur[i]);
			 g2.drawString(".",(X-tur[i]*eyim2[i]),Y-tur[i]);
			 g2.drawString(".",(X-tur[i]*eyim3[i]),Y+tur[i]);
			 g2.drawString(".",(X+tur[i]*eyim4[i]),Y-tur[i]);
		   */
			
			g2.fillRect((int)(X+tur[i]*eyim [i]), (int)(Y+tur[i]), PIX_SIZE, PIX_SIZE);
			g2.fillRect((int)(X-tur[i]*eyim2[i]), (int)(Y-tur[i]), PIX_SIZE, PIX_SIZE);
			g2.fillRect((int)(X-tur[i]*eyim3[i]), (int)(Y+tur[i]), PIX_SIZE, PIX_SIZE);
			g2.fillRect((int)(X+tur[i]*eyim4[i]), (int)(Y-tur[i]), PIX_SIZE, PIX_SIZE);
			
		}
	}

	private boolean birKere = true;

	public void birkereSetle(int buy, int p_say, int x, int y)
	{
		if(birKere)
		{
			setPatlat(buy,p_say,x,y);
			birKere = false;
		}
		else
			return;
	}
}
