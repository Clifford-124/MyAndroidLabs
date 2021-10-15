package com.algonquin.dsa00001;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Chatroom extends AppCompatActivity {

    private ArrayList<ChatMessage> messages = new ArrayList<>();
    private RecyclerView chatList;
    private Button sendButtton;
    private Button receiveButtton;
    private EditText textbox;
    private Context context;
    public static final int SEND = 1;
    public static final int RECEIVE = 2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatlayout);

        textbox = (EditText) findViewById(R.id.text_box);
        sendButtton = (Button) findViewById(R.id.send_button);
        receiveButtton = (Button) findViewById(R.id.receive_button);

        chatList = (RecyclerView) findViewById(R.id.recycler_view);
        chatList.setLayoutManager(new LinearLayoutManager(this));


        MyChatAdapter adp = new MyChatAdapter(context, messages);
        chatList.setAdapter(adp);

        sendButtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
                //String currentDateandTime = sdf.format(new Date());

                ChatMessage thisMessage = new ChatMessage(textbox.getText().toString(), SEND, sdf);
                messages.add(thisMessage);
                adp.notifyDataSetChanged();
                //adp.notifyItemInserted(messages.size() - 1);
                textbox.setText(""); // clear the text content

                Log.d("Chatroom", "onSend clicked");
                Log.d("Chatroom", "onSend clicked + message size :" + messages.size());

            }
        });

        receiveButtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
                String currentDateandTime = sdf.format(new Date());
                ChatMessage thisMessage = new ChatMessage(textbox.getText().toString(), 2, sdf);
                textbox.setText(""); // clear the text content
                messages.add(thisMessage);
                adp.notifyDataSetChanged();

                Log.d("Chatroom", "onReceive clicked");
            }
        });
    }


    private class MyRowViews extends RecyclerView.ViewHolder {

        TextView messageText;
        TextView timeText;

        public MyRowViews(View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);
        }
    }

    public class MyChatAdapter extends RecyclerView.Adapter<MyRowViews> {
        Context context;
        ArrayList<ChatMessage> messages = new ArrayList<>();

        @Override
        public MyRowViews onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater inflater = getLayoutInflater();
            int layoutID;
            if (viewType == 1)
                layoutID = R.layout.sent_message;
            else
                layoutID = R.layout.receive_layout;
            View loadedRow = inflater.inflate(layoutID, parent, false);
            MyRowViews initRow = new MyRowViews(loadedRow);
            return initRow;
        }

        @Override
        public int getItemViewType(int position) {
            ChatMessage thisRow = messages.get(position);
            int request = thisRow.getSendOrReceive();
            //int request = messages.get(position).getSendOrReceive();
            Log.d("Chatroom", "View type: " + request);
            switch (request) {
                case SEND:
                    Log.d("Chatroom", "View 1");
                    return 1;
                case RECEIVE:
                    Log.d("Chatroom", "View 2");
                    return 2;
                default:
                    return 1 ;
            }
        }

        public MyChatAdapter(Context ctx, ArrayList<ChatMessage> messageList) {
            this.context = ctx;
            this.messages = messageList;
        }

        @Override
        public void onBindViewHolder(MyRowViews holder, int position) {

            holder.messageText.setText(messages.get(position).getMessage());
            holder.timeText.setText(messages.get(position).getTimeSent());

            Log.d("MyChatAdapter", "onBindViewHolder evoked");

        }

        @Override
        public int getItemCount() {
            Log.d("Chatroom", "MyChatAdapter+ message size :" + messages.size());
            return messages.size();
        }
    }

    private class ChatMessage {
        String message;
        int sendOrReceive;
        String timeSent;

        public ChatMessage(String messageString, int request, SimpleDateFormat time) {
            this.message = messageString;
            this.sendOrReceive = request;
            this.timeSent = time.format(new Date());
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getSendOrReceive() {
            return sendOrReceive;
        }

        public void setSendOrReceive(int sendOrReceive) {
            this.sendOrReceive = sendOrReceive;
        }

        public String getTimeSent() {

            return timeSent;
        }

        public void setTimeSent(String timeSent) {
            this.timeSent = timeSent;
        } // alt: Date


    }

}
