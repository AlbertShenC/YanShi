package info.albertcode.service.impl;

import info.albertcode.dao.IRequestDao;
import info.albertcode.domain.event.Event;
import info.albertcode.domain.event.HttpResponseEvent;
import info.albertcode.domain.request.HttpRequestRequest;
import info.albertcode.domain.request.Request;
import info.albertcode.domain.task.HttpRequestTask;
import info.albertcode.domain.task.Task;
import info.albertcode.service.ITaskService;
import info.albertcode.utils.http.director.Director;
import info.albertcode.utils.http.domain.HttpRequestAndResponse;
import org.apache.http.NameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: Albert Shen
 */
@Service(value = "taskService")
public class TaskService implements ITaskService {

    @Autowired
    private IRequestDao requestDao;

    // 临时模拟dao层使用
    private Task getTask(Integer taskId){
        Request request = requestDao.findRequestById(1); // todo:通过Task获取到request的id
        System.out.println(request);
        System.out.println("taskId = " + taskId);

        Task task = new HttpRequestTask();
        task.setId(taskId);
        task.setRequest(request);

        return task;
    }

    @Override
    public Event execute(Integer taskId) throws Exception {
        Task task = getTask(taskId);

        if (task.getType().equals("HttpRequest")){
            System.out.println("调用HttpRequest执行方法...");
            return executeHttpRequest(task);
        } else {
            System.out.println("调用具体执行方法错误...");
            return null;
        }
    }

    private Event executeHttpRequest(Task task) throws Exception {
        HttpRequestRequest request = new HttpRequestRequest();
        request.setOverview(task.getRequest().getOverview());
        request.setHeader(task.getRequest().getHeader());
        request.setBody(task.getRequest().getBody());

        Director director = new Director(request.getMethod())
                .uri(request.getUrl());

        String[] headers = request.getHeader().split("&");
        for (String header : headers){
            String[] nameAndValue = header.split("=");
            director.header(nameAndValue[0], nameAndValue[1]);
        }

        String[] parameters = request.getBody().split("&");
        for (String parameter : parameters){
            String[] nameAndValue = parameter.split("=");
            director.parameter(nameAndValue[0], nameAndValue[1]);
        }

        HttpRequestAndResponse requestAndResponse = director.build();
        requestAndResponse.execute();

        HttpResponseEvent event = new HttpResponseEvent();
        event.setSuccessful(true);
        event.setHttpVersion(requestAndResponse.getResponseProtocolVersion());
        event.setStatusCode(requestAndResponse.getResponseStatus());
        event.setReasonPhrase(requestAndResponse.getResponseReasonPhrase());

        String resultHeaders = new String();
        List<NameValuePair> responseHeaders = requestAndResponse.getResponseHeaders();
        for (NameValuePair responseHeader : responseHeaders){
            resultHeaders = resultHeaders + responseHeader.getName() + "=" +
                    responseHeader.getValue() + "&";
        }
        event.setHeader(resultHeaders.substring(0, resultHeaders.length() - 1));

        event.setBody(requestAndResponse.getResponseEntity());

        return event;
    }
}
