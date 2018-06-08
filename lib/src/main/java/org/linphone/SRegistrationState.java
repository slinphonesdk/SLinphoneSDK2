package org.linphone;

import org.linphone.core.LinphoneCore;

import java.util.Vector;

public class SRegistrationState {

    private static Vector<SRegistrationState> values = new Vector();
    public static SRegistrationState RegistrationNone = new SRegistrationState(0, "RegistrationNone");
    public static SRegistrationState RegistrationProgress = new SRegistrationState(1, "RegistrationProgress");
    public static SRegistrationState RegistrationOk = new SRegistrationState(2, "RegistrationOk");
    public static SRegistrationState RegistrationCleared = new SRegistrationState(3, "RegistrationCleared");
    public static SRegistrationState RegistrationFailed = new SRegistrationState(4, "RegistrationFailed");
    private final int mValue;
    private final String mStringValue;

    public SRegistrationState(int value, String stringValue) {
        this.mValue = value;
        values.addElement(this);
        this.mStringValue = stringValue;
    }

    public static SRegistrationState fromInt(int value) {
        for(int i = 0; i < values.size(); ++i) {
            SRegistrationState state = (SRegistrationState)values.elementAt(i);
            if (state.mValue == value) {
                return state;
            }
        }

        throw new RuntimeException("state not found [" + value + "]");
    }

    public String toString() {
        return this.mStringValue;
    }
}
