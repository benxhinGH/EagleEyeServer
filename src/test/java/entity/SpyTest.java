package entity;

import com.alibaba.fastjson.JSONArray;
import org.junit.Test;
import utils.Log;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SpyTest {

    private final String TAG = SpyTest.class.getSimpleName();
    @Test
    public void parse(){
        Set<Spy> spies = new HashSet<>();
        spies.add(new Spy("1","1",1));
        spies.add(new Spy("2","2",2));
        spies.add(new Spy("3","3",3));
        String jsonStr = JSONArray.toJSONString(spies);
        Log.i(TAG, jsonStr);
        List<Spy> list = JSONArray.parseArray(jsonStr, Spy.class);
        for(Spy spy:list){
            Log.i(TAG, "after:" + spy.toString());
        }
    }

}