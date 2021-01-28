package ufu.facom.lia.cache;

import java.util.Hashtable;
import java.util.Map;

import ufu.facom.lia.exceptions.TranspositionTableException;
import ufu.facom.lia.interfaces.INode;
import ufu.facom.lia.interfaces.cache.ICache;
import ufu.facom.lia.interfaces.cache.IHashKey;

public class TranspositionTableBigTree implements ICache {

	private Map<Key, Entry> ttable;
	
	private int masterDepth;

	@SuppressWarnings("unused")
	private IHashKey hashKey;

	public TranspositionTableBigTree(IHashKey hashKey) {

		this.hashKey = hashKey;

		ttable = new Hashtable<Key, Entry>();

	}

	@Override
	public void store(INode state, short scoreType) throws TranspositionTableException {

		Entry entry = new Entry();

		entry.setState(state);
		entry.setScoreType(scoreType);
		//entry.setKey(state.getKey());

		Key k = state.getKey();

		ttable.put(k, entry);
		
		//return entry;
		
	}

	@Override
	public Boolean get(INode state, short type, int depth, float alpha, float beta) {

		/*Key k = state.getKey();

		Entry entry = ttable.get(k);

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

		}
*/
		return false;
	}

	@Override
	public void update(INode state) {

		Key k = state.getKey();

		Entry entry = ttable.get(k);

		if (entry != null) {
			entry.setState(state);
			entry.setScoreType(state.getScoreType());
		}

	}

	@Override
	public void remove(INode state) {

		Key k = state.getKey();

		ttable.remove(k);

	}

	public Integer getSize() {

		return ttable.size();
	}

	public void addAll(Map<Key, Entry> shadowTable) {

		ttable.putAll(shadowTable);

	}

	public Map<Key, Entry> getTtable() {
		return ttable;
	}

	@Override
	public void clear() {
		//TODO:
	}

	//@Override
	public INode get(INode state) {

		Key k = state.getKey();

		Entry entry = ttable.get(k);

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
