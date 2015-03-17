package com.intel.cryptostream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.SecureRandom;

import com.intel.cryptostream.CipherSuite;
import com.intel.cryptostream.ConfigurationKeys;
import com.intel.cryptostream.CryptoCodec;
import com.intel.cryptostream.CryptoInputStream;
import com.intel.cryptostream.CryptoOutputStream;


public class TestCryptoCodec {
  public static void testCryptoCodec() throws IOException {
    SecureRandom random = new SecureRandom();
    int dataLen = 10000000;
    byte[] inputData = new byte[dataLen];
    byte[] outputData = new byte[dataLen];
    random.nextBytes(inputData);

    System.setProperty(ConfigurationKeys.CRYPTOSTREAM_CRYPTO_CODEC_CLASSES_KEY_PREFIX
        + CipherSuite.AES_CTR_NOPADDING.getConfigSuffix(),
        "com.intel.cryptostream.JceAesCtrCryptoCodec");

    // encrypt
    CryptoCodec codec = CryptoCodec.getInstance(CipherSuite.AES_CTR_NOPADDING);
    ByteArrayOutputStream aos = new ByteArrayOutputStream();
    BufferedOutputStream bos = new BufferedOutputStream(aos);
    byte[] key = new byte[16];
    byte[] iv = new byte[16];
    random.nextBytes(key);
    random.nextBytes(iv);
    CryptoOutputStream cos = new CryptoOutputStream(bos, codec, 
        1024, key, iv);
    cos.write(inputData, 0, inputData.length);
    cos.flush();

    // decrypt
    CryptoInputStream cis = new CryptoInputStream(
        new ByteArrayInputStream(aos.toByteArray()), codec, 
        1024, key, iv);
    int readLen = 0;
    int outOffset = 0;
    while (readLen < dataLen) {
      int n = cis.read(outputData, outOffset, outputData.length - outOffset);
      if (n >=0) {
        readLen += n;
        outOffset += n;
      }
    }

    for (int i = 0; i < dataLen; i++) {
      if (inputData[i] != outputData[i]) {
        System.out.println("decrypt failed: " + i);
        break;
      }
      
    }
    System.out.println("decrypt finished.");
  }

  public static void main(String[] args) throws IOException {
    TestCryptoCodec.testCryptoCodec();
  }
}
