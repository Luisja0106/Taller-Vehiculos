const nuevaOrdenBtn = document.getElementById("nueva-orden-button");
const cerrarOrdenBtn = document.getElementById("modal-close");
const cancelModalBtn = document.getElementById("modal-cancel");
const confirmModalBtn = document.getElementById("modal-confirm");

const modalOverlay = document.getElementById("modal-overlay");
const modalVehiculo = document.getElementById("modal-vehiculo");
const modalMecanico = document.getElementById("modal-mecanico");

function openModalOverlay() {
	setUpOverlay();
	modalOverlay.classList.add("open");
}

function cancelarCreation() {
	const modalVehiculo = document.getElementById("modal-vehiculo");
	const modalMecanico = document.getElementById("modal-mecanico");

	modalMecanico.value = "";
	modalVehiculo.value = "";

	closeModalOverlay();
}

function closeModalOverlay() {
	modalOverlay.classList.remove("open");
}

async function setUpOverlay() {
	const vehiculos = await getVehiculos();
	const mecanicos = await getMecanicos();

	vehiculos.forEach((vehiculo) => {
		const optionVehiculo = new Option(
			`${getMarcaText(vehiculo.marca)} ${vehiculo.modelo} ${vehiculo.anio}`,
			vehiculo.placa,
		);
		modalVehiculo.add(optionVehiculo);
	});

	mecanicos.forEach((mecanico) => {
		const optionMecanico = new Option(mecanico.nombre, mecanico.id);
		modalMecanico.add(optionMecanico);
	});
}

//TODO: add the element modal error in html.
async function confirmarCreacion() {
	const vehiculoPlaca = modalVehiculo.value;
	const empleadoId = modalMecanico.value;

	if (!vehiculoPlaca || !empleadoId) {
		document.getElementById("modal-error").textContent =
			"Selecciona un vehículo y un mecánico";
		return;
	}

	const nuevaOrden = await crearOrden(vehiculoPlaca, empleadoId);

	if (!nuevaOrden) {
		document.getElementById("modal-error").textContent =
			"Error al crear la orden, intenta de nuevo";
		return;
	}

	closeModalOverlay();
	window.location.href = `pages/individual-pages/orden.html?id=${nuevaOrden.id}`;
}

confirmModalBtn.addEventListener("click", confirmarCreacion);
nuevaOrdenBtn.addEventListener("click", openModalOverlay);
cancelModalBtn.addEventListener("click", cancelarCreation);
cerrarOrdenBtn.addEventListener("click", closeModalOverlay);
