package com.filter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.filter.utility.Helper;
import com.filter.utility.TransformImage;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class ControlActivity extends AppCompatActivity {

    static
    {
        System.loadLibrary("NativeImageProcessor");
    }

    final static int PICK_IMAGE=2;
    final static int MY_PERMISSIONS_REQUEST_STORAGE_PERMISSIONS=3;

    Toolbar toolbar;
    ImageView mCenterImageView;
    TransformImage mTransformImage;
    int mCurrentFilter;

    SeekBar mSeekBar;
    ImageView mTickImageView;
    Uri mSelectedImageUri;
    //Bitmap mBitmap;

    int mScreenWidth;
    int mScreenHeight;

    Uri uri;

    Target mApplySingleFilter=new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            int currentFilterValue=mSeekBar.getProgress();
            String filename;
            if(mCurrentFilter==TransformImage.FILTER_BRIGHTNESS)
            {
                mTransformImage.applyBrightnessSubFilter(currentFilterValue);

                filename=mTransformImage.getFileName(TransformImage.FILTER_BRIGHTNESS);
                Helper.writeDataIntoExternalStorage(ControlActivity.this,filename,mTransformImage.getBitmap(TransformImage.FILTER_BRIGHTNESS));

                Picasso.with(ControlActivity.this).invalidate(Helper.getFileFromExternalStorage(ControlActivity.this,filename));
                Picasso.with(ControlActivity.this).load(Helper.getFileFromExternalStorage(ControlActivity.this,filename)).resize(mScreenWidth,mScreenHeight/2).into(mCenterImageView);
            }
            else if(mCurrentFilter==TransformImage.FILTER_SATURATION)
            {
                mTransformImage.applySaturationSubFilter(currentFilterValue);

                filename=mTransformImage.getFileName(TransformImage.FILTER_SATURATION);
                Helper.writeDataIntoExternalStorage(ControlActivity.this,filename,mTransformImage.getBitmap(TransformImage.FILTER_SATURATION));

                Picasso.with(ControlActivity.this).invalidate(Helper.getFileFromExternalStorage(ControlActivity.this,filename));
                Picasso.with(ControlActivity.this).load(Helper.getFileFromExternalStorage(ControlActivity.this,filename)).resize(mScreenWidth,mScreenHeight/2).into(mCenterImageView);
            }
            else if(mCurrentFilter==TransformImage.FILTER_CONTRAST)
            {
                mTransformImage.applyContrastSubFilter(currentFilterValue);

                filename=mTransformImage.getFileName(TransformImage.FILTER_CONTRAST);
                Helper.writeDataIntoExternalStorage(ControlActivity.this,filename,mTransformImage.getBitmap(TransformImage.FILTER_CONTRAST));

                Picasso.with(ControlActivity.this).invalidate(Helper.getFileFromExternalStorage(ControlActivity.this,filename));
                Picasso.with(ControlActivity.this).load(Helper.getFileFromExternalStorage(ControlActivity.this,filename)).resize(mScreenWidth,mScreenHeight/2).into(mCenterImageView);
            }
            else if(mCurrentFilter==TransformImage.FILTER_VIGNETTE)
            {
                mTransformImage.applyVignetteSubFilter(currentFilterValue);

                filename=mTransformImage.getFileName(TransformImage.FILTER_VIGNETTE);
                Helper.writeDataIntoExternalStorage(ControlActivity.this,filename,mTransformImage.getBitmap(TransformImage.FILTER_VIGNETTE));

                Picasso.with(ControlActivity.this).invalidate(Helper.getFileFromExternalStorage(ControlActivity.this,filename));
                Picasso.with(ControlActivity.this).load(Helper.getFileFromExternalStorage(ControlActivity.this,filename)).resize(mScreenWidth,mScreenHeight/2).into(mCenterImageView);
            }
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    Target mSmallTarget=new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            mTransformImage=new TransformImage(ControlActivity.this,bitmap);
            String filename;

            /*mTransformImage.applyBrightnessSubFilter(TransformImage.DEFAULT_BRIGHTNESS);
            SharedPreferences sp=getSharedPreferences("Pictures", Context.MODE_PRIVATE);
            SharedPreferences.Editor ed=sp.edit();
            ed.putString("Pic1",mTransformImage.getBitmap(TransformImage.FILTER_BRIGHTNESS).toString());
            ed.commit();

            String mImage=sp.getString("Pic1","");
            uri=uri.parse(mImage);

            try {
                mBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                mFirstFilterPreviewImage.setImageBitmap(bitmap);
            }
            catch (Exception e)
            {
                Toast.makeText(getApplicationContext(), "File not found", Toast.LENGTH_SHORT).show();
            }

            filename=mTransformImage.getFileName(TransformImage.FILTER_BRIGHTNESS);*/

            //brightness
            mTransformImage.applyBrightnessSubFilter(TransformImage.DEFAULT_BRIGHTNESS);
            filename=mTransformImage.getFileName(TransformImage.FILTER_BRIGHTNESS);
            //Picasso.with(ControlActivity.this).load(mSelectedImageUri).fit().centerInside().into(mFirstFilterPreviewImage);
            Helper.writeDataIntoExternalStorage(ControlActivity.this,filename,mTransformImage.getBitmap(TransformImage.FILTER_BRIGHTNESS));
            Picasso.with(ControlActivity.this).load(Helper.getFileFromExternalStorage(ControlActivity.this,filename)).fit().centerInside().into(mFirstFilterPreviewImage);

            //saturation
            mTransformImage.applySaturationSubFilter(TransformImage.DEFAULT_SATURATION);
            filename=mTransformImage.getFileName(TransformImage.FILTER_SATURATION);
            //Picasso.with(ControlActivity.this).load(mSelectedImageUri).fit().centerInside().into(mSecondFilterPreviewImage);
            Helper.writeDataIntoExternalStorage(ControlActivity.this,filename,mTransformImage.getBitmap(TransformImage.FILTER_SATURATION));
            Picasso.with(ControlActivity.this).load(Helper.getFileFromExternalStorage(ControlActivity.this,filename)).fit().centerInside().into(mSecondFilterPreviewImage);

            //contrast
            mTransformImage.applyContrastSubFilter(TransformImage.DEFAULT_CONTRAST);
            filename=mTransformImage.getFileName(TransformImage.FILTER_CONTRAST);
            //Picasso.with(ControlActivity.this).load(mSelectedImageUri).fit().centerInside().into(mThirdFilterPreviewImage);
            Helper.writeDataIntoExternalStorage(ControlActivity.this,filename,mTransformImage.getBitmap(TransformImage.FILTER_CONTRAST));
            Picasso.with(ControlActivity.this).load(Helper.getFileFromExternalStorage(ControlActivity.this,filename)).fit().centerInside().into(mThirdFilterPreviewImage);

            //vignette
            mTransformImage.applyVignetteSubFilter(TransformImage.DEFAULT_VIGNETTE);
            filename=mTransformImage.getFileName(TransformImage.FILTER_VIGNETTE);
            //Picasso.with(ControlActivity.this).load(mSelectedImageUri).fit().centerInside().into(mFourthFilterPreviewImage);
            Helper.writeDataIntoExternalStorage(ControlActivity.this,filename,mTransformImage.getBitmap(TransformImage.FILTER_VIGNETTE));
            Picasso.with(ControlActivity.this).load(Helper.getFileFromExternalStorage(ControlActivity.this,filename)).fit().centerInside().into(mFourthFilterPreviewImage);

        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    ImageView mFirstFilterPreviewImage;
    ImageView mSecondFilterPreviewImage;
    ImageView mThirdFilterPreviewImage;
    ImageView mFourthFilterPreviewImage;

    private static final String TAG=ControlActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mCenterImageView = (ImageView) findViewById(R.id.imageView);
        mTickImageView = (ImageView) findViewById(R.id.imageView2);
        mSeekBar = (SeekBar) findViewById(R.id.seekBar);

        toolbar.setNavigationIcon(R.drawable.icon);
        toolbar.setTitle(getString(R.string.app_name));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        mFirstFilterPreviewImage = (ImageView) findViewById(R.id.imageView7);
        mFirstFilterPreviewImage.setEnabled(false);
        mSecondFilterPreviewImage = (ImageView) findViewById(R.id.imageView6);
        mSecondFilterPreviewImage.setEnabled(false);
        mThirdFilterPreviewImage = (ImageView) findViewById(R.id.imageView5);
        mThirdFilterPreviewImage.setEnabled(false);
        mFourthFilterPreviewImage = (ImageView) findViewById(R.id.imageView4);
        mFourthFilterPreviewImage.setEnabled(false);

        mCenterImageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                requestStoragePermissions();
                if (ContextCompat.checkSelfPermission(ControlActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });



        //brightness
        mFirstFilterPreviewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSeekBar.setMax(TransformImage.MAX_BRIGHTNESS);
                mSeekBar.setProgress(TransformImage.DEFAULT_BRIGHTNESS);
                mCurrentFilter = TransformImage.FILTER_BRIGHTNESS;
                Picasso.with(ControlActivity.this).load(Helper.getFileFromExternalStorage(ControlActivity.this, mTransformImage.getFileName(TransformImage.FILTER_BRIGHTNESS))).resize(mScreenWidth, mScreenHeight / 2).into(mCenterImageView);
            }
        });

        //saturation
        mSecondFilterPreviewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSeekBar.setMax(TransformImage.MAX_SATURATION);
                mSeekBar.setProgress(TransformImage.DEFAULT_SATURATION);
                mCurrentFilter = TransformImage.FILTER_SATURATION;
                Picasso.with(ControlActivity.this).load(Helper.getFileFromExternalStorage(ControlActivity.this, mTransformImage.getFileName(TransformImage.FILTER_SATURATION))).resize(mScreenWidth, mScreenHeight / 2).into(mCenterImageView);
            }
        });

        //contrast
        mThirdFilterPreviewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSeekBar.setMax(TransformImage.MAX_CONTRAST);
                mSeekBar.setProgress(TransformImage.DEFAULT_CONTRAST);
                mCurrentFilter = TransformImage.FILTER_CONTRAST;
                Picasso.with(ControlActivity.this).load(Helper.getFileFromExternalStorage(ControlActivity.this, mTransformImage.getFileName(TransformImage.FILTER_CONTRAST))).resize(mScreenWidth, mScreenHeight / 2).into(mCenterImageView);
            }
        });

        //vignette
        mFourthFilterPreviewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSeekBar.setMax(TransformImage.MAX_VIGNETTE);
                mSeekBar.setProgress(TransformImage.DEFAULT_VIGNETTE);
                mCurrentFilter = TransformImage.FILTER_VIGNETTE;
                Picasso.with(ControlActivity.this).load(Helper.getFileFromExternalStorage(ControlActivity.this, mTransformImage.getFileName(TransformImage.FILTER_VIGNETTE))).resize(mScreenWidth, mScreenHeight / 2).into(mCenterImageView);
            }
        });

        mTickImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.with(ControlActivity.this).load(mSelectedImageUri).into(mApplySingleFilter);
            }
        });

        DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mScreenHeight=displayMetrics.heightPixels;
        mScreenWidth=displayMetrics.widthPixels;
    }


    public void onActivityResult(int requestcode, int resultcode, Intent data)
    {
        if(requestcode==PICK_IMAGE && resultcode==Activity.RESULT_OK){
            mSelectedImageUri=data.getData();
            Picasso.with(ControlActivity.this).load(mSelectedImageUri).resize(mScreenWidth,mScreenHeight/2).into(mCenterImageView);
            mFirstFilterPreviewImage.setEnabled(true);
            mSecondFilterPreviewImage.setEnabled(true);
            mThirdFilterPreviewImage.setEnabled(true);
            mFourthFilterPreviewImage.setEnabled(true);

            Picasso.with(ControlActivity.this).load(mSelectedImageUri).into(mSmallTarget);
        }
    }

    public void requestStoragePermissions()
    {
        if(ContextCompat.checkSelfPermission(ControlActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(ControlActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE))
            {
                new MaterialDialog.Builder(ControlActivity.this).title(R.string.Permission_title)
                        .content(R.string.Permission_content)
                        .negativeText(R.string.Permission_cancel)
                        .positiveText(R.string.Permission_agree_settings)
                        .onPositive(new MaterialDialog.SingleButtonCallback()
                        {
                            public void onClick(MaterialDialog dialog, DialogAction which)
                            {
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                intent.setData(uri);
                                startActivity(intent);
                            }
                        })
                        .canceledOnTouchOutside(true)
                        .show();
            }
            else
            {
                ActivityCompat.requestPermissions(ControlActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_PERMISSIONS_REQUEST_STORAGE_PERMISSIONS);
            }
        }
    }

    public void onRequestPermissionsResult(int requestcode, String permissions[], int[] grantResults)
    {
        switch(requestcode)
        {
            case MY_PERMISSIONS_REQUEST_STORAGE_PERMISSIONS:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    new MaterialDialog.Builder(ControlActivity.this).title("Permission Granted")
                            .content("Thank you for providing storage access")
                            .positiveText("OK")
                            .canceledOnTouchOutside(true)
                            .show();
                }
                else
                {
                    Log.d(TAG,"Permission denied!");
                }
        }
    }
}
