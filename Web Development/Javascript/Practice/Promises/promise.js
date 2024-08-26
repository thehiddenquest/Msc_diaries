const promiseOne = new Promise((resolve, reject) => {
  setTimeout(() => {
    console.log("Hello This is promise one");
    resolve("Promise one resolved");
  }, 1000);
});

promiseOne
  .then((data) => {
    console.log(data);
    console.log("After promise work");
  })
  .catch(() => {
    console.log("Promise one rejected");
  });

function promiseTwo() {
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      console.log("Hello This is promise two");
      //Generate random number between zero and one
      let random = Math.floor(Math.random() * 2);
      if (random === 0) {
        reject("Error: occur");
      } else {
        resolve("Promise two resolved");
      }
    }, 3000);
  });
}

promiseTwo()
  .then((data) => {
    console.log(data);
  })
  .catch((err) => {
    console.log(err);
  });
