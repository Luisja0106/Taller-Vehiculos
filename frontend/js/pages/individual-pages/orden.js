const orderId = new URLSearchParams(window.location.search).get("id");

async function initPage() {
	await initializeOrderView();

	setUpCloseOrder();
	setUpEliminarOrden();
	setUpUsesCases();
	displayPagoInfo();
}

function displayPagoInfo() {
	const pagoElements = document.querySelectorAll(".hidden-info");
	const currentEstado = document.getElementById("orden-estado").textContent;
	if (currentEstado !== "Finalizado" && currentEstado !== "En espera de pago") {
		pagoElements.forEach((element) => {
			element.classList.remove("show");
		});
		return;
	}
	pagoElements.forEach((element) => {
		element.classList.add("show");
	});
}

function displayFinalizacionInfo() {
	const finalizacionElement = document.querySelector(".hidden-info-final");
	const currentEstado = document.getElementById("orden-estado").textContent;
	if (currentEstado !== "Finalizado") {
		finalizacionElement.forEach((element) => {
			element.classList.add("show");
		});
		return;
	}
	finalizacionElement.forEach((element) => {
		element.classList.add("show");
	});
}

async function initializeOrderView() {
	if (!orderId) {
		redirectToDashboard();
		return;
	}

	const orderData = await getOrden(orderId);
	const vehiculoData = await getVehiculoByPlaca(orderData.vehiculoPlaca);

	if (!orderData) {
		redirectToDashboard();
		return;
	}

	updatePageMetadata(orderData.id);
	renderOrdenHeader(orderData);
	renderOrdenDetails(orderData, vehiculoData);
	renderServicesList(orderData.servicios);
}

function redirectToDashboard() {
	window.location.href = "../../index.html";
}

function updatePageMetadata(id) {
	document.title = `Orden ${id} - AutoService`;
}

function renderOrdenHeader(order) {
	const heroSection = document.getElementById("hero");
	const idDisplay = document.getElementById("orden-id");

	idDisplay.textContent = order.id;
	heroSection.style.background = getBrandColor(order.vehiculoMarca);
}

function renderOrdenDetails(order, vehiculo) {
	// Información del Vehículo
	document.getElementById("orden-vehiculo").textContent =
		`${order.vehiculoModelo} ${order.vehiculoAnio}`;

	// Enlaces de Entidades (Mecánico y Cliente)
	setupLink(
		"orden-mecanico",
		order.empleadoNombre,
		`empleado.html?id=${order.empleadoId}`,
	);
	setupLink(
		"orden-cliente",
		order.clienteNombre,
		`cliente.html?id=${order.clienteId}`,
	);
	setupLink(
		"orden-vehiculo-link",
		order.vehiculoModelo,
		`vehiculo.html?placa=${vehiculo.placa}`,
	);

	// Estado y Fecha
	const estadosFormat = {
		Pendiente: "PENDIENTE",
		"En Proceso": "EN_PROCESO",
		"En espera de pago": "EN_ESPERA_DE_PAGO",
		Finalizado: "FINALIZADO",
	};
	const estadoFormateado = estadosFormat[order.estado];
	const statusElement = document.getElementById("orden-estado");
	statusElement.textContent = getEstadoText(estadoFormateado);
	statusElement.className = `info-estado ${getBadgeClass(estadoFormateado)}`;

	document.getElementById("orden-fecha").textContent = order.fechaEntrada;

	//hidden elements
	document.getElementById("orden-precio").textContent = order.valorVenta;
	document.getElementById("orden-fecha-pago").textContent = order.fechaDePago;
	document.getElementById("orden-fecha-finalizacion").textContent =
		order.fechaDeFinalizacion;
}

function setupLink(elementId, text, href) {
	const el = document.getElementById(elementId);
	el.textContent = text;
	el.href = href;
}

async function setUpEliminarOrden() {
	const btnEliminar = document.getElementById("eliminar-orden-btn");

	btnEliminar.addEventListener("click", () =>
		openConfirmation(
			"¿Estas seguro? esta accion no se puede deshacer.",
			async () => {
				const res = await removeOrden(orderId);
				if (res.success) {
					redirectToDashboard();
				}
				return res;
			},
		),
	);
}

function setUpCloseOrder() {
	const btnClose = document.getElementById("close-slide");
	btnClose.addEventListener("click", redirectToDashboard);
}

function setUpUsesCases() {
	cambiarEstadoSetUp();
	setUpRegistrarPago();
	setUpCambiarMecanico();
	setUpAñadirServicio();
}

function cambiarEstadoSetUp() {
	const avanzarEstadoBtn = document.getElementById("avanzar-estado-btn");

	const nextEstado = {
		Pendiente: "En proceso",
		"En proceso": "En espera de pago",
		"En espera de pago": "Finalizado",
		Finalizado: "INVALIDO",
	};

	const currentEstado = document.getElementById("orden-estado").textContent;

	avanzarEstadoBtn.addEventListener("click", async () => {
		openConfirmation(
			`¿Avazar al estado: "${nextEstado[currentEstado]}"?`,
			async () => {
				const res = await avanzarEstadoDeOrden(orderId);
				if (res.success) {
					window.location.reload();
				}
				return res;
			},
		);
	});
}

function setUpRegistrarPago() {
	const registrarPagoBtn = document.getElementById("registrar-pago-btn");

	registrarPagoBtn.addEventListener("click", async () => {
		openModalInput("Ingrese el valor de la orden", "number", async (valor) => {
			const res = await registrarPago(orderId, valor);
			if (res.success) {
				window.location.reload();
			}
			return res;
		});
	});
}

function setUpCambiarMecanico() {
	const cambiarMecacniciBtn = document.getElementById("cambiar-mecanico-btn");

	cambiarMecacniciBtn.addEventListener("click", async () => {
		const mecanicos = await getMecanicos();

		openSelectModal("Seleccione un mecanico", mecanicos, async (mecanicoId) => {
			const resu = await reasignarEmpleado(orderId, mecanicoId);
			if (resu.success) window.location.reload();
			return resu;
		});
	});
}

function setUpAñadirServicio() {
	const anadirServicioBtn = document.getElementById("anadir-servicio-btn");

	anadirServicioBtn.addEventListener("click", async () => {
		const servicios = await getServicios();

		openSelectModal("Selccione el Servicio", servicios, async (servicioId) => {
			const resu = await agregarServicioAOrden(orderId, servicioId);
			if (resu.success) window.location.reload();
			return resu;
		});
	});
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
              <a class="servicio-name" href="servicio.html?id=${service.id}">${service.nombre}</a>
              <button type="button" onclick="handleRemoveService('${service.id}')">Remover</button>
            </div>`;
		container.insertAdjacentHTML("beforeend", serviceHtml);
	});
}

document.addEventListener("DOMContentLoaded", initPage);
