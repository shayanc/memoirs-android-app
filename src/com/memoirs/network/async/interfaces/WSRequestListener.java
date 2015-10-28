package com.memoirs.network.async.interfaces;

import com.memoirs.application.interfaces.MemoryInterface;

public interface WSRequestListener {
	void onCreateMemoryDone (String phone_number, RequestErrorHolderInterface errorHolder);
	void onRetrieveMemoryDone (MemoryInterface memory, RequestErrorHolderInterface errorHolder);
	void onDeleteMemoryDone (RequestErrorHolderInterface errorHolder);
}
