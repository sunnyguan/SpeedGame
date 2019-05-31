import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.util.Scanner;

/**
 * 
 *  Server for the game speed
 *
 *  @author  Sunny Guan
 *  @version May 30, 2019
 *  @author  Period: 5
 *  @author  Assignment: SpeedGame
 *
 *  @author  Sources: none
 */
public class SpeedServer
{

    public static final int PORT = 4441;

    private static boolean listening = true;

    /**
     * 
     * Starts the server
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

        SpeedServer ss = new SpeedServer();
        ServerInputRunner sir = ss.new ServerInputRunner();
        Thread t = new Thread( sir );
        t.start();

        try (ServerSocket serverSocket = new ServerSocket( PORT ))
        {
            /*SpeedServerThread mst = new SpeedServerThread( serverSocket.accept() );
            mst.start();
            SpeedServerThread mst2 = new SpeedServerThread( serverSocket.accept() );
            mst2.start();
            mst.otherPlayer = mst2;
            mst2.otherPlayer = mst;*/
        }
        catch ( IOException e )
        {
            System.err.println( "Could not listen on port " + PORT );
            System.exit( -1 );
        }
    }

    /**
     * 
     *  This gets the input from the server 
     *
     *  @author  Sunny Guan
     *  @version May 30, 2019
     *  @author  Period: 5
     *  @author  Assignment: SpeedGame
     *
     *  @author  Sources: none
     */
    class ServerInputRunner implements Runnable
    {

        /**
         * What to do when serverinput is running
         */
        @Override 
        public void run()
        {
            Scanner s = new Scanner( System.in );
            String fromUser = s.nextLine();
            while ( !fromUser.equals( null ) )
            {
                if ( fromUser.toLowerCase().equals( "quit" ) || fromUser.toLowerCase().equals( "exit" ) )
                {
                    listening = false;
                    break;
                }
                else
                {
                    System.out.println( "Type \"quit\" or \"exit\" to stop the server." );
                }
                fromUser = s.nextLine();
            }
        }
    }

    /**
     * 
     * Returns the IP 
     * @return IP 
     */
    public static String getIP()
    {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command( "cmd.exe", "/c", "ipconfig" );

        try
        {

            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader( new InputStreamReader( process.getInputStream() ) );

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