package com.nggdvu.littleclover.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.nggdvu.littleclover.R;

import java.text.DecimalFormat;

public class DonateFragment extends Fragment implements View.OnClickListener {

    private EditText moneyEditText;
    private Button button1, button2, button3, button4, button5, button6, confirmButton;
    ImageButton backBtn;
    String amount;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_donate, container, false);

        moneyEditText = view.findViewById(R.id.editText);
        button1 = view.findViewById(R.id.button1);
        button2 = view.findViewById(R.id.button2);
        button3 = view.findViewById(R.id.button3);
        button4 = view.findViewById(R.id.button4);
        button5 = view.findViewById(R.id.button5);
        button6 = view.findViewById(R.id.button6);
        confirmButton = view.findViewById(R.id.confirmBtn);
        backBtn = view.findViewById(R.id.backBtn);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment homeFragment = new HomeFragment("photo", "hashtag", "image", "title", "aiming", "location", "description", "description", "time");
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_bottom_to_top, R.anim.exit_top_to_bottom);
                fragmentTransaction.replace(R.id.containerId, homeFragment);
                fragmentTransaction.commit();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        DonateCompleteFragment donateCompleteFragment = new DonateCompleteFragment();
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.setCustomAnimations(R.anim.enter_bottom_to_top, R.anim.exit_top_to_bottom);
                        fragmentTransaction.replace(R.id.containerId, donateCompleteFragment);
                        fragmentTransaction.commit();
                    }
                }, 500);
            }
        });

        moneyEditText.addTextChangedListener(new TextWatcher() {
            DecimalFormat decimalFormat = new DecimalFormat("#,###đ");

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
                    String cleanString = originalString.replaceAll("[,.đ]", "");
                    long longVal = Long.parseLong(cleanString);
                    String formattedString = decimalFormat.format(longVal);
                    moneyEditText.setText(formattedString);
                    moneyEditText.setSelection(formattedString.length());

                    // Update the amount parameter in the URL
                    Uri.Builder builder = new Uri.Builder();
                    builder.scheme("https")
                            .authority("img.vietqr.io")
                            .appendPath("image")
                            .appendPath("mbbank-0855229688-compact2.png")
                            .appendQueryParameter("amount", cleanString)
                            .appendQueryParameter("addInfo", "Quyen%20Gop");
                    amount = builder.build().toString();

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    // Handle the exception, display an error message to the user, or take appropriate action.
                }
                moneyEditText.addTextChangedListener(this);
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button1) {
            moneyEditText.setText("5,000đ");
        } else if (view.getId() == R.id.button2) {
            moneyEditText.setText("10,000đ");
        } else if (view.getId() == R.id.button3) {
            moneyEditText.setText("20,000đ");
        } else if (view.getId() == R.id.button4) {
            moneyEditText.setText("50,000đ");
        } else if (view.getId() == R.id.button5) {
            moneyEditText.setText("100,000đ");
        } else if (view.getId() == R.id.button6) {
            moneyEditText.setText("500,000đ");
        }
    }
    private void openLink() {
        Uri uri = Uri.parse(amount);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        getContext().startActivity(intent);
    }
}