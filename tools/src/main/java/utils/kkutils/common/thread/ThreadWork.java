package utils.kkutils.common.thread;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import utils.kkutils.common.LogTool;

/**
 * Created by coder on 16/1/22.
 */
public class ThreadWork {
    public static  interface Work{
        public void work(int i);
    }

    /***
     * 分线程执行一个for 循环, 指定使用线程, 指定 总共需要遍历的次数, 指定需要做的工作
     *
     *
     * 例子:
     *
      File  fileDir=new File(path);
     if(fileDir.isDirectory()){
     File []  files=fileDir.listFiles();

     ThreadTool.doWork(8, files.length, new ThreadTool.Work() {
    @Override
    public void work(int i) {
    File file=files[i];
    readGpDataFromFile(file);
    }
    });

     }
     *
     *
     * @param threadCount
     * @param allSize
     * @param work
     */
    public static int doWork(int threadCount,int allSize,Work work){
        return new WorkImp().doWork(threadCount,allSize,work);
    }

    static class WorkImp{

        AtomicInteger workedCount=new AtomicInteger();
        public  int doWork(int threadCount, final int allSize, final Work work){
            if(threadCount>allSize){
                threadCount=allSize;
            }
            if(threadCount<2)threadCount=2;
            final CountDownLatch countDownLatch=new CountDownLatch(threadCount);
            int every=allSize/(threadCount-1);
//            if(allSize%threadCount==0){
//                every=allSize/threadCount;
//            }
            for(int i=0;i<threadCount;i++){
                final int begin=i*every;
                final int end=begin+every;
                getExecutorService().execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            for(int i=begin;i<end;i++){
                                if (allSize<=i){
                                    break;
                                }
                                try {
                                    workedCount.addAndGet(1);
                                    work.work(i);
                                }catch (Exception e){
                                    LogTool.s(e);
                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }finally {
                            // LogTool.s("完成一组: "+begin+"  "+end);
                            countDownLatch.countDown();
                        }
                    }
                });

            }
            try {
                countDownLatch.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            LogTool.s("目标数量: "+allSize+"  完成数量:"+workedCount.intValue());
            return workedCount.intValue();
        }
    }

    public static ExecutorService getExecutorService() {
        if(executorService==null)initExecutor();
        return executorService;
    }

    private static  ExecutorService executorService;
    public static void  initExecutor(){
        theads.clear();
        executorService= Executors.newFixedThreadPool(8, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread=new Thread(r);
                theads.add(thread);
                return thread;
            }
        });

    }
    static ArrayList<Thread> theads=new ArrayList<>();
    public static void stopAllThead(){
        for(int i=0;i<theads.size();i++){
            theads.get(i).stop();
        }
        initExecutor();
    }
}
