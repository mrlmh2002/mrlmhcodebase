package chat2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;

public class ServerThread2 extends Thread {
    static Vector<Socket> connections = null;
    BufferedReader is;
    PrintWriter os;
    char a;

    ServerThread2(Vector<Socket> vector, PrintWriter os,BufferedReader is) {
        connections = vector;
        this.os=os;
        this.is=is;
    }


    public void run() {
        //System.out.println(is.readLine());
        //PrintWriter pw = new PrintWriter(((Socket) e.nextElement()).getOutputStream());
        try {
            a= (char) is.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Ö´ÐÐServerThread2"+ChatServer.connections.size());
        switch (a){
            case 't'://ÈºÁÄ
                for (int i=0;i<ChatServer.connections.size();i++){
                    ServerThread serverThread=new ServerThread(ChatServer.connections.get(i),os,is);
                    //String str =is.readLine();
                    serverThread.start();
                }
                break;
            default:
                for (int i=0;i<ChatServer.connections.size();i++){
                    ServerThread3 serverThread3=new ServerThread3(ChatServer.connections.get(i),os,is,a);
                    //String str =is.readLine();
                    serverThread3.start();
                }
                break;

        }

    }
}
