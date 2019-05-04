package main;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

	public static ServerSocket clientServer;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
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
