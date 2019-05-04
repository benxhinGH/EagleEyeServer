package protocol;

import utils.Config;
import utils.Util;

import java.io.File;
import java.nio.ByteBuffer;

public class ProtocolFactory {

    public static BasicProtocol createFileSendRequest(int taskId, File file){
        FileSendRequest fileSendRequest = new FileSendRequest();
        fileSendRequest.setFileName(file.getName());
        fileSendRequest.setFileLength(file.length());
        BasicProtocol basicProtocol = new BasicProtocol();
        basicProtocol.setMsgId(MsgId.FILE_SEND_REQUEST);
        basicProtocol.setTransactionId((byte) taskId);
        basicProtocol.setDataArray(fileSendRequest.getMsgBytes());
        return basicProtocol;
    }

    public static BasicProtocol createIdentificationResponse(int errorCode){
        BasicProtocol basicProtocol = new BasicProtocol();
        basicProtocol.setMsgId(MsgId.IDENTIFICATION_RESPONSE);
        basicProtocol.setErrorCode((byte)errorCode);
        return basicProtocol;
    }

}
