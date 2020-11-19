package com.example.helloworld;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.helloworld.models.Mahasiswa;
import com.example.helloworld.ui.main.PageViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FragmentMhs extends Fragment {
    private EditText noMhs;
    private EditText namaMhs;
    private EditText phoneMhs;
    private Button buttonSimpan;
    private Button buttonHapus;
    private FirebaseFirestore firebaseFirestoreDb;
    private PageViewModel viewModel;
    public static DocumentSnapshot documentSnapshot;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mhs, container, false);
        noMhs = view.findViewById(R.id.txtPhone);
        namaMhs = view.findViewById(R.id.namaMhs);
        phoneMhs = view.findViewById(R.id.phoneMhs);
        buttonSimpan = view.findViewById(R.id.simpanButton);
        buttonHapus = view.findViewById(R.id.hapusButton);
        firebaseFirestoreDb = FirebaseFirestore.getInstance();

        buttonSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sanity check
                if (!noMhs.getText().toString().isEmpty() && !namaMhs.getText().toString().isEmpty()) {
                    tambahMahasiswa();
                } else {
                    Toast.makeText(requireActivity(), "No dan Nama Mhs tidak boleh kosong",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        buttonHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDataMahasiswa();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.updateData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(PageViewModel.class);

    }

    private void tambahMahasiswa() {
        if(documentSnapshot != null){
            this.deleteDataMahasiswa();
        }
        Mahasiswa mhs = new Mahasiswa(noMhs.getText().toString(),
                namaMhs.getText().toString(),
                phoneMhs.getText().toString());

        firebaseFirestoreDb.collection("DaftarMhs").document().set(mhs)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(requireActivity(), "Mahasiswa berhasil didaftarkan",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireActivity(), "ERROR" + e.toString(),
                                Toast.LENGTH_SHORT).show();
                        Log.d("TAG", e.toString());
                    }
                });
    }
    private void deleteDataMahasiswa() {
        firebaseFirestoreDb.collection("DaftarMhs").document(documentSnapshot.getId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        noMhs.setText("");
                        namaMhs.setText("");
                        phoneMhs.setText("");
                        Toast.makeText(requireActivity(), "Mahasiswa berhasil dihapus",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireActivity(), "Error deleting document: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateData() {
        if(documentSnapshot != null){
            noMhs.setText((String) documentSnapshot.get("nim"));
            namaMhs.setText((String)documentSnapshot.get("nama"));
            phoneMhs.setText((String)documentSnapshot.get("phone"));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(documentSnapshot != null){
            noMhs.setText((String) documentSnapshot.get("nim"));
            namaMhs.setText((String)documentSnapshot.get("nama"));
            phoneMhs.setText((String)documentSnapshot.get("phone"));
        }
        Log.d("TAG", "onResume: remuse buos");
    }

    @Override
    public void onPause() {
        super.onPause();
        if(documentSnapshot != null){
            noMhs.setText((String) documentSnapshot.get("nim"));
            namaMhs.setText((String)documentSnapshot.get("nama"));
            phoneMhs.setText((String)documentSnapshot.get("phone"));
        }
        Log.d("TAG", "onPause: on pusea buos");
    }
}
