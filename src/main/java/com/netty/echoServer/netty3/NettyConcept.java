package com.netty.echoServer.netty3;



/**
    central concept
    SocketChannel contains a ChannelPipeline

    每一个Netty SocketChannel 包含着一个 ChannelPipeline
    每一个ChannelPipeline包含一列ChannelHandler实例 (可以有多个)

    Codecs
    Netty has the concept of codecs
    (encoders + decoders).
    A Netty codec converts bytes to message objects (Java objects),
    or message objects to bytes. For instance, a codec might convert
    the raw bytes of an incoming HTTP request to an HTTP object,
    or convert an HTTP response object back to raw bytes.
 */
public class NettyConcept {

}

