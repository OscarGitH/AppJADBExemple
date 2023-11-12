import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import java.io.IOException;

public class CallDetectionService extends Service {

    private PhoneStateReceiver phoneStateReceiver;
    private TelephonyManager telephonyManager;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startCallDetection();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        stopCallDetection();
        super.onDestroy();
    }

    private void startCallDetection() {
        phoneStateReceiver = new PhoneStateReceiver();
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneStateReceiver, PhoneStateListener.LISTEN_CALL_STATE);
    }

    private void stopCallDetection() {
        telephonyManager.listen(phoneStateReceiver, PhoneStateListener.LISTEN_NONE);
    }

    private class PhoneStateReceiver extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String phoneNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    // L'appel est terminé
                    try {
                        sendAdbCommand("Call ended");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    // L'appel est en cours
                    try {
                        sendAdbCommand("Call in progress");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    // L'appel est en cours de sonnerie
                    break;
            }
        }
    }

    private void sendAdbCommand(String message) throws IOException {
        String adbCommand = "adb shell am broadcast -a com.example.CallDetectionService -e message \"" + message + "\"";
        Runtime.getRuntime().exec(adbCommand);
    }
}