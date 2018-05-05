package com.bincontrol.binstore.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class BitmapUtils {

    private static BitmapUtils instance;


    public static BitmapUtils getInstance() {
        if (instance == null) {
            synchronized (BitmapUtils.class) {
                if (instance == null) {
                    instance = new BitmapUtils();
                }
            }
        }
        return instance;
    }

    private BitmapUtils() {}


    public void loadImage(Object data, ImageView imageView, Context context) {
        if (cancelPotentialWork(data, imageView)) {
            BitmapUtils.BitmapLoadTask task = new BitmapUtils.BitmapLoadTask(imageView);
            BitmapUtils.AsyncDrawable asyncDrawable = new BitmapUtils.AsyncDrawable(context.getResources(),
                    BitmapFactory.decodeResource(context.getResources(), 2130837504), task);
            imageView.setImageDrawable(asyncDrawable);
            task.execute(data);
        }
    }


    private static boolean cancelPotentialWork(Object data, ImageView imageView) {
        BitmapUtils.BitmapLoadTask bitmapWorkerTask = getBitmapLoadTask(imageView);
        if (bitmapWorkerTask != null) {
            Object bitmapData = bitmapWorkerTask.data;
            if (bitmapData != null && bitmapData.equals(data)) {
                return false;
            }

            bitmapWorkerTask.cancel(true);
        }
        return true;
    }


    private static BitmapUtils.BitmapLoadTask getBitmapLoadTask(ImageView imageView) {
        if (imageView != null) {
            Drawable drawable = imageView.getDrawable();
            if (drawable instanceof BitmapUtils.AsyncDrawable) {
                BitmapUtils.AsyncDrawable asyncDrawable = (BitmapUtils.AsyncDrawable)drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }


    private static class AsyncDrawable extends BitmapDrawable {

        private final WeakReference<BitmapUtils.BitmapLoadTask> bitmapWorkerTaskReference;

        private AsyncDrawable(Resources res, Bitmap bitmap, BitmapUtils.BitmapLoadTask bitmapWorkerTask) {
            super(res, bitmap);
            this.bitmapWorkerTaskReference = new WeakReference<>(bitmapWorkerTask);
        }

        private BitmapUtils.BitmapLoadTask getBitmapWorkerTask() {
            return (BitmapUtils.BitmapLoadTask)this.bitmapWorkerTaskReference.get();
        }
    }


    private static class BitmapLoadTask extends AsyncTask<Object, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;
        private Object data;

        private BitmapLoadTask(ImageView imageView) {
            this.imageViewReference = new WeakReference<>(imageView);
        }

        @Override
        protected Bitmap doInBackground(Object... params) {

            this.data = params[0];
            String urlString = String.valueOf(this.data);
            Bitmap bitmap = null;
            HttpURLConnection conn = null;

            try {
                URL url = new URL(urlString);
                conn = (HttpURLConnection)url.openConnection();
                bitmap = BitmapFactory.decodeStream(conn.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            ImageView imageView = this.getAttachedImageView();
            if (bitmap != null && imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }

        private ImageView getAttachedImageView() {
            ImageView imageView = (ImageView) this.imageViewReference.get();
            BitmapUtils.BitmapLoadTask bitmapWorkerTask = BitmapUtils.getBitmapLoadTask(imageView);
            return this == bitmapWorkerTask ? imageView : null;
        }
    }

}
