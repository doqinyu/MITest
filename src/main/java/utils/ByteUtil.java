package utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ByteUtil {

    public static byte[] objectToBytes(Object obj) {
        try {
            if (null == obj) {
                return null;
            }
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            byte[] bytes = bos.toByteArray();

            bos.close();
            oos.close();

            return bytes;

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public static <T> T bytesToObject(byte[] bytes, Class<T> type) {
        T result = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);

            Object o = ois.readObject();
            if (o != null && type.isInstance(o)) {
                result = type.cast(o);
            }
            ois.close();
            bis.close();

        } catch (Exception e) {

        }
        return result;
    }
}
