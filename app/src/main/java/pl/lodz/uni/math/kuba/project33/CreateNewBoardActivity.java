package pl.lodz.uni.math.kuba.project33;

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

public class CreateNewBoardActivity extends AppCompatActivity {

    private EditText boardNameEditText;
    private EditText boardDescriptionEditText;
    private Button addNewBoardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_board);

        init();

        addNewBoardOnClick();
    }

    private void init() {
        boardNameEditText = (EditText) findViewById(R.id.board_name_edit_text);
        boardDescriptionEditText = (EditText) findViewById(R.id.board_description_edit_text);
        addNewBoardButton = (Button) findViewById(R.id.add_new_board_button);
    }

    private void addNewBoardOnClick() {
        addNewBoardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String boardName = boardNameEditText.getText().toString();
                String boardDesc = boardDescriptionEditText.getText().toString();
                PDKClient.getInstance().createBoard(boardName, boardDesc, new PDKCallback() {
                    @Override
                    public void onSuccess(PDKResponse response) {
                        Log.d(getClass().getName(), response.getData().toString());
                        Toast.makeText(CreateNewBoardActivity.this, "Dodano nową tablice", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(PDKException exception) {
                        Log.e(getClass().getName(), exception.getDetailMessage());
                    }
                });
            }
        });
    }
}
