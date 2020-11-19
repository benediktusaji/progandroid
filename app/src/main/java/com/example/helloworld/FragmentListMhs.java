package com.example.helloworld;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helloworld.models.Mahasiswa;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class FragmentListMhs extends Fragment {
    private FirebaseFirestore db;
    private CollectionReference mahasiswaRef;
    private MyAdapterMhs adapter;
    private FirestoreRecyclerOptions<Mahasiswa> options;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_mhs, container, false);
        db = FirebaseFirestore.getInstance();
        Query query = db.collection("DaftarMhs");;
        options = new FirestoreRecyclerOptions.Builder<Mahasiswa>()
                .setQuery(query, Mahasiswa.class)
                .build();
        Log.d("coba", "onCreateView: List MHS");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new MyAdapterMhs(options);
        RecyclerView recyclerView = getView().findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter.setOnItemClickListener(new MyAdapterMhs.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                final DocumentSnapshot ds = documentSnapshot;
                Toast.makeText(requireActivity(), "coba" + documentSnapshot.get("nama"), Toast.LENGTH_SHORT).show();
                FragmentMhs.documentSnapshot = documentSnapshot;
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
