package com.java.ro.tasks.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by BartekRO on 2015-07-10.
 */
@Entity
public class Task  implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 20, nullable = false)
    private String applicationId;

    @Column(length = 20, nullable = false)
    private String taskType;

    @Column(length = 20, nullable = false)
    private String foreignObjectId;

    @Column(nullable = false)
    private Date createdOn;

    @Column(nullable = false)
    private Date lastStateChanged;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskState state;

    @Column(length = 20, nullable = true)
    private String owner;

    @Column(length = 20, nullable = true)
    private String taskGroup;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getLastStateChanged() {
        return lastStateChanged;
    }

    public void setLastStateChanged(Date lastStateChanged) {
        this.lastStateChanged = lastStateChanged;
    }

    public TaskState getState() {
        return state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTaskGroup() {
        return taskGroup;
    }

    public void setTaskGroup(String taskGroup) {
        this.taskGroup = taskGroup;
    }

    public String getForeignObjectId() {
        return foreignObjectId;
    }

    public void setForeignObjectId(String foreignObjectId) {
        this.foreignObjectId = foreignObjectId;
    }
}
