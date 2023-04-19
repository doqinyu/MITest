package test3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 合并后的新文件
 */
public class newFile implements Runnable{
    /**
     * 一级key = FileNo, 二级 key = LineNo
     */
    public static ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, String>> map;
    /**
     * key = FileNo, value = 该文件是否遍历结束
     */
    public static ConcurrentHashMap<Integer, Boolean> fileEndFlagMap;
    /**
     * 合并的文件数
     */
    public static int fileTotal = 10;
    /**
     * 当前应写入的文件行数
     */
    public static int curLineNo = 0;

    static Random random;
    static {
        map = new ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, String>>();
        fileEndFlagMap = new ConcurrentHashMap<Integer, Boolean>();
    }

    /**
     * 写文件
     * @param contentList
     */
    public void writedata(List<String> contentList) {
        //将contentList 写入新文件
        //移除第i行的内容
        for (int i = 0; i < fileTotal; i++) {
            if (fileEndFlagMap.get(i) != null && fileEndFlagMap.get(i) == false) {
                map.get(i).remove(curLineNo);
            }
        }
    }

    @Override
    public void run() {
        try {
            while (!hasDone()) {
                batchFile ready = ready();
                if (ready.ready) {
                    writedata(ready.contentList);
                    curLineNo ++;
                } else {
                    Thread.sleep(random.nextLong());
                }
            }
        } catch (Exception e) {

        }

    }

    /**
     * 写入操作是否结束
     * @return
     */
    public boolean hasDone() {
        //子文件的内容没有读取完
        for (int i = 0; i < fileTotal; i++) {
            if (fileEndFlagMap.get(i) == null || fileEndFlagMap.get(i) == false) {
                return false;
            }
        }
        //map 中的内容已经写到新文件中
        return map.size() == 0;
    }

    /**
     * 当前行的一批文件的内容是否准备好
     * @return
     */
    public batchFile ready () {
        batchFile batchFile = new batchFile();
        batchFile.contentList = new ArrayList<>();
        for (int i = 0; i < fileTotal; i++) {
            /**
             * 如果第i个文件还没准备好，直接返回 no ready
             */
            if (map.get(i) == null || ((fileEndFlagMap.get(i) == null || fileEndFlagMap.get(i) == false) && map.get(i).get(curLineNo) == null) ) {
                return batchFile;
            }

            if (fileEndFlagMap.get(i) == null || fileEndFlagMap.get(i) == false) {
                //未到文件末尾，将第i个文件的第curLineNo内容添加到返回的结果集中
                batchFile.contentList.add(map.get(i).get(curLineNo));
            }

        }
        batchFile.ready = true;
        return batchFile;
    }


    class batchFile {
        /**
         * 该批文件是否已经准备好
         */
        Boolean ready;
        /**
         * 该批文件的内容
         */
        List<String> contentList;

    }
}
