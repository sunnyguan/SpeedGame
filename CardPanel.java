import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *  Card panel deals with collision
 *  @author  Sunny Guan
 *  @version May 30, 2019
 *  @author  Period: 5
 *  @author  Assignment: SpeedGame
 *
 *  @author  Sources: none
 */
public class CardPanel extends JPanel
{

    private Point textPt = new Point( 0, 0 );

    private Point oriPt = new Point( 0, 0 );

    private Point mousePt;

    private boolean collide1 = false;

    private boolean collide2 = false;

    private boolean deact = false;

    private Card card;
    
    private BufferedImage image;

    private boolean released;

    /**
     * 
     * Returns collide1
     * @return collide1
     */
    public boolean isCollide1()
    {
        return collide1;
    }

    /**
     * 
     * Sets collide1
     * @param collide1 - what to set collide1 to
     */
    public void setCollide1( boolean collide1 )
    {
        this.collide1 = collide1;
    }

    /**
     * 
     * Returns collide2
     * @return collide2
     */
    public boolean isCollide2()
    {
        return collide2;
    }

    /**
     * 
     * Sets collide1
     * @param collide2 - what to set collide2 to 
     */
    public void setCollide2( boolean collide2 )
    {
        this.collide2 = collide2;
    }

    /**
     * 
     * Returns deact
     * @return deach
     */
    public boolean isDeact()
    {
        return deact;
    }

    /**
     * 
     * Sets deact
     * @param deact - what to set deact to
     */
    public void setDeact( boolean deact )
    {
        this.deact = deact;
    }

    /**
     * 
     * Returns card
     * @return card
     */
    public Card getCard()
    {
        return card;
    }
    
    /** 
     * 
     * Sets card
     * @param card - what card to set it to
     */
    public void setCard( Card card )
    {
        this.card = card;
        setImage();
    }

    /**
     * 
     * returns released
     * @return released
     */
    public boolean isReleased()
    {
        return released;
    }

    /**
     * 
     * sets released
     * @param released - what to set released to
     */
    public void setReleased( boolean released )
    {
        this.released = released;
    }
    
    /**
     * 
     * Set image of card based on card value and suite
     */
    public void setImage () {
        try
        {
            image = ImageIO.read(new File("images/" + card.getValue() + card.getSuite() + ".png"));
        }
        catch ( IOException e1 )
        {
            e1.printStackTrace();
        }
    }
    
    /**
     * 
     * Set image to backcard
     */
    public void setBackImage() {
        try
        {
            image = ImageIO.read(new File("images/back.png"));
        }
        catch ( IOException e1 )
        {
            e1.printStackTrace();
        }
    }
    
    /**
     * Creates a cardpanel with coordinates
     * @param x - x location
     * @param y - y location
     */
    public CardPanel(int x, int y) {
        setBackImage();
        this.setFont( new Font( "Serif", Font.ITALIC + Font.BOLD, 32 ) );
        this.setBounds( x, y, 70, 92 );
        setBackground( new Color( 0, 100, 0 ) );
    }

    /**
     * Creates card panel with index and coordinates
     * @param c - Card to set card to
     * @param index - index of card
     * @param x - x location
     * @param y - y location
     */
    public CardPanel( Card c, int index, int x, int y )
    {
        this.card = c;
        setImage();
        
        // int x = GameGUI.myStartx + GameGUI.playerdx * index;
        // int y = GameGUI.myStarty;
        textPt = new Point( x, y );
        oriPt = new Point( x, y );
        this.setFont( new Font( "Serif", Font.ITALIC + Font.BOLD, 32 ) );
        this.setBounds( x, y, 60, 92 );
        this.addMouseListener( new MouseAdapter()
        {

            /**
             * Get mouse location and repaint
             * @param e - mouse event
             */
            @Override
            public void mousePressed( MouseEvent e )
            {
                mousePt = e.getLocationOnScreen();
                released = false;
                repaint();
            }
            
            /**
             * set released to true
             * @param e - mouse event
             */
            @Override
            public void mouseReleased( MouseEvent e )
            {
                released = true;
            }
        } );
        this.addMouseMotionListener( new MouseMotionAdapter()
        {

            /**
             * Determines what to do when a card is dragged 
             * @param e - mouse event
             */
            @Override
            public void mouseDragged( MouseEvent e )
            {
                if ( !released && !deact )
                {
                    int dx = e.getXOnScreen() - mousePt.x;
                    int dy = e.getYOnScreen() - mousePt.y;
                    setBounds( textPt.x + dx, textPt.y + dy, 60, 92 );
                    textPt.x = textPt.x + dx;
                    textPt.y = textPt.y + dy;
                    mousePt = e.getLocationOnScreen();

                    if ( collide( textPt.x, textPt.y, GameGUI.centralX + GameGUI.centraldx, GameGUI.centralY ) )
                    {
                        collide1 = true;
                        setBounds( oriPt.x, oriPt.y, 60, 92 );
                        textPt.x = oriPt.x;
                        textPt.y = oriPt.y;
                        released = true;
                    }
                    else if ( collide( textPt.x, textPt.y, GameGUI.centralX + 2 * GameGUI.centraldx, GameGUI.centralY ) )
                    {
                        collide2 = true;
                        setBounds( oriPt.x, oriPt.y, 60, 92 );
                        textPt.x = oriPt.x;
                        textPt.y = oriPt.y;
                        released = true;
                    }
                    repaint();
                }
            }
        } );
        
        setBackground( new Color( 0, 100, 0 ) );
    }

    /**
     * 
     * Return if cards have collided
     * @param x1 - x location of first card
     * @param y1 - y location of first card
     * @param x2 - x location of second card
     * @param y2 - y location of second card
     * @return
     */
    private boolean collide( int x1, int y1, int x2, int y2 )
    {
        boolean x = Math.abs( x1 - x2 ) <= 20;
        boolean y = Math.abs( y1 - y2 ) <= 20;
        return x && y;
    }

    /**
     * draws the image
     * @param g - Graphics container
     */
    @Override
    public void paintComponent( Graphics g )
    {
        super.paintComponent( g );
        if ( !deact )
        {
            g.drawImage( image, 0, 0, null );
            //icon.paintIcon( this, g, 0, 0 );
        }
    }
}