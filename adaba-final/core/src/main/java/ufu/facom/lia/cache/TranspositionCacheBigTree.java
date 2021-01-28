package ufu.facom.lia.cache;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.jcs.JCS;
import org.apache.commons.jcs.access.CacheAccess;

import ufu.facom.lia.interfaces.INode;
import ufu.facom.lia.interfaces.cache.ICache;

public class TranspositionCacheBigTree implements ICache, Serializable {

	private static final long serialVersionUID = 1L;

	private CacheAccess<Key, Entry> cache;
	
	private int masterDepth;

	public TranspositionCacheBigTree(String region) {

		cache = JCS.getInstance(region);

	}

	@Override
	public void store(INode state, short scoreType) {

		Entry entry = new Entry();

		//String s = state.getName();

		//s = s.replace("S", "");

		entry.setState(state);
		entry.setScoreType(scoreType);
		//entry.setKey(state.getKey());

		Key k = state.getKey();

		cache.put(k, entry);
		
		//return entry;

	}

	/**
	 * 
	 * @param state
	 *            - estado procurado
	 * @param type
	 *            - pai do estado procurado é minimizador ou maximizador
	 * @param depth
	 *            - profundidade do estado procurado
	 * @return
	 */
	@Override
	public Boolean get(INode state, short type, int depth, float alpha, float beta) {

		/*Key k = new Key(state.getKey().getKey64(), state.getKey().getKey32());

		Entry entry = cache.get(k);

		if (entry != null && entry.getState().getType() == state.getType()) {

			state.setVisited(entry.getState().isVisited());
			//state.setChildren(entry.getState().getChildren());
			//state.setHistoric(entry.getState().getHistoric());
			//state.setLocation(entry.getState().getLocation());
			//state.setRepresentation(entry.getState().getRepresentation());
			//state.setStatus(entry.getState().getStatus());

			System.out
					.println("PEGOU NA TT: " + state.getName() + " d: " + state.getDepth() + " depthCurrent: " + depth);

			if (type == entry.getState().getType() && entry.getState().getParentKey32() != state.getParentKey32()) {

				if (type == Type.MAX.getValue()) {

					if ((entry.getState().getDepth() >= (depth + masterDepth)) && (entry.getScoreType() == ScoreType.HASH_EXACT.getValue()
							|| entry.getState().getEvaluation() <= alpha)) {
						state.setEvaluation(entry.getState().getEvaluation());
						return true;
					}

				} else if (type == Type.MIN.getValue()) {

					if ((entry.getState().getDepth() >= (depth + masterDepth)) && (entry.getScoreType() == ScoreType.HASH_EXACT.getValue()
							|| entry.getState().getEvaluation() >= beta)) {
						state.setEvaluation(entry.getState().getEvaluation());
						return true;
					}
				}
			}

			return false;

		}*/

		return false;

	}

	@Override
	public void update(INode state) {

		Key k = new Key(state.getKey().getKey64(), state.getKey().getKey32());

		Entry entry = cache.get(k);

		if (entry != null) {
			System.out.println("Evaluation before: " + cache.get(k).getState().getEvaluation());
			System.out.println("ATUALIZOU CACHEEEEEEE!!! " + state.getEvaluation());
			entry.setState(state);
			entry.setScoreType(state.getScoreType());
			System.out.println("Evaluation after: " + cache.get(k).getState().getEvaluation());
		}

	}

	@Override
	public void remove(INode state) {

	}

	@Override
	public void clear() {
		cache.clear();
	}

	@Override
	public void addAll(Map<Key, Entry> shadowTable) {
		// TODO Auto-generated method stub

	}

	//@Override
	public INode get(INode state) {

		Key k = new Key(state.getKey().getKey64(), state.getKey().getKey32());

		Entry entry = cache.get(k);

		if (entry != null && entry.getState().equals(state)) {
			return entry.getState();
		}
		
		return null;
	}
	
	public void setMasterDepth(int masterDepth){
		this.masterDepth = masterDepth;
	}

	@Override
	public Boolean getToHeuritic(INode state, short type, int depth, float alpha, float beta) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void get(INode state, int depth) {
		// TODO Auto-generated method stub
		//return null;
	}
}
