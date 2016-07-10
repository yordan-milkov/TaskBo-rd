package milkov.taskbo_rd;

import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Proxy;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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
            InetAddress server  = InetAddress.getByName( "192.168.0.201" );
            //InetAddress server  = InetAddress.getLocalHost();

            if ( socket != null && user.equals( UserData.userInfo.getUserName() ) )
            {
                return "OK";
            }

            socket = new Socket(server, 4444);
            recived	= new BufferedReader( new InputStreamReader( socket.getInputStream(), "UTF-8" ) );
            sent	= new PrintWriter( new OutputStreamWriter( socket.getOutputStream(), "UTF-8" ) );

            JSONObject credentials = new JSONObject();
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
                UserData.userInfo.setUserName( user );
                UserData.userInfo.setDisplayName( result.getString("name") );
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

    public  static String RequestGroups()
    {
        String result = null;
        try {
            JSONObject requestGroup = new JSONObject();
            requestGroup.put("key", "getGroups");
            sent.println(requestGroup.toString());
            sent.flush();

            JSONArray receivedGroups   = new JSONArray( recived.readLine() );

            for ( int i = 0; i < receivedGroups.length(); i++ )
            {
                JSONObject  item    = receivedGroups.getJSONObject( i );
                GroupInfo   info    = new GroupInfo();

                info.setIDnumber    ( item.getInt( "groupUID" ) );
                info.setName        ( item.getString( "name" ) );
                info.setDescription ( item.getString( "description" ) );
                info.setUsers       ( GetUsersByGroup( info.getIDnumber() ) );

                UserData.groupsList.add( info );
            }

        }
        catch ( JSONException | IOException e ) {
        }
        return result;
    }

    public  static String RequestIssuesByGroup( int gropuUID )
    {
        String result = null;
        try {
            JSONObject requestIssues = new JSONObject();
            requestIssues.put("key", "getIssues");
            requestIssues.put("UID", gropuUID );
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
                info.setGroupUID    ( gropuUID );
                info.setResolved    ( item.getInt( "isResolved" ) == 1 );
                issuesList.add( info );
            }

        }
        catch ( JSONException | IOException e ) {
        }
        return result;
    }

    private static List<UserInfo> GetUsersByGroup( int groupID )
    {
        List<UserInfo>  result = null;
        try {
            JSONObject requestUsers = new JSONObject();
            requestUsers.put("key", "getGroupUsers");
            requestUsers.put("UID", groupID);
            sent.println( requestUsers.toString() );
            sent.flush();

            JSONArray receivedUsers   = new JSONArray( recived.readLine() );
            result  = new ArrayList<UserInfo>();
            for ( int i = 0; i < receivedUsers.length(); i++ ) {
                UserInfo    user = new UserInfo();
                JSONObject  item = receivedUsers.getJSONObject( i );
                user.setUserName( item.getString( "UID" ) );
                user.setDisplayName( item.getString( "name" ) );
                result.add( user );
            }

        }
        catch (JSONException|IOException e)
        {

        }
        return result;
    }

    public static IssueData RequestIssueData(IssueInfo info)
    {
        IssueData result = null;
        try {
            JSONObject reqestIssueData = new JSONObject();
            reqestIssueData.put("key", "getIssueData");
            reqestIssueData.put("issueUID", info.getIssueUID() );
            sent.println( reqestIssueData.toString() );
            sent.flush();

            JSONObject recivedData  = new JSONObject( recived.readLine() );
            result  = new IssueData(info);
            JSONArray checkList = recivedData.getJSONArray("checkList");
            for ( int i = 0; i < checkList.length(); i++ )
            {
                JSONObject  itemObject  = checkList.getJSONObject( i );
                CheckItem   checkItem   = new CheckItem();
                checkItem.description   = itemObject.getString("name");
                checkItem.isChecked     = itemObject.getInt("isFinished") != 0;
                checkItem.chekcID       = itemObject.getInt("checkUID");
                result.AddCheck(checkItem);
            }

            List<UserInfo>  groupUsers = null;
            for ( GroupInfo curr :  UserData.groupsList )
            {
                if ( curr.getIDnumber() == info.getGroupUID() )
                {
                    groupUsers = curr.getUsers();
                    break;
                }
            }

            JSONArray userList  = recivedData.getJSONArray("userList");
            for ( int i = 0; i < userList.length(); i++ )
            {
                String   userUID = userList.getString(i);
                for ( UserInfo user : groupUsers )
                {
                    String shittyJava = user.getUserName();
                    if ( userUID.equals( shittyJava ) )
                    {
                        result.AddParticipant(user);
                        break;
                    }
                }
            }

        }
        catch (JSONException | IOException e)
        {

        }
        return result;
    }

    public static boolean AddCheckData(CheckItem info, int issueUID)
    {
        boolean result = false;
        try {
            JSONObject addCheck = new JSONObject();
            addCheck.put("key","addCheck");
            addCheck.put("issueUID", issueUID );
            addCheck.put("name", info.description);
            sent.println( addCheck.toString() );
            sent.flush();

            JSONObject recivedData  = new JSONObject( recived.readLine() );
            info.chekcID = recivedData.getInt("GENERATED_KEY");
            result  = true;
        }
        catch (JSONException|IOException e)
        {
        }
        return result;
    }

    public static boolean UpdateCheckData(int checkUID, boolean state) {
        boolean result = false;
        try {
            JSONObject updateCheck = new JSONObject();

            updateCheck.put("key", "updateCehckState");
            updateCheck.put("checkUID", checkUID);
            updateCheck.put("state", state);
            sent.println(updateCheck.toString());
            sent.flush();

            JSONObject recivedData = new JSONObject(recived.readLine());
            result = recivedData.getBoolean("res");
        } catch (JSONException | IOException e) {
        }
        return result;
    }

    public static int AddParticipantData(String name, int issueUID)
    {
        int result = 0;
        try {
            JSONObject addParticipant = new JSONObject();
            addParticipant.put("key","addParticipant");
            addParticipant.put("issueUID", issueUID );
            addParticipant.put("name", name);
            sent.println( addParticipant.toString() );
            sent.flush();

            JSONObject recivedData  = new JSONObject( recived.readLine() );
            result = recivedData.getInt("res");
        }
        catch (JSONException|IOException e)
        {
        }
        return result;
    }

    public static boolean UpdateIssueStatus(int issueUID, boolean status)
    {
        boolean result = false;
        try {
            JSONObject updateState = new JSONObject();

            updateState.put("key", "updateIssueState");
            updateState.put("issueUID", issueUID);
            updateState.put("state", status);
            sent.println(updateState.toString());
            sent.flush();

            JSONObject recivedData = new JSONObject(recived.readLine());
            result = recivedData.getBoolean("res");
        }
        catch (JSONException | IOException e) {
        }
        return result;
    }
}
