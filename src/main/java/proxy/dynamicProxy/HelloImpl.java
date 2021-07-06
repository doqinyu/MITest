package proxy.dynamicProxy;

public class HelloImpl implements IHello {

    @Override
    public void sayHello() {
        System.out.println("Hello world!");
    }

    @Override
    public void eat() {
        System.out.println("eat...");
    }
}
