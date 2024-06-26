const express = require("express");
const path = require("path");
const bodyParser = require("body-parser");

const db = require("./db");
const app = express();

//Getting frontend folder
app.use(express.static(path.join(__dirname, "../FrontEnd")));

//middleware
app.use(bodyParser.urlencoded({ extended: true }));

app.get("/", (req, res) => {
  // res.send("Hello world");
  res.sendFile("/index.html");
});

app.listen(5000, () => {
  console.log("Server is running on port 5000");
  console.log("http://localhost:5000");
});
