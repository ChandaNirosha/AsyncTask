package com.example.sys9.asynctask;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class AsyncTaskExample extends AppCompatActivity {
    ProgressDialog mProgressDialog;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task_example);
        imageView = findViewById(R.id.imageView);



        String Url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQmPeyBqDtZmAA4t8uRzKy5Zg2ZwvRsQG5qtGyvvdOEbU_YOYia";
        new DownloadImage().execute(Url);

    }
    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(AsyncTaskExample.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Download Image Tutorial");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        protected Bitmap doInBackground(String... url) {

            String imageURL = url[0];

            Bitmap bitmap = null;
            try {

                // Download Image from URL

                InputStream input = new URL(imageURL).openStream();
                bitmap = BitmapFactory.decodeStream(input);

                File storagePath = Environment.getExternalStorageDirectory();
                OutputStream os = new FileOutputStream(new File(storagePath,"name.jpg"));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                os.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // Set the bitmap into ImageView
            imageView.setImageBitmap(result);
            // Close progressdialog
            mProgressDialog.dismiss();
        }
    }
}

