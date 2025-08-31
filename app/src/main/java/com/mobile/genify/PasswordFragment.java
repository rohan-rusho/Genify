package com.mobile.genify;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.security.SecureRandom;

public class PasswordFragment extends Fragment {

    private TextView textResult, textPlaceholder;
    private Button generateButton, btnCopy, btnShare;

    public PasswordFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_password, container, false);

        textResult = view.findViewById(R.id.textResult);
        textPlaceholder = view.findViewById(R.id.textPlaceholder);
        generateButton = view.findViewById(R.id.generateButton);
        btnCopy = view.findViewById(R.id.btnCopy);
        btnShare = view.findViewById(R.id.btnShare);

        generateButton.setOnClickListener(v -> generatePassword());
        btnCopy.setOnClickListener(v -> copyToClipboard());
        btnShare.setOnClickListener(v -> shareText());

        return view;
    }

    private void generatePassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+-=";
        SecureRandom random = new SecureRandom();
        int length = 8 + random.nextInt(9); // 8-16 characters
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }

        textResult.setText(sb.toString());
        textPlaceholder.setVisibility(View.GONE);
        btnCopy.setVisibility(View.VISIBLE);
        btnShare.setVisibility(View.VISIBLE);
    }

    private void copyToClipboard() {
        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Generated Password", textResult.getText().toString());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(getContext(), "Copied to clipboard", Toast.LENGTH_SHORT).show();
    }

    private void shareText() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, textResult.getText().toString());
        startActivity(Intent.createChooser(intent, "Share via"));
    }
}
