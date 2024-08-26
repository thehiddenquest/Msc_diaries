const express = require("express");
const path = require("path");
const bodyParser = require("body-parser");

const db = require("./db");
const app = express();

//used to parse incoming requests with JSON payloads.
app.use(express.json());

//Getting frontend folder
app.use(express.static(path.join(__dirname, "../Front End")));

//middleware
app.use(bodyParser.urlencoded({ extended: true }));

async function getLastQuoteId() {
  return new Promise((resolve, reject) => {
    const sql = "SELECT MAX(Quote_id) as lastId FROM Quotes";
    db.query(sql, (err, result) => {
      if (err) {
        console.error("Error getting last Quote_id:", err);
        reject(err);
      } else {
        resolve(result[0].lastId || 0); // If table is empty, return 0
      }
    });
  });
}

//Starting page
app.get("/", (req, res) => {
  res.sendFile("Index.html");
});

//Get Production Company Name
app.get("/getProductionCompanyName", (req, res) => {
  const sql = "SELECT `Name` from `Production_Company`";
  db.query(sql, (err, result) => {
    if (err) throw err;
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
  const { title, year, length, plot, productionCompany, genres } = req.body;

  const sqlInsertMovie =
    "INSERT INTO Movie (Title, Year, Length, Plot_outline, Production_Company_Name) VALUES (?, ?, ?, ?, ?)";
  db.query(
    sqlInsertMovie,
    [title, year, length, plot, productionCompany],
    (err, result) => {
      if (err) {
        console.error("Error inserting movie:", err);
        return res
          .status(500)
          .json({ error: "Database error. Please try again later." });
      }

      // Prepare the values for multiple genre insertion
      const genreValues = genres
        .map((genre) => `('${title}', ${year}, '${genre}')`)
        .join(",");

      // Insert multiple genres in a single query
      const sqlInsertMovieGenre = `INSERT INTO Movie_Genre (Movie_Title, Movie_Year, Genre_Type) VALUES ${genreValues}`;

      db.query(sqlInsertMovieGenre, (err, result) => {
        if (err) {
          console.error("Error inserting movie genres:", err);
          return res
            .status(500)
            .json({ error: "Database error. Please try again later." });
        }

        res.json({ message: "Movie details and genres inserted successfully" });
      });
    }
  );
});
console;
// Insert Actor Details and Quotes
app.post("/insertActorDetails", async (req, res) => {
  const { name, dateOfBirth, movies } = req.body;

  db.beginTransaction(async (err) => {
    if (err) {
      console.error("Error beginning transaction:", err);
      return res
        .status(500)
        .json({ error: "Database error. Please try again later." });
    }

    try {
      // Insert Actor
      const sqlInsertActor =
        "INSERT INTO Actor (Name, Date_of_Birth) VALUES (?, ?)";
      await new Promise((resolve, reject) => {
        db.query(sqlInsertActor, [name, dateOfBirth], (err, result) => {
          if (err) {
            if (err.code === "ER_DUP_ENTRY") {
              reject(new Error("Actor already exists."));
            } else {
              console.error("Error inserting actor:", err);
              reject(new Error("Database error. Please try again later."));
            }
          } else {
            resolve(result);
          }
        });
      });

      // Process each movie
      for (const movie of movies) {
        const { movieId, role, quote } = movie;
        console.log("Processing movie:", movieId); // Add this line for debugging

        let movieTitle, movieYear;
        if (typeof movieId === "string" && movieId.includes("(")) {
          [movieTitle, movieYear] = movieId.split(" (");
          movieYear = movieYear ? movieYear.replace(")", "").trim() : null;
        } else {
          console.error("Invalid movieId format:", movieId);
          throw new Error("Invalid movie format");
        }

        if (!movieTitle || !movieYear) {
          console.error("Missing movie title or year:", movieId);
          throw new Error("Invalid movie data");
        }

        // Insert into Movie_Actor table
        const sqlInsertMovieActor =
          "INSERT IGNORE INTO Movie_Actor (Movie_Title, Movie_Year, Actor_Name, Role) VALUES (?, ?, ?, ?)";
        await new Promise((resolve, reject) => {
          db.query(
            sqlInsertMovieActor,
            [movieTitle, movieYear, name, role],
            (err, result) => {
              if (err) {
                console.error("Error inserting movie_actor:", err);
                reject(err);
              } else {
                resolve(result);
              }
            }
          );
        });

        // Get the last Quote_id and increment it
        const lastId = await getLastQuoteId();
        const newId = lastId + 1;

        // Insert into Quotes table
        const sqlInsertQuote =
          "INSERT INTO Quotes (Quote_id, Movie_Title, Movie_Year, Quote, Actor_name) VALUES (?, ?, ?, ?, ?)";
        await new Promise((resolve, reject) => {
          db.query(
            sqlInsertQuote,
            [newId, movieTitle, movieYear, quote, name],
            (err, result) => {
              if (err) {
                console.error("Error inserting quote:", err);
                reject(err);
              } else {
                resolve(result);
              }
            }
          );
        });
      }

      // Commit the transaction
      await new Promise((resolve, reject) => {
        db.commit((err) => {
          if (err) {
            console.error("Error committing transaction:", err);
            reject(new Error("Database error. Please try again later."));
          } else {
            resolve();
          }
        });
      });

      console.log("Transaction committed successfully");
      res.json({
        message: "Actor details, roles, and quotes inserted successfully",
      });
    } catch (error) {
      // Rollback the transaction on any error
      db.rollback(() => {
        console.error("Error in transaction, rolled back:", error);
        res.status(500).json({ error: error.message });
      });
    }
  });
});
//Insert Director Details
app.post("/Director_page", (req, res) => {
  const { name, dob, movies } = req.body;
  console.log(name, dob, movies);

  db.beginTransaction((err) => {
    if (err) {
      console.error("Error starting transaction:", err);
      return res
        .status(500)
        .json({ error: "Transaction error. Please try again later." });
    }

    const sqlInsertDirector =
      "INSERT INTO Director (Name, Date_of_Birth) VALUES (?, ?)";
    db.query(sqlInsertDirector, [name, dob], (err, result) => {
      if (err) {
        return db.rollback(() => {
          if (err.code === "ER_DUP_ENTRY") {
            return res.status(409).json({ error: "Director already exists." });
          }
          console.error("Error inserting director:", err);
          res
            .status(500)
            .json({ error: "Database error. Please try again later." });
        });
      }

      const moviePromises = movies.map((movie) => {
        return new Promise((resolve, reject) => {
          const { m_name, isActed, isDirected, role } = movie;
          const [movieName, movieYear] = m_name.split(",");

          if (isDirected) {
            const sqlMovieDirector =
              "INSERT INTO Movie_Director (Movie_Title, Movie_Year, Director_Name) VALUES (?, ?, ?)";
            db.query(sqlMovieDirector, [movieName, movieYear, name], (err) => {
              if (err) {
                if (err.code === "ER_DUP_ENTRY") {
                  console.warn(
                    "Duplicate entry in Movie_Director:",
                    err.message
                  );
                } else {
                  return reject(err);
                }
              }
              resolve();
            });
          }

          if (isActed) {
            const sqlActor =
              "INSERT INTO Actor (Name, Date_of_Birth) VALUES (?, ?) ON DUPLICATE KEY UPDATE Date_of_Birth = VALUES(Date_of_Birth)";
            db.query(sqlActor, [name, dob], (err) => {
              if (err) return reject(err);

              const sqlMovieActor =
                "INSERT INTO Movie_Actor (Movie_Title, Movie_Year, Actor_Name, Role) VALUES (?, ?, ?, ?)";
              db.query(
                sqlMovieActor,
                [movieName, movieYear, name, role],
                (err) => {
                  if (err) {
                    if (err.code === "ER_DUP_ENTRY") {
                      console.warn(
                        "Duplicate entry in Movie_Actor:",
                        err.message
                      );
                    } else {
                      return reject(err);
                    }
                  }

                  if (isDirected) {
                    const sqlDirectorActor =
                      "INSERT IGNORE INTO Director_Actor (Director_Name) VALUES (?)";
                    db.query(sqlDirectorActor, [name], (err) => {
                      if (err) return reject(err);
                      resolve();
                    });
                  } else {
                    resolve();
                  }
                }
              );
            });
          } else {
            resolve();
          }
        });
      });

      Promise.all(moviePromises)
        .then(() => {
          db.commit((err) => {
            if (err) {
              return db.rollback(() => {
                console.error("Error committing transaction:", err);
                res.status(500).json({
                  error: "Transaction commit error. Please try again later.",
                });
              });
            }
            res.json({
              message: "Director details and movies inserted successfully",
            });
          });
        })
        .catch((err) => {
          db.rollback(() => {
            console.error("Error in movie operations:", err);
            res.status(500).json({
              error: "Error processing movies. Please try again later.",
            });
          });
        });
    });
  });
});

app.get("/searchMovies", (req, res) => {
  const { fromYear, toYear, productionCompany } = req.query;
  let sql;
  let params;
  if (fromYear && toYear) {
    sql = `SELECT m.Title, m.Year, m.Length, m.Production_Company_Name, GROUP_CONCAT(
        DISTINCT mg.Genre_Type
        ORDER BY mg.Genre_Type
    ) AS Genre,
       GROUP_CONCAT(DISTINCT md.Director_Name) AS Director
FROM Movie m
JOIN Movie_Genre mg ON m.Title = mg.Movie_Title AND m.Year = mg.Movie_Year
JOIN Movie_Director md ON m.Title = md.Movie_Title AND m.Year = md.Movie_Year
WHERE m.Year BETWEEN ? AND ?
GROUP BY m.Title, m.Year, m.Length, m.Production_Company_Name
ORDER BY m.Year, Genre`;
    params = [fromYear, toYear];
  } else if (productionCompany) {
    sql = `SELECT m.Title, m.Year, GROUP_CONCAT(
        DISTINCT md.Director_Name
        ORDER BY md.Director_Name
    ) AS Director, GROUP_CONCAT(
        DISTINCT mg.Genre_Type
        ORDER BY mg.Genre_Type
    ) AS Genre, m.Production_Company_Name, m.Length
FROM
    Movie m
    JOIN Movie_Genre mg ON m.Title = mg.Movie_Title
    AND m.Year = mg.Movie_Year
    JOIN Movie_Director md ON m.Title = md.Movie_Title
    AND m.Year = md.Movie_Year
WHERE
    m.Production_Company_Name = ?
GROUP BY
    m.Title,
    m.Year,
    m.Length,
    m.Production_Company_Name
ORDER BY Genre, Director;`;
    params = [productionCompany];
  } else {
    return res.status(400).json({ error: "Invalid search parameters" });
  }
  db.query(sql, params, (err, results) => {
    if (err) {
      console.error("Error executing search query:", err);
      return res.status(500).json({ error: "Database error" });
    }
    res.json(results);
  });
});

//Start listening port 5000
app.listen(5000, () => {
  console.log(
    `Mysql Server with user name ${db.config.user} is running on port 5000 using ${db.config.database}`
  );
  console.log(`http://${db.config.host}:5000`);
});
