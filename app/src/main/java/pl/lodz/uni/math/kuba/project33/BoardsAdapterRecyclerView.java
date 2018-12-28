package pl.lodz.uni.math.kuba.project33;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.pinterest.android.pdk.PDKBoard;

import java.util.ArrayList;
import java.util.List;

public class BoardsAdapterRecyclerView extends RecyclerView.Adapter<BoardsAdapterRecyclerView.ViewHolder> {
    private List<PDKBoard> items = new ArrayList<>();
    private Context mContext;

    public BoardsAdapterRecyclerView(List<PDKBoard> items, Context mContext) {
        this.items = items;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.board_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.name.setText("Nazwa: " + items.get(i).getName());
        viewHolder.description.setText("Opis: " + items.get(i).getDescription());
        viewHolder.countOfPins.setText("Ilosc pinów: " + items.get(i).getPinsCount().toString());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idBoard = items.get(i).getUid();
                Intent intent = new Intent(mContext, CreateNewPinActivity.class);
                intent.putExtra("BOARD_ID", idBoard);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView description;
        private TextView countOfPins;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.board_name);
            description = (TextView) itemView.findViewById(R.id.board_description);
            countOfPins = (TextView) itemView.findViewById(R.id.pins_counts_board);

        }
    }

}
