package ufu.facom.lia.main;

import java.rmi.Naming;
import java.rmi.RemoteException;

import ufu.facom.lia.interfaces.IState;
import ufu.facom.lia.remote.IMasterRemote;
import ufu.facom.lia.remote.ISlaveRemote;

public class MasterManager {

	public ISlaveRemote doCallSlave(String host) {

		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		try {

			String registryURL = "rmi://" + host + "/APHID";
			
			System.out.println(registryURL);

			ISlaveRemote slaveRemote = (ISlaveRemote) Naming.lookup(registryURL);

			return slaveRemote;

		} catch (Exception e) {

			System.out.println("Occurred an error getting the slave byt the host: " + host);
			e.printStackTrace();
		}

		return null;

	}

	public void registryMasterRemote(ISlaveRemote slaveRemote, IMasterRemote masterRemote) {

		try {

			slaveRemote.registryMasterRemote(masterRemote);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addStateToSlave(ISlaveRemote slaveRemote, IState state) {

		try {

			slaveRemote.registerEntry(state);

		} catch (RemoteException e) {

			e.printStackTrace();
		}
	}
}
