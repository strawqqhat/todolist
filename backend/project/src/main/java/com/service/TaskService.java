package com.service;

import com.service.model.TaskModel;

import java.util.List;

/**
 * @author czf
 * @Date 2020/4/8 11:49 下午
 */
public interface TaskService {
    /**
     * 列出任务列表
     * @return
     */
    List<TaskModel> getTaskList();

    /**
     * 增加一个任务
     * @param taskModel
     */
    void addTask(TaskModel taskModel);

    /**
     * 根据id删除任务
     * @param id
     */
    void deleteTask(Integer id);

    /**
     * 修改task
     * @param taskModel
     */
    void modifyTask(TaskModel taskModel);

}
