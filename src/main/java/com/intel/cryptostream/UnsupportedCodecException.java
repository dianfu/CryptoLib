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

/**
 * Thrown to indicate that the specific codec is not supported.
 */
public class UnsupportedCodecException extends RuntimeException {

  /** Default constructor */
  public UnsupportedCodecException() {
  }

  /**
   * Constructs an UnsupportedCodecException with the specified
   * detail message.
   * 
   * @param message the detail message
   */
  public UnsupportedCodecException(String message) {
    super(message);
  }

  /**
   * Constructs a new exception with the specified detail message and
   * cause.
   * 
   * @param message the detail message
   * @param cause the cause
   */
  public UnsupportedCodecException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructs a new exception with the specified cause.
   * 
   * @param cause the cause
   */
  public UnsupportedCodecException(Throwable cause) {
    super(cause);
  }

  private static final long serialVersionUID = 6713920435487942224L;
}