package com.example.spotifywrapped.ui;

import android.util.Log;

import com.example.spotifywrapped.Artist;
import com.example.spotifywrapped.Song;
import com.example.spotifywrapped.ui.wrap.WrapViewModel;
import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GeminiApiHelper {
    private static final String API_KEY = "AIzaSyAXaTuI_G_esUSi8AEZYBXp2NffTeLVOXA";
    private static final String MODEL_NAME = "gemini-pro";

    public static void getResponseFromGemini(List<Song> songs, WrapViewModel wrapViewModel) {
        GenerativeModel gm = new GenerativeModel(MODEL_NAME, API_KEY);
        // GenerativeModelFutures is compatible with ListenableFutures which handles the API call asynchronously
        GenerativeModelFutures model = GenerativeModelFutures.from(gm);

        String prompt = constructPrompt(songs);
        Content content = new Content.Builder()
                .addText(prompt)
                .build();

        Executor executor = Executors.newFixedThreadPool(10);
        ListenableFuture<GenerateContentResponse> response = model.generateContent(content);
        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
                wrapViewModel.updateGeminiResult(result.getText());
                Log.d("THE SHIT IS WORKING", result.getText());
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        }, executor);
    }


    public static void getResponseFromGeminiArtists(List<Artist> artists, WrapViewModel wrapViewModel) {
        GenerativeModel gm = new GenerativeModel(MODEL_NAME, API_KEY);
        // GenerativeModelFutures is compatible with ListenableFutures which handles the API call asynchronously
        GenerativeModelFutures model = GenerativeModelFutures.from(gm);

        String prompt = constructPromptArtistRecs(artists);
        Content content = new Content.Builder()
                .addText(prompt)
                .build();

        Executor executor = Executors.newFixedThreadPool(10);
        ListenableFuture<GenerateContentResponse> response = model.generateContent(content);
        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
                wrapViewModel.updateGeminiResultArtists(result.getText());
                Log.d("THE SHIT IS WORKING", result.getText());
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        }, executor);
    }

    public static String constructPrompt(List<Song> songs) {
        String prompt = "Write a 2-3 sentence description of how I act, think, and dress " +
                "based on the fact that my top 3 listened to songs are ";
        for (int i = 0; i < 3; i++) {
            Song song = songs.get(i);
            prompt += song.getName() + " by " + song.getArtists()[0] + ", ";
        }
        return prompt;
    }

    public static String constructPromptArtistRecs(List<Artist> artists) {
        String prompt = "List 3 artists that I would enjoy " +
                "based on the fact that my top 3 artists listened to artists are ";
        for (int i = 0; i < 3; i++) {
            Artist artist = artists.get(i);
            prompt += artist.getName();
        }
        prompt += " don't use '**' while listing artists in your response. For each artist, include a very breif 1 sentence justification as to why I would enjoy them ";
        return prompt;
    }
}
