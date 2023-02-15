package excel;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class FileTest {

    public void diff(String before, String after) {
        Set<String> beforeSet = new HashSet<>();
        Set<String> afterSet = new HashSet<>();

        Set<String> commonSet = new HashSet<>();

        try {
            FileReader fr = new FileReader(before);
            BufferedReader br = new BufferedReader(fr);
            String text;
            while ((text = br.readLine()) != null) {
                beforeSet.add(text);
            }


            fr = new FileReader(after);
            br = new BufferedReader(fr);
            while ((text = br.readLine()) != null) {
                afterSet.add(text);
            }

            for (String s: beforeSet) {
                if (afterSet.contains(s)) {
                    commonSet.add(s);
                }
            }

//            HashSet<String> OnlyBeforeSet = new HashSet<>();
//            OnlyBeforeSet.addAll(beforeSet);
//            OnlyBeforeSet.removeAll(commonSet);
//
//            HashSet<String> OnlyAfterSet = new HashSet<>();
//            OnlyAfterSet.addAll(afterSet);
//            OnlyAfterSet.removeAll(commonSet);

            System.out.println();

        } catch (Exception e) {
            System.out.println();
        }

        System.out.println("");
    }

    public void process(String source, String dest) {
        //读取源文件的值，过滤掉真曝光数 < 10000的
        //记录其他三个指标的最大最小值
        int minLike = Integer.MAX_VALUE;
        int maxLike = Integer.MIN_VALUE;
        int minShare = Integer.MAX_VALUE;
        int maxShare = Integer.MIN_VALUE;
        int minDownload = Integer.MAX_VALUE;
        int maxDownload = Integer.MIN_VALUE;
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(dest)));
            FileReader fr = new FileReader(source);
            BufferedReader br = new BufferedReader(fr);
            String text;
            while ((text = br.readLine()) != null) {
                String[] split = text.split("\\s+");
                if (Integer.parseInt(split[1]) >= 10000) {
                    int like = Integer.parseInt(split[2]);
                    int share = Integer.parseInt(split[3]);
                    int down = Integer.parseInt(split[4]);
                    minLike = Math.min(minLike, like);
                    maxLike = Math.max(maxLike, like);

                    minShare = Math.min(minShare, share);
                    maxShare = Math.max(maxShare, share);

                    minDownload = Math.min(minDownload, down);
                    maxDownload = Math.max(maxDownload, down);

                    bw.write(text + "\n");
                    bw.flush();
                }
            }

            bw.write("minLike = " + minLike + ", maxLike = " + maxLike + ", minShare = " + minShare + ", maxShare = " + maxShare +
                    ", minDownload = " + minDownload + ", maxDownload = " + maxDownload + "\n");
            bw.flush();

        } catch (Exception e) {
            System.out.println();
        }

        System.out.println("");

    }

    public static void main(String[] args) {
        String before = "/Users/doqinvera/Desktop/exp_redis.log";
        String after = "/Users/doqinvera/Desktop/normal_redis.log";
        FileTest fileTest = new FileTest();
        fileTest.process(before, before + ".filter");
        fileTest.process(after, after + ".filter");
        System.out.println();
    }
}
