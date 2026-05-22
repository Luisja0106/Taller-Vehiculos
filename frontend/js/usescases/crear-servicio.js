const nuevoServicioBtn = document.getElementById("nuevo-servicio-button");
const cerrarServicioBtn = document.getElementById("modal-close");
const cancelModalBtn = document.getElementById("modal-cancel");
const confirmModalBtn = document.getElementById("modal-confirm");

const modalOverlay = document.getElementById("modal-overlay");
const modalNombre = document.getElementById("modal-nombre");
const modalPrecio = document.getElementById("modal-precio");

function openModalOverlay() {
	modalOverlay.classList.add("open");
}

function cancelarCreation() {
	const modalError = document.getElementById("modal-error");

	modalNombre.value = "";
	modalPrecio.value = "";
	modalError.style.display = "none";

	closeModalOverlay();
}

function closeModalOverlay() {
	modalOverlay.classList.remove("open");
}

async function confirmarCreacion() {
	const nombreServicio = modalNombre.value;
	const precioServicio = modalPrecio.value;
	const modalError = document.getElementById("modal-error");

	if (!nombreServicio || !precioServicio) {
		modalError.textContent = "Llene todos los datos";
		modalError.style.display = "block";
		return;
	}

	const nuevoServicio = await crearServicio(nombreServicio, precioServicio);
	if (!nuevoServicio.success) {
		modalError.textContent = nuevoServicio.error;
		modalError.style.display = "block";
		return;
	}

	closeModalOverlay();
	window.location.href = `./individual-pages/servicio.html?id=${nuevoServicio.data.id}`;
}

confirmModalBtn.addEventListener("click", confirmarCreacion);
nuevoServicioBtn.addEventListener("click", openModalOverlay);
cancelModalBtn.addEventListener("click", cancelarCreation);
cerrarServicioBtn.addEventListener("click", closeModalOverlay);
