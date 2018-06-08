package org.linphone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.linphone.core.LinphoneAddress;
import org.linphone.core.LinphoneAuthInfo;
import org.linphone.core.LinphoneCall;
import org.linphone.core.LinphoneCallStats;
import org.linphone.core.LinphoneChatMessage;
import org.linphone.core.LinphoneChatRoom;
import org.linphone.core.LinphoneContent;
import org.linphone.core.LinphoneCore;
import org.linphone.core.LinphoneCoreListener;
import org.linphone.core.LinphoneEvent;
import org.linphone.core.LinphoneFriend;
import org.linphone.core.LinphoneFriendList;
import org.linphone.core.LinphoneInfoMessage;
import org.linphone.core.LinphoneProxyConfig;
import org.linphone.core.PublishState;
import org.linphone.core.SubscriptionState;
import org.linphone.mediastream.Log;

import java.nio.ByteBuffer;
import java.util.List;


public class DemoMainActivity extends Activity {

    private TextView stateTextView;
    private TextView callStateTextView;
    private EditText phoneEditText;

    private LinphoneCall mCall;
    private LinphoneCoreListener mlinphoneCoreListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_main);

        stateTextView = (TextView) findViewById(R.id.state_textView);
        callStateTextView = (TextView) findViewById(R.id.call_state_textView);
        phoneEditText = (EditText) findViewById(R.id.call_editText);

//        SLinphoneSDK.init(this, "192.168.2.112", "5060", LinphoneAddress.TransportType.LinphoneTransportUdp);

