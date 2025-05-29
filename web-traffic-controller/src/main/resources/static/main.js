// Update traffic light UI
function updateUI(state) {
    document.getElementById('redLight').className = 'light light-' + (state === 'RED' ? 'red' : 'off');
    document.getElementById('yellowLight').className = 'light light-' + (state === 'YELLOW' ? 'yellow' : 'off');
    document.getElementById('greenLight').className = 'light light-' + (state === 'GREEN' ? 'green' : 'off');
}

// Update mode and timer
function updateMode(mode) {
    const modeDisplay = document.getElementById('mode');
    modeDisplay.textContent = mode;
    modeDisplay.className = 'mode-' + mode.toLowerCase();
    if (mode === 'BLINKING') {
        const timerDisplay = document.getElementById('timerDisplay');
        timerDisplay.classList.remove('timer-red', 'timer-green');
        timerDisplay.textContent = 'OFF';
    }
}

function updateTimer(type, value) {
    const timerDisplay = document.getElementById('timerDisplay');

    if (type === 'value') {
        if (value === -1) {
            timerDisplay.classList.remove('timer-red', 'timer-green');
            timerDisplay.textContent = 'OFF';
        } else {
            timerDisplay.textContent = value;
        }
    }
    else if (type === 'color') {
        timerDisplay.classList.add('timer-' + value.toLowerCase());
    }
}

// Trigger events
async function toggleBlink() {
    try {
        const response = await axios.post('/api/traffic/toggleBlink');
        updateMode(response.data);
    } catch (error) {
        console.error('Error toggling blink:', error);
    }
}

async function togglePower() {
    try {
        const response = await axios.post('/api/traffic/togglePower');
        updateMode(response.data);
    } catch (error) {
        console.error('Error toggling power:', error);
    }
}

// Initialize WebSocket
const socket = new SockJS('/ws');
const stompClient = Stomp.over(socket);

stompClient.connect({}, function(frame) {
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/state', function(message) {
        updateUI(message.body);
    });
    stompClient.subscribe('/topic/timer', function(message) {
        const update = JSON.parse(message.body);
        updateTimer(update.type, update.value);
    });
}, function(error) {
    console.error('WebSocket error:', error);
});

// Fetch initial state and mode
async function init() {
    try {
        const stateResponse = await axios.get('/api/traffic/state');
        updateUI(stateResponse.data);
        const modeResponse = await axios.get('/api/traffic/mode');
        updateMode(modeResponse.data);
    } catch (error) {
        console.error('Error fetching initial data:', error);
    }
}

init();