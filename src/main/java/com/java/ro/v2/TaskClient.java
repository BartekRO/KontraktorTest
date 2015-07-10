package com.java.ro.v2;

import com.java.ro.v1.TestServer;
import org.nustaq.kontraktor.Actor;
import org.nustaq.kontraktor.Actors;
import org.nustaq.kontraktor.IPromise;
import org.nustaq.kontraktor.remoting.tcp.TCPConnectable;
import org.nustaq.kontraktor.util.Log;

/**
 * Created by BartekRO on 2015-07-10.
 */
public class TaskClient extends Actor<TaskClient> {

    public static final int NUM_TASKS = 100_000;
    private TaskServer server = null;

    public IPromise doAddTasks() {
        int resCount[] = {0}; // valid callback in same thread
        for ( int i = 0; i < NUM_TASKS; i++ ) {
            while ( server.isMailboxPressured() )
                yield(); // nonblocking wait. else actor thread gets stuck as messages queue up massively
            server.addTask("applicationId", "taskType", ""+i, null, "owner"+i).then(res -> {
                resCount[0]++;
            }).catchError(err -> {
                resCount[0]++;
                System.out.println("Error occured: " + err);});
        }
        while( resCount[0] < NUM_TASKS ) {
            yield(1000);
            System.out.println("waiting "+resCount[0]);
        }
        Log.Info(this, "Done");
        return resolve();
    }

    public IPromise init() {
        server = (TaskServer) new TCPConnectable(TaskServer.class,"localhost",7002).connect( (res,err) -> {
            Log.Info(this,"disconnected, exiting");
            System.exit(0);
        }).await(); // pseudo block as inside actor
        return resolve();
    }

    public static void main(String[] args) {
        TaskClient client = Actors.AsActor(TaskClient.class, 512000);
        client.init().await(); // we are outside actors => blocking is ok here
        client.doAddTasks();
    }

}
