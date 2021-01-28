package ufu.facom.lia.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import ufu.facom.lia.interfaces.IState;
import ufu.facom.lia.search.MasterAlphaBeta;

public class MasterRemote extends UnicastRemoteObject implements IMasterRemote{

	private static final long serialVersionUID = 1L;
	
	private MasterAlphaBeta masterAlphaBeta;
	
	public MasterRemote() throws RemoteException {
	}

	/*@Override
	public void receiveState(IState state) throws RemoteException {
		
		if(state!=null){
			//System.out.println("MasterRemote received a state.");
			//System.out.println("State name: " + state.getName() + "Result: " + state.getEvaluation().toPlainString());
			
			if(masterAlphaBeta != null){
				masterAlphaBeta.handleMessageReceived(state);
			}else{
				System.out.println("Ixi...");
			}
			
		}else{
			System.out.println("Occurred a problem receiving the state from SlaveRemote.");
		}
	}*/

	public MasterAlphaBeta getMasterAlphaBeta() {
		return masterAlphaBeta;
	}

	public void setMasterAlphaBeta(MasterAlphaBeta masterAlphaBeta) {
		this.masterAlphaBeta = masterAlphaBeta;
	}
	
	@Override
	public boolean unblockSlave(String host){
		return this.masterAlphaBeta.unblockSlave(host);
	}

	@Override
	public synchronized void receiveStates(List<IState> states) throws RemoteException {
		
		if(!states.isEmpty()){
			if(masterAlphaBeta != null){
				masterAlphaBeta.handleMessageReceived(states);
			}else{
				System.out.println("Ixiiiiiiiii...");
			}
		}else{
			System.out.println("Occured a problem receiving the states from SlaveRemote.");
		}
	}

	@Override
	public void confirmMsgSlaveFinalized(String host) throws RemoteException {
		masterAlphaBeta.checkFinalizedSlave(host);
	}
	
	@Override
	public void confirmMsgSlaveRestarted(String host) throws RemoteException {
		masterAlphaBeta.checkRestartSlave(host);
	}

	@Override
	public void slaveWaitingTask(String host) throws RemoteException {
		masterAlphaBeta.slaveNotification(host);
	}
}
