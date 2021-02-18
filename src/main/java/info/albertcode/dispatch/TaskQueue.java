package info.albertcode.dispatch;

import info.albertcode.domain.task.Task;
import info.albertcode.utils.constants.Setting;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Description:
 * @Author: Albert Shen
 */
@Service(value = "taskQueue")
public class TaskQueue {
    private Queue<Task> queue;

    public TaskQueue() {
        queue = new LinkedList<>();
    }

    public void push(Task task){
        if (queue.size() >= Setting.taskQueueSize){
            //todo:自定义异常，排队的任务数量过多
            return;
        }
        queue.offer(task);
    }

    public Task pop(){
        if (queue.isEmpty()){
            return null;
        } else {
            return queue.poll();
        }
    }

    public Task top(){
        if (queue.isEmpty()){
            return null;
        } else {
            return queue.peek();
        }
    }

    public boolean isEmpty(){
        return queue.isEmpty();
    }

    public Integer size(){
        return queue.size();
    }
}
