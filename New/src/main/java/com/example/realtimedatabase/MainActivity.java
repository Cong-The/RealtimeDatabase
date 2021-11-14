package com.example.realtimedatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvUser.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvUser.addItemDecoration(dividerItemDecoration);

        mListUser = new ArrayList<>();
        mUserAdapter = new UserAdapter(mListUser, new UserAdapter.IClickListener() {
            @Override
            public void onClickUpdateItem(User user) {
                openDialogUpdateItem(user);
            }

            @Override
            public void onClickDeleteItem(User user) {
                OnClickDeleteData(user);
            }
        });

        rcvUser.setAdapter(mUserAdapter);

    }

    private void onClickAddUser(User user) {
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("User");

        String pathObject = String.valueOf(user.getId()); // gan  dinh danh = ID
        myRef.child(pathObject).setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(MainActivity.this, "Add user success", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getListUserFromRealtimeDatabase() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("User");
//        // Cach 1
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (mListUser != null) {
//                    mListUser.clear();
//                }
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    User user = dataSnapshot.getValue(User.class);
//                    mListUser.add(user);
//                }
//                mUserAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(MainActivity.this, "Get list users faild", Toast.LENGTH_SHORT).show();
//            }
//        });
        Query query =  myRef.orderByKey(); // xap sep tang dan theo key
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user = snapshot.getValue(User.class);
                if (user != null) {
                    mListUser.add(user); //mListUser.add(0,user); xap sep giam dan
                    mUserAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user = snapshot.getValue(User.class);
                if (user == null || mListUser == null || mListUser.isEmpty()) {
                    return;
                }
                for (int i = 0; i < mListUser.size(); i++) {
                    if (user.getId() == mListUser.get(i).getId()) {
                        mListUser.set(i, user);
                        break;
                    }
                }
                mUserAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user == null || mListUser == null || mListUser.isEmpty()) {
                    return;
                }
                for (int i = 0; i < mListUser.size(); i++) {
                    if (user.getId() == mListUser.get(i).getId()) {
                        mListUser.remove(mListUser.get(i));
                        break;
                    }
                }
                mUserAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void openDialogUpdateItem(User user) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_update);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        EditText edtUpdateName = dialog.findViewById(R.id.edt_update_name);
        Button btnUpdate = dialog.findViewById(R.id.btn_update);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);

        edtUpdateName.setText(user.getName());
        btnCancel.setOnClickListener(view -> dialog.dismiss());
        btnUpdate.setOnClickListener(view -> {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("User");

            String NewName = edtUpdateName.getText().toString().trim();
            user.setName(NewName);

            myRef.child(String.valueOf(user.getId())).updateChildren(user.toMap(), new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    Toast.makeText(MainActivity.this, "Update data success", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
        });

        dialog.show();
    }


    private void OnClickDeleteData(User user) {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.app_name))
                .setMessage("Ban co chac chan muon xoa du lieu nay khong? ")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("User");
//                        myRef.removeValue(new DatabaseReference.CompletionListener() {
//                            @Override
//                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//                                Toast.makeText(MainActivity.this, "Delete data success", Toast.LENGTH_SHORT).show();
//                            }
//                        });
                        myRef.child(String.valueOf(user.getId())).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(MainActivity.this, "Delete data success", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}