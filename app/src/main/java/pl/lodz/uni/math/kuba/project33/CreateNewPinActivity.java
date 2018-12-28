package pl.lodz.uni.math.kuba.project33;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.pinterest.android.pdk.PDKCallback;
import com.pinterest.android.pdk.PDKClient;
import com.pinterest.android.pdk.PDKException;
import com.pinterest.android.pdk.PDKPin;
import com.pinterest.android.pdk.PDKResponse;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;

public class CreateNewPinActivity extends AppCompatActivity {

    private Button addPinButton;
    private EditText pinNameEditText;
    private EditText pinImageUrlEditText;
    private EditText pinLinkEditText;
    private String boardId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_pin);
        init();

        boardId = getIntent().getStringExtra("BOARD_ID");

        addPinOnClick();
    }

    private void init() {
        addPinButton = (Button) findViewById(R.id.add_new_pin_button);
        pinNameEditText = (EditText) findViewById(R.id.pin_name_edit_text);
        pinImageUrlEditText = (EditText) findViewById(R.id.pin_image_url_edit_text);
        pinLinkEditText = (EditText) findViewById(R.id.pin_link_edit_text);
    }

    private void setPins() {
        String pinName = pinNameEditText.getText().toString();
        String pinImageUrl = pinImageUrlEditText.getText().toString();
        String pinLink = pinLinkEditText.getText().toString();

        PDKClient.getInstance().createPin(pinName, boardId, pinImageUrl, pinLink, new PDKCallback() {
            @Override
            public void onSuccess(PDKResponse response) {
                Log.d(getClass().getName(), response.getData().toString());
                Toast.makeText(CreateNewPinActivity.this, "Dodano noweg pina", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(PDKException exception) {
                Log.e(getClass().getName(), exception.getDetailMessage());
            }
        });
    }

    private void addPinOnClick() {
        addPinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPins();
            }
        });
    }
}
