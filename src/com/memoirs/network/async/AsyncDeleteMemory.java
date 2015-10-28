package com.memoirs.network.async;

import java.io.UnsupportedEncodingException;

import com.memoirs.network.async.abstractt.AbstractRequest;
import com.memoirs.network.async.interfaces.RequestErrorHolderInterface;
import com.memoirs.network.async.interfaces.WSRequestListener;
import com.memoirs.network.exceptions.NetworkModuleException;
import com.memoirs.network.interfaces.NetworkModuleInterface;

public class AsyncDeleteMemory extends AbstractRequest<String>{

	public AsyncDeleteMemory(NetworkModuleInterface netModule,
			WSRequestListener listener) {
		super(netModule, listener);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Void doInBackground(String... params) {
		
		try {
			netMod.deleteMemory(params[0]);
		} catch (UnsupportedEncodingException e) {
			excp = e;
		} catch (NetworkModuleException e) {
			excp = e;
		}
		return null;
	}
	
	@Override
	protected void onPostExecute (Void result){		
		RequestErrorHolderInterface errorHolder = new RequestErrorHolder(excp);
		listener.onDeleteMemoryDone(errorHolder);
	}

}
