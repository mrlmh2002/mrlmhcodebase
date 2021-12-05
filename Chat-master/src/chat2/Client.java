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
    Socket socket=null; //�����뱾�߳���ص�Socket����,������Ϣ�߳�

    private BufferedReader is = null;
    static int flag=0;

    private PrintWriter os= null;
    private TextField inputChat= new TextField();//������
    private static TextArea showChat = new TextArea();//��ʾ��
    private MenuItem OPEN=new MenuItem("���ļ�");//�˵�����



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

        addWindowListener(new WindowAdapter() {//��Ӧ�رհ�ť

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
            showChat.append("ѡ��"+x);
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

                fd.setVisible(true);//FileDialog���ڹ���

                if (fd.getFile() != null) {

                    File file = new File(fd.getDirectory() + fd.getFile());

                    String fileName = file.getName();

                    // prefix=fileName.substring(fileName.lastIndexOf(".")+1);//�����ж�ȡ��׺��

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

        //���ļ�������ˢ������
    	//System.out.println("����readfile");

    String readline;

   // os.println("��ʼ�����ļ�");
    //os.flush();

    BufferedReader in = new BufferedReader(new FileReader(file));

   // System.out.println("zzz1");

        while((readline=in.readLine())!=null)

        {

               //System.out.println("zzz2");
        os.println(readline);
        //os.println('\r');
        os.flush();//ˢ���������ʹServer�����յ����ַ���
        //��ϵͳ��׼����ϴ�ӡ������ַ���
        System.out.println(readline);
        }

        //os.println("�ļ���������");
        //os.flush();//�ļ��������

}
    public static void main (String [] args) throws IOException {
        String hostName = "localhost";
        int port = 4700;
        Socket socket = new Socket(hostName, port);
        Client cl=new Client(socket);
        ClientThread2 clientThread2=new ClientThread2(socket);
        clientThread2.start();
        showChat.append("���Ҫ����Ⱥ��������t������������Ҫ���������ߵı��");
        showChat.append("\r\n");
        String sendfile;
       // File f1;
      //  f1=new File("test.txt");
       // BufferedReader fis=null;


        //��ϵͳ��׼�����豸����BufferedReader����
        BufferedReader sin=new BufferedReader(new InputStreamReader(System.in));
        BufferedReader is=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter os=new PrintWriter(socket.getOutputStream());
        
       
      //  System.out.println("�������ǳƣ�"); 
        
 


    }

    public static void getinfo(File f )throws IOException {

        SimpleDateFormat sdf;
        sdf=new SimpleDateFormat("yyyy ��  MM �� dd �� hh ʱ mm ��");
        if(f.isFile()) {
        	showChat.append("�ļ���СΪ"+f.length()+"      "+"ʱ��Ϊ"+sdf.format(new Date(f.lastModified())));
           
        }


    }
   static class ClientThread2 extends Thread {
  //////�û�������Ϣ�߳�
      private
      Socket socket=null;
      static int number;
      int op;
     // private static Map<Integer,String> onLineUsers = new HashMap<Integer,String>();// ���������û�
      //PrintWriter os;
      BufferedReader is =null;
      ClientThread2(Socket socket) throws IOException {
          this.socket=socket;
          this.is=new BufferedReader(new InputStreamReader(socket.getInputStream()));
          number=is.read()-49; //����1��ASC��
          op=number+1;
          showChat.append("Ŀǰ��������Ϊ"+op+",�����߿ͻ���0��ʼ��ţ����ı��Ϊ"+number);
          showChat.append("\r\n");
          showChat.append("���Ҫ�����ļ���������sendfile,���ͽ���������end");
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
            	 // System.out.println("������Ϣ��");
            	  //String ss=is.readLine();
            	 // System.out.println(ss);
            	//  showChat.append("���յ�"+ss);
              	
                 	/* System.out.println("����������ǳƣ�");
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
                      	System.out.println("��ǰ�����б�Ϊ��");
                      	System.out.println("ID "+(Integer)iterKey.next()+"�ǳ�"+(String)iterValue.next()+"/n");
                      	
                      	//System.out.println("Ŀǰ��������Ϊ"+op+",�����߿ͻ���0��ʼ��ţ����ı��Ϊ"+number);
                      }*/
            	  //FileWriter fw=new FileWriter("recv.txt");
                  String ss=is.readLine();
                  //showChat.append(socket.getInetAddress()+"˵:"+ ss +"\n\r");
                  showChat.append("�յ�"+ss +"\n\r");
            		if(ss.contains("sendfile"))

                    {
                        try {
                            File f2;
                          
                            f2=new File("recv.txt");
                         
                            FileWriter fw=new FileWriter("recv.txt");
                            showChat.append("�ļ����俪ʼ");
                            
                            
                            //int i=is.read()-48;
                            //String  x=is.readLine();
                            //System.out.println("�������ļ���СΪ"+x);
                            //while(ss.length()<Integer.parseInt(x)){
                            //for(int j=0;j<i;j++){
                            while((ss=is.readLine())!=null){
                                fw.write(ss);
                                fw.write("\r\n");
                                //  System.out.println(ss);
                                fw.flush();
                                if(ss.contains("end")){
                                	
                                	
                                	showChat.append("�ļ��������!!");
                                	getinfo(f2);
                                    break;
                                    
                                }
                                
                            }
                            
                           
                           

                        }
                        
                        catch(Exception e) {

                            e.printStackTrace();
                        }
            	
                  //ss=is.readLine();
                 // showChat.append(socket.getInetAddress()+"Client�յ�:"+ ss +"\n\r");
                 // showChat.append(socket.getInetAddress()+"Client�յ�:"+ ss +"\n\r");
                  
                  //System.out.println("Client�յ�:" +ss);
                  /*if(ss.equals("sendfile"))

                 {
                      try {
                          File f2;
                          //System.out.println("��������յ����ļ��ķ���·��");
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
          sdf=new SimpleDateFormat("yyyy ��  MM �� dd �� hh ʱ mm ��");
          if(f.isFile()) {
              System.out.println("�ļ���СΪ"+f.length()+"      "+"ʱ��Ϊ"+sdf.format(new Date(f.lastModified())));
              System.out.println("�ļ�λ�ã�"+"<Dir>\t"+f.getAbsolutePath()+"\t");

          }*/

      

  }


}
}
