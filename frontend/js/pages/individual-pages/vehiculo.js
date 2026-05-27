const vehiculoPlaca = new URLSearchParams(window.location.search).get("placa");

async function initPage() {
	await initializeOrderView();
	setUpCloseButton();
	await setUpEliminarVehculo();
}

async function setUpEliminarVehculo() {
	const eliminarBtn = document.getElementById("eliminar-vehiculo-btn");

	eliminarBtn.addEventListener("click", async () => {
		openConfirmation(
			"¿Esta seguro que desea eliminar este vehiculo?",
			async () => {
				const resu = await removeVehiculo(vehiculoPlaca);
				if (resu.success) {
					closeSlide();
				}
				return resu;
			},
		);
	});
}

async function initializeOrderView() {
	const vehiculoPlaca = new URLSearchParams(window.location.search).get(
		"placa",
	);

	if (!vehiculoPlaca) {
		redirectToDashboard();
		return;
	}

	const vehiculoData = await getVehiculo(vehiculoPlaca);
	if (!vehiculoData) {
		redirectToDashboard();
		return;
	}
	const idCliente = vehiculoData.dueñoId || vehiculoData.id_cliente;

	let dueñoData = { nombre: "No asignado" };
	let vehiculoOrdenes = [];

	try {
		if (idCliente) {
			dueñoData = await getCliente(idCliente);
		}
		vehiculoOrdenes = await getOrdenesActivas(null, vehiculoData.placa);
	} catch (error) {
		console.error("Error cargando datos relaciones", error);
	}

	updatePageMetadata(vehiculoData.placa);
	renderVehiculoHeader(vehiculoData);
	renderVehiculoDetails(vehiculoData, dueñoData);
	renderOrdenesList(vehiculoOrdenes);
}

function redirectToDashboard() {
	window.location.href = "../../index.html";
}

function setUpCloseButton() {
	const btnClose = document.getElementById("close-slide");
	btnClose.addEventListener("click", closeSlide);
}

function closeSlide() {
	window.location.href = "../vehiculos.html";
}

function updatePageMetadata(placa) {
	document.title = `Vehiculo ${placa} - AutoService`;
}

function renderVehiculoHeader(vehiculo) {
	const heroSection = document.getElementById("hero");
	const idDisplay = document.getElementById("vehiculo-id");

	idDisplay.textContent = vehiculo.placa;
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
		`cliente.html?id=${vehiculo.dueñoId}`;

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

	const estadosFormat = {
		Pendiente: "PENDIENTE",
		"En Proceso": "EN_PROCESO",
		"En espera de pago": "EN_ESPERA_DE_PAGO",
		Finalizado: "FINALIZADO",
	};

	ordenes.forEach((orden) => {
		const ordenHtml = `
            <div class="servicios-box">
              <a class="servicio-name order-id" href="orden.html?id=${orden.id}"> ${orden.id}</a>
              <a class="servicio-name order-empleado" href="empleado.html?id=${orden.empleadoId}"> ${orden.empleadoNombre}</a>
              <span class="servicio-name order-estado badge-${estadosFormat[orden.estado]}" href="orden.html?id=${orden.id}">${getEstadoText(estadosFormat[orden.estado])}</span>
            </div>`;
		container.insertAdjacentHTML("beforeend", ordenHtml);
	});
}

document.addEventListener("DOMContentLoaded", initPage);
