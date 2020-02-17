import com.arctouch.codechallenge.model.Genre
import com.arctouch.codechallenge.model.Movie

object FakeData {
     val movie = Movie(1, "Boneco do Mal 2", "Liza e sua jovem família se mudam para a Mansão Heelshire, em uma pequena vila na Inglaterra. Quando seu filho encontra um amigo no realista boneco Brahms, estranhos acontecimentos passam a cercar suas vidas de terror.", null, listOf(28, 12), "\\/dJdi4lwKE1kmCdhQsU0JPtKQYLQ.jpg", "\\/tE7SjDwITs333u4fBICqEmCRfgk.jpg", "2020-03-05")
     val listOfGenres = listOf(Genre(28, "Ação"), Genre(12, "Aventura"))
     val listOfMovie = listOf(movie)
}