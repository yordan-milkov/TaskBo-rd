import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionServer
{
	static final	int				SERVER_PORT = 4444;
	private			ServerSocket		socket;
	private			List<ClientHandler>	clients;
	
	public ConnectionServer() throws IOException
	{
		socket	= new ServerSocket( SERVER_PORT );
		clients	= new ArrayList<ClientHandler>();
		
		while( true )
		{
			ClientHandler	client	= new ClientHandler( socket.accept() );
			client.run();
		}
	}
	
	private class ClientHandler implements Runnable 
	{
		private	Socket			connectionSocet;
		private	MessageHandler	handler;
		
		public	ClientHandler( Socket socket )
		{
			connectionSocet = socket;
		}
		
		public void run()
		{
			clients.add( this );
			try
			{
				handler	= new MessageHandler( connectionSocet );
				
				if ( handler.AceptLoginMessage() )
				{
					
				}
				
				
			} catch( IOException | SQLException e )
			{
				e.printStackTrace();
			}
			
			
			try
			{
				connectionSocet.close();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			clients.remove( this );
		}
	}
	

	
	
}