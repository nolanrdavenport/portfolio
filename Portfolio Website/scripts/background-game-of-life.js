let width;
let height;
var canvas;
var context;

let squareSize;

let xSize = 0;
let ySize = 0;

// Holds the data for each square or, Cell.
function Cell(isAlive, xPos, yPos) {
    this.isAlive = isAlive;
    this.xPos = xPos;
    this.yPos = yPos;
}

let cells;
let gameColor = "rgb(0, 20, 0)";

let interval;

function changeColor(newColor){
    gameColor = newColor;
}

// Initialize game
window.onload = function() {
    this.init();
}

function init(){
    console.time('game_initialize_time');

    canvas = document.getElementById("backgroundCanvas");
    context = canvas.getContext("2d");
    canvas.width = Math.ceil($(window).width() / 200) * 200;
    canvas.height = Math.ceil($(window).height() / 200) * 200;

    squareSize = (canvas.width / 200);
    for (let i = 0; i < this.canvas.width; i += squareSize) {
        xSize++;
        ySize = 0;
        for (let j = 0; j < this.canvas.height; j += squareSize) {
            ySize++;
        }
    }

    cells = new this.Array(ySize);
    for (let i = 0; i < ySize; i++) {
        cells[i] = new this.Array(xSize);
    }
    
    for (let r = 0; r < ySize; r++) {
        for (let c = 0; c < xSize; c++) {
            cells[r][c] = new Cell(false, c * squareSize, r * squareSize);
        }
    }
    for (let i = 0; i < 10000; i++) {
        let xShotGun = Math.floor((Math.random() * xSize));
        let yShotGun = Math.floor((Math.random() * ySize));
        cells[yShotGun][xShotGun].isAlive = true;
    }

    // Starts the game loop
    interval = this.setInterval(tick, 1000 / 20);
    
    console.timeEnd('game_initialize_time');
}

// Each tick is a frame in the game
function tick() {
    context.clearRect(0, 0, canvas.width, canvas.height);
    context.fillStyle = gameColor;

    let next;
    next = new this.Array(ySize);
    for (let i = 0; i < ySize; i++) {
        next[i] = new this.Array(xSize);
    }

    for (let r = 0; r < ySize; r++) {
        for (let c = 0; c < xSize; c++) {
            next[r][c] = new Cell(false, c * squareSize, r * squareSize);
        }
    }
    for (let r = 0; r < ySize - 1; r++) {
        for (let c = 0; c < xSize - 1; c++) {
            let neighbours = 0;

            for (let i = -1; i <= 1; i++) {
                for (let j = -1; j <= 1; j++) {
                    if ((r + i) >= 0 && (c + j) >= 0 && cells[r + i][c + j].isAlive) {
                        neighbours++;
                    }
                }
            }
            if (cells[r][c].isAlive) {
                neighbours -= 1;
            }

            // Rules of the game
            if ((cells[r][c].isAlive) && neighbours < 2) {
                next[r][c].isAlive = false;
            } else if ((cells[r][c].isAlive) && neighbours > 3) {
                next[r][c].isAlive = false;
            } else if ((cells[r][c].isAlive == false) && neighbours == 3) {
                next[r][c].isAlive = true;
            } else {
                next[r][c].isAlive = cells[r][c].isAlive;
            }

        }
    }
    for (let r = 0; r < ySize; r++) {
        for (let c = 0; c < xSize; c++) {
            cells[r][c] = next[r][c];
        }
    }



    // Draws the game onto the canvas
    for (let r = 0; r < ySize; r++) {
        for (let c = 0; c < xSize; c++) {
            if (cells[r][c].isAlive) {
                context.fillRect(cells[r][c].xPos, cells[r][c].yPos, squareSize, squareSize);
            }
        }
    }
}

$(window).resize(function() {
    clearInterval(interval);

    width = undefined;
    height = undefined;
    canvas = undefined;
    context = undefined;
    squareSize = undefined;
    xSize = 0;
    ySize = 0;
    cells = undefined;

    init();
});