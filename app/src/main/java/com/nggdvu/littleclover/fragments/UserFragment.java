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
    CardView memberCard, changeLanguage, userGuide;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        userToolbar = view.findViewById(R.id.userToolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        memberCard = view.findViewById(R.id.card);
        userGuide = view.findViewById(R.id.userGuide);

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

        memberCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện khi người dùng nhấn vào CardView
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

        // Lưu bitmap vào bộ nhớ tạm
        String imagePath = MediaStore.Images.Media.insertImage(requireActivity().getContentResolver(), bitmap, "CardView", null);

        // Tạo URI từ đường dẫn hình ảnh
        Uri imageUri = Uri.parse(imagePath);

        // Tạo Intent để chia sẻ hình ảnh
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);

        // Khởi chạy Intent chia sẻ
        startActivity(Intent.createChooser(shareIntent, "Chia sẻ qua"));
        return;
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
                getActivity().finish(); // Optional: finish the current activity to prevent going back
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

    /*aSwitch = view.findViewById(R.id.darkmodeSwitch);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                recreateActivity(); // Hàm này để tạo lại activity sau khi thay đổi chế độ giao diện
            }
        });*/

        /*changeLanguage = view.findViewById(R.id.changeLanguage);

        Spinner languageSpinner = view.findViewById(R.id.languageSpinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(adapter);
        languageSpinner.setSelection(0);
        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLanguage = parent.getItemAtPosition(position).toString();
                if (selectedLanguage.equals("Tiếng Việt")) {
                    setLocale(requireActivity(), "vi");
                    Intent intent = new Intent(requireActivity(), requireActivity().getClass());
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    requireActivity().startActivity(intent);
                    requireActivity().finish();
                } else if (selectedLanguage.equals("English")) {
                    setLocale(requireActivity(), "en");
                    Intent intent = new Intent(requireActivity(), requireActivity().getClass());
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    requireActivity().startActivity(intent);
                    requireActivity().finish();
                } else if (selectedLanguage.equals("日本語")) {
                    setLocale(requireActivity(), "ja");
                    Intent intent = new Intent(requireActivity(), requireActivity().getClass());
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    requireActivity().startActivity(intent);
                    requireActivity().finish();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /*languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLanguageCode = ""; // Lưu trữ mã ngôn ngữ được chọn từ Spinner
                switch (position) {
                    case 0:
                        selectedLanguageCode = "en";
                        break;
                    case 1:
                        selectedLanguageCode = "vi";
                        break;
                    case 2:
                        selectedLanguageCode = "ja";
                        break;
                    // Thêm các case khác nếu bạn có nhiều ngôn ngữ hơn
                }
                updateLanguage(selectedLanguageCode);
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Xử lý khi không có ngôn ngữ nào được chọn (không cần thiết)
            }
        });*/
    /*public void setLocale(Activity activity, String langCode){
        Locale locale = new Locale(langCode);
        locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        Intent intent = new Intent(activity, activity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
        activity.finish();
    }*/

    /*private void recreateActivity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getActivity().recreate();
        } else {
            Intent intent = getActivity().getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            getActivity().finish();
            getActivity().overridePendingTransition(0, 0);
            startActivity(intent);
            getActivity().overridePendingTransition(0, 0);
        }
    }*/

    /*private void updateLanguage(String languageCode) {
        currentLanguage = languageCode;
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }*/
    }
}