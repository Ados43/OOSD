const net = require('net');

function rotateCW(shape) {
  const h = shape.length, w = shape[0].length;
  const out = Array.from({length: w}, () => Array(h).fill(0));
  for (let y = 0; y < h; y++)
    for (let x = 0; x < w; x++)
      out[x][h - 1 - y] = shape[y][x];
  return out;
}

function eqShape(a,b){
  if (a.length !== b.length || a[0].length !== b[0].length) return false;
  for (let y=0; y<a.length; y++)
    for (let x=0; x<a[0].length; x++)
      if (a[y][x] !== b[y][x]) return false;
  return true;
}

function uniqueRotations(shape){
  const rots = [];
  let cur = shape;
  for (let i=0;i<4;i++){
    if (!rots.some(r => eqShape(r, cur))) rots.push(cur);
    cur = rotateCW(cur);
  }
  return rots;
}

function canPlace(board, shape, col, row){
  const H = board.length, W = board[0].length;
  const h = shape.length, w = shape[0].length;
  for (let y=0;y<h;y++) for (let x=0;x<w;x++){
    if (!shape[y][x]) continue;
    const bx = col + x, by = row + y;
    if (bx < 0 || bx >= W || by < 0 || by >= H) return false;
    if (board[by][bx] !== 0) return false;
  }
  return true;
}

function dropRow(board, shape, col){
  let row = 0;
  while (canPlace(board, shape, col, row)) row++;
  return row - 1;
}

function copyBoard(b){ return b.map(r => r.slice()); }

function place(board, shape, col, row){
  const out = copyBoard(board);
  const h = shape.length, w = shape[0].length;
  for (let y=0;y<h;y++) for (let x=0;x<w;x++){
    if (shape[y][x]) out[row+y][col+x] = 1;
  }
  return out;
}

function clearLines(board){
  const W = board[0].length;
  const out = [];
  let cleared = 0;
  for (let y=0;y<board.length;y++){
    if (board[y].every(v => v !== 0)) {
      cleared++;
    } else {
      out.push(board[y].slice());
    }
  }
  while (out.length < board.length) out.unshift(Array(W).fill(0));
  return { board: out, lines: cleared };
}

// heuristic pieces
function columnHeights(board){
  const H = board.length, W = board[0].length;
  const h = Array(W).fill(0);
  for (let x=0;x<W;x++){
    for (let y=0;y<H;y++){
      if (board[y][x] !== 0) { h[x] = H - y; break; }
    }
  }
  return h;
}

function holes(board){
  const H = board.length, W = board[0].length;
  let holes = 0;
  for (let x=0;x<W;x++){
    let blockSeen = false;
    for (let y=0;y<H;y++){
      if (board[y][x] !== 0) blockSeen = true;
      else if (blockSeen) holes++;
    }
  }
  return holes;
}

function bumpiness(heights){
  let s = 0;
  for (let x=0;x<heights.length-1;x++)
    s += Math.abs(heights[x] - heights[x+1]);
  return s;
}

function evaluate(board, linesCleared){
  const hs = columnHeights(board);
  const heightScore = Math.max(...hs);
  const holesScore = holes(board);
  const bumpScore = bumpiness(hs);
  // weights similar to the tutorial
  return (-4 * heightScore) + (3 * linesCleared) - (5 * holesScore) - (2 * bumpScore);
}

function bestMove(game){
  const W = game.width, H = game.height;
  const board = game.cells;          // [y][x] 0/1
  const base = copyBoard(board);
  const rots = uniqueRotations(game.currentShape);

  let best = { score: -1e15, col: 0, rotIdx: 0 };

  for (let rIdx=0; rIdx<rots.length; rIdx++){
    const sh = rots[rIdx];
    const sw = sh[0].length, shh = sh.length;
    for (let col=0; col <= W - sw; col++){
      const row = dropRow(base, sh, col);
      if (row < 0) continue; // cannot place
      const placed = place(base, sh, col, row);
      const { board: cleared, lines } = clearLines(placed);
      const sc = evaluate(cleared, lines);
      if (sc > best.score) best = { score: sc, col, rotIdx: rIdx };
    }
  }

  // Fallback (no placement possible): do something harmless
  if (!isFinite(best.score)) return { opX: 0, opRotate: 0 };

  return { opX: best.col, opRotate: best.rotIdx };
}

const server = net.createServer((socket) => {
  let buf = '';
  socket.on('data', (chunk) => {
    buf += chunk.toString('utf8');
    const nl = buf.indexOf('\n');
    if (nl >= 0) {
      const line = buf.slice(0, nl).trim();
      buf = buf.slice(nl + 1);
      try {
        const game = JSON.parse(line);
        const move = bestMove(game);
        socket.write(JSON.stringify(move) + '\n');
      } catch (e) {
        socket.write(JSON.stringify({ opX: 0, opRotate: 0 }) + '\n');
      }
      socket.end();
    }
  });
});

server.listen(3000, '127.0.0.1', () => {
  console.log('TetrisServer listening on localhost:3000');
});
