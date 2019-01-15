/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Consumer implements Runnable {

    private BlockingQueue<String> bq;
    private int size;
    private List<String> dataList;

    public Consumer(BlockingQueue<String> bq, int size, List<String> dataList) {
        this.bq = bq;
        this.size = size;
        this.dataList = dataList;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < size; i++) {

                String data = bq.take();
                System.out.println(data);
                dataList.add(data);
            }
        } catch (InterruptedException ex) {
            System.out.println("Interupted Consumer");
        }
    }

}
