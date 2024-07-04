const express = require("express");
const path = require("path");
const bodyParser = require("body-parser");
const alert = require("alert");

const db = require("./db");
const app = express();

app.use(express.json());
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
  let DA = 0; // Initialize DA
  switch (empPosition) {
    case "Jr. Supervisor":
      DA = 22; // 22% of salary
      break;
    case "Sr. Supervisor":
      DA = 25; // 25% of salary
      break;
    case "Team Lead":
      DA = 28; // 28% of salary
      break;
    default:
      DA = 0;
  }

  // Start transaction
  db.beginTransaction((err) => {
    if (err) {
      console.log("Cannot begin transaction:", err);
      return res
        .status(500)
        .json({ error: "Database error. Please try again later." });
    }

    const checkDept = "SELECT deptName FROM dept WHERE deptID = ?";
    db.query(checkDept, [deptID], (err, result) => {
      if (err) {
        console.log("Error checking department:", err);
        return db.rollback(() => {
          res
            .status(500)
            .json({ error: "Database error. Please try again later." });
        });
      }

      if (result.length === 0) {
        return db.rollback(() => {
          res.status(500).json({ error: "Department does not exist." });
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
                res.status(500).json({
                  error: "Duplicate employee ID. Please use a different ID.",
                });
              } else {
                res
                  .status(500)
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
                  .status(500)
                  .json({ error: "Database error. Please try again later." });
              });
            }

            // Commit transaction if everything is successful
            db.commit((err) => {
              if (err) {
                console.log("Error committing transaction:", err);
                return db.rollback(() => {
                  res.status(500).json({
                    error: "Database error. Please try again later.",
                  });
                });
              }
              res.json("Successful");
              //    res.redirect("/success.html"); // Redirect to success page after successful transaction
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

app.get("/getEmpDetails/:empID", (req, res) => {
  const empID = req.params.empID;

  // SQL query with parameterized query to prevent SQL injection
  const sql = `
    SELECT employee.*, salary.salAmt, salary.DA, dept.deptName 
    FROM employee 
    JOIN salary ON employee.empID = salary.empID 
    JOIN dept ON employee.deptID = dept.deptID 
    WHERE employee.empID = ?
`;

  // Execute SQL query with parameters
  db.query(sql, [empID], (err, results) => {
    if (err) {
      console.error("Error executing SQL query:", err);
      return res.status(500).json({ error: "Internal Server Error" });
    }

    if (results.length === 0) {
      return res.status(404).json({ error: "Employee not found" });
    }
    // Send response as JSON
    res.json(results[0]); // Assuming empID is unique, so we take the first result
  });
});

app.delete("/deleteEmployee/:empID", (req, res) => {
  const empID = req.params.empID;
  const sql = `DELETE From employee where empID = ?`;
  db.query(sql, [empID], (err, result) => {
    if (err) {
      console.error("Error deleting employee:", err);
      res.status(500).json({
        error: "Internal Server Error",
        code: err.code,
        message: err.message,
      });
      return;
    }

    if (result.affectedRows === 0) {
      res.status(404).json({ error: "Employee not found" });
      return;
    }

    res.json({ message: "Employee deleted successfully" });
  });
});

app.listen(5000, () => {
  console.log("Server is running on port 5000");
  console.log("http://localhost:5000");
});
