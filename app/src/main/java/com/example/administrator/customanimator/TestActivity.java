package com.example.administrator.customanimator;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

/**
 * Created by Lei Xiaoyue on 2015-11-18.
 */
public class TestActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyView view = new MyView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addContentView(view, params);
    }
}
