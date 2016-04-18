package me.zhf.myapplication.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import me.zhf.myapplication.API.Constant;
import me.zhf.myapplication.API.productAPI;
import me.zhf.myapplication.R;
import me.zhf.myapplication.model.Qhtwxproduct;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ZF on 2016/4/15.
 */
public class RecyclerViewFragment extends Fragment {
    private static final String TAG = "RecyclerViewFragment";

    protected RecyclerView mRecyclerView;
    protected CustomAdapter mAdapter;
    //protected List<Qhtwxproduct> mDataset;
    protected RecyclerView.LayoutManager mLayoutManager;

    TextView btn1;
    TextView btn2;
    TextView btn3;
    private LinearLayout loading;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize dataset, this data would usually come from a local content provider or
        // remote server.
        //initDataset();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.recycler_view_frag, container, false);
        rootView.setTag(TAG);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        mLayoutManager = new LinearLayoutManager(getActivity());
        //mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(0);

        //mRecyclerView.addItemDecoration(
        //        new DividerItemDecoration(container.getContext(),R.drawable.divider_bg));
        loading = (LinearLayout) rootView.findViewById(R.id.loading);
        getProductFromServer("8a80915a532ad5c201532b161f5500f5");
        mRecyclerView.addOnScrollListener(new ScrollListListener());

        btn1 = (TextView) rootView.findViewById(R.id.btn1);
        btn2 = (TextView) rootView.findViewById(R.id.btn2);
        btn3 = (TextView) rootView.findViewById(R.id.btn3);

        btn1.setOnClickListener(btnClicklistener);
        btn2.setOnClickListener(btnClicklistener);
        btn3.setOnClickListener(btnClicklistener);


        return rootView;
    }

    TextView.OnClickListener btnClicklistener = new TextView.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn1) {
                getProductFromServer("8a80915a532ad5c201532b161f5500f5");
                Toast.makeText(v.getContext(), "全部", Toast.LENGTH_SHORT).show();
            } else if (v.getId() == R.id.btn2) {
                getProductFromServer("8a80915a5356a3a00153745f80630262");
                Toast.makeText(v.getContext(), "精选", Toast.LENGTH_SHORT).show();

            } else if (v.getId() == R.id.btn3) {
                getProductFromServer("8a80915a5356a3a00153745fc9160267");
                Toast.makeText(v.getContext(), "基地", Toast.LENGTH_SHORT).show();

            }
        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        CustomAdapter.AnimateFirstDisplayListener.displayedImages.clear();
    }

    private class ScrollListListener extends RecyclerView.OnScrollListener {
        //设置滑动时不加载图片
        ImageLoader imageLoader = ImageLoader.getInstance();

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

            switch (newState) {
                case RecyclerView.SCROLL_STATE_DRAGGING:
                    //正在滑动
                    imageLoader.pause();
                    break;
                case RecyclerView.SCROLL_STATE_IDLE:
                    //滑动停止
                    imageLoader.resume();
                    break;
            }
        }
    }

    public void getProductFromServer(String ptype) {
        mRecyclerView.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constant.SERVER_URL).build();
        productAPI productApi = restAdapter.create(productAPI.class);
        productApi.getProduct(ptype, new Callback<List<Qhtwxproduct>>() {
            @Override
            public void success(List<Qhtwxproduct> qhtwxproducts, Response response) {
                //Log.d("zf",qhtwxproducts.get(0).getQTSWXPRODUCTNAME());
                if (mAdapter == null) {
                    //mDataset = qhtwxproducts;
                    mAdapter = new CustomAdapter(qhtwxproducts);
                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    mAdapter.swap(qhtwxproducts);
                }
                mRecyclerView.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.d("zf", retrofitError.getMessage());
            }
        });

    }
}
