import javax.swing.ImageIcon;


/**
 * Represents a card. The value is the number of the card. An Ace is 1, a King
 * is 13, etc. The suite is a capital letter. Hearts is H, Spades is S, Clovers
 * is C, Diamonds is D
 *
 * @author joyce tung
 * @version May 30, 2019
 * @author Period: 5
 * @author Assignment: SpeedGame
 *
 * @author Sources: None
 */
public class Card
{
    private int value;

    private String suite;

    private ImageIcon img;

    private boolean deact = false;


    /**
     * Returns true if the card is deactivated
     * 
     * @return true if deactivated, false otherwise
     */
    public boolean isDeact()
    {
        return deact;
    }


    /**
     * Sets the deactivated to deact
     * 
     * @param deact
     *            boolean new value for deactivated boolean field
     */
    public void setDeact( boolean deact )
    {
        this.deact = deact;
    }


    /**
     * Constructs a Card
     * 
     * @param val
     *            int value of card (An Ace is 1, a King is 13, etc.)
     * @param suite
     *            String representation of suite. Must be H, S, C, or D.
     */
    public Card( int val, String suite )
    {
        value = val;
        this.suite = suite;
    }


    /**
     * Constructs a Card
     * 
     * @param toString
     *            String representation of card. Must be in the form value,
     *            suite. Example: the Ace of Hearts as toString will be "1,H"
     */
    public Card( String toString )
    {
        String[] split = toString.split( "," );
        value = Integer.parseInt( split[0] );
        suite = split[1];
    }


    /**
     * returns the value of the card
     * 
     * @return int card's value
     */
    public int getValue()
    {
        return value;
    }


    /**
     * returns the suite
     * 
     * @return String suite
     */
    public String getSuite()
    {
        return suite;
    }


    /**
     * Returns card as a String
     */
    public String toString()
    {
        return value + "," + suite;
    }


    /**
     * Finds the image for the card and returns it.
     * 
     * @return ImageIcon the card's image
     */
    public ImageIcon getImage()
    {
        if ( img == null )
        {
            String imageURI = "/images/" + value + suite + ".png";
            img = new ImageIcon( imageURI );
        }
        return img;
    }


    /**
     * Returns the hashCode version of the card
     * 
     * @return int hashCode representation of the card
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
            + ( ( suite == null ) ? 0 : suite.hashCode() );
        result = prime * result + value;
        return result;
    }


    /**
     * Returns true if obj is equivalent to this card. False otherwise.
     * 
     * @param obj
     *            Object to be compared to.
     * @return boolean true if the card equals object
     */
    @Override
    public boolean equals( Object obj )
    {
        if ( this == obj )
        {
            return true;
        }
        if ( obj == null )
        {
            return false;
        }
        if ( getClass() != obj.getClass() )
        {
            return false;
        }
        Card other = (Card)obj;
        if ( suite == null )
        {
            if ( other.suite != null )
            {
                return false;
            }
        }
        else if ( !suite.equals( other.suite ) )
            return false;
        if ( value != other.value )
            return false;
        return true;
    }

}