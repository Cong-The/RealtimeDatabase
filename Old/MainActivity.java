package com.example.realtimedatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
    TextView tvtData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnPushData = findViewById(R.id.btn_push_data);
        Button btnGetData = findViewById(R.id.btn_get_data);
        tvtData = findViewById(R.id.tvt_get_data);

        btnPushData.setOnClickListener(view -> onClickPushData());
        btnGetData.setOnClickListener(view -> onClickgetData());
        Button btnDel = findViewById(R.id.btn_delete_data);
        btnDel.setOnClickListener(view -> OnClickDelData());
    }

    private void onClickPushData() {
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("User_info");

        User user = new User(1,"Name1",new Job(1,"job1"));
        user.setAddress("HCM");
        myRef.setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(MainActivity.this, "Push Data success", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onClickgetData(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("User_info");
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                User user = dataSnapshot.getValue(User.class);
                tvtData.setText(user.toString());

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    private void OnClickDelData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        myRef.removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(MainActivity.this,"Delete data success",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
