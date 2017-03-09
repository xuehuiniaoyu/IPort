package com.huan.iport;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Created by tjy on 2016/12/29 0029.
 */
public class Sender extends Getter {
    public Sender() {
        super();
    }

    public Sender(String groupAddr, int groupPort) {
        super(groupAddr, groupPort);
    }

    /**
     * 发送消息
     * @param msg
     */
    public void send(String msg){
        byte[] data;
        try {
            data = msg.getBytes("utf-8");
            InetAddress targetAddress = InetAddress.getByName(getGroupAddr());
            DatagramPacket outPacket = new DatagramPacket(data, data.length, targetAddress, getGroupPort());
            MulticastSocket socket = new MulticastSocket();
            socket.send(outPacket);
            socket.close();
            mOMulticastSocketListener.onSend(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param b
     */
    public void send(byte[] b){
        try {
            InetAddress targetAddress = InetAddress.getByName(getGroupAddr());
            DatagramPacket outPacket = new DatagramPacket(b, b.length, targetAddress, getGroupPort());
            MulticastSocket socket = new MulticastSocket();
            socket.send(outPacket);
            socket.close();
            mOMulticastSocketListener.onSend(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
