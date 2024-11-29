document.addEventListener("DOMContentLoaded", function() {
    // Fetch fan status data on load
    fetchFanStatus();
    fetchHistory();

    // Event listener for updating thresholds
    document.getElementById("updateThresholds").addEventListener("click", function() {
        const tempThreshold = document.getElementById("tempThreshold").value;
        const proximityThreshold = document.getElementById("proximityThreshold").value;

        updateThresholds(tempThreshold, proximityThreshold);
    });
});

function fetchFanStatus() {
    fetch('/api/fanstatus')
        .then(response => response.json())
        .then(data => {
            // Update the fan status data
            document.getElementById("temperature").innerText = `Temperature: ${data.temperature} °C`;
            document.getElementById("distance").innerText = `Distance: ${data.distance} cm`;
            document.getElementById("fanStatus").innerText = `Fan Status: ${data.status}`;
        })
        .catch(error => console.error('Error fetching fan status:', error));
}

function fetchHistory() {
    fetch('/api/fanstatus/history')
        .then(response => response.json())
        .then(data => {
            const historyData = document.getElementById("historyData");
            historyData.innerHTML = ''; // Clear existing rows

            data.forEach(status => {
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
        .catch(error => console.error('Error fetching history:', error));
}

function updateThresholds(tempThreshold, proximityThreshold) {
    const data = {
        temperature: tempThreshold,
        distance: proximityThreshold
    };

    fetch('/api/fanstatus/updateThresholds', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => response.json())
    .then(() => {
        console.log('Thresholds updated');
        fetchFanStatus();  // Refresh fan status after updating thresholds
    })
    .catch(error => console.error('Error updating thresholds:', error));
}
