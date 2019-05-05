package main;

import network.Server;
import network.ServerCallback;
import utils.Config;

import java.io.IOException;

public class Main {

	public static Server server;

	public static void main(String[] args) throws IOException {
		server = new Server(Config.REMOTE_SERVER_PORT, new ServerCallback() {
			@Override
			public void finish() {

			}
		});
		server.start();
		
	}


}
