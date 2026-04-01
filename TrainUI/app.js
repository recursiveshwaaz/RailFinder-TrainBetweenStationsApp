/* ============================================
   RailFinder — app.js
   API integration + rendering
============================================ */

const API_BASE = 'http://localhost:8081';

// ── DOM ──
const sourceInput = document.getElementById('sourceCode');
const destInput   = document.getElementById('destinationCode');
const searchBtn   = document.getElementById('searchBtn');
const swapBtn     = document.getElementById('swapBtn');
const errorMsg    = document.getElementById('errorMsg');
const statusTag   = document.getElementById('statusTag');
const resultsDiv  = document.getElementById('results');
const btnText     = document.getElementById('btnText');

// ── Helpers ──
function setError(msg) {
  errorMsg.textContent = msg ? '⚠ ' + msg : '';
}

function setStatus(msg, type = '') {
  statusTag.textContent = msg;
  statusTag.className = 'status-tag ' + type;
}

function calcDuration(dep, arr) {
  try {
    const [dh, dm] = dep.split(':').map(Number);
    const [ah, am] = arr.split(':').map(Number);
    let mins = (ah * 60 + am) - (dh * 60 + dm);
    if (mins < 0) mins += 1440;
    const h = Math.floor(mins / 60);
    const m = mins % 60;
    return h > 0 ? `${h}h ${m > 0 ? m + 'm' : ''}`.trim() : `${m}m`;
  } catch { return '—'; }
}

function fmtTime(t) {
  if (!t) return '--:--';
  const [h, m] = t.split(':');
  return `${h.padStart(2,'0')}:${(m||'00').padStart(2,'0')}`;
}

// ── Skeletons ──
function showSkeletons(n = 3) {
  resultsDiv.innerHTML = '';
  for (let i = 0; i < n; i++) {
    const s = document.createElement('div');
    s.className = 'skeleton-card';
    s.style.animationDelay = `${i * 0.08}s`;
    s.innerHTML = `
      <div class="skel skel-title"></div>
      <div class="skel skel-sub"></div>
      <div class="skel skel-strip"></div>
    `;
    resultsDiv.appendChild(s);
  }
}

// ── Render one train card ──
function renderCard(train, index) {
  const card = document.createElement('div');
  card.className = 'train-card';
  card.style.animationDelay = `${index * 0.07}s`;

  const duration = calcDuration(train.departureTime, train.arrivalTime);

  card.innerHTML = `
    <div class="tc-header">
      <div>
        <div class="tc-name">${train.train.trainName}</div>
        <div class="tc-number">Train No: ${train.train.trainNumber}</div>
      </div>
      <div class="tc-badge">Route #${train.id}</div>
    </div>

    <div class="tc-journey">
      <div class="tc-station">
        <div class="tc-time">${fmtTime(train.departureTime)}</div>
        <div class="tc-code">${train.source.stationCode}</div>
        <div class="tc-stn-name">${train.source.stationName}</div>
      </div>

      <div class="tc-line">
        <div class="tc-bar"></div>
        <div class="tc-duration">🕐 ${duration}</div>
      </div>

      <div class="tc-station right">
        <div class="tc-time">${fmtTime(train.arrivalTime)}</div>
        <div class="tc-code">${train.destination.stationCode}</div>
        <div class="tc-stn-name">${train.destination.stationName}</div>
      </div>
    </div>
  `;

  return card;
}

// ── Render all results ──
function renderResults(data) {
  resultsDiv.innerHTML = '';

  if (!data || data.length === 0) {
    resultsDiv.innerHTML = `
      <div class="state-box">
        <div class="state-icon">🔍</div>
        <div class="state-title">No Trains Found</div>
        <div class="state-sub">Try different station codes and search again.</div>
      </div>
    `;
    return;
  }

  const hdr = document.createElement('div');
  hdr.className = 'results-header';
  hdr.innerHTML = `
    <div class="results-title">Available Trains</div>
    <div class="results-badge">${data.length} Result${data.length !== 1 ? 's' : ''}</div>
  `;
  resultsDiv.appendChild(hdr);

  data.forEach((t, i) => resultsDiv.appendChild(renderCard(t, i)));
}

// ── Loading state ──
function setLoading(on) {
  if (on) {
    searchBtn.disabled = true;
    searchBtn.classList.add('loading');
    btnText.textContent = 'Searching';
  } else {
    searchBtn.disabled = false;
    searchBtn.classList.remove('loading');
    btnText.textContent = 'Search Trains';
  }
}

// ── Validate ──
function validate() {
  const src  = sourceInput.value.trim();
  const dest = destInput.value.trim();

  sourceInput.closest('.input-wrap').classList.remove('error-input');
  destInput.closest('.input-wrap').classList.remove('error-input');
  setError('');

  if (!src || !dest) {
    setError('Please enter both station codes.');
    return null;
  }

  if (src.toUpperCase() === dest.toUpperCase()) {
    setError('Source and destination cannot be the same.');
    return null;
  }

  return { src: src.toUpperCase(), dest: dest.toUpperCase() };
}

// ── Main Search ──
async function handleSearch() {
  const valid = validate();
  if (!valid) return;

  const { src, dest } = valid;

  setLoading(true);
  setStatus(`Looking for trains from ${src} → ${dest}…`, '');
  showSkeletons(3);

  try {
    const url = `${API_BASE}/search/by-code?sourceCode=${src}&destinationCode=${dest}`;
    const res = await fetch(url);

    if (!res.ok) throw new Error(`Server error: ${res.status}`);

    const data = await res.json();

    // ✅ Remove duplicate trains
    const uniqueTrains = [];
    const seen = new Set();

    data.forEach(train => {
      if (!seen.has(train.train.trainNumber)) {
        seen.add(train.train.trainNumber);
        uniqueTrains.push(train);
      }
    });

    setLoading(false);

    setStatus(
      `✓ ${uniqueTrains.length} train${uniqueTrains.length !== 1 ? 's' : ''} found from ${src} → ${dest}`,
      'success'
    );

    renderResults(uniqueTrains);

  } catch (err) {
    setLoading(false);
    setStatus('', '');

    resultsDiv.innerHTML = `
      <div class="state-box">
        <div class="state-icon">⚠️</div>
        <div class="state-title">Connection Failed</div>
        <div class="state-sub">${err.message}</div>
      </div>
    `;

    setError(err.message);
  }
}

// ── Events ──
searchBtn.addEventListener('click', handleSearch);

swapBtn.addEventListener('click', () => {
  [sourceInput.value, destInput.value] = [destInput.value, sourceInput.value];
});

// ── Pre-fill ──
sourceInput.value = 'NDLS';
destInput.value = 'CST';