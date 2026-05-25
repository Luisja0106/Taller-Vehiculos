const clienteId = new URLSearchParams(window.location.search).get("id");

async function initPage() {
	await initializeOrderView();
	setUpCloseButton();
	await setUpEliminarClienteButton();
	await setUpAsignarNuevoVehiculoButton();
}

function setUpCloseButton() {
	const btnClose = document.getElementById("close-slide");
	btnClose.addEventListener("click", closeSlide);
}

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

async function setUpAsignarNuevoVehiculoButton() {
	const nuevoVehiculoBtn = document.getElementById(
		"asignar-nuevo-vehiculo-btn",
	);

	nuevoVehiculoBtn.addEventListener("click", async () => {
		const vehiculos = await getVehiculos();

		openSelectModalVehiculo(
			"Seleccione un vehiculo",
			vehiculos,
			async (vehiculoPlaca) => {
				const resu = await actualizarVehiculo(
					vehiculoPlaca,
					clienteId,
					null,
					null,
					null,
				);
				if (resu.success) {
					window.location.reload();
				}
				return resu;
			},
		);
	});
}

async function setUpEliminarClienteButton() {
	const eliminarBtn = document.getElementById("eliminar-cliente-btn");
	eliminarBtn.addEventListener("click", async () => {
		openConfirmation(
			"¿Estás seguro de que deseas eliminar este cliente?",
			async () => {
				const resu = await removeCliente(clienteId);
				if (resu.success) {
					closeSlide();
				}
				return resu;
			},
		);
	});
}

function closeSlide() {
	window.location.href = "../clientes.html";
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
              <a class="servicio-name vehiculo-modelo" href="vehiculo.html?placa=${vehiculo.placa}">${getMarcaText(vehiculo.marca)} ${vehiculo.modelo} ${vehiculo.anio}</a>
              <a class="servicio-name vehiculo-id info-estado" href="vehiculo.html?placa=${vehiculo.placa}">${vehiculo.placa}</a>
            </div>`;
		container.insertAdjacentHTML("beforeend", ordenHtml);
	});
}

document.addEventListener("DOMContentLoaded", initPage);
