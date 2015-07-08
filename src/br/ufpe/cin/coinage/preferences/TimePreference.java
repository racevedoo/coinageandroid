package br.ufpe.cin.coinage.preferences;


import java.util.concurrent.TimeUnit;

import br.ufpe.cin.coinage.android.MainApplication;
import br.ufpe.cin.coinage.android.R;
import br.ufpe.cin.coinage.services.MyService;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.NumberPicker;

public class TimePreference extends DialogPreference{
	
	private static final long DEFAULT_INTERVAL2 = 86400000; //24h em ms
	private static final long DEFAULT_INTERVAL = 30000; //30s
	private long interval;
	private NumberPicker picker = null;

	public TimePreference(Context context) {
		this(context, null);
	}
	
	public TimePreference(Context context, AttributeSet attributes) {
		this(context, attributes, android.R.attr.dialogPreferenceStyle);
	}
	
	public TimePreference(Context context, AttributeSet attributes, int defStyle) {
		super(context, attributes, defStyle);
		
		setPositiveButtonText(R.string.alterar);
		setNegativeButtonText(R.string.cancelar);
		this.interval = DEFAULT_INTERVAL;
	}
	
	@Override
	protected View onCreateDialogView() {
		this.picker = new NumberPicker(getContext());
		this.picker.setMinValue(1);
		this.picker.setMaxValue(24);
		return this.picker;
	}
	
	@Override
	protected void onDialogClosed(boolean positiveResult) {
		super.onDialogClosed(positiveResult);
		
		if(positiveResult) {
			setInterval(picker.getValue());
			if (picker.getValue() == 7)
				this.interval = 60000;
			if (picker.getValue() == 8)
				this.interval = 30000;
			
			setSummary(getSummary());
			if(callChangeListener(interval)) {
				persistLong(interval);
				AlarmManager alarmManager = (AlarmManager) MainApplication.getContext().getSystemService(Context.ALARM_SERVICE);
				alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+this.interval, PendingIntent.getService(MainApplication.getContext(), 0, new Intent(MainApplication.getContext(), MyService.class), 0));
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
				interval = getPersistedLong(DEFAULT_INTERVAL);
			else
				setInterval(Long.parseLong(getPersistedString((String) defaultValue)));
		}
		else {
			if (defaultValue == null)
				interval = DEFAULT_INTERVAL;
			else
				setInterval(Long.parseLong((String) defaultValue));
		}
		
		setSummary(getSummary());
	}
	
	@Override
	public CharSequence getSummary() {		
		return getInterval() + "h";
	}	
	
	private void setInterval(long hour) {
		this.interval = TimeUnit.HOURS.toMillis(hour);
	}
	
	private long getInterval() {
		return TimeUnit.MILLISECONDS.toHours(this.interval);
	}

}





















