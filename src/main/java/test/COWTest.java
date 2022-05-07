package test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class COWTest {
    CopyOnWriteArrayList<UserMedalDO> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
    CopyOnWriteArrayList<Integer> copyOnWriteArrayList2 = new CopyOnWriteArrayList<>();


    public void init () {
        UserMedalDO userMedalDO = new UserMedalDO();
        userMedalDO.setId(1L);
        copyOnWriteArrayList.add(userMedalDO);
        userMedalDO = new UserMedalDO();
        userMedalDO.setId(2L);
        copyOnWriteArrayList.add(userMedalDO);
        userMedalDO = new UserMedalDO();
        userMedalDO.setId(3L);
        copyOnWriteArrayList.add(userMedalDO);
        userMedalDO = new UserMedalDO();
        userMedalDO.setId(4L);
        copyOnWriteArrayList.add(userMedalDO);
        userMedalDO = new UserMedalDO();
        userMedalDO.setId(5L);
        copyOnWriteArrayList.add(userMedalDO);
        userMedalDO = new UserMedalDO();
        userMedalDO.setId(6L);
        copyOnWriteArrayList.add(userMedalDO);
    }

    public void init2() {
        copyOnWriteArrayList2.add(11);
        copyOnWriteArrayList2.add(12);
        copyOnWriteArrayList2.add(13);
        copyOnWriteArrayList2.add(14);
        copyOnWriteArrayList2.add(15);
    }

    public void testAdd() {
        List<UserMedalDO> files = new ArrayList<>();
        Object[] baks = copyOnWriteArrayList.toArray();
        for (int i =0;i < baks.length; i++) {
            UserMedalDO str = (UserMedalDO)baks[i];
            if (str.getId()==1 || str.getId()==5) {
                files.add(str);
            }
        }

        Iterator<UserMedalDO> iterator = files.iterator();
        while (iterator.hasNext()) {
            UserMedalDO s = iterator.next();
            if (!copyOnWriteArrayList.contains(s)) {
                iterator.remove();
            }
        }

        if (!copyOnWriteArrayList.removeAll(files)) {
            System.out.println("false");
        }

        System.out.println("true");
    }

    public void testAdd2() {
        List<Integer> files = new ArrayList<>();
        Object[] baks = copyOnWriteArrayList2.toArray();
        for (int i =0;i < baks.length; i++) {
            if ((Integer) baks[i]==11 || (Integer) baks[i]==15) {
                files.add((Integer) baks[i]);
            }
        }

        Iterator<Integer> iterator = files.iterator();
        while (iterator.hasNext()) {
            Integer s = iterator.next();
            if (!copyOnWriteArrayList2.contains(s)) {
                iterator.remove();
            }
        }

        if (!copyOnWriteArrayList2.removeAll(files)) {
            System.out.println("false");
        }

        System.out.println("true");
    }

    public static void main(String[] args) {
        COWTest cowTest = new COWTest();
        //cowTest.init();
        //cowTest.testAdd();

        cowTest.init2();
        cowTest.testAdd2();
    }
}
