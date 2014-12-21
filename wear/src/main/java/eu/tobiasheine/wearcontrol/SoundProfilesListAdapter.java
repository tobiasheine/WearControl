package eu.tobiasheine.wearcontrol;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;

public class SoundProfilesListAdapter extends WearableListView.Adapter {

    private final Context context;
    private final LayoutInflater inflater;

    public SoundProfilesListAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public static class ProfileViewHolder extends WearableListView.ViewHolder {
        private TextView tvProfileName;
        public ProfileViewHolder(View itemView) {
            super(itemView);
            // find the text view within the custom item's layout
            tvProfileName = (TextView) itemView.findViewById(R.id.profile_name);
        }
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProfileViewHolder(inflater.inflate(R.layout.sound_profile_list_item, null));
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {
        ProfileViewHolder profileViewHolder = (ProfileViewHolder) holder;
        ((ProfileViewHolder) holder).tvProfileName.setText("Foo "+position);
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
