package com.filter.utility;

import android.content.Context;
import android.graphics.Bitmap;

import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubfilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubfilter;
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubfilter;
import com.zomato.photofilters.imageprocessors.subfilters.VignetteSubfilter;

/**
 * Created by ANISH on 23-07-2017.
 */

public class TransformImage {
    public static final int MAX_BRIGHTNESS=200;
    public static final int MAX_SATURATION=10;
    public static final int MAX_CONTRAST=20;
    public static final int MAX_VIGNETTE=255;

    public static final int DEFAULT_BRIGHTNESS=90;
    public static final int DEFAULT_SATURATION=3;
    public static final int DEFAULT_CONTRAST=10;
    public static final int DEFAULT_VIGNETTE=150;

    private String mFilename;
    private Context mContext;
    private Bitmap mBitmap;

    private Bitmap brightnessFilteredBitmap;
    private Bitmap saturationFilteredBitmap;
    private Bitmap contrastFilteredBitmap;
    private Bitmap vignetteFilteredBitmap;

    public static int FILTER_BRIGHTNESS=0;
    public static int FILTER_SATURATION=1;
    public static int FILTER_CONTRAST=2;
    public static int FILTER_VIGNETTE=3;

    public String getFileName(int filter)
    {
        if(filter==FILTER_BRIGHTNESS)
        {
            return mFilename+"_brightness.png";
        }
        else if(filter==FILTER_SATURATION)
        {
            return mFilename+"_saturation.png";
        }
        else if(filter==FILTER_CONTRAST)
        {
            return mFilename+"_contrast.png";
        }
        else if(filter==FILTER_VIGNETTE)
        {
            return mFilename+"_vignette.png";
        }
        return mFilename;
    }

    public Bitmap getBitmap(int filter)
    {
        if(filter==FILTER_BRIGHTNESS)
        {
            return brightnessFilteredBitmap;
        }
        else if(filter==FILTER_SATURATION)
        {
            return saturationFilteredBitmap;
        }
        else if(filter==FILTER_CONTRAST)
        {
            return contrastFilteredBitmap;
        }
        else if(filter==FILTER_VIGNETTE)
        {
            return vignetteFilteredBitmap;
        }
        return mBitmap;
    }

    public TransformImage(Context context,Bitmap bitmap)
    {
        mContext=context;
        mBitmap=bitmap;
        mFilename=System.currentTimeMillis()+"";
    }

    public Bitmap applyBrightnessSubFilter(int brightness)
    {
        Filter myFilterBrightness = new Filter();

        Bitmap workingBitmap=Bitmap.createBitmap(mBitmap);
        Bitmap mutableBitmap=workingBitmap.copy(Bitmap.Config.ARGB_8888,true);

        myFilterBrightness.addSubFilter(new BrightnessSubfilter(brightness));

        Bitmap outputImage = myFilterBrightness.processFilter(mutableBitmap);

        brightnessFilteredBitmap=outputImage;

        return outputImage;
    }

    public Bitmap applySaturationSubFilter(int saturation)
    {
        Filter myFilterSaturation = new Filter();

        Bitmap workingBitmap=Bitmap.createBitmap(mBitmap);
        Bitmap mutableBitmap=workingBitmap.copy(Bitmap.Config.ARGB_8888,true);

        myFilterSaturation.addSubFilter(new SaturationSubfilter(saturation));

        Bitmap outputImage = myFilterSaturation.processFilter(mutableBitmap);

        saturationFilteredBitmap=outputImage;

        return outputImage;
    }

    public Bitmap applyContrastSubFilter(int contrast)
    {
        Filter myFilterContrast = new Filter();

        Bitmap workingBitmap=Bitmap.createBitmap(mBitmap);
        Bitmap mutableBitmap=workingBitmap.copy(Bitmap.Config.ARGB_8888,true);

        myFilterContrast.addSubFilter(new ContrastSubfilter(contrast));

        Bitmap outputImage = myFilterContrast.processFilter(mutableBitmap);

        contrastFilteredBitmap=outputImage;

        return outputImage;
    }

    public Bitmap applyVignetteSubFilter(int vignette)
    {
        Filter myFilterVignette = new Filter();

        Bitmap workingBitmap=Bitmap.createBitmap(mBitmap);
        Bitmap mutableBitmap=workingBitmap.copy(Bitmap.Config.ARGB_8888,true);

        myFilterVignette.addSubFilter(new VignetteSubfilter(mContext,vignette));

        Bitmap outputImage = myFilterVignette.processFilter(mutableBitmap);

        vignetteFilteredBitmap=outputImage;

        return outputImage;
    }
}
