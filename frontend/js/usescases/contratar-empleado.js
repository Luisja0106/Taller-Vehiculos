const nuevoEmpleadoBtn = document.getElementById("nuevo-empleado-button");
const cerrarEmpleadoBtn = document.getElementById("modal-close");
const cancelModalBtn = document.getElementById("modal-cancel");
const confirmModalBtn = document.getElementById("modal-confirm");

const modalOverlay = document.getElementById("modal-overlay");
const modalNombre = document.getElementById("modal-nombre");
const modalTelefono = document.getElementById("modal-telefono");
const modalEmail = document.getElementById("modal-email");
const modalRol = document.getElementById("modal-rol");
const modalContrato = document.getElementById("modal-contrato");

function openModalOverlay() {
	modalOverlay.classList.add("open");
}

function cancelarCreation() {
	const modalError = document.getElementById("modal-error");

	modalNombre.value = "";
	modalTelefono.value = "";
	modalEmail.value = "";
	modalRol.value = "";
	modalContrato.value = "";
	modalError.style.display = "none";

	closeModalOverlay();
}

function closeModalOverlay() {
	modalOverlay.classList.remove("open");
}

async function confirmarCreacion() {
	const nombreEmpleado = modalNombre.value;
	const telefonoEmpleado = modalTelefono.value;
	const emailEmpleado = modalEmail.value;
	const rolEmpleado = modalRol.value;
	const contratoEmpleado = modalContrato.value;
	const modalError = document.getElementById("modal-error");

	if (
		!nombreEmpleado ||
		!telefonoEmpleado ||
		!emailEmpleado ||
		!rolEmpleado ||
		!contratoEmpleado
	) {
		modalError.textContent = "Llene todos los datos";
		modalError.style.display = "block";
		return;
	}

	const nuevoEmpleado = await registrarEmpleado(
		nombreEmpleado,
		telefonoEmpleado,
		emailEmpleado,
		rolEmpleado,
		contratoEmpleado,
	);
	if (!nuevoEmpleado.success) {
		modalError.textContent = nuevoEmpleado.error;
		modalError.style.display = "block";
		return;
	}

	closeModalOverlay();
	window.location.href = `./individual-pages/empleado.html?id=${nuevoEmpleado.data.id}`;
}

confirmModalBtn.addEventListener("click", confirmarCreacion);
nuevoEmpleadoBtn.addEventListener("click", openModalOverlay);
cancelModalBtn.addEventListener("click", cancelarCreation);
cerrarEmpleadoBtn.addEventListener("click", closeModalOverlay);
