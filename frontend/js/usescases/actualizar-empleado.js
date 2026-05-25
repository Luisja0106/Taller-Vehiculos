const nuevoEmpleadoBtn = document.getElementById("actualizar-empleado-btn");
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

function cancelarActualizacion() {
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

async function confirmarActualizacion() {
	const empleadoId = new URLSearchParams(window.location.search).get("id");
	const nombreEmpleado = modalNombre.value || null;
	const telefonoEmpleado = modalTelefono.value || null;
	const emailEmpleado = modalEmail.value || null;
	const rolEmpleado = modalRol.value || null;
	const contratoEmpleado = modalContrato.value || null;
	const modalError = document.getElementById("modal-error");

	const actuEmpleado = await actualizarEmpleado(
		empleadoId,
		nombreEmpleado,
		telefonoEmpleado,
		emailEmpleado,
		rolEmpleado,
		contratoEmpleado,
	);
	if (!actuEmpleado.success) {
		modalError.textContent = actuEmpleado.error;
		modalError.style.display = "block";
		return;
	}

	closeModalOverlay();
	window.location.reload();
}

confirmModalBtn.addEventListener("click", confirmarActualizacion);
nuevoEmpleadoBtn.addEventListener("click", openModalOverlay);
cancelModalBtn.addEventListener("click", cancelarActualizacion);
cerrarEmpleadoBtn.addEventListener("click", closeModalOverlay);
