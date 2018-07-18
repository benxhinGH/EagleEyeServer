package tcp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FileRecServer {
	
	
	
	public FileRecServer() {
		
	}
	
	public void startServer() {
		try {
			ServerSocket socket=new ServerSocket(Config.FILE_PORT);
			while(true) {
				System.out.println("file server start listening");
				Socket s=socket.accept();
				String fileName=System.currentTimeMillis()+Config.PIC_SUFFIX;
				File file=new File(Config.FILE_SAVE_PATH+fileName);
				BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(file));
				BufferedInputStream bis=new BufferedInputStream(s.getInputStream());
				byte[] buffer=new byte[4096];
				int r;
				while((r=bis.read(buffer))!=-1) {
					bos.write(buffer, 0, r);
				}
				bos.flush();
				bos.close();
				bis.close();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
