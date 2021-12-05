package chat2;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class Client extends Frame implements  ActionListener {
    Socket socket=null; //保存与本线程相关的Socket对象,发送消息线程

    private BufferedReader is = null;
    static int flag=0;

    private PrintWriter os= null;
    private TextField inputChat= new TextField();//输入区
    private static TextArea showChat = new TextArea();//显示区
    private MenuItem OPEN=new MenuItem("打开文件");//菜单构造



    Client(Socket socket) throws IOException {

        this.socket=socket;
        this.is=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.os=new PrintWriter(socket.getOutputStream());
       // ClientThread2 clientThread2=new ClientThread2(socket);
       // clientThread2.start();
        setLocation(999, 99);
        setSize(999, 99);

        Menu file=new Menu("File");

             file.add(OPEN);
             OPEN.setEnabled(true);
             OPEN.addActionListener(this);

            MenuBar bar=new MenuBar();

             bar.add(file);

             setMenuBar(bar);

             setSize(66,66);

             setVisible(true);

        add(inputChat, BorderLayout.SOUTH);

        add(showChat, BorderLayout.NORTH);

        pack();

        inputChat.addActionListener(this);

        addWindowListener(new WindowAdapter() {//响应关闭按钮

            @Override

            public void windowClosing(WindowEvent e) {

           os.println(ClientThread2.getNumber()+"get off");
           os.flush();

                System.exit(0);

            }

        });

        setVisible(true);


    }
    
   
    
    public void actionPerformed(ActionEvent e) {
        if(flag==0){
        char x=inputChat.getText().charAt(0);
            inputChat.setText("");
            showChat.append("选择"+x);
            os.print(x);
            os.flush();
            flag++;
        }
       // inputChat.setText("");
        else {
            String s = ClientThread2.getNumber() +":"+ inputChat.getText().trim();
            inputChat.setText("");
            showChat.append( s + "\n\r");//System.out.println(socket.getInetAddress()
            os.println(s);
            os.flush();

            if (e.getSource() == OPEN)

            {

                FileDialog fd = new FileDialog(this, "open file", FileDialog.LOAD);

                fd.setVisible(true);//FileDialog窗口构造

                if (fd.getFile() != null) {

                    File file = new File(fd.getDirectory() + fd.getFile());

                    String fileName = file.getName();

                    // prefix=fileName.substring(fileName.lastIndexOf(".")+1);//这两行读取后缀名

                    if (file.exists())

                        try {


                            readFile(file.toString());

                        } catch (IOException e1) {

                            // TODO Auto-generated catch block

                            e1.printStackTrace();

                        }

                    else

                        showChat.setText("Filename:" + file + "invalid");

                }

                fd.dispose();

            }
        }

}   
    public void readFile(String file) throws IOException{

        //读文件，并且刷入流中
    	//System.out.println("进入readfile");

    String readline;

   // os.println("开始接收文件");
    //os.flush();

    BufferedReader in = new BufferedReader(new FileReader(file));

   // System.out.println("zzz1");

        while((readline=in.readLine())!=null)

        {

               //System.out.println("zzz2");
        os.println(readline);
        //os.println('\r');
        os.flush();//刷新输出流，使Server马上收到该字符串
        //在系统标准输出上打印读入的字符串
        System.out.println(readline);
        }

        //os.println("文件结束！！");
        //os.flush();//文件结束句柄

}
    public static void main (String [] args) throws IOException {
        String hostName = "localhost";
        int port = 4700;
        Socket socket = new Socket(hostName, port);
        Client cl=new Client(socket);
        ClientThread2 clientThread2=new ClientThread2(socket);
        clientThread2.start();
        showChat.append("如果要进行群聊请输入t；单聊输入想要进行聊天者的编号");
        showChat.append("\r\n");
        String sendfile;
       // File f1;
      //  f1=new File("test.txt");
       // BufferedReader fis=null;


        //由系统标准输入设备构造BufferedReader对象
        BufferedReader sin=new BufferedReader(new InputStreamReader(System.in));
        BufferedReader is=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter os=new PrintWriter(socket.getOutputStream());
        
       
      //  System.out.println("请输入昵称："); 
        
 


    }

    public static void getinfo(File f )throws IOException {

        SimpleDateFormat sdf;
        sdf=new SimpleDateFormat("yyyy 年  MM 月 dd 日 hh 时 mm 分");
        if(f.isFile()) {
        	showChat.append("文件大小为"+f.length()+"      "+"时间为"+sdf.format(new Date(f.lastModified())));
           
        }


    }
   static class ClientThread2 extends Thread {
  //////用户接收消息线程
      private
      Socket socket=null;
      static int number;
      int op;
     // private static Map<Integer,String> onLineUsers = new HashMap<Integer,String>();// 所有在线用户
      //PrintWriter os;
      BufferedReader is =null;
      ClientThread2(Socket socket) throws IOException {
          this.socket=socket;
          this.is=new BufferedReader(new InputStreamReader(socket.getInputStream()));
          number=is.read()-49; //数字1的ASC码
          op=number+1;
          showChat.append("目前上线人数为"+op+",对上线客户从0开始编号，您的编号为"+number);
          showChat.append("\r\n");
          showChat.append("如果要发送文件请先输入sendfile,发送结束请输入end");
          showChat.append("\r\n");
      }
      ClientThread2(){

      }

      public  static int getNumber(){
          return number;
      }
     /* public String getname() {
      	
      	return onLineUsers.get(number);
      }*/
      public   void  run() {
    	  
    	  ClientThread2 ct2=new  ClientThread2();
      	

          while(true)

          {
              try {
            	 // System.out.println("接收消息中");
            	  //String ss=is.readLine();
            	 // System.out.println(ss);
            	//  showChat.append("接收到"+ss);
              	
                 	/* System.out.println("请设置你的昵称：");
                      BufferedReader keyReader = new BufferedReader(new InputStreamReader(System.in));   
                      String name;
             			name = keyReader.readLine();
             		
                      onLineUsers.put(number, name);
                      Set<Integer>keys=onLineUsers.keySet();
                      Iterator iterKey=keys.iterator();
                      Collection<String>values=onLineUsers.values();
                      Iterator iterValue=values.iterator();
                      while(iterKey.hasNext())
                      {
                      	System.out.println("当前好友列表为：");
                      	System.out.println("ID "+(Integer)iterKey.next()+"昵称"+(String)iterValue.next()+"/n");
                      	
                      	//System.out.println("目前上线人数为"+op+",对上线客户从0开始编号，您的编号为"+number);
                      }*/
            	  //FileWriter fw=new FileWriter("recv.txt");
                  String ss=is.readLine();
                  //showChat.append(socket.getInetAddress()+"说:"+ ss +"\n\r");
                  showChat.append("收到"+ss +"\n\r");
            		if(ss.contains("sendfile"))

                    {
                        try {
                            File f2;
                          
                            f2=new File("recv.txt");
                         
                            FileWriter fw=new FileWriter("recv.txt");
                            showChat.append("文件传输开始");
                            
                            
                            //int i=is.read()-48;
                            //String  x=is.readLine();
                            //System.out.println("读到的文件大小为"+x);
                            //while(ss.length()<Integer.parseInt(x)){
                            //for(int j=0;j<i;j++){
                            while((ss=is.readLine())!=null){
                                fw.write(ss);
                                fw.write("\r\n");
                                //  System.out.println(ss);
                                fw.flush();
                                if(ss.contains("end")){
                                	
                                	
                                	showChat.append("文件接收完成!!");
                                	getinfo(f2);
                                    break;
                                    
                                }
                                
                            }
                            
                           
                           

                        }
                        
                        catch(Exception e) {

                            e.printStackTrace();
                        }
            	
                  //ss=is.readLine();
                 // showChat.append(socket.getInetAddress()+"Client收到:"+ ss +"\n\r");
                 // showChat.append(socket.getInetAddress()+"Client收到:"+ ss +"\n\r");
                  
                  //System.out.println("Client收到:" +ss);
                  /*if(ss.equals("sendfile"))

                 {
                      try {
                          File f2;
                          //System.out.println("请输入接收到的文件的放置路径");
                          //BufferedReader sin=new BufferedReader(new InputStreamReader(System.in));
                          //String pathname=sin.readLine();
                          //f2=new File(pathname);
                          f2=new File("recv.txt");
                          //FileWriter fw=new FileWriter(pathname);
                          FileWriter fw=new FileWriter("recv.txt");
                          getinfo(f2);
                          while((ss=is.readLine())!=null){

                              fw.write(ss);

                              fw.write("\r\n");

                              //  System.out.println(ss);

                              fw.flush();
                          }

                      }
                      catch(Exception e) {

                          e.printStackTrace();
                      }
                  }*/

              } 
            		
                   // getinfo(f2);
          }
              catch(Exception e) {

                  e.printStackTrace();
              }
      }


   /* public static void getinfo(File f )throws IOException {

          SimpleDateFormat sdf;
          sdf=new SimpleDateFormat("yyyy 年  MM 月 dd 日 hh 时 mm 分");
          if(f.isFile()) {
              System.out.println("文件大小为"+f.length()+"      "+"时间为"+sdf.format(new Date(f.lastModified())));
              System.out.println("文件位置："+"<Dir>\t"+f.getAbsolutePath()+"\t");

          }*/

      

  }


}
}
