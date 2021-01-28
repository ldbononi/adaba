package ufu.facom.lia.interfaces;

import java.io.Serializable;

public enum Location implements Serializable {

	PV((short) 1), CRITICAL_TREE((short) 2), NORMAL((short) 3), OLD_PV((short) 4), ELDEST_BROTHER((short) 5);

	private short value;

	private Location(short value) {
		this.value = value;
	}

	public short getValue() {
		return this.value;
	}

}
