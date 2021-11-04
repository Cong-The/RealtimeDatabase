package com.example.realtimedatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    TextView tvtData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnPushData = findViewById(R.id.btn_push_data);
        Button btnGetData = findViewById(R.id.btn_get_data);
        Button btnUpdateData = findViewById(R.id.btn_update_data);
        tvtData = findViewById(R.id.tvt_get_data);

        btnPushData.setOnClickListener(view -> onClickPushData());
        btnGetData.setOnClickListener(view -> onClickgetData());
<<<<<<< Updated upstream:Old/MainActivity.java
        Button btnDel = findViewById(R.id.btn_delete_data);
        btnDel.setOnClickListener(view -> OnClickDelData());
=======
        btnUpdateData.setOnClickListener(view -> onClickUpdateData());
        Button btnDel = findViewById(R.id.btn_delete_data);
        btnDel.setOnClickListener(view -> OnClickDeleteData());
>>>>>>> Stashed changes:MainActivity.java
    }


    private void onClickPushData() {
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
<<<<<<< Updated upstream:Old/MainActivity.java
        DatabaseReference myRef = database.getReference("User_info");

        User user = new User(1,"Name1",new Job(1,"job1"));
        user.setAddress("HCM");
        myRef.setValue(user, new DatabaseReference.CompletionListener() {
=======
        DatabaseReference myRef = database.getReference("my_map");

        Map<String, Boolean> map = new HashMap<>();
        map.put("1",true);
        map.put("2",false);
        map.put("3",false);
        map.put("4",true);
        myRef.setValue(map, new DatabaseReference.CompletionListener() {
>>>>>>> Stashed changes:MainActivity.java
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(MainActivity.this, "Push Data success", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onClickgetData(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
<<<<<<< Updated upstream:Old/MainActivity.java
        DatabaseReference myRef = database.getReference("User_info");
=======
        DatabaseReference myRef = database.getReference("my_map");
>>>>>>> Stashed changes:MainActivity.java
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
<<<<<<< Updated upstream:Old/MainActivity.java
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                User user = dataSnapshot.getValue(User.class);
                tvtData.setText(user.toString());
=======
                Map<String,Boolean> mapResult = new HashMap<>();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String key = dataSnapshot1.getKey();
                    Boolean value = dataSnapshot1.getValue(Boolean.class);
>>>>>>> Stashed changes:MainActivity.java

                    mapResult.put(key,value);
                }
                tvtData.setText(mapResult.toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

<<<<<<< Updated upstream:Old/MainActivity.java
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
=======
//    private void OnClickDelData() {
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
//        myRef.removeValue(new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//                Toast.makeText(MainActivity.this,"Delete data success",Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void OnClickDeleteData() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.app_name))
                .setMessage("Ban co chac chan muon xoa du lieu nay khong? ")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("my_map");
//                        myRef.removeValue(new DatabaseReference.CompletionListener() {
//                            @Override
//                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//                                Toast.makeText(MainActivity.this, "Delete data success", Toast.LENGTH_SHORT).show();
//                            }
//                        });
                        myRef.removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(MainActivity.this, "Delete data success", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                })
                .setNegativeButton("Cancel",null)
                .show();
    }

    private void onClickUpdateData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("my_map");

        Map<String, Object> mapUpdate = new HashMap<>();
        mapUpdate.put("2",true);
        mapUpdate.put("4",true);


        myRef.updateChildren(mapUpdate, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(MainActivity.this, "Update Data success", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
>>>>>>> Stashed changes:MainActivity.java
