package threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ConsumerProducer {

    private List<String> items;
    
    
    //Change the generic to what you need
    public ConsumerProducer(List<String> items) {
        if (items == null) {
            throw new IllegalArgumentException();
        }
        this.items = items;
    }

    public List<String> start() throws InterruptedException {
        ArrayBlockingQueue<String> bq = new ArrayBlockingQueue(10);
       
        List<String> dataList = new ArrayList<>();
        
        ExecutorService es = Executors.newFixedThreadPool(4);
     
        es.submit(new Consumer(bq, items.size(), dataList));
        
        for (String item : items) {
            es.submit(new Producer(bq, item));
        }
        
        es.shutdown();
        es.awaitTermination(30, TimeUnit.DAYS);
        return dataList;
        
    }
    
    public static void main(String[] args) throws InterruptedException {
        List<String> lol = new ArrayList();
        lol.add("karin");
        lol.add("nikoErGud");
        lol.add("nikoErGud1");
        lol.add("nikoErGud2");
        lol.add("nikoErGud3");
        lol.add("nikoErGud4");
        lol.add("nikoErGud5");
        
        ConsumerProducer cp = new ConsumerProducer(lol);
        
        cp.start();
        
    }

}
