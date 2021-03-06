// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: namenode.proto

package like.cn.dfs.model.namenode;

/**
 * Protobuf type {@code com.ruyuan.dfs.common.proto.RebalanceSlotsRequest}
 */
public final class RebalanceSlotsRequest extends
        com.google.protobuf.GeneratedMessageV3 implements
        // @@protoc_insertion_point(message_implements:com.ruyuan.dfs.common.proto.RebalanceSlotsRequest)
        RebalanceSlotsRequestOrBuilder {
  public static final int NAMENODEID_FIELD_NUMBER = 1;
  private static final long serialVersionUID = 0L;
  // @@protoc_insertion_point(class_scope:com.ruyuan.dfs.common.proto.RebalanceSlotsRequest)
  private static final like.cn.dfs.model.namenode.RebalanceSlotsRequest DEFAULT_INSTANCE;
  private static final com.google.protobuf.Parser<RebalanceSlotsRequest>
          PARSER = new com.google.protobuf.AbstractParser<RebalanceSlotsRequest>() {
    public RebalanceSlotsRequest parsePartialFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return new RebalanceSlotsRequest(input, extensionRegistry);
    }
  };

  static {
    DEFAULT_INSTANCE = new like.cn.dfs.model.namenode.RebalanceSlotsRequest();
  }

  private int nameNodeId_;
  private byte memoizedIsInitialized = -1;

  // Use RebalanceSlotsRequest.newBuilder() to construct.
  private RebalanceSlotsRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }

  private RebalanceSlotsRequest() {
    nameNodeId_ = 0;
  }

  private RebalanceSlotsRequest(
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
          case 8: {

            nameNodeId_ = input.readInt32();
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

  public static like.cn.dfs.model.namenode.RebalanceSlotsRequest parseFrom(
          java.nio.ByteBuffer data)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static like.cn.dfs.model.namenode.RebalanceSlotsRequest parseFrom(
          java.nio.ByteBuffer data,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static like.cn.dfs.model.namenode.RebalanceSlotsRequest parseFrom(
          com.google.protobuf.ByteString data)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static like.cn.dfs.model.namenode.RebalanceSlotsRequest parseFrom(
          com.google.protobuf.ByteString data,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static like.cn.dfs.model.namenode.RebalanceSlotsRequest parseFrom(byte[] data)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static like.cn.dfs.model.namenode.RebalanceSlotsRequest parseFrom(
          byte[] data,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static like.cn.dfs.model.namenode.RebalanceSlotsRequest parseFrom(java.io.InputStream input)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
            .parseWithIOException(PARSER, input);
  }

  public static like.cn.dfs.model.namenode.RebalanceSlotsRequest parseFrom(
          java.io.InputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
            .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static like.cn.dfs.model.namenode.RebalanceSlotsRequest parseDelimitedFrom(java.io.InputStream input)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
            .parseDelimitedWithIOException(PARSER, input);
  }

  public static like.cn.dfs.model.namenode.RebalanceSlotsRequest parseDelimitedFrom(
          java.io.InputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
            .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }

  public static like.cn.dfs.model.namenode.RebalanceSlotsRequest parseFrom(
          com.google.protobuf.CodedInputStream input)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
            .parseWithIOException(PARSER, input);
  }

  public static like.cn.dfs.model.namenode.RebalanceSlotsRequest parseFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
            .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }

  public static Builder newBuilder(like.cn.dfs.model.namenode.RebalanceSlotsRequest prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }

  public static like.cn.dfs.model.namenode.RebalanceSlotsRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  public static com.google.protobuf.Parser<RebalanceSlotsRequest> parser() {
    return PARSER;
  }

  public static final com.google.protobuf.Descriptors.Descriptor
  getDescriptor() {
    return like.cn.dfs.model.namenode.Namenode.internal_static_com_ruyuan_dfs_common_proto_RebalanceSlotsRequest_descriptor;
  }

  /**
   * <code>int32 nameNodeId = 1;</code>
   */
  public int getNameNodeId() {
    return nameNodeId_;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof like.cn.dfs.model.namenode.RebalanceSlotsRequest)) {
      return super.equals(obj);
    }
    like.cn.dfs.model.namenode.RebalanceSlotsRequest other = ( like.cn.dfs.model.namenode.RebalanceSlotsRequest ) obj;

    boolean result = true;
    result = result && (getNameNodeId()
            == other.getNameNodeId());
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + NAMENODEID_FIELD_NUMBER;
    hash = (53 * hash) + getNameNodeId();
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
  public com.google.protobuf.Parser<RebalanceSlotsRequest> getParserForType() {
    return PARSER;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
  internalGetFieldAccessorTable() {
    return like.cn.dfs.model.namenode.Namenode.internal_static_com_ruyuan_dfs_common_proto_RebalanceSlotsRequest_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                    like.cn.dfs.model.namenode.RebalanceSlotsRequest.class, like.cn.dfs.model.namenode.RebalanceSlotsRequest.Builder.class);
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
    if (nameNodeId_ != 0) {
      output.writeInt32(1, nameNodeId_);
    }
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (nameNodeId_ != 0) {
      size += com.google.protobuf.CodedOutputStream
              .computeInt32Size(1, nameNodeId_);
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

  public like.cn.dfs.model.namenode.RebalanceSlotsRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

  /**
   * Protobuf type {@code com.ruyuan.dfs.common.proto.RebalanceSlotsRequest}
   */
  public static final class Builder extends
          com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
          // @@protoc_insertion_point(builder_implements:com.ruyuan.dfs.common.proto.RebalanceSlotsRequest)
          like.cn.dfs.model.namenode.RebalanceSlotsRequestOrBuilder {
    private int nameNodeId_;

    // Construct using like.cn.dfs.model.namenode.RebalanceSlotsRequest.newBuilder()
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
      return like.cn.dfs.model.namenode.Namenode.internal_static_com_ruyuan_dfs_common_proto_RebalanceSlotsRequest_descriptor;
    }

    public like.cn.dfs.model.namenode.RebalanceSlotsRequest getDefaultInstanceForType() {
      return like.cn.dfs.model.namenode.RebalanceSlotsRequest.getDefaultInstance();
    }

    public like.cn.dfs.model.namenode.RebalanceSlotsRequest build() {
      like.cn.dfs.model.namenode.RebalanceSlotsRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public like.cn.dfs.model.namenode.RebalanceSlotsRequest buildPartial() {
      like.cn.dfs.model.namenode.RebalanceSlotsRequest result = new like.cn.dfs.model.namenode.RebalanceSlotsRequest(this);
      result.nameNodeId_ = nameNodeId_;
      onBuilt();
      return result;
    }

    public Builder clone() {
      return super.clone();
    }

    public Builder clear() {
      super.clear();
      nameNodeId_ = 0;

      return this;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
    internalGetFieldAccessorTable() {
      return like.cn.dfs.model.namenode.Namenode.internal_static_com_ruyuan_dfs_common_proto_RebalanceSlotsRequest_fieldAccessorTable
              .ensureFieldAccessorsInitialized(
                      like.cn.dfs.model.namenode.RebalanceSlotsRequest.class, like.cn.dfs.model.namenode.RebalanceSlotsRequest.Builder.class);
    }

    public com.google.protobuf.Descriptors.Descriptor
    getDescriptorForType() {
      return like.cn.dfs.model.namenode.Namenode.internal_static_com_ruyuan_dfs_common_proto_RebalanceSlotsRequest_descriptor;
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
      if (other instanceof like.cn.dfs.model.namenode.RebalanceSlotsRequest) {
        return mergeFrom(( like.cn.dfs.model.namenode.RebalanceSlotsRequest ) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      like.cn.dfs.model.namenode.RebalanceSlotsRequest parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = ( like.cn.dfs.model.namenode.RebalanceSlotsRequest ) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    public Builder mergeFrom(like.cn.dfs.model.namenode.RebalanceSlotsRequest other) {
      if (other == like.cn.dfs.model.namenode.RebalanceSlotsRequest.getDefaultInstance()) return this;
      if (other.getNameNodeId() != 0) {
        setNameNodeId(other.getNameNodeId());
      }
      onChanged();
      return this;
    }

    /**
     * <code>int32 nameNodeId = 1;</code>
     */
    public int getNameNodeId() {
      return nameNodeId_;
    }

    /**
     * <code>int32 nameNodeId = 1;</code>
     */
    public Builder setNameNodeId(int value) {

      nameNodeId_ = value;
      onChanged();
      return this;
    }

    /**
     * <code>int32 nameNodeId = 1;</code>
     */
    public Builder clearNameNodeId() {

      nameNodeId_ = 0;
      onChanged();
      return this;
    }

    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }


    // @@protoc_insertion_point(builder_scope:com.ruyuan.dfs.common.proto.RebalanceSlotsRequest)
  }

}
