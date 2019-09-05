package com.eleganzit.workmanagerexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.widget.AbsListView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main2Activity extends AppCompatActivity implements InfiniteScrollListener.OnLoadMoreListener {

    private String TAG=Main2Activity.class.getSimpleName();
    private String TAG1=MyWorker.class.getSimpleName();
SwipeRefreshLayout pullToRefresh;
    ArrayList<VideoImage> dataArrayList=new ArrayList<>();
    ArrayList<String> videoarray=new ArrayList<>();
    ArrayList<String> imagearray=new ArrayList<>();
    LinearLayoutManager layoutManager;
    InfiniteScrollListener infiniteScrollListener;
    RecyclerView rc_posts;
    private static int firstVisibleInListview;
    int currentitems,totalitems,scrollitem;
    boolean isScrolling;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        pullToRefresh=findViewById(R.id.pullToRefresh);
        rc_posts=findViewById(R.id.rc_posts);
        layoutManager=new LinearLayoutManager(Main2Activity.this, LinearLayoutManager.VERTICAL,false);
        rc_posts.setLayoutManager(layoutManager);
        firstVisibleInListview = layoutManager.findFirstVisibleItemPosition();
        infiniteScrollListener = new InfiniteScrollListener(layoutManager, this);
        infiniteScrollListener.setLoaded();
        //rc_posts.setNestedScrollingEnabled(false);
        rc_posts.addOnScrollListener(infiniteScrollListener);

       //getIMage();
       getVideo();


        rc_posts.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                {
                    isScrolling=true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentitems=layoutManager.getChildCount();
                totalitems=layoutManager.getItemCount();
                scrollitem=layoutManager.findFirstVisibleItemPosition();


                int currentFirstVisible = layoutManager.findFirstVisibleItemPosition();
                if (isScrolling && (currentitems+scrollitem==totalitems))
                {
                    isScrolling=false;

                    if(currentFirstVisible > firstVisibleInListview)
                    {
                        getVideo(totalitems);
                        Log.d("ttttt","if"+totalitems);
                    }
                    else {
                        getVideo(totalitems);
                        Log.d("ttttt","else"+totalitems);
                    }

                    firstVisibleInListview = currentFirstVisible;

                }





            }
        });

    }

    private void getVideo(final int limit) {
        Log.d("zdssad","called");
        MyInterface myInterface= RetrofitAPI.getRetrofit().create(MyInterface.class);
        Call<VideoFeedlistResponse> call=myInterface.videoFeedlist(""+limit,"1","3");
        call.enqueue(new Callback<VideoFeedlistResponse>() {

            @Override
            public void onResponse(Call<VideoFeedlistResponse> call, Response<VideoFeedlistResponse> response) {

               if (response.body().getData()!=null)
               {
                   Log.d(TAG, "" + response.body().getData().size());
                   for (int i=0;i<response.body().getData().size();i++) {

                       VideoImage videoImage=new VideoImage(""+response.body().getData().get(i).getVideoTitle(),"");
                       dataArrayList.add(videoImage);
                       Log.d(TAG1, limit+"  " + response.body().getData().get(i).getVideoLink());

                       videoarray.add(""+i);

                   }


                   rc_posts.getAdapter().notifyDataSetChanged();
                   int a=imagearray.size()+1;
                   Log.d("sizeimage if",""+a);
                   getIMage(a);
               }
               else
               {
                   int a=imagearray.size()+1;
                   Log.d("sizeimage else",""+a);
                   getIMage(a);
               }

            }

            @Override
            public void onFailure(Call<VideoFeedlistResponse> call, Throwable t) {

            }
        });
    }

    private void getVideo() {
        MyInterface myInterface= RetrofitAPI.getRetrofit().create(MyInterface.class);
        Call<VideoFeedlistResponse> call=myInterface.videoFeedlist("1","1","3");
        call.enqueue(new Callback<VideoFeedlistResponse>() {

            @Override
            public void onResponse(Call<VideoFeedlistResponse> call, Response<VideoFeedlistResponse> response) {


                Log.d(TAG, "" + response.body().getData().size());
               for (int i=0;i<response.body().getData().size();i++) {
                   Log.d(TAG1, "  " + response.body().getData().get(i).getVideoTitle());

                   //  Log.d(TAG, "" + response.body().getData().get(i).getVideoTitle());
                   VideoImage videoImage=new VideoImage(""+response.body().getData().get(i).getVideoLink(),"");
                   dataArrayList.add(videoImage);
                   videoarray.add(""+i);
               }
                rc_posts.setAdapter(new OpportuniesAdapter(dataArrayList,Main2Activity.this));
               int a=imagearray.size()+1;
               getIMage(a);
            }

            @Override
            public void onFailure(Call<VideoFeedlistResponse> call, Throwable t) {

            }
        });
    }

    private void getIMage() {
        MyInterface myInterface= RetrofitAPI.getRetrofit().create(MyInterface.class);
        Call<ImageFeedlistResponse> call=myInterface.imageFeedlist("1","1");
        call.enqueue(new Callback<ImageFeedlistResponse>() {

            @Override
            public void onResponse(Call<ImageFeedlistResponse> call, Response<ImageFeedlistResponse> response) {

                Log.d(TAG, "" + response.body().getData().size());

                for (int i=0;i<response.body().getData().size();i++) {
                   // Log.d(TAG,""+response.body().getData().get(i).getImageTitle());
                    VideoImage videoImage=new VideoImage("",""+response.body().getData().get(i).getImageTitle());
                    dataArrayList.add(videoImage);
                }
                rc_posts.getAdapter().notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<ImageFeedlistResponse> call, Throwable t) {

            }
        });

    }


    private void getIMage(int j) {
        MyInterface myInterface= RetrofitAPI.getRetrofit().create(MyInterface.class);
        Call<ImageFeedlistResponse> call=myInterface.imageFeedlist(""+j,"1");
        call.enqueue(new Callback<ImageFeedlistResponse>() {

            @Override
            public void onResponse(Call<ImageFeedlistResponse> call, Response<ImageFeedlistResponse> response) {

             //   Log.d(TAG, "" + response.body().getData().size());

                if (response.body().getData()!=null)
                {
                    for (int i=0;i<response.body().getData().size();i++) {
                        // Log.d(TAG,""+response.body().getData().get(i).getImageTitle());
                        VideoImage videoImage=new VideoImage("",""+response.body().getData().get(i).getImagePath());
                        dataArrayList.add(videoImage);
                        imagearray.add(""+i);
                    }
                    rc_posts.getAdapter().notifyDataSetChanged();

                }

            }

            @Override
            public void onFailure(Call<ImageFeedlistResponse> call, Throwable t) {

            }
        });

    }
    @Override
    public void onLoadMore() {

    }
}
