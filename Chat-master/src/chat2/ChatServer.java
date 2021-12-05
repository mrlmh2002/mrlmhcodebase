package chat2;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Vector;

public class ChatServer extends Frame  {
    static ServerSocket serverSocket=null;
    static Vector<Socket> connections=null;
    static int Clientnum=0;
    private TextField inputChat= new TextField();//������
    private static TextArea showChat = new TextArea();//��ʾ��

    private void init() throws IOException {
        //ServerSocket serverSocket = null;
        setLocation(666, 66);
        setSize(666, 666);
        setSize(66,66);

        setVisible(true);

        add(inputChat, BorderLayout.SOUTH);

        add(showChat, BorderLayout.NORTH);

        pack();

        //inputChat.addActionListener(this);

        addWindowListener(new WindowAdapter() {//��Ӧ�رհ�ť

            @Override

            public void windowClosing(WindowEvent e) {
                for (int i = 0; i < ChatServer.connections.size(); i++) {
                    PrintWriter os1 = null;
                    try {
                        os1 = new PrintWriter(ChatServer.connections.get(i).getOutputStream());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    os1.println("something wrong with server");
                    os1.flush();
                }

                System.exit(0);

            }

        });

        setVisible(true);
        boolean listening = true;
        try {
            //����һ��ServerSocket�ڶ˿�4700�����ͻ�����
            serverSocket = new ServerSocket(4700);
            showChat.append("�������Ѿ����������ڼ���");
            showChat.append("\r\n");

        } catch (IOException e) {
            System.out.println("Could not listen on port:4700.");
            //������ӡ������Ϣ
            System.exit(-1); //�˳�
        }
        try {
            while (listening) { //ѭ������
                //�������ͻ����󣬸��ݵõ���Socket����Ϳͻ��������������̣߳�������֮
                Socket cs = serverSocket.accept();

                addConnection(cs);
                Clientnum++;
                showChat.append("��¼�ɹ�~~~");
                showChat.append("��ǰ��������Ϊ��" + Clientnum);
                showChat.append("\r\n");

                PrintWriter os = new PrintWriter(cs.getOutputStream(), true);
                BufferedReader is = new BufferedReader(new InputStreamReader(cs.getInputStream()));
                os.print(Clientnum);
                os.flush();
                //new ServerThread(connections,os,is).start();
                new ServerThread2(connections, os, is).start();
                for (int i = 0; i < ChatServer.connections.size(); i++) {
                    PrintWriter os1 = new PrintWriter(ChatServer.connections.get(i).getOutputStream());
                    os1.println("�ͻ�" + (Clientnum - 1) + "������");
                    os1.flush();
                }

            }
        }catch (Exception e){
            Clientnum--;
            for (int i = 0; i < ChatServer.connections.size(); i++) {
                PrintWriter os1 = new PrintWriter(ChatServer.connections.get(i).getOutputStream());
                os1.println("�ͻ�"+"������");
                os1.flush();
            }
        }
        serverSocket.close(); //�ر�ServerSocket
    }


    public static void addConnection(Socket s) {
        if (connections == null) {
            connections = new Vector<Socket>();
        }
        connections.addElement(s);

    }

    public static int getClientnum(){
        return Clientnum;
    }

    public static void main(String args[]) throws IOException {
        ChatServer server = new ChatServer();
        server.init();
    }
}

