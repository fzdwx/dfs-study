// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: namenode.proto

package like.cn.dfs.model.namenode;

public interface UserStorageEntityOrBuilder extends
        // @@protoc_insertion_point(interface_extends:com.ruyuan.dfs.common.proto.UserStorageEntity)
        com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int64 storageSize = 1;</code>
     */
    long getStorageSize();

    /**
     * <code>int32 fileCount = 2;</code>
     */
    int getFileCount();

    /**
     * <code>repeated string dataNodes = 3;</code>
     */
    java.util.List<java.lang.String>
    getDataNodesList();

    /**
     * <code>repeated string dataNodes = 3;</code>
     */
    int getDataNodesCount();

    /**
     * <code>repeated string dataNodes = 3;</code>
     */
    java.lang.String getDataNodes(int index);

    /**
     * <code>repeated string dataNodes = 3;</code>
     */
    com.google.protobuf.ByteString
    getDataNodesBytes(int index);
}
