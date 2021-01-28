package ufu.facom.lia.cache;

import java.io.Serializable;

public class Key implements Serializable {

	private static final long serialVersionUID = 1L;

	private long key64;

	private int key32;

	public Key(long key64, int key32) {

		this.key64 = key64;
		this.key32 = key32;
	}
	
	

	public long getKey64() {
		return key64;
	}

	public int getKey32() {
		return key32;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + key32;
		result = prime * result + (int) (key64 ^ (key64 >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Key other = (Key) obj;
		if (key32 != other.key32)
			return false;
		if (key64 != other.key64)
			return false;
		return true;
	}

}
