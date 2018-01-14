package com.example.mikel.granny.Controller;

import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.example.mikel.granny.R;

import java.io.IOException;

/**
 * Created by mikel on 2018/1/13.
 */

public class WallpaperController {
    private Context context;
    public WallpaperController(Context context){
        this.context = context;
    }

    @SuppressLint("ResourceType")
    public void changeWallPaper(int usecase) throws IOException {
        WallpaperManager wpm = WallpaperManager.getInstance(context);
//        Uri uri = Uri.parse("android.resource://com.example.mikel.granny/drawable/wp");
        switch (usecase){
            case 2:
                wpm.setResource(R.drawable.wp2);
                break;
            case 3:
                wpm.setResource(R.drawable.wp3);
                break;
            case 4:
                wpm.setResource(R.drawable.wp4);
                break;
            case 5:
                wpm.setResource(R.drawable.wp5);
                break;
            case 6:
                wpm.setResource(R.drawable.wp6);
                break;
        }

    }
}
