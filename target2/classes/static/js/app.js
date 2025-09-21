function formatDate(dateStr) {
  if (!dateStr) return "";
  const date = new Date(dateStr);
  return date.toLocaleString("he-IL", { dateStyle: "short", timeStyle: "short" });
}


async function loadNotifications() {
  const userId = localStorage.getItem("userId");
    console.log("User ID for notifications:", userId);
  const container = document.getElementById("studentNotifications") || document.getElementById("notifications");
  if (!userId || !container) return;

  try {
    const res = await fetch(`/api/notifications/user/${userId}`);
      if (!res.ok) throw new Error("Server error");
    const data = await res.json();

    if (!data.length) {
      container.innerHTML = "<p>אין התראות חדשות.</p>";
      return;
    }

let table = `
      <table border="1" class="notifications-table">
        <thead>
          <tr>
            <th>#</th>
            <th>הודעה</th>
            <th>תאריך</th>
          </tr>
        </thead>
        <tbody>
    `;

    data.forEach((n, index) => {
      table += `
        <tr>
          <td>${index + 1}</td>
          <td>${n.message}</td>
          <td>${formatDate(n.timestamp)}</td>
        </tr>
      `;
    });

    table += "</tbody></table>";
    container.innerHTML = table;

  } catch (err) {
    console.error("Error loading notifications", err);
    container.innerHTML = "<p>שגיאה בטעינת התראות</p>";
  }
}




async function loadCreatedLessons() {
  const container = document.getElementById("createdLessons") || document.getElementById("studentCourses") || document.getElementById("lessonsList");
  if (!container) return;

  try {
    const res = await fetch("/api/lessons");
    const lessons = await res.json();

    if (!lessons.length) {
      container.innerHTML = "<p>לא נמצאו שיעורים.</p>";
      return;
    }

    const html = lessons.map(l => `
      <div class="lesson-box">
        <h3>${l.title}</h3>
        <p>${l.description}</p>
        <p><strong>Zoom:</strong> <a href="${l.zoomLink}" target="_blank">${l.zoomLink}</a></p>
        <small>תאריך: ${formatDate(l.date)}</small>
      </div>
    `).join("");

    container.innerHTML = html;

  } catch (err) {
    console.error("שגיאה בטעינת שיעורים", err);
    container.innerHTML = "<p>שגיאה בטעינת שיעורים</p>";
  }
}




async function submitLessonForm() {
  const title = document.getElementById("lessonTitle").value.trim();
  const description = document.getElementById("lessonDescription").value.trim();
  const date = document.getElementById("lessonDate").value;
  const zoomLink = document.getElementById("zoomLink").value.trim();

  if (!title || !description || !date || !zoomLink) {
    alert("נא למלא את כל השדות");
    return false;
  }
if (!date || isNaN(Date.parse(date))) {
  alert("תאריך אינו תקני");
  return false;
}

  try {
    const teacherId = localStorage.getItem("userId");
    const res = await fetch("/api/lessons", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        title,
        description,
        date,
        zoomLink,
        teacher: { id: teacherId }
      })
    });

    if (res.ok) {
      alert("השיעור נוסף ונשלחה התראה!");
      loadCreatedLessons();
    } else {
      alert("שגיאה ביצירת השיעור");
    }
  } catch (err) {
    console.error(err);
    alert("שגיאה ברשת");
  }

  return false;
}




async function login() {
  const email = document.getElementById("loginEmail")?.value?.trim() || document.getElementById("email")?.value?.trim();
  const password = document.getElementById("loginPassword")?.value || document.getElementById("password")?.value;

  if (!email || !password) {
    alert("נא למלא את כל השדות");
    return false;
  }

  try {
    const response = await fetch("/api/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ email, password })
    });

    if (response.ok) {
      const user = await response.json();

      if (user && user.role) {

        localStorage.setItem("loggedIn", "true");
        localStorage.setItem("userId", user.id || "");
        localStorage.setItem("role", user.role);
        localStorage.setItem("userEmail", user.email || "");
        alert("ברוך הבא!");

        if (user.role === "TEACHER") {
          window.location.href = "../teachers.html";
        } else if (user.role === "STUDENT") {
          window.location.href = "../students.html";
        } else if (user.role === "ADMIN") {
          window.location.href = "../teachers.html";
        } else {
          alert("תפקיד לא ידוע");
        }
      } else {
        alert("דואר או סיסמה שגויים");
      }
    } else {
      alert("שגיאה בשרת");
    }
  } catch (err) {
    console.error(err);
    alert("בעיה ברשת: " + err.message);
  }

  return false;
}


async function validateRegisterForm() {
  const name = document.getElementById("name").value.trim();
  const email = document.getElementById("email").value.trim();
  const password = document.getElementById("password").value;

  if (name.length < 3) {
    alert("יש להזין שם מלא באורך תקני.");
    return false;
  }

  if (password.length < 6) {
    alert("הסיסמה חייבת להכיל לפחות 6 תווים.");
    return false;
  }

  try {
    const res = await fetch("/api/register", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ name, email, password }),
    });

    if (res.ok) {
      alert("!נרשמת בהצלחה");
      window.location.href = "index.html";
    } else {
      alert("שגיאה בהרשמה");
    }
  } catch (err) {
    console.error(err);
    alert("שגיאה בשרת");
  }

  return false;
}

