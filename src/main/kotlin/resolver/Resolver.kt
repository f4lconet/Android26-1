package resolver

import model.Player
import model.Team

class Resolver(private val players: List<Player>) : IResolver {

    val playerPosition = mapOf(
        "DEFENDER" to "Защитник",
        "MIDFIELD" to "Полузащитник",
        "FORWARD" to "Нападающий",
        "GOALKEEPER" to "Вратарь"
    )

    override fun getCountWithoutAgency(): Int {
        var emptyAgencyCount = 0
        for(player in players) {
            if (player.agency.isNullOrBlank()) {
                emptyAgencyCount++
            }
        }
        return emptyAgencyCount
    }

    override fun getBestScorerDefender(): Pair<String, Int> {
        var bestScorerDefender: Pair<String, Int> = Pair("",0)
        var mostGoals = -1
        for(player in players) {
            if (player.position == "DEFENDER" &&  player.goals>mostGoals) {
                mostGoals = player.goals
                bestScorerDefender = Pair(player.name, player.goals)
            }
        }
        return bestScorerDefender
    }

    override fun getTheExpensiveGermanPlayerPosition(): String {
        val mostExpensiveGermanPlayer = players
            .filter { it.nationality == "Germany" }
            .maxByOrNull { it.transferCost }
        return if (mostExpensiveGermanPlayer != null) {
            playerPosition[mostExpensiveGermanPlayer.position] ?: mostExpensiveGermanPlayer.position
        } else {
            "Игрок не найден"
        }
    }

    override fun getTheRudestTeam(): Team {
        val playerByTeam = players.groupBy { it.team.name }
        val teamAvgRedCards = playerByTeam.map { (teamName, teamPlayers) ->
            val avgRedCards = teamPlayers.map { it.redCards }.average()
            teamName to avgRedCards
        }
        val rudestTeamPair = teamAvgRedCards.maxByOrNull { it.second }
        val rudestTeamName = rudestTeamPair?.first
        return players.first { it.team.name == rudestTeamName }.team
    }
}
