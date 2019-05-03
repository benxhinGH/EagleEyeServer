package tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import tcp.callback.ResponseCallback;
import tcp.protocol.DataProtocol;


/**
 * Created by meishan on 16/12/1.
 */
public class ConnectionServer {

    private static boolean isStart = true;
    private static ServerResponseTask serverResponseTask;
    private static List<ServerResponseTask> spys=new ArrayList<>();

    public ConnectionServer() {
    	
    }

    public void startServer() {
    	System.out.println("start tcp server");

        ServerSocket serverSocket = null;
        ExecutorService executorService = Executors.newCachedThreadPool();
        try {
            serverSocket = new ServerSocket(Config.PORT);
            while (isStart) {
                Socket socket = serverSocket.accept();
                serverResponseTask = new ServerResponseTask(socket, new ResponseCallback() {

                            @Override
                            public void targetIsOffline(DataProtocol reciveMsg) {
                                if (reciveMsg != null) {
                                    System.out.println(reciveMsg.getData());
                                }
                                spys.remove(serverResponseTask);
                            }

                            @Override
                            public void targetIsOnline(String clientIp) {
                                System.out.println(clientIp + " is onLine");
                                System.out.println("-----------------------------------------");
                                
                            }

							
                        });

                if (socket.isConnected()) {
                	spys.add(serverResponseTask);
                    executorService.execute(serverResponseTask);
                }
            }

            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    isStart = false;
                    serverSocket.close();
                    if (serverSocket != null)
                        serverResponseTask.stop();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public List<ServerResponseTask> getSpys(){
    	return spys;
    }
}
