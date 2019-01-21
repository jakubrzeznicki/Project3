package pl.lodz.uni.math.kuba.project33.pinterest;

import android.content.Intent;
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

import pl.lodz.uni.math.kuba.project33.R;
import pl.lodz.uni.math.kuba.project33.pinterest.board.BoardsActivity;
import pl.lodz.uni.math.kuba.project33.pinterest.pin.CreateNewPinActivity;
import pl.lodz.uni.math.kuba.project33.pinterest.pin.PinsActivity;

public class MainActivity extends AppCompatActivity {
    public static final String PINS_LIST = "PINS_LIST";
    public static final String BOARD_LIST = "BOARD_LIST";
    private final String APP_ID = "5006082538307877555";
    private final String PROFILE_FIELDS = "id, image, first_name, last_name";
    private final String PIN_FIELDS = "id, link, note, image";
    private final String BOARDS_FIELDS = "id, name, description, counts";

    private PDKClient pdkClient;
    private PDKUser pdkUser;

    private List<PDKPin> pinsList;
    private List<PDKBoard> boardsList;
    private LinearLayout linearLayout;
    private ImageView profileImage;
    private TextView firstName, lastName;
    private Button goToPinsActivity, goToCreateBoardActivity, goToBoardsActivity, logout, login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeVariables();

        pdkClient = PDKClient.configureInstance(this, APP_ID);
        pdkClient.onConnect(this);

        loginOnPinterest();
        showPinsList();
        createBoard();
        showBoardList();
        logoutOnClick();
    }

    private void initializeVariables() {
        linearLayout = (LinearLayout) findViewById(R.id.linear_layout_main);
        profileImage = (ImageView) findViewById(R.id.profile_image_iv);
        firstName = (TextView) findViewById(R.id.profile_first_name_tv);
        lastName = (TextView) findViewById(R.id.profile_last_name_tv);

        goToPinsActivity = (Button) findViewById(R.id.go_to_pins_list_button);
        goToCreateBoardActivity = (Button) findViewById(R.id.go_to_create_board_button);
        goToBoardsActivity = (Button) findViewById(R.id.go_to_boards_list_button);
        logout = (Button) findViewById(R.id.logout_button);
        login = (Button) findViewById(R.id.login_with_pinterest_button);
    }

    private void loginOnPinterest() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List permissionList = new ArrayList<String>();
                permissionList.add(PDKClient.PDKCLIENT_PERMISSION_READ_PUBLIC);
                permissionList.add(PDKClient.PDKCLIENT_PERMISSION_WRITE_PUBLIC);

                pdkClient.login(MainActivity.this, permissionList, new PDKCallback() {
                    @Override
                    public void onSuccess(PDKResponse response) {
                        Log.d(getClass().getName(), response.getData().toString());
                        setProfileInformation();
                        setPinsList();
                        setBoardsList();
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
                pdkUser = response.getUser();
                firstName.setText("First Name: " + pdkUser.getFirstName());
                lastName.setText("Last Name: " + pdkUser.getLastName());
                Glide.with(MainActivity.this).load(pdkUser.getImageUrl()).into(profileImage);
            }

            @Override
            public void onFailure(PDKException exception) {
                super.onFailure(exception);
            }
        });
    }

    private void setPinsList() {
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

    private void setBoardsList() {
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

    public void showPinsList() {
        goToPinsActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PinsActivity.class);
                intent.putExtra(PINS_LIST, (Serializable) pinsList);
                startActivity(intent);
            }
        });
    }

    public void createBoard() {
        goToCreateBoardActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateNewPinActivity.class);
                startActivity(intent);
            }
        });
    }

    public void showBoardList() {
        goToBoardsActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BoardsActivity.class);
                intent.putExtra(BOARD_LIST, (Serializable) boardsList);
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
