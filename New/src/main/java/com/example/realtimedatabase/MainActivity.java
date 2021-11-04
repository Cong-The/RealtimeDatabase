package com.example.realtimedatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText edtId, edtName;
    private Button btnAddUser;
    private RecyclerView rcvUser;
    private UserAdapter mUserAdapter;
    private List<User> mListUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUi();// Anh xa

        btnAddUser.setOnClickListener(view -> {
            int id = Integer.parseInt(edtId.getText().toString().trim());
            String name = edtName.getText().toString().trim();
            User user = new User(id, name);
            onClickAddUser(user);
        });
 //       clickAddAllUser(); // add list user
        getListUserFromRealtimeDatabase();

    }

    private void initUi() {
        edtId = findViewById(R.id.edit_id);
        edtName = findViewById(R.id.edit_name);
        btnAddUser = findViewById(R.id.btn_AddUser);

        rcvUser = findViewById(R.id.rcv_user);
        LinearLayoutManager  linearLayoutManager = new LinearLayoutManager(this);
        rcvUser.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        rcvUser.addItemDecoration(dividerItemDecoration);

        mListUser = new ArrayList<>();
        mUserAdapter = new UserAdapter(mListUser);

        rcvUser.setAdapter(mUserAdapter);

    }

    private void onClickAddUser(User user) {
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("User");

        String pathObject = String.valueOf(user.getId());
        myRef.child(pathObject).setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(MainActivity.this, "Add user success", Toast.LENGTH_SHORT).show();
            }
        });

    }

//    private void clickAddAllUser(){
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("List)User");
//
//        List<User> list = new ArrayList<>();
//        list.add(new User(1,"user1"));
//        list.add(new User(2,"user2"));
//        list.add(new User(3,"user3"));
//
//        myRef.setValue(list, new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//                Toast.makeText(MainActivity.this, "Add user success", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void getListUserFromRealtimeDatabase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("User");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    mListUser.add(user);
                }
                mUserAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this,"Get list users faild",Toast.LENGTH_SHORT).show();
            }
        });
    }


//    private void OnClickDeleteData() {
//        new AlertDialog.Builder(this)
//                .setTitle(getString(R.string.app_name))
//                .setMessage("Ban co chac chan muon xoa du lieu nay khong? ")
//                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        FirebaseDatabase database = FirebaseDatabase.getInstance();
//                        DatabaseReference myRef = database.getReference("my_map");
////                        myRef.removeValue(new DatabaseReference.CompletionListener() {
////                            @Override
////                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
////                                Toast.makeText(MainActivity.this, "Delete data success", Toast.LENGTH_SHORT).show();
////                            }
////                        });
//                        myRef.removeValue(new DatabaseReference.CompletionListener() {
//                            @Override
//                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//                                Toast.makeText(MainActivity.this, "Delete data success", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//
//                    }
//                })
//                .setNegativeButton("Cancel",null)
//                .show();
//    }
}