package eu.tobiasheine.wearcontrol;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SoundProfileListItem extends LinearLayout implements WearableListView.OnCenterProximityListener {

    private final float fadedTextAlpha;
    private final float imageScale;

    private TextView tvProfileName;
    private ImageView ivProfileImage;

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
        imageScale = getResources()
                .getInteger(R.integer.action_image_scale) / 100f;;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tvProfileName = (TextView) findViewById(R.id.profile_name);
        ivProfileImage = (ImageView) findViewById(R.id.profile_image);
    }

    @Override
    public void onCenterPosition(boolean b) {
        setValues(1f, 1f);
    }

    @Override
    public void onNonCenterPosition(boolean b) {
        setValues(fadedTextAlpha, imageScale);
    }

    private void setValues(float textAlpha, float scale) {
        ivProfileImage.setScaleX(scale);
        ivProfileImage.setScaleY(scale);

        ivProfileImage.setAlpha(textAlpha);
        tvProfileName.setAlpha(textAlpha);
    }
}


