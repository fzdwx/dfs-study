// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: namenode.proto

package like.cn.dfs.model.namenode;

/**
 * Protobuf type {@code com.ruyuan.dfs.common.proto.NameNodeSlots}
 */
public final class NameNodeSlots extends
        com.google.protobuf.GeneratedMessageV3 implements
        // @@protoc_insertion_point(message_implements:com.ruyuan.dfs.common.proto.NameNodeSlots)
        NameNodeSlotsOrBuilder {
  public static final int OLDSLOTS_FIELD_NUMBER = 1;
  public static final int NEWSLOTS_FIELD_NUMBER = 2;
  public static final int REBALANCE_FIELD_NUMBER = 3;
  public static final int REBALANCENODEID_FIELD_NUMBER = 4;
  private static final long serialVersionUID = 0L;
  // @@protoc_insertion_point(class_scope:com.ruyuan.dfs.common.proto.NameNodeSlots)
  private static final like.cn.dfs.model.namenode.NameNodeSlots DEFAULT_INSTANCE;
  private static final com.google.protobuf.Parser<NameNodeSlots>
          PARSER = new com.google.protobuf.AbstractParser<NameNodeSlots>() {
    public NameNodeSlots parsePartialFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return new NameNodeSlots(input, extensionRegistry);
    }
  };

  static {
    DEFAULT_INSTANCE = new like.cn.dfs.model.namenode.NameNodeSlots();
  }

  private int bitField0_;
  private com.google.protobuf.MapField<
          java.lang.Integer, java.lang.Integer> oldSlots_;
  private com.google.protobuf.MapField<
          java.lang.Integer, java.lang.Integer> newSlots_;
  private boolean rebalance_;
  private int rebalanceNodeId_;
  private byte memoizedIsInitialized = -1;

  // Use NameNodeSlots.newBuilder() to construct.
  private NameNodeSlots(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }

  private NameNodeSlots() {
    rebalance_ = false;
    rebalanceNodeId_ = 0;
  }

  private NameNodeSlots(
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
              oldSlots_ = com.google.protobuf.MapField.newMapField(
                      OldSlotsDefaultEntryHolder.defaultEntry);
              mutable_bitField0_ |= 0x00000001;
            }
            com.google.protobuf.MapEntry<java.lang.Integer, java.lang.Integer>
                    oldSlots__ = input.readMessage(
                    OldSlotsDefaultEntryHolder.defaultEntry.getParserForType(), extensionRegistry);
            oldSlots_.getMutableMap().put(
                    oldSlots__.getKey(), oldSlots__.getValue());
            break;
          }
          case 18: {
            if (!((mutable_bitField0_ & 0x00000002) == 0x00000002)) {
              newSlots_ = com.google.protobuf.MapField.newMapField(
                      NewSlotsDefaultEntryHolder.defaultEntry);
              mutable_bitField0_ |= 0x00000002;
            }
            com.google.protobuf.MapEntry<java.lang.Integer, java.lang.Integer>
                    newSlots__ = input.readMessage(
                    NewSlotsDefaultEntryHolder.defaultEntry.getParserForType(), extensionRegistry);
            newSlots_.getMutableMap().put(
                    newSlots__.getKey(), newSlots__.getValue());
            break;
          }
          case 24: {

            rebalance_ = input.readBool();
            break;
          }
          case 32: {

            rebalanceNodeId_ = input.readInt32();
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

  public static like.cn.dfs.model.namenode.NameNodeSlots parseFrom(
          java.nio.ByteBuffer data)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static like.cn.dfs.model.namenode.NameNodeSlots parseFrom(
          java.nio.ByteBuffer data,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static like.cn.dfs.model.namenode.NameNodeSlots parseFrom(
          com.google.protobuf.ByteString data)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static like.cn.dfs.model.namenode.NameNodeSlots parseFrom(
          com.google.protobuf.ByteString data,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static like.cn.dfs.model.namenode.NameNodeSlots parseFrom(byte[] data)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static like.cn.dfs.model.namenode.NameNodeSlots parseFrom(
          byte[] data,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static like.cn.dfs.model.namenode.NameNodeSlots parseFrom(java.io.InputStream input)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
            .parseWithIOException(PARSER, input);
  }

  public static like.cn.dfs.model.namenode.NameNodeSlots parseFrom(
          java.io.InputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
            .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static like.cn.dfs.model.namenode.NameNodeSlots parseDelimitedFrom(java.io.InputStream input)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
            .parseDelimitedWithIOException(PARSER, input);
  }

  public static like.cn.dfs.model.namenode.NameNodeSlots parseDelimitedFrom(
          java.io.InputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
            .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }

  public static like.cn.dfs.model.namenode.NameNodeSlots parseFrom(
          com.google.protobuf.CodedInputStream input)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
            .parseWithIOException(PARSER, input);
  }

  public static like.cn.dfs.model.namenode.NameNodeSlots parseFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
            .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }

  public static Builder newBuilder(like.cn.dfs.model.namenode.NameNodeSlots prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }

  public static like.cn.dfs.model.namenode.NameNodeSlots getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  public static com.google.protobuf.Parser<NameNodeSlots> parser() {
    return PARSER;
  }

  public static final com.google.protobuf.Descriptors.Descriptor
  getDescriptor() {
    return like.cn.dfs.model.namenode.Namenode.internal_static_com_ruyuan_dfs_common_proto_NameNodeSlots_descriptor;
  }

  public int getOldSlotsCount() {
    return internalGetOldSlots().getMap().size();
  }

  /**
   * <code>map&lt;int32, int32&gt; oldSlots = 1;</code>
   */

  public boolean containsOldSlots(
          int key) {

    return internalGetOldSlots().getMap().containsKey(key);
  }

  /**
   * Use {@link #getOldSlotsMap()} instead.
   */
  @java.lang.Deprecated
  public java.util.Map<java.lang.Integer, java.lang.Integer> getOldSlots() {
    return getOldSlotsMap();
  }

  /**
   * <code>map&lt;int32, int32&gt; oldSlots = 1;</code>
   */

  public java.util.Map<java.lang.Integer, java.lang.Integer> getOldSlotsMap() {
    return internalGetOldSlots().getMap();
  }

  /**
   * <code>map&lt;int32, int32&gt; oldSlots = 1;</code>
   */

  public int getOldSlotsOrDefault(
          int key,
          int defaultValue) {

    java.util.Map<java.lang.Integer, java.lang.Integer> map =
            internalGetOldSlots().getMap();
    return map.containsKey(key) ? map.get(key) : defaultValue;
  }

  /**
   * <code>map&lt;int32, int32&gt; oldSlots = 1;</code>
   */

  public int getOldSlotsOrThrow(
          int key) {

    java.util.Map<java.lang.Integer, java.lang.Integer> map =
            internalGetOldSlots().getMap();
    if (!map.containsKey(key)) {
      throw new java.lang.IllegalArgumentException();
    }
    return map.get(key);
  }

  public int getNewSlotsCount() {
    return internalGetNewSlots().getMap().size();
  }

  /**
   * <code>map&lt;int32, int32&gt; newSlots = 2;</code>
   */

  public boolean containsNewSlots(
          int key) {

    return internalGetNewSlots().getMap().containsKey(key);
  }

  /**
   * Use {@link #getNewSlotsMap()} instead.
   */
  @java.lang.Deprecated
  public java.util.Map<java.lang.Integer, java.lang.Integer> getNewSlots() {
    return getNewSlotsMap();
  }

  /**
   * <code>map&lt;int32, int32&gt; newSlots = 2;</code>
   */

  public java.util.Map<java.lang.Integer, java.lang.Integer> getNewSlotsMap() {
    return internalGetNewSlots().getMap();
  }

  /**
   * <code>map&lt;int32, int32&gt; newSlots = 2;</code>
   */

  public int getNewSlotsOrDefault(
          int key,
          int defaultValue) {

    java.util.Map<java.lang.Integer, java.lang.Integer> map =
            internalGetNewSlots().getMap();
    return map.containsKey(key) ? map.get(key) : defaultValue;
  }

  /**
   * <code>map&lt;int32, int32&gt; newSlots = 2;</code>
   */

  public int getNewSlotsOrThrow(
          int key) {

    java.util.Map<java.lang.Integer, java.lang.Integer> map =
            internalGetNewSlots().getMap();
    if (!map.containsKey(key)) {
      throw new java.lang.IllegalArgumentException();
    }
    return map.get(key);
  }

  /**
   * <code>bool rebalance = 3;</code>
   */
  public boolean getRebalance() {
    return rebalance_;
  }

  /**
   * <code>int32 rebalanceNodeId = 4;</code>
   */
  public int getRebalanceNodeId() {
    return rebalanceNodeId_;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof like.cn.dfs.model.namenode.NameNodeSlots)) {
      return super.equals(obj);
    }
    like.cn.dfs.model.namenode.NameNodeSlots other = ( like.cn.dfs.model.namenode.NameNodeSlots ) obj;

    boolean result = true;
    result = result && internalGetOldSlots().equals(
            other.internalGetOldSlots());
    result = result && internalGetNewSlots().equals(
            other.internalGetNewSlots());
    result = result && (getRebalance()
            == other.getRebalance());
    result = result && (getRebalanceNodeId()
            == other.getRebalanceNodeId());
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    if (!internalGetOldSlots().getMap().isEmpty()) {
      hash = (37 * hash) + OLDSLOTS_FIELD_NUMBER;
      hash = (53 * hash) + internalGetOldSlots().hashCode();
    }
    if (!internalGetNewSlots().getMap().isEmpty()) {
      hash = (37 * hash) + NEWSLOTS_FIELD_NUMBER;
      hash = (53 * hash) + internalGetNewSlots().hashCode();
    }
    hash = (37 * hash) + REBALANCE_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
            getRebalance());
    hash = (37 * hash) + REBALANCENODEID_FIELD_NUMBER;
    hash = (53 * hash) + getRebalanceNodeId();
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
  public com.google.protobuf.Parser<NameNodeSlots> getParserForType() {
    return PARSER;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
  internalGetFieldAccessorTable() {
    return like.cn.dfs.model.namenode.Namenode.internal_static_com_ruyuan_dfs_common_proto_NameNodeSlots_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                    like.cn.dfs.model.namenode.NameNodeSlots.class, like.cn.dfs.model.namenode.NameNodeSlots.Builder.class);
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
            .serializeIntegerMapTo(
                    output,
                    internalGetOldSlots(),
                    OldSlotsDefaultEntryHolder.defaultEntry,
                    1);
    com.google.protobuf.GeneratedMessageV3
            .serializeIntegerMapTo(
                    output,
                    internalGetNewSlots(),
                    NewSlotsDefaultEntryHolder.defaultEntry,
                    2);
    if (rebalance_ != false) {
      output.writeBool(3, rebalance_);
    }
    if (rebalanceNodeId_ != 0) {
      output.writeInt32(4, rebalanceNodeId_);
    }
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    for (java.util.Map.Entry<java.lang.Integer, java.lang.Integer> entry
            : internalGetOldSlots().getMap().entrySet()) {
      com.google.protobuf.MapEntry<java.lang.Integer, java.lang.Integer>
              oldSlots__ = OldSlotsDefaultEntryHolder.defaultEntry.newBuilderForType()
              .setKey(entry.getKey())
              .setValue(entry.getValue())
              .build();
      size += com.google.protobuf.CodedOutputStream
              .computeMessageSize(1, oldSlots__);
    }
    for (java.util.Map.Entry<java.lang.Integer, java.lang.Integer> entry
            : internalGetNewSlots().getMap().entrySet()) {
      com.google.protobuf.MapEntry<java.lang.Integer, java.lang.Integer>
              newSlots__ = NewSlotsDefaultEntryHolder.defaultEntry.newBuilderForType()
              .setKey(entry.getKey())
              .setValue(entry.getValue())
              .build();
      size += com.google.protobuf.CodedOutputStream
              .computeMessageSize(2, newSlots__);
    }
    if (rebalance_ != false) {
      size += com.google.protobuf.CodedOutputStream
              .computeBoolSize(3, rebalance_);
    }
    if (rebalanceNodeId_ != 0) {
      size += com.google.protobuf.CodedOutputStream
              .computeInt32Size(4, rebalanceNodeId_);
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
        return internalGetOldSlots();
      case 2:
        return internalGetNewSlots();
      default:
        throw new RuntimeException(
                "Invalid map field number: " + number);
    }
  }

  public like.cn.dfs.model.namenode.NameNodeSlots getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

  private com.google.protobuf.MapField<java.lang.Integer, java.lang.Integer>
  internalGetOldSlots() {
    if (oldSlots_ == null) {
      return com.google.protobuf.MapField.emptyMapField(
              OldSlotsDefaultEntryHolder.defaultEntry);
    }
    return oldSlots_;
  }

  private com.google.protobuf.MapField<java.lang.Integer, java.lang.Integer>
  internalGetNewSlots() {
    if (newSlots_ == null) {
      return com.google.protobuf.MapField.emptyMapField(
              NewSlotsDefaultEntryHolder.defaultEntry);
    }
    return newSlots_;
  }

  private static final class OldSlotsDefaultEntryHolder {
    static final com.google.protobuf.MapEntry<
            java.lang.Integer, java.lang.Integer> defaultEntry =
            com.google.protobuf.MapEntry
                    .newDefaultInstance(
                            like.cn.dfs.model.namenode.Namenode.internal_static_com_ruyuan_dfs_common_proto_NameNodeSlots_OldSlotsEntry_descriptor,
                            com.google.protobuf.WireFormat.FieldType.INT32,
                            0,
                            com.google.protobuf.WireFormat.FieldType.INT32,
                            0);
  }

  private static final class NewSlotsDefaultEntryHolder {
    static final com.google.protobuf.MapEntry<
            java.lang.Integer, java.lang.Integer> defaultEntry =
            com.google.protobuf.MapEntry
                    .newDefaultInstance(
                            like.cn.dfs.model.namenode.Namenode.internal_static_com_ruyuan_dfs_common_proto_NameNodeSlots_NewSlotsEntry_descriptor,
                            com.google.protobuf.WireFormat.FieldType.INT32,
                            0,
                            com.google.protobuf.WireFormat.FieldType.INT32,
                            0);
  }

  /**
   * Protobuf type {@code com.ruyuan.dfs.common.proto.NameNodeSlots}
   */
  public static final class Builder extends
          com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
          // @@protoc_insertion_point(builder_implements:com.ruyuan.dfs.common.proto.NameNodeSlots)
          like.cn.dfs.model.namenode.NameNodeSlotsOrBuilder {
    private int bitField0_;
    private com.google.protobuf.MapField<
            java.lang.Integer, java.lang.Integer> oldSlots_;
    private com.google.protobuf.MapField<
            java.lang.Integer, java.lang.Integer> newSlots_;
    private boolean rebalance_;
    private int rebalanceNodeId_;

    // Construct using like.cn.dfs.model.namenode.NameNodeSlots.newBuilder()
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
      return like.cn.dfs.model.namenode.Namenode.internal_static_com_ruyuan_dfs_common_proto_NameNodeSlots_descriptor;
    }

    public like.cn.dfs.model.namenode.NameNodeSlots getDefaultInstanceForType() {
      return like.cn.dfs.model.namenode.NameNodeSlots.getDefaultInstance();
    }

    public like.cn.dfs.model.namenode.NameNodeSlots build() {
      like.cn.dfs.model.namenode.NameNodeSlots result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public like.cn.dfs.model.namenode.NameNodeSlots buildPartial() {
      like.cn.dfs.model.namenode.NameNodeSlots result = new like.cn.dfs.model.namenode.NameNodeSlots(this);
      int from_bitField0_ = bitField0_;
      int to_bitField0_ = 0;
      result.oldSlots_ = internalGetOldSlots();
      result.oldSlots_.makeImmutable();
      result.newSlots_ = internalGetNewSlots();
      result.newSlots_.makeImmutable();
      result.rebalance_ = rebalance_;
      result.rebalanceNodeId_ = rebalanceNodeId_;
      result.bitField0_ = to_bitField0_;
      onBuilt();
      return result;
    }

    public Builder clone() {
      return super.clone();
    }

    public Builder clear() {
      super.clear();
      internalGetMutableOldSlots().clear();
      internalGetMutableNewSlots().clear();
      rebalance_ = false;

      rebalanceNodeId_ = 0;

      return this;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
    internalGetFieldAccessorTable() {
      return like.cn.dfs.model.namenode.Namenode.internal_static_com_ruyuan_dfs_common_proto_NameNodeSlots_fieldAccessorTable
              .ensureFieldAccessorsInitialized(
                      like.cn.dfs.model.namenode.NameNodeSlots.class, like.cn.dfs.model.namenode.NameNodeSlots.Builder.class);
    }

    public com.google.protobuf.Descriptors.Descriptor
    getDescriptorForType() {
      return like.cn.dfs.model.namenode.Namenode.internal_static_com_ruyuan_dfs_common_proto_NameNodeSlots_descriptor;
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
          return internalGetOldSlots();
        case 2:
          return internalGetNewSlots();
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
          return internalGetMutableOldSlots();
        case 2:
          return internalGetMutableNewSlots();
        default:
          throw new RuntimeException(
                  "Invalid map field number: " + number);
      }
    }

    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof like.cn.dfs.model.namenode.NameNodeSlots) {
        return mergeFrom(( like.cn.dfs.model.namenode.NameNodeSlots ) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      like.cn.dfs.model.namenode.NameNodeSlots parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = ( like.cn.dfs.model.namenode.NameNodeSlots ) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    public Builder mergeFrom(like.cn.dfs.model.namenode.NameNodeSlots other) {
      if (other == like.cn.dfs.model.namenode.NameNodeSlots.getDefaultInstance()) return this;
      internalGetMutableOldSlots().mergeFrom(
              other.internalGetOldSlots());
      internalGetMutableNewSlots().mergeFrom(
              other.internalGetNewSlots());
      if (other.getRebalance() != false) {
        setRebalance(other.getRebalance());
      }
      if (other.getRebalanceNodeId() != 0) {
        setRebalanceNodeId(other.getRebalanceNodeId());
      }
      onChanged();
      return this;
    }

    public int getOldSlotsCount() {
      return internalGetOldSlots().getMap().size();
    }

    /**
     * <code>map&lt;int32, int32&gt; oldSlots = 1;</code>
     */

    public boolean containsOldSlots(
            int key) {

      return internalGetOldSlots().getMap().containsKey(key);
    }

    /**
     * Use {@link #getOldSlotsMap()} instead.
     */
    @java.lang.Deprecated
    public java.util.Map<java.lang.Integer, java.lang.Integer> getOldSlots() {
      return getOldSlotsMap();
    }

    /**
     * <code>map&lt;int32, int32&gt; oldSlots = 1;</code>
     */

    public java.util.Map<java.lang.Integer, java.lang.Integer> getOldSlotsMap() {
      return internalGetOldSlots().getMap();
    }

    /**
     * <code>map&lt;int32, int32&gt; oldSlots = 1;</code>
     */

    public int getOldSlotsOrDefault(
            int key,
            int defaultValue) {

      java.util.Map<java.lang.Integer, java.lang.Integer> map =
              internalGetOldSlots().getMap();
      return map.containsKey(key) ? map.get(key) : defaultValue;
    }

    /**
     * <code>map&lt;int32, int32&gt; oldSlots = 1;</code>
     */

    public int getOldSlotsOrThrow(
            int key) {

      java.util.Map<java.lang.Integer, java.lang.Integer> map =
              internalGetOldSlots().getMap();
      if (!map.containsKey(key)) {
        throw new java.lang.IllegalArgumentException();
      }
      return map.get(key);
    }

    public int getNewSlotsCount() {
      return internalGetNewSlots().getMap().size();
    }

    /**
     * <code>map&lt;int32, int32&gt; newSlots = 2;</code>
     */

    public boolean containsNewSlots(
            int key) {

      return internalGetNewSlots().getMap().containsKey(key);
    }

    /**
     * Use {@link #getNewSlotsMap()} instead.
     */
    @java.lang.Deprecated
    public java.util.Map<java.lang.Integer, java.lang.Integer> getNewSlots() {
      return getNewSlotsMap();
    }

    /**
     * <code>map&lt;int32, int32&gt; newSlots = 2;</code>
     */

    public java.util.Map<java.lang.Integer, java.lang.Integer> getNewSlotsMap() {
      return internalGetNewSlots().getMap();
    }

    /**
     * <code>map&lt;int32, int32&gt; newSlots = 2;</code>
     */

    public int getNewSlotsOrDefault(
            int key,
            int defaultValue) {

      java.util.Map<java.lang.Integer, java.lang.Integer> map =
              internalGetNewSlots().getMap();
      return map.containsKey(key) ? map.get(key) : defaultValue;
    }

    /**
     * <code>map&lt;int32, int32&gt; newSlots = 2;</code>
     */

    public int getNewSlotsOrThrow(
            int key) {

      java.util.Map<java.lang.Integer, java.lang.Integer> map =
              internalGetNewSlots().getMap();
      if (!map.containsKey(key)) {
        throw new java.lang.IllegalArgumentException();
      }
      return map.get(key);
    }

    /**
     * <code>bool rebalance = 3;</code>
     */
    public boolean getRebalance() {
      return rebalance_;
    }

    /**
     * <code>bool rebalance = 3;</code>
     */
    public Builder setRebalance(boolean value) {

      rebalance_ = value;
      onChanged();
      return this;
    }

    /**
     * <code>int32 rebalanceNodeId = 4;</code>
     */
    public int getRebalanceNodeId() {
      return rebalanceNodeId_;
    }

    /**
     * <code>int32 rebalanceNodeId = 4;</code>
     */
    public Builder setRebalanceNodeId(int value) {

      rebalanceNodeId_ = value;
      onChanged();
      return this;
    }

    public Builder clearOldSlots() {
      internalGetMutableOldSlots().getMutableMap()
              .clear();
      return this;
    }

    /**
     * <code>map&lt;int32, int32&gt; oldSlots = 1;</code>
     */

    public Builder removeOldSlots(
            int key) {

      internalGetMutableOldSlots().getMutableMap()
              .remove(key);
      return this;
    }

    /**
     * Use alternate mutation accessors instead.
     */
    @java.lang.Deprecated
    public java.util.Map<java.lang.Integer, java.lang.Integer>
    getMutableOldSlots() {
      return internalGetMutableOldSlots().getMutableMap();
    }

    /**
     * <code>map&lt;int32, int32&gt; oldSlots = 1;</code>
     */
    public Builder putOldSlots(
            int key,
            int value) {


      internalGetMutableOldSlots().getMutableMap()
              .put(key, value);
      return this;
    }

    /**
     * <code>map&lt;int32, int32&gt; oldSlots = 1;</code>
     */

    public Builder putAllOldSlots(
            java.util.Map<java.lang.Integer, java.lang.Integer> values) {
      internalGetMutableOldSlots().getMutableMap()
              .putAll(values);
      return this;
    }

    public Builder clearNewSlots() {
      internalGetMutableNewSlots().getMutableMap()
              .clear();
      return this;
    }

    /**
     * <code>map&lt;int32, int32&gt; newSlots = 2;</code>
     */

    public Builder removeNewSlots(
            int key) {

      internalGetMutableNewSlots().getMutableMap()
              .remove(key);
      return this;
    }

    /**
     * Use alternate mutation accessors instead.
     */
    @java.lang.Deprecated
    public java.util.Map<java.lang.Integer, java.lang.Integer>
    getMutableNewSlots() {
      return internalGetMutableNewSlots().getMutableMap();
    }

    /**
     * <code>map&lt;int32, int32&gt; newSlots = 2;</code>
     */
    public Builder putNewSlots(
            int key,
            int value) {


      internalGetMutableNewSlots().getMutableMap()
              .put(key, value);
      return this;
    }

    /**
     * <code>map&lt;int32, int32&gt; newSlots = 2;</code>
     */

    public Builder putAllNewSlots(
            java.util.Map<java.lang.Integer, java.lang.Integer> values) {
      internalGetMutableNewSlots().getMutableMap()
              .putAll(values);
      return this;
    }

    /**
     * <code>bool rebalance = 3;</code>
     */
    public Builder clearRebalance() {

      rebalance_ = false;
      onChanged();
      return this;
    }

    /**
     * <code>int32 rebalanceNodeId = 4;</code>
     */
    public Builder clearRebalanceNodeId() {

      rebalanceNodeId_ = 0;
      onChanged();
      return this;
    }

    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }

    private com.google.protobuf.MapField<java.lang.Integer, java.lang.Integer>
    internalGetOldSlots() {
      if (oldSlots_ == null) {
        return com.google.protobuf.MapField.emptyMapField(
                OldSlotsDefaultEntryHolder.defaultEntry);
      }
      return oldSlots_;
    }

    private com.google.protobuf.MapField<java.lang.Integer, java.lang.Integer>
    internalGetMutableOldSlots() {
      onChanged();
      if (oldSlots_ == null) {
        oldSlots_ = com.google.protobuf.MapField.newMapField(
                OldSlotsDefaultEntryHolder.defaultEntry);
      }
      if (!oldSlots_.isMutable()) {
        oldSlots_ = oldSlots_.copy();
      }
      return oldSlots_;
    }

    private com.google.protobuf.MapField<java.lang.Integer, java.lang.Integer>
    internalGetNewSlots() {
      if (newSlots_ == null) {
        return com.google.protobuf.MapField.emptyMapField(
                NewSlotsDefaultEntryHolder.defaultEntry);
      }
      return newSlots_;
    }

    private com.google.protobuf.MapField<java.lang.Integer, java.lang.Integer>
    internalGetMutableNewSlots() {
      onChanged();
      if (newSlots_ == null) {
        newSlots_ = com.google.protobuf.MapField.newMapField(
                NewSlotsDefaultEntryHolder.defaultEntry);
      }
      if (!newSlots_.isMutable()) {
        newSlots_ = newSlots_.copy();
      }
      return newSlots_;
    }


    // @@protoc_insertion_point(builder_scope:com.ruyuan.dfs.common.proto.NameNodeSlots)
  }

}
