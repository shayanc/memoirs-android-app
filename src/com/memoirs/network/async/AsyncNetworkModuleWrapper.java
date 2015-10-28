package com.memoirs.network.async;

import com.memoirs.application.interfaces.MemoryInterface;
import com.memoirs.network.async.interfaces.AsyncNetworkModuleInterface;
import com.memoirs.network.async.interfaces.WSRequestListener;
import com.memoirs.network.interfaces.NetworkModuleInterface;

public class AsyncNetworkModuleWrapper implements AsyncNetworkModuleInterface {

	private WSRequestListener clientListener;
	private NetworkModuleInterface netModule;
	public AsyncNetworkModuleWrapper (NetworkModuleInterface netModule, WSRequestListener listener){
		this.clientListener = listener;
		this.netModule = netModule;
	}
	@Override
	public void createMemory(MemoryInterface memory) {
		//Creates a new CreateMemory task
		AsyncCreateMemory createMemoryTask = new AsyncCreateMemory(netModule, clientListener);
		//executes it
		createMemoryTask.execute(memory);		
	}


	@Override
	public void deleteMemory(String phoneNumber) {
		AsyncDeleteMemory deleteMemoryTask = new AsyncDeleteMemory(netModule, clientListener);
		deleteMemoryTask.execute(phoneNumber);
		
	}
	@Override
	public void retrieveMemory(String phoneNumber, MemoryInterface memory) {
		AsyncRetrieveMemory retrieveMemoryTask = new AsyncRetrieveMemory(netModule, clientListener, memory);
		retrieveMemoryTask.execute(phoneNumber);
		
	}

}
