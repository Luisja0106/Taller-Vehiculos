const btnClose = document.getElementById("close-slide");

async function initializeOrderView() {
	const servicioId = new URLSearchParams(window.location.search).get("id");

	if (!servicioId) {
		redirectToDashboard();
		return;
	}

	const servicioData = await getServicio(servicioId);
	const ordenesAplicado = await getOrdenesByServicio(servicioId);

	if (!servicioData) {
		redirectToDashboard();
		return;
	}

	updatePageMetadata(servicioData.id);
	renderServicioHeader(servicioData);
	renderServicioDetails(servicioData);
	renderOrdenesQueUsa(ordenesAplicado);
}

function redirectToDashboard() {
	window.location.href = "../../index.html";
}

function closeSlide() {
	window.location.href = "../servicios.html";
}

function updatePageMetadata(id) {
	document.title = `Servicio ${id} - AutoService`;
}

function renderServicioHeader(servicio) {
	const heroSection = document.getElementById("hero");
	const idDisplay = document.getElementById("servicio-id");

	idDisplay.textContent = servicio.id;
	heroSection.style.background = "var(--color-complementary-servicio)";
}

function renderServicioDetails(servicio) {
	// Información del servicio
	document.getElementById("nombre-servicio-header").textContent =
		`${servicio.nombre}`;

	// Nombre del servicio
	document.getElementById("nombre-servicio").textContent = servicio.nombre;
	//Precio Servicio
	document.getElementById("precio-servicio").textContent = servicio.precio;
}

async function renderOrdenesQueUsa(ordenes) {
	const container = document.getElementById("ordenes-aplicadas");

	if (!ordenes || ordenes.length === 0) {
		container.innerHTML =
			"<p id='servicios-fallback'>No tiene Ordenes que la apliquen</p>";
		return;
	}

	container.innerHTML = "";

	ordenes.forEach((orden) => {
		const ordenHtml = `
            <div class="servicios-box">
              <a class="servicio-name order-id" href="orden.html?id=${orden.id}">${orden.id}</a>
              <span class="servicio-name order-estado badge-${orden.estado} info-estado" href="orden.html?id=${orden.id}">${getEstadoText(orden.estado)}</span>
            </div>`;
		container.insertAdjacentHTML("beforeend", ordenHtml);
	});
}

document.addEventListener("DOMContentLoaded", initializeOrderView);
btnClose.addEventListener("click", closeSlide);
