package socket_programming;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class TCP_SERVER {

    static void printProg(String prog) {
        String[] result = prog.split("  ");
        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i]);
        }
    }

    static void printProgToFile(String prog, String fileName) {
        try {
            String[] result = prog.split("  ");

            // Create a PrintWriter to write to the file
            PrintWriter writer = new PrintWriter(new FileWriter(fileName));

            // Write each element of the result array to the file
            for (int i = 0; i < result.length; i++) {
                writer.println(result[i]);
            }

            // Close the PrintWriter to flush and release resources
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) throws Exception {
        String sendProg;
        String receiveProg;
        try (ServerSocket welcomesocket = new ServerSocket(8889)) {
            while (true) {
                System.out.println("Server is online");
                Socket connectionSocket = welcomesocket.accept();
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

                // Read the received C program
                sendProg = inFromClient.readLine();
                System.out.println("Getting C Program Output:\n");

                printProg(sendProg);
                // Create a temporary C file
                String fileName = "temp.c";
                printProgToFile(sendProg, fileName);
                // Compile the temporary C file using Runtime
                try {
                    String compilerCommand = "C:\\Program Files (x86)\\Dev-Cpp\\MinGW64\\bin\\gcc";
                    String[] compileArgs = {compilerCommand, "-o", "temp.exe", fileName, "-lm", "-mconsole"};
                    // Add -mconsole flag to indicate a console application

                    ProcessBuilder compilerProcessBuilder = new ProcessBuilder(compileArgs);
                    compilerProcessBuilder.redirectErrorStream(true);
                    compilerProcessBuilder.inheritIO();

                    Process compilerProcess = compilerProcessBuilder.start();
                    int compilerExitCode = compilerProcess.waitFor();

                    // Read the output and error streams of the compilation process
                    InputStream inputStream = compilerProcess.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader compilerOutputReader = new BufferedReader(inputStreamReader);

                    StringBuilder compilerOutput = new StringBuilder();
                    String line;
                    while ((line = compilerOutputReader.readLine()) != null) {
                        compilerOutput.append(line).append("\n");
                    }

                    if (compilerExitCode == 0) {
                        receiveProg = "Compilation successful\n" + compilerOutput.toString();

                        // Run the compiled program
                        try {
                            Process runProcess = new ProcessBuilder("temp.exe").start();
                            int runExitCode = runProcess.waitFor();

                            // Read the standard output stream of the execution process
                            InputStream runInputStream = runProcess.getInputStream();
                            InputStreamReader runInputStreamReader = new InputStreamReader(runInputStream);
                            BufferedReader runOutputReader = new BufferedReader(runInputStreamReader);

                            StringBuilder runOutput = new StringBuilder();
                            String runLine;
                            while ((runLine = runOutputReader.readLine()) != null) {
                                runOutput.append(runLine).append("\n");
                            }
                            System.out.println(runOutput.toString());
                            // Append the execution output to the receiveProg message
                            receiveProg += "\nExecution output:\n" + runOutput.toString();
                           
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                            receiveProg += "\nExecution error: " + e.getMessage();
                        }
                    } else {
                        receiveProg = "Compilation failed\n" + compilerOutput.toString();
                    }

                    // Send the result back to the client (if not sent earlier in the 'if' block)
                    if (!receiveProg.isEmpty()) {
                        OutputStream outputStream = connectionSocket.getOutputStream();
                        
                        outputStream.write(receiveProg.getBytes("UTF-8"));
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                    receiveProg = "Compilation error: " + e.getMessage();
                }
                // Close the connection after sending the result
                connectionSocket.close();
            }
        }
    }
}