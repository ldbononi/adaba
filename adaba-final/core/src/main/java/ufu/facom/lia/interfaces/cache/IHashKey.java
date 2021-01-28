package ufu.facom.lia.interfaces.cache;

import ufu.facom.lia.interfaces.INode;

public interface IHashKey {
	
	long getKey64(INode state);
	
	int getKey32(INode state);

}
