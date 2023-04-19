package test3;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class FileTask implements Runnable{
    /**
     * 文件编号
     */
    int no;
    /**
     * 行号
     */
    int lineNo;
    /**
     * 文件内容
     */
    String content;

    /**
     * 是否是文件最后一行
     */
    boolean end;


    public FileTask (int no, int lineNo, String content, boolean end) {
        this.no = no;
        this.lineNo = lineNo;
        this.content = content;
        this.end = end;
    }


    @Override
    public void run() {
        //执行文件的写入
        /**
         */
        while (true) {
            try {
                /**
                 * 该文件是否创建过
                 */
                ConcurrentHashMap<Integer, String> fileMap = newFile.map.get(no);
                if (fileMap == null) {
                    fileMap = new ConcurrentHashMap<>();
                }
                if (!end) {
                    //添加文件内容
                    fileMap.put(lineNo, content);
                }

                //添加文件结束的标识
                newFile.fileEndFlagMap.put(no, end);

            } catch (Exception e) {
            }
        }
    }
}
