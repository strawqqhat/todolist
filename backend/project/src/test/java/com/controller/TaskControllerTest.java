package com.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author czf
 * @Date 2020/4/9 9:53 上午
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskControllerTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mvc;
    private MultiValueMap<String, String> paramsMap;
    private RequestBuilder request;

    @Before
    public void setup(){
        //让每个测试用例启动之前都构建这样一个启动项
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        // 初始化参数
        paramsMap = new LinkedMultiValueMap<>();
        paramsMap.add("taskName", "finish_homework~");
        paramsMap.add("description", "practice makes perfect!");
        paramsMap.add("finished", "0");
        paramsMap.add("deadline", "2020-04-22 18:00:00");
    }

    /**
     * 测试 tasklist 接口
     * */
    @Test
    public void taskListTest() throws  Exception{
        mvc.perform(MockMvcRequestBuilders.get("/task/list"))
                .andExpect(MockMvcResultMatchers.status().isOk()) //andExpect
                .andDo(MockMvcResultHandlers.print()) //andDo
                .andExpect(jsonPath("$.status").value("success"))
                .andReturn();//andReturn
    }
    /**
     * 测试 addtask 接口
     * */
    @Test
    public void addTaskTest() throws  Exception{
//        String paramString = "{\"taskName\": \"finishhomework\", \"description\":\"practicemakesperfect!\", \"finished\": 0 ";
//        mvc.perform(MockMvcRequestBuilders.post("/task/add").contentType(MediaType.APPLICATION_JSON)
//                .content(paramString)
//                .accept(MediaType.APPLICATION_JSON)) //accept指定客户端能够接收的内容类型
//                .andExpect(MockMvcResultMatchers.status().isOk()) //andExpect
//                .andDo(MockMvcResultHandlers.print()) //andDo
//                .andExpect(jsonPath("$.status").value("success"))
//                .andReturn();//andReturn
        request = MockMvcRequestBuilders.post("/task/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .params(paramsMap);
        mvc.perform(request)
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.status").value("success"))
                .andReturn(); //andReturn
    }

    /**
     * 测试 modify 接口
     * */
    @Test
    public void modifyTaskTest() throws  Exception{
//        mvc.perform(MockMvcRequestBuilders.put("/task/modify").contentType(MediaType.APPLICATION_JSON)
//                .content("{\"taskName\":\"finish homework\", \"description\":\"practice makes perfect!\",\"finished\": 0 ")
//                .accept(MediaType.APPLICATION_JSON)) //accept指定客户端能够接收的内容类型
//                .andExpect(MockMvcResultMatchers.status().isOk()) //andExpect
//                .andDo(MockMvcResultHandlers.print()) //andDo
//                .andExpect(jsonPath("$.status").value("success"))
//                .andReturn();//andReturn
        paramsMap.set("finished", "1");
        request = MockMvcRequestBuilders.put("/task/modify")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .params(paramsMap);
        mvc.perform(request)
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.status").value("success"))
                .andReturn(); //andReturn
        paramsMap.set("finished","0");
    }

    /**
     * 测试 delete 接口
     * */
    @Test
    public void deleteTaskTest() throws  Exception {
        request = MockMvcRequestBuilders.delete("/task/delete")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .params(paramsMap);
        mvc.perform(request)
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.status").value("success"))
                .andReturn(); //andReturn
    }

    /**
     * 测试 TaskController
     * */
    @Test
    public void testTaskController() throws Exception {
        // 测试UserController
        taskListTest();  // 查
        addTaskTest();  // 增
        taskListTest();  // 查
        modifyTaskTest(); // 改
        deleteTaskTest(); // 删
//        RequestBuilder request;
//
//        // 测试 list
//        request = get("/task/list");
//        mvc.perform(request)
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.status").value("success"))
//                .andDo(MockMvcResultHandlers.print())
//                .andReturn(); //andReturn
//
//        // 测试 add
//        String paramString = "{\"taskName\":\"finishhomework\",\"description\":\"practicemakesperfect!\", \"finished\": 0 }";
//        //Map<String,String> paramsMap = new HashMap<>();
//
//
//        request = MockMvcRequestBuilders.post("/task/add")
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//                .params(paramsMap);
//        mvc.perform(request)
//                .andExpect(status().isOk())
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(jsonPath("$.status").value("success"))
//                .andReturn(); //andReturn
//
//        // 测试 put
//        paramsMap.set("finished", "1");
//        request = MockMvcRequestBuilders.put("/task/modify")
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//                .params(paramsMap);
//        mvc.perform(request)
//                .andExpect(status().isOk())
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(jsonPath("$.status").value("success"))
//                .andReturn(); //andReturn
//
//        // 测试delete
//        request = MockMvcRequestBuilders.delete("/task/delete")
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//                .params(paramsMap);
//        mvc.perform(request)
//                .andExpect(status().isOk())
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(jsonPath("$.status").value("success"))
//                .andReturn(); //andReturn
    }

}
