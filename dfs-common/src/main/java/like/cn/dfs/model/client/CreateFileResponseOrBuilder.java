// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: dfs-client.proto

package like.cn.dfs.model.client;

public interface CreateFileResponseOrBuilder extends
        // @@protoc_insertion_point(interface_extends:com.ruyuan.dfs.common.proto.CreateFileResponse)
        com.google.protobuf.MessageOrBuilder {

    /**
     * <code>repeated .com.ruyuan.dfs.common.proto.DataNode dataNodes = 1;</code>
     */
    java.util.List<like.cn.dfs.model.common.DataNode>
    getDataNodesList();

    /**
     * <code>repeated .com.ruyuan.dfs.common.proto.DataNode dataNodes = 1;</code>
     */
    like.cn.dfs.model.common.DataNode getDataNodes(int index);

    /**
     * <code>repeated .com.ruyuan.dfs.common.proto.DataNode dataNodes = 1;</code>
     */
    int getDataNodesCount();

    /**
     * <code>repeated .com.ruyuan.dfs.common.proto.DataNode dataNodes = 1;</code>
     */
    java.util.List<? extends like.cn.dfs.model.common.DataNodeOrBuilder>
    getDataNodesOrBuilderList();

    /**
     * <code>repeated .com.ruyuan.dfs.common.proto.DataNode dataNodes = 1;</code>
     */
    like.cn.dfs.model.common.DataNodeOrBuilder getDataNodesOrBuilder(
            int index);

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