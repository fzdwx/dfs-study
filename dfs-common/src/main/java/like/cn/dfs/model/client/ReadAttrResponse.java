// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: dfs-client.proto

package like.cn.dfs.model.client;

/**
 * Protobuf type {@code com.ruyuan.dfs.common.proto.ReadAttrResponse}
 */
public final class ReadAttrResponse extends
        com.google.protobuf.GeneratedMessageV3 implements
        // @@protoc_insertion_point(message_implements:com.ruyuan.dfs.common.proto.ReadAttrResponse)
        ReadAttrResponseOrBuilder {
  public static final int ATTR_FIELD_NUMBER = 1;
  private static final long serialVersionUID = 0L;
  // @@protoc_insertion_point(class_scope:com.ruyuan.dfs.common.proto.ReadAttrResponse)
  private static final like.cn.dfs.model.client.ReadAttrResponse DEFAULT_INSTANCE;
  private static final com.google.protobuf.Parser<ReadAttrResponse>
          PARSER = new com.google.protobuf.AbstractParser<ReadAttrResponse>() {
    public ReadAttrResponse parsePartialFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return new ReadAttrResponse(input, extensionRegistry);
    }
  };

  static {
    DEFAULT_INSTANCE = new like.cn.dfs.model.client.ReadAttrResponse();
  }

  private com.google.protobuf.MapField<
          java.lang.String, java.lang.String> attr_;
  private byte memoizedIsInitialized = -1;

  // Use ReadAttrResponse.newBuilder() to construct.
  private ReadAttrResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }

  private ReadAttrResponse() {
  }

  private ReadAttrResponse(
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
            if (!((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
              attr_ = com.google.protobuf.MapField.newMapField(
                      AttrDefaultEntryHolder.defaultEntry);
              mutable_bitField0_ |= 0x00000001;
            }
            com.google.protobuf.MapEntry<java.lang.String, java.lang.String>
                    attr__ = input.readMessage(
                    AttrDefaultEntryHolder.defaultEntry.getParserForType(), extensionRegistry);
            attr_.getMutableMap().put(
                    attr__.getKey(), attr__.getValue());
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

  public static like.cn.dfs.model.client.ReadAttrResponse parseFrom(
          java.nio.ByteBuffer data)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static like.cn.dfs.model.client.ReadAttrResponse parseFrom(
          java.nio.ByteBuffer data,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static like.cn.dfs.model.client.ReadAttrResponse parseFrom(
          com.google.protobuf.ByteString data)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static like.cn.dfs.model.client.ReadAttrResponse parseFrom(
          com.google.protobuf.ByteString data,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static like.cn.dfs.model.client.ReadAttrResponse parseFrom(byte[] data)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static like.cn.dfs.model.client.ReadAttrResponse parseFrom(
          byte[] data,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static like.cn.dfs.model.client.ReadAttrResponse parseFrom(java.io.InputStream input)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
            .parseWithIOException(PARSER, input);
  }

  public static like.cn.dfs.model.client.ReadAttrResponse parseFrom(
          java.io.InputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
            .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static like.cn.dfs.model.client.ReadAttrResponse parseDelimitedFrom(java.io.InputStream input)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
            .parseDelimitedWithIOException(PARSER, input);
  }

  public static like.cn.dfs.model.client.ReadAttrResponse parseDelimitedFrom(
          java.io.InputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
            .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }

  public static like.cn.dfs.model.client.ReadAttrResponse parseFrom(
          com.google.protobuf.CodedInputStream input)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
            .parseWithIOException(PARSER, input);
  }

  public static like.cn.dfs.model.client.ReadAttrResponse parseFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
            .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }

  public static Builder newBuilder(like.cn.dfs.model.client.ReadAttrResponse prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }

  public static like.cn.dfs.model.client.ReadAttrResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  public static com.google.protobuf.Parser<ReadAttrResponse> parser() {
    return PARSER;
  }

  public static final com.google.protobuf.Descriptors.Descriptor
  getDescriptor() {
    return like.cn.dfs.model.client.DfsClient.internal_static_com_ruyuan_dfs_common_proto_ReadAttrResponse_descriptor;
  }

  public int getAttrCount() {
    return internalGetAttr().getMap().size();
  }

  /**
   * <code>map&lt;string, string&gt; attr = 1;</code>
   */

  public boolean containsAttr(
          java.lang.String key) {
    if (key == null) {throw new java.lang.NullPointerException();}
    return internalGetAttr().getMap().containsKey(key);
  }

  /**
   * Use {@link #getAttrMap()} instead.
   */
  @java.lang.Deprecated
  public java.util.Map<java.lang.String, java.lang.String> getAttr() {
    return getAttrMap();
  }

  /**
   * <code>map&lt;string, string&gt; attr = 1;</code>
   */

  public java.util.Map<java.lang.String, java.lang.String> getAttrMap() {
    return internalGetAttr().getMap();
  }

  /**
   * <code>map&lt;string, string&gt; attr = 1;</code>
   */

  public java.lang.String getAttrOrDefault(
          java.lang.String key,
          java.lang.String defaultValue) {
    if (key == null) {throw new java.lang.NullPointerException();}
    java.util.Map<java.lang.String, java.lang.String> map =
            internalGetAttr().getMap();
    return map.containsKey(key) ? map.get(key) : defaultValue;
  }

  /**
   * <code>map&lt;string, string&gt; attr = 1;</code>
   */

  public java.lang.String getAttrOrThrow(
          java.lang.String key) {
    if (key == null) {throw new java.lang.NullPointerException();}
    java.util.Map<java.lang.String, java.lang.String> map =
            internalGetAttr().getMap();
    if (!map.containsKey(key)) {
      throw new java.lang.IllegalArgumentException();
    }
    return map.get(key);
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof like.cn.dfs.model.client.ReadAttrResponse)) {
      return super.equals(obj);
    }
    like.cn.dfs.model.client.ReadAttrResponse other = ( like.cn.dfs.model.client.ReadAttrResponse ) obj;

    boolean result = true;
    result = result && internalGetAttr().equals(
            other.internalGetAttr());
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    if (!internalGetAttr().getMap().isEmpty()) {
      hash = (37 * hash) + ATTR_FIELD_NUMBER;
      hash = (53 * hash) + internalGetAttr().hashCode();
    }
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
  public com.google.protobuf.Parser<ReadAttrResponse> getParserForType() {
    return PARSER;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
  internalGetFieldAccessorTable() {
    return like.cn.dfs.model.client.DfsClient.internal_static_com_ruyuan_dfs_common_proto_ReadAttrResponse_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                    like.cn.dfs.model.client.ReadAttrResponse.class, like.cn.dfs.model.client.ReadAttrResponse.Builder.class);
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
    com.google.protobuf.GeneratedMessageV3
            .serializeStringMapTo(
                    output,
                    internalGetAttr(),
                    AttrDefaultEntryHolder.defaultEntry,
                    1);
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    for (java.util.Map.Entry<java.lang.String, java.lang.String> entry
            : internalGetAttr().getMap().entrySet()) {
      com.google.protobuf.MapEntry<java.lang.String, java.lang.String>
              attr__ = AttrDefaultEntryHolder.defaultEntry.newBuilderForType()
              .setKey(entry.getKey())
              .setValue(entry.getValue())
              .build();
      size += com.google.protobuf.CodedOutputStream
              .computeMessageSize(1, attr__);
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

  @SuppressWarnings({"rawtypes"})
  protected com.google.protobuf.MapField internalGetMapField(
          int number) {
    switch (number) {
      case 1:
        return internalGetAttr();
      default:
        throw new RuntimeException(
                "Invalid map field number: " + number);
    }
  }

  public like.cn.dfs.model.client.ReadAttrResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

  private com.google.protobuf.MapField<java.lang.String, java.lang.String>
  internalGetAttr() {
    if (attr_ == null) {
      return com.google.protobuf.MapField.emptyMapField(
              AttrDefaultEntryHolder.defaultEntry);
    }
    return attr_;
  }

  private static final class AttrDefaultEntryHolder {
    static final com.google.protobuf.MapEntry<
            java.lang.String, java.lang.String> defaultEntry =
            com.google.protobuf.MapEntry
                    .newDefaultInstance(
                            like.cn.dfs.model.client.DfsClient.internal_static_com_ruyuan_dfs_common_proto_ReadAttrResponse_AttrEntry_descriptor,
                            com.google.protobuf.WireFormat.FieldType.STRING,
                            "",
                            com.google.protobuf.WireFormat.FieldType.STRING,
                            "");
  }

  /**
   * Protobuf type {@code com.ruyuan.dfs.common.proto.ReadAttrResponse}
   */
  public static final class Builder extends
          com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
          // @@protoc_insertion_point(builder_implements:com.ruyuan.dfs.common.proto.ReadAttrResponse)
          like.cn.dfs.model.client.ReadAttrResponseOrBuilder {
    private int bitField0_;
    private com.google.protobuf.MapField<
            java.lang.String, java.lang.String> attr_;

    // Construct using like.cn.dfs.model.client.ReadAttrResponse.newBuilder()
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
      return like.cn.dfs.model.client.DfsClient.internal_static_com_ruyuan_dfs_common_proto_ReadAttrResponse_descriptor;
    }

    public like.cn.dfs.model.client.ReadAttrResponse getDefaultInstanceForType() {
      return like.cn.dfs.model.client.ReadAttrResponse.getDefaultInstance();
    }

    public like.cn.dfs.model.client.ReadAttrResponse build() {
      like.cn.dfs.model.client.ReadAttrResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public like.cn.dfs.model.client.ReadAttrResponse buildPartial() {
      like.cn.dfs.model.client.ReadAttrResponse result = new like.cn.dfs.model.client.ReadAttrResponse(this);
      int from_bitField0_ = bitField0_;
      result.attr_ = internalGetAttr();
      result.attr_.makeImmutable();
      onBuilt();
      return result;
    }

    public Builder clone() {
      return super.clone();
    }

    public Builder clear() {
      super.clear();
      internalGetMutableAttr().clear();
      return this;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
    internalGetFieldAccessorTable() {
      return like.cn.dfs.model.client.DfsClient.internal_static_com_ruyuan_dfs_common_proto_ReadAttrResponse_fieldAccessorTable
              .ensureFieldAccessorsInitialized(
                      like.cn.dfs.model.client.ReadAttrResponse.class, like.cn.dfs.model.client.ReadAttrResponse.Builder.class);
    }

    public com.google.protobuf.Descriptors.Descriptor
    getDescriptorForType() {
      return like.cn.dfs.model.client.DfsClient.internal_static_com_ruyuan_dfs_common_proto_ReadAttrResponse_descriptor;
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

    @SuppressWarnings({"rawtypes"})
    protected com.google.protobuf.MapField internalGetMapField(
            int number) {
      switch (number) {
        case 1:
          return internalGetAttr();
        default:
          throw new RuntimeException(
                  "Invalid map field number: " + number);
      }
    }

    @SuppressWarnings({"rawtypes"})
    protected com.google.protobuf.MapField internalGetMutableMapField(
            int number) {
      switch (number) {
        case 1:
          return internalGetMutableAttr();
        default:
          throw new RuntimeException(
                  "Invalid map field number: " + number);
      }
    }

    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof like.cn.dfs.model.client.ReadAttrResponse) {
        return mergeFrom(( like.cn.dfs.model.client.ReadAttrResponse ) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      like.cn.dfs.model.client.ReadAttrResponse parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = ( like.cn.dfs.model.client.ReadAttrResponse ) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    public Builder mergeFrom(like.cn.dfs.model.client.ReadAttrResponse other) {
      if (other == like.cn.dfs.model.client.ReadAttrResponse.getDefaultInstance()) return this;
      internalGetMutableAttr().mergeFrom(
              other.internalGetAttr());
      onChanged();
      return this;
    }

    public int getAttrCount() {
      return internalGetAttr().getMap().size();
    }

    /**
     * <code>map&lt;string, string&gt; attr = 1;</code>
     */

    public boolean containsAttr(
            java.lang.String key) {
      if (key == null) {throw new java.lang.NullPointerException();}
      return internalGetAttr().getMap().containsKey(key);
    }

    /**
     * Use {@link #getAttrMap()} instead.
     */
    @java.lang.Deprecated
    public java.util.Map<java.lang.String, java.lang.String> getAttr() {
      return getAttrMap();
    }

    /**
     * <code>map&lt;string, string&gt; attr = 1;</code>
     */

    public java.util.Map<java.lang.String, java.lang.String> getAttrMap() {
      return internalGetAttr().getMap();
    }

    /**
     * <code>map&lt;string, string&gt; attr = 1;</code>
     */

    public java.lang.String getAttrOrDefault(
            java.lang.String key,
            java.lang.String defaultValue) {
      if (key == null) {throw new java.lang.NullPointerException();}
      java.util.Map<java.lang.String, java.lang.String> map =
              internalGetAttr().getMap();
      return map.containsKey(key) ? map.get(key) : defaultValue;
    }

    /**
     * <code>map&lt;string, string&gt; attr = 1;</code>
     */

    public java.lang.String getAttrOrThrow(
            java.lang.String key) {
      if (key == null) {throw new java.lang.NullPointerException();}
      java.util.Map<java.lang.String, java.lang.String> map =
              internalGetAttr().getMap();
      if (!map.containsKey(key)) {
        throw new java.lang.IllegalArgumentException();
      }
      return map.get(key);
    }

    public Builder clearAttr() {
      internalGetMutableAttr().getMutableMap()
              .clear();
      return this;
    }

    /**
     * <code>map&lt;string, string&gt; attr = 1;</code>
     */

    public Builder removeAttr(
            java.lang.String key) {
      if (key == null) {throw new java.lang.NullPointerException();}
      internalGetMutableAttr().getMutableMap()
              .remove(key);
      return this;
    }

    /**
     * Use alternate mutation accessors instead.
     */
    @java.lang.Deprecated
    public java.util.Map<java.lang.String, java.lang.String>
    getMutableAttr() {
      return internalGetMutableAttr().getMutableMap();
    }

    /**
     * <code>map&lt;string, string&gt; attr = 1;</code>
     */
    public Builder putAttr(
            java.lang.String key,
            java.lang.String value) {
      if (key == null) {throw new java.lang.NullPointerException();}
      if (value == null) {throw new java.lang.NullPointerException();}
      internalGetMutableAttr().getMutableMap()
              .put(key, value);
      return this;
    }

    /**
     * <code>map&lt;string, string&gt; attr = 1;</code>
     */

    public Builder putAllAttr(
            java.util.Map<java.lang.String, java.lang.String> values) {
      internalGetMutableAttr().getMutableMap()
              .putAll(values);
      return this;
    }

    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }

    private com.google.protobuf.MapField<java.lang.String, java.lang.String>
    internalGetAttr() {
      if (attr_ == null) {
        return com.google.protobuf.MapField.emptyMapField(
                AttrDefaultEntryHolder.defaultEntry);
      }
      return attr_;
    }

    private com.google.protobuf.MapField<java.lang.String, java.lang.String>
    internalGetMutableAttr() {
      onChanged();
      if (attr_ == null) {
        attr_ = com.google.protobuf.MapField.newMapField(
                AttrDefaultEntryHolder.defaultEntry);
      }
      if (!attr_.isMutable()) {
        attr_ = attr_.copy();
      }
      return attr_;
    }


    // @@protoc_insertion_point(builder_scope:com.ruyuan.dfs.common.proto.ReadAttrResponse)
  }

}
