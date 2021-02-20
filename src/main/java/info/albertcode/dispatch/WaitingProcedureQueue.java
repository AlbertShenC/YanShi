package info.albertcode.dispatch;

import info.albertcode.domain.procedure.Procedure;
import info.albertcode.utils.constants.Setting;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Description:
 * @Author: Albert Shen
 */
@Service(value = "waitingProcedureQueue")
public class WaitingProcedureQueue {
    private Queue<Procedure> queue;

    public WaitingProcedureQueue() {
        queue = new LinkedList<>();
    }

    public void push(Procedure procedure){
        if (queue.size() >= Setting.procedureQueueSize){
            //todo:自定义异常，排队的任务数量过多
            return;
        }
        queue.offer(procedure);
    }

    public Procedure pop(){
        if (queue.isEmpty()){
            return null;
        } else {
            return queue.poll();
        }
    }

    public Procedure top(){
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
