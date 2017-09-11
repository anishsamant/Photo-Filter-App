package com.filter.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.filter.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by ANISH on 23-07-2017.
 */

public class Helper {
    public static Boolean writeDataIntoExternalStorage(Context context, String fileName, Bitmap bitmap)
    {
        File directory=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+context.getString(R.string.app_name));
        if(!directory.exists() && !directory.mkdir())
        {
            return false;
        }

        File file=new File(directory.getAbsolutePath()+"/"+fileName);
        if(file.exists() && !(file.canWrite()))
        {
            return false;
        }

        try
        {
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            return bitmap.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream);
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static File getFileFromExternalStorage(Context context,String filename)
    {
        File directory=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+context.getString(R.string.app_name));
        File file=new File(directory.getAbsolutePath()+"/"+filename);

        if(!file.exists() || !(file.canRead()))
        {
            return null;
        }

        return file;
    }
}
