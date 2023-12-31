package com.github.bspammer.klox

import com.github.bspammer.klox.scanner.Scanner
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import kotlin.system.exitProcess
import kotlin.text.Charsets.UTF_8


object Lox {
    var hadError: Boolean = false

    fun main(args: Array<String>) {
        if (args.size > 1) {
            println("Usage: jlox [script]");
            exitProcess(64);
        } else if (args.size == 1) {
            runFile(args[0]);
        } else {
            runPrompt();
        }
    }

    fun runFile(path: String) {
        run(File(path).readText(UTF_8))
        if (hadError) exitProcess(65)
    }

    fun runPrompt() {
        val input = InputStreamReader(System.`in`)
        val reader = BufferedReader(input)

        while (true) {
            print("> ")
            val line = reader.readLine() ?: break
            run(line)
            hadError = false;
        }
    }

    fun run(source: String) {
        val scanner = Scanner(source)
        val tokens = scanner.scanTokens()

        for (token in tokens) {
            println(token)
        }
    }

    fun error(line: Int, message: String, where: String = "") {
        System.err.println("[line $line] Error$where: $message")
        hadError = true
    }

}
