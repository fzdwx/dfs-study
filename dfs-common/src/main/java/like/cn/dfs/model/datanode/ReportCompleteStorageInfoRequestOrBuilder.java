// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: datanode.proto

package like.cn.dfs.model.datanode;

public interface ReportCompleteStorageInfoRequestOrBuilder extends
        // @@protoc_insertion_point(interface_extends:com.ruyuan.dfs.common.proto.ReportCompleteStorageInfoRequest)
        com.google.protobuf.MessageOrBuilder {

    /**
     * <code>string hostname = 1;</code>
     */
    java.lang.String getHostname();

    /**
     * <code>string hostname = 1;</code>
     */
    com.google.protobuf.ByteString
    getHostnameBytes();

    /**
     * <code>repeated .com.ruyuan.dfs.common.proto.FileMetaInfo fileInfos = 2;</code>
     */
    java.util.List<like.cn.dfs.model.datanode.FileMetaInfo>
    getFileInfosList();

    /**
     * <code>repeated .com.ruyuan.dfs.common.proto.FileMetaInfo fileInfos = 2;</code>
     */
    like.cn.dfs.model.datanode.FileMetaInfo getFileInfos(int index);

    /**
     * <code>repeated .com.ruyuan.dfs.common.proto.FileMetaInfo fileInfos = 2;</code>
     */
    int getFileInfosCount();

    /**
     * <code>repeated .com.ruyuan.dfs.common.proto.FileMetaInfo fileInfos = 2;</code>
     */
    java.util.List<? extends like.cn.dfs.model.datanode.FileMetaInfoOrBuilder>
    getFileInfosOrBuilderList();

    /**
     * <code>repeated .com.ruyuan.dfs.common.proto.FileMetaInfo fileInfos = 2;</code>
     */
    like.cn.dfs.model.datanode.FileMetaInfoOrBuilder getFileInfosOrBuilder(
            int index);

    /**
     * <code>bool finished = 3;</code>
     */
    boolean getFinished();
}
