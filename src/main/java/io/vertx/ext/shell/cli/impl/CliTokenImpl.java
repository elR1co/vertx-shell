/*
 * Copyright 2015 Red Hat, Inc.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  and Apache License v2.0 which accompanies this distribution.
 *
 *  The Eclipse Public License is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  The Apache License v2.0 is available at
 *  http://www.opensource.org/licenses/apache2.0.php
 *
 *  You may elect to redistribute this code under either of these licenses.
 *
 *
 * Copyright (c) 2015 The original author or authors
 * ------------------------------------------------------
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 *
 *     The Eclipse Public License is available at
 *     http://www.eclipse.org/legal/epl-v10.html
 *
 *     The Apache License v2.0 is available at
 *     http://www.opensource.org/licenses/apache2.0.php
 *
 * You may elect to redistribute this code under either of these licenses.
 *
 */

package io.vertx.ext.shell.cli.impl;

import io.termd.core.readline.LineStatus;
import io.vertx.ext.shell.cli.CliToken;

import java.util.LinkedList;
import java.util.List;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class CliTokenImpl implements CliToken {

  final boolean text;
  final String raw;
  final String value;

  public CliTokenImpl(boolean text, String value) {
    this(text, value, value);
  }

  public CliTokenImpl(boolean text, String raw, String value) {
    this.text = text;
    this.raw = raw;
    this.value = value;
  }

  @Override
  public boolean isText() {
    return text;
  }

  @Override
  public boolean isBlank() {
    return !text;
  }

  public String raw() {
    return raw;
  }

  public String value() {
    return value;
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    } else if (obj instanceof CliTokenImpl) {
      CliTokenImpl that = (CliTokenImpl) obj;
      return text == that.text && value.equals(that.value);
    }
    return false;
  }

  @Override
  public String toString() {
    return "CliToken[text=" + text + ",value=" + value + "]";
  }

  public static List<CliToken> tokenize(String s) {

    List<CliToken> tokens = new LinkedList<>();

    tokenize(s, 0, tokens);

    return tokens;

  }

  private static void tokenize(String s, int index, List<CliToken> builder) {
    while (index < s.length()) {
      char c = s.charAt(index);
      switch (c) {
        case ' ':
        case '\t':
          index = blankToken(s, index, builder);
          break;
        default:
          index = textToken(s, index, builder);
          break;
      }
    }
  }

  // Todo use code points and not chars
  private static int textToken(String s, int index, List<CliToken> builder) {
    LineStatus quoter = new LineStatus();
    int from = index;
    StringBuilder value = new StringBuilder();
    while (index < s.length()) {
      char c = s.charAt(index);
      quoter.accept(c);
      if (!quoter.isQuoted() && !quoter.isEscaped() && isBlank(c)) {
        break;
      }
      if (quoter.isCodePoint()) {
        if (quoter.isEscaped() && quoter.isWeaklyQuoted() && c != '"') {
          value.append('\\');
        }
        value.append(c);
      }
      index++;
    }
    builder.add(new CliTokenImpl(true, s.substring(from, index), value.toString()));
    return index;
  }

  private static int blankToken(String s, int index, List<CliToken> builder) {
    int from = index;
    while (index < s.length() && isBlank(s.charAt(index))) {
      index++;
    }
    builder.add(new CliTokenImpl(false, s.substring(from, index)));
    return index;
  }

  private static boolean isBlank(char c) {
    return c == ' ' || c == '\t';
  }
}
