package network;

import com.alibaba.fastjson.JSONArray;
import entity.Spy;
import entity.SpyManager;
import entity.TaskIdPool;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import protocol.*;
import utils.Config;
import utils.Log;
import utils.LogLevel;
import utils.Util;

import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    private final String TAG = ServerHandler.class.getSimpleName();

    private ServerCallback callback;
    private Executor executor = Executors.newCachedThreadPool();

    public ServerHandler(ServerCallback callback){
        this.callback = callback;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        BasicProtocol basicProtocol = (BasicProtocol)msg;
        Log.log(TAG, LogLevel.INFO, "receive msg:" + basicProtocol.toString());
        switch (basicProtocol.getMsgId()){
            case MsgId.IDENTIFICATION_REQUEST:
                String hostname = new String(basicProtocol.getDataArray(), 0, basicProtocol.getDataArray().length);
                String ip = Util.getChannelRemoteAddressIp(ctx).getHostAddress();
                int port = Util.getChannelRemoteAddressPort(ctx);
                Spy spy = new Spy(hostname, ip, port);
                SpyManager.getInstance().add(spy, ctx);
                BasicProtocol identificationResponse = ProtocolFactory.createIdentificationResponse(ErrorCode.SUCCESS);
                ctx.writeAndFlush(identificationResponse);
                break;
                case MsgId.SPY_LIST_REQUEST:

                    break;
                default:
                    Log.log(TAG, LogLevel.INFO, "unknow msg");
                    break;
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
