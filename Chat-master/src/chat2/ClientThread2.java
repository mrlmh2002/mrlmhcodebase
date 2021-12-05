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
//////用户接收消息线程

    private
    Socket socket=null;
    int number;
    int op;
    private static Map<Integer,String> onLineUsers = new HashMap<Integer,String>();// 所有在线用户
    //PrintWriter os;
    BufferedReader is =null;
    ClientThread2(Socket socket) throws IOException {
        this.socket=socket;
        this.is=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        number=is.read()-49; //数字1的ASC码
        op=number+1;
        System.out.println("目前上线人数为"+op+",对上线客户从0开始编号，您的编号为"+number);
       
        
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
            	
               	 System.out.println("请设置你的昵称：");
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
                    }
                String ss=is.readLine();
                //showChat.append(socket.getInetAddress()+":"+ ss +"\n\r");
                System.out.println("Client收到:" +ss);
                if(ss.equals("sendfile"))

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
                }

            } catch (IOException e) {

                // TODOAuto-generated catch block

                e.printStackTrace();

            }

        }
    }

    public static void getinfo(File f )throws IOException {

        SimpleDateFormat sdf;
        sdf=new SimpleDateFormat("yyyy 年  MM 月 dd 日 hh 时 mm 分");
        if(f.isFile()) {
            System.out.println("文件大小为"+f.length()+"      "+"时间为"+sdf.format(new Date(f.lastModified())));
            System.out.println("文件位置："+"<Dir>\t"+f.getAbsolutePath()+"\t");

        }

    }

}
