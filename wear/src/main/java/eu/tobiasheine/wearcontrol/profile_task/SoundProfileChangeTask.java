package eu.tobiasheine.wearcontrol.profile_task;

import android.os.AsyncTask;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableStatusCodes;

import java.util.List;

import eu.tobiasheine.wearcontrol.core.SoundProfile;
import eu.tobiasheine.wearcontrol.profile_task.ISoundProfileChangedListener;

public class SoundProfileChangeTask extends AsyncTask<SoundProfile, Integer, com.google.android.gms.common.api.Status> {

    private final GoogleApiClient googleApiClient;

    private ISoundProfileChangedListener profileChangedListener;

    public SoundProfileChangeTask(GoogleApiClient googleApiClient) {
        this.googleApiClient = googleApiClient;
    }

    @Override
    protected com.google.android.gms.common.api.Status doInBackground(SoundProfile... params) {

        final List<Node> connectedNodes = Wearable.NodeApi.getConnectedNodes(googleApiClient).await().getNodes();

        if (connectedNodes.isEmpty()) {
            return new com.google.android.gms.common.api.Status(WearableStatusCodes.ERROR);
        }

        com.google.android.gms.common.api.Status status = null;

        for (Node node : connectedNodes) {
            if (status == null || !status.isSuccess()) {
                status = Wearable.MessageApi.sendMessage(googleApiClient, node.getId(), params[0].name(), null).await().getStatus();
            }
        }

        return status;
    }

    @Override
    protected void onPostExecute(com.google.android.gms.common.api.Status result) {
        super.onPostExecute(result);

        if (profileChangedListener != null) {
            if (result.isSuccess()) {
                profileChangedListener.onSuccess();
            } else {
                profileChangedListener.onFailure();
            }
        }
    }

    public void setOnSoundProfileChangedListener(final ISoundProfileChangedListener profileChangedListener) {
        this.profileChangedListener = profileChangedListener;
    }
}
