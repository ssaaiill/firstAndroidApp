package me.zhf.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.List;

import me.zhf.myapplication.API.Constant;
import me.zhf.myapplication.API.productAPI;
import me.zhf.myapplication.Fragment.LoginFragment;
import me.zhf.myapplication.Fragment.RecyclerViewFragment;
import me.zhf.myapplication.Fragment.TwoFragment;
import me.zhf.myapplication.model.Qhtwxproduct;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class NDActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    //ViewPager mViewPager;
    FrameLayout frameLayout;
    List<Fragment> fragmentList;
    LoginFragment loginFragment;
    TwoFragment twoFragment;
    RecyclerViewFragment recyclerViewFragment;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nd);
        //
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.ab_search) {
                    Toast.makeText(NDActivity.this, "1111", Toast.LENGTH_SHORT).show();
                    Log.d("zf", "MENU----------1");
                    setFragment(3);
                } else if (item.getItemId() == R.id.action_share) {
                    Toast.makeText(NDActivity.this, "222222", Toast.LENGTH_SHORT).show();
                    Log.d("zf", "MENU----------2");
                    getProductFromServer();

                } else if (item.getItemId() == R.id.action_settings) {
                    Toast.makeText(NDActivity.this, "3333333", Toast.LENGTH_SHORT).show();
                    Log.d("zf", "MENU----------333333");
                }
                return false;

            }
        });
        //
        //        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //        fab.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View view) {
        //                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //                        .setAction("Action", null).show();
        //            }
        //        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        fragmentManager = getSupportFragmentManager();
//        fragmentList = new ArrayList<Fragment>();
//        loginFragment = new LoginFragment();
//        twoFragment = new TwoFragment();
//
//        fragmentList.add(loginFragment);
//        fragmentList.add(twoFragment);
        //mViewPager.setAdapter(new MyFrageStatePagerAdapter(getSupportFragmentManager()));

        initImageLoader(this);
    }

    private void setFragment(int i) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (loginFragment != null) {
            transaction.hide(loginFragment);
        }
        if (twoFragment != null) {
            transaction.hide(twoFragment);
        }
        if (recyclerViewFragment != null) {
            transaction.hide(recyclerViewFragment);
        }

        if (i == 1) {
            if (loginFragment == null) {
                loginFragment = new LoginFragment();
                transaction.add(R.id.frameLayout, loginFragment);
            } else {
                transaction.show(loginFragment);
            }
        } else if (i == 2) {
            if (twoFragment == null) {
                twoFragment = new TwoFragment();
                transaction.add(R.id.frameLayout, twoFragment);
            } else {
                transaction.show(twoFragment);
            }
        } else  if(i==3){
            if (recyclerViewFragment == null) {
                recyclerViewFragment = new RecyclerViewFragment();
                transaction.add(R.id.frameLayout, recyclerViewFragment);
            } else {
                transaction.show(recyclerViewFragment);
            }

        }
        transaction.commit();
    }

    class MyFrageStatePagerAdapter extends FragmentStatePagerAdapter {


        public MyFrageStatePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nd, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            setFragment(1);
        } else if (id == R.id.nav_gallery) {
            setFragment(2);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        Log.d("zf", item.getTitle().toString());
        toolbar.setTitle(item.getTitle());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static void initImageLoader(Context context) {

        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }

    public  void  getProductFromServer(){
        String ptype="8a80915a532ad5c201532b161f5500f5";
        RestAdapter restAdapter = new RestAdapter.Builder()
                        .setEndpoint(Constant.SERVER_URL).build();
        productAPI productApi = restAdapter.create(productAPI.class);
        productApi.getProduct(ptype, new Callback<List<Qhtwxproduct>>() {
            @Override
            public void success(List<Qhtwxproduct> qhtwxproducts, Response response) {
                Log.d("zf",qhtwxproducts.get(0).getQTSWXPRODUCTNAME());
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.d("zf",retrofitError.getMessage());
            }
        });

    }
}
