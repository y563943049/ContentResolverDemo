package com.example.contentresolverdemo;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_CALL_LOG},1);
        }else {

        }
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_CALL_LOG) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_CALL_LOG},2);
        }else {

        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                }else {

                }
                break;
            case 2:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                }else {

                }
                break;

        }
    }

    /**
     * 插入数据 / 增加数据
     */
    public void insert(View view) {
        /**
         * 得到内容解析器对象
         * */
        ContentResolver resolver = getContentResolver();
        /**
         * 创建uri对象
         * */
        Uri uri = Uri.parse("content://com.example/contactinfo");

        ContentValues values = new ContentValues();
        values.put("name", "还还");
        values.put("phone", "15162226176");
        Uri result = resolver.insert(uri, values);
        Toast.makeText(this, result.toString(), Toast.LENGTH_SHORT).show();
    }

    /**
     * 删除数据
     */
    public void delete(View view) {
        /**
         * 得到内容解析器对象
         * */
        ContentResolver resolver = getContentResolver();
        /**
         * 创建Uri对象
         * */
        Uri uri = Uri.parse("content://com.example/contactinfo");
        resolver.delete(uri, "name = ?", new String[]{"还还"});
    }

    /**
     * 修改数据 / 更新数据
     */
    public void update(View view) {
        /**
         * 得到内容解析器对象
         * */
        ContentResolver resolver = getContentResolver();
        /**
         * 创建Uri对象
         * */
        Uri uri = Uri.parse("content://com.example/contactinfo");

        ContentValues values = new ContentValues();
        values.put("phone", "15252098133");
        resolver.update(uri, values, "name = ?", new String[]{"还还"});
    }

    /**
     * 查询数据
     */
    public void query(View view) {

        /**
         * 得到内容解析器对象
         * */
        ContentResolver resolver = getContentResolver();
        /**
         * 创建Uri对象
         * */
        Uri uri = Uri.parse("content://com.example/contactinfo/");
        String type = resolver.getType(uri);
        if (type.startsWith("vnd.android.cursor.dir")) {
            //查询多条数据
            Cursor cursor = resolver.query(uri, null, null, null, null);
            while (cursor.moveToNext()) {
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                String phone = cursor.getString(2);
                Log.i(TAG, "id : " + id + ",name:" + name + ",phone:" + phone);
            }
        } else if (type.startsWith("vnd.android.cursor.item")) {
            //查询单条数据
            Cursor cursor = resolver.query(uri, null, null, null, null);
            while (cursor.moveToFirst()) {
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                String phone = cursor.getString(2);
                Log.i(TAG, "id : " + id + ",name:" + name + ",phone:" + phone);
            }
        }
    }

    /**
     * 读取通讯记录
     * Uri:content://call_log/calls
     */

    public void readCall(View view) {
        ContentResolver resolver = getContentResolver();
        Uri uri = Uri.parse("content://call_log/calls");
        Cursor cursor = resolver.query(uri, new String[]{"number","date","type"}, null, null, null);
        while (cursor.moveToNext()) {
            String phone = cursor.getString(0);
            String date = cursor.getString(1);
            String type = cursor.getString(2);
            Log.i(TAG, "phone:" + phone + ",date:" + date + ",type:" + type);
        }
        cursor.close();
    }

    /**
     * 插入通讯记录
     * */

    public void insertCall(View view){
        ContentResolver resolver = getContentResolver();
        Uri uri = Uri.parse("content://call_log/calls");
        ContentValues values = new ContentValues();
        values.put("number","15162226176");
        values.put("date",System.currentTimeMillis());
        values.put("type","1");
        values.put("duration",60);
        resolver.insert(uri,values);
    }




}
