document.addEventListener("DOMContentLoaded", function () {
    // Fetch fan status data and history on load
    fetchFanStatus();
    fetchHistory();
    checkDeviceStatus();

    // Periodically check the device status
    setInterval(checkDeviceStatus, 5000); // Every 5 seconds

    // Event listener for updating thresholds
    document.getElementById("updateThresholds").addEventListener("click", function () {
        const tempThreshold = document.getElementById("tempThreshold").value;
        const proximityThreshold = document.getElementById("proximityThreshold").value;

        if (isValidThreshold(tempThreshold) && isValidThreshold(proximityThreshold)) {
            updateThresholds(tempThreshold, proximityThreshold);
        } else {
            alert("Please enter valid numerical thresholds.");
        }
    });
});

function fetchFanStatus() {
    showLoading("fanStatusLoading");
    fetch("/api/fanstatus")
        .then((response) => response.json())
        .then((data) => {
            document.getElementById("temperature").innerText = `Temperature: ${data.temperature} °C`;
            document.getElementById("distance").innerText = `Distance: ${data.distance} cm`;
            document.getElementById("fanStatus").innerText = `Fan Status: ${data.status}`;
        })
        .catch((error) => {
            console.error("Error fetching fan status:", error);
            showError("fanStatusLoading", "Unable to fetch fan status.");
        })
        .finally(() => hideLoading("fanStatusLoading"));
}

function fetchHistory() {
    showLoading("historyLoading");
    fetch("/api/fanstatus/history")
        .then((response) => response.json())
        .then((data) => {
            const historyData = document.getElementById("historyData");
            historyData.innerHTML = ""; // Clear existing rows

            data.forEach((status) => {
                const row = document.createElement("tr");

                row.innerHTML = `
                    <td>${status.id}</td>
                    <td>${status.temperature}</td>
                    <td>${status.distance}</td>
                    <td>${status.status}</td>
                    <td>${status.timestamp}</td>
                `;

                historyData.appendChild(row);
            });
        })
        .catch((error) => {
            console.error("Error fetching history:", error);
            showError("historyLoading", "Unable to fetch history.");
        })
        .finally(() => hideLoading("historyLoading"));
}

function updateThresholds(tempThreshold, proximityThreshold) {
    const data = {
        temperature: tempThreshold,
        distance: proximityThreshold,
    };

    showLoading("updateThresholdsLoading");
    fetch("/api/fanstatus/updateThresholds", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
    })
        .then((response) => {
            if (!response.ok) throw new Error("Failed to update thresholds");
            return response.json();
        })
        .then(() => {
            alert("Thresholds updated successfully!");
            fetchFanStatus(); // Refresh fan status after updating thresholds
        })
        .catch((error) => {
            console.error("Error updating thresholds:", error);
            showError("updateThresholdsLoading", "Failed to update thresholds.");
        })
        .finally(() => hideLoading("updateThresholdsLoading"));
}

function checkDeviceStatus() {
    fetch("/api/device/status")
        .then((response) => response.json())
        .then((data) => {
            const statusText = document.getElementById("status-text");

            if (data.connected) {
                statusText.innerText = "Connected";
                statusText.className = "connected";
            } else {
                statusText.innerText = "Disconnected";
                statusText.className = "disconnected";
            }
        })
        .catch((error) => {
            console.error("Error checking device status:", error);
            const statusText = document.getElementById("status-text");
            statusText.innerText = "Unknown";
            statusText.className = "unknown";
        });
}


function isValidThreshold(value) {
    return !isNaN(value) && value.trim() !== "";
}

function showLoading(elementId) {
    const loadingElement = document.getElementById(elementId);
    if (loadingElement) loadingElement.style.display = "block";
}

function hideLoading(elementId) {
    const loadingElement = document.getElementById(elementId);
    if (loadingElement) loadingElement.style.display = "none";
}

function showError(elementId, message) {
    const errorElement = document.getElementById(elementId);
    if (errorElement) errorElement.innerText = message;
}
