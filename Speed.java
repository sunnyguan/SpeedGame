
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Stack;


/**
 *  Represents a game of speed
 *
 *  @author  joyce tung
 *  @version May 30, 2019
 *  @author  Period: 5
 *  @author  Assignment: SpeedGame
 *
 *  @author  Sources: None
 */
public class Speed
{

    private ArrayList<Card> deck;

    private ArrayList<Card> centralPile1 = new ArrayList<Card>( 52 ),
                    centralPile2 = new ArrayList<Card>( 52 );

    private Stack<Card> sideDeck1 = new Stack<Card>(),
                    sideDeck2 = new Stack<Card>();

    private ArrayList<Card> hand1, hand2;

    private Stack<Card> deck1, deck2;


    /**
     * Initializes the deck
     */
    private void initDeck()
    {
        deck = new ArrayList<Card>();
        for ( int i = 1; i <= 13; i++ )
        {
            deck.add( new Card( i, "H" ) );
            deck.add( new Card( i, "C" ) );
            deck.add( new Card( i, "D" ) );
            deck.add( new Card( i, "S" ) );
        }
    }


    /**
     * Constructor for Speed. Sets up the game by shuffling and distributing
     * cards.
     */
    public Speed()
    {
        initDeck();
        Collections.shuffle( deck );
        hand1 = new ArrayList<Card>( 5 );
        hand2 = new ArrayList<Card>( 5 );
        deck1 = new Stack<Card>();
        deck2 = new Stack<Card>();

        for ( int i = 0; i < 5; i++ )
        {
            Card c1 = deck.get( 4 * i );
            Card c2 = deck.get( 4 * i + 1 );

            hand1.add( c1 );
            hand2.add( c2 );

            sideDeck1.push( deck.get( 4 * i + 2 ) );
            sideDeck2.push( deck.get( 4 * i + 3 ) );

        }

        for ( int i = 0; i < 15; i++ )
        {
            deck1.push( deck.get( 2 * i + 20 ) );
            deck2.push( deck.get( 2 * i + 21 ) );
        }

        centralPile1.add( deck.get( 50 ) );
        centralPile2.add( deck.get( 51 ) );
    }


    /**
     * Tries to set the card on the given pile. Returns true if the action is
     * successfully completed, false otherwise
     * 
     * @param id
     *            int player
     * @param p
     *            Player card is from
     * @param c
     *            Card to be set
     * @param pile
     *            must be either 1 or 2. 1 corresponds to the left pile
     * @return boolean true if the card can be set, false otherwise
     */
    public boolean set( int id, Card c, int pile )
    {
        ArrayList<Card> cPile = centralPile2;
        if ( pile == 1 )
        {
            cPile = centralPile1;
        }
        Card c1 = cPile.get( cPile.size() - 1 );
        if ( Math.abs( c1.getValue() - c.getValue() ) == 1
            || Math.abs( c1.getValue() - c.getValue() ) == 12 )
        {
            cPile.add( c );
            return true;
        }

        return false;
    }

    public int stuckCount = 0;


    /**
     * Removes the card from the hand of the player given by id
     * 
     * @param id
     *            int player
     * @param card
     *            String card to remove
     */
    public void removeCard( int id, String card )
    {
        Card c = new Card( card );
        if ( id == 1 )
        {
            hand1.remove( c );
        }
        else if ( id == 2 )
        {
            hand2.remove( c );
        }
    }


    /**
     * Returns deck1, player1's deck
     * 
     * @return Stack<Card> deck1
     */
    public Stack<Card> getDeck1()
    {
        return deck1;
    }


    /**
     * Sets player1's deck to deck1
     * 
     * @param deck1
     *            Stack<Card> deck
     */
    public void setDeck1( Stack<Card> deck1 )
    {
        this.deck1 = deck1;
    }


    /**
     * Returns deck2, player2's deck
     * 
     * @return Stack<Card> deck2
     */
    public Stack<Card> getDeck2()
    {
        return deck2;
    }


    /**
     * Sets player2's deck to deck1
     * 
     * @param deck2
     *            Stack<Card> deck
     */
    public void setDeck2( Stack<Card> deck2 )
    {
        this.deck2 = deck2;
    }


    /**
     * adds one to Stuck
     */
    public void addStuck()
    {
        stuckCount++;
    }


    /**
     * Replaces the cards on the central piles with the cards from the side
     * decks. If the side decks are empty, shuffles the central piles.
     * Should only be done when both players are stuck
     * 
     * @return boolean true if completed
     */
    public boolean flipSideDeck()
    {
        if ( !sideDeck1.isEmpty() )
        {
            centralPile1.add( sideDeck1.pop() );
            centralPile2.add( sideDeck2.pop() );
        }
        else
        {
            // May need it to be more efficient
            Collections.shuffle( centralPile1 );
            Collections.shuffle( centralPile2 );
            sideDeck1.addAll( centralPile1 );
            sideDeck2.addAll( centralPile2 );
            centralPile1.add( sideDeck1.pop() );
            centralPile2.add( sideDeck2.pop() );
        }
        stuckCount = 0;
        return true;
    }


    /**
     * Returns the top card of the first central pile
     * 
     * @return Card the top card of the first central pile
     */
    public Card getCentralPile1Card()
    {
        return centralPile1.get( centralPile1.size() - 1 );
    }


