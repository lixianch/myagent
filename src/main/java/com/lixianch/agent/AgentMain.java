package com.lixianch.agent;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.nameStartsWith;

/**
 * created by lixianch on 2023/2/10
 */
public class AgentMain {
    public static void premain(String args, Instrumentation inst) {
        System.out.println("start process premain");
        final ByteBuddy byteBuddy = new ByteBuddy();
        new AgentBuilder.Default(byteBuddy).ignore(nameStartsWith("net.bytebuddy.")
                .or(nameStartsWith("org.slf4j.")
                        .or(nameStartsWith("org.apache.logging.")
                                .or(nameStartsWith("org.groovy."))
                                .or(nameStartsWith("javassist"))
                                .or(nameStartsWith(".asm."))
                                .or(nameStartsWith("sun.reflect"))
                                .or(ElementMatchers.isSynthetic()))))
                //你想切面的包名
                .type(ElementMatchers.nameStartsWith("com.github.shoothzj.agent.test"))
                .transform(new AgentTransformer())
                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
                .installOn(inst);
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
        System.out.println("start agent main");
    }
}
