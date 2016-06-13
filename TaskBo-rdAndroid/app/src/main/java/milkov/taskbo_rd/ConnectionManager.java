package milkov.taskbo_rd;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import org.json.*;


public class ConnectionManager {

    private static Socket          socket;
    private static BufferedReader  recived;
    private static PrintWriter     sent;
    private static String          status;

    public class gga
    {

    }

    public  static String  SetupConnection(String user, String password)
    {
        String      resolution  = null;
        try {
            InetAddress server  = InetAddress.getByName( "192.168.0.103" );

            socket = new Socket(server, 4444);
            recived	= new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
            sent	= new PrintWriter( new OutputStreamWriter( socket.getOutputStream() ) );

            JSONObject credentials = new JSONObject();;
            try {
                credentials.put("UID", user);
                credentials.put("password", password);
            }
            catch (JSONException e) {

            }

            sent.println( credentials.toString() );
            sent.flush();

            JSONObject  result      = new JSONObject( recived.readLine() );
            resolution  = result.getString("res");
            if ( resolution.equals("OK") ) {
                UserData.userName = user;
                UserData.displayName = result.getString("name");
                UserData.mail = result.getString("mail");
                UserData.GSM = result.getString("GSM");
            }

        } catch ( Exception e)
        {

        }

        return resolution;
    }
}
