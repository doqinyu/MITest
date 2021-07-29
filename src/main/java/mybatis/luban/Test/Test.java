package mybatis.luban.Test;

import org.apache.commons.lang3.text.StrSubstitutor;

import java.util.*;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {
//        Map valuesMap = new HashMap();
//        valuesMap.put("animal", "quick brown fox");
//        valuesMap.put("target", "lazy dog");
//        valuesMap.put("title", "dog");
//        String templateString = "The ${animal} jumped over the ${target}. ${undefined.number:-1234567890}.";
//        String templateString2= "The ${title} jumped over the";
//        StrSubstitutor sub = new StrSubstitutor(valuesMap);
//        String resolvedString = sub.replace(templateString);
//        String resolvedString2 = sub.replace(templateString2);
        List<Long> noticeIdList = new ArrayList<>();
        noticeIdList.add(-2L);
        noticeIdList.add(1L);
        noticeIdList.add(4L);
        noticeIdList.add(6L);
        noticeIdList.add(5L);
        //Long min = Collections.min(noticeIdList);
        noticeIdList = noticeIdList.stream().filter(noticeId -> noticeId <= 0).collect(Collectors.toList());
        System.out.println();
       // System.out.println(min);
    }

}
