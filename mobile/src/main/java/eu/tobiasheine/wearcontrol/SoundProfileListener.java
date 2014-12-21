package eu.tobiasheine.wearcontrol;

import android.content.Context;
import android.media.AudioManager;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import eu.tobiasheine.wearcontrol.core.Const;
import eu.tobiasheine.wearcontrol.core.SoundProfile;

public class SoundProfileListener extends WearableListenerService {

    private AudioManager audioManager;

    @Override
    public void onCreate() {
        super.onCreate();
        audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);
        SoundProfile profile = SoundProfile.valueOf(messageEvent.getPath());

        switch (profile) {
            case SOUND_ON:
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                break;

            case SOUND_OFF:
                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                break;

            case VIBRATE:
                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                break;

        }

        Log.d(Const.TAG, "on profile changed: " + profile.name());
    }
}
