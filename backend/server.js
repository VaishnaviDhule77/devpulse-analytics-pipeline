const express = require('express');
const fs = require('fs');
const path = require('path');
const { execSync } = require('child_process');

const app = express();
app.use(express.json());

// Enable CORS so your web and mobile apps can connect without blocks
app.use((req, res, next) => {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    next();
});

const DB_FILE = path.join(__dirname, 'metrics.json');

// Initialize local JSON database if it doesn't exist
if (!fs.existsSync(DB_FILE)) {
    fs.writeFileSync(DB_FILE, JSON.stringify([]));
}

// 1. POST Endpoint: Log a coding session
app.post('/api/logs', (req, res) => {
    const { minutes, language, focusType } = req.body;
    if (!minutes || !language || !focusType) {
        return res.status(400).json({ error: "Missing required fields" });
    }

    const currentData = JSON.parse(fs.readFileSync(DB_FILE, 'utf8'));
    const newLog = { minutes: parseInt(minutes), language, focusType, timestamp: new Date().toISOString() };
    currentData.push(newLog);
    
    fs.writeFileSync(DB_FILE, JSON.stringify(currentData, null, 2));
    res.status(201).json({ message: "Log saved successfully", data: newLog });
});

// 2. GET Endpoint: Trigger Python data engine and return analytics
app.get('/api/analytics', (req, res) => {
    try {
        // Reverted to python3 for Render Linux compatibility
        const pythonOutput = execSync('python3 analytics.py', { encoding: 'utf-8' });
        res.json(JSON.parse(pythonOutput));
    } catch (error) {
        res.status(500).json({ error: "Data engine error", details: error.message });
    }
});

// Use Render's dynamic port assignment or fallback to 3000 locally
const PORT = process.env.PORT || 3000;
app.listen(PORT, () => console.log(`🚀 DevPulse Backend running on port ${PORT}`));