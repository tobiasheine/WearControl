package eu.tobiasheine.wearcontrol;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import eu.tobiasheine.wearcontrol.core.SoundProfile;


public class SoundProfilesListAdapter extends WearableListView.Adapter {

    private final LayoutInflater inflater;
    private final Context context;

    public SoundProfilesListAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    public static class ProfileViewHolder extends WearableListView.ViewHolder {

        private TextView tvProfileName;
        private ImageView ivProfileImage;

        public ProfileViewHolder(View itemView) {
            super(itemView);
            tvProfileName = (TextView) itemView.findViewById(R.id.profile_name);
            ivProfileImage = (ImageView) itemView.findViewById(R.id.profile_image);
        }
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProfileViewHolder(inflater.inflate(R.layout.sound_profile_list_item, null));
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {
        SoundProfile profile = SoundProfile.values()[position];

        ProfileViewHolder profileViewHolder = (ProfileViewHolder) holder;

        switch (profile) {
            case SOUND_ON:
                profileViewHolder.tvProfileName.setText(context.getString(R.string.sound_profile_on));
                profileViewHolder.ivProfileImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_volume_up_black_48dp));
                break;

            case SOUND_OFF:
                profileViewHolder.tvProfileName.setText(context.getString(R.string.sound_profile_off));
                profileViewHolder.ivProfileImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_volume_off_black_48dp));
                break;

            case VIBRATE:
                profileViewHolder.tvProfileName.setText(context.getString(R.string.sound_profile_vibrate));
                profileViewHolder.ivProfileImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_vibrate));
                break;

            default:
                profileViewHolder.tvProfileName.setText("unknown");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return SoundProfile.values().length;
    }
}
