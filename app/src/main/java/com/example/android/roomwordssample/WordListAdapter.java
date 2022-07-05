package com.example.android.roomwordssample;

/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;


public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    private Context context;
    private HandleWordClick clickListener;


    class WordViewHolder extends RecyclerView.ViewHolder {
        private final TextView wordItemView;

        private WordViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Word> mWords = Collections.emptyList(); // Cached copy of words

    WordListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        final Word current = mWords.get(position);
        holder.wordItemView.setText(current.getWord());

        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(context.getApplicationContext(),
                    new GestureDetector.SimpleOnGestureListener() {

                        @Override
                        public boolean onDoubleTap(MotionEvent e) {
                            clickListener.editItem(current);
                            return super.onDoubleTap(e);
                        }
                    });

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gestureDetector.onTouchEvent(motionEvent);
                return false;
            }
        });
        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(context.getApplicationContext(),
                    new GestureDetector.SimpleOnGestureListener() {

                        @Override
                        public boolean onDoubleTap(MotionEvent e) {
                            clickListener.editItem(current);
                            return super.onDoubleTap(e);
                        }
                        @Override
                        public boolean onSingleTapConfirmed(MotionEvent e) {
                            Toast.makeText(context, "Doble tap para actualizar y agregar imagen", Toast.LENGTH_LONG).show();
                            return super.onSingleTapConfirmed(e);
                        }
                    });

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gestureDetector.onTouchEvent(motionEvent);
                return true;
            }

        });

    }

    void setWords(List<Word> words) {
        mWords = words;
        notifyDataSetChanged();
    }

    public interface  HandleWordClick {
        void editItem(Word word);
        void OnActivityResult(int requestCode, int resultCode, @Nullable Intent data);
    }

    @Override
    public int getItemCount() {
        return mWords.size();
    }
}


