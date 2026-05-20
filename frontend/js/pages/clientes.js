const grid = document.getElementById("cards-grid"); //grid in the index html

function renderClienteCard(cliente) {
	return `
        <article class="card" onclick="window.location.href='./individual-pages/cliente.html?id=${cliente.id}'">
          <div class="card-header" style="background: var(--color-client)">
            <span class="order-id">${cliente.id}</span>
          </div>
          <div class="card-body">
            <h3>${cliente.nombre}</h3>
            <div class="card-row">
              <span class="card-label">Email:</span>
              <span class="card-value">${cliente.email}</span>
            </div>
            <div class="card-row">
              <span class="card-label">Telefono:</span>
              <span class="card-value">${cliente.telefono}</span>
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

async function initDashboard() {
	const clientes = await getClientes();
	renderClientesCards(clientes);
}

document.addEventListener("DOMContentLoaded", initDashboard);
