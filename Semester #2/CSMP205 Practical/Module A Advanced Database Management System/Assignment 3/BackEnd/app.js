const express = require("express");
const path = require("path");
const bodyParser = require("body-parser");

const app = express();

//Getting frontend folder
app.use(express.static(path.join(__dirname, "../FrontEnd")));

app.get("/", (req, res) => {
  // res.send("Hello world");
  res.sendFile("/index.html");
});

app.listen(5000, () => {
  console.log("Server is running on port 5000");
  console.log("http://localhost:5000");
});
