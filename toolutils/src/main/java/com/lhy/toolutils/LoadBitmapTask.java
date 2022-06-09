package com.lhy.toolutils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.lhy.toolutils.callback.LoadBitmapCallback;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoadBitmapTask extends AsyncTask<String, Void, Bitmap> {

    private LoadBitmapCallback loadBitmapCallback;

    public LoadBitmapTask(LoadBitmapCallback loadBitmapCallback) {
        this.loadBitmapCallback = loadBitmapCallback;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        loadBitmapCallback.bitmapLoaded(bitmap);
        super.onPostExecute(bitmap);
    }

    @Override
    protected void onPreExecute() {
        loadBitmapCallback.bitmapLoadBefore();
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        Bitmap bitmap = null;
        InputStream inputStream = null;
        try {
            String urlStr = strings[0];
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            inputStream = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }
}
