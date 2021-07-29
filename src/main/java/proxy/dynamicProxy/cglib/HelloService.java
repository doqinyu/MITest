package proxy.dynamicProxy.cglib;

public class HelloService {

    public HelloService () {
        System.out.println("HelloService构造");
    }

    final public String sayOthers(String name) {
        System.out.println("HelloService:sayOthers " + name);
        return null;
    }

    public void sayHello() {
        System.out.println("HelloService:sayHello");
    }
}
