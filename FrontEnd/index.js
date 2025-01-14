const http = require('http');
const fs = require('fs');
const path = require('path');
const PORT = 3000;

const server = http.createServer((req, res) => {
    res.writeHead(200, { 'Content-Type': 'text/html' });

    fs.readFile(path.join(__dirname, 'index.html'), 'utf-8', (err, data) => {
        if (err) {
            res.writeHead(500, { 'Content-Type': 'text/plain' });
            res.end('Error loading the HTML page.');
        } else {
            res.end(data);
        }
    });
});

server.listen(PORT, () => {
    console.log(`Server is running at http://localhost:${PORT}`);
});
