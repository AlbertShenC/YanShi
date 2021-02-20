package info.albertcode.dispatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: Albert Shen
 */
@Service(value = "waitingQueue")
public class WaitingQueue {
    WaitingProcedureQueue waitingProcedureQueue;
    WaitingTaskQueue waitingTaskQueue;

    @Autowired
    public WaitingQueue(WaitingProcedureQueue waitingProcedureQueue, WaitingTaskQueue waitingTaskQueue) {
        this.waitingProcedureQueue = waitingProcedureQueue;
        this.waitingTaskQueue = waitingTaskQueue;
    }

    public WaitingProcedureQueue getWaitingProcedureQueue() {
        return waitingProcedureQueue;
    }

    public void setWaitingProcedureQueue(WaitingProcedureQueue waitingProcedureQueue) {
        this.waitingProcedureQueue = waitingProcedureQueue;
    }

    public WaitingTaskQueue getWaitingTaskQueue() {
        return waitingTaskQueue;
    }

    public void setWaitingTaskQueue(WaitingTaskQueue waitingTaskQueue) {
        this.waitingTaskQueue = waitingTaskQueue;
    }
}
