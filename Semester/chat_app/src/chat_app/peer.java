package chat_app;



import java.io.*;

import java.net.*;

import java.util.HashMap;

import java.util.Map;

import java.util.Scanner;



public class peer {

	static Map<String, peer_info> clientInfoMap = new HashMap<>();

    static Map<String, String> ipClientMap = new HashMap<>();

    public static void main(String[] args) {

        final int port = 8880;

        

        clientInfoMap.put("me", new peer_info("172.16.75.92", 8880));

        clientInfoMap.put("abhik", new peer_info("172.16.75.118", 8880));

        clientInfoMap.put("tirto", new peer_info("172.16.75.116", 8880));
        
        clientInfoMap.put("deep", new peer_info("192.168.0.100", 8880));
        

        ipClientMap.put("/172.16.75.92", "self");

        ipClientMap.put("/172.16.75.118", "abhik");

        ipClientMap.put("/172.16.75.116", "tirto");

        ipClientMap.put("/172.16.75.91", "masum");
        
        ipClientMap.put("192.168.0.100", "deep");

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("Peer Chat is running and waiting for connections on port " + port);



            // Accept incoming connections in a separate thread

            new Thread(() -> {

                while (true) {

                    try {

                        Socket clientSocket = serverSocket.accept();

                        new Thread(() -> handleClient(clientSocket)).start();

                    } catch (IOException e) {

                        e.printStackTrace();

                    }

                }

            }).start();



            // Allow the user to send messages

            Scanner scanner = new Scanner(System.in);

            while (true) {

                System.out.print("Enter message (or 'exit' to quit): ");

                String message = scanner.nextLine();



                if ("exit".equalsIgnoreCase(message)) {

                    break;

                }

                

//                System.out.print("Enter peer's IP address: ");

//                String peerIP = scanner.nextLine();

                for (Map.Entry<String, peer_info> entry : clientInfoMap.entrySet()) {

                    String clientName = entry.getKey();

                    peer_info clientInfo = entry.getValue();



                    System.out.println("Client Name: " + clientName);

//                    System.out.println("IP Address: " + );

//                    System.out.println("Port Number: " + );

                    sendMessage(clientInfo.getIpAddress(), clientInfo.getPort(), message);

                    System.out.println();

                }

                // Send the message to the specified peer



            }



        } catch (IOException e) {

            e.printStackTrace();

        }

    }



    private static void handleClient(Socket clientSocket) {

    	String clientInfo=null;

        try (

                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))

        ) {

            String peerMessage;

            while ((peerMessage = reader.readLine()) != null) {

                String ip = clientSocket.getInetAddress().toString();

            	String keyToCheck = ip;

            	System.out.println(ip);

                

                if (ipClientMap.containsKey(keyToCheck)) {

                    //System.out.println(keyToCheck + " exists in the HashMap.");

                    // You can also retrieve the corresponding value

                    clientInfo = ipClientMap.get(keyToCheck);

                    System.out.println("Received from " + clientInfo + " : " + peerMessage);

                } else {

                   // System.out.println(keyToCheck + " does not exist in the HashMap.");

                }

            }



        } catch (IOException e) {

            e.printStackTrace();

        }

    }



    private static void sendMessage(String peerIP, int peerPort, String message) {

        try (

                Socket socket = new Socket(peerIP, peerPort);

                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)

        ) {

            // Send the message to the specified peer

            writer.println(message);



        } catch (IOException e) {

            e.printStackTrace();

        }

    }

}