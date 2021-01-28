package ufu.facom.lia.cache;

import java.io.Serializable;

public class ItemTTable implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Entry e1;
	
	private Entry e2;

	public Entry getE1() {
		return e1;
	}

	public void setE1(Entry e1) {
		this.e1 = e1;
	}

	public Entry getE2() {
		return e2;
	}

	public void setE2(Entry e2) {
		this.e2 = e2;
	}
}
