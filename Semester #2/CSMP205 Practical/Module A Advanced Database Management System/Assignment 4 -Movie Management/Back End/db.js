const mysql = require("mysql2");

const dbName = "movie_management";
const hostName = "localhost";
const userName = "root";
const password = "root";
const port = 3306;
const conn = mysql.createConnection({
  host: hostName,
  user: userName,
  password: password,
  database: dbName,
  port: port,
});

conn.connect((err) => {
  if (err) {
    console.error("error connecting:", err.stack);
    throw err;
  }
  console.log("connected as id " + conn.threadId + " with " + dbName);
});

module.exports = conn;
