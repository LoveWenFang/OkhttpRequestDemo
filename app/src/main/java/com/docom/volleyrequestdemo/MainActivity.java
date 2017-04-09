package com.docom.volleyrequestdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.okhttp.Request;

import imx6_device.docom.com.network.OkHttpClientManager;

/**
 * 对okhttp二次封装的网络数据请求类
 * 使用network-debug.arr包
 * app的build.gradle中添加
 * repositories {
 * flatDir {
 * dirs 'libs'
 * }
 * }
 * dependencies {
 * compile(name: 'network-debug', ext: 'aar')
 * compile files('build/intermediates/exploded-aar/network-debug/jars/classes.jar')
 * compile 'com.squareup.okio:okio:1.11.0'
 * compile 'com.squareup.okhttp:okhttp:2.7.5'
 * compile 'com.google.code.gson:gson:2.8.0'
 * }
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button btn;
    private TextView tv;
    private Button btn2;
    private TextView tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


    }

    private void initView() {
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(this);
        tv = (TextView) findViewById(R.id.tv);
        btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(this);
        tv2 = (TextView) findViewById(R.id.tv2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                String url = "http://112.74.131.57:3024/Service1.asmx/GetDisplayInfo";
                postRequest(url);
                break;
            case R.id.btn2:
                String url2 = "http://www.iwens.org/School_Sky/categoryboy.php";
                getRequest(url2);
                break;
        }
    }

    private void postRequest(String url) {
        Log.e("----", "------------");
        //post请求的参数
        OkHttpClientManager.Param[] params = {
                new OkHttpClientManager.Param("Json", "{\"MerchantCode\":\"S20161005\"}")
        };
        //请求结果的回调
        OkHttpClientManager.ResultCallback<String> resultCallback = new OkHttpClientManager.ResultCallback<String>() {

            //请求失败
            @Override
            public void onError(Request request, Exception e) {

            }

            //请求成功
            @Override
            public void onResponse(String o) {
                Log.e("----", o);
                //截取字符串
                String s = o.substring(o.indexOf("{"), o.length());
                s = s.replace("</string>", "").trim();
                Log.e("----s", s);
                tv.setText(s);
            }
        };

        //初始化请求
        OkHttpClientManager.postAsyn(url, resultCallback, params);


//        OkHttpClientManager.postAsyn("http://112.74.131.57:3024/Service1.asmx/GetDisplayInfo",
//                new OkHttpClientManager.ResultCallback<String>() {
//                    //请求失败的回调
//                    @Override
//                    public void onError(Request request, Exception e) {
//`
//                    }
//                    //请求成功的回调
//                    @Override
//                    public void onResponse(String o) {
//                        Log.e("----", o);
//                        String s = o.substring(o.indexOf("{"), o.length());
//                        s = s.replace("</string>", "").trim();
//                        Log.e("----s", s);
//                        tv.setText(s);
//                    }
//                }, OkHttpClientManager.Param[] jsons = {
//        new OkHttpClientManager.Param("Json", "{\"MerchantCode\":\"S20161005\"}")
//    });
    }

    public void getRequest(String url) {
        OkHttpClientManager.ResultCallback<String> resultCallback = new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String s) {
                tv2.setText(s);
            }
        };
        OkHttpClientManager.getAsyn(url, resultCallback);
    }
}