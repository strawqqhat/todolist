package com.controller;

import com.response.CommonReturnType;
import com.service.TaskService;
import com.service.model.TaskModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author czf
 * @Date 2020/4/9 12:18 上午
 */
@RestController
@RequestMapping("/task")
public class TaskController extends BaseController {
    @Autowired
    private TaskService taskService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonReturnType listTasks(){
        List<TaskModel> taskModelList = taskService.getTaskList();
        return CommonReturnType.create(taskModelList);
    }

    @RequestMapping(value = "/add", method = RequestMethod.PUT)
    public CommonReturnType addTask(@RequestParam(value = "id",required = false) Integer id,
                                    @RequestParam(value = "taskName",required = true)String taskName,
                                    @RequestParam(value = "description", required = true)String description,
                                    @RequestParam(value = "deadline", required = false)String deadline,
                                    @RequestParam(value = "finished", required = true)Integer finished){
        TaskModel taskModel = new TaskModel();
        taskModel.setDescription(description);
        taskModel.setTaskName(taskName);
        taskModel.setFinished(finished);
        taskService.addTask(taskModel);
        return CommonReturnType.create(null);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.PUT)
    public CommonReturnType deleteTask(@RequestParam("id")Integer id){
        taskService.deleteTask(id);
        return CommonReturnType.create(null);
    }

    @RequestMapping(value = "/modify", method = RequestMethod.PUT)
    public CommonReturnType modifyTask(@RequestParam(value = "id",required = true) Integer id,
                                       @RequestParam(value = "taskName",required = true)String taskName,
                                       @RequestParam(value = "description", required = true)String description,
                                       @RequestParam(value = "deadline", required = false)String deadline,
                                       @RequestParam(value = "finished", required = true)Integer finished){
        TaskModel taskModel = new TaskModel();
        taskModel.setDescription(description);
        taskModel.setTaskName(taskName);
        taskModel.setFinished(finished);
        taskModel.setId(id);
        taskService.modifyTask(taskModel);
        return CommonReturnType.create(null);
    }
}
