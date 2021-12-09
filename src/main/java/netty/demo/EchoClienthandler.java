package netty.demo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.demo.model.Header;
import netty.demo.model.Message;

import java.nio.charset.StandardCharsets;

public class EchoClienthandler extends ChannelInboundHandlerAdapter {

    //客户端连接服务器后调用
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel Active");
        String content = "hello world, this is netty client";
        Header header = new Header((byte)0, (byte)1, (byte)1, (byte)1,(byte)0, "713f17ca614361fb257dc6741332caf2", content.getBytes(StandardCharsets.UTF_8).length, 1);
        Message message1 = new Message(header, content);
        ctx.writeAndFlush(message1);
    }

    //接收到数据后调用
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
       Message message =  (Message) msg;
       System.out.println("EchoClienthandler channelRead: " + message.getData());
    }

    //完成时调用
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("EchoClienthandler channelReadComplete");
        ctx.flush();
    }

    //发生异常时调用
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("EchoClienthandler exceptionCaught");
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("EchoClienthandler channelRegistered");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("EchoClienthandler channelUnregistered");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("EchoClienthandler channelInactive");
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("EchoClienthandler handlerAdded");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("EchoClienthandler handlerRemoved");
    }
}
