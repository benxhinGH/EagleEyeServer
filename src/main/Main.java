package main;

import tcp.ConnectionServer;
import tcp.FileRecServer;

public class Main {
	
	public static ConnectionServer server=new ConnectionServer();
	public static FileRecServer fileRecServer=new FileRecServer();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				//server.startServer();
				//fileRecServer.startServer();
			}
			
		});
		fileRecServer.startServer();
		
		
		
		
		
	}

}
