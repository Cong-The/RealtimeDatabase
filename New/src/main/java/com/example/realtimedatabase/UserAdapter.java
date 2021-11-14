package com.example.realtimedatabase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{

    public UserAdapter(List<User> mListUser,IClickListener listener) {
        this.mListUser = mListUser;
        this.mIClickListener = listener;
    }

    private List<User> mListUser;
    private IClickListener  mIClickListener;

    public  interface IClickListener{
        void onClickUpdateItem(User user);
        void onClickDeleteItem(User user);
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = mListUser.get(position);
        if(user ==null){
            return;
        }
        holder.tvId.setText("ID:"+user.getId());
        holder.tvName.setText("Name:"+user.getName());

        holder.btnUpdate.setOnClickListener(view -> mIClickListener.onClickUpdateItem(user));
        holder.btnDelete.setOnClickListener(view -> mIClickListener.onClickDeleteItem(user));
    }

    @Override
    public int getItemCount() {
        if (mListUser != null){
            return mListUser.size();
        }
        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{

        private TextView tvId;
        private TextView tvName;
        private Button  btnUpdate;
        private Button  btnDelete;

        public UserViewHolder(@NonNull View itemView) {

            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id);
            tvName = itemView.findViewById(R.id.tv_name);
            btnUpdate = itemView.findViewById(R.id.btn_update);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
