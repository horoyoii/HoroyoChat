package org.horoyoii.horoyochat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import org.horoyoii.horoyochat.app.HoroyoChatApp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class ImageCacheDownloader extends AsyncTask<String, Integer, String> {
    private final String TAG = "asynTaskk";
    private CircleImageView imageView;
    private String imgUrl;
    private final String CACHE_DIR = "/save_profile_image/";
    private String fileFullPath;
    boolean flag = false; // 캐싱이 되어있다 or not


    public ImageCacheDownloader(CircleImageView imageView, String imgUrl) {
        this.imageView = imageView;
        this.imgUrl = imgUrl;
    }

    private String urlToFileFullPath(String url){
        return HoroyoChatApp.getInstance().getCacheDir().toString()+CACHE_DIR+url.substring(url.lastIndexOf("/"), url.length())+".jpg";
    }

    @Override
    protected void onPreExecute() {
        Log.d(TAG,"onPreExe");
        super.onPreExecute();
        fileFullPath =urlToFileFullPath(imgUrl); // data/cache/save_profile_image/01239cx1039x9234.jpg
        if(new File(fileFullPath).exists()) {
            // 이미지가 캐싱되어있는 경우
            imageView.setImageURI(Uri.parse(fileFullPath));
            flag = true;
        }

    }

    @Override
    protected String doInBackground(String... strings) {
        Log.d(TAG,"doInBack");
        // 이미지가 캐싱되어있지 않는 경우
        if(!flag){
            Log.d(TAG,"캐싱안되있음0");
            downloadBitmap(imgUrl);
            //imageView.setImageURI(Uri.parse(fileFullPath));
        }else{
            Log.d(TAG,"캐싱 되있음0");
        }

        return null;
    }


    @Override
    protected void onPostExecute(String bitmap) {
        super.onPostExecute(bitmap);
        if(!flag){
            imageView.setImageURI(Uri.parse(fileFullPath));
        }



    }


    //fileFullPath =urlToFileFullPath(imgUrl); data/cache/save_profile_image/01239cx1039x9234.jpg
    //imgUrl : "https://firebasestorage.googleapis.com/v0/b/horoyochat.appspot.com/o/users%2Fqwe%40nate.com.jpg?alt=media&token=666cdbb7-a0ab-491f-aa70-754f007faf0e"
    private String downloadBitmap(String FullimgUrl){

        try{
            URL imgUrl = new URL(FullimgUrl);
            HttpURLConnection conn = (HttpURLConnection)imgUrl.openConnection();
            int len = conn.getContentLength();
            byte[] tmpByte = new byte[len];
            Log.d("TAF",String.valueOf(len));
            InputStream is = conn.getInputStream();

            File file = new File(fileFullPath);

            //파일 저장 스트림 생성

            FileOutputStream fos = new FileOutputStream(file);

            int read;

            //입력 스트림을 파일로 저장

            for (;;) {
                read = is.read(tmpByte);
                if (read <= 0) {
                    break;
                }
                fos.write(tmpByte, 0, read); //file 생성
            }
            is.close();
            fos.close();
            conn.disconnect();
        }catch (Exception e){
            e.printStackTrace();
        }


        return null;

    }

}
