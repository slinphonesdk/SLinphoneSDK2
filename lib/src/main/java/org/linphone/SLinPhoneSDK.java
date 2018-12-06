package org.linphone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import org.linphone.assistant.RemoteProvisioningActivity;
import org.linphone.core.LinphoneAddress;
import org.linphone.core.LinphoneAuthInfo;
import org.linphone.core.LinphoneCall;
import org.linphone.core.LinphoneCallStats;
import org.linphone.core.LinphoneChatMessage;
import org.linphone.core.LinphoneChatRoom;
import org.linphone.core.LinphoneContent;
import org.linphone.core.LinphoneCore;
import org.linphone.core.LinphoneCoreException;
import org.linphone.core.LinphoneCoreListener;
import org.linphone.core.LinphoneEvent;
import org.linphone.core.LinphoneFriend;
import org.linphone.core.LinphoneFriendList;
import org.linphone.core.LinphoneInfoMessage;
import org.linphone.core.LinphoneProxyConfig;
import org.linphone.core.PublishState;
import org.linphone.core.SubscriptionState;
import org.linphone.mediastream.Log;
import org.linphone.mediastream.Version;
import org.linphone.tutorials.TutorialLauncherActivity;

import java.nio.ByteBuffer;
import java.util.List;

import static android.content.Intent.ACTION_MAIN;

public class SLinphoneSDK
{
    private static SLinphoneSDK instance;
    private Handler mHandler;
    private ServiceWaitThread mThread;

    private String Host;
    private String port;
    private final LinphoneAddress.TransportType transportType = LinphoneAddress.TransportType.LinphoneTransportUdp;
    public static synchronized final SLinphoneSDK getInstance() {
        if (instance != null) return instance;

        throw new RuntimeException("Linphone Manager should be created before accessed");
    }

    public synchronized static final SLinphoneSDK init(Context context, String Host, String port) {
        if (instance != null)
            throw new RuntimeException("SLinphoneSDK is already initialized");

        instance = new SLinphoneSDK();
        instance.start(context);
        instance.Host = Host;
        instance.port = port;
        instance.mHandler = new Handler();
        return instance;
    }

    private synchronized void start(Context context) {

        if (LinphoneService.isReady()) {
            onServiceReady();
        } else {
            // start linphone as background
            context.startService(new Intent(ACTION_MAIN).setClass(context, LinphoneService.class));
            mThread = new ServiceWaitThread();
            mThread.start();
        }
    }

    private void onServiceReady() {
        setCoreListener();
        if (sLinphoneSDKListener != null) {
            instance.configCoreListener();
            sLinphoneSDKListener.serviceIsReady();
        }
    }

    private static void refreshAccounts(String number){
        if (LinphoneManager.getLc().getProxyConfigList().length > 1) {
            for(LinphoneProxyConfig proxyConfig : LinphoneManager.getLc().getProxyConfigList()){
                if (proxyConfig.isPhoneNumber(number)) {
                    LinphoneManager.getLc().setDefaultProxyConfig(proxyConfig);
                }
                else {
                    LinphoneManager.getLc().removeProxyConfig(proxyConfig);
                }
            }
        }
    }

    private static String defaultPwd = "123456";
    public static void register(String phonenumber) {
        if (instance == null)
            return;
        LinphonePreferences.AccountBuilder builder = new LinphonePreferences.AccountBuilder(LinphoneManager.getLc())
                .setUsername(phonenumber)
                .setDomain(instance.Host+":"+instance.port)
                .setPassword(defaultPwd)
                .setTransport(instance.transportType);
        try {
            builder.saveNewAccount();
        } catch (LinphoneCoreException e) {
            Log.e(e);
        }
        refreshAccounts(phonenumber);
    }

