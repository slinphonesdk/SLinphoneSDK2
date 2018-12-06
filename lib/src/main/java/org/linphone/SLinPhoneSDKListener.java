package org.linphone;

import org.linphone.core.LinphoneProxyConfig;

public interface SLinphoneSDKListener
{
    void serviceIsReady();
    void callState(String from, String state, String s);
    void registrationState(String state, String s);

}
