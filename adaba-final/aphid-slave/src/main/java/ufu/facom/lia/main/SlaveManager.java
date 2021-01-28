package ufu.facom.lia.main;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import ufu.facom.lia.configs.Configs;
import ufu.facom.lia.remote.ISlaveRemote;
import ufu.facom.lia.remote.SlaveRemote;

public class SlaveManager {

	private static Boolean startedRegistry = false;

	private Registry registry;

	public void manageRemoteConnection() {

		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		try {

			Registry registry = LocateRegistry.getRegistry();

			// Registry registry = startRegistry(1099);

			ISlaveRemote slaveRemote = new SlaveRemote();

			registry.rebind("APHID", slaveRemote);

			System.out.println("Server is ready!");

		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	public ISlaveRemote connectionRemoteSlave(String serverName, int port) {

		System.out.println(port);

		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		try {

			if (!startedRegistry) {

				registry = startRegistry(port);

				startedRegistry = true;
			}

			ISlaveRemote slaveRemote = new SlaveRemote();

			// registry.rebind(serverName, slaveRemote);

			registry.bind(serverName, slaveRemote);

			System.out.println("Server " + serverName + " is ready!");

			return slaveRemote;

		} catch (RemoteException | AlreadyBoundException e) {
			e.printStackTrace();
		}

		return null;

	}

	public ISlaveRemote reconnectionRemoteSlave(String serverName, int port) {

		System.out.println(port);

		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		try {

			registry = LocateRegistry.getRegistry();

			ISlaveRemote slaveRemote = new SlaveRemote();

			registry.rebind(serverName, slaveRemote);

			// registry.bind(serverName, slaveRemote);

			System.out.println("Server " + serverName + " is ready!");

			return slaveRemote;

		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return null;

	}

	private Registry startRegistry(int port) throws RemoteException {

		try {
			return LocateRegistry.createRegistry(port);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return null;
	}

	public ISlaveRemote doCallNeighborSlave(String host) {

		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		try {

			String registryURL = "rmi://" + host + "/" + Configs.NEIGHBOR_SERVER_NAME + host.replace(".", "");

			System.out.println("URL: " + registryURL);

			ISlaveRemote slaveRemote = (ISlaveRemote) Naming.lookup(registryURL);

			return slaveRemote;

		} catch (Exception e) {

			System.out.println("Occurred an error getting the slave by the host: " + host);
			e.printStackTrace();
		}

		return null;

	}

	public void unbind(String nome, ISlaveRemote remote) {

		try {
			registry.unbind(nome);
			UnicastRemoteObject.unexportObject(remote, true);
		} catch (AccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		SlaveManager sm = new SlaveManager();

		sm.manageRemoteConnection();

	}
}
