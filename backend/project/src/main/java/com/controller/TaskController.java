package com.controller;

import com.error.BusinessException;
import com.error.EmBusinessError;
import com.response.CommonReturnType;
import com.service.TaskService;
import com.service.model.TaskModel;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
                                       @RequestParam(value = "finished", required = true)Integer finished) throws BusinessException {
        TaskModel taskModel = new TaskModel();
        taskModel.setDescription(description);
        taskModel.setTaskName(taskName);
        taskModel.setFinished(finished);
        taskModel.setId(id);

        if (deadline!=null) {
            if ( !isDate(deadline) )
                throw new BusinessException(EmBusinessError.FORMAT_ERROR,"时间类型格式错误！");
            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
            taskModel.setDeadline(DateTime.parse(deadline, formatter));
        }
        taskService.modifyTask(taskModel);
        return CommonReturnType.create(null);
    }

    /**
     * 检测时间类型是否合法
     * @param date
     * @return
     */
    private boolean isDate(String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            format.parse(date);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}
