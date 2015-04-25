package br.ufpe.cin.coinage.utils;

import android.content.Context;

public class FragmentsCatalog {
	
	private int[] mTitleConstants = null;
	
	private String[] mFragmentConstants = null;
	
	public FragmentsCatalog(int[] titles, String[] fragmentConstants) {
		mTitleConstants = titles;
		mFragmentConstants = fragmentConstants;
	}
	
	public int[] getTitles() {
		return mTitleConstants;
	}
	
	public String[] getFragmentConstants() {
		return mFragmentConstants;
	}

	public String[] loadTitles(Context context) {
		
		String[] titles = null;
		
		if(mTitleConstants != null) {
			titles = new String[mTitleConstants.length];
			
			for (int i = 0; i < titles.length; i++) {
				titles[i] = context.getResources().getString(mTitleConstants[i]);
			}
		}
		return titles;
	}


}
