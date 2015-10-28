package com.memoirs.network.async.interfaces;

import java.io.UnsupportedEncodingException;

import com.memoirs.network.exceptions.NetworkModuleException;

public interface RequestErrorHolderInterface {
	boolean hasErrorOccurred  () throws NetworkModuleException, UnsupportedEncodingException;
}
