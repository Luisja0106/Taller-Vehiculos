const empleadoId = new URLSearchParams(window.location.search).get("id");

async function initView() {
	await initializeOrderView();
	closeInfo();
	setUpEliminarEmpleado();
}

async function initializeOrderView() {
	if (!empleadoId) {
		redirectToDashboard();
		return;
	}

	const empleadoData = await getEmpleado(empleadoId);
	const empleadoOrdenes = await getOrdenesActivas(empleadoId, null);

	if (!empleadoData) {
		redirectToDashboard();
		return;
	}

	updatePageMetadata(empleadoData.id);
	renderEmpleadoHeader(empleadoData);
	renderVehiculoDetails(empleadoData);
	renderOrdenesList(empleadoOrdenes);
}

function closeInfo() {
	const btnClose = document.getElementById("close-slide");
	btnClose.addEventListener("click", closeSlide);
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

function closeSlide() {
	window.location.href = "../empleados.html";
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

async function renderOrdenesList(ordenes) {
	const container = document.getElementById("ordenes-activas");

	if (!ordenes || ordenes.length === 0) {
		container.innerHTML =
			"<p id='servicios-fallback'>No tiene ordenes activas</p>";
		return;
	}

	container.innerHTML = "";

	const ordenesConVehiculo = await Promise.all(
		ordenes.map(async (orden) => {
			const vehiculo = await getVehiculoByPlaca(orden.vehiculoPlaca);
			return { ...orden, vehiculoId: vehiculo.id };
		}),
	);
	ordenesConVehiculo.forEach((orden) => {
		const ordenHtml = `
            <div class="servicios-box">
              <a class="servicio-name order-id" href="orden.html?id=${orden.id}">${orden.id}</a>
              <a class="servicio-name order-vehiculo" id="orden-vehiculo" href="vehiculo.html?placa=${orden.vehiculoPlaca}">${orden.vehiculoModelo}</a>
              <span class="servicio-name order-estado ${getBadgeClass(orden.estado)} info-estado" href="orden.html?id=${orden.id}">${orden.estado}</span>
            </div>`;
		container.insertAdjacentHTML("beforeend", ordenHtml);
	});
}
async function setUpEliminarEmpleado() {
	const btnEliminar = document.getElementById("eliminar-empleado-btn");

	btnEliminar.addEventListener("click", () =>
		openConfirmation(
			"¿Estas seguro? esta accion no se puede deshacer.",
			async () => {
				const res = await removeEmpleado(empleadoId);
				if (res.success) {
					closeSlide();
				}
				return res;
			},
		),
	);
}

document.addEventListener("DOMContentLoaded", initView);
