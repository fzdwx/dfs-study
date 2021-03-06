// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: backupnode.proto

package like.cn.dfs.model.backup;

/**
 * Protobuf type {@code com.ruyuan.dfs.common.proto.BackupNodeInfo}
 */
public final class BackupNodeInfo extends
        com.google.protobuf.GeneratedMessageV3 implements
        // @@protoc_insertion_point(message_implements:com.ruyuan.dfs.common.proto.BackupNodeInfo)
        BackupNodeInfoOrBuilder {
  public static final int HOSTNAME_FIELD_NUMBER = 1;
  public static final int PORT_FIELD_NUMBER = 2;
  private static final long serialVersionUID = 0L;
  // @@protoc_insertion_point(class_scope:com.ruyuan.dfs.common.proto.BackupNodeInfo)
  private static final like.cn.dfs.model.backup.BackupNodeInfo DEFAULT_INSTANCE;
  private static final com.google.protobuf.Parser<BackupNodeInfo>
          PARSER = new com.google.protobuf.AbstractParser<BackupNodeInfo>() {
    public BackupNodeInfo parsePartialFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return new BackupNodeInfo(input, extensionRegistry);
    }
  };

  static {
    DEFAULT_INSTANCE = new like.cn.dfs.model.backup.BackupNodeInfo();
  }

  private volatile java.lang.Object hostname_;
  private int port_;
  private byte memoizedIsInitialized = -1;

  // Use BackupNodeInfo.newBuilder() to construct.
  private BackupNodeInfo(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }

  private BackupNodeInfo() {
    hostname_ = "";
    port_ = 0;
  }

  private BackupNodeInfo(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    int mutable_bitField0_ = 0;
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          default: {
            if (!input.skipField(tag)) {
              done = true;
            }
            break;
          }
          case 10: {
            java.lang.String s = input.readStringRequireUtf8();

            hostname_ = s;
            break;
          }
          case 16: {

            port_ = input.readInt32();
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
              e).setUnfinishedMessage(this);
    } finally {
      makeExtensionsImmutable();
    }
  }

  public static like.cn.dfs.model.backup.BackupNodeInfo parseFrom(
          java.nio.ByteBuffer data)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static like.cn.dfs.model.backup.BackupNodeInfo parseFrom(
          java.nio.ByteBuffer data,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static like.cn.dfs.model.backup.BackupNodeInfo parseFrom(
          com.google.protobuf.ByteString data)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static like.cn.dfs.model.backup.BackupNodeInfo parseFrom(
          com.google.protobuf.ByteString data,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static like.cn.dfs.model.backup.BackupNodeInfo parseFrom(byte[] data)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static like.cn.dfs.model.backup.BackupNodeInfo parseFrom(
          byte[] data,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static like.cn.dfs.model.backup.BackupNodeInfo parseFrom(java.io.InputStream input)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
            .parseWithIOException(PARSER, input);
  }

  public static like.cn.dfs.model.backup.BackupNodeInfo parseFrom(
          java.io.InputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
            .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static like.cn.dfs.model.backup.BackupNodeInfo parseDelimitedFrom(java.io.InputStream input)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
            .parseDelimitedWithIOException(PARSER, input);
  }

  public static like.cn.dfs.model.backup.BackupNodeInfo parseDelimitedFrom(
          java.io.InputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
            .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }

  public static like.cn.dfs.model.backup.BackupNodeInfo parseFrom(
          com.google.protobuf.CodedInputStream input)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
            .parseWithIOException(PARSER, input);
  }

  public static like.cn.dfs.model.backup.BackupNodeInfo parseFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
            .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }

  public static Builder newBuilder(like.cn.dfs.model.backup.BackupNodeInfo prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }

  public static like.cn.dfs.model.backup.BackupNodeInfo getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  public static com.google.protobuf.Parser<BackupNodeInfo> parser() {
    return PARSER;
  }

  public static final com.google.protobuf.Descriptors.Descriptor
  getDescriptor() {
    return like.cn.dfs.model.backup.Backupnode.internal_static_com_ruyuan_dfs_common_proto_BackupNodeInfo_descriptor;
  }

  /**
   * <code>string hostname = 1;</code>
   */
  public java.lang.String getHostname() {
    java.lang.Object ref = hostname_;
    if (ref instanceof java.lang.String) {
      return ( java.lang.String ) ref;
    } else {
      com.google.protobuf.ByteString bs =
              ( com.google.protobuf.ByteString ) ref;
      java.lang.String s = bs.toStringUtf8();
      hostname_ = s;
      return s;
    }
  }

  /**
   * <code>string hostname = 1;</code>
   */
  public com.google.protobuf.ByteString
  getHostnameBytes() {
    java.lang.Object ref = hostname_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8(
                      ( java.lang.String ) ref);
      hostname_ = b;
      return b;
    } else {
      return ( com.google.protobuf.ByteString ) ref;
    }
  }

  /**
   * <code>int32 port = 2;</code>
   */
  public int getPort() {
    return port_;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof like.cn.dfs.model.backup.BackupNodeInfo)) {
      return super.equals(obj);
    }
    like.cn.dfs.model.backup.BackupNodeInfo other = ( like.cn.dfs.model.backup.BackupNodeInfo ) obj;

    boolean result = true;
    result = result && getHostname()
            .equals(other.getHostname());
    result = result && (getPort()
            == other.getPort());
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + HOSTNAME_FIELD_NUMBER;
    hash = (53 * hash) + getHostname().hashCode();
    hash = (37 * hash) + PORT_FIELD_NUMBER;
    hash = (53 * hash) + getPort();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public Builder newBuilderForType() {return newBuilder();}

  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
            ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  public com.google.protobuf.Parser<BackupNodeInfo> getParserForType() {
    return PARSER;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
  internalGetFieldAccessorTable() {
    return like.cn.dfs.model.backup.Backupnode.internal_static_com_ruyuan_dfs_common_proto_BackupNodeInfo_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                    like.cn.dfs.model.backup.BackupNodeInfo.class, like.cn.dfs.model.backup.BackupNodeInfo.Builder.class);
  }

  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
          throws java.io.IOException {
    if (!getHostnameBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, hostname_);
    }
    if (port_ != 0) {
      output.writeInt32(2, port_);
    }
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!getHostnameBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, hostname_);
    }
    if (port_ != 0) {
      size += com.google.protobuf.CodedOutputStream
              .computeInt32Size(2, port_);
    }
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  protected Builder newBuilderForType(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }

  public like.cn.dfs.model.backup.BackupNodeInfo getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

  /**
   * Protobuf type {@code com.ruyuan.dfs.common.proto.BackupNodeInfo}
   */
  public static final class Builder extends
          com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
          // @@protoc_insertion_point(builder_implements:com.ruyuan.dfs.common.proto.BackupNodeInfo)
          like.cn.dfs.model.backup.BackupNodeInfoOrBuilder {
    private java.lang.Object hostname_ = "";
    private int port_;

    // Construct using like.cn.dfs.model.backup.BackupNodeInfo.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
            com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }

    public static final com.google.protobuf.Descriptors.Descriptor
    getDescriptor() {
      return like.cn.dfs.model.backup.Backupnode.internal_static_com_ruyuan_dfs_common_proto_BackupNodeInfo_descriptor;
    }

    public like.cn.dfs.model.backup.BackupNodeInfo getDefaultInstanceForType() {
      return like.cn.dfs.model.backup.BackupNodeInfo.getDefaultInstance();
    }

    public like.cn.dfs.model.backup.BackupNodeInfo build() {
      like.cn.dfs.model.backup.BackupNodeInfo result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public like.cn.dfs.model.backup.BackupNodeInfo buildPartial() {
      like.cn.dfs.model.backup.BackupNodeInfo result = new like.cn.dfs.model.backup.BackupNodeInfo(this);
      result.hostname_ = hostname_;
      result.port_ = port_;
      onBuilt();
      return result;
    }

    public Builder clone() {
      return super.clone();
    }

    public Builder clear() {
      super.clear();
      hostname_ = "";

      port_ = 0;

      return this;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
    internalGetFieldAccessorTable() {
      return like.cn.dfs.model.backup.Backupnode.internal_static_com_ruyuan_dfs_common_proto_BackupNodeInfo_fieldAccessorTable
              .ensureFieldAccessorsInitialized(
                      like.cn.dfs.model.backup.BackupNodeInfo.class, like.cn.dfs.model.backup.BackupNodeInfo.Builder.class);
    }

    public com.google.protobuf.Descriptors.Descriptor
    getDescriptorForType() {
      return like.cn.dfs.model.backup.Backupnode.internal_static_com_ruyuan_dfs_common_proto_BackupNodeInfo_descriptor;
    }

    public Builder setField(
            com.google.protobuf.Descriptors.FieldDescriptor field,
            Object value) {
      return super.setField(field, value);
    }

    public Builder clearField(
            com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }

    public Builder clearOneof(
            com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }

    public Builder setRepeatedField(
            com.google.protobuf.Descriptors.FieldDescriptor field,
            int index, Object value) {
      return super.setRepeatedField(field, index, value);
    }

    public Builder addRepeatedField(
            com.google.protobuf.Descriptors.FieldDescriptor field,
            Object value) {
      return super.addRepeatedField(field, value);
    }

    public final Builder setUnknownFields(
            final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }

    public final Builder mergeUnknownFields(
            final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }

    public final boolean isInitialized() {
      return true;
    }

    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof like.cn.dfs.model.backup.BackupNodeInfo) {
        return mergeFrom(( like.cn.dfs.model.backup.BackupNodeInfo ) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      like.cn.dfs.model.backup.BackupNodeInfo parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = ( like.cn.dfs.model.backup.BackupNodeInfo ) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    public Builder mergeFrom(like.cn.dfs.model.backup.BackupNodeInfo other) {
      if (other == like.cn.dfs.model.backup.BackupNodeInfo.getDefaultInstance()) return this;
      if (!other.getHostname().isEmpty()) {
        hostname_ = other.hostname_;
        onChanged();
      }
      if (other.getPort() != 0) {
        setPort(other.getPort());
      }
      onChanged();
      return this;
    }

    /**
     * <code>string hostname = 1;</code>
     */
    public java.lang.String getHostname() {
      java.lang.Object ref = hostname_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
                ( com.google.protobuf.ByteString ) ref;
        java.lang.String s = bs.toStringUtf8();
        hostname_ = s;
        return s;
      } else {
        return ( java.lang.String ) ref;
      }
    }

    /**
     * <code>string hostname = 1;</code>
     */
    public com.google.protobuf.ByteString
    getHostnameBytes() {
      java.lang.Object ref = hostname_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
                com.google.protobuf.ByteString.copyFromUtf8(
                        ( java.lang.String ) ref);
        hostname_ = b;
        return b;
      } else {
        return ( com.google.protobuf.ByteString ) ref;
      }
    }

    /**
     * <code>string hostname = 1;</code>
     */
    public Builder setHostnameBytes(
            com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);

      hostname_ = value;
      onChanged();
      return this;
    }

    /**
     * <code>int32 port = 2;</code>
     */
    public int getPort() {
      return port_;
    }

    /**
     * <code>int32 port = 2;</code>
     */
    public Builder setPort(int value) {

      port_ = value;
      onChanged();
      return this;
    }

    /**
     * <code>string hostname = 1;</code>
     */
    public Builder setHostname(
            java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }

      hostname_ = value;
      onChanged();
      return this;
    }

    /**
     * <code>string hostname = 1;</code>
     */
    public Builder clearHostname() {

      hostname_ = getDefaultInstance().getHostname();
      onChanged();
      return this;
    }

    /**
     * <code>int32 port = 2;</code>
     */
    public Builder clearPort() {

      port_ = 0;
      onChanged();
      return this;
    }

    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }


    // @@protoc_insertion_point(builder_scope:com.ruyuan.dfs.common.proto.BackupNodeInfo)
  }

}
