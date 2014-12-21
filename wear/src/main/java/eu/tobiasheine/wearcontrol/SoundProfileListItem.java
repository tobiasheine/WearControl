package eu.tobiasheine.wearcontrol;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SoundProfileListItem extends LinearLayout implements WearableListView.OnCenterProximityListener {

    private final float fadedTextAlpha;
    private TextView tvProfileName;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tvProfileName = (TextView) findViewById(R.id.profile_name);

    }

    public SoundProfileListItem(Context context) {
        this(context, null);
    }

    public SoundProfileListItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SoundProfileListItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        fadedTextAlpha = getResources()
                .getInteger(R.integer.action_text_faded_alpha) / 100f;
    }

    @Override
    public void onCenterPosition(boolean b) {
        tvProfileName.setAlpha(1f);
    }

    @Override
    public void onNonCenterPosition(boolean b) {
        tvProfileName.setAlpha(fadedTextAlpha);
    }
}


