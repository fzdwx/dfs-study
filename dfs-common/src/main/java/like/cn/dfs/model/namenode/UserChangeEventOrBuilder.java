// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: namenode.proto

package like.cn.dfs.model.namenode;

public interface UserChangeEventOrBuilder extends
        // @@protoc_insertion_point(interface_extends:com.ruyuan.dfs.common.proto.UserChangeEvent)
        com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int32 eventType = 1;</code>
     */
    int getEventType();

    /**
     * <code>.com.ruyuan.dfs.common.proto.UserEntity userEntity = 2;</code>
     */
    boolean hasUserEntity();

    /**
     * <code>.com.ruyuan.dfs.common.proto.UserEntity userEntity = 2;</code>
     */
    like.cn.dfs.model.namenode.UserEntity getUserEntity();

    /**
     * <code>.com.ruyuan.dfs.common.proto.UserEntity userEntity = 2;</code>
     */
    like.cn.dfs.model.namenode.UserEntityOrBuilder getUserEntityOrBuilder();
}
