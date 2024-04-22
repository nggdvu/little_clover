package com.nggdvu.littleclover.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nggdvu.littleclover.R;
import com.nggdvu.littleclover.models.Campaign;
import com.nggdvu.littleclover.models.Story;

import java.io.ByteArrayOutputStream;

public class AddStoryFragment extends Fragment {

    Spinner hashtagSpinner;
    Button chooseImageButton, saveButton, captureImageButton;
    ImageButton cancelButton;
    TextView hashtagTxt;
    ImageView uploadImg;
    Uri imageUri;
    String imageURL;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    StorageReference storageReference;
    DatabaseReference newStoryRef;
    FirebaseStorage storage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_add_story, container, false);

        uploadImg = fragmentView.findViewById(R.id.addstoryImg);
        chooseImageButton = fragmentView.findViewById(R.id.uploadImgBtn);
        saveButton = fragmentView.findViewById(R.id.saveBtn);
        hashtagSpinner = fragmentView.findViewById(R.id.hashtagSpinner);
        hashtagTxt = fragmentView.findViewById(R.id.hashtagTxt);
        cancelButton = fragmentView.findViewById(R.id.cancelBtn);
        captureImageButton = fragmentView.findViewById(R.id.captureImgBtn);

        storage = FirebaseStorage.getInstance();
        newStoryRef = FirebaseDatabase.getInstance().getReference().child("stories");

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertStoryData();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment homeFragment = new HomeFragment("photo", "hashtag", "image", "title", "aiming", "location", "description", "description", "time");
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_bottom_to_top, R.anim.exit_top_to_bottom);
                fragmentTransaction.replace(R.id.containerId, homeFragment);
                fragmentTransaction.commit();
            }
        });

        chooseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
        }

        captureImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });

        captureImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 101);
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

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            uploadImg.setImageBitmap(imageBitmap);
            imageUri = getImageUri(getContext(), imageBitmap);
            uploadData();
        } else if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            uploadImg.setImageURI(imageUri);
        } else {
            Toast.makeText(getActivity(), "Không có ảnh", Toast.LENGTH_SHORT).show();
        }
    }

    private Uri getImageUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }

    private void insertStoryData() {
        storageReference = FirebaseStorage.getInstance().getReference().child("stories").child(imageUri.getLastPathSegment());
        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete()) ;
                        Uri urlImage = uriTask.getResult();
                        imageURL = urlImage.toString();
                        uploadData();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Đăng ảnh thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void uploadData() {
        String hashtag = hashtagTxt.getText().toString();

        Story story = new Story(imageURL, hashtag);
        newStoryRef.push().setValue(story);

        HomeFragment homeFragment = new HomeFragment("photo", "hashtag", "image", "title", "aiming", "location", "description", "description", "time");
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_bottom_to_top, R.anim.exit_top_to_bottom);
        fragmentTransaction.replace(R.id.containerId, homeFragment);
        fragmentTransaction.commit();
        Toast.makeText(getContext(), "Đăng tải thành công!", Toast.LENGTH_SHORT).show();

    }
}