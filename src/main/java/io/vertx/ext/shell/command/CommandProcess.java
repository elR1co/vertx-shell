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

package io.vertx.ext.shell.command;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.cli.CommandLine;
import io.vertx.ext.shell.session.Session;
import io.vertx.ext.shell.term.Tty;
import io.vertx.ext.shell.cli.CliToken;

import java.util.List;

/**
 * The command process provides interaction with the process of the command provided by Vert.x Shell.
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface CommandProcess extends Tty {

  /**
   * @return the current Vert.x instance
   */
  Vertx vertx();

  /**
   * @return the unparsed arguments tokens
   */
  List<CliToken> argsTokens();

  /**
   * @return the actual string arguments of the command
   */
  List<String> args();

  /**
   * @return the command line object or null
   */
  CommandLine commandLine();

  /**
   * @return the shell session
   */
  Session session();

  /**
   * @return true if the command is running in foreground
   */
  boolean isForeground();

  @Fluent
  CommandProcess stdinHandler(Handler<String> handler);

  /**
   * Set an interrupt handler, this handler is called when the command is interrupted, for instance user
   * press <code>Ctrl-C</code>.
   *
   * @param handler the interrupt handler
   * @return this command
   */
  @Fluent
  CommandProcess interruptHandler(Handler<Void> handler);

  /**
   * Set a suspend handler, this handler is called when the command is suspended, for instance user
   * press <code>Ctrl-Z</code>.
   *
   * @param handler the interrupt handler
   * @return this command
   */
  @Fluent
  CommandProcess suspendHandler(Handler<Void> handler);

  /**
   * Set a resume handler, this handler is called when the command is resumed, for instance user
   * types <code>bg</code> or <code>fg</code> to resume the command.
   *
   * @param handler the interrupt handler
   * @return this command
   */
  @Fluent
  CommandProcess resumeHandler(Handler<Void> handler);

  /**
   * Set an end handler, this handler is called when the command is ended, for instance the command is running
   * and the shell closes.
   *
   * @param handler the end handler
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  CommandProcess endHandler(Handler<Void> handler);

  /**
   * Write some text to the standard output.
   *
   * @param data the text
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  CommandProcess write(String data);

  /**
   * Set a background handler, this handler is called when the command is running and put to background.
   *
   * @param handler the background handler
   * @return this command
   */
  @Fluent
  CommandProcess backgroundHandler(Handler<Void> handler);

  /**
   * Set a foreground handler, this handler is called when the command is running and put to foreground.
   *
   * @param handler the foreground handler
   * @return this command
   */
  @Fluent
  CommandProcess foregroundHandler(Handler<Void> handler);

  @Override
  CommandProcess resizehandler(Handler<Void> handler);

  /**
   * End the process with the exit status {@literal 0}
   */
  void end();

  /**
   * End the process.
   *
   * @param status the exit status.
   */
  void end(int status);

}
