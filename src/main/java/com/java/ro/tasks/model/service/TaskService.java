package com.java.ro.tasks.model.service;

import com.java.ro.tasks.model.entity.Task;
import com.java.ro.tasks.model.entity.TaskState;
import com.java.ro.tasks.model.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by BartekRO on 2015-07-10.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Long addTask(String applicationId, String taskType, String foreignObjectId, String taskGroup, String owner) {
        Task task = new Task();
        task.setApplicationId(applicationId);
        task.setTaskType(taskType);
        task.setForeignObjectId(foreignObjectId);
        task.setTaskGroup(taskGroup);
        task.setOwner(owner);
        task.setState(TaskState.NEW);
        task.setCreatedOn(new Date());
        task.setLastStateChanged(task.getCreatedOn());
        return taskRepository.save(task).getId();
    }
}
