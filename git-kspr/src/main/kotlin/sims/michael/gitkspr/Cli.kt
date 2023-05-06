package sims.michael.gitkspr

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.sources.PropertiesValueSource

class Status : CliktCommand() {
    private val appWiring by requireObject<AppWiring>()
    override fun run() {
        appWiring.gitKspr.getPullRequests()
    }
}

class GitKsprCommand : CliktCommand() {
    init {
        context {
            valueSource = PropertiesValueSource
                .from(
                    "${System.getenv("HOME")}/.git-kspr.properties"
                )
        }
        subcommands(
            Status()
        )
    }

    private val gitKsprToken by option(envvar = "GIT_KSPR_TOKEN").required()

    override fun run() {
        currentContext.findOrSetObject<AppWiring> { DefaultAppWiring(gitKsprToken) }
    }
}

fun main(args: Array<out String>) {
    GitKsprCommand().main(args)
}