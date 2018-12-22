package pl.lodz.uni.math.kuba.project33;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.pinterest.android.pdk.PDKCallback;
import com.pinterest.android.pdk.PDKClient;
import com.pinterest.android.pdk.PDKException;
import com.pinterest.android.pdk.PDKPin;
import com.pinterest.android.pdk.PDKResponse;
import com.pinterest.android.pdk.PDKUser;

import java.util.ArrayList;
import java.util.List;

public class PinsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PinsAdapterRecyclerView adapter;
    private List<PDKPin> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pins);
        list = new ArrayList<>();

        list = (List<PDKPin>) getIntent().getSerializableExtra("PINS_LIST");

        recyclerView = (RecyclerView) findViewById(R.id.pins_list_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        adapter = new PinsAdapterRecyclerView(list, PinsActivity.this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }


}
