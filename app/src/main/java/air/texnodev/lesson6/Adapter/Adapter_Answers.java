package air.texnodev.lesson6.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import air.texnodev.lesson6.OnItemsClick;
import air.texnodev.lesson6.R;

public class Adapter_Answers extends RecyclerView.Adapter<Adapter_Answers.ViewHolder>{
    private LayoutInflater inflater;
    private List<String> roots;
    public final OnItemsClick onItemsClick;

    public Adapter_Answers(Context inflater, List<String> roots, OnItemsClick onItemsClick) {
        this.inflater = LayoutInflater.from(inflater);
        this.roots = roots;
        this.onItemsClick = onItemsClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.all_variants, parent, false);

        return new ViewHolder(view, onItemsClick);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String root = roots.get(position);
        holder.name.setText(root.toString());
    }

    @Override
    public int getItemCount() {
        return roots.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        public ViewHolder(@NonNull View itemView, OnItemsClick onItemsClick) {
            super(itemView);
            name = itemView.findViewById(R.id.example_answer);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemsClick != null){

                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION){
                            onItemsClick.onClick(itemView, pos);
                        }
                    }

                }
            });
        }
    }

}
