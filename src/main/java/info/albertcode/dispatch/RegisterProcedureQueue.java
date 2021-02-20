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
@Service(value = "registerProcedureQueue")
public class RegisterProcedureQueue {
    private Queue<Integer> queue;

    public RegisterProcedureQueue() {
        queue = new LinkedList<>();
    }

    public void push(Integer procedureId){
        queue.offer(procedureId);
    }

    public Integer pop(){
        if (queue.isEmpty()){
            return null;
        } else {
            return queue.poll();
        }
    }

    public Integer top(){
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
