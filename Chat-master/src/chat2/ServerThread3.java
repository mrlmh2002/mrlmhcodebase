package chat2;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
//���������ķ����߳�

public class ServerThread3 extends Thread {
    Socket connections = null;
    PrintWriter os;
    BufferedReader is;
    char a;

    ServerThread3(Socket socket,PrintWriter os,BufferedReader is,char a) {
        this.connections=socket;
        this.os=os;
        this.is=is;
        this.a=a;
    }


    public void sendn() throws IOException{
        for(int i=0;i<ChatServer.connections.size();i++) {
            PrintWriter os1 = new PrintWriter(ChatServer.connections.get(a-48).getOutputStream());
            os1.println("Ŀǰ��������Ϊ"+ChatServer.Clientnum );
            os1.flush();
            //os.println(str);
            //os.flush();}
        }

    }

    public void sendtoone(String str)throws IOException{
        System.out.println(a);
        PrintWriter os1 = new PrintWriter(ChatServer.connections.get(a-48).getOutputStream());
        os1.println(str);
        os1.flush();
        //os.println(str);
        //os.flush();}
    }

    public void run() {
        while (true) {
            try {
                //System.out.println("ִ��ServerThread");
                //System.out.println(is.readLine());
                String line = is.readLine();
                System.out.println(line);
                //sendn();
                sendtoone(line);
                //os.println(is.readLine());
                //os.flush();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
