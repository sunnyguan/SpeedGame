import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;


/**
 * Represents a player
 *
 * @author joyce tung
 * @version May 30, 2019
 * @author Period: 5
 * @author Assignment: SpeedGame
 *
 * @author Sources: None
 */
public class Player
{
    private ArrayList<Card> hand;

    private Card pile1, pile2;

    private boolean stuck = false;

    private ImageIcon imgs[] = new ImageIcon[52];

    private boolean deck1Empty = false;

    private boolean deck2Empty = false;


    /**
     * True if deck1 is empty, false otherwise
     * 
     * @return boolean deck1 empty
     */
    public boolean isDeck1Empty()
    {
        return deck1Empty;
    }


    /**
     * True if deck2 is empty, false otherwise
     * 
     * @return boolean deck2 empty
     */
    public boolean isDeck2Empty()
    {
        return deck2Empty;
    }


    /**
     * Returns pile1
     * 
     * @return Card pile1
     */
    public Card getPile1()
    {
        return pile1;
    }


    /**
     * Returns pile2
     * 
     * @return Card pile2
     */
    public Card getPile2()
    {
        return pile2;
    }


    /**
     * Returns hand
     * 
     * @return Player's hand
     */
    public ArrayList<Card> getHand()
    {
        return hand;
    }

    private String ip;


    /**
     * Constructs a player
     */
    public Player()
    {
        hand = new ArrayList<Card>();
        pile1 = new Card( 0, "NULL" );
        pile2 = new Card( 0, "NULL" );
    }


    /**
     * Adds the player's hand as hand
     * 
     * @param hand
     *            ArrayList<Card> hand
     */
    public void addHand( ArrayList<Card> hand )
    {
        this.hand = hand;
    }


    /**
     * Changes stuck's value
     */
    public void stuck()
    {
        if ( !stuck )
        {
            stuck = true;
            if ( out != null )
            {
                out.println( "STUCK" );
            }
        }
        else
        {
            stuck = false;
            if ( out != null )
            {
                out.println( "UNSTUCK" );
            }
        }
    }


    /**
     * Moves the Card to the given pile
     * 
     * @param c
     *            Card to move
     * @param pileNum
     *            int pile to move to
     */
    public void moveToPile( Card c, int pileNum )
    {
        if ( hand.contains( c ) )
        {
            out.println( "MOVETOPILE|" + c.toString() + "|" + pileNum );
        }
    }

    int refillIndex = 0;


    /**
     * Removes the Card from the hand. Returns true if successfully completed.
     * Returns false if the card isn't in hand.
     * 
     * @param c
     *            Card to remove
     * @return true if successfuly completed, false otherwise.
     */
    public boolean remove( Card c )
    {
        if ( hand.contains( c ) )
        {
            refillIndex = hand.indexOf( c );
            refill();
            return true;
        }
        return false;
    }


    /**
     * returns the value of stuck
     * 
     * @return boolean stuck
     */
    public boolean isStuck()
    {
        return stuck;
    }

    // networking

    String name = "";

    String oppName = "";

    Socket kkSocket;

    PrintWriter out;

    BufferedReader in;


    /**
     * Returns the opponent's name
     * 
     * @return String oppName
     */
    public String getOppName()
    {
        return oppName;
    }


    /**
     * Initializes the player's network
     * 
     * @param ip
     *            String the host's name
     * @param port
     *            int the port's number
     */
    public void initialize( String ip, int port )
        throws UnknownHostException,
        IOException
    {
        String hostName = ip;
        int portNumber = port;

        oppoHand = new boolean[5];
        for ( int i = 0; i < 5; i++ )
        {
            oppoHand[i] = true;
        }

        kkSocket = new Socket( hostName, portNumber );
        out = new PrintWriter( kkSocket.getOutputStream(), true );
        in = new BufferedReader(
            new InputStreamReader( kkSocket.getInputStream() ) );

        startThreads();
    }

    private final boolean TESTING = false;


    /**
     * Starts the server threads
     */
    public void startThreads()
    {
        if ( TESTING )
        {
            Thread userRunner = new Thread( new UserInputRunner() );
            userRunner.start();
        }
        Thread serverRunner = new Thread( new ServerOutputRunner() );
        serverRunner.start();
    }


