async function initializeOrderView() {
	const empleadoId = new URLSearchParams(window.location.search).get("id");

	if (!empleadoId) {
		redirectToDashboard();
		return;
	}

	const empleadoData = await getEmpleado(empleadoId);
	const empleadoOrdenes = await getOrdenesByEmpleado(empleadoId);

	if (!empleadoData) {
		redirectToDashboard();
		return;
	}

	updatePageMetadata(empleadoData.id);
	renderEmpleadoHeader(empleadoData);
	renderVehiculoDetails(empleadoData);
	renderOrdenesList(empleadoOrdenes.ordenes);
}

function redirectToDashboard() {
	window.location.href = "../../index.html";
}

function updatePageMetadata(id) {
	document.title = `Empleado ${id} - AutoService`;
}

function renderEmpleadoHeader(empleado) {
	const heroSection = document.getElementById("hero");
	const idDisplay = document.getElementById("empleado-id");

	idDisplay.textContent = empleado.id;
	heroSection.style.background = getRoleColor(empleado.rol);
}

function renderVehiculoDetails(empleado) {
	// Información del Empledo
	document.getElementById("nombre-empleado-header").textContent =
		`${empleado.nombre}`;

	// Nombre del empleado
	document.getElementById("nombre-empleado").textContent = empleado.nombre;
	//Rol del empleado
	document.getElementById("rol-empleado").textContent = getRoleText(
		empleado.rol,
	);
	//Contrato empleado
	document.getElementById("contrato-empleado").textContent = getContratoText(
		empleado.contrato,
	);

	//email empleado
	document.getElementById("email-empleado").textContent = empleado.email;
	//telefono empleado
	document.getElementById("telefono-empleado").textContent = empleado.telefono;
}

function renderOrdenesList(ordenes) {
	const container = document.getElementById("ordenes-activas");

	if (!ordenes || ordenes.length === 0) {
		container.innerHTML =
			"<p id='servicios-fallback'>No tiene ordenes activas</p>";
		return;
	}

	container.innerHTML = "";

	ordenes.forEach((orden) => {
		//TODO: poner bonito esto =>
		const ordenHtml = `
            <div class="servicios-box">
              <a class="servicio-name order-id" href="orden.html?id=${orden.id}">${orden.id}</a>
              <a class="servicio-name order-vehiculo" href="orden.html?id=${orden.id}">${orden.vehiculo}</a>
              <span class="servicio-name order-estado badge-${orden.estado} info-estado" href="orden.html?id=${orden.id}">${getEstadoText(orden.estado)}</span>
            </div>`;
		container.insertAdjacentHTML("beforeend", ordenHtml);
	});
}

document.addEventListener("DOMContentLoaded", initializeOrderView);
