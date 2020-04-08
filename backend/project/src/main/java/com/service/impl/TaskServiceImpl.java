package com.service.impl;

import com.service.TaskService;
import com.service.model.TaskModel;
import com.dao.TaskDOMapper;
import com.dataobject.TaskDO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author czf
 * @Date 2020/4/8 11:52 下午
 */
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskDOMapper taskDOMapper;

    /**
     * 列出任务列表
     *
     * @return
     */
    @Override
    public List<TaskModel> getTaskList() {
        List<TaskDO> taskDOList = taskDOMapper.listTasks();
        List<TaskModel> taskModelList =  taskDOList.stream().map(taskDO->{
            return convertModelFromDataObject(taskDO);
        }).collect(Collectors.toList());
        return taskModelList;
    }

    private TaskModel convertModelFromDataObject(TaskDO taskDO){
        TaskModel taskModel = new TaskModel();
        BeanUtils.copyProperties(taskDO,taskModel);
        return taskModel;
    }

    /**
     * 增加一个任务
     *
     * @param taskModel
     */
    @Override
    public void addTask(TaskModel taskModel) {
        taskDOMapper.insertSelective(convertDataObjectFromTaskModel(taskModel));
    }

    private TaskDO convertDataObjectFromTaskModel(TaskModel taskModel) {
        TaskDO taskDO = new TaskDO();
        BeanUtils.copyProperties(taskModel, taskDO);
        return taskDO;
    }

    /**
     * 根据id删除任务
     *
     * @param id
     */
    @Override
    public void deleteTask(Integer id) {
        taskDOMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改task
     *
     * @param taskModel
     */
    @Override
    public void modifyTask(TaskModel taskModel) {
        taskDOMapper.updateByPrimaryKeySelective(convertDataObjectFromTaskModel(taskModel));
    }
}
