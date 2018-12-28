package pl.lodz.uni.math.kuba.project33;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pinterest.android.pdk.PDKBoard;
import com.pinterest.android.pdk.PDKCallback;
import com.pinterest.android.pdk.PDKClient;
import com.pinterest.android.pdk.PDKException;
import com.pinterest.android.pdk.PDKPin;
import com.pinterest.android.pdk.PDKResponse;
import com.pinterest.android.pdk.PDKUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    PDKClient pdkClient;
    PDKUser user;

    private final String appId = "5006082538307877555";
    private final String PROFILE_FIELDS = "id, image, counts, created_at, first_name, last_name, bio";
    private final String PIN_FIELDS = "id, link, note, image";
    private final String BOARDS_FIELDS = "id, name, description, counts";

    private List<PDKPin> pinsList;
    private List<PDKBoard> boardsList;
    private LinearLayout linearLayout;
    private ImageView profileImg;
    private TextView fname, lname, bio;
    private Button goToPins, goToCreateBoard, goToBoards, logout, login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        pdkClient = PDKClient.configureInstance(this, appId);
        pdkClient.onConnect(this);

        loginOnPinterest();
        PinsListOnClick();
        CreateBoardOnClick();
        BoardsListOnClick();
        logoutOnClick();
    }

    private void init() {
        linearLayout = (LinearLayout) findViewById(R.id.linearLayId);
        profileImg = (ImageView) findViewById(R.id.imgload);
        fname = (TextView) findViewById(R.id.nameId);
        lname = (TextView) findViewById(R.id.lastnmId);
        bio = (TextView) findViewById(R.id.bioId);

        goToPins = (Button) findViewById(R.id.go_to_pins);
        goToCreateBoard = (Button) findViewById(R.id.go_to_create_board);
        goToBoards = (Button) findViewById(R.id.go_to_boards);
        logout = (Button) findViewById(R.id.logout);
        login = (Button) findViewById(R.id.login);
    }

    private void loginOnPinterest() {

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List list = new ArrayList<String>();
                list.add(PDKClient.PDKCLIENT_PERMISSION_READ_PUBLIC);
                list.add(PDKClient.PDKCLIENT_PERMISSION_WRITE_PUBLIC);

                pdkClient.login(MainActivity.this, list, new PDKCallback() {
                    @Override
                    public void onSuccess(PDKResponse response) {
                        Log.d(getClass().getName(), response.getData().toString());
                        setProfileInformation();
                        setPins();
                        setBoards();
                    }

                    @Override
                    public void onFailure(PDKException exception) {
                        Log.e(getClass().getName(), exception.getDetailMessage());
                    }
                });
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        pdkClient.onOauthResponse(requestCode, resultCode, data);
    }

    private void setProfileInformation() {

        linearLayout.setVisibility(View.VISIBLE);
        PDKClient.getInstance().getMe(PROFILE_FIELDS, new PDKCallback() {

            @Override
            public void onSuccess(PDKResponse response) {
                user = response.getUser();
                fname.setText("First Name: " + user.getFirstName());
                lname.setText("Last Name: " + user.getLastName());
                bio.setText("Bio: " + user.getBio());

                Glide.with(MainActivity.this).load(user.getImageUrl()).into(profileImg);
            }

            @Override
            public void onFailure(PDKException exception) {
                super.onFailure(exception);
            }
        });
    }


    private void setPins() {
        pinsList = new ArrayList<>();
        PDKClient.getInstance().getMyPins(PIN_FIELDS, new PDKCallback() {
                    @Override
                    public void onSuccess(PDKResponse response) {
                        pinsList = response.getPinList();
                    }

                    @Override
                    public void onFailure(PDKException exception) {
                        Log.e(getClass().getName(), exception.getDetailMessage());
                    }
                }
        );

    }

    private void setBoards() {
        boardsList = new ArrayList<>();
        PDKClient.getInstance().getMyBoards(BOARDS_FIELDS, new PDKCallback() {
            @Override
            public void onSuccess(PDKResponse response) {
                boardsList = response.getBoardList();
            }

            @Override
            public void onFailure(PDKException exception) {
                Log.e(getClass().getName(), exception.getDetailMessage());
            }

        });
    }

    public void PinsListOnClick() {
        goToPins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PinsActivity.class);
                intent.putExtra("PINS_LIST", (Serializable) pinsList);
                startActivity(intent);

            }
        });
    }

    public void CreateBoardOnClick() {
        goToCreateBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateNewPinActivity.class);
                startActivity(intent);
            }
        });
    }

    public void BoardsListOnClick() {
        goToBoards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BoardsActivity.class);
                intent.putExtra("BOARDS_LIST", (Serializable) boardsList);
                startActivity(intent);
            }
        });
    }

    public void logoutOnClick() {
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdkClient.logout();
                linearLayout.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Wylogowano", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
