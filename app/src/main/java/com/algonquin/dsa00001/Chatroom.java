package com.algonquin.dsa00001;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;

public class Chatroom extends AppCompatActivity {

    private RecyclerView chatList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.chatlayout);

        chatList = (RecyclerView) findViewById(R.id.recycler_view);
        chatList.setAdapter(new MyChatAdapter());
    }

    public class MyChatAdapter extends RecyclerView.Adapter {

        String message;
        int sendOrReceive;
        Date timeSent;

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }

}
