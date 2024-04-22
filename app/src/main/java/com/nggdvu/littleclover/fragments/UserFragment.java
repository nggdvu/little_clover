package com.nggdvu.littleclover.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.nggdvu.littleclover.R;

public class UserFragment extends Fragment {

    Toolbar userToolbar;
    CardView memberCard, contact, userGuide;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        userToolbar = view.findViewById(R.id.userToolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        memberCard = view.findViewById(R.id.card);
        userGuide = view.findViewById(R.id.userGuide);
        contact = view.findViewById(R.id.contact);

        userGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserGuideFragment userGuideFragment = new UserGuideFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_bottom_to_top, R.anim.exit_top_to_bottom);
                fragmentTransaction.replace(R.id.containerId, userGuideFragment);
                fragmentTransaction.commit();
            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"nggdvu@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "HỖ TRỢ - HỎI ĐÁP VỀ ỨNG DỤNG LÁ NHỎ");
                startActivity(intent);
            }
        });

        memberCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareImage(memberCard);
            }
        });

        activity.setSupportActionBar(userToolbar);
        activity.getSupportActionBar().setTitle("");
        setHasOptionsMenu(true);
        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.user_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "🍀 Cùng lan tỏa hành động đẹp với ứng dụng 'lá nhỏ' nhé!");

            startActivity(Intent.createChooser(shareIntent, "Chia sẻ qua"));
            return true;
        } else if (item.getItemId() == R.id.logout) {
            showLogoutConfirmationDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void shareImage(CardView memberCard) {
        // Tạo bitmap từ CardView
        memberCard.setDrawingCacheEnabled(true);
        memberCard.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(memberCard.getDrawingCache());
        memberCard.setDrawingCacheEnabled(false);

        String imagePath = MediaStore.Images.Media.insertImage(requireActivity().getContentResolver(), bitmap, "CardView", null);

        Uri imageUri = Uri.parse(imagePath);

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);

        // Khởi chạy Intent chia sẻ
        startActivity(Intent.createChooser(shareIntent, "Chia sẻ qua"));
    }

    private void showLogoutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Đăng xuất");
        builder.setMessage("Bạn có chắc chắn muốn đăng xuất?");
        builder.setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Perform logout action
                Intent intent = new Intent(getActivity(), WelcomeFragment.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        builder.setNegativeButton("Hủy", null);

        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                // Customize button colors
                Button positiveButton = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
                positiveButton.setTextColor(getResources().getColor(R.color.red));
                Button negativeButton = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_NEGATIVE);
                negativeButton.setTextColor(getResources().getColor(R.color.black));
            }
        });

        dialog.show();
    }
}