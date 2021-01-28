package ufu.facom.lia.configs;

import java.math.BigDecimal;

public interface Configs {
	
	int INIT_HOST_ID = 1;
	
	int DEFAULT_PRIORITY = 4;
	
	//Quanto vai descer na árvore de busca a cada iteração
	int ITERATIVE_DEEPENING = 2;
	
	String MAX = "MAX";
	
	String MIN = "MIN";
	
	String LEAF = "LEAF";
	
	String SERVER_NAME = "APHID";
	
	String NEIGHBOR_SERVER_NAME="TABLESHADOW";
	
	int PORT_SHADOW_SERVER = 1099;
	
	//BigDecimal LOWER_BOUND = new BigDecimal("0.15");
	
	//BigDecimal UPPER_BOUND = new BigDecimal("0.15");
	
	//BigDecimal LOWER_BOUND = new BigDecimal("-0.3");
	
	//BigDecimal UPPER_BOUND = new BigDecimal("0.3");
	
	int BATCH_SIZE = 50;
	
	int INITIAL_SIZE_QUEUE = 50;
	
	String REGION_MASTER = "master";
	
	String REGION_SLAVE = "slave";
}
