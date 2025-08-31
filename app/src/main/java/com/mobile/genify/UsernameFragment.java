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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class UsernameFragment extends Fragment {

    private EditText inputField;
    private TextView textResult, textPlaceholder;
    private Button generateButton, btnCopy, btnShare;

    public UsernameFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_username, container, false);

        inputField = view.findViewById(R.id.inputField);
        textResult = view.findViewById(R.id.textResult);
        textPlaceholder = view.findViewById(R.id.textPlaceholder);
        generateButton = view.findViewById(R.id.generateButton);
        btnCopy = view.findViewById(R.id.btnCopy);
        btnShare = view.findViewById(R.id.btnShare);

        generateButton.setOnClickListener(v -> generateUsername());
        btnCopy.setOnClickListener(v -> copyToClipboard());
        btnShare.setOnClickListener(v -> shareText());

        return view;
    }

    private void generateUsername() {
        String base = inputField.getText().toString().trim();
        if (base.isEmpty()) {
            Toast.makeText(getContext(), "Enter a base word", Toast.LENGTH_SHORT).show();
            return;
        }

        Random rand = new Random();
        int number = rand.nextInt(9999);
        String username = base + "_" + number;

        textResult.setText(username);
        textPlaceholder.setVisibility(View.GONE);
        btnCopy.setVisibility(View.VISIBLE);
        btnShare.setVisibility(View.VISIBLE);
    }

    private void copyToClipboard() {
        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Generated Username", textResult.getText().toString());
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
