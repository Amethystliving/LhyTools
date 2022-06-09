package com.lhy.toolutils.callback;

import android.graphics.Bitmap;

public interface LoadBitmapCallback {
     void bitmapLoaded(Bitmap bitmap);
     void bitmapLoadBefore();
}
