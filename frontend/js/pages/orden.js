async function initializeOrderView() {
	const orderId = new URLSearchParams(window.location.search).get("id");

	if (!orderId) {
		redirectToDashboard();
		return;
	}

	const orderData = await getOrden(orderId);

	if (!orderData) {
		redirectToDashboard();
		return;
	}

	updatePageMetadata(orderData.id);
	renderOrderHeader(orderData);
	renderOrderDetails(orderData);
	renderServicesList(orderData.servicios);
}

function redirectToDashboard() {
	window.location.href = "../index.html";
}

function updatePageMetadata(id) {
	document.title = `Orden ${id} - AutoService`;
}

function renderOrderHeader(order) {
	const heroSection = document.getElementById("hero");
	const idDisplay = document.getElementById("orden-id");

	idDisplay.textContent = order.id;
	heroSection.style.background = getBrandColor(order.vehiculoMarca);
}

function renderOrderDetails(order) {
	// Información del Vehículo
	document.getElementById("orden-vehiculo").textContent =
		`${order.vehiculoModelo} ${order.vehiculoAnio}`;

	// Enlaces de Entidades (Mecánico y Cliente)
	setupLink(
		"orden-mecanico",
		order.empleadoNombre,
		`mecanico.html?id=${order.empleadoId}`,
	);
	setupLink(
		"orden-cliente",
		order.clienteNombre,
		`cliente.html?id=${order.clienteId}`,
	);

	// Estado y Fecha
	const statusElement = document.getElementById("orden-estado");
	statusElement.textContent = getEstadoText(order.estado);
	statusElement.className = `info-estado ${getBadgeClass(order.estado)}`;

	document.getElementById("orden-fecha").textContent = order.fechaEntrada;
}

function setupLink(elementId, text, href) {
	const el = document.getElementById(elementId);
	el.textContent = text;
	el.href = href;
}

function renderServicesList(services) {
	const container = document.getElementById("servicios-lista");

	if (!services || services.length === 0) {
		container.innerHTML =
			"<p id='servicios-fallback'>No hay servicios aplicados</p>";
		return;
	}

	container.innerHTML = "";

	services.forEach((service) => {
		const serviceHtml = `
            <div class="servicios-box">
              <span class="servicio-name">${service.nombre}</span>
              <button type="button" onclick="handleRemoveService('${service.id}')">Remover</button>
            </div>`;
		container.insertAdjacentHTML("beforeend", serviceHtml);
	});
}

document.addEventListener("DOMContentLoaded", initializeOrderView);
