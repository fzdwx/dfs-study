// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: dfs-client.proto

package like.cn.dfs.model.client;

public interface GetDataNodeForFileResponseOrBuilder extends
        // @@protoc_insertion_point(interface_extends:com.ruyuan.dfs.common.proto.GetDataNodeForFileResponse)
        com.google.protobuf.MessageOrBuilder {

    /**
     * <code>.com.ruyuan.dfs.common.proto.DataNode dataNode = 1;</code>
     */
    boolean hasDataNode();

    /**
     * <code>.com.ruyuan.dfs.common.proto.DataNode dataNode = 1;</code>
     */
    like.cn.dfs.model.common.DataNode getDataNode();

    /**
     * <code>.com.ruyuan.dfs.common.proto.DataNode dataNode = 1;</code>
     */
    like.cn.dfs.model.common.DataNodeOrBuilder getDataNodeOrBuilder();

    /**
     * <code>string realFileName = 2;</code>
     */
    java.lang.String getRealFileName();

    /**
     * <code>string realFileName = 2;</code>
     */
    com.google.protobuf.ByteString
    getRealFileNameBytes();
}
