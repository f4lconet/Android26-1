package resolver

import model.Player
import model.Position
import model.Team

class Resolver(private val players: List<Player>) : IResolver {

    override fun getCountWithoutAgency(): Int {
        return players.count { it.agency.isNullOrBlank() }
    }

    override fun getBestScorerDefender(): Pair<String, Int> {
       return players
           .filter { it.position == Position.DEFENDER }
           .maxByOrNull { it.goals }
           ?.let { Pair(it.name, it.goals) }
           ?: Pair("",0)
    }

    override fun getTheExpensiveGermanPlayerPosition(): String {
        val mostExpensiveGermanPlayer = players
            .filter { it.nationality == "Germany" }
            .maxByOrNull { it.transferCost }

        return when (mostExpensiveGermanPlayer?.position) {
            Position.DEFENDER -> "Защитник"
            Position.MIDFIELD -> "Полузащитник"
            Position.FORWARD -> "Нападающий"
            Position.GOALKEEPER -> "Вратарь"
            null -> "Игрок не найден"
        }
    }

    override fun getTheRudestTeam(): Team {
        val rudestTeamName = players
            .groupBy { it.team.name }
            .map { (teamName, teamPlayers) ->
                teamName to teamPlayers.map { it.redCards }.average()
            }
            .maxByOrNull { it.second }
            ?.first
        return players.first { it.team.name == rudestTeamName }.team
    }
}
