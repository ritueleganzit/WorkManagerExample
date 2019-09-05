package com.eleganzit.workmanagerexample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class OpportuniesAdapter  extends RecyclerView.Adapter<OpportuniesAdapter.MyViewHolder> {

    List<VideoImage> opportunitiesListData;
    Context context;
    Activity activity;

    public OpportuniesAdapter(List<VideoImage> opportunitiesListData, Context context) {
        this.opportunitiesListData = opportunitiesListData;
        this.context = context;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_row,viewGroup,false);

        MyViewHolder myViewHolder=new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

        VideoImage videoFeedlist=opportunitiesListData.get(i);
        int j=i+1;

        if (videoFeedlist.getVideotitle()!=null && !videoFeedlist.getVideotitle().isEmpty()) {
            holder.mWebView.setVisibility(View.VISIBLE);
            String url = videoFeedlist.getVideotitle();
            if (url!=null && !url.isEmpty())
            {
                String videoId[] = url.split("v="); //you will get this video id "tResEeK6P5I"

                if (videoId.length>0) {
                    String thumbnailMq = "http://img.youtube.com/vi/" + videoId[0] + "/mqdefault.jpg";


                    Glide
                            .with(context)
                            .load(thumbnailMq)
                            .into(holder.mWebView);
                }
            }


        }
        else
        {
            holder.mWebView.setVisibility(View.GONE);

        }
        if (videoFeedlist.getImgtitle()!=null && !videoFeedlist.getImgtitle().isEmpty()) {
            holder.tv.setVisibility(View.VISIBLE);
            Glide
                    .with(context)
                    .load(videoFeedlist.getImgtitle())
                    .into(  holder.tv);
        }
        else
        {
            holder.tv.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return opportunitiesListData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView mWebView;
        ImageView tv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv=itemView.findViewById(R.id.tv);
            mWebView=itemView.findViewById(R.id.txt);
        }
    }

}
