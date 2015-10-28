package com.memoirs.network.async;

import java.io.UnsupportedEncodingException;

import com.memoirs.application.interfaces.MemoryInterface;
import com.memoirs.network.async.abstractt.AbstractRequest;
import com.memoirs.network.async.interfaces.RequestErrorHolderInterface;
import com.memoirs.network.async.interfaces.WSRequestListener;
import com.memoirs.network.exceptions.NetworkModuleException;
import com.memoirs.network.interfaces.NetworkModuleInterface;

public class AsyncCreateMemory extends AbstractRequest<MemoryInterface>{

	private String phone_number;
	public AsyncCreateMemory(NetworkModuleInterface netModule,
			WSRequestListener listener) {
		super(netModule, listener);
		phone_number = null;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Void doInBackground(MemoryInterface... mems) {
		
		try {
			phone_number = netMod.createMemory(mems[0]);
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
		listener.onCreateMemoryDone(phone_number, errorHolder);
	}

}
