const express = require("express");

const app = express();
const alert = require("alert");
var popup = require("popups");

const notifier = require("node-notifier");

notifier.notify({
  title: "My Notification",
  message: "Hello, this is a toast!",
});

app.get("/", (req, res) => {
  res.send("<h1> Hello World <h1>");
  alert.toast("Hello, Node.js! This is an alert message.");
  popup.alert({
    content: "Hello!",
  });
});

app.listen(5000, () => {
  console.log("Started Server at port 5000");
});
