package br.ufpe.cin.coinage.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import br.ufpe.cin.coinage.android.MainApplication;
import br.ufpe.cin.coinage.android.R;
import br.ufpe.cin.coinage.database.DBHelper;
import br.ufpe.cin.coinage.fragments.NavigationDrawerFragment;
import br.ufpe.cin.coinage.model.Game;
import br.ufpe.cin.coinage.model.Product;
import br.ufpe.cin.coinage.model.Store;
import br.ufpe.cin.coinage.services.MyService;
import br.ufpe.cin.coinage.utils.Constants;
import br.ufpe.cin.coinage.utils.FragmentsCatalog;

public class GamesActivity extends Activity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {
	
	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;
	private FragmentsCatalog fragmentsCatalog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		fragmentsCatalog = new FragmentsCatalog(Constants.OPTIONS_TITLES, Constants.FRAGMENTS);
		setContentView(R.layout.activity_games);
		

		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));			
	}

	
	@Override
	public void onNavigationDrawerItemSelected(int position) {
		FragmentManager fragmentManager = getFragmentManager();
		Fragment replaceFragment = null;
		
		Bundle bundle = new Bundle();
		replaceFragment = Fragment.instantiate(this, 
				fragmentsCatalog.getFragmentConstants()[position], bundle);
		
		if (replaceFragment != null) {
			fragmentManager.beginTransaction()
					.replace(R.id.container, replaceFragment, fragmentsCatalog.getFragmentConstants()[position]).commit();
		}
	}
	
	
	
	public void onSectionAttached(int number) {
		mTitle = getString(fragmentsCatalog.getTitles()[number]);
	}

	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public void onBackPressed() {
		if(mNavigationDrawerFragment != null && mNavigationDrawerFragment.isAdded() && mNavigationDrawerFragment.isDrawerOpen()) {
			mNavigationDrawerFragment.closeDrawer();
		} else {
			super.onBackPressed();
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		
		return super.onOptionsItemSelected(item);
	}

	public boolean isDrawerOpen() {
		return mNavigationDrawerFragment.isDrawerOpen();
	}
	
	public FragmentsCatalog getFragmentsCatalog(){
		return fragmentsCatalog;
	}

}
