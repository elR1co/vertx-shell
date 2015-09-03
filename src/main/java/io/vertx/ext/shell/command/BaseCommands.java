package io.vertx.ext.shell.command;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Vertx;
import io.vertx.core.file.FileProps;
import io.vertx.ext.shell.cli.Completion;
import io.vertx.ext.shell.getopt.GetOptCommand;
import io.vertx.ext.shell.getopt.GetOptCommandProcess;
import io.vertx.ext.shell.registry.CommandRegistration;
import io.vertx.ext.shell.registry.CommandRegistry;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface BaseCommands {

  static Command ls() {
    Command cmd = Command.command("ls");
    cmd.completeHandler(completion -> {
      String last;
      int s = completion.lineTokens().size();
      if (s > 0 && completion.lineTokens().get(s - 1).isText()) {
        last = completion.lineTokens().get(s - 1).value();
      } else {
        last = "";
      }
      Vertx vertx = completion.vertx();
      vertx.<Runnable>executeBlocking(fut -> {
        List<String> files;
        String name;
        if (last.isEmpty() || last.lastIndexOf('/') == -1) {
          name = last.substring(last.lastIndexOf('/') + 1);
          files = vertx.
              fileSystem().
              readDirBlocking(".").stream().
              map(file -> file.substring(file.lastIndexOf('/') + 1)).
              collect(Collectors.toList());
        } else {
          String path;
          if (last.startsWith("/") && last.indexOf('/', 1) == -1) {
            name = last.substring(1);
            path = "/";
          } else {
            name = last.substring(last.lastIndexOf('/') + 1);
            path = last.substring(0, last.lastIndexOf('/'));
          }
          files = vertx.
              fileSystem().
              readDirBlocking(path).stream().
              map(file -> file.substring(file.lastIndexOf('/') + 1)).
              collect(Collectors.toList());
        }
        Runnable done = () -> {
          List<String> matches = files.stream().filter(file -> file.startsWith(name)).collect(Collectors.toList());
          if (matches.isEmpty()) {
            completion.complete(Collections.emptyList());
          } else if (matches.size() == 1) {
            boolean terminal = true;
            String compl = matches.get(0).substring(name.length());
            FileProps props = vertx.fileSystem().propsBlocking(last + compl);
            if (props.isDirectory()) {
              compl += "/";
              terminal = false;
            }
            completion.complete(compl, terminal);
          } else {
            String common = Completion.findLongestCommonPrefix(matches);
            if (common.length() > name.length()) {
              completion.complete(common.substring(name.length()), false);
            } else {
              completion.complete(matches);
            }
          }
        };
        fut.complete(done);
      }, res -> {
        if (res.succeeded()) {
          res.result().run();
        } else {
          completion.complete(Collections.emptyList());
        }
      });
    });
    cmd.processHandler(process -> {
      String path = process.args().stream().findFirst().orElse(".");
      Vertx vertx = process.vertx();
      vertx.fileSystem().props(path, ar1 -> {
        if (ar1.succeeded()) {
          vertx.fileSystem().readDir(path, ar2 -> {
            if (ar1.succeeded()) {
              List<String> files = ar2.result();
              for (String file : files) {
                String name = file.substring(file.lastIndexOf('/') + 1);
                process.write(name + "\n");
              }
            } else {
              ar1.cause().printStackTrace();
            }
            process.end();
          });
        } else {
          process.write("ls: " + path + ": No such file or directory");
          process.end();
        }
      });
    });
    return cmd;
  }

  static Command sleep() {
    class SleepImpl {

      void run(GetOptCommandProcess process) {
        if (process.arguments().isEmpty()) {
          process.write("usage: sleep seconds\n");
          process.end();
        } else {
          String arg = process.arguments().get(0);
          int seconds = -1;
          try {
            seconds = Integer.parseInt(arg);
          } catch (NumberFormatException ignore) {
          }
          scheduleSleep(process, seconds * 1000);
        }
      }

      void scheduleSleep(GetOptCommandProcess process, long millis) {
        Vertx vertx = process.vertx();
        if (millis > 0) {
          System.out.println("Scheduling timer " + millis);
          long now = System.currentTimeMillis();
          AtomicLong remaining = new AtomicLong(-1);
          long id = process.vertx().setTimer(millis, v -> {
            process.end();
          });
          process.eventHandler("SIGINT", v -> {
            if (vertx.cancelTimer(id)) {
              System.out.println("Cancelling timer");
              process.end();
            }
          });
          process.eventHandler("SIGTSTP", v -> {
            if (vertx.cancelTimer(id)) {
              remaining.set(millis - (System.currentTimeMillis() - now));
              System.out.println("Suspending timer " + remaining.get());
            }
          });
          process.eventHandler("SIGCONT", v -> {
            scheduleSleep(process, remaining.get());
          });
        } else {
          process.end();
        }
      }
    }

    SleepImpl sleep = new SleepImpl();
    GetOptCommand sleepCmd = GetOptCommand.create("sleep");
    sleepCmd.processHandler(sleep::run);
    return sleepCmd.build();
  }

  static Command echo() {
    Command echo = Command.command("echo");
    echo.processHandler(process -> {
      process.argsTokens().forEach(token -> {
        if (token.isText()) {
          process.write(token.value());
        } else {
          process.write(" ");
        }
      });
      process.write("\n");
      process.end();
    });
    return echo;
  }

  static Command help() {
    Command help = Command.command("help");
    help.processHandler(process -> {
      CommandRegistry manager = CommandRegistry.get(process.vertx());
      manager.registrations();
      process.write("available commands:\n");
      for (CommandRegistration command : manager.registrations()) {
        process.write(command.command().name()).write("\n");
      }
      process.end();
    });
    return help;
  }
}
