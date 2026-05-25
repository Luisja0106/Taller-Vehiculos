const nuevoClienteBtn = document.getElementById("actualizar-cliente-btn");
const cerrarClienteBtn = document.getElementById("modal-close");
const cancelModalBtn = document.getElementById("modal-cancel");
const confirmModalBtn = document.getElementById("modal-confirm");

const modalOverlay = document.getElementById("modal-overlay");
const modalNombre = document.getElementById("modal-nombre");
const modalTelefono = document.getElementById("modal-telefono");
const modalEmail = document.getElementById("modal-email");

function openModalOverlay() {
	modalOverlay.classList.add("open");
}

function cancelarActualizacion() {
	const modalError = document.getElementById("modal-error");

	modalNombre.value = "";
	modalTelefono.value = "";
	modalEmail.value = "";
	modalError.style.display = "none";

	closeModalOverlay();
}

function closeModalOverlay() {
	modalOverlay.classList.remove("open");
}

async function confirmarActualizacion() {
	const idCliente = new URLSearchParams(window.location.search).get("id");
	const nombreCliente = modalNombre.value || null;
	const telefonoCliente = modalTelefono.value || null;
	const emailCliente = modalEmail.value || null;
	const modalError = document.getElementById("modal-error");

	const clienteActualizado = await actualizarCliente(
		idCliente,
		nombreCliente,
		telefonoCliente,
		emailCliente,
	);
	if (!clienteActualizado.success) {
		modalError.textContent = clienteActualizado.error;
		modalError.style.display = "block";
		return;
	}

	closeModalOverlay();
	window.location.reload();
}

confirmModalBtn.addEventListener("click", confirmarActualizacion);
nuevoClienteBtn.addEventListener("click", openModalOverlay);
cancelModalBtn.addEventListener("click", cancelarActualizacion);
cerrarClienteBtn.addEventListener("click", closeModalOverlay);
