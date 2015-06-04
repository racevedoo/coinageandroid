package br.ufpe.cin.coinage.adapters;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.ufpe.cin.coinage.android.R;
import br.ufpe.cin.coinage.android.MainApplication;
import br.ufpe.cin.coinage.model.SteamGame;

public class ResultsAdapter extends BaseAdapter{
	Activity activity;
    List<SteamGame> listItems;
 
    public ResultsAdapter(Activity activity, List<SteamGame> listItems) {
        this.activity = activity;
        this.listItems = listItems;
    }
	public int getCount() {
		return listItems.size();
	}

	public SteamGame getItem(int position) {
		return listItems.get(position);
	}

	public long getItemId(int position) {
		return getItem(position).getAppId();
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) MainApplication.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.list_item_game, parent, false);
		SteamGame game = getItem(position);
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
	public List<SteamGame> getListItems() {
		return listItems;
	}
	public void setListItems(List<SteamGame> listItems) {
		this.listItems = listItems;
	}
	
}