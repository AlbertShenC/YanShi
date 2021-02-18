package info.albertcode.dispatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: Albert Shen
 */
@Service(value = "controller")
public class Controller {
    private TaskQueue taskQueue;
    private Timer timer;
    private Executor executor;
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    public Controller(TaskQueue taskQueue, Timer timer, Executor executor, ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.taskQueue = taskQueue;
        this.timer = timer;
        this.executor = executor;
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }

    public void init(){
        threadPoolTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                return;
            }
        });
    }
}
