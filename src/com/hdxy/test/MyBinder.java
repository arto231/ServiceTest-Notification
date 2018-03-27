package com.hdxy.test;

import android.os.Binder;

public class MyBinder extends Binder{
	
	public String getResult(){
		return "result";
	}

}
