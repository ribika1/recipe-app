package com.example.project;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private Context mCtx;
    private List<String> groceries;
    onButtonListener mbuttonListener;
    DatabaseReference mRootRef;

    public ListAdapter(Context mCtx, List<String> groceryList, onButtonListener buttonListener) {
        this.mCtx = mCtx;
        this.groceries = groceryList;
        this.mbuttonListener = buttonListener;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_element, parent, false);
        return new ListViewHolder(view, mbuttonListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return groceries.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder implements onButtonListener{

        EditText newItem;
        Button delete;
        Button save;
        onButtonListener buttonListener;


        public ListViewHolder(@NonNull View itemView, onButtonListener buttonListener) {
            super(itemView);

            mRootRef = FirebaseDatabase.getInstance().getReference("list");
            newItem = itemView.findViewById(R.id.newListItem);
            delete = itemView.findViewById(R.id.deleteFromList);
            save = itemView.findViewById(R.id.saveItem);
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mbuttonListener.onClickS(v, getAdapterPosition());
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mbuttonListener.onClick(v, getAdapterPosition());
                }
            });
        }

        @Override
        public void onClickS(View v, final int position) {
            mRootRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String element = mRootRef.push().getKey();
                    mRootRef.child(element).setValue(newItem.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        }
                    });

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        @Override
        public void onClick(View v, final int position) {

        }
    }



    public interface onButtonListener{
        void onClick(View v, int position);
        void onClickS(View v, int position);
    }

}
