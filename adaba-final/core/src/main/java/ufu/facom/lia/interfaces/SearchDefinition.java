package ufu.facom.lia.interfaces;

public enum SearchDefinition {

	DEPTH_FIRST("DEPTH_FIRST"), DEPTH_FIRST_WITH_TT("DEPTH_FIRST_WITH_TT"), ITERATIVE_DEEPENING("ITERATIVE_DEEPENING");

	private String value;

	private SearchDefinition(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
