/**
 * 
 */
package com.xzg.cn.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

/**
 * @author xzg
 * @TIME 2017��1��3��
 * ע��������غ�ʵ������
 */
public class TestSockNioCkient {
	public static  void  main(String arg[]){
		client();
		}
	public static void client(){
        SocketChannel socketChannel = null;//����ܵ����൱�ڹ�����
        ByteBuffer buffer = ByteBuffer.allocate(1024);//�����ж��ٵ���Ϊ
        try
        {
        	//��SocketChannel��
            socketChannel = SocketChannel.open();//��̬������ȡʵ��
            socketChannel.configureBlocking(false);//���÷�����
            socketChannel.connect(new InetSocketAddress("127.0.0.1",8080));//���ӵ�ַ
            if(socketChannel.finishConnect())//�������״̬
            {
                int i=0;
                while(true)//������������
                {
                    TimeUnit.SECONDS.sleep(1);
                    //��ȡ���ݣ�д�����ݵ�Buffer(int bytesRead = fileChannel.read(buf);)
                    String info = "I'm "+i+++"-th information from client";
                    buffer.clear();//�Ƚ�������
                    //��Buffer�ж�ȡ���ݣ�System.out.print((char)buf.get());��
                    /*��Channelд��Buffer (fileChannel.read(buf))
                    ͨ��Buffer��put()���� buf.put(��)*/
                    buffer.put(info.getBytes("utf-8"));
                    buffer.flip();
                    while(buffer.hasRemaining()){
                        System.out.println((char)buffer.get());
                        /*ע��SocketChannel.write()�����ĵ�������һ��whileѭ���еġ�
                         * Write()�����޷���֤��д�����ֽڵ�SocketChannel�����ԣ������ظ�����write()ֱ��Bufferû��Ҫд���ֽ�Ϊֹ��
                        ������ģʽ��,read()��������δ��ȡ���κ�����ʱ���ܾͷ����ˡ�
                        ������Ҫ��ע����int����ֵ������������ȡ�˶����ֽڡ�*/
                        socketChannel.write(buffer);
                    }
                }
            }
        }
        catch (IOException | InterruptedException e)
        {
            e.printStackTrace();
        }
        finally{
            try{
                if(socketChannel!=null){
                	//�رգ�
                    socketChannel.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
