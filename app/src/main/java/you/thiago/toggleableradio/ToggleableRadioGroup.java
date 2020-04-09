package you.thiago.toggleableradio;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

@SuppressWarnings({"unused"})
public class ToggleableRadioGroup extends LinearLayout implements RadioGroup.OnCheckedChangeListener {

    public final static int UNCHECKED_ID = -1;

    private RadioGroup radioGroup;
    private int checkedId;
    private boolean preventUncheck;
    private boolean hasError;

    private RadioGroup.OnCheckedChangeListener checkedListener;
    private RadioGroup.OnCheckedChangeListener uncheckedListener;
    private ToggleableChange changeListener;

    public ToggleableRadioGroup(Context context) {
        super(context);
        init(context, null);
    }

    public ToggleableRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ToggleableRadioGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void initComponent(Context context, AttributeSet attrs) {
        radioGroup = new RadioGroup(context, attrs);
        radioGroup.setOnCheckedChangeListener(this);
    }

    private void init(Context context, AttributeSet attrs) {
        /* init with default id */
        checkedId = ToggleableRadioGroup.UNCHECKED_ID;

        /* config view components */
        initComponent(context, attrs);
        addView(radioGroup);
    }

    /**
     * Check specific child
     */
    public void check(int radioButtonId) {
        ToggleableRadioButton child = findViewById(radioButtonId);
        if (child != null) {
            child.setChecked(true);
        }
    }

    /**
     * Uncheck specific child
     */
    public void uncheck(int radioButtonId) {
        ToggleableRadioButton child = findViewById(radioButtonId);
        if (child != null) {
            child.setChecked(false);
        }
    }

    public int getCheckedId() {
        return checkedId;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        this.checkedId = checkedId;
    }

    public boolean isToggleable() {
        return !preventUncheck;
    }

    public void setPreventUncheck(boolean prevent) {
        preventUncheck = prevent;
    }

    /**
     * Set only/specific setChecked listener
     */
    public void setOnCheckedChangedListener(RadioGroup.OnCheckedChangeListener listener) {
        checkedListener = listener;
    }

    /**
     * Set only/specific setUnchecked listener
     */
    public void setOnUncheckedChangedListener(RadioGroup.OnCheckedChangeListener listener) {
        uncheckedListener = listener;
    }

    /**
     * Set change listener (setChecked/setUnchecked)
     */
    public void setChangeListener(ToggleableChange listener) {
        changeListener = listener;
    }

    public void setError(boolean hasError) {
        this.hasError = hasError;

        /* setUnchecked and reset children color */
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            if (getChildAt(i) instanceof ToggleableRadioButton) {
                ToggleableRadioButton child = (ToggleableRadioButton) getChildAt(i);
                child.setError(hasError);
            }
        }
    }

    protected void setChecked(int checkedId) {
        this.checkedId = checkedId;

        if (checkedListener != null) {
            checkedListener.onCheckedChanged(radioGroup, checkedId);
        } else if (changeListener != null) {
            changeListener.onCheckedListener(radioGroup, checkedId);
        }

        /* remove not set errors */
        if (hasError) {
            setError(false);
        }
    }

    protected void setUnchecked() {
        if (uncheckedListener != null) {
            uncheckedListener.onCheckedChanged(radioGroup, checkedId);
        } else if (changeListener != null) {
            changeListener.onUncheckedListener(radioGroup, checkedId);
        }

        checkedId = ToggleableRadioGroup.UNCHECKED_ID;
    }

    /**
     * Complete change listener (setChecked/setUnchecked)
     */
    public interface ToggleableChange {
        /**
         * Event on radio is checked
         */
        void onCheckedListener(RadioGroup radioGroup, int checkedId);

        /**
         * Event on radio is unchecked
         */
        void onUncheckedListener(RadioGroup radioGroup, int checkedId);
    }
}
