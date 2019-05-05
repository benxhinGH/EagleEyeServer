package entity;

import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class TerminalManager {

    public static final String TAG = TerminalManager.class.getSimpleName();

    private static TerminalManager instance;
    private HashMap<Terminal, ChannelHandlerContext> map;
    private HashMap<Integer, Client> mapWaitRspClient;

    private TerminalManager(){
        map = new HashMap<>();
        mapWaitRspClient = new HashMap<>();
    }

    public static TerminalManager getInstance(){
        if(instance == null){
            instance = new TerminalManager();
        }
        return instance;
    }

    public void add(Terminal terminal, ChannelHandlerContext ctx){
        map.put(terminal, ctx);
    }

    public ChannelHandlerContext getCtx(Terminal terminal){
        return map.get(terminal);
    }

    public List<Spy> getSpies(){
        ArrayList<Spy> arrayList = new ArrayList<>();
        Set<Terminal> set = map.keySet();
        for(Terminal t: set){
            if(t.getType() == Terminal.TYPE_SPY){
                arrayList.add((Spy)t);
            }
        }
        return arrayList;
    }

    public void remove(Terminal terminal){
        map.remove(terminal);
    }

    public void putWaitRspClient(int transactionId, Client client){
        mapWaitRspClient.put(transactionId, client);
    }

    public Client getWaitRspClient(int transactionId){
        return mapWaitRspClient.get(transactionId);
    }

    public void removeWaitRspClient(int transactionId){
        mapWaitRspClient.remove(transactionId);
    }

    public Client getClient(String ip, int port){
        Set<Terminal> set = map.keySet();
        for(Terminal t:set){
            if(t.getIp().equals(ip) && t.getPort() == port){
                return (Client)t;
            }
        }
        return null;
    }
}
