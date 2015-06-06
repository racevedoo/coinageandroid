package br.ufpe.cin.coinage.adapters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.ufpe.cin.coinage.android.R;
import br.ufpe.cin.coinage.android.MainApplication;
import br.ufpe.cin.coinage.model.ui.ResultAdapterItem;

public class ResultsAdapter extends BaseAdapter{
	Activity activity;
	List<ResultAdapterItem> listItems;
 
    public ResultsAdapter(Activity activity, List<ResultAdapterItem> listItems) {
        this.activity = activity;
        this.listItems = listItems;
    }
	public int getCount() {
		return listItems.size();
	}

	public ResultAdapterItem getItem(int position) {
		return listItems.get(position);
	}

	public long getItemId(int position) {
		return getItem(position).getAppId();
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) MainApplication.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.list_item_game, parent, false);
		ResultAdapterItem game = getItem(position);
		//TODO: add price to layout
		TextView titleTextView = (TextView) rowView.findViewById(R.id.firstLine);
		titleTextView.setText(game.getName());

		return rowView;
	}
	
	public Activity getActivity() {
		return activity;
	}
	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	public List<ResultAdapterItem> getListItems() {
		return listItems;
	}
	public void setListItems(List<ResultAdapterItem> listItems) {
		this.listItems = listItems;
	}
	
}