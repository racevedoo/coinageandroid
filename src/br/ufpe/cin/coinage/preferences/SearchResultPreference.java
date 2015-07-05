package br.ufpe.cin.coinage.preferences;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.NumberPicker;
import br.ufpe.cin.coinage.android.R;

public class SearchResultPreference extends DialogPreference{
	
	private final int DEFAULT_QUANTITY = 3;
	private int quantity;
	private NumberPicker picker = null;

	public SearchResultPreference(Context context) {
		this(context, null);
	}
	
	public SearchResultPreference(Context context, AttributeSet attributes) {
		this(context, attributes, android.R.attr.dialogPreferenceStyle);
	}
	
	public SearchResultPreference(Context context, AttributeSet attributes, int defStyle) {
		super(context, attributes, defStyle);
		
		setPositiveButtonText(R.string.alterar);
		setNegativeButtonText(R.string.cancelar);
		this.quantity = DEFAULT_QUANTITY;
	}
	
	@Override
	protected View onCreateDialogView() {
		this.picker = new NumberPicker(getContext());
		this.picker.setMinValue(1);
		this.picker.setMaxValue(10);
		return this.picker;
	}
	
	@Override
	protected void onDialogClosed(boolean positiveResult) {
		super.onDialogClosed(positiveResult);
		
		if(positiveResult) {
			this.quantity = picker.getValue();
			
			setSummary(getSummary());
			if(callChangeListener(this.quantity)) {
				persistLong(this.quantity);
				notifyChanged();
			}			
		}
	}
	
	@Override
	protected Object onGetDefaultValue(TypedArray a, int index) {
		return (a.getString(index));
	}
	
	@Override
	protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {		
		if (restoreValue) {
			if (defaultValue == null)
				this.quantity = (int) getPersistedLong(DEFAULT_QUANTITY);
			else
				this.quantity = Integer.parseInt(getPersistedString((String) defaultValue));
		}
		else {
			if (defaultValue == null)
				this.quantity = DEFAULT_QUANTITY;
			else
				this.quantity = Integer.parseInt((String) defaultValue);
		}
		
		setSummary(getSummary());
	}
	
	@Override
	public CharSequence getSummary() {		
		return this.quantity + "";
	}

}






























































