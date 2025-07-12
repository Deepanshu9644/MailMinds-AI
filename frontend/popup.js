document.getElementById("generate").addEventListener("click", async () => {
  const prompt = document.getElementById("prompt").value;

  const response = await fetch("http://localhost:8080/api/generate-email", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({ prompt })
  });

  const result = await response.text();
  document.getElementById("output").innerText = result;
});
