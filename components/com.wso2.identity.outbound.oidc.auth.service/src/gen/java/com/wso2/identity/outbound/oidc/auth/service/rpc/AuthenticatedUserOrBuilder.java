// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: OutboundOIDCService.proto

package com.wso2.identity.outbound.oidc.auth.service.rpc;

public interface AuthenticatedUserOrBuilder extends
    // @@protoc_insertion_point(interface_extends:AuthenticatedUser)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>bool isFederatedUser = 1;</code>
   */
  boolean getIsFederatedUser();

  /**
   * <code>map&lt;string, string&gt; userAttributes = 2;</code>
   */
  int getUserAttributesCount();
  /**
   * <code>map&lt;string, string&gt; userAttributes = 2;</code>
   */
  boolean containsUserAttributes(
      java.lang.String key);
  /**
   * Use {@link #getUserAttributesMap()} instead.
   */
  @java.lang.Deprecated
  java.util.Map<java.lang.String, java.lang.String>
  getUserAttributes();
  /**
   * <code>map&lt;string, string&gt; userAttributes = 2;</code>
   */
  java.util.Map<java.lang.String, java.lang.String>
  getUserAttributesMap();
  /**
   * <code>map&lt;string, string&gt; userAttributes = 2;</code>
   */

  java.lang.String getUserAttributesOrDefault(
      java.lang.String key,
      java.lang.String defaultValue);
  /**
   * <code>map&lt;string, string&gt; userAttributes = 2;</code>
   */

  java.lang.String getUserAttributesOrThrow(
      java.lang.String key);
}
