
public class Collusion
{
    public boolean isCollide(int x1, int x2, int y1, int y2,
            			     int ex1, int ex2, int ey1, int ey2)
    {
        if( ((x1>=ex1 && x1<=ex2) || (x2>=ex1 && x2<=ex2)) &&
            ((y1>=ey1 && y1<=ey2) || (y2>=ey1 && y2<=ey2)) )
            return true;
        
        else if( ((ex1>=x1 && ex1<=x2) || (ex2>=x1 && ex2<=x2)) &&
                 ((ey1>=y1 && ey1<=y2) || (ey2>=y1 && ey2<=y2)) )
            return true;
        
        else
            return false;
    }
    
    public boolean readyToShoot(int hawkNorm, int enemyX1, int enemyX2,
            					int sens)
    {
        if(hawkNorm>(enemyX1-sens) && hawkNorm<(enemyX2+sens))
            return true;
        else 
            return false;   
    }

}
