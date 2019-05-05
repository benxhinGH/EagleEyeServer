package network;

import entity.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import protocol.*;
import utils.Log;
import utils.LogLevel;
import utils.Util;

import java.io.File;
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
        int transactionId = basicProtocol.getTransactionId();
        Log.log(TAG, LogLevel.INFO, "receive msg:" + basicProtocol.toString());
        switch (basicProtocol.getMsgId()){
            case MsgId.IDENTIFICATION_REQUEST:
                IdentificationRequest identificationRequest = Parser.parseIdentificationRequest(basicProtocol);
                String ip = Util.getChannelRemoteAddressIp(ctx).getHostAddress();
                int port = Util.getChannelRemoteAddressPort(ctx);
                Terminal terminal = null;
                if(identificationRequest.getTerminalType() == Terminal.TYPE_SPY){
                    terminal = new Spy(ip, port, identificationRequest.getHostname());
                }else if(identificationRequest.getTerminalType() == Terminal.TYPE_CLIENT){
                    terminal = new Client(ip, port, identificationRequest.getHostname());
                }
                if(terminal == null){
                    Log.w(TAG, "terminal is null");
                }
                TerminalManager.getInstance().add(terminal, ctx);
                BasicProtocol identificationResponse = ProtocolFactory.createIdentificationResponse(ErrorCode.SUCCESS);
                ctx.writeAndFlush(identificationResponse);
                break;
            case MsgId.SPY_LIST_REQUEST:
                BasicProtocol spyListResponse = ProtocolFactory.createSpyListResponse();
                ctx.writeAndFlush(spyListResponse);
                break;
            case MsgId.FILE_SEND_REQUEST:
                FileSendRequest fileSendRequest = Parser.parseFileSendRequest(basicProtocol.getDataArray());
                FileReceiver fileReceiver = new FileReceiver(fileSendRequest.getFileName(), fileSendRequest.getFileLength(), new FileReceiverCallback() {
                    @Override
                    public void ready(int port) {
                        BasicProtocol fileSendResponse = ProtocolFactory.createFileSendResponse(ErrorCode.SUCCESS, port);
                        ctx.writeAndFlush(fileSendResponse);
                    }

                    @Override
                    public void currentProgress(int progress) {

                    }

                    @Override
                    public void finish(File file) {
                        Client client = TerminalManager.getInstance().getWaitRspClient(transactionId);
                        ChannelHandlerContext channelHandlerContext = TerminalManager.getInstance().getCtx(client);
                        BasicProtocol fileSendRequest1 = ProtocolFactory.createFileSendRequest(transactionId, file);
                        channelHandlerContext.writeAndFlush(fileSendRequest1);
                    }
                });
                executor.execute(fileReceiver);
                break;
            case MsgId.SCREENSHOT_REQUEST:
                Spy trgSpy = Parser.parseScreenShotRequest(basicProtocol);
                ChannelHandlerContext channelHandlerContext = TerminalManager.getInstance().getCtx(trgSpy);
                BasicProtocol screenShotRequest = ProtocolFactory.createScreenShotRequest(transactionId);
                channelHandlerContext.writeAndFlush(screenShotRequest);

                String clientIp = Util.getChannelRemoteAddressIp(ctx).getHostAddress();
                int clientPort = Util.getChannelRemoteAddressPort(ctx);
                Client client = TerminalManager.getInstance().getClient(clientIp, clientPort);
                TerminalManager.getInstance().putWaitRspClient(transactionId, client);
                break;
            case MsgId.FILE_SEND_RESPONSE:
                int port1 = Util.byteArrayToInt(basicProtocol.getDataArray());
                File file = ImageCacheManager.getInstance().get(transactionId);
                FileSender fileSender = new FileSender(Util.getChannelRemoteAddressIp(ctx), port1, file, new FileSenderCallback() {
                    @Override
                    public void currentProgress(int progress) {

                    }

                    @Override
                    public void finish() {

                    }
                });
                executor.execute(fileSender);
                break;
            case MsgId.SCREENSHOT_RESPONSE:
                BasicProtocol screenShotResponse = ProtocolFactory.createScreenShotResponse(transactionId);
                ChannelHandlerContext clientCtx = TerminalManager.getInstance().getCtx(TerminalManager.getInstance().getWaitRspClient(transactionId));
                clientCtx.writeAndFlush(screenShotResponse);
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
