package info.albertcode.dispatch;

import info.albertcode.dao.IProcedureDao;
import info.albertcode.domain.procedure.Procedure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @Description:
 * @Author: Albert Shen
 */
@Service(value = "yanShiCoreTimer")
public class YanShiCoreTimer {
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    private WaitingQueue waitingQueue;
    private RegisterProcedureQueue registerProcedureQueue;
    private IProcedureDao procedureDao;

    @Autowired
    public YanShiCoreTimer(ThreadPoolTaskExecutor threadPoolTaskExecutor, WaitingQueue waitingQueue, RegisterProcedureQueue registerProcedureQueue, IProcedureDao procedureDao) {
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
        this.waitingQueue = waitingQueue;
        this.registerProcedureQueue = registerProcedureQueue;
        this.procedureDao = procedureDao;
    }

    public void registerProcedure(Integer procedureId){
        Procedure procedure = procedureDao.findProcedureById(procedureId);
        procedure.calculateExpectExecuteDateTime();
        synchronized (registerProcedureQueue){
            registerProcedureQueue.push(procedure);
            registerProcedureQueue.notify();
        }
    }

    public void execute(){
        threadPoolTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("Timer：初始化中...");
                WaitingProcedureQueue waitingProcedureQueue = waitingQueue.getWaitingProcedureQueue();
                System.out.println("Timer：初始化完成");
                while (true){
                    synchronized (registerProcedureQueue){
                        try {
                            if (registerProcedureQueue.isEmpty()){
                                registerProcedureQueue.wait();
                            } else {
                                Long milliSecond = registerProcedureQueue.top().getExpectExecuteDateTime() // 队列头部Procedure的预计执行时间
                                        .toInstant(ZoneOffset.of("+8")).toEpochMilli(); // 并将其转为东八区毫秒级的时间戳
                                Long milliSecondNow = LocalDateTime.now()
                                        .toInstant(ZoneOffset.of("+8")).toEpochMilli();
                                System.out.println((milliSecond - milliSecondNow) + "毫秒后运行");
                                registerProcedureQueue.wait((milliSecond - milliSecondNow > 0) ? (milliSecond - milliSecondNow) : 1);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        synchronized (waitingQueue){
                            boolean flag = false;
                            while (!registerProcedureQueue.isEmpty()){
                                if (registerProcedureQueue.top().getExpectExecuteDateTime()
                                        .isBefore(LocalDateTime.now())){
                                    Procedure procedure = registerProcedureQueue.pop();
                                    waitingProcedureQueue.push(procedure);
                                    procedure.setLastExecuteDateTime(procedure.getExpectExecuteDateTime());
                                    procedureDao.updateProcedure(procedure);
                                    flag = true;
                                } else {
                                    break;
                                }
                            }
                            if (flag == true){
                                waitingQueue.notify();
                            }
                        }
                    }
                }
            }
        });
    }
}
