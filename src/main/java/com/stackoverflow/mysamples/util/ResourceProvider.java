package com.stackoverflow.mysamples.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.apache.commons.io.IOUtils;

/**
 * @author Yuriy Tsarkov (yurait6@gmail.com) on 17.10.2022
 */
public class ResourceProvider {

  public static String getResourceString(Class<?> clazz, String resource) throws IOException {
    return InstanceHolder.getInstance().getResourceInternal(clazz.getClassLoader(), resource);
  }

  public static byte[] getResourceBytes(Class<?> clazz, String resource) throws IOException {
    return InstanceHolder.getInstance().getResourceBytesInternal(clazz.getClassLoader(), resource);
  }

  private String getResourceInternal(ClassLoader classLoader, String resource) throws IOException {
    return IOUtils.resourceToString(getPath(resource), StandardCharsets.UTF_8, classLoader);
  }

  private byte[] getResourceBytesInternal(ClassLoader classLoader, String resource) throws IOException {
    return IOUtils.resourceToByteArray(getPath(resource), classLoader);
  }

  private String getPath(String value) {
    String path = Optional.ofNullable(value).orElse("");
    return path.startsWith("/")
        ? path.replaceFirst("/", "")
        : path;
  }

  static class InstanceHolder {
    private static final ResourceProvider INSTANCE = new ResourceProvider();

    public static ResourceProvider getInstance() {
      return INSTANCE;
    }
  }
}
