package sims.michael.gitkspr

import com.expediagroup.graphql.client.GraphQLClient
import kotlinx.coroutines.runBlocking
import sims.michael.gitkspr.generated.PullRequests

class GitKspr(val client: GraphQLClient<*>) {
    fun getPullRequests() {
        runBlocking {
            val response = client.execute(
                PullRequests(
                    PullRequests.Variables(
                        repo_owner = "MichaelSims",
                        repo_name = "git-spr-demo"
                    )
                )
            )
            println(response.data?.viewer?.login)
        }
    }
}