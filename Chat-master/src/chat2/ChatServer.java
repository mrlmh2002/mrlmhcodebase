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
    private TextField inputChat= new TextField();//输入区
    private static TextArea showChat = new TextArea();//显示区

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

        addWindowListener(new WindowAdapter() {//响应关闭按钮

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
            //创建一个ServerSocket在端口4700监听客户请求
            serverSocket = new ServerSocket(4700);
            showChat.append("服务器已经启动，正在监听");
            showChat.append("\r\n");

        } catch (IOException e) {
            System.out.println("Could not listen on port:4700.");
            //出错，打印出错信息
            System.exit(-1); //退出
        }
        try {
            while (listening) { //循环监听
                //监听到客户请求，根据得到的Socket对象和客户计数创建服务线程，并启动之
                Socket cs = serverSocket.accept();

                addConnection(cs);
                Clientnum++;
                showChat.append("登录成功~~~");
                showChat.append("当前上线人数为：" + Clientnum);
                showChat.append("\r\n");

                PrintWriter os = new PrintWriter(cs.getOutputStream(), true);
                BufferedReader is = new BufferedReader(new InputStreamReader(cs.getInputStream()));
                os.print(Clientnum);
                os.flush();
                //new ServerThread(connections,os,is).start();
                new ServerThread2(connections, os, is).start();
                for (int i = 0; i < ChatServer.connections.size(); i++) {
                    PrintWriter os1 = new PrintWriter(ChatServer.connections.get(i).getOutputStream());
                    os1.println("客户" + (Clientnum - 1) + "上线了");
                    os1.flush();
                }

            }
        }catch (Exception e){
            Clientnum--;
            for (int i = 0; i < ChatServer.connections.size(); i++) {
                PrintWriter os1 = new PrintWriter(ChatServer.connections.get(i).getOutputStream());
                os1.println("客户"+"下线了");
                os1.flush();
            }
        }
        serverSocket.close(); //关闭ServerSocket
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

