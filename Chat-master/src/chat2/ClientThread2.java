package chat2;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class ClientThread2 extends Thread {
//////�û�������Ϣ�߳�

    private
    Socket socket=null;
    int number;
    int op;
    private static Map<Integer,String> onLineUsers = new HashMap<Integer,String>();// ���������û�
    //PrintWriter os;
    BufferedReader is =null;
    ClientThread2(Socket socket) throws IOException {
        this.socket=socket;
        this.is=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        number=is.read()-49; //����1��ASC��
        op=number+1;
        System.out.println("Ŀǰ��������Ϊ"+op+",�����߿ͻ���0��ʼ��ţ����ı��Ϊ"+number);
       
        
    }

    public int getNumber(){
        return number;
    }
    public String getname() {
    	
    	return onLineUsers.get(number);
    }
    public   void  run() {
    	

        while(true)

        {
            try {
            	
               	 System.out.println("����������ǳƣ�");
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
                    }
                String ss=is.readLine();
                //showChat.append(socket.getInetAddress()+":"+ ss +"\n\r");
                System.out.println("Client�յ�:" +ss);
                if(ss.equals("sendfile"))

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
                }

            } catch (IOException e) {

                // TODOAuto-generated catch block

                e.printStackTrace();

            }

        }
    }

    public static void getinfo(File f )throws IOException {

        SimpleDateFormat sdf;
        sdf=new SimpleDateFormat("yyyy ��  MM �� dd �� hh ʱ mm ��");
        if(f.isFile()) {
            System.out.println("�ļ���СΪ"+f.length()+"      "+"ʱ��Ϊ"+sdf.format(new Date(f.lastModified())));
            System.out.println("�ļ�λ�ã�"+"<Dir>\t"+f.getAbsolutePath()+"\t");

        }

    }

}
