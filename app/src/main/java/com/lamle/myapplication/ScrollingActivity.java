package com.lamle.myapplication;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import com.lamle.myapplication.databinding.ActivityScrollingBinding;

import java.util.concurrent.atomic.AtomicInteger;

public class ScrollingActivity extends AppCompatActivity {

    private ActivityScrollingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityScrollingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) binding.image.getLayoutParams();

        int imageMarginTop = Math.round(convertDpToPixel(32, this));

        final Rect scrollBounds = new Rect();
        binding.scrollView.getHitRect(scrollBounds);
        AtomicInteger menuPosition = new AtomicInteger(-1);
        binding.scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {

            Log.d("onScrollChanged", "onScrollChanged: " + scrollY + " --- " + scrollX);

            if (binding.menu.getLocalVisibleRect(scrollBounds)) {
                if (!binding.menu.getLocalVisibleRect(scrollBounds)
                        || scrollBounds.height() < binding.menu.getHeight()) {
                    Log.i("onScrollChanged", "BTN APPEAR PARCIALY");
                    if (menuPosition.get() == -1) {
                        menuPosition.set(scrollY);
                    }

                    params.setMargins(0, imageMarginTop + menuPosition.get() - scrollY, 10, 0);
                    binding.image.setLayoutParams(params);
                } else {
                    Log.i("onScrollChanged", "BTN APPEAR FULLY!!!");
                }
            } else {
                Log.i("onScrollChanged", "No");
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    public float convertDpToPixel(float dp, Context context){
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}