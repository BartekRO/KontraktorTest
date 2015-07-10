package com.java.ro.v2;

import com.java.ro.tasks.model.service.TaskService;
import org.nustaq.kontraktor.Actor;
import org.nustaq.kontraktor.Actors;
import org.nustaq.kontraktor.IPromise;
import org.nustaq.kontraktor.remoting.tcp.TCPPublisher;
import org.nustaq.kontraktor.util.RateMeasure;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by BartekRO on 2015-07-10.
 */
public class TaskServer extends Actor<TaskServer> {

    RateMeasure measure = new RateMeasure("msg/sec");
    private static final String CONFIG_PATH  = "classpath*:spring/root-context.xml";
    private static ApplicationContext context = null;
    private TaskService taskService;

    public IPromise<Long> addTask(String applicationId, String taskType, String foreignObjectId, String taskGroup, String owner) {
        measure.count();
        return exec(() -> {
            try {
                return taskService.addTask(applicationId, taskType, foreignObjectId, taskGroup, owner);
            } catch (Throwable t) {
                t.printStackTrace();
                throw t;
            }
        });
    }

    public IPromise init() {
        taskService = context.getBean(TaskService.class);
        return resolve();
    }

    public static void main(String[] args) {
        context = new ClassPathXmlApplicationContext(CONFIG_PATH);

        TaskServer server = Actors.AsActor(TaskServer.class, 512000);
        server.init().await();
        new TCPPublisher(server,7002).publish();
    }
}
