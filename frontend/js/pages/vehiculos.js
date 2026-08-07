const grid = document.getElementById("cards-grid"); //grid in the index html
const input = document.getElementById("search-input");
const error = document.getElementById("search-error");
const form = document.getElementById("search-form");

function renderVehiculoCard(vehiculo) {
	return `
        <article class="card" onclick="window.location.href='./individual-pages/vehiculo.html?placa=${vehiculo.placa}'">
          <div class="card-header" style="background: ${getBrandColor(vehiculo.marca)}">
            <span class="order-id">VHC-${escapeHtml(vehiculo.placa)}</span>
          </div>
          <div class="card-body">
            <h3>${getMarcaText(escapeHtml(vehiculo.marca))} ${escapeHtml(vehiculo.modelo)} ${escapeHtml(vehiculo.anio)}</h3>
            <div class="card-row">
              <span class="card-label">Placa:</span>
              <span class="card-value">${escapeHtml(vehiculo.placa)}</span>
            </div>
            <div class="card-row">
              <span class="card-label">Marca:</span>
              <span class="card-value">${getMarcaText(escapeHtml(vehiculo.marca))}</span>
            </div>
            <div class="card-row">
              <span class="card-label">Año:</span>
              <span class="card-value">${escapeHtml(vehiculo.anio)}</span>
            </div>
          </div>
        </article>
  `;
}

function renderVehiculosCards(vehiculos) {
	if (!vehiculos || vehiculos.length === 0) {
		grid.innerHTML = "<p id='cards-fallback'>No hay vehiculos</p>";
		return;
	}

	grid.innerHTML = vehiculos.map(renderVehiculoCard).join("");
}

async function initDashboard() {
	const vehiculos = await getVehiculos();
	renderVehiculosCards(vehiculos);
}

function showError(message) {
	error.textContent = message;

	error.classList.add("show");
}

function hideError() {
	error.classList.remove("show");
}

async function searchBarHandle(event) {
	event.preventDefault(); //prevent pages reload on form submit

	const inputSearch = input.value.trim().toUpperCase();

	if (!inputSearch) {
		hideError();
		return;
	}

	hideError();

	const prefix = inputSearch.substring(0, 3).toUpperCase();

	let busqueda;
	if (prefix === "VHC") {
		busqueda = await fetchByPrefix(inputSearch);
	} else {
		busqueda = await getVehiculoByPlaca(inputSearch);
	}
	if (!busqueda) {
		showError("No se encontro ningun resultado");
		return;
	}
	console.log("working");
	window.location.href = `./individual-pages/vehiculo.html?placa=${busqueda.placa}`;
}

document.addEventListener("DOMContentLoaded", initDashboard);
form.addEventListener("submit", searchBarHandle);
