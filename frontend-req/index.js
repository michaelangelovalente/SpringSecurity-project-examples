const test = () => {
  fetch("http://localhost:8080/notices")
    .then((response) => response.json())
    .then((data) => console.log("result: ", data));
};
