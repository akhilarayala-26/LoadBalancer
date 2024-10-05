const apiUrl = 'http://localhost:8080/api';

function updateServerStatus() {
    fetch(`${apiUrl}/status`)
        .then(response => response.json())
        .then(data => {
            const serverStatus = document.getElementById('serverStatus');
            serverStatus.innerHTML = ''; // Clear previous status

            data.servers.forEach(server => {
                const serverDiv = document.createElement('div');
                serverDiv.className = 'server';
                serverDiv.innerHTML = `<h3>${server.name}</h3><p>Active Connections: ${server.activeConnections}</p>`;
                serverStatus.appendChild(serverDiv);
            });
        })
        .catch(error => console.error('Error fetching server status:', error));
}

function sendRequest() {
    const algorithm = document.getElementById('algorithm').value;
    fetch(`${apiUrl}/send-request?algorithm=${algorithm}`, { method: 'POST' })
        .then(() => updateServerStatus())
        .catch(error => console.error('Error sending request:', error));
}

// Initialize server status on page load
window.onload = updateServerStatus;
