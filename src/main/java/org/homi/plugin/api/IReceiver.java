package org.homi.plugin.api;

import org.homi.plugin.api.exceptions.InternalPluginException;

public interface IReceiver<T> {

	T doAction(Object ...args) throws InternalPluginException;
}
