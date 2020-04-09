package com.service.impl;

import com.error.BusinessException;
import com.error.EmBusinessError;
import com.service.TaskService;
import com.service.model.TaskModel;
import com.dao.TaskDOMapper;
import com.dataobject.TaskDO;
import org.joda.time.DateTime;
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
        taskModel.setDeadline(new DateTime(taskDO.getDeadline()));
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
        if (taskModel.getDeadline()!=null)
            taskDO.setDeadline(taskModel.getDeadline().toDate());
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
     * 如果task不存在则直接插入.
     *
     * @param taskModel
     */
    @Override
    public void modifyTask(TaskModel taskModel) {
        TaskDO taskDO = convertDataObjectFromTaskModel(taskModel);
        if (taskDO.getId()==null) {
            TaskDO tm = taskDOMapper.selectByTaskName(taskModel.getTaskName());
//            if (tm==null)
//                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"不存在这一task，请先添加再修改");
            if (tm!=null)
                taskDO.setId(tm.getId());
        }
        // 如果id依然为null，则说明表中没有此task
        if (taskDO.getId()!=null)
            taskDOMapper.updateByPrimaryKeySelective(taskDO);
        else taskDOMapper.insertSelective(taskDO);
    }
}
