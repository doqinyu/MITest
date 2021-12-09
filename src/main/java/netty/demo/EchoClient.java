package netty.demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import netty.demo.model.MessageDecoder;
import netty.demo.model.MessageEncoder;

public class EchoClient {
    static final String HOST = "127.0.0.1";
    static final int PORT = 8007;
    static final int SIZe = 256;

    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new MessageDecoder());
                            pipeline.addLast(new MessageEncoder());
                            pipeline.addLast(new EchoClienthandler());

                        }
                    });

            ChannelFuture future = bootstrap.connect(HOST, PORT).sync();
            System.out.println("EchoClient bootstrap 配置启动完成");

            future.channel().closeFuture().sync();
            System.out.println("EchoClient end");

        } catch (Exception e) {
            System.out.println(e);

        } finally {
            group.shutdownGracefully();
        }

    }
}
