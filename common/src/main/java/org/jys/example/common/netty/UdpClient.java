package org.jys.example.common.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;

/**
 * @author YueSong Jiang
 * @date 2019/3/13
 * Udp client example use netty
 */
public class UdpClient {

    public void start() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioDatagramChannel.class)
                .handler(new ChannelInitializer<NioDatagramChannel>() {

                    @Override
                    protected void initChannel(NioDatagramChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast("decoder", new ByteArrayDecoder());
                        pipeline.addLast("encoder", new ByteArrayEncoder());
                    }
                });
        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8086).sync();
        if (channelFuture.isSuccess()) {
            System.out.println("connect success");
        }
        for (int i = 0; i < 200; i++) {
            channelFuture.channel().writeAndFlush("test");
        }
    }
}
