async function initializeOrderView() {
	const clienteId = new URLSearchParams(window.location.search).get("id");

	if (!clienteId) {
		redirectToDashboard();
		return;
	}

	const clienteData = await getCliente(clienteId);
	const vehiculosDueno = await getVehiculosDeUnCliente(clienteId);

	if (!clienteData) {
		redirectToDashboard();
		return;
	}

	updatePageMetadata(clienteData.id);
	renderServicioHeader(clienteData);
	renderServicioDetails(clienteData);
	renderOrdenesQueUsa(vehiculosDueno);
}

function redirectToDashboard() {
	window.location.href = "../../index.html";
}

function updatePageMetadata(id) {
	document.title = `Cliente ${id} - AutoService`;
}

function renderServicioHeader(cliente) {
	const heroSection = document.getElementById("hero");
	const idDisplay = document.getElementById("cliente-id");

	idDisplay.textContent = cliente.id;
	heroSection.style.background = "var(--color-complementary-client)";
}

function renderServicioDetails(cliente) {
	// Información del Cliente
	document.getElementById("nombre-cliente-header").textContent =
		`${cliente.nombre}`;

	// Nombre del cliente
	document.getElementById("nombre-cliente").textContent = cliente.nombre;
	//Email del cliente
	document.getElementById("email-cliente").textContent = cliente.email;
	//Telefono cliente
	document.getElementById("telefono-cliente").textContent = cliente.telefono;
}

async function renderOrdenesQueUsa(vehiculos) {
	const container = document.getElementById("vehiculos-posee");

	if (!vehiculos || vehiculos.length === 0) {
		container.innerHTML = "<p id='servicios-fallback'>No tiene vehiculos</p>";
		return;
	}

	container.innerHTML = "";

	vehiculos.forEach((vehiculo) => {
		const ordenHtml = `
            <div class="servicios-box">
              <a class="servicio-name vehiculo-modelo" href="vehiculo.html?id=${vehiculo.id}">${getMarcaText(vehiculo.marca)} ${vehiculo.modelo} ${vehiculo.anio}</a>
              <a class="servicio-name vehiculo-id info-estado" href="vehiculo.html?id=${vehiculo.id}">${vehiculo.id}</a>
            </div>`;
		container.insertAdjacentHTML("beforeend", ordenHtml);
	});
}

document.addEventListener("DOMContentLoaded", initializeOrderView);
