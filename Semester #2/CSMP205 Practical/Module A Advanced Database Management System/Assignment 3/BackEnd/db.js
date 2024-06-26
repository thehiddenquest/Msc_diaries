const mysql = require("mysql2");

const dbName = "office";

const conn = mysql.createConnection({
  host: "localhost",
  user: "root",
  password: "root",
  database: dbName,
  port: 3306,
});

conn.connect((err) => {
  if (err) {
    console.error("error connecting:", err.stack);
    throw err;
  }
  console.log("connected as id " + conn.threadId + " with " + dbName);
});

module.exports = conn;
