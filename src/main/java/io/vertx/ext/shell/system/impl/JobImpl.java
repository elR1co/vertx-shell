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

package io.vertx.ext.shell.system.impl;

import io.vertx.core.Handler;

import io.vertx.ext.shell.system.Process;
import io.vertx.ext.shell.term.Tty;
import io.vertx.ext.shell.system.Job;
import io.vertx.ext.shell.system.ExecStatus;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
class JobImpl implements Job {

  final int id;
  final ShellImpl shell;
  final Process process;
  final String line;
  private volatile ExecStatus status;
  volatile long lastStopped; // When the job was last stopped
  volatile Tty tty;
  volatile Handler<Integer> terminateHandler;

  JobImpl(int id, ShellImpl shell, Process process, String line) {
    this.id = id;
    this.shell = shell;
    this.process = process;
    this.line = line;

    process.terminateHandler(status -> {
      JobImpl.this.status = ExecStatus.TERMINATED;
      shell.removeJob(JobImpl.this.id);
      if (terminateHandler != null) {
        terminateHandler.handle(status);
      }
    });
  }

  @Override
  public Job terminateHandler(Handler<Integer> handler) {
    terminateHandler = handler;
    return this;
  }

  @Override
  public boolean interrupt() {
    return process.interrupt();
  }

  @Override
  public void resume() {
    try {
      process.resume();
      status = ExecStatus.RUNNING;
    } catch (IllegalStateException ignore) {
    }
  }

  @Override
  public void suspend() {
    try {
      process.suspend();
      status = ExecStatus.STOPPED;
    } catch (IllegalStateException ignore) {
    }
  }

  @Override
  public void terminate() {
    status = ExecStatus.TERMINATED;
    process.terminate();
  }

  public long lastStopped() {
    return lastStopped;
  }

  public ExecStatus status() {
    return status;
  }

  public String line() {
    return line;
  }

  @Override
  public int id() {
    return id;
  }

  @Override
  public Tty getTty() {
    return tty;
  }

  @Override
  public Job setTty(Tty tty) {
    this.tty = tty;
    return this;
  }

  @Override
  public void run() {
    status = ExecStatus.RUNNING;
    process.setTty(tty);
    process.setSession(shell.session);
    process.run();
  }
}
