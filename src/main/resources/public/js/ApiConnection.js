const movieForm = document.getElementById('movie-form');
const movieTitleInput = document.getElementById('movie-title');
const movieInfoDiv = document.getElementById('movie-info');

movieForm.addEventListener('submit', (e) => {
    e.preventDefault();
    const movieTitle = movieTitleInput.value.toLowerCase();

    fetch(`/api/movies?title=${movieTitle}`)
        .then(response => response.json())
        .then(data => {
            movieInfoDiv.innerHTML = '';
            if (data.movie) {
                const movieInfo = createMovieInfo(data.movie);
                movieInfoDiv.appendChild(movieInfo);
            } else {
                const noMovie = document.createElement('p');
                noMovie.textContent = 'No se encontró la película.';
                movieInfoDiv.appendChild(noMovie);
            }
        })
        .catch(error => console.error('Error:', error));
});

function createMovieInfo(movie) {
    const movieInfo = document.createElement('div');
    movieInfo.classList.add('movie-info');

    const title = document.createElement('h2');
    title.textContent = movie.Title;
    movieInfo.appendChild(title);

    const year = document.createElement('p');
    year.textContent = `Año: ${movie.Year}`;
    movieInfo.appendChild(year);

    const genre = document.createElement('p');
    genre.textContent = `Género: ${movie.Genre}`;
    movieInfo.appendChild(genre);

    const rating = document.createElement('p');
    rating.textContent = `Calificación: ${movie.imdbRating}`;
    movieInfo.appendChild(rating);

    const actors = document.createElement('p');
    actors.textContent = `Actores: ${movie.Actors}`;
    movieInfo.appendChild(actors);

    const director = document.createElement('p');
    director.textContent = `Director: ${movie.Director}`;
    movieInfo.appendChild(director);

    const plot = document.createElement('p');
    plot.textContent = `Sinopsis: ${movie.Plot}`;
    movieInfo.appendChild(plot);

    return movieInfo;
}