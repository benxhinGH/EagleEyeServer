package protocol;

import com.alibaba.fastjson.JSONObject;
import entity.Spy;
import entity.Terminal;
import jdk.nashorn.internal.parser.JSONParser;
import netscape.javascript.JSObject;
import utils.Log;
import utils.Util;

import java.util.Arrays;

public class Parser {

    private static final String TAG = Parser.class.getSimpleName();

    public static Spy parseScreenShotRequest(BasicProtocol basicProtocol){
        Spy spy = null;
        if(DataFormat.JSON != basicProtocol.getDataFormat()){
            Log.e(TAG, "data format not excepted");
            return null;
        }
        String jsonStr = new String(basicProtocol.getDataArray(), 0, basicProtocol.getDataArray().length);
        Log.i(TAG, "jsonStr : " + jsonStr);
        spy = JSONObject.parseObject(jsonStr, Spy.class);
        return spy;
    }



    public static FileSendRequest parseFileSendRequest(byte[] data){
        FileSendRequest fileSendRequest = new FileSendRequest();
        fileSendRequest.parse(data);
        return fileSendRequest;
    }

    public static IdentificationRequest parseIdentificationRequest(BasicProtocol basicProtocol){
        IdentificationRequest identificationRequest = new IdentificationRequest();
        identificationRequest.parse(basicProtocol.getDataArray());
        return identificationRequest;
    }
}
