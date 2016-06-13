import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DatabaseConnection
{
	private Connection	sqlConnection;
	private	String		userUID;
	
	public	DatabaseConnection() throws SQLException
	{
		String url = "jdbc:mysql://localhost:3306/taskbo-rd";
		sqlConnection = DriverManager.getConnection( url, "root", "" );
		userUID = "";
	}
	
	public	boolean	UserExists( String user )
	{
		boolean success	= false;
		try
		{
			String	query	= "SELECT UID FROM users WHERE UID='" + user + "'"; 
			PreparedStatement findUser;
			findUser = sqlConnection.prepareStatement( query );
			ResultSet	result	= findUser.executeQuery();
			success		= result.next();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return success;
	}
	
	public	boolean	LoginUser(String user, String password)
	{
		boolean	success = false;
		try
		{
			String	query	= "SELECT password FROM users WHERE UID='" + user + "'"; 
			PreparedStatement	findUser = sqlConnection.prepareStatement( query );
			ResultSet	result	= findUser.executeQuery();
			
			if ( result.next() )
			{
				if ( password.equals( result.getString( "password" ) ) )
				{
					userUID	= user;
					success = true;
				}
			}
			
			
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return success;
	}
	
	public Map<String, String> GetUserData()
	{
		Map<String, String> dataArray = null;
		try
		{
			String	query	= "SELECT name, GSM, mail FROM users WHERE UID = '" + userUID + "'";
			PreparedStatement getUserData = sqlConnection.prepareStatement( query );
			ResultSet result = getUserData.executeQuery();
			if ( result.next() )
			{
				dataArray = this.ReultSetRowToMap( result );
			}
			
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataArray;
	}
	
	
	private Map<String, String> ReultSetRowToMap( ResultSet result ) throws SQLException
	{
		Map<String, String> dataAray = new HashMap<String, String>();
		ResultSetMetaData metadata = result.getMetaData();
		int columns	= metadata.getColumnCount();
		for ( int i = 1; i <= columns; i++ )
		{
			dataAray.put( metadata.getColumnName(i), result.getString(i) );
		}
		
		return dataAray;
	}
}
