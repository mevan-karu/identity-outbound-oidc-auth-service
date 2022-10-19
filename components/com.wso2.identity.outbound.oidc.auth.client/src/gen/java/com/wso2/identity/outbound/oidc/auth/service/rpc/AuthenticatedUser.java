// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: OutboundOIDCService.proto

package com.wso2.identity.outbound.oidc.auth.service.rpc;

/**
 * Protobuf type {@code AuthenticatedUser}
 */
public  final class AuthenticatedUser extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:AuthenticatedUser)
    AuthenticatedUserOrBuilder {
  // Use AuthenticatedUser.newBuilder() to construct.
  private AuthenticatedUser(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private AuthenticatedUser() {
    isFederatedUser_ = false;
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
  }
  private AuthenticatedUser(
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

            isFederatedUser_ = input.readBool();
            break;
          }
          case 18: {
            if (!((mutable_bitField0_ & 0x00000002) == 0x00000002)) {
              userAttributes_ = com.google.protobuf.MapField.newMapField(
                  UserAttributesDefaultEntryHolder.defaultEntry);
              mutable_bitField0_ |= 0x00000002;
            }
            com.google.protobuf.MapEntry<java.lang.String, java.lang.String>
            userAttributes__ = input.readMessage(
                UserAttributesDefaultEntryHolder.defaultEntry.getParserForType(), extensionRegistry);
            userAttributes_.getMutableMap().put(
                userAttributes__.getKey(), userAttributes__.getValue());
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
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.wso2.identity.outbound.oidc.auth.service.rpc.OutboundOIDCServiceOuterClass.internal_static_AuthenticatedUser_descriptor;
  }

  @SuppressWarnings({"rawtypes"})
  protected com.google.protobuf.MapField internalGetMapField(
      int number) {
    switch (number) {
      case 2:
        return internalGetUserAttributes();
      default:
        throw new RuntimeException(
            "Invalid map field number: " + number);
    }
  }
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.wso2.identity.outbound.oidc.auth.service.rpc.OutboundOIDCServiceOuterClass.internal_static_AuthenticatedUser_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticatedUser.class, com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticatedUser.Builder.class);
  }

  private int bitField0_;
  public static final int ISFEDERATEDUSER_FIELD_NUMBER = 1;
  private boolean isFederatedUser_;
  /**
   * <code>bool isFederatedUser = 1;</code>
   */
  public boolean getIsFederatedUser() {
    return isFederatedUser_;
  }

  public static final int USERATTRIBUTES_FIELD_NUMBER = 2;
  private static final class UserAttributesDefaultEntryHolder {
    static final com.google.protobuf.MapEntry<
        java.lang.String, java.lang.String> defaultEntry =
            com.google.protobuf.MapEntry
            .<java.lang.String, java.lang.String>newDefaultInstance(
                com.wso2.identity.outbound.oidc.auth.service.rpc.OutboundOIDCServiceOuterClass.internal_static_AuthenticatedUser_UserAttributesEntry_descriptor, 
                com.google.protobuf.WireFormat.FieldType.STRING,
                "",
                com.google.protobuf.WireFormat.FieldType.STRING,
                "");
  }
  private com.google.protobuf.MapField<
      java.lang.String, java.lang.String> userAttributes_;
  private com.google.protobuf.MapField<java.lang.String, java.lang.String>
  internalGetUserAttributes() {
    if (userAttributes_ == null) {
      return com.google.protobuf.MapField.emptyMapField(
          UserAttributesDefaultEntryHolder.defaultEntry);
    }
    return userAttributes_;
  }

  public int getUserAttributesCount() {
    return internalGetUserAttributes().getMap().size();
  }
  /**
   * <code>map&lt;string, string&gt; userAttributes = 2;</code>
   */

  public boolean containsUserAttributes(
      java.lang.String key) {
    if (key == null) { throw new java.lang.NullPointerException(); }
    return internalGetUserAttributes().getMap().containsKey(key);
  }
  /**
   * Use {@link #getUserAttributesMap()} instead.
   */
  @java.lang.Deprecated
  public java.util.Map<java.lang.String, java.lang.String> getUserAttributes() {
    return getUserAttributesMap();
  }
  /**
   * <code>map&lt;string, string&gt; userAttributes = 2;</code>
   */

  public java.util.Map<java.lang.String, java.lang.String> getUserAttributesMap() {
    return internalGetUserAttributes().getMap();
  }
  /**
   * <code>map&lt;string, string&gt; userAttributes = 2;</code>
   */

  public java.lang.String getUserAttributesOrDefault(
      java.lang.String key,
      java.lang.String defaultValue) {
    if (key == null) { throw new java.lang.NullPointerException(); }
    java.util.Map<java.lang.String, java.lang.String> map =
        internalGetUserAttributes().getMap();
    return map.containsKey(key) ? map.get(key) : defaultValue;
  }
  /**
   * <code>map&lt;string, string&gt; userAttributes = 2;</code>
   */

  public java.lang.String getUserAttributesOrThrow(
      java.lang.String key) {
    if (key == null) { throw new java.lang.NullPointerException(); }
    java.util.Map<java.lang.String, java.lang.String> map =
        internalGetUserAttributes().getMap();
    if (!map.containsKey(key)) {
      throw new java.lang.IllegalArgumentException();
    }
    return map.get(key);
  }

  private byte memoizedIsInitialized = -1;
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (isFederatedUser_ != false) {
      output.writeBool(1, isFederatedUser_);
    }
    com.google.protobuf.GeneratedMessageV3
      .serializeStringMapTo(
        output,
        internalGetUserAttributes(),
        UserAttributesDefaultEntryHolder.defaultEntry,
        2);
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (isFederatedUser_ != false) {
      size += com.google.protobuf.CodedOutputStream
        .computeBoolSize(1, isFederatedUser_);
    }
    for (java.util.Map.Entry<java.lang.String, java.lang.String> entry
         : internalGetUserAttributes().getMap().entrySet()) {
      com.google.protobuf.MapEntry<java.lang.String, java.lang.String>
      userAttributes__ = UserAttributesDefaultEntryHolder.defaultEntry.newBuilderForType()
          .setKey(entry.getKey())
          .setValue(entry.getValue())
          .build();
      size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(2, userAttributes__);
    }
    memoizedSize = size;
    return size;
  }

  private static final long serialVersionUID = 0L;
  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticatedUser)) {
      return super.equals(obj);
    }
    com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticatedUser other = (com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticatedUser) obj;

    boolean result = true;
    result = result && (getIsFederatedUser()
        == other.getIsFederatedUser());
    result = result && internalGetUserAttributes().equals(
        other.internalGetUserAttributes());
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + ISFEDERATEDUSER_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
        getIsFederatedUser());
    if (!internalGetUserAttributes().getMap().isEmpty()) {
      hash = (37 * hash) + USERATTRIBUTES_FIELD_NUMBER;
      hash = (53 * hash) + internalGetUserAttributes().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticatedUser parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticatedUser parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticatedUser parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticatedUser parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticatedUser parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticatedUser parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticatedUser parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticatedUser parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticatedUser parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticatedUser parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticatedUser parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticatedUser parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticatedUser prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code AuthenticatedUser}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:AuthenticatedUser)
      com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticatedUserOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.wso2.identity.outbound.oidc.auth.service.rpc.OutboundOIDCServiceOuterClass.internal_static_AuthenticatedUser_descriptor;
    }

    @SuppressWarnings({"rawtypes"})
    protected com.google.protobuf.MapField internalGetMapField(
        int number) {
      switch (number) {
        case 2:
          return internalGetUserAttributes();
        default:
          throw new RuntimeException(
              "Invalid map field number: " + number);
      }
    }
    @SuppressWarnings({"rawtypes"})
    protected com.google.protobuf.MapField internalGetMutableMapField(
        int number) {
      switch (number) {
        case 2:
          return internalGetMutableUserAttributes();
        default:
          throw new RuntimeException(
              "Invalid map field number: " + number);
      }
    }
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.wso2.identity.outbound.oidc.auth.service.rpc.OutboundOIDCServiceOuterClass.internal_static_AuthenticatedUser_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticatedUser.class, com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticatedUser.Builder.class);
    }

    // Construct using com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticatedUser.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    public Builder clear() {
      super.clear();
      isFederatedUser_ = false;

      internalGetMutableUserAttributes().clear();
      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.wso2.identity.outbound.oidc.auth.service.rpc.OutboundOIDCServiceOuterClass.internal_static_AuthenticatedUser_descriptor;
    }

    public com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticatedUser getDefaultInstanceForType() {
      return com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticatedUser.getDefaultInstance();
    }

    public com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticatedUser build() {
      com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticatedUser result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticatedUser buildPartial() {
      com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticatedUser result = new com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticatedUser(this);
      int from_bitField0_ = bitField0_;
      int to_bitField0_ = 0;
      result.isFederatedUser_ = isFederatedUser_;
      result.userAttributes_ = internalGetUserAttributes();
      result.userAttributes_.makeImmutable();
      result.bitField0_ = to_bitField0_;
      onBuilt();
      return result;
    }

    public Builder clone() {
      return (Builder) super.clone();
    }
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.setField(field, value);
    }
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return (Builder) super.clearField(field);
    }
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return (Builder) super.clearOneof(oneof);
    }
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, Object value) {
      return (Builder) super.setRepeatedField(field, index, value);
    }
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.addRepeatedField(field, value);
    }
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticatedUser) {
        return mergeFrom((com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticatedUser)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticatedUser other) {
      if (other == com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticatedUser.getDefaultInstance()) return this;
      if (other.getIsFederatedUser() != false) {
        setIsFederatedUser(other.getIsFederatedUser());
      }
      internalGetMutableUserAttributes().mergeFrom(
          other.internalGetUserAttributes());
      onChanged();
      return this;
    }

    public final boolean isInitialized() {
      return true;
    }

    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticatedUser parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticatedUser) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private boolean isFederatedUser_ ;
    /**
     * <code>bool isFederatedUser = 1;</code>
     */
    public boolean getIsFederatedUser() {
      return isFederatedUser_;
    }
    /**
     * <code>bool isFederatedUser = 1;</code>
     */
    public Builder setIsFederatedUser(boolean value) {
      
      isFederatedUser_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>bool isFederatedUser = 1;</code>
     */
    public Builder clearIsFederatedUser() {
      
      isFederatedUser_ = false;
      onChanged();
      return this;
    }

    private com.google.protobuf.MapField<
        java.lang.String, java.lang.String> userAttributes_;
    private com.google.protobuf.MapField<java.lang.String, java.lang.String>
    internalGetUserAttributes() {
      if (userAttributes_ == null) {
        return com.google.protobuf.MapField.emptyMapField(
            UserAttributesDefaultEntryHolder.defaultEntry);
      }
      return userAttributes_;
    }
    private com.google.protobuf.MapField<java.lang.String, java.lang.String>
    internalGetMutableUserAttributes() {
      onChanged();;
      if (userAttributes_ == null) {
        userAttributes_ = com.google.protobuf.MapField.newMapField(
            UserAttributesDefaultEntryHolder.defaultEntry);
      }
      if (!userAttributes_.isMutable()) {
        userAttributes_ = userAttributes_.copy();
      }
      return userAttributes_;
    }

    public int getUserAttributesCount() {
      return internalGetUserAttributes().getMap().size();
    }
    /**
     * <code>map&lt;string, string&gt; userAttributes = 2;</code>
     */

    public boolean containsUserAttributes(
        java.lang.String key) {
      if (key == null) { throw new java.lang.NullPointerException(); }
      return internalGetUserAttributes().getMap().containsKey(key);
    }
    /**
     * Use {@link #getUserAttributesMap()} instead.
     */
    @java.lang.Deprecated
    public java.util.Map<java.lang.String, java.lang.String> getUserAttributes() {
      return getUserAttributesMap();
    }
    /**
     * <code>map&lt;string, string&gt; userAttributes = 2;</code>
     */

    public java.util.Map<java.lang.String, java.lang.String> getUserAttributesMap() {
      return internalGetUserAttributes().getMap();
    }
    /**
     * <code>map&lt;string, string&gt; userAttributes = 2;</code>
     */

    public java.lang.String getUserAttributesOrDefault(
        java.lang.String key,
        java.lang.String defaultValue) {
      if (key == null) { throw new java.lang.NullPointerException(); }
      java.util.Map<java.lang.String, java.lang.String> map =
          internalGetUserAttributes().getMap();
      return map.containsKey(key) ? map.get(key) : defaultValue;
    }
    /**
     * <code>map&lt;string, string&gt; userAttributes = 2;</code>
     */

    public java.lang.String getUserAttributesOrThrow(
        java.lang.String key) {
      if (key == null) { throw new java.lang.NullPointerException(); }
      java.util.Map<java.lang.String, java.lang.String> map =
          internalGetUserAttributes().getMap();
      if (!map.containsKey(key)) {
        throw new java.lang.IllegalArgumentException();
      }
      return map.get(key);
    }

    public Builder clearUserAttributes() {
      internalGetMutableUserAttributes().getMutableMap()
          .clear();
      return this;
    }
    /**
     * <code>map&lt;string, string&gt; userAttributes = 2;</code>
     */

    public Builder removeUserAttributes(
        java.lang.String key) {
      if (key == null) { throw new java.lang.NullPointerException(); }
      internalGetMutableUserAttributes().getMutableMap()
          .remove(key);
      return this;
    }
    /**
     * Use alternate mutation accessors instead.
     */
    @java.lang.Deprecated
    public java.util.Map<java.lang.String, java.lang.String>
    getMutableUserAttributes() {
      return internalGetMutableUserAttributes().getMutableMap();
    }
    /**
     * <code>map&lt;string, string&gt; userAttributes = 2;</code>
     */
    public Builder putUserAttributes(
        java.lang.String key,
        java.lang.String value) {
      if (key == null) { throw new java.lang.NullPointerException(); }
      if (value == null) { throw new java.lang.NullPointerException(); }
      internalGetMutableUserAttributes().getMutableMap()
          .put(key, value);
      return this;
    }
    /**
     * <code>map&lt;string, string&gt; userAttributes = 2;</code>
     */

    public Builder putAllUserAttributes(
        java.util.Map<java.lang.String, java.lang.String> values) {
      internalGetMutableUserAttributes().getMutableMap()
          .putAll(values);
      return this;
    }
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }

    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }


    // @@protoc_insertion_point(builder_scope:AuthenticatedUser)
  }

  // @@protoc_insertion_point(class_scope:AuthenticatedUser)
  private static final com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticatedUser DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticatedUser();
  }

  public static com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticatedUser getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<AuthenticatedUser>
      PARSER = new com.google.protobuf.AbstractParser<AuthenticatedUser>() {
    public AuthenticatedUser parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
        return new AuthenticatedUser(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<AuthenticatedUser> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<AuthenticatedUser> getParserForType() {
    return PARSER;
  }

  public com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticatedUser getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

