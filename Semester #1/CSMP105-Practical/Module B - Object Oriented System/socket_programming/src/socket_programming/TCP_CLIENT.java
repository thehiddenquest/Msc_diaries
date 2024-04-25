package socket_programming;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class TCP_CLIENT {
	
	static String readCProg () throws IOException {
		String result = "";
		BufferedReader cprog;
		try {
			cprog = new BufferedReader(new FileReader("sum.c"));
			while(cprog.ready()) {
				result += cprog.readLine() + "  ";
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	public static void main(String args[]) throws Exception{
		String sendProg = readCProg();
		String receiveProg;
		Socket clientSocket = new Socket("192.168.0.105",8889);
		OutputStream outToServer = clientSocket.getOutputStream();
		outToServer.write((sendProg+"\n").getBytes());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		StringBuilder resultBuilder = new StringBuilder();
        String line;
        while ((line = inFromServer.readLine()) != null) {
            resultBuilder.append(line).append("\n");  // Append each line with a newline character
        }

        String result = resultBuilder.toString();
        System.out.println(result);
//		receiveProg = inFromServer.readLine();
//		System.out.println(receiveProg);
//		String[] output = receiveProg.split("\n");  // Split based on newline characters
//
//		for (int i = 0; i < output.length; i++) {
//		    System.out.println(output[i]);
//		}
		clientSocket.close();
		
	}
}
