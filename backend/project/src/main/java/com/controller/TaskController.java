package com.controller;

import com.controller.view.TaskVO;
import com.error.BusinessException;
import com.error.EmBusinessError;
import com.response.CommonReturnType;
import com.service.TaskService;
import com.service.model.TaskModel;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author czf
 * @Date 2020/4/9 12:18 上午
 */
@RestController
@RequestMapping("/task")
public class TaskController extends BaseController {
    @Autowired
    private TaskService taskService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public CommonReturnType test(){
        return CommonReturnType.create("test");
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonReturnType listTasks(){
        List<TaskModel> taskModelList = taskService.getTaskList();
        List<TaskVO> taskVOList = taskModelList.stream().map(taskModel -> {
            return convertVOFromModel(taskModel);
        }).collect(Collectors.toList());
        return CommonReturnType.create(taskVOList);
    }

    /**
     * 从model转换成视图
     * @param taskModel
     * @return
     */
    private TaskVO convertVOFromModel(TaskModel taskModel){
        TaskVO taskVO = new TaskVO();
        BeanUtils.copyProperties(taskModel,taskVO);
        taskVO.setDeadline(taskModel.getDeadline().toString("yyyy-MM-dd HH:mm:ss"));
        // 无期限。。。
        if (taskVO.getDeadline().startsWith("1970"))
            taskVO.setDeadline(null);
        return taskVO;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonReturnType addTask(@RequestParam(value = "id",required = false) Integer id,
                                    @RequestParam(value = "taskName",required = true)String taskName,
                                    @RequestParam(value = "description", required = false)String description,
                                    @RequestParam(value = "deadline", required = false)String deadline,
                                    @RequestParam(value = "finished", required = true)Integer finished) throws BusinessException {
        TaskModel taskModel = new TaskModel();
        taskModel.setDescription(description);
        taskModel.setTaskName(taskName.trim());
        taskModel.setFinished(finished);
        if (deadline!=null) {
            if ( !isDate(deadline) )
                throw new BusinessException(EmBusinessError.FORMAT_ERROR,"时间类型格式错误！");
            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
            taskModel.setDeadline(DateTime.parse(deadline, formatter));
        }
        taskService.addTask(taskModel);
        return CommonReturnType.create(null);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public CommonReturnType deleteTask(@RequestParam("id")Integer id){
        taskService.deleteTask(id);
        return CommonReturnType.create(null);
    }

    @RequestMapping(value = "/modify", method = RequestMethod.PUT)
    public CommonReturnType modifyTask(@RequestParam(value = "id",required = false) Integer id,
                                       @RequestParam(value = "taskName",required = true)String taskName,
                                       @RequestParam(value = "description", required = false)String description,
                                       @RequestParam(value = "deadline", required = false)String deadline,
                                       @RequestParam(value = "finished", required = true)Integer finished) throws BusinessException {
        TaskModel taskModel = new TaskModel();
        taskName = taskName.trim();
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
