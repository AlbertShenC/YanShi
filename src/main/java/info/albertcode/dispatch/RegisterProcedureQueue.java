package info.albertcode.dispatch;

import info.albertcode.domain.procedure.Procedure;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @Description:
 * @Author: Albert Shen
 */
@Service(value = "registerProcedureQueue")
public class RegisterProcedureQueue {
    private Queue<Procedure> queue;

    public RegisterProcedureQueue() {
        queue = new PriorityQueue<>(new Comparator<Procedure>() {
            @Override
            public int compare(Procedure o1, Procedure o2) {
                if (o1.getExpectExecuteDateTime().isBefore(o2.getExpectExecuteDateTime())){
                    return -1;
                } else {
                    return 1;
                }
            }
        });
    }

    public void push(Procedure procedure){
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
