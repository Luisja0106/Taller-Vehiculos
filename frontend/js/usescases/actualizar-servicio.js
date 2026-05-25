const actualizarServicioBtn = document.getElementById("actualizar-servcio-btn");
const cerrarServicioBtn = document.getElementById("modal-close");
const cancelModalBtn = document.getElementById("modal-cancel");
const confirmModalBtn = document.getElementById("modal-confirm");

const modalOverlay = document.getElementById("modal-overlay");
const modalNombre = document.getElementById("modal-nombre");
const modalPrecio = document.getElementById("modal-precio");

function openModalOverlay() {
	modalOverlay.classList.add("open");
}

function cancelarActualizacion() {
	const modalError = document.getElementById("modal-error");

	modalNombre.value = "";
	modalPrecio.value = "";
	modalError.style.display = "none";

	closeModalOverlay();
}

function closeModalOverlay() {
	modalOverlay.classList.remove("open");
}

async function confirmarActualizacion() {
	const idServicio = new URLSearchParams(window.location.search).get("id");
	const nombreServicio = modalNombre.value || null;
	const precioServicio = modalPrecio.value || null;
	const modalError = document.getElementById("modal-error");

	const nuevoServicio = await actualizarServicio(
		idServicio,
		nombreServicio,
		precioServicio,
	);
	if (!nuevoServicio.success) {
		modalError.textContent = nuevoServicio.error;
		modalError.style.display = "block";
		return;
	}

	closeModalOverlay();
	window.location.reload();
}

confirmModalBtn.addEventListener("click", confirmarActualizacion);
actualizarServicioBtn.addEventListener("click", openModalOverlay);
cancelModalBtn.addEventListener("click", cancelarActualizacion);
cerrarServicioBtn.addEventListener("click", closeModalOverlay);
