package pl.lodz.uni.math.kuba.project33;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.pinterest.android.pdk.PDKCallback;
import com.pinterest.android.pdk.PDKClient;
import com.pinterest.android.pdk.PDKException;
import com.pinterest.android.pdk.PDKResponse;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    PDKClient pdkClient;

    final String appId = "5006082538307877555";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pdkClient = PDKClient.configureInstance(this, appId);
        pdkClient.onConnect(this);
    }

    public void CLick(View view) {

        List list = new ArrayList<String>();
        list.add(PDKClient.PDKCLIENT_PERMISSION_READ_PUBLIC);
        list.add(PDKClient.PDKCLIENT_PERMISSION_WRITE_PUBLIC);


        pdkClient.login(this, list, new PDKCallback() {
            @Override
            public void onSuccess(PDKResponse response) {
                Log.d(getClass().getName(), response.getData().toString());

            }

            @Override
            public void onFailure(PDKException exception) {
                Log.e(getClass().getName(), exception.getDetailMessage());
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        pdkClient.onOauthResponse(requestCode, resultCode, data);
    }
}
