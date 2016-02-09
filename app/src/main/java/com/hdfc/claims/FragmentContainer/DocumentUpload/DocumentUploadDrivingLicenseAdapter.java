
/**
 * Created by patelmih on 12/21/2015.
 */
package com.hdfc.claims.FragmentContainer.DocumentUpload;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.hdfc.claims.FragmentContainer.FragmentContainerActivity;
import com.hdfc.claims.R;
import com.hdfc.claims.Utilities.ImageLoadingUtils;

public class DocumentUploadDrivingLicenseAdapter extends BaseAdapter {

    private ImageLoadingUtils utils;
    //private LruCache<String, Bitmap> memoryCache;
    // private ImageView imageView;
    private ArrayList<String> listImages;
    private Context context;

    public DocumentUploadDrivingLicenseAdapter(Activity activity, ArrayList<String> listImages) {

        context = activity;
        utils = new ImageLoadingUtils(context);

        this.listImages = listImages;
    }

    public class Holder
    {

        ImageView imageView;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listImages.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        final Holder holder=new Holder();


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.document_upload_driving_license_item, parent, false);

        holder.imageView = (ImageView) view.findViewById(R.id.imgView);
        view.setTag(holder);
        loadBitmap(listImages.get(position), holder.imageView, context);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String imageURI = listImages.get(position);
                Intent intent = new Intent(view.getContext(), FullScreenImageActivity.class);
                intent.putExtra("fullScreenImageURI", listImages.get(position));
                ((FragmentContainerActivity) context).startActivityForResult(intent, 1);
            }
        });

        holder.imageView.setOnLongClickListener(new OnLongClickListener() {

            @Override
            public boolean onLongClick(View arg0) {
                // TODO Auto-generated method stub


                // TODO Auto-generated method stub
                AlertDialog.Builder alert = new AlertDialog.Builder(
                        context);

                alert.setTitle("Delete");
                alert.setMessage("Do you want delete this item?");
                alert.setPositiveButton("YES", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TOD O Auto-generated method stub

                        // main code on after clicking yes
                        //Toast.makeText(mContext, String.valueOf(position), Toast.LENGTH_SHORT);

                        listImages.remove(position);
                        notifyDataSetChanged();



                    }
                });
                alert.setNegativeButton("CANCEL", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                    }
                });

                alert.show();

                return true;


            }
        });
        return view;

    }



    public void loadBitmap(String filePath, ImageView imageView, Context context) {
        if (cancelPotentialWork(filePath, imageView)) {

            BitmapFactory.Options options = new BitmapFactory.Options();

            // options.inSampleSize = 8;
            Bitmap bitmap = BitmapFactory.decodeFile(filePath,
                    options);
            if(bitmap != null){
                bitmap = Bitmap.createScaledBitmap(bitmap, 110, 110, false);
                imageView.setImageBitmap(bitmap);
            }
            else{
                final BitmapWorkerTask task = new BitmapWorkerTask(imageView);
                final AsyncDrawable asyncDrawable = new AsyncDrawable(context.getResources(), utils.icon, task);
                imageView.setImageDrawable(asyncDrawable);
                task.execute(filePath);
            }
        }
    }

    /* public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
          if (getBitmapFromMemCache(key) == null) {
              memoryCache.put(key, bitmap);
          }
      }*/

     /* public Bitmap getBitmapFromMemCache(String key) {
          return memoryCache.get(key);
      } */



    class AsyncDrawable extends BitmapDrawable {

        private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;

        public AsyncDrawable(Resources res, Bitmap bitmap, BitmapWorkerTask bitmapWorkerTask) {
            super(res, bitmap);
            bitmapWorkerTaskReference = new WeakReference<BitmapWorkerTask>(bitmapWorkerTask);
        }

        public BitmapWorkerTask getBitmapWorkerTask() {
            return bitmapWorkerTaskReference.get();
        }
    }

    public boolean cancelPotentialWork(String filePath, ImageView imageView) {

        final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

        if (bitmapWorkerTask != null) {
            final String bitmapFilePath = bitmapWorkerTask.filePath;
            if (bitmapFilePath != null && !bitmapFilePath.equalsIgnoreCase(filePath)) {
                bitmapWorkerTask.cancel(true);
            } else {
                return false;
            }
        }
        return true;
    }

    private BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }

    class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap>{
        private final WeakReference<ImageView> imageViewReference;
        public String filePath;

        public BitmapWorkerTask(ImageView imageView){
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            filePath = params[0];
            Bitmap bitmap = utils.decodeBitmapFromPath(filePath);
            //addBitmapToMemoryCache(filePath, bitmap);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                bitmap = null;
            }
            if(imageViewReference != null && bitmap != null){
                final ImageView imageView = imageViewReference.get();
                final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);
                if (this == bitmapWorkerTask && imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }



}
