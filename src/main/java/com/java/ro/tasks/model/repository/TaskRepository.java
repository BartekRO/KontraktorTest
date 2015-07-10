package com.java.ro.tasks.model.repository;

import com.java.ro.tasks.model.entity.Task;
import com.java.ro.v2.TaskServer;
import org.nustaq.kontraktor.IPromise;
import org.nustaq.kontraktor.util.Log;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by BartekRO on 2015-07-10.
 */
public interface TaskRepository extends JpaRepository<Task, Long> {

}
