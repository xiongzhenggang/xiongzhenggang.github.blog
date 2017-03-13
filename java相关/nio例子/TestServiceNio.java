/**
 * 
 */
package com.xzg.cn.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author hasee
 * @TIME 2017��1��3��
 * ע��������غ�ʵ������
 */
public class TestServiceNio {
	private static final int BUF_SIZE=1024;
    private static final int PORT = 8080;
    private static final int TIMEOUT = 3000;
 
    public static void main(String[] args)
    {
        selector();
    }
 //�����½���������
    public static void handleAccept(SelectionKey key) throws IOException{
        ServerSocketChannel ssChannel = (ServerSocketChannel)key.channel();
        SocketChannel sc = ssChannel.accept();
        //�ڷ�����ģʽ�£�accept() ���������̷��أ������û���½���������,���صĽ���null�� ��ˣ���Ҫ��鷵�ص�SocketChannel�Ƿ���null
        sc.configureBlocking(false);//���÷�����
        sc.register(key.selector(), SelectionKey.OP_READ,ByteBuffer.allocateDirect(BUF_SIZE));
    }
 
    public static void handleRead(SelectionKey key) throws IOException{//׼��������SelectionKey����Channel��Selector�ܼ�
        SocketChannel sc = (SocketChannel)key.channel();
        ByteBuffer buf = (ByteBuffer)key.attachment();
        long bytesRead = sc.read(buf);
        while(bytesRead>0){
            buf.flip();
            while(buf.hasRemaining()){
                System.out.print((char)buf.get());
            }
            System.out.println();
            buf.clear();
            bytesRead = sc.read(buf);
        }
        if(bytesRead == -1){
            sc.close();
        }
    }
 
    public static void handleWrite(SelectionKey key) throws IOException{//׼��д
        ByteBuffer buf = (ByteBuffer)key.attachment();
        buf.flip();
        SocketChannel sc = (SocketChannel) key.channel();
        while(buf.hasRemaining()){
            sc.write(buf);
        }
        buf.compact();
    }
 
    public static void selector() {
        Selector selector = null;
        ServerSocketChannel ssc = null;
        try{
        	//Selector�Ĵ�����Selector selector = Selector.open();
            selector = Selector.open();
            //��ServerSocketChannel��
            ssc= ServerSocketChannel.open();
            //�󶨵�ַ�˿ں�
            ssc.socket().bind(new InetSocketAddress(PORT));
            ssc.configureBlocking(false);//���÷�����
            /*��Selectorһ��ʹ��ʱ��Channel���봦�ڷ�����ģʽ�¡�����ζ�Ų��ܽ�FileChannel��Selectorһ��ʹ�ã�
             * ��ΪFileChannel�����л���������ģʽ�����׽���ͨ�������ԡ�
             * Ϊ�˽�Channel��Selector���ʹ�ã����뽫Channelע�ᵽSelector�ϣ�
             * ͨ��SelectableChannel.register()������ʵ�֣����ð���5�еĲ��ִ��룺*/
            ssc.register(selector, SelectionKey.OP_ACCEPT);//register()�����ĵڶ�������������һ����interest���ϡ�.Selector����Channelʱ��ʲô�¼�����Ȥ�����Լ������ֲ�ͬ���͵��¼���
            /*Connect
             * 2. Accept
            3. Read
            4. Write*/
            /*ServerSocketChannel�������óɷ�����ģʽ���ڷ�����ģʽ�£�accept() ���������̷��أ�
            �����û���½���������,���صĽ���null�� ��ˣ���Ҫ��鷵�ص�SocketChannel�Ƿ���null.�磺
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (socketChannel != null)
            {
                // do something with socketChannel...
            }    */
            while(true){
         /*һ����Selectorע����һ����ͨ�����Ϳ��Ե��ü������ص�select()��������Щ����������������Ȥ���¼��������ӡ����ܡ�����д��
         �Ѿ�׼����������Щͨ�������仰˵�������ԡ�����������ͨ������Ȥ��select()�����᷵�ض��¼��Ѿ���������Щͨ����*/
            	//select()�������ص�intֵ��ʾ�ж���ͨ���Ѿ�������Ȼ�����ͨ������selector��selectedKeys()���������ʡ���ѡ�������selected key set�����еľ���ͨ����
                if(selector.select(TIMEOUT) == 0){//select()������������һ��ͨ������ע����¼��Ͼ�����
                    System.out.println("==");
                  /*selectNow()��������������ʲôͨ�����������̷��أ�����ע���˷���ִ�з�������ѡ�������
                    ����Դ�ǰһ��ѡ�������û��ͨ����ɿ�ѡ��ģ���˷���ֱ�ӷ����㡣��*/
                    continue;
                }
                /*ͨ��������һ���¼���˼�Ǹ��¼��Ѿ����������ԣ�ĳ��channel�ɹ����ӵ���һ����������Ϊ�����Ӿ�������
                 * һ��server socket channel׼���ý����½�������ӳ�Ϊ�����վ�������
                 * һ�������ݿɶ���ͨ������˵�ǡ������������ȴ�д���ݵ�ͨ������˵�ǡ�д�������� �������¼���SelectionKey���ĸ���������ʾ*/
                //SelectionKey.OP_CONNECT\SelectionKey.OP_ACCEPT\ SelectionKey.OP_READ\SelectionKey.OP_WRITE
                Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                while(iter.hasNext()){
                	//����Selectorע��Channelʱ��register()�����᷵��һ��SelectionKey����������������һЩ�����Ȥ�����ԣ�
                    SelectionKey key = iter.next();
 // ����������interest���������ķ����������channel��ʲô�¼�������Ѿ�������Ҳ��ʹ�����ĸ����������Ƕ�����һ����������
                    if(key.isAcceptable()){//���channel��ʲô�¼�������Ѿ�����
                        handleAccept(key);//�����½���������
                    }
                    if(key.isReadable()){
                        handleRead(key);
                    }
                    if(key.isWritable() && key.isValid()){
                        handleWrite(key);
                    }
                    if(key.isConnectable()){
                        System.out.println("isConnectable = true");
                    }
                    iter.remove();
                    /*ע��ÿ�ε���ĩβ��keyIterator.remove()���á�Selector�����Լ�����ѡ��������Ƴ�SelectionKeyʵ����
                    �����ڴ�����ͨ��ʱ�Լ��Ƴ����´θ�ͨ����ɾ���ʱ��Selector���ٴν��������ѡ�������*/
                }
            }
 
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try{
                if(selector!=null){
                    selector.close();
                }
                if(ssc!=null){
                	//�ر�ServerSocketChannel
                    ssc.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
