package app.rm.replica.mudra;

import java.io.*;

import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import app.Util;
import app.rm.replica.mudra.ServerImpl;
public class ConnectionListener implements Runnable {
    public ServerImpl server;
    DatagramSocket aSocket;
    boolean killed = false;

    public ConnectionListener(ServerImpl server) {
        this.server = server;
    }

    @Override
    public void run() {
        
        try {
            // create a DatagramSocket to receive request and send response
            aSocket = new DatagramSocket(server.currentServer.port);

            // continuously listen to receive data
            while (!killed) {
                // create a blank 1000 bytes array to wrap data
                byte[] buffer = new byte[1000];

                DatagramPacket request = new DatagramPacket(buffer, buffer.length); // make "blank" packet object by encapsulating blank byte-array into it
                aSocket.receive(request);   // wait while we receive data in the "blank" packet

                new RequestExecutor(server, aSocket, request).start();
            }
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
            server.writeToLogFile("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
            server.writeToLogFile("IO: " + e.getMessage());
        } finally {
            if (aSocket != null)
                aSocket.close();
        }
    }

	public void stop() {
        killed = true;
        if (aSocket != null) {
            aSocket.close();
            aSocket = null;
        }
    }
}

class RequestExecutor extends Thread {
    private final ServerImpl server;
    private final DatagramSocket aSocket;
    private final DatagramPacket request;

    public RequestExecutor(ServerImpl server, DatagramSocket aSocket, DatagramPacket request) {
        this.server = server;
        this.aSocket = aSocket;
        this.request = request;
    }

    @Override
    public void run() {
        try {
            String data = new String(request.getData()).trim();  // convert bytes into String and trim the string
            byte[] requestData= request.getData();
            // data must be in the form of [XXXXX : XXXXX : ....]
            // check for data
            String[] req = data.split("-=");
            DatagramPacket reply;
            String response;
            InetAddress host = InetAddress.getByName(Util.FRONT_END_HOST);
            int port = Util.FRONT_END_PORT;
            boolean isRedirect = false;
            try {
                int seq = Integer.valueOf(req[0]);
                port = Integer.valueOf(req[1]);
            } catch (NumberFormatException e) {
                isRedirect = true;
                port = request.getPort();
                host = request.getAddress();
            }

            if (!isRedirect) {
                server.handleRequest(req, host, port);
            } else {
                server.processRequest(req, host, port, false);
            }
//			switch (req[1]) {
//                case "create" :
//                	// create response message
//                    response = server.getSize(req[0]);
//                    reply = new DatagramPacket(response.getBytes(), response.length(), request.getAddress(), request.getPort()); // encapsulate response in a DatagraPacket object
//                    aSocket.send(reply); // send the response DatagramPacket object to the requester again
//                	break;
//
//                case "delete" :
//                	// create response message
//                    response = server.getSize(req[0]);
//                    reply = new DatagramPacket(response.getBytes(), response.length(), request.getAddress(), request.getPort()); // encapsulate response in a DatagraPacket object
//                    aSocket.send(reply); // send the response DatagramPacket object to the requester again
//                	break;
//
//                case "book" :
//                	// create response message
//                    response = server.getSize(req[0]);
//                    reply = new DatagramPacket(response.getBytes(), response.length(), request.getAddress(), request.getPort()); // encapsulate response in a DatagraPacket object
//                    aSocket.send(reply); // send the response DatagramPacket object to the requester again
//                	break;
//
//                case "getTimeSlots":
//                    // create response message
//                    response = server.getSize(req[0]);
//                    reply = new DatagramPacket(response.getBytes(), response.length(), request.getAddress(), request.getPort()); // encapsulate response in a DatagraPacket object
//                    aSocket.send(reply); // send the response DatagramPacket object to the requester again
//                    break;
//
//                case "cancelRoom":
//                	// create response message
//                    response = server.getSize(req[0]);
//                    reply = new DatagramPacket(response.getBytes(), response.length(), request.getAddress(), request.getPort()); // encapsulate response in a DatagraPacket object
//                    aSocket.send(reply); // send the response DatagramPacket object to the requester again
//                    break;
//
//                case "changeBooking":
//                    String[] record = req[2].split("-=");
//				String str = null;
//				// transfer records
//				String newtimeSlot = null;
//				String newroomNo = null;
//				String date =null;
//				String status = server.changeBooking(req[0], record[0].substring(0,3), newroomNo, date, newtimeSlot, str);
//                    // check status after transferring record
//                    if (status.equals(true))
//                        response = "success";
//                    else
//                        response = "failed";
//                    reply = new DatagramPacket(response.getBytes(), response.length(), request.getAddress(), request.getPort()); // encapsulate response in a DatagraPacket object
//                    aSocket.send(reply); // send the response DatagramPacket object to the requester again
//                    break;
//
//                default:
//                    // wrong request sent by another server
//                    System.out.println("\t...Wrong Request : " + data);
//                    server.writeToLogFile("Wrong request by " + data);
//                    response = "Invalid Request";
//                    reply = new DatagramPacket(response.getBytes(), response.length(), request.getAddress(), request.getPort()); // encapsulate response in a DatagraPacket object
//                    aSocket.send(reply); // send the response DatagramPacket object to the requester again
//                    break;
//            }
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
            server.writeToLogFile("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
            server.writeToLogFile("IO: " + e.getMessage());
        }
    }
}
