package org.homi.plugin.api.observer;

public interface ISubject {
	
	public void attach(IObserver observer);
	
	public void detach(IObserver observer);
	
	public void notifyListeners(Object ...args);
	
}
