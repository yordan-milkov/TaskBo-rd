import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class MessageHandler
{
	private	BufferedReader		recived;
	private	PrintWriter			send;
	private	DatabaseConnection	database;

	public MessageHandler(Socket socket) throws IOException, SQLException
	{
		recived		= new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
		send		= new PrintWriter( new OutputStreamWriter( socket.getOutputStream() ) );
		database	= new DatabaseConnection();		
	}
	
	public boolean AceptLoginMessage()
	{
		boolean	success = false;
		
		try
		{
			JSONObject	creadentials	= new JSONObject( recived.readLine() );
			String		username		= creadentials.getString( "UID" );
			String		password		= creadentials.getString( "password" );
			
			JSONObject	result			= new JSONObject();
			if ( database.UserExists( username ) )
			{
				if ( database.LoginUser( username, password ) )
				{
					result.put( "res", "OK" );
					
					Map<String, String>  userData = database.GetUserData();
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
	
	private void DataMapToJSON(Map<String, String> map, JSONObject result) throws JSONException
	{
		for ( Map.Entry<String, String> entry : map.entrySet() )
		{
			result.put( entry.getKey(), entry.getValue() );
		}
	}
}
