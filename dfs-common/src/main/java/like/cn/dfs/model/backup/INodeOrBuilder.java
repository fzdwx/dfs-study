// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: backupnode.proto

package like.cn.dfs.model.backup;

public interface INodeOrBuilder extends
        // @@protoc_insertion_point(interface_extends:com.ruyuan.dfs.common.proto.INode)
        com.google.protobuf.MessageOrBuilder {

    /**
     * <code>string path = 1;</code>
     */
    java.lang.String getPath();

    /**
     * <code>string path = 1;</code>
     */
    com.google.protobuf.ByteString
    getPathBytes();

    /**
     * <code>int32 type = 2;</code>
     */
    int getType();

    /**
     * <code>repeated .com.ruyuan.dfs.common.proto.INode children = 3;</code>
     */
    java.util.List<like.cn.dfs.model.backup.INode>
    getChildrenList();

    /**
     * <code>repeated .com.ruyuan.dfs.common.proto.INode children = 3;</code>
     */
    like.cn.dfs.model.backup.INode getChildren(int index);

    /**
     * <code>repeated .com.ruyuan.dfs.common.proto.INode children = 3;</code>
     */
    int getChildrenCount();

    /**
     * <code>repeated .com.ruyuan.dfs.common.proto.INode children = 3;</code>
     */
    java.util.List<? extends like.cn.dfs.model.backup.INodeOrBuilder>
    getChildrenOrBuilderList();

    /**
     * <code>repeated .com.ruyuan.dfs.common.proto.INode children = 3;</code>
     */
    like.cn.dfs.model.backup.INodeOrBuilder getChildrenOrBuilder(
            int index);

    /**
     * <code>map&lt;string, string&gt; attr = 4;</code>
     */
    int getAttrCount();

    /**
     * <code>map&lt;string, string&gt; attr = 4;</code>
     */
    boolean containsAttr(
            java.lang.String key);

    /**
     * Use {@link #getAttrMap()} instead.
     */
    @java.lang.Deprecated
    java.util.Map<java.lang.String, java.lang.String>
    getAttr();

    /**
     * <code>map&lt;string, string&gt; attr = 4;</code>
     */
    java.util.Map<java.lang.String, java.lang.String>
    getAttrMap();

    /**
     * <code>map&lt;string, string&gt; attr = 4;</code>
     */

    java.lang.String getAttrOrDefault(
            java.lang.String key,
            java.lang.String defaultValue);

    /**
     * <code>map&lt;string, string&gt; attr = 4;</code>
     */

    java.lang.String getAttrOrThrow(
            java.lang.String key);
}