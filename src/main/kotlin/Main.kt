import model.Player
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.data.general.DefaultPieDataset
import parser.CsvParser
import resolver.Resolver
import javax.swing.JFrame
import javax.swing.WindowConstants


fun main(args: Array<String>) {
    val players = CsvParser.parseCsv("src/main/resources/fakePlayers.csv")
    val resolver = Resolver(players)
    val emptyAgencyCount = resolver.getCountWithoutAgency()
    println("Количество игроков, интересы которых не представляет агенство: $emptyAgencyCount")

    val bestScorerDefender = resolver.getBestScorerDefender()
    println("Автор наибольшего числа голов из числа защитников и их количество: ${bestScorerDefender.first}, ${bestScorerDefender.second}")

    val mostExpensiveGermanPlayer = resolver.getTheExpensiveGermanPlayerPosition()
    println("Русское название позиции самого дорогого немецкого игрока: $mostExpensiveGermanPlayer")

    val rudestTeam = resolver.getTheRudestTeam()
    println("Команда с наибольшим средним числом красных карточек на одного игрока: ${rudestTeam.name} (${rudestTeam.city})")

    createNationalityPieChart(players)
}

fun createNationalityPieChart(players: List<Player>) {
    val nationalityPlayersCount = players.groupingBy { it.nationality }
        .eachCount()
        .toList()
        .sortedByDescending { it.second }
    val dataset = DefaultPieDataset<String>()

    nationalityPlayersCount.forEach { (country, count) ->
        dataset.setValue(country, count.toDouble())
    }

    val chart = ChartFactory.createPieChart(
        "Соотношение количества игроков из разных стран",
        dataset,
        true,
        true,
        false
    )

    JFrame("Национальности").apply {
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        contentPane.add(ChartPanel(chart))
        setSize(800, 600)
        setLocationRelativeTo(null)
        isVisible = true
    }
}
