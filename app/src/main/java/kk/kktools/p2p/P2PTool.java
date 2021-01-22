package kk.kktools.p2p;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utils.kkutils.common.LogTool;

/**
 * 功能不通。。。
 */
public class P2PTool {
    public Socket socketClient;
    public ServerSocket socketServer;
    public String name="A";
    public int localPort;
    List<Socket> clientList= Collections.synchronizedList(new ArrayList<>());

    public P2PTool(int localPort,P2PListener p2PListener){
        this.p2PListener=p2PListener;
        this.localPort=localPort;
        getIp();
    }

    public void startP2P(String remoteIp,int remotePort){
        execute(new Runnable() {
            @Override
            public void run() {
                boolean connect = connect(remoteIp, remotePort);//先连接
                System.out.println("startP2P:是否先连接上"+connect);
                if(!connect){//没连接成功
                    startServer();
                }else {
                    send(socketClient,"我连接了");
                }
            }
        });

    }


    public boolean connect(String ip,int port){
        boolean isConnect=false;
        try {
            if(socketServer!=null){
                socketServer.close();
            }
            socketClient =new Socket();
            socketClient.setReuseAddress(true);
            socketClient.bind(new InetSocketAddress(localPort));
            socketClient.setKeepAlive(true);
            socketClient.connect(new InetSocketAddress(ip,port));
            isConnect=true;
            handlerRead(socketClient);
        }catch (Exception e){
            e.printStackTrace();
        }
        return isConnect;
    }
    public  void startServer(){
        try {
            if(socketClient!=null){
                socketClient.close();
            }
            socketServer=new ServerSocket();
            socketServer.setReuseAddress(true);
            socketServer.bind(new InetSocketAddress(localPort));
            System.out.println("服务启动了："+localPort);
            while (true){
                Socket client = socketServer.accept();
                clientList.add(client);
                handlerRead(client);
                send(client,"服务连接");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void handlerRead(Socket socket){
        execute(new Runnable() {
            @Override
            public void run() {
                try {
                    while (socket.isConnected()){
                        String read = read(socket.getInputStream());
                        if(p2PListener!=null){
                            p2PListener.onReadMsg(socket,read);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    public void sendAll(String text){
        for (Socket socket : clientList) {
            send(socket,text);
        }
    }
    public void send(Socket client,String text){
        execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (client.isConnected()){
                        write(client.getOutputStream(),text);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }



    public  void getIp(){
        execute(new Runnable() {
            @Override
            public void run() {
                Object[] ipPort = getIpPort(localPort);//打洞出去
                if(p2PListener!=null){
                    p2PListener.onIpGet(""+ipPort[0],Integer.valueOf(""+ipPort[1]));
                }
            }
        });

    }
    public P2PListener p2PListener;
    public interface P2PListener{
        public void onReadMsg(Socket client,String text);
        public void onIpGet(String ip,int port);
    }













    public static void execute(Runnable runnable){
        if(runnable==null)return;
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    runnable.run();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();
    }
    public static Object[] getIpPort(int localPort){
        String ip="";
        int port=0;
        try {
            String result=getHttp("https://zh-hans.ipshu.com/my_info",localPort);
            BufferedReader br=new BufferedReader(new StringReader(result));
            while (true){
                String s = br.readLine();
                if(s==null){
                    break;
                }
                if(s.contains("<li>远程地址")){//远程地址
                    Pattern pattern=Pattern.compile("[0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+");
                    Matcher matcher = pattern.matcher(s);
                    if(matcher.find()){
                        String group = matcher.group();
                        ip=group;
                    }
                }
                if(s.contains("<li>远程端口")){//远程地址
                    Pattern pattern=Pattern.compile("[0-9]+");
                    Matcher matcher = pattern.matcher(s);
                    if(matcher.find()){
                        String group = matcher.group();
                        port=Integer.valueOf(group);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println(ip+":"+port);
        Object[] result=new Object[2];
        result[0]=ip;
        result[1]=port;
        return result;
    }


    public static String read(InputStream inputStream){
        StringBuffer sb=new StringBuffer();
        try {
            BufferedReader br=new BufferedReader(new InputStreamReader(inputStream));
            while (true){
                String s=br.readLine();
                if(s!=null){
                    sb.append(s+"\n");
                }else {
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }
    public static void write(OutputStream outputStream,String text){
        try {
            BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(outputStream));
            bw.write(text);
            bw.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static String getHttp(String urlStr,int localPort){
        String result="";
        try {
            URL url=new URL(urlStr);
            String text="GET "+url.getPath()+" HTTP/1.1\r\n" +
                    "Host: "+url.getHost()+"\r\n" +
                    "\r\n";
            SSLSocket socket = (SSLSocket) ((SSLSocketFactory) SSLSocketFactory.getDefault()).createSocket();
            socket.setKeepAlive(true);
            socket.setReuseAddress(true);
            socket.bind(new InetSocketAddress(localPort));
            socket.connect(new InetSocketAddress(url.getHost(), url.getPort()<0?url.getDefaultPort():url.getPort()));
            write(socket.getOutputStream(),text);

            result = read(socket.getInputStream());
            //socket.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

}
