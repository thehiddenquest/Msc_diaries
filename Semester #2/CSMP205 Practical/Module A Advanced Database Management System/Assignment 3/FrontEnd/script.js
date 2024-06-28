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


