package com.huan.iport;

import java.io.IOException;
import java.net.DatagramPacket;

/**
 * Created by tjy on 2016/12/29 0029.
 */
public class Getter extends C implements Runnable {

    /**
     *  监听，不再建议使用 onSend方法，因为对于Getter来说是没有send动作的。
     */

    public static abstract class OnMulticastSocketListener implements C.OnMulticastSocketListener {
        @Deprecated
        public void onSend(byte[] b) {

        }

        @Override
        public abstract void onGet(byte[] b);
    }

    private boolean readied;

    public Getter() {
        super();
    }

    public Getter(String groupAddr, int groupPort) {
        super(groupAddr, groupPort);
    }

    @Override
    public void run() {
        if(!isOnReady()){
            return;
        }
        System.out.println("onReady!");
        readied = true;
        while (readied){
            byte[] receiveData = new byte[1024];
            DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);
            try {
                getMulticastSocket().receive(packet);
                mOMulticastSocketListener.onGet(receiveData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void stop() {
        readied = false;
        super.stop();
    }

    public void start(){
        if(!isOnReady()){
            ready();
            new Thread(this).start();
        }
        else{
            throw new UnsupportedOperationException("thread is readied!");
        }
    }
}
