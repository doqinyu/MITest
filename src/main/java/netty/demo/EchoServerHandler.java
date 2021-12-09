package netty.demo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.demo.model.Header;
import netty.demo.model.Message;

import java.nio.charset.StandardCharsets;

/**
 * Channel: 客户端和服务端建立的一个连接通道
 * ChannelHandler： 负责Channel的逻辑处理,ChannelInboundHandler/ChannelOutboundHandler
 * ChannelPipeline: 负责管理ChannelHandler的有序容器
 *
 * 一个Channel包含一个ChannelPipeline，所有ChannelHandler都会顺序加入到ChannelPipeline中
 * 创建Channel时会自动创建一个ChannelPipeline，每个Channel都有一个管理它的pipeline，这关联是永久性的
 *
 * Channel当状态出现变化，就会触发对应的事件:
 * handlerAdded -> channelRegistered -> channelActive -> channelRead -> channelReadComplete -> channelInactive -> channelUnregistered -> handlerRemoved
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取客户端的请求，并且向客户端返回响应的一个方法
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message =  (Message) msg;

        System.out.println("EchoServerHandler channelRead: " + message.getData());

        //此处写接收到客户端请求后的业务逻辑
        String content = "Hello world, this is nettyServer";
        Header header = new Header((byte)0, (byte)1, (byte)1, (byte)1,(byte)0, "713f17ca614361fb257dc6741332caf2", content.getBytes(StandardCharsets.UTF_8).length, 1);
        Message message1 = new Message(header, content);
        ctx.writeAndFlush(message1);
    }

    /**
     * channel注册到一个EventLoop
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("EchoServerHandler channelRegistered");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("EchoServerHandler channelUnregistered");
    }

    /**
     * channel 变为活跃状态（连接到了远程主机），可以接受和发送数据
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("EchoServerHandler channelActive");
    }

    /**
     * channel处于非活跃状态，没有连接到远程主机
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("EchoServerHandler channelInactive");
    }

    /**
     * 当 ChannelHandler 添加到 ChannelPipeline 调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("EchoServerHandler handlerAdded");
    }

    /**
     * 当 ChannelHandler 从 ChannelPipeline 移除时调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("EchoServerHandler handlerRemoved");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("EchoServerHandler channelReadComplete");
    }

    /**
     * 执行抛出异常时调用
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("EchoServerHandler exceptionCaught");
        cause.printStackTrace();
        ctx.close();
    }
}
