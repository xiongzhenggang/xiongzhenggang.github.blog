/**
 * 
 */
package com.xzg.cn.nio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * @author hasee
 * @TIME 2017��1��3��
 * ע��������غ�ʵ������
 */
public class TestServerIo {
	public static  void  main(String arg[]){
		server();
		}
	public static void server(){
	       ServerSocket serverSocket = null;
	       InputStream in = null;
	       try
	       {
	           serverSocket = new ServerSocket(80);
	           int recvMsgSize = 0;
	           byte[] recvBuf = new byte[1024];
	           while(true){
	               Socket clntSocket = serverSocket.accept();
	               SocketAddress clientAddress = clntSocket.getRemoteSocketAddress();//��ȡ�ͻ��˶εĵ�ַ
	               System.out.println("Handling client at "+clientAddress);
	               in = clntSocket.getInputStream();
	            /*   ������ģʽ��,read()��������δ��ȡ���κ�����ʱ���ܾͷ����ˡ�������Ҫ��ע����int����ֵ������������ȡ�˶����ֽڡ�*/
	               while((recvMsgSize=in.read(recvBuf))!=-1){
	                   byte[] temp = new byte[recvMsgSize];
	                   System.arraycopy(recvBuf, 0, temp, 0, recvMsgSize);
	                   System.out.println(new String(temp));
	               }
	           }
	       }
	       catch (IOException e)
	       {
	           e.printStackTrace();
	       }
	       finally{
	           try{
	               if(serverSocket!=null){
	                   serverSocket.close();
	               }
	               if(in!=null){
	                   in.close();
	               }
	           }catch(IOException e){
	               e.printStackTrace();
	           }
	       }
	   }
}
