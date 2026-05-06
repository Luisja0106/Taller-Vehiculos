const grid = document.getElementById("cards-grid"); //grid in the index html

function renderVehiculoCard(vehiculo) {
	return `
        <article class="card" onclick="window.location.href='./individual-pages/empleado.html?id=${vehiculo.id}'">
          <div class="card-header" style="background: ${getBrandColor(vehiculo.marca)}">
            <span class="order-id">${vehiculo.id}</span>
          </div>
          <div class="card-body">
            <h3>${getMarcaText(vehiculo.marca)} ${vehiculo.modelo} ${vehiculo.anio}</h3>
            <div class="card-row">
              <span class="card-label">Placa:</span>
              <span class="card-value">${vehiculo.placa}</span>
            </div>
            <div class="card-row">
              <span class="card-label">Marca:</span>
              <span class="card-value">${getMarcaText(vehiculo.marca)}</span>
            </div>
            <div class="card-row">
              <span class="card-label">Año:</span>
              <span class="card-value">${vehiculo.anio}</span>
            </div>
          </div>
        </article>
  `;
}

function renderVehiculosCards(vehiculos) {
	if (!vehiculos || vehiculos.length === 0) {
		grid.innerHTML = "<p>No hay vehiculos</p>";
		return;
	}

	grid.innerHTML = vehiculos.map(renderVehiculoCard).join("");
}

async function initDashboard() {
	const vehiculos = await getVehiculos();
	renderVehiculosCards(vehiculos);
}

document.addEventListener("DOMContentLoaded", initDashboard);
