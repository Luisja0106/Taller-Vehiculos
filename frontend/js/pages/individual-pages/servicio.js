const servicioId = new URLSearchParams(window.location.search).get("id");

async function initPage() {
	await initializeOrderView();
	setUpCloseButton();
	setUpEliminarServcio();
}
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

async function setUpEliminarServcio() {
	const btnEliminar = document.getElementById("eliminar-servicio-btn");
	btnEliminar.addEventListener("click", async () => {
		openConfirmation(
			"¿Estás seguro de que deseas eliminar este servicio?",
			async () => {
				const resu = await removeServicio(servicioId);
				if (resu.success) {
					closeSlide();
				}
				return resu;
			},
		);
	});
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

function setUpCloseButton() {
	const btnClose = document.getElementById("close-slide");
	btnClose.addEventListener("click", closeSlide);
}

async function renderOrdenesQueUsa(ordenes) {
	const container = document.getElementById("ordenes-aplicadas");

	if (!ordenes || ordenes.length === 0) {
		container.innerHTML =
			"<p id='servicios-fallback'>No tiene Ordenes que la apliquen</p>";
		return;
	}

	container.innerHTML = "";
	const estadosFormat = {
		Pendiente: "PENDIENTE",
		"En Proceso": "EN_PROCESO",
		"En espera de pago": "EN_ESPERA_DE_PAGO",
		Finalizado: "FINALIZADO",
	};

	ordenes.forEach((orden) => {
		const ordenHtml = `
            <div class="servicios-box">
              <a class="servicio-name order-id" href="orden.html?id=${orden.id}">${orden.id}</a>
              <span class="servicio-name order-estado badge-${estadosFormat[orden.estado]} info-estado" href="orden.html?id=${orden.id}">${getEstadoText(estadosFormat[orden.estado])}</span>
            </div>`;
		container.insertAdjacentHTML("beforeend", ordenHtml);
	});
}

document.addEventListener("DOMContentLoaded", initPage);
