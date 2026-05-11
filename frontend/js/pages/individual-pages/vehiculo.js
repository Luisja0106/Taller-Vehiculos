const btnClose = document.getElementById("close-slide");

async function initializeOrderView() {
	const vehiculoId = new URLSearchParams(window.location.search).get("id");

	if (!vehiculoId) {
		redirectToDashboard();
		return;
	}

	const vehiculoData = await getVehiculo(vehiculoId);
	const dueñoData = await getCliente(vehiculoData.clienteId);
	const vehiculoOrdenes = await getOrdenesByVehiculo(vehiculoData.placa);

	if (!vehiculoData) {
		redirectToDashboard();
		return;
	}

	updatePageMetadata(vehiculoData.id);
	renderVehiculoHeader(vehiculoData);
	renderVehiculoDetails(vehiculoData, dueñoData);
	renderOrdenesList(vehiculoOrdenes);
}

function redirectToDashboard() {
	window.location.href = "../../index.html";
}

function closeSlide() {
	window.location.href = "../vehiculos.html";
}

function updatePageMetadata(id) {
	document.title = `Vehiculo ${id} - AutoService`;
}

function renderVehiculoHeader(vehiculo) {
	const heroSection = document.getElementById("hero");
	const idDisplay = document.getElementById("vehiculo-id");

	idDisplay.textContent = vehiculo.id;
	heroSection.style.background = getBrandColor(vehiculo.marca);
}

function renderVehiculoDetails(vehiculo, cliente) {
	// Información del vehiculo
	document.getElementById("modelo-vehiculo-header").textContent =
		`${getMarcaText(vehiculo.marca)} ${vehiculo.modelo} ${vehiculo.anio}`;

	// Nombre del vehiculo
	document.getElementById("modelo-vehiculo").textContent =
		`${getMarcaText(vehiculo.marca)} ${vehiculo.modelo}`;
	//Placa del vehiculo
	document.getElementById("placa-vehiculo").textContent = vehiculo.placa;
	//vehiculo dueño
	document.getElementById("dueño-vehiculo").textContent = cliente.nombre;
	document.getElementById("dueño-vehiculo").href =
		`cliente.html?id=${vehiculo.clienteId}`;

	//año vehiculo
	document.getElementById("anio-vehiculo").textContent = vehiculo.anio;
	//telefono empleado
	document.getElementById("marca-vehiculo").textContent = getMarcaText(
		vehiculo.marca,
	);
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
		const ordenHtml = `
            <div class="servicios-box">
              <a class="servicio-name order-id" href="orden.html?id=${orden.id}"> ${orden.id}</a>
              <a class="servicio-name order-empleado" href="empleado.html?id=${orden.empleadoId}"> ${orden.empleadoNombre}</a>
              <span class="servicio-name order-estado badge-${orden.estado}" href="orden.html?id=${orden.id}">${getEstadoText(orden.estado)}</span>
            </div>`;
		container.insertAdjacentHTML("beforeend", ordenHtml);
	});
}

document.addEventListener("DOMContentLoaded", initializeOrderView);
btnClose.addEventListener("click", closeSlide);
