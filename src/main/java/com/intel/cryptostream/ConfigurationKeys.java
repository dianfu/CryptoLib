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

public class ConfigurationKeys {
  public static final String CRYPTOSTREAM_SYSTEM_PROPERTIES_FILE =
      "com-intel-cryptostream.properties";

  public static final String CRYPTOSTREAM_CRYPTO_CODEC_CLASSES_KEY_PREFIX = 
      "cryptostream.crypto.codec.classes";
  public static final String CRYPTOSTREAM_CRYPTO_CIPHER_SUITE_KEY =
      "cryptostream.crypto.cipher.suite";
  public static final String CRYPTOSTREAM_CRYPTO_CIPHER_SUITE_DEFAULT = 
      "AES/CTR/NoPadding";

  public static final String CRYPTOSTREAM_RANDOM_DEVICE_FILE_PATH_KEY = 
      "cryptostream.random.device.file.path";
  public static final String CRYPTOSTREAM_RANDOM_DEVICE_FILE_PATH_DEFAULT = 
      "/dev/urandom";

  public static final String CRYPTOSTREAM_CRYPTO_JCE_PROVIDER_KEY =
      "cryptostream.crypto.jce.provider";
  public static final String CRYPTOSTREAM_JAVA_SECURE_RANDOM_ALGORITHM_KEY = 
      "cryptostream.java.secure.random.algorithm";
  public static final String CRYPTOSTREAM_JAVA_SECURE_RANDOM_ALGORITHM_DEFAULT = 
      "SHA1PRNG";

  public static final String CRYPTOSTREAM_SECURE_RANDOM_IMPL_KEY = 
      "cryptostream.secure.random.impl";

  public static final int CRYPTOSTREAM_CRYPTO_BUFFER_SIZE_DEFAULT = 8192;
  public static final String CRYPTOSTREAM_CRYPTO_BUFFER_SIZE_KEY = 
      "cryptostream.crypto.buffer.size";

  public static final String CRYPTOSTREAM_LIB_PATH_KEY = "cryptostream.lib.path";
  public static final String CRYPTOSTREAM_LIB_NAME_KEY = "cryptostream.lib.name";
  public static final String CRYPTOSTREAM_TEMPDIR_KEY = "cryptostream.tempdir";
}
