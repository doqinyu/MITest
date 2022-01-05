package netty.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import netty.demo.model.MessageDecoder;
import netty.demo.model.MessageEncoder;

public class EchoServer {
    static final int PORT = 8007;

    public static void main(String[] args) {
        //bossGroup是获取连接的，workerGroup 是用来处理连接的，这两个线程组都是死循环Epoll
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        //EpollEventLoopGroup bossGroup = new EpollEventLoopGroup();

        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //创建一个ServerBootstrap 实例用来配置启动服务器
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    //设置channel通道类型，如NioServerSocketChannel.class/OioServerSocketChannel.class
                    .channel(NioServerSocketChannel.class)
                    //ChannelInitializer:初始化连接的时候执行的回调
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            //添加自定义的解码器
                            pipeline.addLast(new MessageDecoder());
                            //添加自定义的编码器
                            pipeline.addLast(new MessageEncoder());
                            //添加自定义的服务处理器Handler
                            pipeline.addLast(new EchoServerHandler());

                        }
                    });

            ChannelFuture future = serverBootstrap.bind(PORT).sync();
            System.out.println("EchoServer ServerBoosatrap start");

            //等待直至 server socket 关闭
            future.channel().closeFuture().sync();
            System.out.println("EchoServer ServerBoosatrap end");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
