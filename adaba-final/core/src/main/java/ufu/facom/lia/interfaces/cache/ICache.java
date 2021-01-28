package ufu.facom.lia.interfaces.cache;

import java.util.Map;

import ufu.facom.lia.cache.Entry;
import ufu.facom.lia.cache.Key;
import ufu.facom.lia.exceptions.TranspositionTableException;
import ufu.facom.lia.interfaces.INode;

public interface ICache {
	
	void store(INode state, short scoreType) throws TranspositionTableException;
	
	Boolean get(INode state, short type, int depth, float alpha, float beta);
	
	Boolean getToHeuritic(INode state, short type, int depth, float alpha, float beta);
	
	void get(INode state, int depth);
	
	void addAll(Map<Key, Entry> shadowTable);
	
	void update(INode state);
	
	void remove(INode state);
	
	void clear();
	
	void setMasterDepth(int masterDepth);
}
