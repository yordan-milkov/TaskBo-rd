import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	
	public	List< Map<String, String> >	GetUserGroups()
	{
		List< Map<String, String> > list = null;
		
		try
		{
			String	query	= "SELECT groupusers.groupUID, groups.name, groups.description FROM groupusers INNER JOIN groups ON groupusers.groupUID = groups.UID WHERE groupusers.userUID = '" + userUID + "'";
			PreparedStatement getUserData;
			getUserData = sqlConnection.prepareStatement( query );
			ResultSet result = getUserData.executeQuery();
			list = ResultToMapList( result );
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	public	List< Map<String, String> >	GetIssues(int groupUID)
	{
		List< Map<String, String> > list = null;
		/*
		try
		{
			String	query	= "SELECT groupusers.groupUID, groups.name, groups.description FROM groupusers INNER JOIN groups ON groupusers.groupUID = groups.UID WHERE groupusers.userUID = '" + userUID + "'";
			PreparedStatement getUserData;
			getUserData = sqlConnection.prepareStatement( query );
			ResultSet result = getUserData.executeQuery();
			list = ResultToMapList( result );
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		list = new ArrayList<Map<String, String>>();
		Map<String, String>	i1 = new HashMap<>();
		Map<String, String> i2 = new HashMap<>();
		Map<String, String> i3 = new HashMap<>();
		i1.put("name", "alabala");i1.put("description", "kikiriki");i1.put( "issueUID", "123" );
		i2.put("name", "asdasdadd");i2.put("description", "dxghvjbnk");i2.put( "issueUID", "77" );
		i3.put("name", "kkjklkj");i3.put("description", "jhhgfgvbn");i3.put( "issueUID", "154" );

		list.add( i1 );
		list.add( i2 );
		list.add( i3 );
		
		
		return list;
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
	
	private	List< Map<String, String> > ResultToMapList( ResultSet result ) throws SQLException
	{
		ArrayList< Map<String, String> > list = new ArrayList< Map<String, String> >();
		while ( result.next() )
		{
			list.add( this.ReultSetRowToMap( result ) );
		}
		
		return list;
	}
}
