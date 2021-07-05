package com.shaksham.view.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shaksham.R;
import com.shaksham.model.database.ShgData;

import java.util.List;

public class BaseLineDoneAdapter extends RecyclerView.Adapter<BaseLineDoneAdapter.ViewHoalder> {

    List<ShgData> baselineDoneShg;
    Context context;

public BaseLineDoneAdapter(List<ShgData> baselineDoneShg, Context context)
{
    this.baselineDoneShg=baselineDoneShg;
    this.context=context;


}

    @NonNull
    @Override
    public BaseLineDoneAdapter.ViewHoalder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

    View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.baseline_done_shg_custom_layout,parent,false);
        return new ViewHoalder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseLineDoneAdapter.ViewHoalder holder, int position) {

    ShgData shgData=baselineDoneShg.get(position);

    holder.baseline_shg.setText(shgData.getShgName());


    }

    @Override
    public int getItemCount() {
        return baselineDoneShg.size();
    }

    public class ViewHoalder extends RecyclerView.ViewHolder{

    TextView baseline_shg;
        public ViewHoalder(@NonNull View itemView) {
            super(itemView);

            baseline_shg=itemView.findViewById(R.id.baseline_shg);
        }
    }
}
