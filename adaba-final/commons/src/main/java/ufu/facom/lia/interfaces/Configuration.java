package ufu.facom.lia.interfaces;

import java.math.RoundingMode;

public interface Configuration {
	
	String NET_EXTENSION = ".NET";
	
	int BIG_DEC_SCALE = 6;
	
	RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;
	
	int TOTAL_MOVES_ARRAY = 5;
	
	int LENGTH_ARRAY_UPDATE = 3;
	
	int INITIAL_CAPACITY_LIST = 8;
	
	int MOVES_TO_CLASSIFY_LIKE_DRAW = 40;//voltar para 75, está assim só para os agentes de final de jogo
	
	int MIN_MOVES_TO_AVALIATE_GAME = 20;
	
	int INCREASE_ITERATIVE_DEEPENING = 1;//TODO: MUDEI AQUI
	
	String REGION_MASTER = "master";
	
	int FEATURE_POSITION = 32;
	
	float BIAS_VALUE = 1.0f;

}
