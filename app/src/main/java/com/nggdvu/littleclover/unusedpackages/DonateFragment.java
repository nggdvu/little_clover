package com.nggdvu.littleclover.unusedpackages;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nggdvu.littleclover.R;

//Chưa hoạt động
public class DonateFragment extends Fragment implements View.OnClickListener {

    private EditText editText;
    private TextView textView;
    private Button button1, button2, button5, button10, button20, button50;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_donate, container, false);

        editText = view.findViewById(R.id.editText);
        textView = view.findViewById(R.id.textView);
        button1 = view.findViewById(R.id.button1);
        button2 = view.findViewById(R.id.button2);
        button5 = view.findViewById(R.id.button5);
        button10 = view.findViewById(R.id.button10);
        button20 = view.findViewById(R.id.button20);
        button50 = view.findViewById(R.id.button50);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button5.setOnClickListener(this);
        button10.setOnClickListener(this);
        button20.setOnClickListener(this);
        button50.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button1) {
            editText.setText("1");
        } else if (view.getId() == R.id.button2) {
            editText.setText("2");
        } else if (view.getId() == R.id.button5) {
            editText.setText("5");
        } else if (view.getId() == R.id.button10) {
            editText.setText("10");
        } else if (view.getId() == R.id.button20) {
            editText.setText("20");
        } else if (view.getId() == R.id.button50) {
            editText.setText("50");
        }
    }
}