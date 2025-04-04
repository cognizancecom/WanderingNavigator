package com.example.d308vacationplanner.UI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308vacationplanner.R;
import com.example.d308vacationplanner.entities.Excursion;

import java.util.List;

public class ExcursionAdapter extends RecyclerView.Adapter<ExcursionAdapter.ExcursionViewHolder> {
    private List<Excursion> mExcursions;
    private final Context context;
    private final LayoutInflater mInflater;




    public class ExcursionViewHolder extends RecyclerView.ViewHolder {
        private final TextView excursionItemView;
        private final TextView excursionItemView2;
        private final TextView excursionItemView3;


    private ExcursionViewHolder(View itemView) {
        super(itemView);
        excursionItemView = itemView.findViewById(R.id.textView3);
        excursionItemView2 = itemView.findViewById(R.id.textView4);
        excursionItemView3 = itemView.findViewById(R.id.textView5);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                final Excursion current = mExcursions.get(position);
                Intent intent = new Intent(context, ExcursionDetails.class);
                intent.putExtra("excursionID", current.getExcursionId());
                intent.putExtra("title", current.getTitle());
                intent.putExtra("startDate", current.getDate());
                intent.putExtra("vacationID", current.getVacationId());
                context.startActivity(intent);
            }
            });
        }
    }
        public ExcursionAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
            this.context = context;
        }
        @NonNull
        @Override
        public ExcursionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = mInflater.inflate(R.layout.excursion_list_item, parent, false);
            return new ExcursionViewHolder(itemView);

        }
        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ExcursionViewHolder holder, int position) {
            if (mExcursions != null) {
                Excursion current = mExcursions.get(position);
                String title = current.getTitle();
                int vacationId = current.getVacationId();
                String date = current.getDate();
                holder.excursionItemView.setText(title);
                holder.excursionItemView2.setText(Integer.toString(vacationId));
                holder.excursionItemView3.setText(date);
            } else {
                holder.excursionItemView.setText("No excursion title");
                holder.excursionItemView2.setText("No excursion ID");
                holder.excursionItemView3.setText("No excursion date");
            }
        }

        @Override
        public void onBindViewHolder(@NonNull ExcursionViewHolder holder, int position, @NonNull List<Object> payloads) {
            if (payloads.isEmpty()) {
                onBindViewHolder(holder, position);
            } else {
                super.onBindViewHolder(holder, position, payloads);
            }
        }


    public void setFilteredExcursions(List<Excursion> filteredExcursions) {
        this.mExcursions = filteredExcursions;
        notifyDataSetChanged();
}

    @Override
    public int getItemCount() {
        if(mExcursions != null){
            return mExcursions.size();
        } else return 0;
    }
}