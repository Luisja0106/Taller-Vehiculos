const nuevoVehiculoBtn = document.getElementById("actualizar-vehiculo-btn");
const cerrarVehiculoBtn = document.getElementById("modal-close");
const cancelModalBtn = document.getElementById("modal-cancel");
const confirmModalBtn = document.getElementById("modal-confirm");

const modalOverlay = document.getElementById("modal-overlay");
const modalPropietario = document.getElementById("modal-propietario");
const modalModelo = document.getElementById("modal-modelo");
const modalMarca = document.getElementById("modal-marca");
const modalAnio = document.getElementById("modal-anio");

function openModalOverlay() {
	setUpPropietario();
	modalOverlay.classList.add("open");
}

function cancelarActualizacion() {
	const modalError = document.getElementById("modal-error");

	modalOverlay.value = "";
	modalPropietario.innerHTML =
		"<option value=''>Seleccione un Propietario</option>";
	modalModelo.value = "";
	modalMarca.value = "";
	modalAnio.value = "";
	modalError.style.display = "none";

	closeModalOverlay();
}

function closeModalOverlay() {
	modalOverlay.classList.remove("open");
}

async function confirmarActualizacion() {
	const placaVehiculo = new URLSearchParams(window.location.search).get(
		"placa",
	);
	const propietarioVehiculo = modalPropietario.value || null;
	const modeloVehiculo = modalModelo.value || null;
	const marcaVehiculo = modalMarca.value || null;
	const anioVehiculo = modalAnio.value || null;
	const modalError = document.getElementById("modal-error");

	const vehiculoActualizado = await actualizarVehiculo(
		placaVehiculo,
		propietarioVehiculo,
		modeloVehiculo,
		marcaVehiculo,
		anioVehiculo,
	);
	if (!vehiculoActualizado.success) {
		modalError.textContent = vehiculoActualizado.error;
		modalError.style.display = "block";
		return;
	}

	closeModalOverlay();
	window.location.reload();
}

async function setUpPropietario() {
	const clientes = await getClientes();

	clientes.forEach((cliente) => {
		const option = new Option(cliente.nombre, cliente.id);
		modalPropietario.add(option);
	});
}

confirmModalBtn.addEventListener("click", confirmarActualizacion);
nuevoVehiculoBtn.addEventListener("click", openModalOverlay);
cancelModalBtn.addEventListener("click", cancelarActualizacion);
cerrarVehiculoBtn.addEventListener("click", closeModalOverlay);
