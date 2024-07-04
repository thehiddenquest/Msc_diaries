document.addEventListener("DOMContentLoaded", () => {
  // Fetch department data from Node.js server
  fetch("/departments")
    .then((response) => response.json())
    .then((data) => {
      const deptDropdown = document.getElementById("deptID");

      // Populate dropdown options
      data.forEach((dept) => {
        const option = document.createElement("option");
        option.value = dept.deptID;
        option.textContent = dept.deptName;
        deptDropdown.appendChild(option);
      });
    })
    .catch((error) => {
      console.error("Error fetching departments:", error);
    });
});
document.addEventListener("DOMContentLoaded", function () {
  const form = document.querySelector("form");

  form.addEventListener("submit", (e) => {
    e.preventDefault(); // prevent the default form submission behavior

    const empID = document.getElementById("empID").value;
    const empName = document.getElementById("empName").value;
    const empGender = document.getElementById("empGender").value;
    const empPosition = document.getElementById("empPosition").value;
    const doj = document.getElementById("doj").value;
    const deptIDSelect = document.getElementById("deptID");
    const deptID = deptIDSelect.options[deptIDSelect.selectedIndex].value;
    const sal = document.getElementById("sal").value;
    const comm = document.getElementById("comm").value;
    const empData = {
      empID,
      empName,
      empGender,
      empPosition,
      doj,
      deptID,
      sal,
      comm,
    };
    fetch("/insert", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(empData),
    })
      .then((response) => response.json())
      .then((data) => {
        if (data.error) {
          alert(data.error);
          window.location.reload(); // reload the page after successful registration
        } else {
          window.location.href = "success.html";
        }
      })
      .catch((error) => {
        console.error("Error registering employee:", error);
        alert("An error occurred. Please try again later.", error.message);
      });
  });
});
