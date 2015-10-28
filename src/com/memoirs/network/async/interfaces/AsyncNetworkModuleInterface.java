package com.memoirs.network.async.interfaces;

import com.memoirs.application.interfaces.MemoryInterface;

public interface AsyncNetworkModuleInterface {
	public void createMemory (MemoryInterface memory) ;
	public void retrieveMemory (String phoneNumber, MemoryInterface memory) ;
	public void deleteMemory (String phoneNumber) ;
}
