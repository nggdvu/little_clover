package com.nggdvu.littleclover.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nggdvu.littleclover.R;
import com.nggdvu.littleclover.models.Campaign;
import com.nggdvu.littleclover.models.Story;

public class AddStoryFragment extends Fragment {

    Spinner hashtagSpinner;
    Button chooseImageButton, saveButton;
    TextView hashtagTxt;
    ImageView uploadImg;
    Uri imageUri;
    String imageURL;
    private static final int PICK_IMAGE_REQUEST = 1;
    StorageReference storageReference;
    CollectionReference storiesCollection;
    FirebaseStorage storage;
    FirebaseFirestore firestore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_add_story, container, false);

        uploadImg = fragmentView.findViewById(R.id.addstoryImg);
        chooseImageButton = fragmentView.findViewById(R.id.uploadImgBtn);
        saveButton = fragmentView.findViewById(R.id.saveBtn);
        hashtagSpinner = fragmentView.findViewById(R.id.hashtagSpinner);
        hashtagTxt = fragmentView.findViewById(R.id.hashtagTxt);

        storage = FirebaseStorage.getInstance();
        firestore = FirebaseFirestore.getInstance();
        storiesCollection = firestore.collection("stories");

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertStoryData();
            }
        });

        chooseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });

        String[] campaignTypes = getResources().getStringArray(R.array.hashtag);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, campaignTypes);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hashtagSpinner.setAdapter(spinnerAdapter);
        hashtagSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedText = parent.getItemAtPosition(position).toString();
                hashtagTxt.setText(selectedText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return fragmentView;
    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Chọn ảnh"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            uploadImg.setImageURI(imageUri);
        } else {
            Toast.makeText(getActivity(), "Không có hình ảnh nào được chọn", Toast.LENGTH_SHORT).show();
        }
    }

    private void insertStoryData() {
        // Upload image to Cloud Storage
        storageReference = FirebaseStorage.getInstance().getReference().child("stories").child(imageUri.getLastPathSegment());
        storageReference.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> storageReference.getDownloadUrl())
                .addOnSuccessListener(uri -> {
                    imageURL = uri.toString();
                    uploadData();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Đăng ảnh thất bại", Toast.LENGTH_SHORT).show();
                });
    }

    private void uploadData() {
        String hashtag = hashtagTxt.getText().toString();

        Story story = new Story(imageURL, hashtag);

        storiesCollection.add(story)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Đăng tải thành công!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Đăng tải thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}