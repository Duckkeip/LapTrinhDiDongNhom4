package com.example.sqlite.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlite.R;
import com.example.sqlite.model.Income;

import java.util.List;

public class IncomeAdapter extends RecyclerView.Adapter<IncomeAdapter.IncomeViewHolder> {
    private List<Income> incomeList;
    private OnItemActionListener listener;

    public interface OnItemActionListener {
        void onEdit(Income income);
        void onDelete(Income income);
    }

    public IncomeAdapter(List<Income> incomeList, OnItemActionListener listener) {
        this.incomeList = incomeList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public IncomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_income, parent, false);
        return new IncomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IncomeViewHolder holder, int position) {
        Income income = incomeList.get(position);
        holder.tvAmount.setText(String.valueOf(income.getAmount()));
        holder.tvDate.setText(income.getDate());
        holder.tvNote.setText(income.getNote());
        holder.tvCategory.setText("Danh mục: " + income.getCategoryName());  // thêm dòng này


        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDelete(income);
            }
        });

        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEdit(income);
            }
        });
    }

    @Override
    public int getItemCount() {
        return incomeList.size();
    }

    public static class IncomeViewHolder extends RecyclerView.ViewHolder {
        TextView tvAmount, tvNote, tvDate , tvCategory;
        Button btnEdit, btnDelete;

        public IncomeViewHolder(@NonNull View itemView) {
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
