import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 * 
 *  Creates the different frames for the game (home page, instruction, set up, game)
 *
 *  @author  Rin Ha
 *  @version May 30, 2019
 *  @author  Period: 5
 *  @author  Assignment: SpeedGame
 *
 *  @author  Sources: none
 */ 
public class GameGUI extends JFrame
{

    /**
     * 
     * Creates a GameGUI to make the GUI for the game
     * @param args not used
     */
    public static void main( String args[] )
    {
        GameGUI gg = new GameGUI();
        gg.init();
    }

    /**
     * 
     * Creates the GUI for the Game 
     */
    public void init()
    {
        player = new Player();
        JFrame homePage = new JFrame( "Home Page" );
        homePage.setSize( 600, 550 );
        homePage.getContentPane().setBackground( new Color( 130, 20, 0 ) );

        JButton playButton = new JButton( "PLAY" );
        int mid = homePage.getWidth() / 2 - 182 / 2;
        playButton.setBounds( mid, 248, 182, 29 );
        JButton instructionButton = new JButton( "INSTRUCTIONS" );
        instructionButton.setBounds( mid, 282, 182, 29 );

        String s = "/images/SpeedTitle.png";
        //
        ImageIcon icon = new ImageIcon( GameGUI.class.getResource( s ) );
        JLabel label = new JLabel();
        label.setIcon( icon );
        label.setBounds( homePage.getWidth() / 2 - 306 / 2, 80, 306, 144 );
        homePage.getContentPane().add( label );

        homePage.getContentPane().setLayout( null );
        homePage.getContentPane().add( playButton );
        homePage.getContentPane().add( instructionButton );
        homePage.setResizable( false );
        homePage.setVisible( true );

        // name and IP address GUI
        JFrame setUp = new JFrame( "Set Up" );
        setUp.setSize( 400, 300 );
        setUp.getContentPane().setLayout( null );

        JButton startGame = new JButton( "Let's Play!" );
        startGame.setBounds( 200 - 45, 197, 90, 20 );
        setUp.getContentPane().add( startGame );

        JLabel namelbl = new JLabel( "Name:" );
        namelbl.setBounds( 61, 67, 50, 16 );
        setUp.getContentPane().add( namelbl );
        JLabel IPAddress = new JLabel( "IP Address:" );
        IPAddress.setBounds( 61, 121, 90, 16 );
        setUp.getContentPane().add( IPAddress );

        JTextField nameText = new JTextField( "" );
        JTextField IPText = new JTextField( "" );
        nameText.setBounds( 155, 62, 130, 26 );
        IPText.setBounds( 155, 116, 130, 26 );
        nameText.setEditable( true );
        IPText.setEditable( true );
        setUp.getContentPane().add( nameText );
        setUp.getContentPane().add( IPText );
        nameText.setColumns( 10 );
        IPText.setColumns( 10 );

        JButton ok1 = new JButton( "OK" );
        ok1.setBounds( 322, 62, 50, 29 );
        setUp.getContentPane().add( ok1 );
        JButton ok2 = new JButton( "OK" );
        ok2.setBounds( 322, 116, 50, 29 );
        setUp.getContentPane().add( ok2 );

        // Instructions page GUI
        JFrame instruct = new JFrame( "Instructions" );
        instruct.setResizable( false );
        File file = new File("/Instructions"); 
        String instructions = "";
        try
        {
            instructions = readFileAsString("Instructions.txt");
        }
        catch ( Exception e1 )
        {
            e1.printStackTrace();
        }
        JTextArea text = new JTextArea( instructions, 30, 50 );
        JScrollPane sp = new JScrollPane( text );


        instruct.setLayout( new FlowLayout() );
        instruct.setSize( 700, 570 );
        instruct.getContentPane().add( sp );

        // Speed Game GUI
        game = new JFrame( "Speed Game" );
        game.setSize( 600, 550 );
        pane = game.getLayeredPane();

        pane.setBackground( new Color( 0, 100, 0 ) );
        game.setBackground( new Color( 0, 100, 0 ) );
        game.getContentPane().setBackground( new Color( 0, 100, 0 ) );
        game.setResizable( false );

        JMenuBar menuBar = new JMenuBar();
        game.setJMenuBar( menuBar );
        JMenu gameInstructions = new JMenu( "Instructions" );
        menuBar.add( gameInstructions );

        stuckButton = new JRadioButton( "" );
        stuckButton.setBounds( 515, 166, 42, 23 );
        stuckButton.setBackground( new Color( 0, 100, 0 ) );
        game.getLayeredPane().add( stuckButton );

        lblStuck = new JLabel( "STUCK?" );
        lblStuck.setForeground( new Color( 255, 255, 255 ) );
        lblStuck.setBounds( 505, 148, 61, 16 );
        game.getLayeredPane().add( lblStuck );
        
        JLabel myName = new JLabel( "" );
        myName.setForeground( new Color( 255, 255, 255 ) );
        myName.setBounds( 455, 490, 150, 16 );
        game.getLayeredPane().add( myName );

        // instruction page
        instructionButton.addMouseListener( new MouseAdapter()
        {
            /**
             * Makes the instruction page show up
             * @param e - mouse event 
             */
            public void mouseClicked( MouseEvent e )
            {
                instruct.setVisible( true );
            }

        } );

        // play button --> set up GUI
        playButton.addMouseListener( new MouseAdapter()
        {
            /**
             * Changes to set up GUI
             * @param e - mouse event
             */
            public void mouseClicked( MouseEvent e )
            {
                homePage.setVisible( false );
                setUp.setVisible( true );
            }

        } );

        // play button --> set up GUI
        startGame.addMouseListener( new MouseAdapter() // HERE
        {
            /** 
             * Gets the player name and IP info and changes the GUI to the game
             * @param e - mouse event
             */
            public void mouseClicked( MouseEvent e )
            {
                String ip = IPText.getText();
                if ( IPText.getText().charAt( 0 ) != '1' )
                {
                    ip = "192.168.1.131";
                }
                String nameStr = nameText.getText();
                myName.setText( nameStr );
                
                player.init( ip );
                // setName
                player.setName( nameText.getText() );
                System.out.println( IPText.getText() );
                cardsInit( pane );
                setUp.setVisible( false );
                game.setVisible( true );
            }

        } );

        // instructions pop up when playing the game
        gameInstructions.addMouseListener( new MouseAdapter()
        {
            /**
             * Makes the instruction window pop up
             * @param e - mouse event
             */
            public void mouseClicked( MouseEvent e )
            {
                instruct.setVisible( true );
            }
        } );

        stuckButton.addMouseListener( new MouseAdapter()
        {
            /**
             * Game know player is stuck
             */
            public void mouseClicked( MouseEvent e )
            {
                player.stuck();
                lblStuck.setText( "Waiting..." );
            }
        } );

    }
    
