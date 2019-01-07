package com.x930073498.layoutadapter;

import android.util.Log;

import com.x930073498.Register;

/**
 * Created by x930073498 on 2019/1/7 0007.
 */
public class TestRegister implements Register {
    @Override
    public void run() {
        Log.e("test", "test");
    }

    @Override
    public String getId() {
        return "121";
    }
}
