package milkov.taskbo_rd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Proxy;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.*;


public class ConnectionManager {

    private static Socket          socket;
    private static BufferedReader  recived;
    private static PrintWriter     sent;
    private static String          status;


    public  static String  SetupConnection(String user, String password)
    {
        String      resolution  = "fail";
        try {
            InetAddress server  = InetAddress.getByName( "192.168.0.100" );

            socket = new Socket(server, 4444);
            recived	= new BufferedReader( new InputStreamReader( socket.getInputStream(), "UTF-8" ) );
            sent	= new PrintWriter( new OutputStreamWriter( socket.getOutputStream(), "UTF-8" ) );

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

        }
        catch ( Exception e)
        {
            e.printStackTrace();
        }

        return resolution;
    }

    public  static String RequestGroupds()
    {
        String result = null;
        try {
            JSONObject requestGroup = new JSONObject();
            requestGroup.put("key", "getGroups");
            sent.println(requestGroup.toString());
            sent.flush();

            JSONArray recivedGroups   = new JSONArray( recived.readLine() );

            for ( int i = 0; i < recivedGroups.length(); i++ )
            {
                JSONObject  item    = recivedGroups.getJSONObject( i );
                GroupInfo   info    = new GroupInfo();

                info.setIDnumber    ( item.getInt( "groupUID" ) );
                info.setName        ( item.getString( "name" ) );
                info.setDescription ( item.getString( "description" ) );
                UserData.groupsList.add( info );
            }

        }
        catch ( JSONException | IOException e ) {
        }
        return result;
    }

    public  static String RequestIssuesByGroup( Integer gropuUID )
    {
        String result = null;
        try {
            JSONObject requestIssues = new JSONObject();
            requestIssues.put("key", "getIssues");
            requestIssues.put("UID", gropuUID.toString() );
            sent.println( requestIssues.toString() );
            sent.flush();

            ArrayList   issuesList  = UserData.issuesMap.get( gropuUID );
            if ( issuesList == null )
            {
                issuesList  = new ArrayList();
                UserData.issuesMap.put( gropuUID, issuesList );
            }

            JSONArray receivedIssues   = new JSONArray( recived.readLine() );
            for ( int i = 0; i < receivedIssues.length(); i++ )
            {
                JSONObject  item    = receivedIssues.getJSONObject( i );
                IssueInfo   info    = new IssueInfo();

                info.setIssueUID    ( item.getInt( "issueUID" ) );
                info.setName        ( item.getString( "name" ) );
                info.setDescription ( item.getString( "description" ) );
                issuesList.add( info );
            }

        }
        catch ( JSONException | IOException e ) {
        }
        return result;
    }
}
