package pl.lodz.uni.math.kuba.project33.pinterest.pin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pinterest.android.pdk.PDKCallback;
import com.pinterest.android.pdk.PDKClient;
import com.pinterest.android.pdk.PDKException;
import com.pinterest.android.pdk.PDKResponse;

import pl.lodz.uni.math.kuba.project33.R;

import static pl.lodz.uni.math.kuba.project33.pinterest.board.BoardsAdapterRecyclerView.BOARD_ID;

public class CreateNewPinActivity extends AppCompatActivity {
    private Button addPinBtn;
    private EditText pinName;
    private EditText pinImageUrl;
    private EditText pinLink;
    private String boardId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_pin);

        initializeVariables();

        addPinOnClick();
    }

    private void initializeVariables() {
        addPinBtn = (Button) findViewById(R.id.add_new_pin_button);
        pinName = (EditText) findViewById(R.id.pin_name_edit_text);
        pinImageUrl = (EditText) findViewById(R.id.pin_image_url_edit_text);
        pinLink = (EditText) findViewById(R.id.pin_link_edit_text);
        boardId = getIntent().getStringExtra(BOARD_ID);
    }

    private void setPin() {
        String name = pinName.getText().toString();
        String imageUrl = pinImageUrl.getText().toString();
        String link = pinLink.getText().toString();

        PDKClient.getInstance().createPin(name, boardId, imageUrl, link, new PDKCallback() {
            @Override
            public void onSuccess(PDKResponse response) {
                Log.d(getClass().getName(), response.getData().toString());
                Toast.makeText(CreateNewPinActivity.this, "Dodano nowego pina", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(PDKException exception) {
                Log.e(getClass().getName(), exception.getDetailMessage());
            }
        });
    }

    private void addPinOnClick() {
        addPinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPin();
            }
        });
    }
}
