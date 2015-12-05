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

package io.vertx.groovy.ext.shell.command;
import groovy.transform.CompileStatic
import io.vertx.lang.groovy.InternalHelper
import io.vertx.core.json.JsonObject
import java.util.List
import io.vertx.groovy.core.cli.CommandLine
import io.vertx.groovy.ext.shell.io.Stream
import io.vertx.groovy.core.Vertx
import io.vertx.groovy.ext.shell.cli.CliToken
import io.vertx.core.Handler
import io.vertx.groovy.ext.shell.term.Tty
import io.vertx.groovy.ext.shell.session.Session
/**
 * The command process provides interaction with the process of the command provided by Vert.x Shell.
*/
@CompileStatic
public class CommandProcess extends Tty {
  private final def io.vertx.ext.shell.command.CommandProcess delegate;
  public CommandProcess(Object delegate) {
    super((io.vertx.ext.shell.command.CommandProcess) delegate);
    this.delegate = (io.vertx.ext.shell.command.CommandProcess) delegate;
  }
  public Object getDelegate() {
    return delegate;
  }
  /**
   * @return the current Vert.x instance
   * @return 
   */
  public Vertx vertx() {
    def ret= InternalHelper.safeCreate(this.delegate.vertx(), io.vertx.groovy.core.Vertx.class);
    return ret;
  }
  /**
   * @return the unparsed arguments tokens
   * @return 
   */
  public List<CliToken> argsTokens() {
    def ret = this.delegate.argsTokens()?.collect({underpants -> new io.vertx.groovy.ext.shell.cli.CliToken(underpants)});
      return ret;
  }
  /**
   * @return the actual string arguments of the command
   * @return 
   */
  public List<String> args() {
    def ret = this.delegate.args();
    return ret;
  }
  /**
   * @return the command line object or null
   * @return 
   */
  public CommandLine commandLine() {
    def ret= InternalHelper.safeCreate(this.delegate.commandLine(), io.vertx.groovy.core.cli.CommandLine.class);
    return ret;
  }
  /**
   * @return the shell session
   * @return 
   */
  public Session session() {
    def ret= InternalHelper.safeCreate(this.delegate.session(), io.vertx.groovy.ext.shell.session.Session.class);
    return ret;
  }
  /**
   * @return true if the command is running in foreground
   * @return 
   */
  public boolean isInForeground() {
    def ret = this.delegate.isInForeground();
    return ret;
  }
  public CommandProcess setStdin(Stream stdin) {
    ( /* Work around for https://jira.codehaus.org/browse/GROOVY-6970 */ (io.vertx.ext.shell.command.CommandProcess) this.delegate).setStdin((io.vertx.ext.shell.io.Stream)stdin.getDelegate());
    return this;
  }
  /**
   * Set an interrupt handler, this handler is called when the command is interrupted, for instance user
   * press <code>Ctrl-C</code>.
   * @param handler the interrupt handler
   * @return this command
   */
  public CommandProcess interruptHandler(Handler<Void> handler) {
    this.delegate.interruptHandler(handler);
    return this;
  }
  /**
   * Set a suspend handler, this handler is called when the command is suspended, for instance user
   * press <code>Ctrl-Z</code>.
   * @param handler the interrupt handler
   * @return this command
   */
  public CommandProcess suspendHandler(Handler<Void> handler) {
    this.delegate.suspendHandler(handler);
    return this;
  }
  /**
   * Set a resume handler, this handler is called when the command is resumed, for instance user
   * types <code>bg</code> or <code>fg</code> to resume the command.
   * @param handler the interrupt handler
   * @return this command
   */
  public CommandProcess resumeHandler(Handler<Void> handler) {
    this.delegate.resumeHandler(handler);
    return this;
  }
  /**
   * Set an end handler, this handler is called when the command is ended, for instance the command is running
   * and the shell closes.
   * @param handler the end handler
   * @return a reference to this, so the API can be used fluently
   */
  public CommandProcess endHandler(Handler<Void> handler) {
    this.delegate.endHandler(handler);
    return this;
  }
  /**
   * Write some text to the standard output.
   * @param text the text
   * @return a reference to this, so the API can be used fluently
   */
  public CommandProcess write(String text) {
    this.delegate.write(text);
    return this;
  }
  /**
   * Set a background handler, this handler is called when the command is running and put to background.
   * @param handler the background handler
   * @return this command
   */
  public CommandProcess backgroundHandler(Handler<Void> handler) {
    this.delegate.backgroundHandler(handler);
    return this;
  }
  /**
   * Set a foreground handler, this handler is called when the command is running and put to foreground.
   * @param handler the foreground handler
   * @return this command
   */
  public CommandProcess foregroundHandler(Handler<Void> handler) {
    this.delegate.foregroundHandler(handler);
    return this;
  }
  public CommandProcess resizehandler(Handler<Void> handler) {
    ( /* Work around for https://jira.codehaus.org/browse/GROOVY-6970 */ (io.vertx.ext.shell.command.CommandProcess) this.delegate).resizehandler(handler);
    return this;
  }
  /**
   * End the process with the exit status 
   */
  public void end() {
    this.delegate.end();
  }
  /**
   * End the process.
   * @param status the exit status.
   */
  public void end(int status) {
    this.delegate.end(status);
  }
}
