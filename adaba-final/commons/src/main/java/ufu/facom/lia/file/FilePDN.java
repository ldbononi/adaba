/**
 * CLASSE PARA TRATAR INICIALIZAÇÃO E ESCRITA EM ARQUIVOS PDN
 * @author Matheus
 */
package ufu.facom.lia.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import ufu.facom.lia.configs.SystemConfigs;
import ufu.facom.lia.interfaces.GameState;

public class FilePDN {

	private String name;
	
	/**
	 * Construtor do arquivo PDN => Grava o nome do arquivo para facilitar uso
	 * @param name
	 */
	public FilePDN(String name) {
		
		this.name = name;
		
		getFilePDN();
		
	}
	
	/**
	 * Construtor que cria o arquivo PDN e já o inicializa
	 * @param name
	 * @param initialize
	 */
	public FilePDN(String name, boolean initialize) {
		
		this.name = name;
		
		getFilePDN();
		
		if(initialize){
			InitGamePDN();
		}
		
	}
	
	/**
	 * Cria um arquivo PDN
	 */
	public File getFilePDN() {
		
		File f = new File(SystemConfigs.getInstance().getConfig("pdnFilePath") + this.name + ".PDN");
		
		if(!f.exists()){
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return f;
		
	}
	
	/**
	 * Inicializa o arquivo com os parâmetros iniciais: Event, Date, Black, White, Result 
	 */
	public void InitGamePDN() {
		
		FileWriter fw = null;
		PrintWriter pw = null;
				
		//Data atual
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		String currentDate = formatter.format(new Date());
		
		try {
			
			fw = new FileWriter(getFilePDN(), true);
		
			pw = new PrintWriter(fw);
	        
	        pw.printf("[Event \"Game Test\"]\n");
	        pw.printf("[Date \"" + currentDate + "\"]\n");
	        pw.printf("[Black \"bAPHID\"]\n");
	        pw.printf("[White \"wAPHID\"]\n");
	        pw.printf("[Result \"unknown\"]\n");
	        
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(fw != null){
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if(pw != null){
				pw.close();
			}
		}
		
	}
	
	/**
	 * Grava no arquivo a rodada de movimento 
	 * @param count
	 */
	public void writeRound(Integer count) {
		
		FileWriter fw = null;
		PrintWriter pw = null;
		
		try {
			
			fw = new FileWriter(getFilePDN(), true);
		
			pw = new PrintWriter(fw);
			
			pw.printf(count + ". ");
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(fw != null){
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if(pw != null){
				pw.close();
			}
		}
		
	}
	
	/**
	 * Grava no arquivo o movimento realizado por algum player 
	 * @param from
	 * @param to
	 */
	public void writeMove(int from, int to) {
		
		FileWriter fw = null;
		
		PrintWriter pw = null;
		
		try {
			
			fw = new FileWriter(getFilePDN(), true);
		
			pw = new PrintWriter(fw);
			
			if((Math.abs(from - to) > 5) || (Math.abs(from - to) == 1 ||
					(Math.abs(from - to) == 2) || (Math.abs(from - to) == 3))) 
				pw.printf(mapAPHID_CB(from) + "x" + mapAPHID_CB(to) + " ");
			else
				pw.printf(mapAPHID_CB(from) + "-" + mapAPHID_CB(to) + " ");
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		} finally {
			
			if(fw != null){
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if(pw != null){
				pw.close();
			}
		}
		
		
	}
	
	/**
	 * Grava no arquivo o resultado da partida (1-0, 0-1, 1/2-1/2) 
	 * @param result
	 */
	public void writeResult(int result) {
		
		FileWriter fw = null;
		PrintWriter pw = null;
		
		try {
			
			fw = new FileWriter(getFilePDN(), true);
		
			pw = new PrintWriter(fw);
			
			if(result == GameState.BLACK_WON)
				pw.println("1-0 \n");
			else if(result == GameState.RED_WON)
				pw.println("0-1 \n");
			else
				pw.println("1/2-1/2 \n");
			
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}finally{
			if(fw != null){
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if(pw != null){
				pw.close();
			}
		}
		
	}
	
	/**
	 * Método de mapeamento de posição do tabuleiro Aphid para o tabuleiro da CB 
	 * @param pos
	 * @return
	 */
	public int mapAPHID_CB(int pos) {
		
		int to=0;
		
		int aphid[] = {1,2,3,4,5,6,7,8,10,11,12,13,14,15,16,17,19,20,21,22,23,24,25,26,
				28,29,30,31,32,33,34,35};
		
		int cb[] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,
				26,27,28,29,30,31,32};
		
		for(int i=0; i<32; i++) {
			
			if(aphid[i] == pos) {
				
				to = i;
				break;
				
			}
			
		}
		
		return cb[to];
		
	}
	
}