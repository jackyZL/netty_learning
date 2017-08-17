package com.zl.netty.server;

import org.jboss.netty.channel.*;

/**
 * 消息接受处理类
 *
 * @author jacky
 */
public class HelloHandler extends SimpleChannelHandler {

    /**
     * 接收消息
     */
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {

        System.out.println("messageReceived");

        //int i = 1 / 0;

        //ChannelBuffer channelBuffer = (ChannelBuffer) e.getMessage();
        //String content = new String(channelBuffer.array());

        String s = (String) e.getMessage();   // 在服务端添加了 pipeline.addLast("decoder", new StringDecoder());就可以直接在接收消息的时候，类型转换了，不需要通过ChannelBuffer转换

        System.out.println(s);

        // 回写
        //ChannelBuffer copiedBuffer = ChannelBuffers.copiedBuffer("hi".getBytes());
        //ctx.getChannel().write(copiedBuffer);

        //回写数据
        ctx.getChannel().write("hi"); // pipeline.addLast("encoder", new StringEncoder()); // 在消息回写的时候，需要使用StringEncoder这个类,就可以直接回写字符串了，不需要channelBuffer


        // 这里是可以检测的，例如查看此用户每秒请求的次数，通过ctx.getChannel().close()是可以关闭此通道的

        super.messageReceived(ctx, e);
    }

    /**
     * 捕获异常, 例如在messageReceived的时候抛异常可以触发
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        System.out.println("exceptionCaught");
        super.exceptionCaught(ctx, e);
    }

    /**
     * 新连接  通常用来检测IP是否是黑名单
     */
    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("channelConnected");
        super.channelConnected(ctx, e);
    }

    /**
     * 必须是链接已经建立，关闭通道的时候才会触发.  可以在用户断线的时候清楚用户的缓存数据等
     */
    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {

        System.out.println("channelDisconnected");
        super.channelDisconnected(ctx, e);
    }

    /**
     * channel关闭的时候触发
     */
    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("channelClosed");
        super.channelClosed(ctx, e);
    }
}
