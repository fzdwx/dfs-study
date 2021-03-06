// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: dfs-client.proto

package like.cn.dfs.model.client;

/**
 * Protobuf type {@code com.ruyuan.dfs.common.proto.AuthenticateInfoRequest}
 */
public final class AuthenticateInfoRequest extends
        com.google.protobuf.GeneratedMessageV3 implements
        // @@protoc_insertion_point(message_implements:com.ruyuan.dfs.common.proto.AuthenticateInfoRequest)
        AuthenticateInfoRequestOrBuilder {
  public static final int AUTHENTICATEINFO_FIELD_NUMBER = 1;
  private static final long serialVersionUID = 0L;
  // @@protoc_insertion_point(class_scope:com.ruyuan.dfs.common.proto.AuthenticateInfoRequest)
  private static final like.cn.dfs.model.client.AuthenticateInfoRequest DEFAULT_INSTANCE;
  private static final com.google.protobuf.Parser<AuthenticateInfoRequest>
          PARSER = new com.google.protobuf.AbstractParser<AuthenticateInfoRequest>() {
    public AuthenticateInfoRequest parsePartialFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return new AuthenticateInfoRequest(input, extensionRegistry);
    }
  };

  static {
    DEFAULT_INSTANCE = new like.cn.dfs.model.client.AuthenticateInfoRequest();
  }

  private volatile java.lang.Object authenticateInfo_;
  private byte memoizedIsInitialized = -1;

  // Use AuthenticateInfoRequest.newBuilder() to construct.
  private AuthenticateInfoRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }

  private AuthenticateInfoRequest() {
    authenticateInfo_ = "";
  }

  private AuthenticateInfoRequest(
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

            authenticateInfo_ = s;
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

  public static like.cn.dfs.model.client.AuthenticateInfoRequest parseFrom(
          java.nio.ByteBuffer data)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static like.cn.dfs.model.client.AuthenticateInfoRequest parseFrom(
          java.nio.ByteBuffer data,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static like.cn.dfs.model.client.AuthenticateInfoRequest parseFrom(
          com.google.protobuf.ByteString data)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static like.cn.dfs.model.client.AuthenticateInfoRequest parseFrom(
          com.google.protobuf.ByteString data,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static like.cn.dfs.model.client.AuthenticateInfoRequest parseFrom(byte[] data)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static like.cn.dfs.model.client.AuthenticateInfoRequest parseFrom(
          byte[] data,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static like.cn.dfs.model.client.AuthenticateInfoRequest parseFrom(java.io.InputStream input)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
            .parseWithIOException(PARSER, input);
  }

  public static like.cn.dfs.model.client.AuthenticateInfoRequest parseFrom(
          java.io.InputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
            .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static like.cn.dfs.model.client.AuthenticateInfoRequest parseDelimitedFrom(java.io.InputStream input)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
            .parseDelimitedWithIOException(PARSER, input);
  }

  public static like.cn.dfs.model.client.AuthenticateInfoRequest parseDelimitedFrom(
          java.io.InputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
            .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }

  public static like.cn.dfs.model.client.AuthenticateInfoRequest parseFrom(
          com.google.protobuf.CodedInputStream input)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
            .parseWithIOException(PARSER, input);
  }

  public static like.cn.dfs.model.client.AuthenticateInfoRequest parseFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
            .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }

  public static Builder newBuilder(like.cn.dfs.model.client.AuthenticateInfoRequest prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }

  public static like.cn.dfs.model.client.AuthenticateInfoRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  public static com.google.protobuf.Parser<AuthenticateInfoRequest> parser() {
    return PARSER;
  }

  public static final com.google.protobuf.Descriptors.Descriptor
  getDescriptor() {
    return like.cn.dfs.model.client.DfsClient.internal_static_com_ruyuan_dfs_common_proto_AuthenticateInfoRequest_descriptor;
  }

  /**
   * <code>string authenticateInfo = 1;</code>
   */
  public java.lang.String getAuthenticateInfo() {
    java.lang.Object ref = authenticateInfo_;
    if (ref instanceof java.lang.String) {
      return ( java.lang.String ) ref;
    } else {
      com.google.protobuf.ByteString bs =
              ( com.google.protobuf.ByteString ) ref;
      java.lang.String s = bs.toStringUtf8();
      authenticateInfo_ = s;
      return s;
    }
  }

  /**
   * <code>string authenticateInfo = 1;</code>
   */
  public com.google.protobuf.ByteString
  getAuthenticateInfoBytes() {
    java.lang.Object ref = authenticateInfo_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8(
                      ( java.lang.String ) ref);
      authenticateInfo_ = b;
      return b;
    } else {
      return ( com.google.protobuf.ByteString ) ref;
    }
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof like.cn.dfs.model.client.AuthenticateInfoRequest)) {
      return super.equals(obj);
    }
    like.cn.dfs.model.client.AuthenticateInfoRequest other = ( like.cn.dfs.model.client.AuthenticateInfoRequest ) obj;

    boolean result = true;
    result = result && getAuthenticateInfo()
            .equals(other.getAuthenticateInfo());
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + AUTHENTICATEINFO_FIELD_NUMBER;
    hash = (53 * hash) + getAuthenticateInfo().hashCode();
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
  public com.google.protobuf.Parser<AuthenticateInfoRequest> getParserForType() {
    return PARSER;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
  internalGetFieldAccessorTable() {
    return like.cn.dfs.model.client.DfsClient.internal_static_com_ruyuan_dfs_common_proto_AuthenticateInfoRequest_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                    like.cn.dfs.model.client.AuthenticateInfoRequest.class, like.cn.dfs.model.client.AuthenticateInfoRequest.Builder.class);
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
    if (!getAuthenticateInfoBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, authenticateInfo_);
    }
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!getAuthenticateInfoBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, authenticateInfo_);
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

  public like.cn.dfs.model.client.AuthenticateInfoRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

  /**
   * Protobuf type {@code com.ruyuan.dfs.common.proto.AuthenticateInfoRequest}
   */
  public static final class Builder extends
          com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
          // @@protoc_insertion_point(builder_implements:com.ruyuan.dfs.common.proto.AuthenticateInfoRequest)
          like.cn.dfs.model.client.AuthenticateInfoRequestOrBuilder {
    private java.lang.Object authenticateInfo_ = "";

    // Construct using like.cn.dfs.model.client.AuthenticateInfoRequest.newBuilder()
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
      return like.cn.dfs.model.client.DfsClient.internal_static_com_ruyuan_dfs_common_proto_AuthenticateInfoRequest_descriptor;
    }

    public like.cn.dfs.model.client.AuthenticateInfoRequest getDefaultInstanceForType() {
      return like.cn.dfs.model.client.AuthenticateInfoRequest.getDefaultInstance();
    }

    public like.cn.dfs.model.client.AuthenticateInfoRequest build() {
      like.cn.dfs.model.client.AuthenticateInfoRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public like.cn.dfs.model.client.AuthenticateInfoRequest buildPartial() {
      like.cn.dfs.model.client.AuthenticateInfoRequest result = new like.cn.dfs.model.client.AuthenticateInfoRequest(this);
      result.authenticateInfo_ = authenticateInfo_;
      onBuilt();
      return result;
    }

    public Builder clone() {
      return super.clone();
    }

    public Builder clear() {
      super.clear();
      authenticateInfo_ = "";

      return this;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
    internalGetFieldAccessorTable() {
      return like.cn.dfs.model.client.DfsClient.internal_static_com_ruyuan_dfs_common_proto_AuthenticateInfoRequest_fieldAccessorTable
              .ensureFieldAccessorsInitialized(
                      like.cn.dfs.model.client.AuthenticateInfoRequest.class, like.cn.dfs.model.client.AuthenticateInfoRequest.Builder.class);
    }

    public com.google.protobuf.Descriptors.Descriptor
    getDescriptorForType() {
      return like.cn.dfs.model.client.DfsClient.internal_static_com_ruyuan_dfs_common_proto_AuthenticateInfoRequest_descriptor;
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
      if (other instanceof like.cn.dfs.model.client.AuthenticateInfoRequest) {
        return mergeFrom(( like.cn.dfs.model.client.AuthenticateInfoRequest ) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      like.cn.dfs.model.client.AuthenticateInfoRequest parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = ( like.cn.dfs.model.client.AuthenticateInfoRequest ) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    public Builder mergeFrom(like.cn.dfs.model.client.AuthenticateInfoRequest other) {
      if (other == like.cn.dfs.model.client.AuthenticateInfoRequest.getDefaultInstance()) return this;
      if (!other.getAuthenticateInfo().isEmpty()) {
        authenticateInfo_ = other.authenticateInfo_;
        onChanged();
      }
      onChanged();
      return this;
    }

    /**
     * <code>string authenticateInfo = 1;</code>
     */
    public java.lang.String getAuthenticateInfo() {
      java.lang.Object ref = authenticateInfo_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
                ( com.google.protobuf.ByteString ) ref;
        java.lang.String s = bs.toStringUtf8();
        authenticateInfo_ = s;
        return s;
      } else {
        return ( java.lang.String ) ref;
      }
    }

    /**
     * <code>string authenticateInfo = 1;</code>
     */
    public com.google.protobuf.ByteString
    getAuthenticateInfoBytes() {
      java.lang.Object ref = authenticateInfo_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
                com.google.protobuf.ByteString.copyFromUtf8(
                        ( java.lang.String ) ref);
        authenticateInfo_ = b;
        return b;
      } else {
        return ( com.google.protobuf.ByteString ) ref;
      }
    }

    /**
     * <code>string authenticateInfo = 1;</code>
     */
    public Builder setAuthenticateInfoBytes(
            com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);

      authenticateInfo_ = value;
      onChanged();
      return this;
    }

    /**
     * <code>string authenticateInfo = 1;</code>
     */
    public Builder setAuthenticateInfo(
            java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }

      authenticateInfo_ = value;
      onChanged();
      return this;
    }

    /**
     * <code>string authenticateInfo = 1;</code>
     */
    public Builder clearAuthenticateInfo() {

      authenticateInfo_ = getDefaultInstance().getAuthenticateInfo();
      onChanged();
      return this;
    }

    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }


    // @@protoc_insertion_point(builder_scope:com.ruyuan.dfs.common.proto.AuthenticateInfoRequest)
  }

}
