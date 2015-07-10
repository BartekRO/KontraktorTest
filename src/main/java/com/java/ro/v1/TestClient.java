package com.java.ro.v1;

import org.nustaq.kontraktor.Actor;
import org.nustaq.kontraktor.Actors;
import org.nustaq.kontraktor.IPromise;
import org.nustaq.kontraktor.remoting.tcp.TCPConnectable;
import org.nustaq.kontraktor.util.Log;

import java.util.Random;

/**
 * Created by Bartek on 2015-07-09.
 */
public class TestClient {

    public static final int NUM_MSG = 50_000;

    public static class ClientActor extends Actor<ClientActor> {

        TestServer server;

        public IPromise benchAskSum() {
            server.print("benchAskSum START");
            int resCount[] = {0}; // valid callback in same thread
            for ( int i = 0; i < NUM_MSG; i++ ) {
                while ( server.isMailboxPressured() )
                    yield(); // nonblocking wait. else actor thread gets stuck as messages queue up massively
                server.askSumLong(i, i + 1).then(res -> {
//                    System.out.println(res);
                    resCount[0]++;
                });
            }
            while( resCount[0] < NUM_MSG ) {
                yield(1000);
                System.out.println("waiting "+resCount[0]);
            }
            server.print("benchAskSum DONE");
            Log.Info(this, "Done");
            return resolve();
        }


        public IPromise init() {
            server = (TestServer) new TCPConnectable(TestServer.class,"localhost",7001).connect( (res,err) -> {
                Log.Info(this,"disconnected, exiting");
                System.exit(0);
            }).await(); // pseudo block as inside actor
            return resolve();
        }

    }

    public static void main(String[] args) {
        ClientActor client = Actors.AsActor(ClientActor.class, 512000);
        client.init().await(); // we are outside actors => blocking is ok here
        client.benchAskSum();
    }
}
