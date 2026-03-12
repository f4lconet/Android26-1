package parser

import model.Player
import model.Position
import model.Team
import java.io.File

object CsvParser {
    private fun String?.toIntOrDefault(default: Int = 0): Int {
        return this?.toIntOrNull() ?: default
    }

    fun parseCsv(path: String): List<Player> {
        return File(path)
            .readLines()
            .drop(1)
            .map { parseString(it) }
    }

    fun parseString(string: String): Player {
        val playerInfo = string.split(";")

        return Player(
            playerInfo[0],
            Team(playerInfo[1],playerInfo[2]),
            Position.valueOf(playerInfo[3]),
            playerInfo[4],
            playerInfo[5].takeIf { it.isNotBlank() },
            playerInfo[6].toIntOrDefault(),
            playerInfo[7].toIntOrDefault(),
            playerInfo[8].toIntOrDefault(),
            playerInfo[9].toIntOrDefault(),
            playerInfo[10].toIntOrDefault(),
            playerInfo[11].toIntOrDefault(),
        )
    }
}
