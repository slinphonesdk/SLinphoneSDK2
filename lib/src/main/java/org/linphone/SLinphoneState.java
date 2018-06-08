package org.linphone;

import org.linphone.core.LinphoneCall;

import java.util.Vector;

public class SLinphoneState
{
    private static Vector<SLinphoneState> values = new Vector();
    private final int mValue;
    private final String mStringValue;
    public static final SLinphoneState Idle = new SLinphoneState(0, "Idle");
    public static final SLinphoneState IncomingReceived = new SLinphoneState(1, "IncomingReceived");
        public static final SLinphoneState OutgoingInit = new SLinphoneState(2, "OutgoingInit");
        public static final SLinphoneState OutgoingProgress = new SLinphoneState(3, "OutgoingProgress");
        public static final SLinphoneState OutgoingRinging = new SLinphoneState(4, "OutgoingRinging");
        public static final SLinphoneState OutgoingEarlyMedia = new SLinphoneState(5, "OutgoingEarlyMedia");
        public static final SLinphoneState Connected = new SLinphoneState(6, "Connected");
        public static final SLinphoneState StreamsRunning = new SLinphoneState(7, "StreamsRunning");
        public static final SLinphoneState Pausing = new SLinphoneState(8, "Pausing");
        public static final SLinphoneState Paused = new SLinphoneState(9, "Paused");
        public static final SLinphoneState Resuming = new SLinphoneState(10, "Resuming");
        public static final SLinphoneState Refered = new SLinphoneState(11, "Refered");
        public static final SLinphoneState Error = new SLinphoneState(12, "Error");
        public static final SLinphoneState CallEnd = new SLinphoneState(13, "CallEnd");
        public static final SLinphoneState PausedByRemote = new SLinphoneState(14, "PausedByRemote");
        public static final SLinphoneState CallUpdatedByRemote = new SLinphoneState(15, "UpdatedByRemote");
        public static final SLinphoneState CallIncomingEarlyMedia = new SLinphoneState(16, "IncomingEarlyMedia");
        public static final SLinphoneState CallUpdating = new SLinphoneState(17, "Updating");
        public static final SLinphoneState CallReleased = new SLinphoneState(18, "Released");
        public static final SLinphoneState CallEarlyUpdatedByRemote = new SLinphoneState(19, "EarlyUpdatedByRemote");
        public static final SLinphoneState CallEarlyUpdating = new SLinphoneState(20, "EarlyUpdating");

        public final int value() {
            return this.mValue;
        }

        public SLinphoneState(int value, String stringValue) {
            this.mValue = value;
            values.addElement(this);
            this.mStringValue = stringValue;
        }

        public static SLinphoneState fromInt(int value) {
            for(int i = 0; i < values.size(); ++i) {
                SLinphoneState state = (SLinphoneState)values.elementAt(i);
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
