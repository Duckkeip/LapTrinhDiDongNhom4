package com.example.sqlite.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlite.R;
import com.example.sqlite.model.Budget;

import java.util.List;

public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.BudgetViewHolder> {

    private List<Budget> budgetList;
    private OnBudgetActionListener listener;

    public interface OnBudgetActionListener {
        void onEdit(Budget budget);
        void onDelete(Budget budget);
    }

    public BudgetAdapter(List<Budget> budgetList, OnBudgetActionListener listener) {
        this.budgetList = budgetList;
        this.listener = listener;
    }
    @NonNull
    @Override
    public BudgetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_budget, parent, false);
        return new BudgetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BudgetViewHolder holder, int position) {
        Budget budget = budgetList.get(position);
        holder.txtCategory.setText("Danh mục: " + budget.getCategoryName());
        holder.txtAmount.setText("Số tiền: " + budget.getAmount());
        holder.txtStartDate.setText("Từ: " + budget.getStartDate());
        holder.txtEndDate.setText("Đến: " + budget.getEndDate());

        holder.btnEdit.setOnClickListener(v -> listener.onEdit(budget));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(budget));
    }

    @Override
    public int getItemCount() {
        return budgetList.size();
    }

    public static class BudgetViewHolder extends RecyclerView.ViewHolder {
        TextView txtCategory, txtAmount, txtStartDate, txtEndDate;
        Button btnEdit, btnDelete;

        public BudgetViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCategory = itemView.findViewById(R.id.txtCategory);
            txtAmount = itemView.findViewById(R.id.txtAmount);
            txtStartDate = itemView.findViewById(R.id.txtStartDate);
            txtEndDate = itemView.findViewById(R.id.txtEndDate);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);

        }
    }

    public void setBudgetList(List<Budget> budgets) {
        this.budgetList = budgets;
        notifyDataSetChanged();
    }
}
