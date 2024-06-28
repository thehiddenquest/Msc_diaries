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

app.post("/insert", (req, res) => {
  const { empID, empName, empGender, empPosition, doj, deptID, sal, comm } =
    req.body;
  let DA = 0.0; // Initialize DA as float

  switch (empPosition) {
    case "Jr. Supervisor":
      DA = parseFloat((sal * 0.22).toFixed(2)); // 22% of salary
      break;
    case "Sr. Supervisor":
      DA = parseFloat((sal * 0.25).toFixed(2)); // 25% of salary
      break;
    case "Team Lead":
      DA = parseFloat((sal * 0.28).toFixed(2)); // 28% of salary
      break;
    default:
      DA = 0.0;
  }

  // Start transaction
  db.beginTransaction((err) => {
    if (err) {
      console.log("Cannot begin transaction:", err);
      return res
        .json({ error: "Database error. Please try again later." });
    }

    const checkDept = "SELECT deptName FROM dept WHERE deptID = ?";
    db.query(checkDept, [deptID], (err, result) => {
      if (err) {
        console.log("Error checking department:", err);
        return db.rollback(() => {
          res
            .json({ error: "Database error. Please try again later." });
        });
      }

      if (result.length === 0) {
        return db.rollback(() => {
          res.json({ error: "Department does not exist." });
        });
      }

      const sqlInsertEmp =
        "INSERT INTO employee (empID, empName, empGender, empPosition, doj, deptID) VALUES (?,?,?,?,?,?)";
      db.query(
        sqlInsertEmp,
        [empID, empName, empGender, empPosition, doj, deptID],
        (err, result) => {
          if (err) {
            console.error("Error inserting employee:", err);
            return db.rollback(() => {
              if (err.code === "ER_DUP_ENTRY") {
                res.json({
                  error: "Duplicate employee ID. Please use a different ID.",
                });
              } else {
                res
                  .json({ error: "Database error. Please try again later." });
              }
            });
          }

          const sqlInsertSalary =
            "INSERT INTO salary (empID, salAmt, commAmt, DA) VALUES (?,?,?,?)";
          db.query(sqlInsertSalary, [empID, sal, comm, DA], (err, result) => {
            if (err) {
              console.log("Error inserting salary:", err);
              return db.rollback(() => {
                res
                  .json({ error: "Database error. Please try again later." });
              });
            }

            // Commit transaction if everything is successful
            db.commit((err) => {
              if (err) {
                console.log("Error committing transaction:", err);
                return db.rollback(() => {
                  res
                    .json({ error: "Database error. Please try again later." });
                });
              }
              res.redirect("/success.html"); // Redirect to success page after successful transaction
            });
          });
        }
      );
    });
  });
});
app.get("/getEmp", (req, res) => {
  const sqlGetEmp =
    "SELECT employee.*, salary.salAmt, salary.commAmt, dept.deptName FROM employee JOIN salary ON employee.empID = salary.empID JOIN dept ON employee.deptID = dept.deptID;";
  db.query(sqlGetEmp, [], (err, result) => {
    if (err) {
      throw err;
    }
    res.json(result);
  });
});

app.get("/departments", (req, res) => {
  const sql = "SELECT * FROM `dept`";

  db.query(sql, (err, results) => {
    if (err) {
      console.error("Error querying departments: " + err.stack);
      res.status(500).json({ error: "Error querying departments" });
      return;
    }
    res.json(results);
  });
});

app.listen(5000, () => {
  console.log("Server is running on port 5000");
  console.log("http://localhost:5000");
});
