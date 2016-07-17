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
import java.util.StringTokenizer;

import com.mysql.jdbc.Statement;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DatabaseConnection
{
	private Connection	sqlConnection;
	private	String		userUID;
	
	public	DatabaseConnection() throws SQLException
	{
		//		String url = "jdbc:mysql://localhost:3306/taskbo-rd";
		String url = "jdbc:mysql://192.168.0.200:3306/taskbo-rd?characterEncoding=UTF-8";
		sqlConnection = DriverManager.getConnection( url, "root", "raspy" );
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
				dataArray = this.ResultSetRowToMap( result );
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
	
	public	List< Map<String, String> > GetUsersByGroup(int groupUID)
	{
		List< Map<String, String> > list = null;
		try
		{
			String	query	= "SELECT users.name, users.UID FROM groupusers INNER JOIN users ON groupusers.userUID = users.UID WHERE groupusers.groupUID = '" + groupUID + "'";
			PreparedStatement getUsersData;
			getUsersData = sqlConnection.prepareStatement( query );
			ResultSet result = getUsersData.executeQuery();
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
		try
		{
			String	query	= "SELECT name, description, issueUID, isResolved FROM issues WHERE groupUID = '" + groupUID + "'";
			PreparedStatement getUserData = sqlConnection.prepareStatement( query );
			ResultSet result = getUserData.executeQuery();
			list = ResultToMapList( result );
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public Map<String, ?> GetIssueData(int issueUID) {
		Map<String, ?> result = null;
		try
		{
			String	query	= "SELECT name, description, issueUID, users FROM issues WHERE issueUID = '" + issueUID + "'";
			PreparedStatement getUserData = sqlConnection.prepareStatement( query );
			ResultSet queryResult = getUserData.executeQuery();
			queryResult.next();
			result = ResultSetRowToMap( queryResult );

			getChecklist(result);
			getParticipantsList(result);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public Map<String, Boolean> UpdateIssueData(JSONObject issueData) throws JSONException {
		Map resultMap = new HashMap();
		boolean	result = false;
		try
		{
			String users = updateParticipantsList(issueData.getJSONArray("users"));
			String query = "UPDATE issues SET name = '" + issueData.getString("name") + "', description = '"+issueData.getString("description") +"', users = '"+users+"' WHERE issueUID = " + issueData.getInt("issueUID");
			PreparedStatement updateIssue = sqlConnection.prepareStatement( query );
			int affectedRows = updateIssue.executeUpdate();
			if (affectedRows != 0) {
				updateCheckList(issueData.getInt("issueUID"), issueData.getJSONArray("checks"));
				result = true;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		resultMap.put("result", result);
		return resultMap;
	}

	public Map<String, ?> CreateIssueData(JSONObject issueData) throws JSONException {
		Map resultMap = new HashMap();
		boolean	result = false;
		try
		{
			String users = updateParticipantsList(issueData.getJSONArray("users"));

			String query ="INSERT INTO issues (name, description, users, groupUID)"
					+ "VALUES ('" + issueData.getString("name") + "', '" + issueData.get("description") + "','" + users + "', '"+issueData.getString("groupUID")+"')";

			PreparedStatement updateIssue = sqlConnection.prepareStatement( query, Statement.RETURN_GENERATED_KEYS );
			updateIssue.executeUpdate();
			ResultSet	keys	= updateIssue.getGeneratedKeys();
			if ( keys.next() )
			{
				resultMap = ResultSetRowToMap( keys );
				result = true;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		resultMap.put("result", result);
		return resultMap;
	}

	public Map<String, String> AddCheckItem(int issueID, String name)
	{
		Map<String, String> dataArray = null;
		try
		{
			String	query	= "INSERT INTO checks (issueUID, name, isFinished, checkUID)"
					+ "VALUES ('" + issueID + "', '" + name + "', '0', NULL);";
			PreparedStatement addCheck = sqlConnection.prepareStatement( query, Statement.RETURN_GENERATED_KEYS );
			addCheck.executeUpdate();
			ResultSet	keys	= addCheck.getGeneratedKeys();
			if ( keys.next() )
			{
				dataArray = ResultSetRowToMap( keys );
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return dataArray;
	}

	private void updateCheckList(int issueUID, JSONArray checks) {
		try {
			String query = "SELECT checkUID FROM checks WHERE issueUID = '" + issueUID + "'";
			PreparedStatement getUserData = sqlConnection.prepareStatement( query );
			ResultSet queryResult = getUserData.executeQuery();
			
			List< Map<String, String> > checkUIDsMapList = ResultToMapList( queryResult );
			Map<Integer, JSONObject> checksMapping = toChecksMapping(checks);
			
			for (Map<String, String> checkUIDMap : checkUIDsMapList) {
				PreparedStatement updateCheck;
				int checkUID = Integer.valueOf(checkUIDMap.get("checkUID"));
				JSONObject issueData = checksMapping.get(checkUID);
				if (issueData != null) {
					query = "UPDATE checks SET name = '" + issueData.getString("name") + "' WHERE checkUID = " + checkUID;
				} else {
					query = "DELETE FROM checks WHERE checkUID = "+checkUID;
				}
				updateCheck = sqlConnection.prepareStatement(query);
				updateCheck.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void getChecklist(Map result) {
		try
		{
			String	query	= "SELECT checkUID, name, isFinished FROM checks WHERE issueUID = '" + result.get("issueUID") + "'";
			PreparedStatement getUserData = sqlConnection.prepareStatement( query );
			ResultSet queryResult = getUserData.executeQuery();
			List< Map<String, String> > list = ResultToMapList( queryResult );
			result.put("checks", list);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}


	private Map<Integer, JSONObject> toChecksMapping(JSONArray jsonArray) {
		Map mapping = new HashMap();
		if (jsonArray != null) {
			int len = jsonArray.length();
			for (int i=0;i<len;i++){
				try {
					JSONObject jsonCheck = (JSONObject)jsonArray.get(i);
					mapping.put(jsonCheck.getInt("checkUID"), jsonCheck);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		return mapping;
	}

	private String updateParticipantsList(JSONArray participants) throws JSONException {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < participants.length(); i++) {
			String participant = (String ) participants.get(i);
			stringBuilder.append(",");
			stringBuilder.append(participant);
		}
		return stringBuilder.toString();
	}

	private void getParticipantsList(Map result) {
		String users = (String) result.get("users");
		StringTokenizer	userTokens	= new StringTokenizer( users, "," );
		List<String> usersList = new ArrayList<>();
		while( userTokens.hasMoreTokens() )
		{
			usersList.add( userTokens.nextToken() );
		}
		result.put( "users", usersList );
	}

	public boolean UpdateCheck(int checkUID, boolean state)
	{
		boolean	result = false;
		try
		{
			String	query	= "UPDATE checks SET isFinished = '" + (state ? "1" : "0") + "' WHERE checkUID = " + checkUID;
			PreparedStatement updateCheck = sqlConnection.prepareStatement( query );
			updateCheck.executeUpdate();
			result = true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return result;
	}


	public boolean UpdateIsssueStatus( int issueUID, boolean status )
	{
		boolean	result = false;
		try
		{
			String	query	= "UPDATE issues SET isResolved = '" + (status ? "1" : "0") + "' WHERE issueUID = " + issueUID;
			PreparedStatement updateCheck = sqlConnection.prepareStatement( query );
			updateCheck.executeUpdate();
			result = true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return result;
	}

	private Map<String, String> ResultSetRowToMap(ResultSet result) throws SQLException
	{
		Map<String, String> dataArray = new HashMap<String, String>();
		ResultSetMetaData metadata = result.getMetaData();
		int columns	= metadata.getColumnCount();
		for ( int i = 1; i <= columns; i++ )
		{
			dataArray.put( metadata.getColumnName(i), result.getString(i) );
		}

		return dataArray;
	}

	private	List< Map<String, String> > ResultToMapList( ResultSet result ) throws SQLException
	{
		ArrayList< Map<String, String> > list = new ArrayList< Map<String, String> >();
		while ( result.next() )
		{
			list.add( this.ResultSetRowToMap( result ) );
		}
		return list;
	}
}
