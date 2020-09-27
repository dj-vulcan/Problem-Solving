package com.dj.utils.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.Buffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketMultiThreadRun {

    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(8888);
        ExecutorService service = Executors.newCachedThreadPool();
        socket.setSoTimeout(30000);
        while(true) {
            Socket s;
            try{
                s = socket.accept();
                s.setSoTimeout(30000);
            } catch(SocketTimeoutException e) {
                break;
            }

            service.submit(() -> {
                String sender = null;
                try{
                    BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    String line;
                    while((line = br.readLine()) != null ) {
                        if(sender == null) {
                            sender = line;
                        }
                        if(sender.equals("A")) {
                            ABehavior();
                        } else {
                            BBehavior();
                        }
                    }

                } catch(Exception e) {
                    System.out.println(sender + " END.");
                }
                
            });


        }
        service.shutdown();
    }

    static synchronized void ABehavior() {
        
    }

    static synchronized void BBehavior() {
        
    }
}