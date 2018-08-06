package org.horoyoii.horoyochat;

import android.graphics.Bitmap;
import android.os.AsyncTask;

/*
이미지를 로컬에 캐싱한다. item view에 띄울 이미지가 캐싱되있으면 로컬에서 가져오고
없다면 로컬에 다운받아 그것을 사용한다.
 */

//Sub THread
public class ImageCacheAndDownloadTask extends AsyncTask<String, Integer, Bitmap>{


    @Override
    protected Bitmap doInBackground(String... strings) {
        return null;
    }


}
