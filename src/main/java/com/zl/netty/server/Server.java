package com.zl.netty.server;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * netty服务端入门
 *
 * @author jacky
 */
public class Server {

    public static void main(String[] args) {

        //服务类
        ServerBootstrap bootstrap = new ServerBootstrap();

        //boss线程监听端口，worker线程负责数据读写
        ExecutorService boss = Executors.newCachedThreadPool();
        ExecutorService worker = Executors.newCachedThreadPool();

        //设置niosocket工厂
        bootstrap.setFactory(new NioServerSocketChannelFactory(boss, worker));

        //设置管道的工厂
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {

            @Override
            public ChannelPipeline getPipeline() throws Exception {

                // 获取管道
                ChannelPipeline pipeline = Channels.pipeline();

                // 添加了这些编码，就可以直接在接收消息的时候，类型转换了。StringDecoder是一个上行handler
                pipeline.addLast("decoder", new StringDecoder());

                // 在消息回写的时候，需要使用这个类,就可以直接回写字符串了，不需要channelBuffer。StringEncoder是一个下行的handler
                pipeline.addLast("encoder", new StringEncoder());

                pipeline.addLast("helloHandler", new HelloHandler());
                return pipeline;
            }
        });

        bootstrap.bind(new InetSocketAddress(10101));

        System.out.println("start!!!");

    }

}
