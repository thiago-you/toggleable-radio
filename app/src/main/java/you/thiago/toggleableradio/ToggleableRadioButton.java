package you.thiago.toggleableradio;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.core.content.ContextCompat;
import androidx.core.widget.CompoundButtonCompat;

@SuppressWarnings({"WeakerAccess"})
public class ToggleableRadioButton extends LinearLayout implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private static final int CHECKED_COLOR = R.color.colorSecondary;
    private static final int UNCHECKED_COLOR = R.color.textColor;

    private final Context context;

    private boolean isChecked;
    private RadioButton button;
    private ToggleableRadioGroup parent;

    public ToggleableRadioButton(Context context) {
        super(context);

        this.context = context;
        init(context, null);
        configViewColor(UNCHECKED_COLOR);
    }

    public ToggleableRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        init(context, attrs);
        configViewColor(UNCHECKED_COLOR);
    }

    public ToggleableRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;
        init(context, attrs);
        configViewColor(UNCHECKED_COLOR);
    }

    public void configViewColor(int color) {
        /* set color states list */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            button.setButtonTintList(ColorStateList.valueOf(getContext().getColor(color)));
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                button.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), color)));
            } else {
                CompoundButtonCompat.setButtonTintList(button, ColorStateList.valueOf(ContextCompat.getColor(getContext(), color)));
            }
        }
    }

    private void initComponent(Context context, AttributeSet attrs) {
        /* create button */
        button = new RadioButton(context, attrs);
        button.setOnClickListener(this);
        button.setOnCheckedChangeListener(this);
    }

    private void init(Context context, AttributeSet attrs) {
        initComponent(context, attrs);
        addView(button);
    }

    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void onClick(View v) {
        /* setUnchecked and reset children color */
        int count = getGroupParent().getChildCount();
        for (int i = 0; i < count; i++) {
            if (getGroupParent().getChildAt(i) instanceof ToggleableRadioButton) {
                ToggleableRadioButton child = (ToggleableRadioButton) getGroupParent().getChildAt(i);

                if (child.getId() != button.getId()) {
                    child.setChecked(false);
                }
            }
        }

        /* validate and set toggle value */
        setChecked(!getGroupParent().isToggleable() || !isChecked);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (this.isChecked()) {
            configViewColor(CHECKED_COLOR);
        } else {
            configViewColor(UNCHECKED_COLOR);
        }
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
        button.setChecked(isChecked);

        if (isChecked) {
            getGroupParent().setChecked(button.getId());
            configViewColor(CHECKED_COLOR);
        } else {
            getGroupParent().setUnchecked();
            configViewColor(UNCHECKED_COLOR);
        }
    }

    private ToggleableRadioGroup getGroupParent() {
        if (parent == null) {
            if (getParent() instanceof ToggleableRadioGroup) {
                parent = (ToggleableRadioGroup) getParent();
            } else {
                parent = new ToggleableRadioGroup(context);
            }
        }

        return parent;
    }

    public void setError(boolean hasError) {
        if (hasError) {
            button.setError(context.getString(R.string.toggleable_radio_not_set_error));
        } else {
            button.setError(null);
        }
    }
}
