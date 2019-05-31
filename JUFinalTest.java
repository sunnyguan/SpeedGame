import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;
import junit.framework.JUnit4TestAdapter;


public class JUFinalTest
{
    private Card c = new Card( 1, "H" );

    private Card c1 = new Card( "1,H" );


    // Card
    @Test
    public void testCardConstructor1()
    {
        assertEquals( c.getValue(), 1 );
        assertEquals( c.getSuite(), "H" );
    }


    @Test
    public void testCardConstructor2()
    {
        assertEquals( c1.getValue(), 1 );
        assertEquals( c1.getSuite(), "H" );
    }


    @Test
    public void testGetValue()
    {
        assertEquals( c.getValue(), 1 );
    }


    @Test
    public void testGetSuite()
    {
        assertEquals( c.getSuite(), "H" );
    }


    @Test
    public void testToString()
    {
        assertEquals( c.toString(), "1,H" );
    }


    @Test
    public void testEquals()
    {
        assertTrue( c.equals( c1 ) );
    }


    @Test
    public void testGetImage()
    {
        assertNotNull( c.getImage() );
    }


    @Test
    public void testHashCode()
    {
        assertNotNull( c.hashCode() );
    }


    @Test
    public void testIsDeact()
    {
        assertFalse( c.isDeact() );
    }


    @Test
    public void testSetDeact()
    {
        c.setDeact( true );
        assertTrue( c.isDeact() );
    }

    // TODO: Speed methods
    private Speed game = new Speed();


    @Test
    public void testSpeedConstructor()
    {
        assertNotNull( game );
    }


    @Test
    public void testAddStuck()
    {
        game.addStuck();
        assertEquals( game.stuckCount, 1 );
    }


    @Test
    public void testFlipSideDeck()
    {
        game.addStuck();
        game.flipSideDeck();
        assertEquals( game.stuckCount, 0 );
    }


    @Test
    public void testGetCentralPile1()
    {
        assertNotNull( game.getCentralPile1() );
    }


    @Test
    public void testGetCentralPile1Card()
    {

        assertNotNull( game.getCentralPile1Card() );
    }


    @Test
    public void testGetCentralPile2()
    {

        assertNotNull( game.getCentralPile2() );
    }


    @Test
    public void testGetCentralPile2Card()
    {

        assertNotNull( game.getCentralPile2Card() );
    }


    @Test
    public void testGetDeck1()
    {

        assertNotNull( game.getDeck1() );
    }


    @Test
    public void testGetDeck2()
    {

        assertNotNull( game.getDeck2() );
    }


    @Test
    public void testGetHand1()
    {

        assertNotNull( game.getHand1() );
    }


    @Test
    public void testGetHand2()
    {

        assertNotNull( game.getHand2() );
    }


    @Test
    public void testGetSideDeck1()
    {

        assertNotNull( game.getSideDeck1() );
    }


    @Test
    public void testGetSideDeck2()
    {
        assertNotNull( game.getSideDeck2() );
    }


    @Test
    public void testRemoveCard()
    {
        Card remove = game.getHand1().get( 0 );
        game.removeCard( 1, remove.toString() );
        assertTrue( !game.getHand1().contains( remove ) );
    }


    @Test
    public void testSet()
    {
        Card set = game.getHand2().get( 0 );
        Card t = game.getCentralPile1Card();
        assertEquals( game.set( 2, set, 1 ),
            Math.abs( set.getValue() - t.getValue() ) == 1
                || Math.abs( set.getValue() - t.getValue() ) == 12 );
    }


    @Test
    public void testSetCentralPile1()
    {
        ArrayList<Card> n = new ArrayList<Card>();
        ArrayList<Card> old = game.getCentralPile1();
        game.setCentralPile1( n );
        assertEquals( n, game.getCentralPile1() );
        game.setCentralPile1( old );
    }


