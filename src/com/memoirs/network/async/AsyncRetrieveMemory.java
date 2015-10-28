package com.memoirs.network.async;

import java.io.UnsupportedEncodingException;

import com.memoirs.application.interfaces.MemoryInterface;
import com.memoirs.network.async.abstractt.AbstractRequest;
import com.memoirs.network.async.interfaces.RequestErrorHolderInterface;
import com.memoirs.network.async.interfaces.WSRequestListener;
import com.memoirs.network.exceptions.NetworkModuleException;
import com.memoirs.network.interfaces.NetworkModuleInterface;

public class AsyncRetrieveMemory extends AbstractRequest<String> {

	private MemoryInterface destMemory;
	public AsyncRetrieveMemory(NetworkModuleInterface netModule,
			WSRequestListener listener, MemoryInterface memory) {
		super(netModule, listener);
		destMemory = memory;
	}

	@Override
	protected Void doInBackground(String... arg0) {
		
	
		try {
			netMod.retrieveMemory(arg0[0], destMemory);
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
		listener.onRetrieveMemoryDone(destMemory, errorHolder);
	}

}
