package com.java.ro.v1;

import com.java.ro.tasks.model.service.TaskService;
import org.nustaq.kontraktor.Actor;
import org.nustaq.kontraktor.Actors;
import org.nustaq.kontraktor.IPromise;
import org.nustaq.kontraktor.Promise;
import org.nustaq.kontraktor.remoting.tcp.TCPPublisher;
import org.nustaq.kontraktor.util.RateMeasure;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Bartek on 2015-07-09.
 */
public class TestServer extends Actor<TestServer> {

    RateMeasure measure = new RateMeasure("msg/sec");
    private static final String CONFIG_PATH  = "classpath*:spring/root-context.xml";
    private static ApplicationContext context = null;
    private TaskService taskService;

    // that's how the method looks as idiomatic kontraktor method (params instead message class) 1.05 million/sec
    public IPromise<Integer> askSumLong( int x, int y ) {
        measure.count();
        return exec(() -> {
            Thread.sleep(100);
            return x + y;
        });
    }

    // just enable client to put separating text
    public void print( String s ) {
        System.out.println("Client:" + s);
    }

    public IPromise init() {
        taskService = context.getBean(TaskService.class);
        return resolve();
    }


    public static void main(String[] args) {
        context = new ClassPathXmlApplicationContext(CONFIG_PATH);

        TestServer server = Actors.AsActor(TestServer.class, 512000);
        new TCPPublisher(server,7001).publish();
    }


}