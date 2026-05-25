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
	const modalError = document.getElementById("modal-error");

	modalMecanico.innerHTML = "<option value=''>Seleccione un Mecánico</option>";
	modalVehiculo.innerHTML = "<option value=''>Seleccione un Vehículo</option>";
	modalError.style.display = "none";

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

async function confirmarCreacion() {
	const vehiculoPlaca = modalVehiculo.value;
	const empleadoId = modalMecanico.value;
	const modalError = document.getElementById("modal-error");

	if (!vehiculoPlaca || !empleadoId) {
		modalError.textContent = "Selecciona un vehículo y un mecánico";
		modalError.style.display = "block";
		return;
	}

	const nuevaOrden = await crearOrden(vehiculoPlaca, empleadoId);

	if (!nuevaOrden.success) {
		modalError.textContent = nuevaOrden.error;
		modalError.style.display = "block";
		return;
	}

	closeModalOverlay();
	window.location.href = `pages/individual-pages/orden.html?id=${nuevaOrden.data.id}`;
}

confirmModalBtn.addEventListener("click", confirmarCreacion);
nuevaOrdenBtn.addEventListener("click", openModalOverlay);
cancelModalBtn.addEventListener("click", cancelarCreation);
cerrarOrdenBtn.addEventListener("click", closeModalOverlay);
