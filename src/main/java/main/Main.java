package main;

import network.Server;
import network.ServerCallback;
import utils.Config;
import utils.Log;

import java.io.File;
import java.io.IOException;

public class Main {

	public static final String TAG = Main.class.getSimpleName();
	public static Server server;

	public static void main(String[] args) throws IOException {
		createTemp();
		server = new Server(Config.REMOTE_SERVER_PORT, new ServerCallback() {
			@Override
			public void finish() {

			}
		});
		server.start();
	}

	public static void createTemp(){
		Log.i(TAG, "fileSaveDir:" + Config.fileSaveDir);
		File file = new File(Config.fileSaveDir);
		if(!file.exists()){
			if(!file.mkdir()){
				Log.e(TAG, "make temp dir failed");
			}
		}
	}


}
