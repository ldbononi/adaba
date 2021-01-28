package ufu.facom.lia.cache;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ufu.facom.lia.exceptions.TranspositionTableException;
import ufu.facom.lia.interfaces.INode;
import ufu.facom.lia.interfaces.ScoreType;
import ufu.facom.lia.interfaces.cache.ICache;
import ufu.facom.lia.search.interfaces.Type;

public class TranspositionTable implements ICache{
	
	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(TranspositionTable.class);

	private Map<Key, Entry> ttable;
		
	private int masterDepth;
	
	/*public TranspositionTable(IHashKey hashKey) {
		
		this.hashKey = hashKey;
		
		ttable = new Hashtable<Key, Entry>();
		
	}*/
	
	public TranspositionTable() {
		
		if(ttable == null) {			
			ttable = new HashMap<Key, Entry>();
		}
		
	}
	
	@Override
	public synchronized void store(INode state, short scoreType) throws TranspositionTableException{

		if(state.getEvaluation() != 0f){

			Entry entry = new Entry();
			
			entry.setState(state);
			entry.setScoreType(scoreType);
			
			//logger.warn("ENTRY: " + state.getDepth() + " ScoreType: " + scoreType);
			
			//entry.setKey(state.getKey());
					
			ttable.put(state.getKey(), entry);
			
		}
		
	}
	
	public Boolean getToHeuritic(INode state, short type, int depth, float alpha, float beta) {

		Entry entry = ttable.get(state.getKey());
		
		if (entry != null && entry.getState().equals(state) && entry.getState().getType() == state.getType() && entry.getState().getEvaluation() != 0f) {
			
			if(entry.getScoreType() == ScoreType.HASH_EXACT.getValue()){
				state.setEvaluation(entry.getState().getEvaluation());
				state.setMove(entry.getState().getMove());
				
				return true;
			}

		}

		return false;
	}

	@Override
	public Boolean get(INode state, short type, int depth, float alpha, float beta) {

		Entry entry = ttable.get(state.getKey());

		if (entry != null && entry.getState().equals(state) && entry.getState().getType() == state.getType() && entry.getState().getEvaluation() != 0f && entry.getState().getDepth() >= (depth + masterDepth)) {

		/*	if (type == Type.MAX.getValue()) {
				
		
				
				 if(entry.getState().getEvaluation() >= beta) {
						state.setEvaluation(entry.getState().getEvaluation());
						state.setMove(entry.getState().getMove());
						//state.setChildren(entry.getState().getChildren());
						
						//logger.warn("Tipo nó: " + entry.getState().getType() + "T: " + type + " P-Search: " + (depth + masterDepth) + " P-NodeFound: " + entry.getState().getDepth() + " " + " scoretype: " + entry.getScoreType() + " evaluation <= beta: " + (entry.getState().getEvaluation() <= beta));
						
						return true;
					}
				
			} else if (type == Type.MIN.getValue()) {
				
				if(entry.getState().getEvaluation() <= alpha) {
						state.setEvaluation(entry.getState().getEvaluation());
						state.setMove(entry.getState().getMove());
						
						//logger.warn("Tipo nó: " + entry.getState().getType() + "T: " + type + " P-Search: " + (depth + masterDepth) + " P-NodeFound: " + entry.getState().getDepth() + " " + " scoretype: " + entry.getScoreType() + " evaluation <= beta: " + (entry.getState().getEvaluation() <= beta));
						
						return true;
					}

			
			}*/
			

			if (type == Type.MAX.getValue()) {
				
				if ((entry.getState().getDepth() >= (depth + masterDepth)) && entry.getState().getEvaluation() != 0f && (entry.getScoreType() == ScoreType.HASH_EXACT.getValue()
						|| entry.getState().getEvaluation() <= alpha )) {
					state.setEvaluation(entry.getState().getEvaluation());
					state.setMove(entry.getState().getMove());
					
					//logger.warn("Tipo nó: " + entry.getState().getType() + "T: " + type + " P-Search: " + (depth + masterDepth) + " P-NodeFound: " + entry.getState().getDepth() + " " + " scoretype: " + entry.getScoreType() + " evaluation <= beta: " + (entry.getState().getEvaluation() <= beta));
					return true;
				}

			} else if (type == Type.MIN.getValue()) {

				if ((entry.getState().getDepth() >= (depth + masterDepth)) && entry.getState().getEvaluation() != 0f && (entry.getScoreType() == ScoreType.HASH_EXACT.getValue()
						|| entry.getState().getEvaluation() >= beta)) {
					state.setEvaluation(entry.getState().getEvaluation());
					state.setMove(entry.getState().getMove());
					
					//logger.warn("Tipo nó: " + entry.getState().getType() + "T: " + type + " P-Search: " + (depth + masterDepth) + " P-NodeFound: " + entry.getState().getDepth() + " " + " scoretype: " + entry.getScoreType() + " evaluation <= beta: " + (entry.getState().getEvaluation() <= beta));
					return true;
				}
			}
		}
		
		return false;
	}

	@Override
	public void update(INode state) {

		Key k = new Key(state.getKey().getKey64(), state.getKey().getKey32());

		Entry entry = ttable.get(k);

		if (entry != null) {
			entry.setState(state);
			entry.setScoreType(state.getScoreType());
		}
		
	}

	@Override
	public void remove(INode state) {

		Key k = new Key(state.getKey().getKey64(), state.getKey().getKey32());

		ttable.remove(k);
		
	}
	
	public Integer getSize(){
		
		return ttable.size();
	}
	
	public void addAll(Map<Key, Entry> shadowTable){
		
		ttable.putAll(shadowTable);
		
	}
	
	public Map<Key, Entry> getTtable() {
		return ttable;
	}

	@Override
	public void setMasterDepth(int masterDepth) {
		this.masterDepth = masterDepth;
	}

	@Override
	public void clear() {
	}

	@Override
	public void get(INode state, int depth) {

		Key k = new Key(state.getKey().getKey64(), state.getKey().getKey32());

		Entry entry = ttable.get(k);
		
		if(entry != null && entry.getState().getDepth() >= (depth + masterDepth)){
			
			/*if(entry.getState().getChildren() != null) {
				state.setChildren(entry.getState().getChildren());
			}*/
			
			state.setMove(entry.getState().getMove());
			state.setKey(entry.getState().getKey());
			
		}
	
	}
}
