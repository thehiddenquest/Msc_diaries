const express = require("express");
const path = require("path");
const bodyParser = require("body-parser");

const db = require("./db");
const app = express();

//used to parse incoming requests with JSON payloads.
app.use(express.json());

//Getting frontend folder
app.use(express.static(path.join(__dirname, "../FrontEnd")));

//middleware
app.use(bodyParser.urlencoded({ extended: true }));

//Starting page
app.get("/", (req, res) => {
  res.sendFile("/index.html");
});

//Get Production Company Name
app.get("/getProductionCompanyName", (req, res) => {
  const sql = "SELECT `Name` from `Production_Company`";
  db.query(sql, (err, result) => {
    if (err) throw err;
    console.log(result);
    res.send(result);
  });
});

//Get Movie Name
app.get("/getMovieName", (req, res) => {
  const sql = "SELECT `Title`,`Year` from `Movie`";
  db.query(sql, (err, result) => {
    if (err) throw err;
    res.send(result);
  });
});

// Get Genre Type
app.get("/getGenreType", (req, res) => {
  const sql = "SELECT `Type` from `Genre`";
  db.query(sql, (err, result) => {
    if (err) throw err;
    res.send(result);
  });
});

//Insert Movie Details
app.post("/insertMovieDetails", (req, res) => {
  const {
    movieTitle,
    movieYear,
    MoviePlotOutline,
    movieLength,
    productionCompany,
    genre,
  } = req.body;

  db.beginTransaction((err) => {
    if (err) {
      return res.status(500).json({ error: "Cannot Begin Transaction" });
    }
    const sqlInsertMovie =
      "INSERT INTO Movie (Title, Year, Length, Plot_outline, Production_Company_Name) VALUES (?,?,?,?,?)";
    db.query(
      sqlInsertMovie,
      [movieTitle, movieYear, movieLength, MoviePlotOutline, productionCompany],
      (err, result) => {
        if (err) {
          return db.rollback(() => {
            if (err.code === "ER_DUP_ENTRY") {
              res.status(500).json({
                error: "Duplicate entry.",
              });
            } else {
              res
                .status(500)
                .json({ error: "Database error. Please try again later." });
            }
          });
        }
        const sqlInsertGenre =
          "INSERT INTO Movie_Genre (Movie_Title, Movie_Year, Genre_Type) VALUES (?,?,?)";
        genre.forEach((genre) => {
          db.query(
            sqlInsertGenre,
            [movieTitle, movieYear, genre],
            (err, result) => {
              if (err) {
                return db.rollback(() => {
                  res
                    .status(500)
                    .json({ error: "Database error. Please try again later." });
                });
              }
            }
          );
        });
        db.commit((err) => {
          if (err) {
            return db.rollback(() => {
              res.status(500).json({
                error: "Database error. Please try again later.",
              });
            });
          }
          console.log("Transaction committed successfully");
          res.json({ message: "Movie details inserted successfully" });
        });
      }
    );
  });
});

// //Insert Actor Details
// app.post("/insertActorDetails", (req, res) => {
//   const { actorName, actorDOB, movieName, movieYear, actorQuote } = req.body;
//   const sqlInsertActor = "INSERT INTO actor(name,date_of_birth) VALUES (?,?,?)";
//   const sqlInsertMovieQuote =
//     "INSERT INTO quotes(movie_title,movie_year,movie_quotation,actor_name) VALUES(?,?,?,?)";
//   db.beginTransaction((err) => {
//     if (err) {
//       return res
//         .status(500)
//         .json({ error: "Database error. Please try again later." });
//     }
//     db.query(sqlInsertActor, [actorName, actorDOB], (err, result) => {
//       if (err) {
//         return db.rollback(() => {
//           if (err.code === "ER_DUP_ENTRY") {
//             res.status(500).json({
//               error: "Duplicate entry.",
//             });
//           } else {
//             res
//               .status(500)
//               .json({ error: "Database error. Please try again later." });
//           }
//         });
//       }
//       db.query(sqlInsertMovieQuote,[])
//     });
//   });
// });
// Insert Actor Details and Quotes
app.post("/insertActorDetails", (req, res) => {
  const { actorName, actorDOB, movies, movieQuotes } = req.body;

  db.beginTransaction((err) => {
    if (err) {
      console.error("Error beginning transaction:", err);
      return res
        .status(500)
        .json({ error: "Database error. Please try again later." });
    }

    const sqlInsertActor =
      "INSERT INTO actor (name, date_of_birth) VALUES (?, ?)";
    db.query(sqlInsertActor, [actorName, actorDOB], (err, result) => {
      if (err) {
        return db.rollback(() => {
          if (err.code === "ER_DUP_ENTRY") {
            res.status(500).json({ error: "Duplicate entry." });
          } else {
            console.error("Error inserting actor:", err);
            res
              .status(500)
              .json({ error: "Database error. Please try again later." });
          }
        });
      }

      const insertPromises = [];

      movies.forEach((movie, index) => {
        const movieTitle = movie.split(" (")[0];
        const movieYear = movie.split("(")[1].replace(")", "");
        const movieQuotesForMovie = movieQuotes[index]; // Ensure correct indexing

        if (Array.isArray(movieQuotesForMovie)) {
          movieQuotesForMovie.forEach((quote) => {
            const sqlInsertQuotes =
              "INSERT INTO Quotes (Movie_Title, Movie_Year, Movie_Quotation, Actor_Name) VALUES (?, ?, ?, ?)";
            insertPromises.push(
              new Promise((resolve, reject) => {
                db.query(
                  sqlInsertQuotes,
                  [movieTitle, movieYear, quote, actorName],
                  (err, result) => {
                    if (err) {
                      console.error("Error inserting quote:", err);
                      reject(err);
                    } else {
                      resolve(result);
                    }
                  }
                );
              })
            );
          });
        }
        const sqlMovieActor =
          "INSERT INTO movie_actor(Movie_Title, Movie_Year, Actor_Name) VALUES (?,?,?)";
        db.query(
          sqlMovieActor,
          [movieTitle, movieYear, actorName],
          (err, result) => {
            if (err) {
              console.error("Error inserting quote:", err);
            }
          }
        );
      });

      Promise.all(insertPromises)
        .then(() => {
          db.commit((err) => {
            if (err) {
              console.error("Error committing transaction:", err);
              return db.rollback(() => {
                res
                  .status(500)
                  .json({ error: "Database error. Please try again later." });
              });
            }
            console.log("Transaction committed successfully");
            res.json({
              message: "Actor details and quotes inserted successfully",
            });
          });
        })
        .catch((err) => {
          console.error("Error inserting actor details:", err);
          db.rollback(() => {
            res
              .status(500)
              .json({ error: "Database error. Please try again later." });
          });
        });
    });
  });
});
//Insert Director Details

//Start listening port 5000
app.listen(5000, () => {
  console.log(
    `Mysql Server with user name ${db.config.user} is running on port 5000 using ${db.config.database}`
  );
  console.log(`http://${db.config.host}:5000`);
});
