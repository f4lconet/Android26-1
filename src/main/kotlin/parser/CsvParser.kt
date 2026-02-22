package parser

import model.Player
import model.Team
import java.io.File

object CsvParser {
    fun parseCsv(path: String): List<Player> {
        val strings = File(path).readLines()
        return strings.drop(1).map {
            parseString(it)
        }
    }

    fun parseString(string: String): Player {
        val playerInfo = string.split(";")
        val teamInfo = Team(playerInfo[1],playerInfo[2])
        return Player(
            playerInfo[0],
            teamInfo,
            playerInfo[2],
            playerInfo[3],
            playerInfo[4],
            playerInfo[5],
            playerInfo[6].toIntOrNull()?:0,
            playerInfo[7].toIntOrNull()?:0,
            playerInfo[8].toIntOrNull()?:0,
            playerInfo[9].toIntOrNull()?:0,
            playerInfo[10].toIntOrNull()?:0,
            playerInfo[11].toIntOrNull()?:0
        )
    }
}
