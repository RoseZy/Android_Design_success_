package com.example.apple.android_design;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.apple.android_design.R.id.view;
import static com.example.apple.android_design.R.menu.toolbar;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    private  Fruit[]fruits={new Fruit("苹果呀",R.drawable.apple),
                            new Fruit("香蕉呀",R.drawable.banana),
                            new Fruit("橘子呀",R.drawable.orange),
                            new Fruit("西瓜呀",R.drawable.watermelon),
                            new Fruit("梨子呀",R.drawable.pear),
                            new Fruit("葡萄呀",R.drawable.grape),
                            new Fruit("菠萝呀",R.drawable.pineapple),
                            new Fruit("草莓呀",R.drawable.strawberry),
                            new Fruit("樱桃呀",R.drawable.cherry),
                            new Fruit("芒果呀",R.drawable.mango)    };
    private List<Fruit>fruitList=new ArrayList<>();
    private FruitAdapter adapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFruit();
        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruits();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toobar);
        setSupportActionBar(toolbar);
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_loyout);
        NavigationView navigationView= (NavigationView) findViewById(view);
        ActionBar actionBar=getSupportActionBar();

        FloatingActionButton fab= (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"Data deleted",Snackbar.LENGTH_SHORT).
                        setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtil.showToast(MainActivity.this,"亲，点我干啥");
                            }
                        })
                        .show();
            }
        });

        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.menu);
        }
        navigationView.setCheckedItem(R.id.call);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

    }

    private void refreshFruits() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                   e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFruit();
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    private void initFruit() {
        fruitList.clear();
        for (int i=0;i<50;i++){
            Random random=new Random();
            int index=random.nextInt(fruits.length);
            fruitList.add(fruits[index]);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;

            case R.id.backup:
                ToastUtil.showToast(MainActivity.this,"你是不是傻，点击了备份");
                break;

            case R.id.delete:
                ToastUtil.showToast(MainActivity.this,"你很棒棒，点击了删除");
                break;

            case R.id.setings:
                ToastUtil.showToast(MainActivity.this,"你知道的太多了，点击了设置");
                break;
            default:

        }
        return true;
    }
}