    private JLabel lblStuck;
    
    private JRadioButton stuckButton;

    private Player player;

    public static int playerdx = 75;

    public static int otherStartx = 130;

    public static int myStartx = 80;

    public static int otherStarty = 49;

    public static int myStarty = 370;

    public static int centraldx = 118;

    public static int centralX = 70;

    public static int centralY = ( myStarty + ( otherStarty + 92 ) ) / 2 - 92 / 2;

    private static int[] hand1X = { otherStartx, otherStartx + playerdx, otherStartx + 2 * playerdx,
        otherStartx + 3 * playerdx, otherStartx + 4 * playerdx };

    private static int[] hand2X = { myStartx, myStartx + playerdx, myStartx + 2 * playerdx, myStartx + 3 * playerdx,
        myStartx + 4 * playerdx };

    private CardPanel deck1;

    private CardPanel deck2;
    
    private ArrayList<CardPanel> theirCards;

    /**
     * 
     * Initializes the cards
     * @param pane layered pane used for animation
     */
    private void cardsInit( JLayeredPane pane )
    {
        myCards = new ArrayList<CardPanel>();
        theirCards = new ArrayList<CardPanel>();
        
        while(player.getHand().size() != 5) {
            try
            {
                Thread.sleep( 200 );
            }
            catch ( InterruptedException e1 )
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        ArrayList<Card> h1 = player.getHand();
        ImageIcon back = new ImageIcon( GameGUI.class.getResource( "/images/back.png" ) );
        for ( int i = 0; i < h1.size(); i++ )
        {
            CardPanel cp = new CardPanel( h1.get( i ), i , myStartx + playerdx * i, myStarty);
            // cp.setBounds( myStartx + playerdx * i, myStarty, 60, 92 );
            myCards.add( cp );
            cp.setOpaque( true );

            CardPanel jp = new CardPanel( otherStartx + playerdx * i, otherStarty );
            theirCards.add( jp );

            pane.add( cp, new Integer( 2 ) );
            pane.add( jp, new Integer( 1 ) );
        }

        CardPanel central1 = new CardPanel( centralX + centraldx, centralY );
        CardPanel central2 = new CardPanel( centralX + 2 * centraldx, centralY );
        CardPanel side1 = new CardPanel( centralX, centralY );
        CardPanel side2 = new CardPanel( centralX + 3 * centraldx, centralY );
        deck1 = new CardPanel( 484, 331 );
        deck2 = new CardPanel( 41, 85 );

        pane.add( central1, new Integer( 1 ) );
        pane.add( central2, new Integer( 1 ) );
        pane.add( side1, new Integer( 1 ) );
        pane.add( side2, new Integer( 1 ) );
        pane.add( deck1, new Integer( 1 ) );
        pane.add( deck2, new Integer( 1 ) );

        Timer timer = new Timer( 40, new ActionListener()
        {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                if ( player.state == 2 )
                {
                    win();
                    ( (Timer)e.getSource() ).stop();
                }
                else if ( player.state == 0 )
                {
                    lose();
                    ( (Timer)e.getSource() ).stop();
                }
                else
                {
                    if ( player.isDeck1Empty() )
                    {
                        game.getLayeredPane().remove( deck1 );
                        game.getLayeredPane().revalidate();
                        game.revalidate();
                    }
                    if ( player.isDeck2Empty() )
                    {
                        game.getLayeredPane().remove( deck2 );
                        game.getLayeredPane().revalidate();
                        game.revalidate();
                    }
                    for ( int i = 0; i < 5; i++ )
                    {
                        if ( player.getHand().get( i ).isDeact() )
                        {
                            if(!myCards.get( i ).isDeact()) {
                                myCards.get( i ).setDeact( true );
                                pane.remove( myCards.get( i ) );
                            }
                            // System.out.println(i + " deactivated.");
                            continue;
                        }
                        if(!player.getOppoHand()[i]) {
                            if(!theirCards.get( i ).isDeact()) {
                                theirCards.get( i ).setDeact( true );
                                pane.remove( theirCards.get( i ) );
                            }
                        }
                        if(!player.isStuck()) {
                        	lblStuck.setText("STUCK?");
                        } else if(player.isStuck()) {
                        	lblStuck.setText("Waiting...");
                        }
                        Card c = player.getHand().get( i );
                        myCards.get( i ).setCard( c );
                        myCards.get( i ).setImage();
                        // check collision
                        if ( myCards.get( i ).isCollide1() )
                        {
                            player.out.println( "MOVETOPILE|" + myCards.get( i ).getCard().toString() + "|1" );
                            myCards.get( i ).setCollide1( false );
                        }
                        else if ( myCards.get( i ).isCollide2() )
                        {
                            player.out.println( "MOVETOPILE|" + myCards.get( i ).getCard().toString() + "|2" );
                            myCards.get( i ).setCollide2( false );
                        }
                        if ( !player.isStuck() )
                        {
                            stuckButton.setSelected( false );
                        }
                    }
                    central1.setCard( player.getPile1() );
                    central2.setCard( player.getPile2() );

                    pane.repaint();
                }
            }
        } );

        timer.start();
    }

