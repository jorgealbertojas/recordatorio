package com.softjads.cadeconsumo.service;

import android.content.Context;


public class DB {
	
	private static DataBase instance;


	public static DataBase getInstance(Context context) {
		
		
		if ( instance == null)
		{
			instance = new DataBase(context);
			

		}
		return instance;
		
		
	}
	
}
