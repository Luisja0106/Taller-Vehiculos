const nuevoVehiculoBtn = document.getElementById("nuevo-vehiculo-button");
const cerrarVehiculoBtn = document.getElementById("modal-close");
const cancelModalBtn = document.getElementById("modal-cancel");
const confirmModalBtn = document.getElementById("modal-confirm");

const modalOverlay = document.getElementById("modal-overlay");
const modalPlaca = document.getElementById("modal-placa");
const modalPropietario = document.getElementById("modal-propietario");
const modalModelo = document.getElementById("modal-modelo");
const modalMarca = document.getElementById("modal-marca");
const modalAnio = document.getElementById("modal-anio");

function openModalOverlay() {
	setUpPropietario();
	modalOverlay.classList.add("open");
}

function cancelarCreation() {
	const modalError = document.getElementById("modal-error");

	modalOverlay.value = "";
	modalPlaca.value = "";
	modalPropietario.value = "";
	modalModelo.value = "";
	modalMarca.value = "";
	modalAnio.value = "";
	modalError.style.display = "none";

	closeModalOverlay();
}

function closeModalOverlay() {
	modalOverlay.classList.remove("open");
}

async function confirmarCreacion() {
	const placaVehiculo = modalPlaca.value;
	const propietarioVehiculo = modalPropietario.value;
	const modeloVehiculo = modalModelo.value;
	const marcaVehiculo = modalMarca.value;
	const anioVehiculo = modalAnio.value;
	const modalError = document.getElementById("modal-error");

	if (
		!placaVehiculo ||
		!propietarioVehiculo ||
		!modeloVehiculo ||
		!marcaVehiculo ||
		!anioVehiculo
	) {
		modalError.textContent = "Llene todos los datos";
		modalError.style.display = "block";
		return;
	}

	const nuevoVehiculo = await registrarVehiculo(
		placaVehiculo,
		propietarioVehiculo,
		modeloVehiculo,
		marcaVehiculo,
		anioVehiculo,
	);
	if (!nuevoVehiculo) {
		modalError.textContent = "Error al registrar el vehiculo, intenta de nuevo";
		modalError.style.display = "block";
		return;
	}

	closeModalOverlay();
	window.location.href = `./individual-pages/vehiculo.html?id=${nuevoVehiculo.id}`;
}

async function setUpPropietario() {
	const clientes = await getClientes();

	clientes.forEach((cliente) => {
		const option = new Option(cliente.nombre, cliente.id);
		modalPropietario.add(option);
	});
}

confirmModalBtn.addEventListener("click", confirmarCreacion);
nuevoVehiculoBtn.addEventListener("click", openModalOverlay);
cancelModalBtn.addEventListener("click", cancelarCreation);
cerrarVehiculoBtn.addEventListener("click", closeModalOverlay);
