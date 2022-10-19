// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: OutboundOIDCService.proto

package com.wso2.identity.outbound.oidc.auth.service.rpc;

public interface ProcessAuthResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:ProcessAuthResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.AuthenticatorFlowStatus authenticationStatus = 1;</code>
   */
  int getAuthenticationStatusValue();
  /**
   * <code>.AuthenticatorFlowStatus authenticationStatus = 1;</code>
   */
  com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticatorFlowStatus getAuthenticationStatus();

  /**
   * <code>.AuthenticatedUser authenticatedUser = 2;</code>
   */
  boolean hasAuthenticatedUser();
  /**
   * <code>.AuthenticatedUser authenticatedUser = 2;</code>
   */
  com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticatedUser getAuthenticatedUser();
  /**
   * <code>.AuthenticatedUser authenticatedUser = 2;</code>
   */
  com.wso2.identity.outbound.oidc.auth.service.rpc.AuthenticatedUserOrBuilder getAuthenticatedUserOrBuilder();

  /**
   * <code>map&lt;string, string&gt; authenticationData = 3;</code>
   */
  int getAuthenticationDataCount();
  /**
   * <code>map&lt;string, string&gt; authenticationData = 3;</code>
   */
  boolean containsAuthenticationData(
      java.lang.String key);
  /**
   * Use {@link #getAuthenticationDataMap()} instead.
   */
  @java.lang.Deprecated
  java.util.Map<java.lang.String, java.lang.String>
  getAuthenticationData();
  /**
   * <code>map&lt;string, string&gt; authenticationData = 3;</code>
   */
  java.util.Map<java.lang.String, java.lang.String>
  getAuthenticationDataMap();
  /**
   * <code>map&lt;string, string&gt; authenticationData = 3;</code>
   */

  java.lang.String getAuthenticationDataOrDefault(
      java.lang.String key,
      java.lang.String defaultValue);
  /**
   * <code>map&lt;string, string&gt; authenticationData = 3;</code>
   */

  java.lang.String getAuthenticationDataOrThrow(
      java.lang.String key);
}
