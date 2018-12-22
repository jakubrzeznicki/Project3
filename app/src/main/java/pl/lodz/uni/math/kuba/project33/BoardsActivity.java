package pl.lodz.uni.math.kuba.project33;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.pinterest.android.pdk.PDKBoard;
import com.pinterest.android.pdk.PDKPin;

import java.util.ArrayList;
import java.util.List;

public class BoardsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BoardsAdapterRecyclerView adapter;
    private List<PDKBoard> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boards);

        list = new ArrayList<>();

        list = (List<PDKBoard>) getIntent().getSerializableExtra("BOARDS_LIST");

        recyclerView = (RecyclerView) findViewById(R.id.boards_list_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        adapter = new BoardsAdapterRecyclerView(list, BoardsActivity.this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
