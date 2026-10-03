const grid = document.getElementById("cards-grid"); //grid in the index html
const input = document.getElementById("search-input");
const error = document.getElementById("search-error");
const form = document.getElementById("search-form");

function renderClienteCard(cliente) {
	return `
        <article class="card" onclick="window.location.href='./individual-pages/cliente.html?id=${cliente.id}'">
          <div class="card-header" style="background: var(--color-client)">
            <span class="order-id">${escapeHtml(cliente.id)}</span>
          </div>
          <div class="card-body">
            <h3>${escapeHtml(cliente.nombre)}</h3>
            <div class="card-row">
              <span class="card-label">Email:</span>
              <span class="card-value">${escapeHtml(cliente.email)}</span>
            </div>
            <div class="card-row">
              <span class="card-label">Telefono:</span>
              <span class="card-value">${escapeHtml(cliente.telefono)}</span>
            </div>
          </div>
        </article>
  `;
}

function renderClientesCards(clientes) {
	if (!clientes || clientes.length === 0) {
		grid.innerHTML = "<p id='cards-fallback'>No hay clientes</p>";
		return;
	}

	grid.innerHTML = clientes.map(renderClienteCard).join("");
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
	if (prefix === "CLI") {
		busqueda = await getCliente(inputSearch);
	} else {
		busqueda = await getClienteByEmail(inputSearch);
	}
	if (!busqueda) {
		showError("No se encontro ningun resultado");
		return;
	}
	console.log("working");
	window.location.href = `./individual-pages/cliente.html?id=${busqueda.id}`;
}

async function initDashboard() {
	const clientes = await getClientes();
	renderClientesCards(clientes);
}

document.addEventListener("DOMContentLoaded", initDashboard);
form.addEventListener("submit", searchBarHandle);
