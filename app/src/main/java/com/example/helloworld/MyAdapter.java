package com.example.helloworld;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    String s1[], s2[];
    int img[];
    Context context;

    public MyAdapter(Context context, String s1[], String s2[], int img[]){
        this.context = context;
        this.s1 = s1;
        this.s2 = s2;
        this.img = img;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.movieTitle.setText(s1[position]);
        holder.movieDesc.setText(s2[position]);
        holder.moviePoster.setImageResource(img[position]);
        Log.d("movie", "onBindViewHolder: "+s1[position]);
    }

    @Override
    public int getItemCount() {
        return s1.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView movieTitle, movieDesc;
        ImageView moviePoster;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            movieTitle = itemView.findViewById(R.id.movieTitle);
            movieDesc = itemView.findViewById(R.id.movieDesc);
            moviePoster = itemView.findViewById(R.id.moviePoster);
        }
    }
}
