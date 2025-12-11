package com.example.firebaserealtimedb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    EditText dataInput;
    Button add, remove, get;
    TextView dataOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://fir-realtimedb25-e704f-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference myRef = db.getReference("User");

        dataInput = (EditText) findViewById(R.id.input_txt);
        dataOutput = (TextView) findViewById(R.id.output_txt);

        add = (Button) findViewById(R.id.add_btn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = dataInput.getText().toString();
                myRef.setValue(data);
            }
        });

        remove = (Button) findViewById(R.id.remove_btn);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.removeValue();
            }
        });

        get = (Button) findViewById(R.id.get_btn);
        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String output = snapshot.getValue(String.class);
                        dataOutput.setText(output);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(MainActivity.this, "Error getting from db", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}