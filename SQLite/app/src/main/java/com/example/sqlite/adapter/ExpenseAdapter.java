package com.example.sqlite.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlite.R;
import com.example.sqlite.model.Expense;


import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {
    private List<Expense> expenseList;
    private OnItemActionListener listener;

    public interface OnItemActionListener {
        void onEdit(Expense expense);
        void onDelete(Expense expense);
    }

    public ExpenseAdapter(List<Expense> expenseList, OnItemActionListener listener) {
        this.expenseList = expenseList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expense, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {

        Expense expense = expenseList.get(position);
        holder.tvAmount.setText(String.valueOf(expense.getAmount()));
        holder.tvDate.setText(expense.getDate());
        holder.tvNote.setText(expense.getNote());
        holder.tvCategory.setText("Danh mục: " + expense.getCategoryName());  // thêm dòng này


        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDelete(expense);
            }
        });

        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEdit(expense);
            }
        });
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        TextView tvAmount, tvNote, tvDate , tvCategory;
        Button btnEdit, btnDelete;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvNote = itemView.findViewById(R.id.tvNote);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
