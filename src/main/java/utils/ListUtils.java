package utils;

//import org.apache.commons.collections.CollectionUtils;

import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * List切分成小的List
 */
public class ListUtils {
    public static <T> List<List<T>> splitList(List<T> list, int shardSize) {
        if (CollectionUtils.isEmpty(list)) {
            throw new RuntimeException("splitList 入参list为空！！");
        }
        List<List<T>> resultList = new ArrayList<>();
        int totalNum = list.size();
        int pageSize = totalNum / shardSize;
        if (totalNum % shardSize != 0) {
            pageSize++;
        }
        for (int i = 0; i < pageSize; i++) {
            if (i == pageSize - 1) {
                resultList.add(list.subList(i * shardSize, list.size()));
            } else {
                resultList.add(list.subList(i * shardSize, i * shardSize + shardSize));
            }
        }
        return resultList;
    }

    /**
     * 获取list中某一列值的list
     * @param list 原始list
     * @param fieldName 属性名称
     * @param <T>
     * @return
     */
    public static <T> List<T> getColumn(List list, String fieldName) {
        List<T> resultList = new ArrayList<>();
        try {
            Field field = null;
            for (Object o : list) {
                if (field == null) {
                    field = getFieldByClasses(fieldName, o);
                }
                Object key = field.get(o);
                resultList.add((T) key);
            }
            return resultList;
        } catch (Exception e) {
            throw new RuntimeException("获取列值错误！" + e.getMessage());
        }
    }

    /**
     * 获取list中某一列值的list
     * @param list 原始list
     * @param fieldName 属性名称
     * @param <T>
     * @return
     */
    public static <T> Set<T> getColumnToSet(List list, String fieldName) {
        Set<T> resultSet = new HashSet();
        try {
            Field field = null;
            for (Object o : list) {
                if (field == null) {
                    field = getFieldByClasses(fieldName, o);
                }
                Object key = field.get(o);
                resultSet.add((T) key);
            }
            return resultSet;
        } catch (Exception e) {
            throw new RuntimeException("获取列值错误！" + e.getMessage());
        }
    }

    /**
     * 获取list中某一列值的list。保证添加顺序与遍历顺序一致
     * @param list 原始list
     * @param fieldName 属性名称
     * @param <T>
     * @return
     */
    public static <T> Set<T> getColumnToSortSet(List list, String fieldName) {
        Set<T> resultSet = new LinkedHashSet<>();
        try {
            Field field = null;
            for (Object o : list) {
                if (field == null) {
                    field = getFieldByClasses(fieldName, o);
                }
                Object key = field.get(o);
                resultSet.add((T) key);
            }
            return resultSet;
        } catch (Exception e) {
            throw new RuntimeException("获取列值错误！" + e.getMessage());
        }
    }

    /**
     * 获取list中以某列为key，某列为value的map
     * @param list 原始list
     * @param keyFieldName key值的属性名
     * @param valueFieldName value值的属性名
     * @param <T>
     * @param <N>
     * @return
     */
    public static <T, N> Map<T, N> getMap(List list, String keyFieldName, String valueFieldName) {
        LinkedHashMap<T, N> resultMap = new LinkedHashMap<>();
        try {
            Field keyField = null;
            Field valueField = null;
            for (Object o : list) {
                if (keyField == null) {
                    keyField = getFieldByClasses(keyFieldName, o);
                }
                if (valueField == null) {
                    valueField = getFieldByClasses(valueFieldName, o);
                }
                Object key = keyField.get(o);
                Object value = valueField.get(o);
                resultMap.put((T)key, (N)value);
            }
            return resultMap;
        } catch (Exception e) {
            throw new RuntimeException("获取getMap错误！" + e.getMessage());
        }
    }

    /**
     * 获取以某列为key-原对象为value的map
     * @param list 原始list
     * @param fieldName key值的属性名
     * @param <T>
     * @return
     */
    public static <T> Map<String, T> getMap(List<T> list, String fieldName) {
        Map<String, T> map = new HashMap<>();
        try {
            Field field = null;
            for (T n : list) {
                if (field == null) {
                    field = getFieldByClasses(fieldName, n);
                }
                Object key = field.get(n);
                map.put(String.valueOf(key), n);
            }
            return map;
        } catch (Exception e) {
            throw new RuntimeException("列表转map失败！" + e.getMessage());
        }
    }

    /**
     * 获取以某列为key-原对象为value的map。保证添加顺序与遍历顺序一致
     * @param list 原始list
     * @param fieldName key值的属性名
     * @param <T>
     * @return
     */
    public static <T> Map<String, T> getSortMap(List<T> list, String fieldName) {
        Map<String, T> map = new LinkedHashMap<>();
        try {
            Field field = null;
            for (T n : list) {
                if (field == null) {
                    field = getFieldByClasses(fieldName, n);
                }
                Object key = field.get(n);
                map.put(String.valueOf(key), n);
            }
            return map;
        } catch (Exception e) {
            throw new RuntimeException("列表转LinkedHashMap失败！" + e.getMessage());
        }
    }

    /**
     * 获取对象中某属性的反射Field对象
     * @param fieldName 属性名称
     * @param object 对象
     * @return
     */
    public static Field getFieldByClasses(String fieldName, Object object) {
        Class<?> clazz = object.getClass();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field;
            } catch (Exception e) {
                //异常属于正确
            }
        }
        return null;
    }

    /**
     * 重组List，获取Map列表中，某一个值为key的Map
     * @param list 原始list
     * @param keyName Map中的key值，最后以map中key值对应的value为最终key
     * @param <T>
     * @param <N>
     * @return
     */
    public static <T, N> Map<String, Map<T, N>> getMap(List<Map<T, N>> list, T keyName) {
        Map<String, Map<T, N>> resultMap = new HashMap<>();
        try {
            for (Map<T, N> map : list) {
                Object key = map.get(keyName);
                resultMap.put(String.valueOf(key), map);
            }
            return resultMap;
        } catch (Exception e) {
            throw new RuntimeException("列表转map失败！" + e.getMessage());
        }
    }
}