    private LinphoneCoreListener linphoneCoreListener;
    private void configCoreListener() {
        linphoneCoreListener = new LinphoneCoreListener() {
            @Override
            public void authInfoRequested(LinphoneCore linphoneCore, String s, String s1, String s2) {

            }

            @Override
            public void authenticationRequested(LinphoneCore linphoneCore, LinphoneAuthInfo linphoneAuthInfo, LinphoneCore.AuthMethod authMethod) {

            }

            @Override
            public void callStatsUpdated(LinphoneCore linphoneCore, LinphoneCall linphoneCall, LinphoneCallStats linphoneCallStats) {

            }

            @Override
            public void newSubscriptionRequest(LinphoneCore linphoneCore, LinphoneFriend linphoneFriend, String s) {

            }

            @Override
            public void notifyPresenceReceived(LinphoneCore linphoneCore, LinphoneFriend linphoneFriend) {

            }

            @Override
            public void dtmfReceived(LinphoneCore linphoneCore, LinphoneCall linphoneCall, int i) {

            }

            @Override
            public void notifyReceived(LinphoneCore linphoneCore, LinphoneCall linphoneCall, LinphoneAddress linphoneAddress, byte[] bytes) {

            }

            @Override
            public void transferState(LinphoneCore linphoneCore, LinphoneCall linphoneCall, LinphoneCall.State state) {

            }

            @Override
            public void infoReceived(LinphoneCore linphoneCore, LinphoneCall linphoneCall, LinphoneInfoMessage linphoneInfoMessage) {

            }

            @Override
            public void subscriptionStateChanged(LinphoneCore linphoneCore, LinphoneEvent linphoneEvent, SubscriptionState subscriptionState) {

            }

            @Override
            public void publishStateChanged(LinphoneCore linphoneCore, LinphoneEvent linphoneEvent, PublishState publishState) {

            }

            @Override
            public void show(LinphoneCore linphoneCore) {

            }

            @Override
            public void displayStatus(LinphoneCore linphoneCore, String s) {

            }

            @Override
            public void displayMessage(LinphoneCore linphoneCore, String s) {

            }

            @Override
            public void displayWarning(LinphoneCore linphoneCore, String s) {

            }

            @Override
            public void fileTransferProgressIndication(LinphoneCore linphoneCore, LinphoneChatMessage linphoneChatMessage, LinphoneContent linphoneContent, int i) {

            }

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
                if (sLinphoneSDKListener != null) {
                    sLinphoneSDKListener.registrationState(registrationState.toString(), s);
                }
            }

            @Override
            public void configuringStatus(LinphoneCore linphoneCore, LinphoneCore.RemoteProvisioningState remoteProvisioningState, String s) {

            }

            @Override
            public void messageReceived(LinphoneCore linphoneCore, LinphoneChatRoom linphoneChatRoom, LinphoneChatMessage linphoneChatMessage) {

            }

            @Override
            public void callState(LinphoneCore linphoneCore, LinphoneCall linphoneCall, LinphoneCall.State state, String s) {
                System.out.print("-----------------------------"+linphoneCall.getRemoteAddress().asStringUriOnly()+"\n"+s);
                if (sLinphoneSDKListener != null) {
                    sLinphoneSDKListener.callState(linphoneCall.getRemoteAddress().asStringUriOnly(), state.toString(), s);
                }
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

    private SLinphoneSDKListener sLinphoneSDKListener;
    public void addSDKListener(SLinphoneSDKListener sLinphoneSDKListener) {
        this.sLinphoneSDKListener = sLinphoneSDKListener;
    }

    public void setCoreListener() {
        if (instance == null)
            return;
        if (instance.linphoneCoreListener == null)
            instance.configCoreListener();

        LinphoneManager.getLc().addListener(instance.linphoneCoreListener);
    }

    public static void callOutgoing(String number) {
        if (instance == null || number == null || number.length() < 2)
            return;

        try {
            if (!LinphoneManager.getInstance().acceptCallIfIncomingPending()) {
                String to = String.format("sip:%s@%s", number, instance.Host+":"+instance.port);
                LinphoneManager.getInstance().newOutgoingCall(to, "");
            }
        } catch (LinphoneCoreException e) {
            LinphoneManager.getInstance().terminateCall();
        }
    }

    public static void callOutgoing(String number, String HOST) {
        if (instance == null || number == null || number.length() < 2)
            return;

        try {
            if (!LinphoneManager.getInstance().acceptCallIfIncomingPending()) {
                String to = String.format("sip:%s@%s", number, HOST+":"+instance.port);
                LinphoneManager.getInstance().newOutgoingCall(to, "");
            }
        } catch (LinphoneCoreException e) {
            LinphoneManager.getInstance().terminateCall();
        }
    }

    public static void hangup() {
        LinphoneManager.getInstance().terminateCall();
    }

    private LinphoneCall mCall;
    public static void acceptCall() {
        if (instance == null)
            return;

        List<LinphoneCall> calls = LinphoneUtils.getLinphoneCalls(LinphoneManager.getLc());
        if (calls.size() == 0)
            return;

        for (LinphoneCall call : calls) {
            if (LinphoneCall.State.IncomingReceived == call.getState()) {
                instance.mCall = call;
                break;
            }
        }
        LinphoneManager.getInstance().acceptCall(instance.mCall);
    }


    private class ServiceWaitThread extends Thread {
        public void run() {
            while (!LinphoneService.isReady()) {
                try {
                    sleep(30);
                } catch (InterruptedException e) {
                    throw new RuntimeException("waiting thread sleep() has been interrupted");
                }
            }
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    onServiceReady();
                }
            });
            mThread = null;
            mHandler = null;
        }
    }

    /*  1.启动服务
        2.注册（注册中，已注册，注册失败）
        3.呼出（）
        4.挂断
        5.通话状态监听（来电，通话中，挂断）
    */
}
