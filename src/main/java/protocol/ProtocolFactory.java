package protocol;

import com.alibaba.fastjson.JSONArray;
import entity.TerminalManager;
import utils.Util;

import java.io.File;

public class ProtocolFactory {

    public static BasicProtocol createFileSendRequest(int transacionId, File file){
        FileSendRequest fileSendRequest = new FileSendRequest();
        fileSendRequest.setFileName(file.getName());
        fileSendRequest.setFileLength(file.length());
        BasicProtocol basicProtocol = new BasicProtocol();
        basicProtocol.setMsgId(MsgId.FILE_SEND_REQUEST);
        basicProtocol.setTransactionId((byte) transacionId);
        basicProtocol.setDataArray(fileSendRequest.getMsgBytes());
        return basicProtocol;
    }

    public static BasicProtocol createFileSendResponse(byte errorCode, int port, int transactionId){
        BasicProtocol res = new BasicProtocol();
        res.setTransactionId((byte)transactionId);
        res.setMsgId(MsgId.FILE_SEND_RESPONSE);
        res.setErrorCode(errorCode);
        res.setDataArray(Util.int2ByteArrays(port));
        return res;
    }

    public static BasicProtocol createIdentificationResponse(int errorCode){
        BasicProtocol basicProtocol = new BasicProtocol();
        basicProtocol.setMsgId(MsgId.IDENTIFICATION_RESPONSE);
        basicProtocol.setErrorCode((byte)errorCode);
        return basicProtocol;
    }

    public static BasicProtocol createSpyListResponse(){
        BasicProtocol basicProtocol = new BasicProtocol();
        basicProtocol.setMsgId(MsgId.SPY_LIST_RESPONSE);
        basicProtocol.setDataFormat(DataFormat.JSON);
        String jsonData = JSONArray.toJSONString(TerminalManager.getInstance().getSpies());
        basicProtocol.setDataArray(jsonData.getBytes());
        return basicProtocol;
    }

    public static BasicProtocol createScreenShotRequest(int transactionId){
        BasicProtocol basicProtocol = new BasicProtocol();
        basicProtocol.setTransactionId((byte)transactionId);
        basicProtocol.setMsgId(MsgId.SCREENSHOT_REQUEST);
        return basicProtocol;
    }

    public static BasicProtocol createScreenShotResponse(int transactionId){
        BasicProtocol basicProtocol = new BasicProtocol();
        basicProtocol.setMsgId(MsgId.SCREENSHOT_RESPONSE);
        basicProtocol.setTransactionId((byte)transactionId);
        return basicProtocol;
    }

}
