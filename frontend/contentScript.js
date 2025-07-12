console.log("MailMinds content script loaded.");

function injectButtonIntoVisibleEditor() {
  const messageBoxes = document.querySelectorAll('[aria-label="Message Body"]');

  messageBoxes.forEach(container => {
    const parent = container.parentElement;
    if (!parent || parent.querySelector(".mailminds-button")) return;

    const button = document.createElement("button");
    button.innerText = "✉️ MailMinds AI";
    button.className = "mailminds-button";
    button.style.marginTop = "8px";
    button.style.padding = "6px 10px";
    button.style.backgroundColor = "#1a73e8";
    button.style.color = "white";
    button.style.border = "none";
    button.style.borderRadius = "4px";
    button.style.cursor = "pointer";

    const resultBox = document.createElement("div");
    resultBox.className = "mailminds-result";
    resultBox.style.marginTop = "6px";
    resultBox.style.padding = "8px";
    resultBox.style.background = "#f1f3f4";
    resultBox.style.borderRadius = "4px";
    resultBox.style.whiteSpace = "pre-wrap";
    resultBox.innerText = "AI-generated reply will appear here...";

    button.addEventListener("click", () => {
      resultBox.innerText = "Generating email with MailMinds AI...";

      let previousThread = "";
      const allThreadBubbles = document.querySelectorAll('.adn');

      allThreadBubbles.forEach((bubble) => {
        if (bubble.innerText.includes("Hi Deepanshu") || bubble.innerText.includes("Best regards")) {
          previousThread = bubble.innerText;
        }
      });

      const emailContent = previousThread || container.innerText || "No content found";

      fetch("http://localhost:8080/api/email/generate", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({ emailContent: emailContent })
      })
        .then(response => response.text())
        .then(data => {
          resultBox.innerText = data || "No response from server.";
        })
        .catch(error => {
          console.error("MailMinds error:", error);
          resultBox.innerText = "Failed to generate email.";
        });
    });

    parent.appendChild(button);
    parent.appendChild(resultBox);
  });
}

setInterval(injectButtonIntoVisibleEditor, 2000);
