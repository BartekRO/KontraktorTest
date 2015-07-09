package com.java.ro.v1;

import org.nustaq.kontraktor.Actor;
import org.nustaq.kontraktor.Actors;
import org.nustaq.kontraktor.IPromise;
import org.nustaq.kontraktor.Promise;
import org.nustaq.kontraktor.remoting.tcp.TCPPublisher;
import org.nustaq.kontraktor.util.RateMeasure;

/**
 * Created by Bartek on 2015-07-09.
 */
public class TestServer extends Actor<TestServer> {

    RateMeasure measure = new RateMeasure("msg/sec");

    // that's how the method looks as idiomatic kontraktor method (params instead message class) 1.05 million/sec
    public IPromise<Integer> askSum( int x, int y ) {
        measure.count();
        return new Promise<>(x + y);
    }

    // just enable client to put separating text
    public void print( String s ) {
        System.out.println("Client:"+s);
    }

    public static void main(String[] args) {
        TestServer server = Actors.AsActor(TestServer.class, 512000);
        new TCPPublisher(server,7001).publish();
    }


}