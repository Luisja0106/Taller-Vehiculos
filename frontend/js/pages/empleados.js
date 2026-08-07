const grid = document.getElementById("cards-grid"); //grid in the index html
const input = document.getElementById("search-input");
const error = document.getElementById("search-error");
const form = document.getElementById("search-form");

function renderEmpleadoCard(empleado) {
	return `
        <article class="card" onclick="window.location.href='./individual-pages/empleado.html?id=${empleado.id}'">
          <div class="card-header" style="background: ${getRoleColor(empleado.rol)}">
            <span class="order-id">${escapeHtml(empleado.id)}</span>
          </div>
          <div class="card-body">
            <h3>${escapeHtml(empleado.nombre)}</h3>
            <div class="card-row">
              <span class="card-label">Rol:</span>
              <span class="card-value">${escapeHtml(getRoleText(empleado.rol))}</span>
            </div>
            <div class="card-row">
              <span class="card-label">Email:</span>
              <span class="card-value">${escapeHtml(empleado.email)}</span>
            </div>
            <div class="card-row">
              <span class="card-label">Telefono:</span>
              <span class="card-value">${escapeHtml(empleado.telefono)}</span>
            </div>
          </div>
        </article>
  `;
}

function renderEmpleadosCards(empleados) {
	if (!empleados || empleados.length === 0) {
		grid.innerHTML = "<p id='cards-fallback'>No hay empleados</p>";
		return;
	}

	grid.innerHTML = empleados.map(renderEmpleadoCard).join("");
}

async function initDashboard() {
	const empleados = await getEmpleados();
	renderEmpleadosCards(empleados);
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
	if (prefix === "EMP") {
		busqueda = await getEmpleado(inputSearch);
	} else {
		busqueda = await getEmpleadoEmail(inputSearch);
	}
	if (!busqueda) {
		showError("No se encontro ningun resultado");
		return;
	}
	console.log("working");
	window.location.href = `./individual-pages/empleado.html?id=${busqueda.id}`;
}

document.addEventListener("DOMContentLoaded", initDashboard);
form.addEventListener("submit", searchBarHandle);
