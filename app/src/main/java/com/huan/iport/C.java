package com.huan.iport;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 * Created by tjy on 2016/12/29 0029.
 */
public class C {

    public interface OnMulticastSocketListener {
        void onSend(byte[] b);
        void onGet(byte[] b);
    }

    private String groupAddr = "239.0.0.239"; // 组ip
    private int groupPort = 1900; // 组端口

    private boolean onReady;
    private InetAddress mGroup; // 组地址
    private MulticastSocket multicastSocket; // 套接字
    protected OnMulticastSocketListener mOMulticastSocketListener = new OnMulticastSocketListener() {
        @Override
        public void onSend(byte[] b) {

        }

        @Override
        public void onGet(byte[] b) {

        }
    };

    public C() {
    }

    public C(String groupAddr, int groupPort) {
        this.groupAddr = groupAddr;
        this.groupPort = groupPort;
    }

    /**
     * 创建套接字
     */
    void ready(){
        try {
            multicastSocket = new MulticastSocket(groupPort);
            multicastSocket.setLoopbackMode(true);
            mGroup = InetAddress.getByName(groupAddr);
            multicastSocket.joinGroup(mGroup);
            onReady = true;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭套接字
     */
    public void stop(){
        if(multicastSocket != null){
            try {
                multicastSocket.leaveGroup(mGroup);
            } catch (IOException e) {
                e.printStackTrace();
            }
            multicastSocket = null;
            mGroup = null;
            onReady = false;
        }
    }

    public MulticastSocket getMulticastSocket() {
        return multicastSocket;
    }

    public String getGroupAddr() {
        return groupAddr;
    }

    public int getGroupPort() {
        return groupPort;
    }

    public boolean isOnReady() {
        return onReady;
    }

    public void setOnMulticastSocketListener(OnMulticastSocketListener onMulticastSocketListener) {
        this.mOMulticastSocketListener = onMulticastSocketListener;
    }
}
