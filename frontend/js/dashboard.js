const grid = document.getElementById("cards-grid"); //grid in the index html
const filterBody = document.getElementById("filters-body");
const filterArrow = document.getElementById("filters-arrow");
const filterBtn = document.getElementById("filters-header");

function renderOrderCard(orden) {
	return `
        <article class="card" onclick="window.location.href='pages/individual-pages/orden.html?id=${orden.id}'">
          <div class="card-header" style="background: ${getBrandColor(orden.vehiculoMarca)}">
            <span class="order-id">${escapeHtml(orden.id)}</span>
          </div>
          <div class="card-body">
            <h3>${escapeHtml(orden.vehiculoModelo)} ${escapeHtml(orden.vehiculoAnio)}</h3>
            <div class="card-row">
              <span class="card-label">Mecánico:</span>
              <span class="card-value">${escapeHtml(orden.empleadoNombre)}</span>
            </div>
            <div class="card-row">
              <span class="card-label">Estado:</span>
              <span class="${getBadgeClass(orden.estado)}">${escapeHtml(orden.estado)}</span>
            </div>
            <div class="card-row">
              <span class="card-label">Fecha de inicio:</span>
              <span class="card-value">${escapeHtml(orden.fechaEntrada)}</span>
            </div>
          </div>
        </article>
  `;
}

function renderCards(ordenes) {
	if (!ordenes || ordenes.length === 0) {
		grid.innerHTML = "<p id='cards-fallback'>No hay órdenes</p>";
		return;
	}

	grid.innerHTML = ordenes.map(renderOrderCard).join("");
}

function toggleFilters() {
	filterBody.classList.toggle("open");
	filterArrow.classList.toggle("rotated");
}

async function initDashboard() {
	const ordenes = await getOrdenes();
	renderCards(ordenes);
}

document.addEventListener("DOMContentLoaded", initDashboard);
filterBtn.addEventListener("click", toggleFilters);
