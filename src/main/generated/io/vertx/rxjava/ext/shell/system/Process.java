/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package io.vertx.rxjava.ext.shell.system;

import java.util.Map;
import rx.Observable;
import io.vertx.ext.shell.system.ExecStatus;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.shell.term.Tty;
import io.vertx.rxjava.ext.shell.session.Session;

/**
 * A process managed by the shell.
 *
 * <p/>
 * NOTE: This class has been automatically generated from the {@link io.vertx.ext.shell.system.Process original} non RX-ified interface using Vert.x codegen.
 */

public class Process {

  final io.vertx.ext.shell.system.Process delegate;

  public Process(io.vertx.ext.shell.system.Process delegate) {
    this.delegate = delegate;
  }

  public Object getDelegate() {
    return delegate;
  }

  /**
   * @return the current process status
   * @return 
   */
  public ExecStatus status() { 
    ExecStatus ret = delegate.status();
    return ret;
  }

  /**
   * @return the process exit code when the status is  otherwise <code>null</code>
   * @return 
   */
  public Integer exitCode() { 
    Integer ret = delegate.exitCode();
    return ret;
  }

  /**
   * Set the process tty.
   * @param tty the process tty
   * @return this object
   */
  public Process setTty(Tty tty) { 
    delegate.setTty((io.vertx.ext.shell.term.Tty)tty.getDelegate());
    return this;
  }

  /**
   * @return the process tty
   * @return 
   */
  public Tty getTty() { 
    if (cached_0 != null) {
      return cached_0;
    }
    Tty ret = Tty.newInstance(delegate.getTty());
    cached_0 = ret;
    return ret;
  }

  /**
   * Set the process session
   * @param session the process session
   * @return this object
   */
  public Process setSession(Session session) { 
    delegate.setSession((io.vertx.ext.shell.session.Session)session.getDelegate());
    return this;
  }

  /**
   * @return the process session
   * @return 
   */
  public Session getSession() { 
    if (cached_1 != null) {
      return cached_1;
    }
    Session ret = Session.newInstance(delegate.getSession());
    cached_1 = ret;
    return ret;
  }

  /**
   * Set an handler for being notified when the process terminates.
   * @param handler the handler called when the process terminates.
   * @return this object
   */
  public Process terminatedHandler(Handler<Integer> handler) { 
    delegate.terminatedHandler(handler);
    return this;
  }

  /**
   * Run the process.
   */
  public void run() { 
    delegate.run();
  }

  /**
   * Run the process.
   * @param foreground 
   */
  public void run(boolean foreground) { 
    delegate.run(foreground);
  }

  /**
   * Run the process.
   * @param completionHandler handler called after process callback
   */
  public void run(Handler<Void> completionHandler) { 
    delegate.run(new Handler<java.lang.Void>() {
      public void handle(java.lang.Void event) {
        completionHandler.handle(event);
      }
    });
  }

  /**
   * Run the process.
   * @param foregraound 
   * @param completionHandler handler called after process callback
   */
  public void run(boolean foregraound, Handler<Void> completionHandler) { 
    delegate.run(foregraound, new Handler<java.lang.Void>() {
      public void handle(java.lang.Void event) {
        completionHandler.handle(event);
      }
    });
  }

  /**
   * Attempt to interrupt the process.
   * @return true if the process caught the signal
   */
  public boolean interrupt() { 
    boolean ret = delegate.interrupt();
    return ret;
  }

  /**
   * Attempt to interrupt the process.
   * @param completionHandler handler called after interrupt callback
   * @return true if the process caught the signal
   */
  public boolean interrupt(Handler<Void> completionHandler) { 
    boolean ret = delegate.interrupt(new Handler<java.lang.Void>() {
      public void handle(java.lang.Void event) {
        completionHandler.handle(event);
      }
    });
    return ret;
  }

  /**
   * Suspend the process.
   */
  public void resume() { 
    delegate.resume();
  }

  /**
   * Suspend the process.
   * @param foreground 
   */
  public void resume(boolean foreground) { 
    delegate.resume(foreground);
  }

  /**
   * Suspend the process.
   * @param completionHandler handler called after resume callback
   */
  public void resume(Handler<Void> completionHandler) { 
    delegate.resume(new Handler<java.lang.Void>() {
      public void handle(java.lang.Void event) {
        completionHandler.handle(event);
      }
    });
  }

  /**
   * Suspend the process.
   * @param foreground 
   * @param completionHandler handler called after resume callback
   */
  public void resume(boolean foreground, Handler<Void> completionHandler) { 
    delegate.resume(foreground, new Handler<java.lang.Void>() {
      public void handle(java.lang.Void event) {
        completionHandler.handle(event);
      }
    });
  }

  /**
   * Resume the process.
   */
  public void suspend() { 
    delegate.suspend();
  }

  /**
   * Resume the process.
   * @param completionHandler handler called after suspend callback
   */
  public void suspend(Handler<Void> completionHandler) { 
    delegate.suspend(new Handler<java.lang.Void>() {
      public void handle(java.lang.Void event) {
        completionHandler.handle(event);
      }
    });
  }

  /**
   * Terminate the process.
   */
  public void terminate() { 
    delegate.terminate();
  }

  /**
   * Terminate the process.
   * @param completionHandler handler called after end callback
   */
  public void terminate(Handler<Void> completionHandler) { 
    delegate.terminate(new Handler<java.lang.Void>() {
      public void handle(java.lang.Void event) {
        completionHandler.handle(event);
      }
    });
  }

  /**
   * Set the process in background.
   */
  public void toBackground() { 
    delegate.toBackground();
  }

  /**
   * Set the process in background.
   * @param completionHandler handler called after background callback
   */
  public void toBackground(Handler<Void> completionHandler) { 
    delegate.toBackground(new Handler<java.lang.Void>() {
      public void handle(java.lang.Void event) {
        completionHandler.handle(event);
      }
    });
  }

  /**
   * Set the process in foreground.
   */
  public void toForeground() { 
    delegate.toForeground();
  }

  /**
   * Set the process in foreground.
   * @param completionHandler handler called after foreground callback
   */
  public void toForeground(Handler<Void> completionHandler) { 
    delegate.toForeground(new Handler<java.lang.Void>() {
      public void handle(java.lang.Void event) {
        completionHandler.handle(event);
      }
    });
  }

  private Tty cached_0;
  private Session cached_1;

  public static Process newInstance(io.vertx.ext.shell.system.Process arg) {
    return arg != null ? new Process(arg) : null;
  }
}
