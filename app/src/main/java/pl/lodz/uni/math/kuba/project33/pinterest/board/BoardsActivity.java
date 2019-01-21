package pl.lodz.uni.math.kuba.project33.pinterest.board;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.pinterest.android.pdk.PDKBoard;

import java.util.ArrayList;
import java.util.List;

import pl.lodz.uni.math.kuba.project33.R;

import static pl.lodz.uni.math.kuba.project33.pinterest.MainActivity.BOARD_LIST;

public class BoardsActivity extends AppCompatActivity {
    private RecyclerView boardListRecyclerView;
    private BoardsAdapterRecyclerView boardsAdapterRecyclerView;
    private List<PDKBoard> boardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boards);

        boardList = new ArrayList<>();

        boardList = (List<PDKBoard>) getIntent().getSerializableExtra(BOARD_LIST);

        boardListRecyclerView = (RecyclerView) findViewById(R.id.boards_list_recycler_view);
        boardListRecyclerView.setHasFixedSize(true);
        boardListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        boardsAdapterRecyclerView = new BoardsAdapterRecyclerView(boardList, BoardsActivity.this);
        boardListRecyclerView.setAdapter(boardsAdapterRecyclerView);
        boardsAdapterRecyclerView.notifyDataSetChanged();
    }
}