    /**
     * Returns the top card of the second central pile
     * 
     * @return Card the top of the second central pile
     */
    public Card getCentralPile2Card()
    {
        return centralPile2.get( centralPile2.size() - 1 );
    }


    /**
     * Runs the game
     * 
     * @param args
     *            String[] does nothing, part of the main class format
     */
    public static void main( String[] args )
    {
        Speed s = new Speed();
        s.startServer();
    }

    public static final int PORT = 4441;

    private static final boolean TESTING = false;


    /**
     * Starts the Servers
     */
    public void startServer()
    {
        String ip = getIP();
        if ( ip.equals( "ERR" ) )
        {
            System.out.println( "Could not get IP from command line. " );
            System.exit( -1 );
        }
        System.out.println( "Listening on IP: " + ip + ":" + PORT );

        if ( TESTING )
        {
            ServerInputRunner sir = new ServerInputRunner();
            Thread t = new Thread( sir );
            t.start();
        }

        try (ServerSocket serverSocket = new ServerSocket( PORT ))
        {
            SpeedServerThread mst = new SpeedServerThread( serverSocket
                .accept(), this, 1 );
            mst.start();
            System.out.println( "Connected! Waiting for name..." );

            SpeedServerThread mst2 = new SpeedServerThread( serverSocket
                .accept(), this, 2 );
            mst2.start();
            System.out.println( "Connected! Waiting for name..." );

            mst.setOtherPlayer( mst2 );
            mst2.setOtherPlayer( mst );
        }
        catch ( IOException e )
        {
            System.err.println( "Could not listen on port " + PORT );
            System.exit( -1 );
        }
    }


    /**
     *  Takes input from the user.
     *
     *  @author  Sunny Guan
     *  @version May 30, 2019
     *  @author  Period: 5
     *  @author  Assignment: SpeedGame
     *
     *  @author  Sources: None
     */
    class ServerInputRunner implements Runnable
    {

        /**
         * Takes user input until the user exits
         */
        @Override
        public void run()
        {
            Scanner s = new Scanner( System.in );
            String fromUser = s.nextLine();
            while ( !fromUser.equals( null ) )
            {
                if ( fromUser.toLowerCase().equals( "quit" )
                    || fromUser.toLowerCase().equals( "exit" ) )
                {
                    break;
                }
                else
                {
                    System.out.println(
                        "Type \"quit\" or \"exit\" to stop the server." );
                }
                fromUser = s.nextLine();
            }
            s.close();
        }
    }


    /**
     * Returns the first central pile
     * 
     * @return ArrayList<Card> centralPile1
     */
    public ArrayList<Card> getCentralPile1()
    {
        return centralPile1;
    }


    /**
     * Sets the first central pile to centralPile1
     * 
     * @param centralPile1 ArrayList<Card> pile
     */
    public void setCentralPile1( ArrayList<Card> centralPile1 )
    {
        this.centralPile1 = centralPile1;
    }


    /**
     * Returns the second central pile
     * 
     * @return ArrayList<Card> centralPile2
     */
    public ArrayList<Card> getCentralPile2()
    {
        return centralPile2;
    }


    /**
     * Sets the the second central pile to centralPile2
     * 
     * @param centralPile2 ArrayList<Card> pile
     */
    public void setCentralPile2( ArrayList<Card> centralPile2 )
    {
        this.centralPile2 = centralPile2;
    }


    /**
     * Returns the first sideDeck
     * 
     * @return Stack<Card> sideDeck
     */
    public Stack<Card> getSideDeck1()
    {
        return sideDeck1;
    }


    /**
     * Sets the first sideDeck to sideDeck1
     * 
     * @param sideDeck1 Stack<Card> first sideDeck
     */
    public void setSideDeck1( Stack<Card> sideDeck1 )
    {
        this.sideDeck1 = sideDeck1;
    }


    /**
     * Returns the second sideDeck
     * 
     * @return Stack<Card> sideDeck2
     */
    public Stack<Card> getSideDeck2()
    {
        return sideDeck2;
    }


    /**
     * Sets the second side deck to sideDeck2
     * 
     * @param sideDeck2 Stack<Card> second sideDeck
     */
    public void setSideDeck2( Stack<Card> sideDeck2 )
    {
        this.sideDeck2 = sideDeck2;
    }


    /**
     * Returns player1's hand
     * 
     * @return ArrayList<Card> player1's hand
     */
    public ArrayList<Card> getHand1()
    {
        return hand1;
    }


    /**
     * Sets player1's hand to hand1
     * 
     * @param hand1 ArrayList<Card> hand
     */
    public void setHand1( ArrayList<Card> hand1 )
    {
        this.hand1 = hand1;
    }


    /**
     * Returns player2's hand
     * 
     * @return ArrayList<Card> player2's hand
     */
    public ArrayList<Card> getHand2()
    {
        return hand2;
    }


    /**
     * Sets player2's hand to hand2
     * 
     * @param hand2 ArrayList<Card> hand
     */
    public void setHand2( ArrayList<Card> hand2 )
    {
        this.hand2 = hand2;
    }


    /**
     * Returns the IP address
     * 
     * @return String IP address
     */
    public static String getIP()
    {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command( "cmd.exe", "/c", "ipconfig" );

        try
        {

            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(
                new InputStreamReader( process.getInputStream() ) );

            String line;
            while ( ( line = reader.readLine() ) != null )
            {
                if ( line.contains( "IPv4" ) )
                {
                    return line.split( ": " )[1];
                }
            }

        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        return "ERR";
    }
}
