/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ztp.tictactoe;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Komputer
 */
public class SocketProxy implements SocketInterface{
    private static String serverName = "127.0.0.1";
    private int port = 1337;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private static boolean notHost = true;
    private static SocketProxy instance = null;

    public static synchronized SocketProxy getInstance() throws IOException {
        if (instance == null) {
            instance = new SocketProxy();
        }
        return instance;
    }

    private SocketProxy() throws IOException {
        if (notHost) {
            this.socket = new Socket(serverName, port);
        } else {
            ServerSocket server = new ServerSocket(port);
            socket = server.accept();
        }
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
    }

    public static void setIp(String ip) {
        serverName = ip;
    }

    public void writeLine(String str) {
        try {
            out.writeUTF(str);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String readLine() {
        String str = null;
        try {
            str = in.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    public void dispose() {
        try {
            this.socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void makeHost() {
        notHost = false;
    }

    public static void makeClient() {
        notHost = true;
    }
    
    public boolean isHost(){
        return !notHost;
    }
}
