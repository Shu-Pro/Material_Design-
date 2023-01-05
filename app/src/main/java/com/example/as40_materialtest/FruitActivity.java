package com.example.as40_materialtest;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class FruitActivity extends AppCompatActivity {
    public static final String FRUIT_NAME = "fruit_name";
    public static final String FRUIT_IMAGE_ID = "fruit_image_id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit);


        Intent intent = getIntent();
        String fruitName= intent.getStringExtra(FRUIT_NAME);
        int fruitImageId = intent.getIntExtra(FRUIT_IMAGE_ID, 0);

//        获取toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);

        //获取可折叠bar
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);


        //获得标题栏图片view
        ImageView fruitImageView = findViewById(R.id.fruit_image_view);

        //获取文本视图
        TextView textView = findViewById(R.id.fruit_content_text);

//        将toolbar设置为标题bar
        setSupportActionBar(toolbar);

        //获取标题bar
        ActionBar actionBar = getSupportActionBar();

        if (actionBar !=null){
            //在bar上设置返回箭头
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //设置折叠标题题目
        collapsingToolbarLayout.setTitle(fruitName);
        //将图片资源设置进入标题图片view中
        Glide.with(this).load(fruitImageId).into(fruitImageView);

        String fruitContent = generateFruitContent(fruitName);
        textView.setText(fruitContent);
    }


    private String generateFruitContent(String fruitName) {
        StringBuilder fruitContent = new StringBuilder();
        for (int i = 0; i < 500; i++) {
            fruitContent.append(fruitName);
        }
        return fruitContent.toString();
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //设置 actionBar.setDisplayHomeAsUpEnabled(true);的回调函数
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}