//        SLinphoneSDK.getInstance().addSDKListener(new SLinphoneSDKListener() {
//            @Override
//            public void serviceIsReady() {
//                stateTextView.setText("Linphone service 已经准备，请开始注册");
//                String number = "188888";
//                SLinphoneSDK.register(number);
//            }
//        });

        mlinphoneCoreListener = new LinphoneCoreListener() {
            @Override
            public void authInfoRequested(LinphoneCore linphoneCore, String s, String s1, String s2) { }

            @Override
            public void authenticationRequested(LinphoneCore linphoneCore, LinphoneAuthInfo linphoneAuthInfo, LinphoneCore.AuthMethod authMethod) { }

            @Override
            public void callStatsUpdated(LinphoneCore linphoneCore, LinphoneCall linphoneCall, LinphoneCallStats linphoneCallStats) { }

            @Override
            public void newSubscriptionRequest(LinphoneCore linphoneCore, LinphoneFriend linphoneFriend, String s) { }

            @Override
            public void notifyPresenceReceived(LinphoneCore linphoneCore, LinphoneFriend linphoneFriend) { }

            @Override
            public void dtmfReceived(LinphoneCore linphoneCore, LinphoneCall linphoneCall, int i) { }

            @Override
            public void notifyReceived(LinphoneCore linphoneCore, LinphoneCall linphoneCall, LinphoneAddress linphoneAddress, byte[] bytes) { }

            @Override
            public void transferState(LinphoneCore linphoneCore, LinphoneCall linphoneCall, LinphoneCall.State state) { }

            @Override
            public void infoReceived(LinphoneCore linphoneCore, LinphoneCall linphoneCall, LinphoneInfoMessage linphoneInfoMessage) { }

            @Override
            public void subscriptionStateChanged(LinphoneCore linphoneCore, LinphoneEvent linphoneEvent, SubscriptionState subscriptionState) { }

            @Override
            public void publishStateChanged(LinphoneCore linphoneCore, LinphoneEvent linphoneEvent, PublishState publishState) { }

            @Override
            public void show(LinphoneCore linphoneCore) { }

            @Override
            public void displayStatus(LinphoneCore linphoneCore, String s) { }

            @Override
            public void displayMessage(LinphoneCore linphoneCore, String s) { }

            @Override
            public void displayWarning(LinphoneCore linphoneCore, String s) { }

            @Override
            public void fileTransferProgressIndication(LinphoneCore linphoneCore, LinphoneChatMessage linphoneChatMessage, LinphoneContent linphoneContent, int i) { }

            @Override
            public void fileTransferRecv(LinphoneCore linphoneCore, LinphoneChatMessage linphoneChatMessage, LinphoneContent linphoneContent, byte[] bytes, int i) {

            }

            @Override
            public int fileTransferSend(LinphoneCore linphoneCore, LinphoneChatMessage linphoneChatMessage, LinphoneContent linphoneContent, ByteBuffer byteBuffer, int i) {
                return 0;
            }

            @Override
            public void globalState(LinphoneCore linphoneCore, LinphoneCore.GlobalState globalState, String s) {

            }

            @Override
            public void registrationState(LinphoneCore linphoneCore, LinphoneProxyConfig linphoneProxyConfig, LinphoneCore.RegistrationState registrationState, String s) {
                stateTextView.setText("状态："+registrationState.toString());
            }

            @Override
            public void configuringStatus(LinphoneCore linphoneCore, LinphoneCore.RemoteProvisioningState remoteProvisioningState, String s) {

            }

            @Override
            public void messageReceived(LinphoneCore linphoneCore, LinphoneChatRoom linphoneChatRoom, LinphoneChatMessage linphoneChatMessage) {

            }

            @Override
            public void callState(LinphoneCore linphoneCore, LinphoneCall linphoneCall, LinphoneCall.State state, String s) {
                if (LinphoneManager.getLc().getCallsNb() == 0) {
                    return;
                }

                LinphoneAddress address = linphoneCall.getRemoteAddress();
                LinphoneContact contact = ContactsManager.getInstance().findContactFromAddress(address);
                String from = "";
                if (contact != null) {
                    from = contact.getFullName();
                }
                else {
                    from = LinphoneUtils.getAddressDisplayName(address);
                }

                if (state == LinphoneCall.State.IncomingReceived) {

                    stateTextView.setText(""+from);
                    callStateTextView.setText("来电号码: "+address.asStringUriOnly());
                    return;
                } else if (state == LinphoneCall.State.OutgoingInit || state == LinphoneCall.State.OutgoingProgress) {

                    stateTextView.setText(""+from);
                    callStateTextView.setText("去电号码: "+address.asStringUriOnly());
                }

                callStateTextView.setText("call State: "+linphoneCore.getPrimaryContact()+linphoneCall.getRemoteContact()+state.toString()+s);
            }

            @Override
            public void callEncryptionChanged(LinphoneCore linphoneCore, LinphoneCall linphoneCall, boolean b, String s) {

            }

            @Override
            public void notifyReceived(LinphoneCore linphoneCore, LinphoneEvent linphoneEvent, String s, LinphoneContent linphoneContent) {

            }

            @Override
            public void isComposingReceived(LinphoneCore linphoneCore, LinphoneChatRoom linphoneChatRoom) {

            }

            @Override
            public void ecCalibrationStatus(LinphoneCore linphoneCore, LinphoneCore.EcCalibratorStatus ecCalibratorStatus, int i, Object o) {

            }

            @Override
            public void uploadProgressIndication(LinphoneCore linphoneCore, int i, int i1) {

            }

            @Override
            public void uploadStateChanged(LinphoneCore linphoneCore, LinphoneCore.LogCollectionUploadState logCollectionUploadState, String s) {

            }

            @Override
            public void friendListCreated(LinphoneCore linphoneCore, LinphoneFriendList linphoneFriendList) {

            }

            @Override
            public void friendListRemoved(LinphoneCore linphoneCore, LinphoneFriendList linphoneFriendList) {

            }
        };

    }


    public void call(View v) {
       String phoneNumber = phoneEditText.getText().toString();
       if (phoneNumber.length() == 0)
           return;

       LinphoneManager.getInstance().routeAudioToSpeaker();
       SLinphoneSDK.callOutgoing(phoneNumber,"192.168.2.112");
    }

    public void hangup(View v) {
        SLinphoneSDK.hangup();
    }

    public void accept(View v) {
        LinphoneManager.getInstance().routeAudioToSpeaker();
        SLinphoneSDK.getInstance().acceptCall();
    }
}
