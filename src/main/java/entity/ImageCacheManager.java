package entity;


import utils.Config;
import utils.Log;
import utils.Util;

import java.io.File;
import java.util.HashMap;

public class ImageCacheManager {

    private final String TAG = ImageCacheManager.class.getSimpleName();

    private String cacheDirPath = Util.getUserHomeDir() + "\\cn.frankliu.ees";
    private File cacheDir;
    private static ImageCacheManager instance;
    private HashMap<Integer, File> fileMap;
    private ImageCacheManager(){
        cacheDir = new File(cacheDirPath);
        if(!cacheDir.exists()){
            if(!cacheDir.mkdir()){
                Log.e(TAG, "make image cache dir fail!");
            }
        }
        fileMap = new HashMap<>();
    }

    public static ImageCacheManager getInstance(){
        if(instance == null){
            instance = new ImageCacheManager();
        }
        return instance;
    }

    public File newImageFile(int transactionId){
        String fileName = cacheDirPath + String.valueOf(System.currentTimeMillis()) + "." + Config.DEFAULT_IMAGE_FORMAT;
        File file = new File(fileName);
        fileMap.put(transactionId, file);
        return file;
    }

    public File getCache(int transactionId){
        return fileMap.get(transactionId);
    }

    public void free(int transactionId){
        fileMap.remove(transactionId);
    }
}
