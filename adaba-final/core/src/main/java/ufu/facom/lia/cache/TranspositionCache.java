package ufu.facom.lia.cache;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.jcs.JCS;
import org.apache.commons.jcs.access.CacheAccess;

import ufu.facom.lia.interfaces.INode;
import ufu.facom.lia.interfaces.cache.ICache;
import ufu.facom.lia.interfaces.cache.IHashKey;

public class TranspositionCache implements ICache, Serializable {

	private static final long serialVersionUID = 1L;

	private CacheAccess<Key, Entry> cache;

	private IHashKey hashKey;
	
	private int masterDepth;

	public TranspositionCache(IHashKey hashKey, String region) {

		cache = JCS.getInstance(region);

		this.hashKey = hashKey;

	}

	@Override
	public void store(INode state, short scoreType) {
		
		if(state.getEvaluation() != 0f){
			Entry entry = new Entry();

			entry.setState(state);
			entry.setScoreType(scoreType);
			//entry.setKey32(hashKey.getKey32(state));
			//entry.setKey64(hashKey.getKey64(state));
			//entry.setKey(state.getKey());

			Key k = state.getKey();

			cache.put(k, entry);
			
			//return entry;
		}
		
		//return null;
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
	public synchronized Boolean get(INode state, short type, int depth, float alpha, float beta) {

		long key64 = hashKey.getKey64(state);
		int key32 = hashKey.getKey32(state);

		Key k = new Key(key64, key32);

		Entry entry = cache.get(k);

		/*if (entry != null && entry.getState().equals(state) && entry.getState().getType() == state.getType() && entry.getState().getEvaluation() != 0f) {

			state.setVisited(entry.getState().isVisited());
			//state.setChildren(entry.getState().getChildren());
			//state.setHistoric(entry.getState().getHistoric());
			//state.setLocation(entry.getState().getLocation());
			//state.setRepresentation(entry.getState().getRepresentation());
			//state.setStatus(entry.getState().getStatus());
			//state.setEvaluation(0f);

			//System.out
				//	.print("\nPEGOU NA TT: " + state.getName() + " d: " + state.getDepth() + " depthCurrent: " + depth );

			// Verifica se os pais são diferentes, por conta do aprofundamento
			// iterativo, senão sempre vai retornar a avaliação mais rasa e não
			// aprofunda na busca
			if (type == entry.getState().getType() && entry.getState().getParentKey32() != state.getParentKey32()) {
				
				if(entry.getState().getType() == Type.LEAF.getValue()){
					state.setEvaluation(entry.getState().getEvaluation());
					return true;
				}

				if (type == Type.MAX.getValue()) {

					if ((entry.getState().getDepth() >= (depth + masterDepth)) && (entry.getScoreType() == ScoreType.HASH_EXACT.getValue()
							|| entry.getState().getEvaluation() <= alpha )) {
						state.setEvaluation(entry.getState().getEvaluation());
						
						//System.out.print(" com avaliação: " + state.getEvaluation());
						
						return true;
					}

				} else if (type == Type.MIN.getValue()) {

					if ((entry.getState().getDepth() >= (depth + masterDepth)) && (entry.getScoreType() == ScoreType.HASH_EXACT.getValue()
							|| entry.getState().getEvaluation() >= beta)) {
						state.setEvaluation(entry.getState().getEvaluation());
						
						//System.out.print(" com avaliação: " + state.getEvaluation());
						
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

		long key64 = hashKey.getKey64(state);
		int key32 = hashKey.getKey32(state);

		Key k = new Key(key64, key32);

		Entry entry = cache.get(k);

		if (entry != null) {
			
			System.out.println("atualizando: " + state.getKey().getKey32() + " " + entry.getState().getDepth());
			
			entry.setState(state);
			entry.setScoreType(state.getScoreType());
			
			System.out.println("##atualizado: " + state.getKey().getKey32() + " " + entry.getState().getDepth());
		}
	}

	@Override
	public void remove(INode state) {

		long key64 = hashKey.getKey64(state);
		int key32 = hashKey.getKey32(state);

		Key k = new Key(key64, key32);

		cache.remove(k);
	}

	@Override
	public void addAll(Map<Key, Entry> shadowTable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() {
		this.cache.clear();
	}

	@Override
	public void get(INode state, int depth) {
		
		long key64 = hashKey.getKey64(state);
		int key32 = hashKey.getKey32(state);

		Key k = new Key(key64, key32);

		Entry entry = cache.get(k);
		
		if(entry != null && entry.getState().equals(state)){
			//return entry.getState();
		}
		
		//return null;
	}

	@Override
	public void setMasterDepth(int masterDepth) {
		this.masterDepth = masterDepth;
	}

	@Override
	public Boolean getToHeuritic(INode state, short type, int depth, float alpha, float beta) {
		// TODO Auto-generated method stub
		return null;
	}
}
