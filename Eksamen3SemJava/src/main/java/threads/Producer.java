/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Producer implements Runnable {

    private BlockingQueue<String> bq;
    private String product;

    public Producer(BlockingQueue<String> bq, String product) {
        this.bq = bq;
        this.product = product;
    }

    @Override
    public void run() {
        try {
            String data = product + " Done";
            Thread.sleep(new Random().nextInt(3000) + 100);
            bq.put(data);
        } catch (InterruptedException ex) {
            System.out.println("Producer Interupted");
        }
    }
}
