package pl.lodz.uni.math.kuba.project33.pinterest.pin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.pinterest.android.pdk.PDKPin;

import java.util.ArrayList;
import java.util.List;

import pl.lodz.uni.math.kuba.project33.R;

import static pl.lodz.uni.math.kuba.project33.pinterest.MainActivity.PINS_LIST;

public class PinsActivity extends AppCompatActivity {
    private RecyclerView pinsListRecyclerView;
    private PinsAdapterRecyclerView pinsAdapterRecyclerView;
    private List<PDKPin> pinsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pins);

        pinsList = new ArrayList<>();

        pinsList = (List<PDKPin>) getIntent().getSerializableExtra(PINS_LIST);

        pinsListRecyclerView = (RecyclerView) findViewById(R.id.pins_list_recycler_view);
        pinsListRecyclerView.setHasFixedSize(true);
        pinsListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        pinsAdapterRecyclerView = new PinsAdapterRecyclerView(pinsList, PinsActivity.this);
        pinsListRecyclerView.setAdapter(pinsAdapterRecyclerView);
        pinsAdapterRecyclerView.notifyDataSetChanged();
    }
}
