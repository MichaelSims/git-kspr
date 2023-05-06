package sims.michael.gitkspr

import com.expediagroup.graphql.client.GraphQLClient
import com.expediagroup.graphql.client.ktor.GraphQLKtorClient
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import java.net.URL

interface AppWiring {
    val gitKspr: GitKspr
}

class DefaultAppWiring(private val githubToken: String) : AppWiring {
    private val bearerTokens by lazy {
        BearerTokens(githubToken, githubToken)
    }

    private val httpClient by lazy {
        HttpClient(engineFactory = CIO)
            .config {
                install(Auth) {
                    bearer {
                        loadTokens {
                            bearerTokens
                        }
                    }
                }
            }
    }

    private val graphQLClient: GraphQLClient<*> by lazy {
        GraphQLKtorClient(URL("https://api.github.com/graphql"), httpClient)
    }

    override val gitKspr: GitKspr by lazy { GitKspr(graphQLClient) }
}