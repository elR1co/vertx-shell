require 'vertx-shell/command'
require 'vertx/vertx'
require 'vertx-shell/command_resolver'
require 'vertx/util/utils.rb'
# Generated from io.vertx.ext.shell.command.CommandRegistry
module VertxShell
  #  A registry that contains the commands known by a shell.<p/>
  # 
  #  It is a mutable command resolver.
  class CommandRegistry < ::VertxShell::CommandResolver
    # @private
    # @param j_del [::VertxShell::CommandRegistry] the java delegate
    def initialize(j_del)
      super(j_del)
      @j_del = j_del
    end
    # @private
    # @return [::VertxShell::CommandRegistry] the underlying java delegate
    def j_del
      @j_del
    end
    #  Get the shared registry for the Vert.x instance.
    # @param [::Vertx::Vertx] vertx the vertx instance
    # @return [::VertxShell::CommandRegistry] the shared registry
    def self.get_shared(vertx=nil)
      if vertx.class.method_defined?(:j_del) && !block_given?
        return ::Vertx::Util::Utils.safe_create(Java::IoVertxExtShellCommand::CommandRegistry.java_method(:getShared, [Java::IoVertxCore::Vertx.java_class]).call(vertx.j_del),::VertxShell::CommandRegistry)
      end
      raise ArgumentError, "Invalid arguments when calling get_shared(vertx)"
    end
    #  Create a new registry.
    # @param [::Vertx::Vertx] vertx the vertx instance
    # @return [::VertxShell::CommandRegistry] the created registry
    def self.create(vertx=nil)
      if vertx.class.method_defined?(:j_del) && !block_given?
        return ::Vertx::Util::Utils.safe_create(Java::IoVertxExtShellCommand::CommandRegistry.java_method(:create, [Java::IoVertxCore::Vertx.java_class]).call(vertx.j_del),::VertxShell::CommandRegistry)
      end
      raise ArgumentError, "Invalid arguments when calling create(vertx)"
    end
    # @param [::VertxShell::Command] command 
    # @yield 
    # @return [self]
    def register_command(command=nil)
      if command.class.method_defined?(:j_del) && !block_given?
        @j_del.java_method(:registerCommand, [Java::IoVertxExtShellCommand::Command.java_class]).call(command.j_del)
        return self
      elsif command.class.method_defined?(:j_del) && block_given?
        @j_del.java_method(:registerCommand, [Java::IoVertxExtShellCommand::Command.java_class,Java::IoVertxCore::Handler.java_class]).call(command.j_del,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ::Vertx::Util::Utils.safe_create(ar.result,::VertxShell::Command) : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling register_command(command)"
    end
    # @param [Array<::VertxShell::Command>] commands 
    # @yield 
    # @return [self]
    def register_commands(commands=nil)
      if commands.class == Array && !block_given?
        @j_del.java_method(:registerCommands, [Java::JavaUtil::List.java_class]).call(commands.map { |element| element.j_del })
        return self
      elsif commands.class == Array && block_given?
        @j_del.java_method(:registerCommands, [Java::JavaUtil::List.java_class,Java::IoVertxCore::Handler.java_class]).call(commands.map { |element| element.j_del },(Proc.new { |ar| yield(ar.failed ? ar.cause : nil, ar.succeeded ? ar.result.to_a.map { |elt| ::Vertx::Util::Utils.safe_create(elt,::VertxShell::Command) } : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling register_commands(commands)"
    end
    # @param [String] commandName 
    # @yield 
    # @return [self]
    def unregister_command(commandName=nil)
      if commandName.class == String && !block_given?
        @j_del.java_method(:unregisterCommand, [Java::java.lang.String.java_class]).call(commandName)
        return self
      elsif commandName.class == String && block_given?
        @j_del.java_method(:unregisterCommand, [Java::java.lang.String.java_class,Java::IoVertxCore::Handler.java_class]).call(commandName,(Proc.new { |ar| yield(ar.failed ? ar.cause : nil) }))
        return self
      end
      raise ArgumentError, "Invalid arguments when calling unregister_command(commandName)"
    end
  end
end
