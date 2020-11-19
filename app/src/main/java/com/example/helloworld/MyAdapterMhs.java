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

import com.example.helloworld.models.Mahasiswa;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class MyAdapterMhs extends FirestoreRecyclerAdapter<Mahasiswa, MyAdapterMhs.MahasiswaViewHolder> {
    private OnItemClickListener listener;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MyAdapterMhs(@NonNull FirestoreRecyclerOptions options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MahasiswaViewHolder holder, int position, @NonNull Mahasiswa model) {
        holder.nim.setText(model.getNim());
        holder.nama.setText(model.getNama());
        holder.phone.setText(model.getPhone());
        Log.d("di dalam ada: ", "MahasiswaViewHolder: "+model.getNama());
    }

    @NonNull
    @Override
    public MahasiswaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_row_mhs, parent, false);
        return new MahasiswaViewHolder(view);
    }


    public class MahasiswaViewHolder extends RecyclerView.ViewHolder {

        TextView nim;
        TextView nama;
        TextView phone;

        public MahasiswaViewHolder(@NonNull View itemView) {
            super(itemView);

            nim = itemView.findViewById(R.id.txtNim);
            nama = itemView.findViewById(R.id.txtNama);
            phone = itemView.findViewById(R.id.txtPhone);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }

    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
