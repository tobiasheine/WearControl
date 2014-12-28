package eu.tobiasheine.wearcontrol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.ConfirmationActivity;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableListView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;

import eu.tobiasheine.wearcontrol.core.SoundProfile;


public class ControlActivity extends Activity implements WearableListView.ClickListener, SoundProfileChangedListener {

    private GoogleApiClient googleApiClient;

    private SoundProfileChangeTask profileChangeTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.wear_activity_control);

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);

        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                WearableListView listView = (WearableListView) findViewById(R.id.sound_profiles_list);
                listView.setAdapter(new SoundProfilesListAdapter(ControlActivity.this));
                listView.setClickListener(ControlActivity.this);
            }
        });

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .build();


    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }

        resetProfileChangeTask();
    }

    @Override
    public void onClick(final WearableListView.ViewHolder viewHolder) {
        final SoundProfile profile = SoundProfile.values()[viewHolder.getPosition()];

        resetProfileChangeTask();

        profileChangeTask = new SoundProfileChangeTask(googleApiClient);
        profileChangeTask.setOnSoundProfileChangedListener(this);

        profileChangeTask.execute(profile);
    }

    private void resetProfileChangeTask() {
        if (profileChangeTask != null) {
            profileChangeTask.cancel(true);
            profileChangeTask = null;
        }
    }

    @Override
    public void onTopEmptyRegionClick() {
        // nop
    }

    @Override
    public void onSuccess() {
        final Intent intent = new Intent(ControlActivity.this, ConfirmationActivity.class);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE,
                ConfirmationActivity.SUCCESS_ANIMATION);
        intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE, getString(R.string.sound_profile_change_confirmation));

        startActivity(intent);
        finish();
    }

    @Override
    public void onFailure() {
        final Intent intent = new Intent(ControlActivity.this, ConfirmationActivity.class);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE,
                ConfirmationActivity.FAILURE_ANIMATION);
        intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE, getString(R.string.sound_profile_change_failure));

        startActivity(intent);
    }
}
