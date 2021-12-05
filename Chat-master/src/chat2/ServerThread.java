package chat2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
//������Ⱥ�ķ����߳�
public class ServerThread extends Thread {
    Socket connections = null;
    PrintWriter os;
    BufferedReader is;

    ServerThread(Socket socket,PrintWriter os,BufferedReader is) {
        this.connections=socket;
        this.os=os;
        this.is=is;
    }

    public void send(String str) throws IOException {
        for(int i=0;i<ChatServer.connections.size();i++) {
            PrintWriter os1=new PrintWriter(ChatServer.connections.get(i).getOutputStream());
                os1.println(str);
                os1.flush();
               
        }

    }

    public void sendn() throws IOException{
        //for(int i=0;i<ChatServer.connections.size();i++) {
           //PrintWriter os1 = new PrintWriter(ChatServer.connections.get(i).getOutputStream());
            os.println("Ŀǰ��������Ϊ"+ChatServer.Clientnum );
            os.flush();
            //os.println(str);
            //os.flush();}
        //}

    }


    public void run() {
        while (true) {
            try {
                //System.out.println("ִ��ServerThread");
                //System.out.println(is.readLine());
                String line = is.readLine();
                if(line.equals("sendfile"))
                {
                	send(line);
                	if(line.equals("end")) {
                		break;
                	}
                }
                else {
                    send(line);
                }
                    //os.println(is.readLine());
                    //os.flush();
            } catch (IOException e1) {
                try {

                    os.println(is.read()+"������");
                    os.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

