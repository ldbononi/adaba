package ufu.facom.lia.services;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/slave")
public class CallSlave {
	
	@Path("/call")
	@GET
	@Produces({ MediaType.APPLICATION_JSON})
	public Response call(@QueryParam("port") Integer port, @QueryParam("filenumber") Integer fileNumber, @QueryParam("iterations") Integer numIterations) throws InterruptedException {
		
		Response rs = new Response();
		
		rs.setMessage("Deu certo!");
		
		System.out.println("Porta: " + port + " Arquivo: " + fileNumber + " Iteração: " + numIterations);
		
		//SlaveAlphaBeta sab = SlaveAlphaBeta.getInstance(port);
		
		//sab.setNumFile(fileNumber);
		//sab.setWaitIterations(numIterations);
		//sab.startSlave();
		
		//String command = "java -jar -Djava.rmi.server.hostname=127.0.0.1 -Djava.security.policy=file:D:/Doutorado/execution-tests/rmi-policy/security.policy -Djava.rmi.server.codebase=file:D:/Doutorado/execution-tests/ D:\\Doutorado\\execution-tests\\aphid-v2\\aphid-slave.jar " + port + " " + fileNumber + " " + numIterations ;
		
		String command = "java -server -jar -Djava.rmi.server.hostname=127.0.0.1 -Djava.security.policy=file:D:/Doutorado/execution-tests/rmi-policy/security.policy -Djava.rmi.server.codebase=file:D:/Doutorado/execution-tests/ D:/Doutorado/execution-tests/aphid-v2/commons.jar 10 ";
		
		try {
			Process p = Runtime.getRuntime().exec(command);
			
			//System.out.println(p.pid());
			
			while(p.isAlive()) {
				try {
					synchronized (this) {
						wait(5000);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			p.destroy();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*SlaveAlphaBeta sab = SlaveAlphaBeta.getInstance(1099);
		
		sab.setNumFile(1);
		sab.setWaitIterations(1099);
		sab.startSlave();
		*/
		return rs;
	}

}
