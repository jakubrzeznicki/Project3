package pl.lodz.uni.math.kuba.project33.pinterest.board;

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

import pl.lodz.uni.math.kuba.project33.R;
import pl.lodz.uni.math.kuba.project33.pinterest.pin.CreateNewPinActivity;

public class BoardsAdapterRecyclerView extends RecyclerView.Adapter<BoardsAdapterRecyclerView.ViewHolder> {
    private List<PDKBoard> items;
    private Context context;

    public BoardsAdapterRecyclerView(List<PDKBoard> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(context);
        view = mInflater.inflate(R.layout.board_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.boardName.setText("Nazwa: " + items.get(i).getName());
        viewHolder.boardDescription.setText("Opis: " + items.get(i).getDescription());
        viewHolder.boardCountOfPins.setText("Ilosc pinów: " + items.get(i).getPinsCount().toString());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idBoard = items.get(i).getUid();
                Intent intent = new Intent(context, CreateNewPinActivity.class);
                intent.putExtra("BOARD_ID", idBoard);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView boardName;
        private TextView boardDescription;
        private TextView boardCountOfPins;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            boardName = (TextView) itemView.findViewById(R.id.board_name);
            boardDescription = (TextView) itemView.findViewById(R.id.board_description);
            boardCountOfPins = (TextView) itemView.findViewById(R.id.pins_counts_board);
        }
    }

}
