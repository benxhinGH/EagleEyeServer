package network;

import entity.ImageCacheManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import protocol.*;
import utils.*;

import java.io.File;
import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    private final String TAG = ClientHandler.class.getSimpleName();

    private ClientCallback callback;
    private ExecutorService executor;

    public ClientHandler(ClientCallback callback){
        this.callback = callback;
        executor = Executors.newCachedThreadPool();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        BasicProtocol basicProtocol = (BasicProtocol)msg;
        int transactionId = basicProtocol.getTransactionId();
        switch (basicProtocol.getMsgId()){
            case MsgId.SCREENSHOT_REQUEST:
//                File imageFile = ImageCacheManager.getInstance().newImageFile(transactionId);
//                ScreenShoter.img2file(ScreenShoter.getScreen(), Config.DEFAULT_IMAGE_FORMAT, imageFile);
//                BasicProtocol fileRequest = ProtocolFactory.createFileSendRequest(transactionId, imageFile);
//                ctx.writeAndFlush(fileRequest);
                break;
            case MsgId.FILE_SEND_RESPONSE:
                if(ErrorCode.SUCCESS == basicProtocol.getErrorCode()){
                    int trgPort = Util.byteArrayToInt(basicProtocol.getDataArray());
                    File file = ImageCacheManager.getInstance().getCache(transactionId);
                    InetAddress remoteAddr = Util.getChannelRemoteAddressIp(ctx);
                    FileSender fileSender = new FileSender(remoteAddr, trgPort, file, new FileSenderCallback() {
                        @Override
                        public void currentProgress(int progress) {

                        }

                        @Override
                        public void finish() {
                            ImageCacheManager.getInstance().free(transactionId);
                        }
                    });
                    executor.execute(fileSender);
                    Log.i(TAG, "send file:" + file.getName() + " to:" + remoteAddr.getHostAddress() + ":" + trgPort);
                }
                default:
                    break;
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


}
