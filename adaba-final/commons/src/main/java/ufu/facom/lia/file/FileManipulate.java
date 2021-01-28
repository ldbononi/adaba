package ufu.facom.lia.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

import ufu.facom.lia.board.Board;
import ufu.facom.lia.board.map.EFeatures;
import ufu.facom.lia.configs.SystemConfigs;
import ufu.facom.lia.exceptions.MlpException;
import ufu.facom.lia.exceptions.PlayerException;
import ufu.facom.lia.interfaces.Configuration;
import ufu.facom.lia.interfaces.ILayer;
import ufu.facom.lia.interfaces.ILayerSimple;
import ufu.facom.lia.mlp.Utils;
import ufu.facom.lia.structures.mlp.Layer;
import ufu.facom.lia.structures.mlp.LayerSimple;
import ufu.facom.lia.structures.mlp.Net;

/**
 * Manipulation of the file of the neural network and features
 * 
 * @author lidia
 *
 */
public class FileManipulate {

	/**
	 * Read the file of the neural network and creates a new object of type
	 * ufu.facom.lia.mlp.Net
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 * @throws MlpException
	 */
	public Net readFile(String path) throws IOException {

		File file = new File(path);

		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;

		Net net = null;
		Layer layer = null;
		LayerSimple layer2 = null;
		LayerSimple layer3 = null;

		try {

			if (file.exists()) {

				is = new FileInputStream(file);
				isr = new InputStreamReader(is);
				br = new BufferedReader(isr);

				String line = br.readLine();

				if (StringUtils.isNotBlank(line)) {
					net = new Net(line.trim());

					line = br.readLine();

					try {

						String[] params = line.split("[\\s\t]");

						for (String param : params) {
							if (StringUtils.isNotBlank(param)) {
								if (net.getNumInputs() == 0) {
									net.setNumInputs(Integer.parseInt(param));
								} else if (net.getNumNodesHidden() == 0) {
									net.setNumNodesHidden(Integer.parseInt(param));
								} else {
									net.setDirectLinks(param.equals("1") ? true : false);
								}
							}
						}

						params = null;

					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				if (net != null) {
					layer = new Layer(net.getNumInputs() + 1, net.getNumNodesHidden());

					net.setLayer1((ILayer) layer);
				}

				int lineNumber = 0;

				while (line != null) {

					line = br.readLine();

					if (StringUtils.isNotBlank(line)) {

						String[] weights = line.split("[\\s\t]");

						if (lineNumber > net.getNumInputs()) {

							layer2 = new LayerSimple(weights.length);
							net.setLayer2((ILayerSimple) layer2);

							for (int i = 0; i < weights.length; i++) {
								layer2.getWeights()[i] = new Float(weights[i]);
							}

							if (net.isDirectLinks()) {

								line = br.readLine();
								weights = line.split("[\\s\t]");

								layer3 = new LayerSimple(weights.length);
								net.setLayer3((ILayerSimple) layer3);

								for (int i = 0; i < weights.length; i++) {
									layer3.getWeights()[i] = new Float(weights[i]);
								}
							}

							break;

						} else {

							// System.out.println(line);

							layer.getWeights()[lineNumber] = new float[weights.length];

							for (int i = 0; i < weights.length; i++) {
								layer.getWeights()[lineNumber][i] = new Float(weights[i].trim());
							}
						}

						weights = null;
					}

					lineNumber++;
				}

			} else {
				throw new FileNotFoundException(path);
			}

		} finally {

			if (is != null) {
				is.close();
			}

			if (isr != null) {
				isr.close();
			}

			if (br != null) {
				br.close();
			}

		}

		return net;

	}

	/**
	 * Record in a file the information about a network
	 * 
	 * @param net
	 * @param pathOutput
	 * @throws IOException
	 */
	public void writeNetFile(Net net, String pathOutput, String nameFile) throws IOException {

		FileWriter fw = null;
		BufferedWriter bw = null;

		File f = new File(pathOutput);

		if (!f.exists()) {
			f.mkdir();
		}

		File fNet = new File(pathOutput + FileSystems.getDefault().getSeparator() + nameFile + Configuration.NET_EXTENSION);

		try {

			System.out.println(fNet.getAbsolutePath());

			fNet.createNewFile();

			fw = new FileWriter(fNet);
			bw = new BufferedWriter(fw);

			bw.write(net.getName());
			bw.newLine();
			bw.write(net.getNumInputs() + " " + net.getNumNodesHidden() + " " + (net.isDirectLinks() ? "1" : "0"));
			bw.newLine();

			String line = "";

			for (int i = 0; i < net.getLayer1().getWeights().length; i++) {

				line = "";

				for (int j = 0; j < net.getLayer1().getWeights()[i].length; j++) {

					float temp = net.getLayer1().getWeights()[i][j];

					//temp = temp.setScale(Configuration.BIG_DEC_SCALE, Configuration.ROUNDING_MODE);

					line += String.valueOf(temp) + " ";
				}

				bw.write(line);
				bw.newLine();
			}

			// Hidden Layer
			line = "";
			bw.newLine();

			for (int j = 0; j < net.getLayer2().getWeights().length; j++) {

				float temp = net.getLayer2().getWeights()[j];

				//temp = temp.setScale(Configuration.BIG_DEC_SCALE, Configuration.ROUNDING_MODE);

				line += String.valueOf(temp) + " ";

			}

			bw.write(line);
			bw.newLine();

			// Direct Link
			if (net.isDirectLinks()) {

				line = "";

				for (int j = 0; j < net.getLayer3().getWeights().length; j++) {

					float temp = net.getLayer3().getWeights()[j];

					//temp = temp.setScale(Configuration.BIG_DEC_SCALE, Configuration.ROUNDING_MODE);

					line += String.valueOf(temp) + " ";
				}

				bw.write(line);
				bw.newLine();
			}

			line = null;

			bw.close();
			fw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * não sei se essa função vai ser usada, a ideia é gerar os pesos em memória
	 * e chamar o método writeNetFile para escrita
	 * 
	 * @param net
	 * @param pathOutput
	 * @throws IOException
	 */
	public void writeNetRoundWeightsFile(Net net, String pathOutput) throws IOException {

		FileWriter fw = null;
		BufferedWriter bw = null;

		Random rand = null;

		int numInputs, numHidden;
		boolean directLink;
		float lowRange, highRange;
		String netName;

		File f = new File(pathOutput);

		if (!f.exists()) {
			f.mkdir();
		}

		if (net == null) {
			net = new Utils().getNetConfiguration(net);

		}
		numInputs = net.getNumInputs();
		numHidden = net.getNumNodesHidden();
		netName = net.getName();
		directLink = net.isDirectLinks();
		lowRange = net.getLowRange();
		highRange = net.getHighRange();
		rand = new Random(net.getRandomSeed());

		File fNet = new File(pathOutput + "/" + netName + Configuration.NET_EXTENSION);

		try {

			fNet.createNewFile();

			fw = new FileWriter(fNet);
			bw = new BufferedWriter(fw);

			bw.write(netName);
			bw.newLine();
			bw.write(numInputs + " " + numHidden + " " + (directLink ? "1" : "0"));
			bw.newLine();

			String line = "";

			for (int i = 0; i <= numInputs; i++) {

				line = "";

				for (int j = 0; j < numHidden; j++) {

					float rNumber = highRange - lowRange;

					Double a = rand.nextDouble();

					/*BigDecimal newNumber = new BigDecimal(String.valueOf(a)).setScale(Configuration.BIG_DEC_SCALE,
							Configuration.ROUNDING_MODE);*/
					
					float newNumber = a.floatValue();

					line += String.valueOf(lowRange + (rNumber * newNumber)) + " ";

				}

				bw.write(line);
				bw.newLine();
			}

			// Hidden Layer
			line = "";
			bw.newLine();

			for (int j = 0; j <= numHidden; j++) {

				float rNumber = highRange - lowRange;

				Double a = rand.nextDouble();

				float newNumber = a.floatValue();

				//line += lowRange.add(rNumber.multiply(newNumber)).toPlainString() + " ";
				
				line += lowRange + (rNumber * newNumber);

			}

			bw.write(line);
			bw.newLine();

			// Direct Link
			if (net.isDirectLinks()) {

				line = "";

				for (int j = 0; j <= numInputs; j++) {

					float rNumber = highRange - lowRange;

					Double a = rand.nextDouble();

					float newNumber = a.floatValue();

					//line += lowRange.add(rNumber.multiply(newNumber)).toPlainString() + " ";
					
					line += lowRange + (rNumber * newNumber);

				}

				bw.write(line);
				bw.newLine();
			}

			line = null;

			bw.close();
			fw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Read the features file with default informations of path defined in
	 * configs.properties file
	 * 
	 * @return
	 * @throws IOException
	 */
	public List<EFeatures> readFeaturesFile() throws IOException {
		String path = SystemConfigs.getInstance().getConfig("basePath");
		path += SystemConfigs.getInstance().getConfig("featureFile");

		return readFeaturesFile(path);
	}

	/**
	 * Read the file of features and obtain the feature's value (the bits
	 * quantity)
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public List<EFeatures> readFeaturesFile(String path) throws IOException {

		File file = new File(path);

		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;

		List<EFeatures> features = null;

		try {

			if (file.exists()) {

				is = new FileInputStream(file);
				isr = new InputStreamReader(is);
				br = new BufferedReader(isr);

				features = new ArrayList<EFeatures>();

				String line = null;

				while ((line = br.readLine()) != null) {

					if (StringUtils.isNotBlank(line)) {

						String feature = StringUtils.split(line, " ")[0].toLowerCase();
						for (EFeatures ftr : EFeatures.values()) {
							if (ftr.getName().toLowerCase().equals(feature)) {
								features.add(ftr);
								break;
							}
						}
					}

				}

				line = null;

			} else {
				throw new FileNotFoundException(path);
			}

		} finally {

			if (is != null) {
				is.close();
			}

			if (isr != null) {
				isr.close();
			}

			if (br != null) {
				br.close();
			}

		}

		return features;

	}

	public Board[] readBoardFile(String path) throws IOException, PlayerException {

		File file = new File(path);

		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;

		Board[] boards = null;
		
		int boardRead = 0;

		try {

			if (file.exists()) {

				is = new FileInputStream(file);
				isr = new InputStreamReader(is);
				br = new BufferedReader(isr);

				String line = br.readLine();

				int totalBoards = 0, index = 0;

				if (StringUtils.isNotBlank(line)) {
					totalBoards = Integer.parseInt(line);

					boards = new Board[totalBoards];
				}

				if (boards == null) {
					throw new PlayerException("The total of boards not was informed");
				}

				while ((line = br.readLine()) != null && boardRead != totalBoards) {

					if (StringUtils.isNotBlank(line)) {

						boards[index++] = new Board(line.split("[\\s\\t]"));
						boardRead++;
					}
				}

				line = null;

			} else {
				throw new FileNotFoundException(path);
			}

		} finally {

			if (is != null) {
				is.close();
			}

			if (isr != null) {
				isr.close();
			}

			if (br != null) {
				br.close();
			}

			file = null;

		}

		return boards;

	}

	@SuppressWarnings("resource")
	public void copyFile(File source, File destination) throws IOException {
		if (destination.exists())
			destination.delete();

		FileChannel sourceChannel = null;
		FileChannel destinationChannel = null;

		try {
			sourceChannel = new FileInputStream(source).getChannel();
			destinationChannel = new FileOutputStream(destination).getChannel();
			sourceChannel.transferTo(0, sourceChannel.size(), destinationChannel);
		} finally {
			if (sourceChannel != null && sourceChannel.isOpen())
				sourceChannel.close();
			if (destinationChannel != null && destinationChannel.isOpen())
				destinationChannel.close();
		}
	}

	public void copyFile(String origemPath, String destinationPath) throws IOException {

		File sourceFile = new File(origemPath);
		FileReader fis = new FileReader(sourceFile);
		BufferedReader bufferedReader = new BufferedReader(fis);
		StringBuilder buffer = new StringBuilder();
		String line = "";
		while ((line = bufferedReader.readLine()) != null) {
			buffer.append(line).append("\n");
		}

		fis.close();
		bufferedReader.close();

		File targetFile = new File(destinationPath);
		FileWriter writer = new FileWriter(targetFile);
		writer.write(buffer.toString());
		writer.flush();
		writer.close();
	}

	/**
	 * Record in a file the information about a network
	 * 
	 * @param net
	 * @param pathOutput
	 * @throws IOException
	 */
	public void writeBoardStateFile(String stateRepresentation, String path) throws IOException {

		FileWriter fw = null;
		BufferedWriter bw = null;

		File f = new File(path);

		try {

			fw = new FileWriter(f);
			bw = new BufferedWriter(fw);
			
			bw.write("1");
			bw.newLine();
			bw.newLine();

			bw.write(stateRepresentation + "\t-0.1 +0.1 4");
			bw.newLine();

			bw.close();
			fw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Long lastModification(String pathFile) {

		File f = new File(pathFile);

		try {
			if (!f.exists()) {
				throw new FileNotFoundException();

			}
			
			return f.lastModified();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return null;

	}
	
	public void writeOutputFile(String message, String fileNumber) throws IOException {

		FileWriter fw = null;
		BufferedWriter bw = null;

		String path = SystemConfigs.getInstance().getConfig("outputfile") + fileNumber + ".txt";

		File f = new File(path);

		try {

			fw = new FileWriter(f, true);
			bw = new BufferedWriter(fw);
			
			bw.append(message);
			bw.newLine();
			bw.close();
			fw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		FileManipulate fm = new FileManipulate();
		
		try {
			//fm.writeBoardStateFile("0 0 0 0 0 0 0 0 0 0 4 0 0 4 0 0 0 2 2 0 2 2 0 0 0 3 0 0 0 0 0 2");
			for(int i = 0; i < 10; i++){
				fm.writeOutputFile("teste " + i + "\t", "1");
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
