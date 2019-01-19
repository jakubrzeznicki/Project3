package pl.lodz.uni.math.kuba.project33.pinterest.pin;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pinterest.android.pdk.PDKPin;

import java.util.List;

import pl.lodz.uni.math.kuba.project33.R;

public class PinsAdapterRecyclerView extends RecyclerView.Adapter<PinsAdapterRecyclerView.ViewHolder> {
    private List<PDKPin> items;
    private Context context;

    public PinsAdapterRecyclerView(List<PDKPin> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(context);
        view = mInflater.inflate(R.layout.pin_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.pinNote.setText(items.get(i).getNote());
        Glide.with(context).load(items.get(i).getImageUrl()).into(viewHolder.pinImage);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(items.get(i).getLink()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView pinImage;
        private TextView pinNote;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pinImage = (ImageView) itemView.findViewById(R.id.pin_image);
            pinNote = (TextView) itemView.findViewById(R.id.pin_note);
        }
    }


}

