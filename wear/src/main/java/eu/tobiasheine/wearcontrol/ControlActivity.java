package eu.tobiasheine.wearcontrol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.ConfirmationActivity;
import android.support.wearable.view.WearableListView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import eu.tobiasheine.wearcontrol.core.SoundProfile;


public class ControlActivity extends Activity implements WearableListView.ClickListener {

    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.wear_activity_control);

        WearableListView listView = (WearableListView) findViewById(R.id.sound_profiles_list);
        listView.setAdapter(new SoundProfilesListAdapter(this));
        listView.setClickListener(this);

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
    }

    @Override
    public void onClick(final WearableListView.ViewHolder viewHolder) {

        final SoundProfile profile = SoundProfile.values()[viewHolder.getPosition()];

        PendingResult<NodeApi.GetConnectedNodesResult> connectedNodes = Wearable.NodeApi.getConnectedNodes(googleApiClient);
        connectedNodes.setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
            @Override
            public void onResult(NodeApi.GetConnectedNodesResult getConnectedNodesResult) {
                for (final Node node : getConnectedNodesResult.getNodes()) {
                    Wearable.MessageApi.sendMessage(googleApiClient, node.getId(), profile.name(), null);
                }

                Intent intent = new Intent(ControlActivity.this, ConfirmationActivity.class);
                intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE,
                        ConfirmationActivity.SUCCESS_ANIMATION);
                intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE, getString(R.string.sound_profile_change_confirmation));
                startActivity(intent);

                finish();
            }
        });
    }

    @Override
    public void onTopEmptyRegionClick() {
        // nop
    }

}
