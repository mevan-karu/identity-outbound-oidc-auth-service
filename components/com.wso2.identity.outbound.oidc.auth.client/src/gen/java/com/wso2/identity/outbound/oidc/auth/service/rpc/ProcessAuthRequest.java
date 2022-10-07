/**
 * Copyright (c) 2022, WSO2 LLC. (https://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 LLC. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein in any form is strictly forbidden, unless permitted by WSO2 expressly.
 * You may not alter or remove any copyright or other notice from copies of this content.
 */

// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: OutboundOIDCService.proto

package com.wso2.identity.outbound.oidc.auth.service.rpc;

/**
 * Protobuf type {@code com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequest}
 */
public  final class ProcessAuthRequest extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequest)
    ProcessAuthRequestOrBuilder {
  // Use ProcessAuthRequest.newBuilder() to construct.
  private ProcessAuthRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private ProcessAuthRequest() {
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
  }
  private ProcessAuthRequest(
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
            com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticationContext.Builder subBuilder = null;
            if (authenticationContext_ != null) {
              subBuilder = authenticationContext_.toBuilder();
            }
            authenticationContext_ = input.readMessage(com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticationContext.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(authenticationContext_);
              authenticationContext_ = subBuilder.buildPartial();
            }

            break;
          }
          case 18: {
            com.wso2.identity.outbound.oidc.auth.service.rpc.Request.Builder subBuilder = null;
            if (request_ != null) {
              subBuilder = request_.toBuilder();
            }
            request_ = input.readMessage(com.wso2.identity.outbound.oidc.auth.service.rpc.Request.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(request_);
              request_ = subBuilder.buildPartial();
            }

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
    return com.wso2.identity.outbound.oidc.auth.service.rpc.OutboundOIDCServiceOuterClass.internal_static_com_wso2_identity_outbound_oidc_auth_service_rpc_ProcessAuthRequest_descriptor;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.wso2.identity.outbound.oidc.auth.service.rpc.OutboundOIDCServiceOuterClass.internal_static_com_wso2_identity_outbound_oidc_auth_service_rpc_ProcessAuthRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequest.class, com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequest.Builder.class);
  }

  public static final int AUTHENTICATIONCONTEXT_FIELD_NUMBER = 1;
  private com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticationContext authenticationContext_;
  /**
   * <code>.com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticationContext authenticationContext = 1;</code>
   */
  public boolean hasAuthenticationContext() {
    return authenticationContext_ != null;
  }
  /**
   * <code>.com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticationContext authenticationContext = 1;</code>
   */
  public com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticationContext getAuthenticationContext() {
    return authenticationContext_ == null ? com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticationContext.getDefaultInstance() : authenticationContext_;
  }
  /**
   * <code>.com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticationContext authenticationContext = 1;</code>
   */
  public com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticationContextOrBuilder getAuthenticationContextOrBuilder() {
    return getAuthenticationContext();
  }

  public static final int REQUEST_FIELD_NUMBER = 2;
  private com.wso2.identity.outbound.oidc.auth.service.rpc.Request request_;
  /**
   * <code>.com.wso2.identity.outbound.oidc.auth.service.rpc.Request request = 2;</code>
   */
  public boolean hasRequest() {
    return request_ != null;
  }
  /**
   * <code>.com.wso2.identity.outbound.oidc.auth.service.rpc.Request request = 2;</code>
   */
  public com.wso2.identity.outbound.oidc.auth.service.rpc.Request getRequest() {
    return request_ == null ? com.wso2.identity.outbound.oidc.auth.service.rpc.Request.getDefaultInstance() : request_;
  }
  /**
   * <code>.com.wso2.identity.outbound.oidc.auth.service.rpc.Request request = 2;</code>
   */
  public com.wso2.identity.outbound.oidc.auth.service.rpc.RequestOrBuilder getRequestOrBuilder() {
    return getRequest();
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
    if (authenticationContext_ != null) {
      output.writeMessage(1, getAuthenticationContext());
    }
    if (request_ != null) {
      output.writeMessage(2, getRequest());
    }
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (authenticationContext_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, getAuthenticationContext());
    }
    if (request_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(2, getRequest());
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
    if (!(obj instanceof com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequest)) {
      return super.equals(obj);
    }
    com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequest other = (com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequest) obj;

    boolean result = true;
    result = result && (hasAuthenticationContext() == other.hasAuthenticationContext());
    if (hasAuthenticationContext()) {
      result = result && getAuthenticationContext()
          .equals(other.getAuthenticationContext());
    }
    result = result && (hasRequest() == other.hasRequest());
    if (hasRequest()) {
      result = result && getRequest()
          .equals(other.getRequest());
    }
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    if (hasAuthenticationContext()) {
      hash = (37 * hash) + AUTHENTICATIONCONTEXT_FIELD_NUMBER;
      hash = (53 * hash) + getAuthenticationContext().hashCode();
    }
    if (hasRequest()) {
      hash = (37 * hash) + REQUEST_FIELD_NUMBER;
      hash = (53 * hash) + getRequest().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequest parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequest parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequest parseFrom(
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
  public static Builder newBuilder(com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequest prototype) {
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
   * Protobuf type {@code com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequest)
      com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.wso2.identity.outbound.oidc.auth.service.rpc.OutboundOIDCServiceOuterClass.internal_static_com_wso2_identity_outbound_oidc_auth_service_rpc_ProcessAuthRequest_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.wso2.identity.outbound.oidc.auth.service.rpc.OutboundOIDCServiceOuterClass.internal_static_com_wso2_identity_outbound_oidc_auth_service_rpc_ProcessAuthRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequest.class, com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequest.Builder.class);
    }

    // Construct using com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequest.newBuilder()
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
      if (authenticationContextBuilder_ == null) {
        authenticationContext_ = null;
      } else {
        authenticationContext_ = null;
        authenticationContextBuilder_ = null;
      }
      if (requestBuilder_ == null) {
        request_ = null;
      } else {
        request_ = null;
        requestBuilder_ = null;
      }
      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.wso2.identity.outbound.oidc.auth.service.rpc.OutboundOIDCServiceOuterClass.internal_static_com_wso2_identity_outbound_oidc_auth_service_rpc_ProcessAuthRequest_descriptor;
    }

    public com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequest getDefaultInstanceForType() {
      return com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequest.getDefaultInstance();
    }

    public com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequest build() {
      com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequest buildPartial() {
      com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequest result = new com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequest(this);
      if (authenticationContextBuilder_ == null) {
        result.authenticationContext_ = authenticationContext_;
      } else {
        result.authenticationContext_ = authenticationContextBuilder_.build();
      }
      if (requestBuilder_ == null) {
        result.request_ = request_;
      } else {
        result.request_ = requestBuilder_.build();
      }
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
      if (other instanceof com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequest) {
        return mergeFrom((com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequest other) {
      if (other == com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequest.getDefaultInstance()) return this;
      if (other.hasAuthenticationContext()) {
        mergeAuthenticationContext(other.getAuthenticationContext());
      }
      if (other.hasRequest()) {
        mergeRequest(other.getRequest());
      }
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
      com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequest parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequest) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticationContext authenticationContext_ = null;
    private com.google.protobuf.SingleFieldBuilderV3<
        com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticationContext, com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticationContext.Builder, com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticationContextOrBuilder> authenticationContextBuilder_;
    /**
     * <code>.com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticationContext authenticationContext = 1;</code>
     */
    public boolean hasAuthenticationContext() {
      return authenticationContextBuilder_ != null || authenticationContext_ != null;
    }
    /**
     * <code>.com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticationContext authenticationContext = 1;</code>
     */
    public com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticationContext getAuthenticationContext() {
      if (authenticationContextBuilder_ == null) {
        return authenticationContext_ == null ? com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticationContext.getDefaultInstance() : authenticationContext_;
      } else {
        return authenticationContextBuilder_.getMessage();
      }
    }
    /**
     * <code>.com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticationContext authenticationContext = 1;</code>
     */
    public Builder setAuthenticationContext(com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticationContext value) {
      if (authenticationContextBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        authenticationContext_ = value;
        onChanged();
      } else {
        authenticationContextBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticationContext authenticationContext = 1;</code>
     */
    public Builder setAuthenticationContext(
        com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticationContext.Builder builderForValue) {
      if (authenticationContextBuilder_ == null) {
        authenticationContext_ = builderForValue.build();
        onChanged();
      } else {
        authenticationContextBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticationContext authenticationContext = 1;</code>
     */
    public Builder mergeAuthenticationContext(com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticationContext value) {
      if (authenticationContextBuilder_ == null) {
        if (authenticationContext_ != null) {
          authenticationContext_ =
            com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticationContext.newBuilder(authenticationContext_).mergeFrom(value).buildPartial();
        } else {
          authenticationContext_ = value;
        }
        onChanged();
      } else {
        authenticationContextBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticationContext authenticationContext = 1;</code>
     */
    public Builder clearAuthenticationContext() {
      if (authenticationContextBuilder_ == null) {
        authenticationContext_ = null;
        onChanged();
      } else {
        authenticationContext_ = null;
        authenticationContextBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticationContext authenticationContext = 1;</code>
     */
    public com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticationContext.Builder getAuthenticationContextBuilder() {
      
      onChanged();
      return getAuthenticationContextFieldBuilder().getBuilder();
    }
    /**
     * <code>.com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticationContext authenticationContext = 1;</code>
     */
    public com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticationContextOrBuilder getAuthenticationContextOrBuilder() {
      if (authenticationContextBuilder_ != null) {
        return authenticationContextBuilder_.getMessageOrBuilder();
      } else {
        return authenticationContext_ == null ?
            com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticationContext.getDefaultInstance() : authenticationContext_;
      }
    }
    /**
     * <code>.com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticationContext authenticationContext = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticationContext, com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticationContext.Builder, com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticationContextOrBuilder> 
        getAuthenticationContextFieldBuilder() {
      if (authenticationContextBuilder_ == null) {
        authenticationContextBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticationContext, com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticationContext.Builder, com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticationContextOrBuilder>(
                getAuthenticationContext(),
                getParentForChildren(),
                isClean());
        authenticationContext_ = null;
      }
      return authenticationContextBuilder_;
    }

    private com.wso2.identity.outbound.oidc.auth.service.rpc.Request request_ = null;
    private com.google.protobuf.SingleFieldBuilderV3<
        com.wso2.identity.outbound.oidc.auth.service.rpc.Request, com.wso2.identity.outbound.oidc.auth.service.rpc.Request.Builder, com.wso2.identity.outbound.oidc.auth.service.rpc.RequestOrBuilder> requestBuilder_;
    /**
     * <code>.com.wso2.identity.outbound.oidc.auth.service.rpc.Request request = 2;</code>
     */
    public boolean hasRequest() {
      return requestBuilder_ != null || request_ != null;
    }
    /**
     * <code>.com.wso2.identity.outbound.oidc.auth.service.rpc.Request request = 2;</code>
     */
    public com.wso2.identity.outbound.oidc.auth.service.rpc.Request getRequest() {
      if (requestBuilder_ == null) {
        return request_ == null ? com.wso2.identity.outbound.oidc.auth.service.rpc.Request.getDefaultInstance() : request_;
      } else {
        return requestBuilder_.getMessage();
      }
    }
    /**
     * <code>.com.wso2.identity.outbound.oidc.auth.service.rpc.Request request = 2;</code>
     */
    public Builder setRequest(com.wso2.identity.outbound.oidc.auth.service.rpc.Request value) {
      if (requestBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        request_ = value;
        onChanged();
      } else {
        requestBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.com.wso2.identity.outbound.oidc.auth.service.rpc.Request request = 2;</code>
     */
    public Builder setRequest(
        com.wso2.identity.outbound.oidc.auth.service.rpc.Request.Builder builderForValue) {
      if (requestBuilder_ == null) {
        request_ = builderForValue.build();
        onChanged();
      } else {
        requestBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.com.wso2.identity.outbound.oidc.auth.service.rpc.Request request = 2;</code>
     */
    public Builder mergeRequest(com.wso2.identity.outbound.oidc.auth.service.rpc.Request value) {
      if (requestBuilder_ == null) {
        if (request_ != null) {
          request_ =
            com.wso2.identity.outbound.oidc.auth.service.rpc.Request.newBuilder(request_).mergeFrom(value).buildPartial();
        } else {
          request_ = value;
        }
        onChanged();
      } else {
        requestBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.com.wso2.identity.outbound.oidc.auth.service.rpc.Request request = 2;</code>
     */
    public Builder clearRequest() {
      if (requestBuilder_ == null) {
        request_ = null;
        onChanged();
      } else {
        request_ = null;
        requestBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.com.wso2.identity.outbound.oidc.auth.service.rpc.Request request = 2;</code>
     */
    public com.wso2.identity.outbound.oidc.auth.service.rpc.Request.Builder getRequestBuilder() {
      
      onChanged();
      return getRequestFieldBuilder().getBuilder();
    }
    /**
     * <code>.com.wso2.identity.outbound.oidc.auth.service.rpc.Request request = 2;</code>
     */
    public com.wso2.identity.outbound.oidc.auth.service.rpc.RequestOrBuilder getRequestOrBuilder() {
      if (requestBuilder_ != null) {
        return requestBuilder_.getMessageOrBuilder();
      } else {
        return request_ == null ?
            com.wso2.identity.outbound.oidc.auth.service.rpc.Request.getDefaultInstance() : request_;
      }
    }
    /**
     * <code>.com.wso2.identity.outbound.oidc.auth.service.rpc.Request request = 2;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        com.wso2.identity.outbound.oidc.auth.service.rpc.Request, com.wso2.identity.outbound.oidc.auth.service.rpc.Request.Builder, com.wso2.identity.outbound.oidc.auth.service.rpc.RequestOrBuilder> 
        getRequestFieldBuilder() {
      if (requestBuilder_ == null) {
        requestBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            com.wso2.identity.outbound.oidc.auth.service.rpc.Request, com.wso2.identity.outbound.oidc.auth.service.rpc.Request.Builder, com.wso2.identity.outbound.oidc.auth.service.rpc.RequestOrBuilder>(
                getRequest(),
                getParentForChildren(),
                isClean());
        request_ = null;
      }
      return requestBuilder_;
    }
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }

    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }


    // @@protoc_insertion_point(builder_scope:com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequest)
  }

  // @@protoc_insertion_point(class_scope:com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequest)
  private static final com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequest();
  }

  public static com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<ProcessAuthRequest>
      PARSER = new com.google.protobuf.AbstractParser<ProcessAuthRequest>() {
    public ProcessAuthRequest parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
        return new ProcessAuthRequest(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<ProcessAuthRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<ProcessAuthRequest> getParserForType() {
    return PARSER;
  }

  public com.wso2.identity.outbound.oidc.auth.service.rpc.ProcessAuthRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