    /**
     * Tries to initialize
     * 
     * @param ip
     *            String the host's name
     */
    public void init( String ip )
    {
        try
        {
            initialize( ip, 4441 );
        }
        catch ( UnknownHostException e )
        {
            e.printStackTrace();
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }


    /**
     * Prints the refill message
     */
    public void refill()
    {
        if ( out != null )
        {
            out.println( "REFILL|" + refillIndex );
        }
    }


    /**
     * Tests networking.
     *
     * @author Sunny Guan
     * @version May 30, 2019
     * @author Period: 5
     * @author Assignment: SpeedGame
     *
     * @author Sources: None
     */
    class UserInputRunner implements Runnable
    {
        /**
         * Runs UserInputRunner
         */
        @Override
        public void run()
        {
            // for testing purposes only
            Scanner s = new Scanner( System.in );
            String fromUser;

            while ( ( fromUser = s.nextLine() ) != null )
            {
                out.println( fromUser );
            }
            s.close();
        }
    }


    /**
     * Sets the player's name
     * 
     * @param name
     */
    public void setName( String name )
    {
        this.name = name;
        if ( out != null )
        {
            out.println( "NAME|" + name );
        }
    }

    private boolean[] oppoHand;


    /**
     * Returns opponent hand
     * 
     * @return
     */
    public boolean[] getOppoHand()
    {
        return oppoHand;
    }


    /**
     * Player's server. Does what the player tells it to do.
     *
     * @author Sunny Guan
     * @version May 30, 2019
     * @author Period: 5
     * @author Assignment: SpeedGame
     *
     * @author Sources: None
     */
    class ServerOutputRunner implements Runnable
    {
        // receive message from other player
        /**
         * Runs SeverOutputRunner and takes the appropriate action with each
         * message
         */
        @Override
        public void run()
        {
            String fromServer = "";
            try
            {
                while ( ( fromServer = in.readLine() ) != null )
                {
                    System.out.println( "Server: " + fromServer );
                    if ( fromServer.startsWith( "HANDADD" ) )
                    {
                        Card c = new Card( fromServer.split( "\\|" )[1] );
                        if ( hand.size() == 5 )
                        {
                            hand.remove( refillIndex );
                        }
                        hand.add( refillIndex, c );
                        System.out.println( "Updated hand: " + hand );
                    }
                    else if ( fromServer.startsWith( "DEACTCARD" ) )
                    {
                        hand.get( refillIndex ).setDeact( true );
                        deck1Empty = true;
                    }
                    else if ( fromServer.startsWith( "DECKEMPTY2" ) )
                    {
                        deck2Empty = true;
                        int deactIndex = Integer
                            .parseInt( fromServer.split( "\\|" )[1] );
                        oppoHand[deactIndex] = false;
                    }
                    else if ( fromServer.startsWith( "SETPILE" ) )
                    {
                        if ( stuck
                            && fromServer.split( "\\|" ).length == 4 )
                        {
                            stuck = false;
                            System.out.println( "UNSTUCK" );
                        }
                        Card c = new Card( fromServer.split( "\\|" )[1] );
                        if ( fromServer.split( "\\|" )[2].equals( "1" ) )
                        {
                            pile1 = c;
                            System.out
                                .println( "Pile 1: " + pile1.toString()
                                    + "; Pile 2: " + pile2.toString() );
                        }
                        else
                        {
                            pile2 = c;
                            System.out
                                .println( "Pile 1: " + pile1.toString()
                                    + "; Pile 2: " + pile2.toString() );
                        }
                    }
                    else if ( fromServer.startsWith( "HANDREMOVE" ) )
                    {
                        Card c = new Card( fromServer.split( "\\|" )[1] );
                        remove( c );
                        System.out.println( "Updated hand: " + hand );
                    }
                    else if ( fromServer.startsWith( "YOUWIN" ) )
                    {
                        state = 2;
                        // break;
                    }
                    else if ( fromServer.startsWith( "YOULOSE" ) )
                    {
                        state = 0;
                        // break;
                    }
                }
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
        }
    }

    public int state = 1; // 0 = lose, 1 = running, 2 = win

    public int stuckCount = 0;
}
