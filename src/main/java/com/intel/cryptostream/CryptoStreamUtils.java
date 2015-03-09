/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intel.cryptostream;

import static com.intel.cryptostream.ConfigurationKeys.CRYPTOSTREAM_LIB_NAME_KEY;
import static com.intel.cryptostream.ConfigurationKeys.CRYPTOSTREAM_LIB_PATH_KEY;
import static com.intel.cryptostream.ConfigurationKeys.CRYPTOSTREAM_TEMPDIR_KEY;
import static com.intel.cryptostream.ConfigurationKeys.CRYPTOSTREAM_CRYPTO_BUFFER_SIZE_DEFAULT;
import static com.intel.cryptostream.ConfigurationKeys.CRYPTOSTREAM_CRYPTO_BUFFER_SIZE_KEY;
import static com.intel.cryptostream.ConfigurationKeys.CRYPTOSTREAM_SYSTEM_PROPERTIES_FILE;
import static com.intel.cryptostream.ConfigurationKeys.CRYPTOSTREAM_CRYPTO_CIPHER_SUITE_DEFAULT;
import static com.intel.cryptostream.ConfigurationKeys.CRYPTOSTREAM_CRYPTO_CIPHER_SUITE_KEY;
import static com.intel.cryptostream.ConfigurationKeys.CRYPTOSTREAM_CRYPTO_CODEC_CLASSES_KEY_PREFIX;
import static com.intel.cryptostream.ConfigurationKeys.CRYPTOSTREAM_CRYPTO_JCE_PROVIDER_KEY;
import static com.intel.cryptostream.ConfigurationKeys.CRYPTOSTREAM_JAVA_SECURE_RANDOM_ALGORITHM_DEFAULT;
import static com.intel.cryptostream.ConfigurationKeys.CRYPTOSTREAM_JAVA_SECURE_RANDOM_ALGORITHM_KEY;
import static com.intel.cryptostream.ConfigurationKeys.CRYPTOSTREAM_RANDOM_DEVICE_FILE_PATH_DEFAULT;
import static com.intel.cryptostream.ConfigurationKeys.CRYPTOSTREAM_RANDOM_DEVICE_FILE_PATH_KEY;
import static com.intel.cryptostream.ConfigurationKeys.CRYPTOSTREAM_SECURE_RANDOM_IMPL_KEY;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Random;

import com.google.common.base.Preconditions;
import com.intel.cryptostream.random.OsSecureRandom;
import com.intel.cryptostream.utils.ReflectionUtils;

public class CryptoStreamUtils {
  private static final int MIN_BUFFER_SIZE = 512;
  
  static {
    loadSnappySystemProperties();
  }

  /**
   * load system properties when configuration file of the name
   * {@link #CRYPTOSTREAM_SYSTEM_PROPERTIES_FILE} is found
   */
  private static void loadSnappySystemProperties() {
    try {
      InputStream is = Thread.currentThread().getContextClassLoader()
          .getResourceAsStream(CRYPTOSTREAM_SYSTEM_PROPERTIES_FILE);

      if (is == null)
        return; // no configuration file is found

      // Load property file
      Properties props = new Properties();
      props.load(is);
      is.close();
      Enumeration<?> names = props.propertyNames();
      while (names.hasMoreElements()) {
        String name = (String) names.nextElement();
        if (name.startsWith("cryptostream.")) {
          if (System.getProperty(name) == null) {
            System.setProperty(name, props.getProperty(name));
          }
        }
      }
    } catch (Throwable ex) {
      System.err.println("Could not load '"
          + CRYPTOSTREAM_SYSTEM_PROPERTIES_FILE + "' from classpath: "
          + ex.toString());
    }
  }

  /** Forcibly free the direct buffer. */
  public static void freeDB(ByteBuffer buffer) {
    if (buffer instanceof sun.nio.ch.DirectBuffer) {
      final sun.misc.Cleaner bufferCleaner =
          ((sun.nio.ch.DirectBuffer) buffer).cleaner();
      bufferCleaner.clean();
    }
  }
  
  /** Read crypto buffer size */
  public static int getBufferSize() {
    String bufferSizeStr = System
        .getProperty(CRYPTOSTREAM_CRYPTO_BUFFER_SIZE_KEY);
    if (bufferSizeStr == null || bufferSizeStr.isEmpty()) {
      return CRYPTOSTREAM_CRYPTO_BUFFER_SIZE_DEFAULT;
    } else {
      return Integer.parseInt(bufferSizeStr);
    }
  }

  public static String getCodecString(CipherSuite cipherSuite) {
    String configName = CRYPTOSTREAM_CRYPTO_CODEC_CLASSES_KEY_PREFIX
        + cipherSuite.getConfigSuffix();
    return System.getProperty(configName);
  }

  public static CipherSuite getCryptoSuite() {
    String name = System.getProperty(CRYPTOSTREAM_CRYPTO_CIPHER_SUITE_KEY,
        CRYPTOSTREAM_CRYPTO_CIPHER_SUITE_DEFAULT);
    return CipherSuite.convert(name);
  }

  public static String getJCEProvider() {
    return System.getProperty(CRYPTOSTREAM_CRYPTO_JCE_PROVIDER_KEY);
  }

  public static String getSecureRandomAlg() {
    return System.getProperty(CRYPTOSTREAM_JAVA_SECURE_RANDOM_ALGORITHM_KEY,
        CRYPTOSTREAM_JAVA_SECURE_RANDOM_ALGORITHM_DEFAULT);
  }

  public static Class<? extends Random> getSecureRandomClass() {
    return ReflectionUtils.getClass(
        CRYPTOSTREAM_SECURE_RANDOM_IMPL_KEY, OsSecureRandom.class,
        Random.class);
  }

  public static String getRandomDevPath() {
    return System.getProperty(
        CRYPTOSTREAM_RANDOM_DEVICE_FILE_PATH_KEY,
        CRYPTOSTREAM_RANDOM_DEVICE_FILE_PATH_DEFAULT);
  }

  public static String getCryptoStreamLibPath() {
    return System.getProperty(CRYPTOSTREAM_LIB_PATH_KEY);
  }

  public static String getCryptoStreamLibName() {
    return System.getProperty(CRYPTOSTREAM_LIB_NAME_KEY);
  }

  public static String getCryptoStreamTmpDir() {
    return System.getProperty(CRYPTOSTREAM_TEMPDIR_KEY,
        System.getProperty("java.io.tmpdir"));
  }

  /** AES/CTR/NoPadding is required */
  public static void checkCodec(CryptoCodec codec) {
    if (codec.getCipherSuite() != CipherSuite.AES_CTR_NOPADDING) {
      throw new UnsupportedCodecException("AES/CTR/NoPadding is required");
    }
  }

  /** Check and floor buffer size */
  public static int checkBufferSize(CryptoCodec codec, int bufferSize) {
    Preconditions.checkArgument(bufferSize >= MIN_BUFFER_SIZE, 
        "Minimum value of buffer size is " + MIN_BUFFER_SIZE + ".");
    return bufferSize - bufferSize % codec.getCipherSuite()
        .getAlgorithmBlockSize();
  }
}
