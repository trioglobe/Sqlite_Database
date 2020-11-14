package com.ams.sqlite_database;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class adapter extends RecyclerView.Adapter<adapter.ViewHolder> {
    Context context;
    List<dbHelper> dbHelperList;
    dbHelper dbHelper;
    sqlite sqlite;

    public adapter(Context context, List<dbHelper> dbHelperList, sqlite sqlite) {
        this.context = context;
        this.dbHelperList = dbHelperList;
        this.sqlite = sqlite;
    }

    @NonNull
    @Override
    public adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final adapter.ViewHolder holder, int position) {
        dbHelper = dbHelperList.get(position);
        final int id = dbHelper.getId();
        String value = dbHelper.getValue();
        holder.value.setText(value);

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.layout.setVisibility(View.GONE);
                holder.edit_layout.setVisibility(View.VISIBLE);
            }
        });

        holder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = holder.edit_value.getText().toString();
                if(TextUtils.isEmpty(value)){
                    holder.edit_value.setError("This is required");
                    holder.edit_value.requestFocus();
                }
                else{
                    sqlite.update(dbHelper.getId(), value);
                    holder.layout.setVisibility(View.VISIBLE);
                    holder.edit_layout.setVisibility(View.GONE);
                    Intent i = new Intent(context, MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(i);
                }
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlite.delete(id);
                Intent i = new Intent(context, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dbHelperList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView value;
        ImageView save, edit, delete;
        EditText edit_value;
        LinearLayout edit_layout, layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            value = (TextView) itemView.findViewById(R.id.value);
            edit_value = (EditText) itemView.findViewById(R.id.edit_value);
            save = (ImageView) itemView.findViewById(R.id.save);
            edit = (ImageView) itemView.findViewById(R.id.edit);
            delete = (ImageView) itemView.findViewById(R.id.delete);
            edit_layout = (LinearLayout) itemView.findViewById(R.id.edit_layout);
            layout = (LinearLayout) itemView.findViewById(R.id.layout);
        }
    }
}
