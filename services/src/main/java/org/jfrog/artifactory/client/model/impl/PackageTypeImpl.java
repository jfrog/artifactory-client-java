package org.jfrog.artifactory.client.model.impl;

import org.jfrog.artifactory.client.model.PackageType;

public enum PackageTypeImpl implements PackageType {
  bower("bower-default"),
  cocoapods("simple-default"),
  debian("simple-default"),
  distribution("simple-default"),
  docker("simple-default"),
  gems("simple-default"),
  generic("simple-default"),
  gitlfs("simple-default"),
  gradle("gradle-default"),
  ivy("ivy-default"),
  maven("maven-2-default"),
  npm("npm-default"),
  nuget("nuget-default"),
  opkg("simple-default"),
  p2("simple-default"),
  pypi("simple-default"),
  sbt("sbt-default"),
  vagrant("simple-default"),
  vcs("vcs-default"),
  yum("simple-default"),
  rpm("simple-default"),
  composer("composer-default"),
  conan("conan-default"),
  chef("simple-default"),
  puppet("puppet-default");

  private final String layout;

  PackageTypeImpl(String layout) {
    this.layout = layout;
  }

  @Override
  public boolean isCustom() {
    return false;
  }

  @Override
  public String getLayout() {
    return layout;
  }
}
