const grid = document.getElementById("cards-grid"); //grid in the index html

function renderEmpleadoCard(empleado) {
	return `
        <article class="card" onclick="window.location.href='./individual-pages/empleado.html?id=${empleado.id}'">
          <div class="card-header" style="background: ${getRoleColor(empleado.rol)}">
            <span class="order-id">${empleado.id}</span>
          </div>
          <div class="card-body">
            <h3>${empleado.nombre}</h3>
            <div class="card-row">
              <span class="card-label">Rol:</span>
              <span class="card-value">${getRoleText(empleado.rol)}</span>
            </div>
            <div class="card-row">
              <span class="card-label">Email:</span>
              <span class="card-value">${empleado.email}</span>
            </div>
            <div class="card-row">
              <span class="card-label">Telefono:</span>
              <span class="card-value">${empleado.telefono}</span>
            </div>
          </div>
        </article>
  `;
}

function renderEmpleadosCards(empleados) {
	if (!empleados || empleados.length === 0) {
		grid.innerHTML = "<p>No hay empleados</p>";
		return;
	}

	grid.innerHTML = empleados.map(renderEmpleadoCard).join("");
}

async function initDashboard() {
	const empleados = await getEmpleados();
	renderEmpleadosCards(empleados);
}

document.addEventListener("DOMContentLoaded", initDashboard);
