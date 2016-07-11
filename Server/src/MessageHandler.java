import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MessageHandler
{
	private	BufferedReader received;
	private	PrintWriter			send;
	private	DatabaseConnection	database;

	public MessageHandler(Socket socket) throws IOException, SQLException
	{
		received = new BufferedReader( new InputStreamReader( socket.getInputStream(), "UTF-8" ) );
		send		= new PrintWriter( new OutputStreamWriter( socket.getOutputStream(), "UTF-8" ) );
		database	= new DatabaseConnection();		
	}
	
	public boolean AcceptLoginMessage()
	{
		boolean	success = false;
		
		try
		{
			JSONObject	credentials	= new JSONObject( received.readLine() );
			String		username		= credentials.getString( "UID" );
			String		password		= credentials.getString( "password" );
			
			JSONObject	result			= new JSONObject();
			if ( database.UserExists( username ) )
			{
				if ( database.LoginUser( username, password ) )
				{
					success = true;
					result.put( "res", "OK" );
					
					Map<String,  String>  userData = database.GetUserData();
					this.DataMapToJSON( userData, result );
				}
				else
				{
					result.put( "res", "Invalid password" );
				}
			}
			else
			{
				result.put("res", "Invalid user");
			}
			send.println( result.toString() );
			send.flush();			
			
		} catch (JSONException | IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return success;
	}
	
	public	Boolean ExecuteMessage()
	{
		Boolean	success = false;
		try
		{
			String	textMessage	= received.readLine();
			success	= (textMessage != null);
			if ( success )
			{
				JSONObject	message	= new JSONObject( textMessage );
				Object	result	= null;
				
				switch ( message.getString( "key" ) )
				{
				case "getGroups":
					result = new JSONArray();
					DataMapListToJSONArray( database.GetUserGroups(), (JSONArray) result );
					break;
					
				case "getIssues":
					result = new JSONArray();
					DataMapListToJSONArray( database.GetIssues( message.getInt( "UID" ) ), (JSONArray) result );
					break;
					
				case "getIssueData":
					result = new JSONObject();
					DataMapToJSON(database.GetIssueData(message.getInt("issueUID")), (JSONObject) result);
					break;
				case "updateIssueData":
					result = new JSONObject();
					JSONObject issueData = message.getJSONObject("issueData");
					if (issueData != null) {
						DataMapToJSON(database.UpdateIssueData(issueData), (JSONObject) result);
					}
					break;
				case "createIssueData":
					result = new JSONObject();
					JSONObject issueData0 = message.getJSONObject("issueData");
					if (issueData0 != null) {
						DataMapToJSON(database.CreateIssueData(issueData0), (JSONObject) result);
					}
					break;
				case "getGroupUsers":
					result = new JSONArray();
					DataMapListToJSONArray( database.GetUsersByGroup( message.getInt( "UID" ) ) , (JSONArray)result);
					break;
					
				case "addCheck":
					result = new JSONObject();
					DataMapToJSON( database.AddCheckItem( message.getInt("issueUID"), message.getString( "name" ) ), (JSONObject) result );
					break;

				case "updateCheckState":
					boolean status = database.UpdateCheck( message.getInt( "checkUID" ), message.getBoolean( "state" ) );
					result = new JSONObject().put("res", status );
					break;

				case "updateIssueState":
					boolean	areUOK  = database.UpdateIsssueStatus( message.getInt("issueUID"), message.getBoolean("state") );
					result = new JSONObject().put( "res", areUOK );
					break;
					
				default:
					break;
				} 
				
				send.println( result.toString() );
				send.flush();
			}
			
		} catch (IOException | JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return success;
	}
	
	private void DataMapToJSON(Map<String, ?> map, JSONObject result) throws JSONException
	{
		for ( Entry<String, ?> entry : map.entrySet() )
		{
			result.put( entry.getKey(), entry.getValue() );
		}
	}

	private void DataMapListToJSONArray(List<Map<String, String>> list, JSONArray result) throws JSONException
	{
		for ( Map<String, String> item : list )
		{
			JSONObject	object	= new JSONObject();
			DataMapToJSON( item, object );
			result.put( object );
		}
	}
}
