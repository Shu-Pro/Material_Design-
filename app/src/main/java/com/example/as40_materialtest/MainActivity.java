package com.example.as40_materialtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Adapter.FruitAdapter;
import pojo.Fruit;

public class MainActivity extends AppCompatActivity {
//    抽屉滑动菜单实例
    DrawerLayout drawerLayout;

    private SwipeRefreshLayout swipeRefresh;

    private List<Fruit> fruitList = new ArrayList<>();
    private FruitAdapter adapter;



    private Fruit[] fruits =
            {
            new Fruit("Apple", R.drawable.apple),
            new Fruit("Banana", R.drawable.banana),
            new Fruit("Orange",  R.drawable.apple),
            new Fruit("Watermelon",  R.drawable.apple),
            new Fruit("Pear",  R.drawable.apple),
            new Fruit("Grape", R.drawable.apple),
            new Fruit("Pineapple", R.drawable.banana),
            new Fruit("Strawberry", R.drawable.banana),
            new Fruit("Cherry", R.drawable.banana),
            new Fruit("Mango", R.drawable.banana)
            };

    private void initFruits() {
        fruitList.clear();
        for (int i = 0; i < 50; i++) {
            Random random = new Random();
            int index = random.nextInt(fruits.length);
            fruitList.add(fruits[index]);
        }
    }

    private void refreshFruits(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //表示下拉数据进度条停止2秒
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //将子线程转换到主线程
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //重新生成数据
                        initFruits();


                        //刷新适配器,让适配器将新数据进行重新展示
                        adapter.notifyDataSetChanged();

                        //表示刷新事件结束,隐藏刷新进度条
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        下拉刷新实现======================================================================================================
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        //设置刷新进度条颜色
        swipeRefresh.setColorSchemeResources(R.color.design_default_color_primary);
        //设置一个下拉刷新监听器,下拉了就执行这个方法
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruits();
            }
        });













//        标题栏和滚动菜单的内容实现======================================================================================================
        //将toolbar替换ActionBar进来
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //获得ActionBar实例，这里的ActionBar是由ToolBar实现的,所以得到的是ToolBar实例
        ActionBar ActionBar = getSupportActionBar();
        if (ActionBar !=null){
            //设置让导航按钮显示出来
//            Toolbar最左侧的这个按钮就叫作HomeAsUp按钮
            ActionBar.setDisplayHomeAsUpEnabled(true);
            //设置这个导航按钮的标志
            ActionBar.setHomeAsUpIndicator(R.drawable.left_get);
        }


        //滚动菜单设置
        //将水果放入集合
        initFruits();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //这里设定recyclerView的布局为gridLayout
        //gridLayout的参数1：是context，第二个是列数
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);





//        抽屉滑动实现======================================================================================================

        //得到抽屉滑动菜单的实例
        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        //解决菜单栏图标不显示的问题
        navigationView.setItemIconTintList(null);
        //设置默认选中
        navigationView.setCheckedItem(R.id.nav_call);

//        设置一个菜单项选中点击事件的监听器
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

//            点击了任意菜单项时，就会回调到onNavigationItemSelected() 方法中
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //关闭滑动抽屉
                drawerLayout.closeDrawers();
                return true;
            }
        });





//       浮动按钮的实现     ======================================================================================================
//
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进行提示并内嵌一个操作，并且有动画效果,Snackbar相当于Toast上加一个操作!
                Snackbar.make(v,"删除数据？",Snackbar.LENGTH_SHORT)
                        //内嵌操作
                        .setAction("确定",new View.OnClickListener(){

                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "删除成功",
                                        Toast.LENGTH_SHORT).show();
                            }


                        }).show();





            }
        });







    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.qt,menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.backup:
                Toast.makeText(this, "You clicked Backup", Toast.LENGTH_SHORT).
                        show();
                break;
            case R.id.delete:
                Toast.makeText(this, "You clicked Delete", Toast.LENGTH_SHORT).
                        show();
                break;
            case R.id.settings:
                Toast.makeText(this, "You clicked Settings", Toast.LENGTH_SHORT).
                        show();
                break;

                //HomeAsUp按钮的id永远都是android.R.id.home
            case android.R.id.home:
                //将滑动菜单显示出来
                drawerLayout.openDrawer(GravityCompat.START);
                break;

            default:
        }
        return true;
    }
}