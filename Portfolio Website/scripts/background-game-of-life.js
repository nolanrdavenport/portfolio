let width;
let height;
var canvas;
var context;

let squareSize;
let xPos = 500;
let yPos = 500;

let xSize = 0;
let ySize = 0;

function Cell(isAlive, xPos, yPos) {
    this.isAlive = isAlive;
    this.xPos = xPos;
    this.yPos = yPos;
}

let cells;

window.onload = function() {
    canvas = document.getElementById("backgroundCanvas");
    context = canvas.getContext("2d");
    canvas.width = Math.ceil($(window).width() / 100) * 100;
    canvas.height = Math.ceil($(window).height() / 100) * 100;

    squareSize = (canvas.width / 100);
    for (let i = 0; i < this.canvas.width; i += squareSize) {
        xSize++;
        ySize = 0;
        for (let j = 0; j < this.canvas.height; j += squareSize) {
            ySize++;
        }
    }

    cells = new this.Array(ySize);
    for (let i = 0; i < xSize; i++) {
        cells[i] = new this.Array(xSize);
    }

    for (let r = 0; r < xSize; r++) {
        for (let c = 0; c < ySize; c++) {
            cells[c][r] = new Cell(false, r * squareSize, c * squareSize);
        }
    }

    for (let i = 0; i < 800; i++) {
        let randCellX = Math.floor(Math.random() * xSize);
        let randCellY = Math.floor(Math.random() * ySize);
        cells[randCellY][randCellX].isAlive = true;
        console.log(randCellX + " " + randCellY);
    }

    this.setInterval(tick, 1000 / 20);
}

function tick() {
    context.clearRect(0, 0, canvas.width, canvas.height);
    context.fillStyle = "rgb(0, 10, 0)";
    //context.fillStyle = "white";

    for (let r = 0; r < ySize - 1; r++) {
        for (let c = 0; c < xSize - 1; c++) {
            let neighbours = 0;
            if (r >= 1 && c >= 1 && cells[r - 1][c - 1].isAlive == true) { neighbours += 1; }
            if (r >= 1 && cells[r - 1][c].isAlive == true) { neighbours += 1; }
            if (r >= 1 && cells[r - 1][c + 1].isAlive == true) { neighbours += 1; }
            if (c >= 1 && cells[r][c - 1].isAlive == true) { neighbours += 1; }
            if (cells[r][c + 1].isAlive) { neighbours += 1; }
            if (c >= 1 && cells[r + 1][c - 1].isAlive == true) { neighbours += 1; }
            if (cells[r + 1][c].isAlive == true) { neighbours += 1; }
            if (cells[r + 1][c + 1].isAlive == true) { neighbours += 1; }

            //live cell with 2 or 3 neighbors stays alive
            if (cells[r][c].isAlive && (neighbours == 2 || neighbours == 3)) {
                // YOU SURVIVE
            } else if (!cells[r][c].isAlive && neighbours == 3) {
                cells[r][c].isAlive = true;
            } else {
                cells[r][c].isAlive = false;
            }

        }
    }

    for (let r = 0; r < ySize; r++) {
        for (let c = 0; c < xSize; c++) {
            if (cells[r][c].isAlive) {
                context.fillRect(cells[r][c].xPos, cells[r][c].yPos, squareSize, squareSize);
            }
        }
    }
}