    @Test
    public void testSetCentralPile2()
    {
        ArrayList<Card> n = new ArrayList<Card>();
        ArrayList<Card> old = game.getCentralPile2();
        game.setCentralPile2( n );
        assertEquals( n, game.getCentralPile2() );
        game.setCentralPile2( old );
    }


    @Test
    public void testSetDeck1()
    {
        Stack<Card> n = new Stack<Card>();
        Stack<Card> old = game.getDeck1();
        game.setDeck1( n );
        assertEquals( n, game.getDeck1() );
        game.setDeck1( old );
    }


    @Test
    public void testSetDeck2()
    {
        Stack<Card> n = new Stack<Card>();
        Stack<Card> old = game.getDeck2();
        game.setDeck2( n );
        assertEquals( n, game.getDeck2() );
        game.setDeck2( old );
    }


    @Test
    public void testSetHand1()
    {
        ArrayList<Card> n = new ArrayList<Card>();
        ArrayList<Card> old = game.getHand1();
        game.setHand1( n );
        assertEquals( n, game.getHand1() );
        game.setHand1( old );
    }


    @Test
    public void testSetHand2()
    {
        ArrayList<Card> n = new ArrayList<Card>();
        ArrayList<Card> old = game.getHand2();
        game.setHand2( n );
        assertEquals( n, game.getHand2() );
        game.setHand2( old );
    }

    @Test
    public void testSetSideDeck1()
    {
        Stack<Card> n = new Stack<Card>();
        Stack<Card> old = game.getSideDeck1();
        game.setSideDeck1( n );
        assertEquals( n, game.getSideDeck1() );
        game.setSideDeck1( old );
    }
    
    @Test
    public void testSetSideDeck2()
    {
        Stack<Card> n = new Stack<Card>();
        Stack<Card> old = game.getSideDeck2();
        game.setSideDeck2( n );
        assertEquals( n, game.getSideDeck2() );
        game.setSideDeck2( old );
    }

    //player methods
    Player p = new Player();
    
    @Test
    public void testPlayerConstructor()
    {
        assertNotNull(p);
    }
    
    @Test
    public void testAddHand()
    {
        ArrayList<Card> c = new ArrayList<Card>();
        p.addHand( c );
        assertEquals(p.getHand(), c);
    }

    @Test
    public void testGetHand()
    {
        ArrayList<Card> c = new ArrayList<Card>();
        p.addHand( c );
        assertEquals(p.getHand(), c);
    }
    
    @Test
    public void testGetOppName()
    {
        assertEquals(p.getOppName(), "");
    }
    @Test
    public void testGetOppoHand()
    {
        assertEquals(p.getOppoHand(), null);
    }
    
    @Test
    public void testGetPile1()
    {
        assertNotNull(p.getPile1());
    }
    @Test
    public void testGetPile2()
    {
        assertNotNull(p.getPile2());
    }
    
    
    @Test
    public void testIsDeck1Empty()
    {
        assertFalse(p.isDeck1Empty());
    }
    @Test
    public void testIsDeck2Empty()
    {
        assertFalse(p.isDeck2Empty());
    }
    
    @Test
    public void testRemove()
    {
        ArrayList<Card> hand = new ArrayList<Card>();
        Card card = new Card(1, "H");
        hand.add( card );
        p.addHand( hand );
        assertTrue(p.remove( card ));
    }
    
    @Test
    public void testIsStuck()
    {
        assertFalse(p.isStuck());
    }
    
    @Test
    public void testSetName()
    {
        String s = "Jeff";
        p.setName( s );
        assertEquals(s, p.name);
    }
    
    @Test
    public void testStuck()
    {
        p.stuck();
        assertTrue(p.isStuck());
    }

    public static junit.framework.Test suite()
    {
        return new JUnit4TestAdapter( JUFinalTest.class );
        // NOTE: depends on the name of the JUnit test file
    }


    public static void main( String args[] )
    {
        org.junit.runner.JUnitCore.main( "JUTestVendor" );
    }
}
