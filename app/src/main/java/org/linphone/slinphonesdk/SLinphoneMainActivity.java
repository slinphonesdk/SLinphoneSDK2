package org.linphone.slinphonesdk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.linphone.SLinphoneCore;
import org.linphone.SLinphoneProxyConfig;
import org.linphone.SLinphoneSDK;
import org.linphone.SLinphoneSDKListener;
import org.linphone.SLinphoneState;
import org.linphone.SRegistrationState;
import org.linphone.SlinphoneCall;

public class SLinphoneMainActivity extends AppCompatActivity
{
    private EditText numberEt;
    private TextView stateTv;
    private TextView sipTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cc);
        configSip();
        bindView();
    }

    private void bindView() {
        numberEt = findViewById(R.id.call_number_tv);
        stateTv = findViewById(R.id.state_tv);
        sipTv = findViewById(R.id.sip_tv);
    }

    private SLinphoneSDKListener sLinphoneSDKListener;
    private void configSip() {

        SLinphoneSDK.init(this, "192.168.88.253", "5060");
        SLinphoneSDK.getInstance().addSDKListener(sLinphoneSDKListener = new SLinphoneSDKListener() {
            @Override
            public void serviceIsReady() {
                SLinphoneSDK.register("28888");
            }

            @Override
            public void callState(String from, String state, String s) {
                Log.e("ppt", "state: "+state+"msg: "+s);
                String sip = "state: "+state + "\n" + "msg: "+s + "\n" + from ;
                sipTv.setText(sip);
            }

            @Override
            public void registrationState(String registrationState, String s) {
                stateTv.setText("  注册状态: "+registrationState);
            }
        });

    }

    public void call(View v) {
        String number = numberEt.getText().toString();
        SLinphoneSDK.callOutgoing(number);
    }

    public void accept(View v) {
        SLinphoneSDK.acceptCall();
    }

    public void hangup(View v) {
        SLinphoneSDK.hangup();
    }
}
