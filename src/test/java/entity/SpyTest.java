package entity;

import com.alibaba.fastjson.JSONArray;
import org.junit.Test;
import utils.Log;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SpyTest {

    private final String TAG = SpyTest.class.getSimpleName();
    @Test
    public void parse(){
        Set<Spy> spies = new HashSet<>();
        spies.add(new Spy("1",1, "1"));
        spies.add(new Spy("2",2, "2"));
        spies.add(new Spy("3",3, "3"));
        String jsonStr = JSONArray.toJSONString(spies);
        Log.i(TAG, jsonStr);
        List<Spy> list = JSONArray.parseArray(jsonStr, Spy.class);
        for(Spy spy:list){
            Log.i(TAG, "after:" + spy.toString());
        }
    }

    @Test
    public void equals() {
        Spy s1 = new Spy("1",1, "1");
        Spy s2 = new Spy("1",1, "1");
        Log.i(TAG, "result" + s1.equals(s2));
        HashMap<Spy, String> hashMap = new HashMap<>();
        hashMap.put(s1, "1");
        String res = hashMap.get(s2);
        Log.i(TAG, "result:" + res);
    }
}