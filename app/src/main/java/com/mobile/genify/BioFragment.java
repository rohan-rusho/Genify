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

import java.util.Random;

public class BioFragment extends Fragment {

    private TextView textResult, textPlaceholder;
    private Button generateButton, btnCopy, btnShare;

    public BioFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bio, container, false);

        textResult = view.findViewById(R.id.textResult);
        textPlaceholder = view.findViewById(R.id.textPlaceholder);
        generateButton = view.findViewById(R.id.generateButton);
        btnCopy = view.findViewById(R.id.btnCopy);
        btnShare = view.findViewById(R.id.btnShare);

        generateButton.setOnClickListener(v -> generateBio());
        btnCopy.setOnClickListener(v -> copyToClipboard());
        btnShare.setOnClickListener(v -> shareText());

        return view;
    }

    private void generateBio() {
        String[] bios = {
                "Living life one day at a time 🌟",
                "Dream big, hustle harder 💪",
                "Spreading positivity wherever I go ✨",
                "Tech enthusiast and coffee lover ☕",
                "Adventure seeker and lifelong learner 🌍",
                "Creating my own sunshine ☀️"
        };

        Random rand = new Random();
        String bio = bios[rand.nextInt(bios.length)];

        textResult.setText(bio);
        textPlaceholder.setVisibility(View.GONE);
        btnCopy.setVisibility(View.VISIBLE);
        btnShare.setVisibility(View.VISIBLE);
    }

    private void copyToClipboard() {
        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Generated Bio", textResult.getText().toString());
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