window.addEventListener("DOMContentLoaded", () => {
  const logoutLink = document.getElementById("logoutLink");
  const isLoggedIn = localStorage.getItem("loggedIn") === "true";
  if (!isLoggedIn && logoutLink) {
    logoutLink.style.display = "none";
  }
});

function logout() {
  const isLoggedIn = localStorage.getItem("loggedIn") === "true";

  if (!isLoggedIn) {
    return;
  }

  localStorage.clear();
  alert("התנתקת בהצלחה");
  window.location.href = "index.html";
}

const characters = document.querySelectorAll(".character");
const charName = document.getElementById("charName");
const startButton = document.getElementById("startButton");

if (characters && charName) {
  characters.forEach((char) => {
    char.addEventListener("click", () => {
      characters.forEach((c) => c.classList.remove("active"));
      char.classList.add("active");
      charName.textContent = char.getAttribute("data-name");
    });
  });
}

if (startButton) {
  startButton.addEventListener("click", () => {
    const isLoggedIn = localStorage.getItem("loggedIn") === "true";
    const role = localStorage.getItem("role");
    const selected = charName.textContent.trim();

    if (!isLoggedIn) {
      alert("עליך להתחבר לפני הגישה לתחומים");
      window.location.href = "login.html";
      return;
    }

    if (selected === "תכנות") {
      if (role === "TEACHER") {
        window.location.href = "teachers.html";
      } else {
        alert("הגישה לתחום זה מותרת רק למורים.");
      }
    } else {
      alert("התחום הזה עדיין לא זמין. בקרוב!");
    }
  });
}


function redirectByRole(role) {
  if (role === "TEACHER") {
    window.location.href = "/html/lessons.html";
  } else if (role === "STUDENT") {
    window.location.href = "/html/lessons.html";
  } else {
    alert("תפקיד לא ידוע");
  }
}


async function loadLessons() {
  const listDiv = document.getElementById("studentCourses") || document.getElementById("lessonsList");
    if (!listDiv) return;
  try {
    const res = await fetch("/api/lessons");
    if (!res.ok) throw new Error("Server error");
    const lessons = await res.json();
    if (!lessons.length) {
      listDiv.innerHTML = "<p>אין שיעורים כרגע.</p>";
      return;
    }

    let table = `
      <table border="1" class="lessons-table">
        <thead>
          <tr>
            <th>#</th>
            <th>שם השיעור</th>
            <th>תיאור</th>
            <th>קישור Zoom</th>
            <th>תאריך</th>
          </tr>
        </thead>
        <tbody>
    `;

    lessons.forEach((l, index) => {
      table += `
        <tr>
          <td>${index + 1}</td>
          <td>${l.title}</td>
          <td>${l.description}</td>
          <td><a href="${l.zoomLink}" target="_blank">פתח</a></td>
          <td>${formatDate(l.date)}</td>
        </tr>
      `;
    });

    table += "</tbody></table>";
    listDiv.innerHTML = table;

  } catch (err) {
    console.error("Error loading lessons", err);
    listDiv.innerHTML = "<p>שגיאה בטעינת שיעורים</p>";
  }
}

if (window.location.pathname.includes("lessons.html")) {
  const isLoggedIn = localStorage.getItem("loggedIn") === "true";
  if (!isLoggedIn) {
    alert("עליך להתחבר כדי לראות את השיעורים");
    window.location.href = "login.html";
  } else {
    loadLessons();
  }
}
if (window.location.pathname.includes("students.html")) {
  const isLoggedIn = localStorage.getItem("loggedIn") === "true";
  if (!isLoggedIn) {
    alert("עליך להתחבר כדי לצפות בשיעורים");
    window.location.href = "login.html";
  } else {
    loadLessons();
  }
}
window.addEventListener("DOMContentLoaded", () => {
  loadLessons();
  loadNotifications();
});




let chatSocket;
const chatUser = localStorage.getItem("userEmail") || "משתמש";

function initChat() {
  const chatBox = document.getElementById("chatMessages");
  chatSocket = new WebSocket("ws://" + window.location.host + "/ws/chat");

  chatSocket.onmessage = function(event) {
    const msg = JSON.parse(event.data);
    const div = document.createElement("div");
    div.className = "chat-message";
    div.innerHTML = "<strong>" + msg.user + ":</strong> " + msg.text;
    chatBox.appendChild(div);
    chatBox.scrollTop = chatBox.scrollHeight;
  };

  chatSocket.onclose = function() {
    console.log("Chat closed.");
  };
}

function sendChatMessage() {
  const input = document.getElementById("chatInput");
  const text = input.value.trim();
  if (text && chatSocket && chatSocket.readyState === WebSocket.OPEN) {
    const msg = { user: chatUser, text: text };
    chatSocket.send(JSON.stringify(msg));
    input.value = "";
  }
  return false;
}

if (window.location.pathname.includes("chat.html")) {
  initChat();
}
