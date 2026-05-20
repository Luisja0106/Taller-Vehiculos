const grid = document.getElementById("cards-grid"); //grid in the index html

function renderServicioCard(servicio) {
	return `
        <article class="card" onclick="window.location.href='./individual-pages/servicio.html?id=${servicio.id}'">
          <div class="card-header" style="background: var(--color-servicio)">
            <span class="order-id">${servicio.id}</span>
          </div>
          <div class="card-body">
            <h3>${servicio.nombre}</h3>
            <div class="card-row">
              <span class="card-label">Precio:</span>
              <span class="card-value">$${servicio.precio}</span>
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

document.addEventListener("DOMContentLoaded", initDashboard);
