package ufu.facom.lia.interfaces.cache;

import java.util.Map;

import ufu.facom.lia.cache.Entry;
import ufu.facom.lia.cache.Key;

public interface ICacheShadow extends ICache {
	
	void addShadowTable(Map<Key, Entry> table);
	
	void allowSendNextTT();
	
}
