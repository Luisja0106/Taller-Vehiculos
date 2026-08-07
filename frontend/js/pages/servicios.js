const grid = document.getElementById("cards-grid"); //grid in the index html
const input = document.getElementById("search-input");
const error = document.getElementById("search-error");
const form = document.getElementById("search-form");

function renderServicioCard(servicio) {
	return `
        <article class="card" onclick="window.location.href='./individual-pages/servicio.html?id=${servicio.id}'">
          <div class="card-header" style="background: var(--color-servicio)">
            <span class="order-id">${escapeHtml(servicio.id)}</span>
          </div>
          <div class="card-body">
            <h3>${escapeHtml(servicio.nombre)}</h3>
            <div class="card-row">
              <span class="card-label">Precio:</span>
              <span class="card-value">$${escapeHtml(servicio.precio)}</span>
            </div>
          </div>
        </article>
  `;
}

function renderServiciosCards(servicios) {
	if (!servicios || servicios.length === 0) {
		grid.innerHTML = "<p id='cards-fallback'>No hay servicios</p>";
		return;
	}

	grid.innerHTML = servicios.map(renderServicioCard).join("");
}

async function initDashboard() {
	const servicios = await getServicios();
	renderServiciosCards(servicios);
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
	if (prefix === "SRV") {
		busqueda = await getServicio(inputSearch);
	} else {
		busqueda = await getServicioPorNombre(inputSearch);
	}
	if (!busqueda) {
		showError("No se encontro ningun resultado");
		return;
	}
	console.log("working");
	window.location.href = `./individual-pages/servicio.html?id=${busqueda.id}`;
}

document.addEventListener("DOMContentLoaded", initDashboard);
form.addEventListener("submit", searchBarHandle);
