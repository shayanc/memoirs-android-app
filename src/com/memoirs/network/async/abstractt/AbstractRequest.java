package com.memoirs.network.async.abstractt;

import com.memoirs.network.async.interfaces.WSRequestListener;
import com.memoirs.network.interfaces.NetworkModuleInterface;

import android.os.AsyncTask;

public abstract class AbstractRequest<T> extends AsyncTask<T,Void,Void>{
	protected NetworkModuleInterface netMod;
	protected WSRequestListener listener;
	protected Exception excp;
	public AbstractRequest (NetworkModuleInterface netModule, WSRequestListener listener){
		this.netMod = netModule;
		this.excp = null;
		this.listener = listener;
	}
	
}