    private ArrayList<CardPanel> myCards = new ArrayList<CardPanel>();

    private JLabel central1 = new JLabel( "" );

    private JLabel central2 = new JLabel( "" );

    private JFrame game;

    private JLayeredPane pane;

    /**
     * 
     * Shows the WIN window
     */
    public void win()
    {
        JFrame winPage = new JFrame( "YOU WON!" );
        winPage.setSize( 600, 400 );
        winPage.getContentPane().setBackground( new Color( 130, 20, 0 ) );
        winPage.getContentPane().setLayout( null );
        winPage.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        JLabel winLabel = new JLabel( "" );
        winLabel.setBounds( 300 - 434 / 2, 100, 434, 126 );
        winLabel.setIcon( new ImageIcon( GameGUI.class.getResource( "/images/Win.png" ) ) );
        winPage.getContentPane().add( winLabel );

        winPage.setVisible( true );
        game.setVisible( false );
    }

    /**
     * 
     * Shows the LOSE window
     */
    public void lose()
    {
        JFrame losePage = new JFrame( "YOU LOST :(" );
        losePage.setSize( 600, 400 );
        losePage.getContentPane().setBackground( new Color( 130, 20, 0 ) );
        losePage.getContentPane().setLayout( null );
        losePage.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        JLabel loseLabel = new JLabel( "" );
        loseLabel.setBounds( 300 - 520 / 2, 100, 520, 126 );
        loseLabel.setIcon( new ImageIcon( GameGUI.class.getResource( "/images/Lost.png" ) ) );
        losePage.getContentPane().add( loseLabel );

        losePage.setVisible( true );
        game.setVisible( false );
    }
    
    /**
     * 
     * Gets the words in the file and returns as one string
     * @param fileName the name of the file
     * @return words in the file
     * @throws Exception thrown if file not found
     */
    public static String readFileAsString(String fileName)throws Exception 
    { 
      String data = ""; 
      data = new String(Files.readAllBytes(Paths.get(fileName))); 
      return data; 
    }
}
