package org.homi.plugin.api.commander;

import org.homi.plugin.api.exceptions.InternalPluginException;

public interface IReceiver<T> {

	T doAction(Object ...args) throws InternalPluginException;
}
