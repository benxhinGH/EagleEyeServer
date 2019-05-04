package entity;

import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class SpyManager {

    private final String TAG = SpyManager.class.getSimpleName();
    private static SpyManager instance;
    private HashMap<Spy, ChannelHandlerContext> spyMap;

    private SpyManager(){
        spyMap = new HashMap<>();
    }

    public static SpyManager getInstance(){
        if(null == instance){
            instance = new SpyManager();
        }
        return instance;
    }

    public void add(Spy spy, ChannelHandlerContext ctx){
        spyMap.put(spy, ctx);
    }

    public ChannelHandlerContext getCtx(Spy spy){
        return spyMap.get(spy);
    }

    public Set<Spy> getSpys(){
        return spyMap.keySet();
    }

    public void free(Spy spy){
        spyMap.remove(spy);
    }
}
