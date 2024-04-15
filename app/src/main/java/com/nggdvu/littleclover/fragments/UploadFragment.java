package com.nggdvu.littleclover.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nggdvu.littleclover.R;
import com.nggdvu.littleclover.models.Campaign;

import java.text.DecimalFormat;

public class UploadFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    ImageView uploadImg;
    Uri imageUri;
    StorageReference storageReference;
    TextView selectedDate, sortTxt;
    EditText titleEditText, moneyEditText, locationEditText, descriptionEditText;
    String imageURL;
    Spinner campaignTypeSpinner;
    private Button chooseImageButton, pickDateButton, saveButton;
    DatabaseReference newCampaignRef;
    FirebaseStorage storage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload, container, false);

        uploadImg = view.findViewById(R.id.uploadImg);
        chooseImageButton = view.findViewById(R.id.uploadImgBtn);
        pickDateButton = view.findViewById(R.id.pickDateBtn);
        selectedDate = view.findViewById(R.id.textViewSelectedDate);
        moneyEditText = view.findViewById(R.id.moneyTxt);
        titleEditText = view.findViewById(R.id.titleTxt);
        locationEditText = view.findViewById(R.id.locationTxt);
        descriptionEditText = view.findViewById(R.id.descriptionTxt);
        saveButton = view.findViewById(R.id.saveBtn);
        campaignTypeSpinner = view.findViewById(R.id.campaignTypeSpinner);
        sortTxt = view.findViewById(R.id.sortTxt);

        storage = FirebaseStorage.getInstance();

        newCampaignRef = FirebaseDatabase.getInstance().getReference().child("campaigns");

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeNotification("lá nhỏ", "Đăng tải chiến dịch thành công!");
                if (imageUri == null || titleEditText.getText().toString().isEmpty() || moneyEditText.getText().toString().isEmpty() || locationEditText.getText().toString().isEmpty() || selectedDate.getText().toString().isEmpty() || descriptionEditText.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng điền tất cả các trường", Toast.LENGTH_SHORT).show();
                } else {
                    insertCampaignData();
                }
            }
        });

        moneyEditText.addTextChangedListener(new TextWatcher() {
            DecimalFormat decimalFormat = new DecimalFormat("#,###");

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                moneyEditText.removeTextChangedListener(this);

                try {
                    String originalString = s.toString();
                    String cleanString = originalString.replaceAll("[,.]", "");
                    long longVal = Long.parseLong(cleanString);
                    String formattedString = decimalFormat.format(longVal);
                    moneyEditText.setText(formattedString);
                    moneyEditText.setSelection(formattedString.length());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                moneyEditText.addTextChangedListener(this);
            }
        });

        pickDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        chooseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });

        String[] campaignTypes = getResources().getStringArray(R.array.campaignType);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, campaignTypes);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        campaignTypeSpinner.setAdapter(spinnerAdapter);
        campaignTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedText = parent.getItemAtPosition(position).toString();

                sortTxt.setText(selectedText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return view;
    }

    private void insertCampaignData() {
        // Kiểm tra xem tất cả các trường bắt buộc đã được điền
        if (imageUri == null || titleEditText.getText().toString().isEmpty() || moneyEditText.getText().toString().isEmpty() || locationEditText.getText().toString().isEmpty() || selectedDate.getText().toString().isEmpty() || descriptionEditText.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng điền tất cả các trường", Toast.LENGTH_SHORT).show();
            return;
        }

        storageReference = FirebaseStorage.getInstance().getReference().child("campaigns").child(imageUri.getLastPathSegment());
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
                        Toast.makeText(getContext(), "Failed to upload image", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void makeNotification(String title, String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(requireContext(), "channel_id")
                .setSmallIcon(R.drawable.cloversvg_02)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(requireContext());

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        notificationManager.notify(1, builder.build());
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

    private void uploadData (){
        String title = titleEditText.getText().toString();
        String aiming = moneyEditText.getText().toString();
        String location = locationEditText.getText().toString();
        String sort = sortTxt.getText().toString();
        String time = selectedDate.getText().toString();
        String description = descriptionEditText.getText().toString();

        Campaign campaign = new Campaign(imageURL, title, aiming, location, sort, description, time);

        newCampaignRef.push().setValue(campaign);
        Toast.makeText(getContext(), "Đăng tải thành công!", Toast.LENGTH_SHORT).show();
    }
    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String selectedDateTxt = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                selectedDate.setText(selectedDateTxt);
            }
        }, year, month, day);
        datePickerDialog.show();
    }
}